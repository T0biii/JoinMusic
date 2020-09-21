package de.t0biii.joinmusic.spigot.domain;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

/**
 * Check for updates on BukkitDev for a given plugin, and download the updates if needed.
 * <p>
 * <b>VERY, VERY IMPORTANT</b>: Because there are no standards for adding auto-update toggles in your plugin's config, this system provides NO CHECK WITH YOUR CONFIG to make sure the user has allowed auto-updating.
 * <br>
 * It is a <b>BUKKIT POLICY</b> that you include a boolean value in your config that prevents the auto-updater from running <b>AT ALL</b>.
 * <br>
 * If you fail to include this option in your config, your plugin will be <b>REJECTED</b> when you attempt to submit it to dev.bukkit.org.
 * </p>
 * An example of a good configuration option would be something similar to 'auto-update: true' - if this value is set to false you may NOT run the auto-updater.
 * <br>
 * If you are unsure about these rules, please read the plugin submission guidelines: http://goo.gl/8iU5l
 *
 * @author Gravity 
 * @version 2.3 - modified
 */
 
public class Updater {
 
    /* Constants */

    // Remote file's title
    private static final String TITLE_VALUE = "name";
    // Remote file's download link
    private static final String LINK_VALUE = "downloadUrl";
    // Remote file's release type
    private static final String TYPE_VALUE = "releaseType";
    // Remote file's build version
    private static final String VERSION_VALUE = "gameVersion";
    // Path to GET
    private static final String QUERY = "/servermods/files?projectIds=";
    // Slugs will be appended to this to get to the project's RSS feed
    private static final String HOST = "https://api.curseforge.com";
    // User-agent when querying Curse
    private static final String USER_AGENT = "Updater (by Gravity)";
    // Used for locating version numbers in file names
    private static final String DELIMETER = "^v|[\\s_-]v";
    // If the version number contains one of these, don't update.
    private static final String[] NO_UPDATE_TAG = { "-DEV", "-PRE", "-SNAPSHOT" };
    // Config key for api key
    private static final String API_KEY_CONFIG_KEY = "api-key";
    // Config key for disabling Updater
    private static final String DISABLE_CONFIG_KEY = "disable";
    // Default api key value in config
    private static final String API_KEY_DEFAULT = "PUT_API_KEY_HERE";
    // Default disable value in config
    private static final boolean DISABLE_DEFAULT = false;

    /* User-provided variables */

    // Plugin running Updater
    private final Plugin plugin;
    // Type of update check to run
    private final UpdateType type;
    // The provided callback (if any)
    private final UpdateCallback callback;
    // Project's Curse ID
    private int id;
    // BukkitDev ServerMods API key
    private String apiKey = null;

    /* Collected from Curse API */

    private String versionName;
    private String versionLink;
    private String versionType;
    private String versionGameVersion;

    /* Update process variables */

    // Connection to RSS
    private URL url;
    // Updater thread
    private Thread thread;
    // Used for determining the outcome of the update process
    private Updater.UpdateResult result = Updater.UpdateResult.SUCCESS;

    /**
     * Gives the developer the result of the update process. Can be obtained by called {@link #getResult()}
     */
    public enum UpdateResult {
        /**
         * The updater found an update, and has readied it to be loaded the next time the server restarts/reloads.
         */
        SUCCESS,
        /**
         * The updater did not find an update, and nothing was downloaded.
         */
        NO_UPDATE,
        /**
         * The server administrator has disabled the updating system.
         */
        DISABLED,
        /**
         * The updater found an update, but was unable to download it.
         */
        FAIL_DOWNLOAD,
        /**
         * For some reason, the updater was unable to contact dev.bukkit.org to download the file.
         */
        FAIL_DBO,
        /**
         * When running the version check, the file on DBO did not contain a recognizable version.
         */
        FAIL_NOVERSION,
        /**
         * The id provided by the plugin running the updater was invalid and doesn't exist on DBO.
         */
        FAIL_BADID,
        /**
         * The server administrator has improperly configured their API key in the configuration.
         */
        FAIL_APIKEY,
        /**
         * The updater found an update, but because of the UpdateType being set to NO_DOWNLOAD, it wasn't downloaded.
         */
        UPDATE_AVAILABLE
    }

    /**
     * Allows the developer to specify the type of update that will be run.
     */
    public enum UpdateType {
        /**
         * Run a version check, and then if the file is out of date, download the newest version.
         */
        DEFAULT,
        /**
         * Don't run a version check, just find the latest update and download it.
         */
        NO_VERSION_CHECK,
        /**
         * Get information about the version and the download size, but don't actually download anything.
         */
        NO_DOWNLOAD
    }

    /**
     * Represents the various release types of a file on BukkitDev.
     */
    public enum ReleaseType {
        /**
         * An "alpha" file.
         */
        ALPHA,
        /**
         * A "beta" file.
         */
        BETA,
        /**
         * A "release" file.
         */
        RELEASE
    }

    /**
     * Initialize the updater.
     *
     * @param plugin   The plugin that is checking for an update.
     * @param id       The dev.bukkit.org id of the project.
     * @param type     Specify the type of update this will be. See {@link UpdateType}
     */
    public Updater(Plugin plugin, int id, UpdateType type) {
        this(plugin, id, type, null);
    }

    /**
     * Initialize the updater with the provided callback.
     *
     * @param plugin   The plugin that is checking for an update.
     * @param id       The dev.bukkit.org id of the project.
     * @param type     Specify the type of update this will be. See {@link UpdateType}
     * @param callback The callback instance to notify when the Updater has finished
     */
    public Updater(Plugin plugin, int id, UpdateType type, UpdateCallback callback) {
        this.plugin = plugin;
        this.type = type;
        this.id = id;
        this.callback = callback;

        final File pluginFile = this.plugin.getDataFolder().getParentFile();
        final File updaterFile = new File(pluginFile, "Updater");
        final File updaterConfigFile = new File(updaterFile, "config.yml");

        YamlConfiguration config = new YamlConfiguration();
        config.options().header("This configuration file affects all plugins using the Updater system (version 2+ - http://forums.bukkit.org/threads/96681/ )" + '\n'
                + "If you wish to use your API key, read http://wiki.bukkit.org/ServerMods_API and place it below." + '\n'
                + "Some updating systems will not adhere to the disabled value, but these may be turned off in their plugin's configuration.");
        config.addDefault(API_KEY_CONFIG_KEY, API_KEY_DEFAULT);
        config.addDefault(DISABLE_CONFIG_KEY, DISABLE_DEFAULT);

        if (!updaterFile.exists()) {
            this.fileIOOrError(updaterFile, updaterFile.mkdir());
        }

        boolean createFile = !updaterConfigFile.exists();
        try {
            if (createFile) {
                this.fileIOOrError(updaterConfigFile, updaterConfigFile.createNewFile());
                config.options().copyDefaults(true);
                config.save(updaterConfigFile);
            } else {
                config.load(updaterConfigFile);
            }
        } catch (final Exception e) {
            final String message;
            if (createFile) {
                message = "The updater could not create configuration at " + updaterFile.getAbsolutePath();
            } else {
                message = "The updater could not load configuration at " + updaterFile.getAbsolutePath();
            }
            this.plugin.getLogger().log(Level.SEVERE, message, e);
        }

        if (config.getBoolean(DISABLE_CONFIG_KEY)) {
            this.result = UpdateResult.DISABLED;
            return;
        }

        String key = config.getString(API_KEY_CONFIG_KEY);
        if (API_KEY_DEFAULT.equalsIgnoreCase(key) || "".equals(key)) {
            key = null;
        }

        this.apiKey = key;

        try {
            this.url = new URL(Updater.HOST + Updater.QUERY + this.id);
        } catch (final MalformedURLException e) {
            this.plugin.getLogger().log(Level.SEVERE, "The project ID provided for updating, " + this.id + " is invalid.", e);
            this.result = UpdateResult.FAIL_BADID;
        }

        if (this.result != UpdateResult.FAIL_BADID) {
            this.thread = new Thread(new UpdateRunnable());
            this.thread.start();
        } else {
            runUpdater();
        }
    }

    /**
     * Get the result of the update process.
     *
     * @return result of the update process.
     * @see UpdateResult
     */
    public Updater.UpdateResult getResult() {
        this.waitForThread();
        return this.result;
    }

    /**
     * Get the latest version's release type.
     *
     * @return latest version's release type.
     * @see ReleaseType
     */
    public ReleaseType getLatestType() {
        this.waitForThread();
        if (this.versionType != null) {
            for (ReleaseType type : ReleaseType.values()) {
                if (this.versionType.equalsIgnoreCase(type.name())) {
                    return type;
                }
            }
        }
        return null;
    }

    /**
     * Get the latest version's game version (such as "CB 1.2.5-R1.0").
     *
     * @return latest version's game version.
     */
    public String getLatestGameVersion() {
        this.waitForThread();
        return this.versionGameVersion;
    }

    /**
     * Get the latest version's name (such as "Project v1.0").
     *
     * @return latest version's name.
     */
    public String getLatestName() {
        this.waitForThread();
        return this.versionName;
    }

    /**
     * Get the latest version's direct file link.
     *
     * @return latest version's file link.
     */
    public String getLatestFileLink() {
        this.waitForThread();
        return this.versionLink;
    }

    /**
     * As the result of Updater output depends on the thread's completion, it is necessary to wait for the thread to finish
     * before allowing anyone to check the result.
     */
    private void waitForThread() {
        if ((this.thread != null) && this.thread.isAlive()) {
            try {
                this.thread.join();
            } catch (final InterruptedException e) {
                this.plugin.getLogger().log(Level.SEVERE, null, e);
            }
        }
    }


    /**
     * Check to see if the program should continue by evaluating whether the plugin is already updated, or shouldn't be updated.
     *
     * @return true if the version was located and is not the same as the remote's newest.
     */
    private boolean versionCheck() {
        final String title = this.versionName;
        if (this.type != UpdateType.NO_VERSION_CHECK) {
            final String localVersion = this.plugin.getDescription().getVersion();
            if (title.split(DELIMETER).length >= 2) {
                // Get the newest file's version number
                final String remoteVersion = title.split(DELIMETER)[title.split(DELIMETER).length - 1].split(" ")[0];

                if (this.hasTag(localVersion) || !this.shouldUpdate(localVersion, remoteVersion)) {
                    // We already have the latest version, or this build is tagged for no-update
                    this.result = Updater.UpdateResult.NO_UPDATE;
                    return false;
                }
            } else {
                // The file's name did not contain the string 'vVersion'
                final String authorInfo = this.plugin.getDescription().getAuthors().isEmpty() ? "" : " (" + this.plugin.getDescription().getAuthors().get(0) + ")";
                this.plugin.getLogger().warning("The author of this plugin" + authorInfo + " has misconfigured their Auto Update system");
                this.plugin.getLogger().warning("File versions should follow the format 'PluginName vVERSION'");
                this.plugin.getLogger().warning("Please notify the author of this error.");
                this.result = Updater.UpdateResult.FAIL_NOVERSION;
                return false;
            }
        }
        return true;
    }

    /**
     * <b>If you wish to run mathematical versioning checks, edit this method.</b>
     * <p>
     * With default behavior, Updater will NOT verify that a remote version available on BukkitDev
     * which is not this version is indeed an "update".
     * If a version is present on BukkitDev that is not the version that is currently running,
     * Updater will assume that it is a newer version.
     * This is because there is no standard versioning scheme, and creating a calculation that can
     * determine whether a new update is actually an update is sometimes extremely complicated.
     * </p>
     * <p>
     * Updater will call this method from {@link #versionCheck()} before deciding whether
     * the remote version is actually an update.
     * If you have a specific versioning scheme with which a mathematical determination can
     * be reliably made to decide whether one version is higher than another, you may
     * revise this method, using the local and remote version parameters, to execute the
     * appropriate check.
     * </p>
     * <p>
     * Returning a value of <b>false</b> will tell the update process that this is NOT a new version.
     * Without revision, this method will always consider a remote version at all different from
     * that of the local version a new update.
     * </p>
     * @param localVersion the current version
     * @param remoteVersion the remote version
     * @return true if Updater should consider the remote version an update, false if not.
     */
    public boolean shouldUpdate(String localVersion, String remoteVersion) {
        return !localVersion.equalsIgnoreCase(remoteVersion);
    }

    /**
     * Evaluate whether the version number is marked showing that it should not be updated by this program.
     *
     * @param version a version number to check for tags in.
     * @return true if updating should be disabled.
     */
    private boolean hasTag(String version) {
        for (final String string : Updater.NO_UPDATE_TAG) {
            if (version.contains(string)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Make a connection to the BukkitDev API and request the newest file's details.
     *
     * @return true if successful.
     */
    private boolean read() {
        try {
            final URLConnection conn = this.url.openConnection();
            conn.setConnectTimeout(5000);

            if (this.apiKey != null) {
                conn.addRequestProperty("X-API-Key", this.apiKey);
            }
            conn.addRequestProperty("User-Agent", Updater.USER_AGENT);

            conn.setDoOutput(true);

            final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final String response = reader.readLine();

            final JSONArray array = (JSONArray) JSONValue.parse(response);

            if (array.isEmpty()) {
                this.plugin.getLogger().warning("The updater could not find any files for the project id " + this.id);
                this.result = UpdateResult.FAIL_BADID;
                return false;
            }

            JSONObject latestUpdate = (JSONObject) array.get(array.size() - 1);
            this.versionName = (String) latestUpdate.get(Updater.TITLE_VALUE);
            this.versionLink = (String) latestUpdate.get(Updater.LINK_VALUE);
            this.versionType = (String) latestUpdate.get(Updater.TYPE_VALUE);
            this.versionGameVersion = (String) latestUpdate.get(Updater.VERSION_VALUE);

            return true;
        } catch (final IOException e) {
            if (e.getMessage().contains("HTTP response code: 403")) {
                this.plugin.getLogger().severe("dev.bukkit.org rejected the API key provided in plugins/Updater/config.yml");
                this.plugin.getLogger().severe("Please double-check your configuration to ensure it is correct.");
                this.result = UpdateResult.FAIL_APIKEY;
            } else {
                this.plugin.getLogger().severe("The updater could not contact dev.bukkit.org for updating.");
                this.plugin.getLogger().severe("If you have not recently modified your configuration and this is the first time you are seeing this message, the site may be experiencing temporary downtime.");
                this.result = UpdateResult.FAIL_DBO;
            }
            this.plugin.getLogger().log(Level.SEVERE, null, e);
            return false;
        }
    }

    /**
     * Perform a file operation and log any errors if it fails.
     * @param file file operation is performed on.
     * @param result result of file operation.
     */
    private void fileIOOrError(File file, boolean result) {
        if (!result) {
            this.plugin.getLogger().severe("The updater could not create file at: " + file.getAbsolutePath());
        }
    }

    /**
     * Called on main thread when the Updater has finished working, regardless
     * of result.
     */
    public interface UpdateCallback {
        /**
         * Called when the updater has finished working.
         * @param updater The updater instance
         */
        void onFinish(Updater updater);
    }

    private class UpdateRunnable implements Runnable {
        @Override
        public void run() {
            runUpdater();
        }
    }

    private void runUpdater() {
        if (this.url != null && (this.read() && this.versionCheck())) {
            // Obtain the results of the project's file feed
            if ((this.versionLink != null) && (this.type == UpdateType.NO_DOWNLOAD)) {
                this.result = UpdateResult.UPDATE_AVAILABLE;
            }
        }

        if (this.callback != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    runCallback();
                }
            }.runTask(this.plugin);
        }
    }

    private void runCallback() {
        this.callback.onFinish(this);
    }
}
