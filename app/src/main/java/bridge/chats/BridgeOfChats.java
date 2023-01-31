package bridge.chats;

import bridge.chats.Platform.TelegramPlatform;
import bridge.chats.Platform.VkPlatform;
import bridge.chats.Utils.Config;

class BridgeOfChats {
  private final static Config config = new Config();

  public static void run() {
    VkPlatform vk = new VkPlatform(config.get("vk.token"));
    TelegramPlatform telegram = new TelegramPlatform("test", config.get("telegram.token"));
    String vksliv = "2000000002";
    String vid = "2000000003";
    String tid = "-874767332";
    Linker sliv2tg = new Linker(vk, telegram, vksliv, tid);
    Linker vk2tg = new Linker(vk, telegram, vid, tid);
    Linker tg2vk = new Linker(telegram, vk, tid, vid);
    var manager = new LinkerManager();
    manager.startLinker(sliv2tg);
    manager.startLinker(vk2tg);
    manager.startLinker(tg2vk);
  }
}
