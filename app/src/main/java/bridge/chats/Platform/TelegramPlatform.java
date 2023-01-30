package bridge.chats.Platform;

import bridge.chats.Handler.TelegramHandler;
import bridge.chats.Object.Message;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramPlatform extends TelegramLongPollingBot implements Platform {
  private static final Logger LOGGER = LoggerFactory.getLogger(TelegramPlatform.class);
  private static final TelegramHandler handler = new TelegramHandler();
  private static String TOKEN;
  private static String USERNAME;
  private static List<Message> messages = new ArrayList<>();

  public TelegramPlatform(final String username, final String token) {
    USERNAME = username;
    TOKEN = token;

    try {
      TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
      botsApi.registerBot(this);
    } catch (TelegramApiException e) {
      LOGGER.error(e.toString());
    }
  }

  // TODO add send sticker, gifts and other doc type message
  @Override
  public void send(final Message message) {
    final SendMessage sendMessage = new SendMessage();
    String text = handler.formatMessage(message);
    sendMessage.setText(text);
    sendMessage.setChatId(message.getConversationId());

    try {
      execute(sendMessage);
    } catch (final TelegramApiException e) {
      LOGGER.error(e.toString());
    }
  }

  @Override
  public List<Message> receiveMessages() {
    final var copy = new ArrayList<>(messages);
    messages.clear();
    return copy;
  }

  @Override
  public String getBotUsername() {
    return USERNAME;
  }

  @Override
  public String getBotToken() {
    return TOKEN;
  }

  @Override
  public void onUpdateReceived(final Update update) {
    final Message message = handler.generate(update);
    messages.add(message);
  }

  @Override
  public void onClosing() {
    // Close session
  }
}
