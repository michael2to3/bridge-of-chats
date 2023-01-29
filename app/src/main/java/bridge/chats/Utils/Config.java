package bridge.chats.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {
    private final Logger logger = LoggerFactory.getLogger(Config.class);
    private String configpath = System.getProperty("user.dir") + File.separator + "config.properties";
    private Properties prop;

    public Config() {
        try (var file = new FileInputStream(configpath)) {
            load(file);
        } catch (FileNotFoundException e) {
            logger.error("File does not exists: Config properity file not exists");
        } catch (IOException e) {
            logger.error("Permission denied: Config properity file read was error");
        }
    }

    public Config(InputStream stream) {
        try {
            load(stream);
        } catch (IOException e) {
            logger.error("Permission denied: Stream for Config properity was aborted with an error");
        }
    }

    public String getConfigpath() {
        return configpath;
    }

    public void setConfigpath(String configpath) {
        this.configpath = configpath;
    }

    final public String get(String key) {
        String envSystem = System.getenv(formatShellVarible(key));
        if (envSystem != null && !envSystem.isEmpty()) {
            return envSystem;
        }
        String envJvm = System.getProperty(formatShellVarible(key));
        if (envJvm != null && !envJvm.isEmpty()) {
            return envJvm;
        }
        return prop.getProperty(key);
    }

    private void load(InputStream stream) throws IOException {
        this.prop = new Properties();
        prop.load(stream);
    }

    private static String formatShellVarible(String name) {
        return name.toUpperCase().replace('.', '_');
    }
}
