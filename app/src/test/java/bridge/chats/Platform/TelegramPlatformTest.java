package bridge.chats.Platform;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramPlatformTest {
  private TelegramPlatform platform = new TelegramPlatform(null, null);

  @Test
  public void testHandleUpdate() {
    Update update = new Update();
  }
}
