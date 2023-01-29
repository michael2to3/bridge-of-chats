package bridge.chats.Platform;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import bridge.chats.Object.Message;

public class TelegramPlatform extends TelegramLongPollingBot implements Platform {
  private static String TOKEN;
  private static String USERNAME;
  private static List<Message> messages;

  public TelegramPlatform(String username, String token) {
    USERNAME = username;
    TOKEN = token;
    messages = new ArrayList<>();
  }

  @Override
  public void send(Message message) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(1928159074L);
    sendMessage.setText(message.getText());

    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Message> receiveMessages() {
    var copy = new ArrayList<>(messages);
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
  public void onUpdateReceived(Update update) {
    String username = update.getMessage().getFrom().getUserName();
    String text = update.getMessage().getText();
    Date date = new Date(update.getMessage().getDate());
    var message = new Message(date, username, text, "");
    messages.add(message);
  }
}
