package bridge.chats.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Prop {
    final private String filename = "config.properties";
    private Properties prop;

    public Prop() throws IOException {
        this.prop = new Properties();
        try (var file = new FileInputStream(filename)) {
            this.prop.load(file);
        } catch (FileNotFoundException e) {
            // @FIXME create template config file
        }
    }

    final public String get(String key) {
        return prop.getProperty(key);
    }
}
