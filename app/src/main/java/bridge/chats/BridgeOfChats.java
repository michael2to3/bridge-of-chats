package bridge.chats;

import bridge.chats.Platform.TelegramPlatform;
import bridge.chats.Platform.VkPlatform;
import bridge.chats.Utils.Config;

class BridgeOfChats {
    private final static Config config = new Config();

    public static void run() {
        VkPlatform vk = new VkPlatform(config.get("vk.token"));
        TelegramPlatform telegram = new TelegramPlatform("test", config.get("telegram.token"));
        String vid = "2000000001";
        String tid = "-495016909";
        Linker vk2tg = new Linker(vk, telegram, vid, tid);
        Linker tg2vk = new Linker(telegram, vk, tid, vid);
        var manager = new LinkerManager();
        manager.startLinker(vk2tg);
        manager.startLinker(tg2vk);
    }
}
