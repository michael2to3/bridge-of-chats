package bridge.chats.Platform;

import bridge.chats.Object.Message;
import java.util.List;

/**
 * Service
 */
public interface Platform {
  public void send(Message message);

  public List<Message> receiveMessages();
}
