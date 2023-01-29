package bridge.chats;

import java.util.List;
import java.util.concurrent.TimeUnit;

import bridge.chats.Object.Message;
import bridge.chats.Platform.Platform;

class PlatformLinker {

    private Platform source;
    private Platform destination;

    public PlatformLinker(Platform source, Platform destination) {
        this.source = source;
        this.destination = destination;
    }

    public void start() {
        while (true) {
            List<Message> messages = source.receiveMessages();
            for (Message message : messages) {
                destination.send(message);
            }
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                System.out.println("Sleep error");
            }
        }
    }
}
