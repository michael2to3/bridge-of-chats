package bridge.chats.Platform;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import bridge.chats.Object.Message;

public class TelegramPlatform extends TelegramLongPollingBot implements Platform {
	private static final Logger LOGGER = LoggerFactory.getLogger(TelegramPlatform.class);
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

	@Override
	public void send(final Message message) {
		final SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(message.conversationId);
		sendMessage.setText(message.getText());

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
		var umessage = update.getMessage();
		if (update.hasMessage() && umessage.hasText()) {
			final String username = umessage.getFrom().getUserName();
			final String text = umessage.getText();
			final Date date = new Date(umessage.getDate());
			final String id = String.valueOf(umessage.getChatId());
			final var message = new Message(date, username, text, id);
			messages.add(message);
		} else {
			LOGGER.error("Oops i don't know what is it");
		}
	}

	@Override
	public void onClosing() {
		// Close session
	}
}
