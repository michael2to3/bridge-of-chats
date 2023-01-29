package bridge.chats.Platform;

import java.util.List;

import bridge.chats.Object.Message;

/**
 * Service
 */
public interface Platform {
    void send(Message message);

    List<Message> receiveMessages();
}
