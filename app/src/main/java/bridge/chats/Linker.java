package bridge.chats;

import java.util.List;
import java.util.concurrent.TimeUnit;

import bridge.chats.Object.Message;
import bridge.chats.Platform.Platform;

class Linker {
    private Platform source;
    private Platform destination;
    private String sourceIdConversation;

    public Platform getSource() {
        return source;
    }

    public void setSource(Platform source) {
        this.source = source;
    }

    public Platform getDestination() {
        return destination;
    }

    public void setDestination(Platform destination) {
        this.destination = destination;
    }

    public String getSourceIdConversation() {
        return sourceIdConversation;
    }

    public void setSourceIdConversation(String sourceIdConversation) {
        this.sourceIdConversation = sourceIdConversation;
    }

    public String getDestinationIdConversation() {
        return destinationIdConversation;
    }

    public void setDestinationIdConversation(String destinationIdConversation) {
        this.destinationIdConversation = destinationIdConversation;
    }

    private String destinationIdConversation;

    public Linker(Platform source, Platform destination, String sourceIdConversation,
            String destinationIdConversation) {
        this.source = source;
        this.destination = destination;
        this.sourceIdConversation = sourceIdConversation;
        this.destinationIdConversation = destinationIdConversation;
    }

    public void start() {
        while (true) {
            List<Message> messages = source.receiveMessages();
            for (Message message : messages) {
                message.setConversationId(destinationIdConversation);
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
