package bridge.chats.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConfigTest {
  private Config config;

  @BeforeEach
  public void setUp() throws Exception {
    String content = "telegram.token=abc\n"
        + "proxy.host=example.com\n"
        + "proxy.port=8080\n";
    var bytes = content.getBytes(StandardCharsets.UTF_8);
    InputStream input = new ByteArrayInputStream(bytes);
    config = new Config(input);
  }

  @Test
  public void testGetProperty() {
    System.clearProperty("TELEGRAM_TOKEN");
    System.clearProperty("PROXY_HOST");
    System.clearProperty("PROXY_PORT");
    assertEquals("abc", config.get("telegram.token"));
    assertEquals("example.com", config.get("proxy.host"));
    assertEquals("8080", config.get("proxy.port"));
  }

  @Test
  public void testGetPropertyWithEnvironmentVariable() {
    System.setProperty("TELEGRAM_TOKEN", "def");
    System.setProperty("PROXY_HOST", "example2.com");
    System.setProperty("PROXY_PORT", "9090");
    assertEquals("def", config.get("telegram.token"));
    assertEquals("example2.com", config.get("proxy.host"));
    assertEquals("9090", config.get("proxy.port"));
  }

  @Test
  public void testNotFoundVarible() {
    assertNull(config.get("something_not_exists"));
  }
}
