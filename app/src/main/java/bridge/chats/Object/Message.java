package bridge.chats.Object;

import java.util.Date;

public class Message extends Object {
    public Date timestamp;
    public String username;
    public String text;
    public String media;

    public Message(Date timestamp,
            String username,
            String text,
            String media) {
        this.timestamp = timestamp;
        this.username = username;
        this.text = text;
        this.media = media;
    }

    @Override
    public String toString() {
        return String.format("%s - [%s]: %s -- %s", timestamp, username, text, media);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }
}
