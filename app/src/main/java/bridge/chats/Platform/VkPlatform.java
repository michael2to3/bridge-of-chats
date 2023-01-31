package bridge.chats.Platform;

import api.longpoll.bots.LongPollBot;
import api.longpoll.bots.exceptions.VkApiException;
import api.longpoll.bots.model.events.messages.MessageNew;
import api.longpoll.bots.model.objects.basic.Community;
import api.longpoll.bots.model.objects.basic.User;
import bridge.chats.Handler.VkHandler;
import bridge.chats.Object.Message;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VkPlatform extends LongPollBot implements Platform {
  private static final Logger LOGGER = LoggerFactory.getLogger(VkPlatform.class);
  private static final VkHandler handler = new VkHandler();
  private static String TOKEN;
  private final List<Message> messages = new ArrayList<>();
  private Thread thread;

  public VkPlatform(final String token) {
    TOKEN = token;
  }

  @Override
  public void onMessageNew(final MessageNew messageNew) {
    Message message = handler.generate(messageNew);
    message.setUsername(getUsername(message.getUsername()));
    messages.add(message);
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
    var id = Integer.parseInt(message.getConversationId());
    final String text = handler.formatMessage(message);
    var resp = vk.messages.send().setMessage(text).setPeerId(id).executeAsync();
    LOGGER.error(resp.join().toString());
  }

  private String getUsername(String id) {
    String from = "";
    try {
      if (id.contains("-")) {
        Community group = vk.groups.getById()
            .setGroupId(id.replaceAll("-", ""))
            .execute()
            .getResponse().get(0);
        from = group.getName();
      } else {
        User user = vk.users.get().setUserIds(id).execute().getResponse().get(0);
        from = user.getFirstName() + " " + user.getLastName();
      }
    } catch (VkApiException e) {
      LOGGER.error(e.toString());
      from = id;
    }
    return from;
  }
}
