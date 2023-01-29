package bridge.chats.Platform;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api.longpoll.bots.LongPollBot;
import api.longpoll.bots.exceptions.VkApiException;
import api.longpoll.bots.model.events.messages.MessageNew;
import bridge.chats.Object.Message;

public class VkPlatform extends LongPollBot implements Platform {
	private static final Logger LOGGER = LoggerFactory.getLogger(VkPlatform.class);
	private static String TOKEN;
	private final List<Message> messages = new ArrayList<>();
	private Thread thread;

	public VkPlatform(final String token) {
		TOKEN = token;
	}

	@Override
	public void onMessageNew(final MessageNew messageNew) {
		final var message = messageNew.getMessage();
		final Date date = new Date(message.getDate());
		final String username = message.getFromId().toString();
		final String text = message.getText();
		final String id = String.valueOf(message.getPeerId());
		final var sendMessage = new Message(date, username, text, id);
		messages.add(sendMessage);
	}

	@Override
	public String getAccessToken() {
		return TOKEN;
	}

	@Override
	public void startPolling() {
		if (thread == null) {
			thread = new Thread(() -> {
				try {
					super.startPolling();
				} catch (final VkApiException e) {
					LOGGER.error("VK auth not be pass - " + e);
				}
			}, "vkPlatform");
			thread.start();
		}
	}

	public void stopPolling() {
		try {
			thread.join();
		} catch (final InterruptedException e) {
			LOGGER.error("Error: Thread is lock:" + e.toString());
		}
	}

	@Override
	public List<Message> receiveMessages() {
		startPolling();
		final var copy = new ArrayList<>(messages);
		messages.clear();
		return copy;
	}

	@Override
	public void send(final Message message) {
		var text = message.getText();
		var id = Integer.parseInt(message.getConversationId());
		var resp = vk.messages.send().setMessage(text).setPeerId(id).executeAsync();
		LOGGER.error(resp.join().toString());
	}
}
