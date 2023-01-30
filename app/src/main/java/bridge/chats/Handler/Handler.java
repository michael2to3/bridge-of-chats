package bridge.chats.Handler;

import bridge.chats.Object.Message;
import java.text.SimpleDateFormat;

class Handler<T> {
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("H:m");
  private static String pattern = "{date} - {username}\n {body}";

  public static String getPattern() {
    return pattern;
  }

  public static void setPattern(String pattern) {
    Handler.pattern = pattern;
  }

  public String formatMessage(Message message) {
    String username = message.getUsername();
    String body = message.getText();
    String date = dateFormat.format(message.getTimestamp());

    return pattern.replace("{username}", username).replace("{body}", body).replace("{date}", date);
  }

  public Message generate(T object) {
    return null;
  }
}
