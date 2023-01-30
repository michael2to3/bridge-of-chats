package bridge.chats.Platform;

import bridge.chats.Object.Message;
import java.util.List;

/**
 * Service
 */
public interface Platform {
  void send(Message message);

  List<Message> receiveMessages();
}
