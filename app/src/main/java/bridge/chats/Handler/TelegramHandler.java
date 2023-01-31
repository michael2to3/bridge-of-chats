package bridge.chats.Handler;

import bridge.chats.Object.Message;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramHandler extends Handler<Update> {
  private static final Logger LOGGER = LoggerFactory.getLogger(TelegramHandler.class);

  @Override
  public Message generate(Update update) {
    var umessage = update.getMessage();
    return generate(umessage);
  }

  public Message generate(org.telegram.telegrambots.meta.api.objects.Message umessage) {
    final String username = umessage.getFrom().getUserName();
    final Date date = new Date(umessage.getDate());
    final String id = String.valueOf(umessage.getChatId());
    final var message = new Message(date, username, "", id);

    if (umessage.hasText()) {
      final String text = umessage.getText();
      message.setText(text);
    } else {
      message.setText("Oops i don't know what is it");
    }
    var reply = umessage.getReplyToMessage();
    if (reply != null) {
      message.setText(message.getText() + "\n" + generate(reply));
    }
    return message;
  }
}
