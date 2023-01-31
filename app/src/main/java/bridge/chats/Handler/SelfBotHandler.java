package bridge.chats.Handler;

import bridge.chats.Object.Message;
import java.util.Date;

public class SelfBotHandler extends Handler<Message> {
  private static String separator = " ";

  public SelfBotHandler() {}

  public Message getAnswer(Message message, String preffix) {
    var ansMessage = callCommand(message);
    return ansMessage;
  }

  @Override
  public Message generate(Message message) {
    return null;
  }

  private Message callCommand(Message message) {
    String body = message.getText();
    String[] cmds = body.split(separator);
    String command = cmds[1];
    switch (command) {
      case "help":
        return cmdHelp(message);
      case "getid":
        return cmdGetId(message);
      case "connect":
        return cmdConnect(message);
    }
    return null;
  }

  private Message confirmLink(String id) {
    return null;
  }

  private String createLink() {
    return null;
  }

  private Message cmdConnect(Message message) {
    return message;
  }

  private Message cmdGetId(Message message) {
    String username = "[BOT]"; // TODO set username bot
    Date now = new Date();
    String body = "Ваш ID: " + message.getConversationId();

    return new Message(now, username, body, null);
  }

  private Message cmdHelp(Message message) {
    String username = "[BOT]"; // TODO set username bot
    Date now = new Date();
    String body =
        "Каждое сообщение должно начинать с {preffix} бота, каждая команда должна разделяться {separator}\n"
        + "help - вывод всех сообщений\n"
        + "getid - вывод id беседы\n"
        + "connect - создать связку\n"
        + "connect {ID} - подтвердить связку\n";

    return new Message(now, username, body, null);
  }
}
