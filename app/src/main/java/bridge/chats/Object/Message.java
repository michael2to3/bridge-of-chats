package bridge.chats.Object;

import java.util.Date;

public class Message extends Object {
  private Date timestamp;
  private String username;
  private String text;
  private String conversationId;

  public Message(
      final Date timestamp, final String username, final String text, final String conversationId) {
    this.timestamp = timestamp;
    this.username = username;
    this.text = text;
    this.conversationId = conversationId;
  }

  public String getConversationId() {
    return conversationId;
  }

  public void setConversationId(final String id) {
    this.conversationId = id;
  }

  @Override
  public String toString() {
    return String.format("%s | %s | - [%s]: %s", timestamp, conversationId, username, text);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getText() {
    return text;
  }

  public void setText(final String text) {
    this.text = text;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(final Date timestamp) {
    this.timestamp = timestamp;
  }
}
