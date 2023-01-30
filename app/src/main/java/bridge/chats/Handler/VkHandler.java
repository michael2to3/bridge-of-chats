package bridge.chats.Handler;

import api.longpoll.bots.model.events.messages.MessageNew;
import api.longpoll.bots.model.objects.media.Attachment;
import bridge.chats.Object.Message;
import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VkHandler extends Handler<MessageNew> {
  private static final Logger LOGGER = LoggerFactory.getLogger(VkHandler.class);

  @Override
  public Message generate(MessageNew messageNew) {
    final var message = messageNew.getMessage();
    return generate(message);
  }

  public Message generate(api.longpoll.bots.model.objects.basic.Message message) {
    final var attachments = message.getAttachments();
    final Date date = new Date(message.getDate());

    final String username = message.getFromId().toString(); // TODO change human name
    final String replay = Joiner.on("\n ----------- \n").skipNulls().join(generateReply(message));
    final String body = generateBody(message.getText(), attachemntsToString(attachments), replay);
    final String id = String.valueOf(message.getPeerId());

    return new Message(date, username, body.toString(), id);
  }

  private String generateBody(String... list) {
    return Joiner.on("\n").skipNulls().join(list);
  }

  private List<Message> generateReply(api.longpoll.bots.model.objects.basic.Message message) {
    List<Message> reply = new ArrayList<>();
    if (message.hasFwdMessages()) {
      var fwds = message.getFwdMessages();
      for (var fwd : fwds) {
        reply.add(generate(fwd));
      }
    }
    // FIXME send reply message no more 2 depth
    if (message.hasReplyMessage()) {
      var rep = message.getReplyMessage();
      reply.add(generate(rep));
    }
    return reply;
  }

  private String attachemntsToString(List<Attachment> attachments) {
    StringBuilder urlAttach = new StringBuilder();
    for (var attachment : attachments) {
      urlAttach.append(attachmentToString(attachment));
      urlAttach.append("\n");
    }
    return urlAttach.toString();
  }

  private String attachmentToString(Attachment attachment) {
    String raw = attachment.toString();

    Pattern pattern = Pattern.compile("url='(.*?)'");
    Matcher matcher = pattern.matcher(raw);

    while (matcher.find()) {
      raw = matcher.group(1);
    }

    return raw;
  }
}
