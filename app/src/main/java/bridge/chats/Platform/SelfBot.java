package bridge.chats.Platform;

import bridge.chats.Handler.*;
import bridge.chats.Object.Message;
import java.util.ArrayList;
import java.util.List;

public class SelfBot implements Platform {
  private static String preffix;
  private static List<Message> messages = new ArrayList<>();
  private static SelfBotHandler handler = new SelfBotHandler();

  public SelfBot(String preffix) {
    SelfBot.preffix = preffix;
  }

  public static String getPreffix() {
    return preffix;
  }

  public static void setPreffix(String preffix) {
    SelfBot.preffix = preffix;
  }

  @Override
  public void send(Message message) {
    Message ans = handler.getAnswer(message, preffix);
    messages.add(ans);
  }

  @Override
  public List<Message> receiveMessages() {
    // TODO Auto-generated method stub
    return null;
  }
}
