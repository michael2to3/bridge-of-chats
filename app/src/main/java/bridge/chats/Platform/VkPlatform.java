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
	private static String VK_TOKEN;
	private List<Message> messages;

	public VkPlatform(String token) {
		VK_TOKEN = token;
		messages = new ArrayList<>();
	}

	@Override
	public void onMessageNew(MessageNew messageNew) {
		var message = messageNew.getMessage();
		var sendMessage = new Message(new Date(message.getDate() * 1000L), message.getFromId().toString(),
				message.getText(), "");
		messages.add(sendMessage);
	}

	@Override
	public String getAccessToken() {
		return VK_TOKEN;
	}

	@Override
	public void startPolling() {
		new Thread(() -> {
			try {
				super.startPolling();
			} catch (VkApiException e) {
				e.printStackTrace();
			}
		}).start();
	}

	@Override
	public List<Message> receiveMessages() {

		startPolling();
		var copy = new ArrayList<>(messages);
		messages.clear();
		return copy;
	}

	@Override
	public void send(Message message) {
		// TODO FUCK
	}
}
