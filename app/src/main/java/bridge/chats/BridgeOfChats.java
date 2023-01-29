package bridge.chats;

import bridge.chats.Platform.TelegramPlatform;
import bridge.chats.Platform.VkPlatform;
import bridge.chats.Utils.Config;

class BridgeOfChats {
    private final static Config config = new Config();

    public static void run() {
        VkPlatform vk = new VkPlatform(config.get("vk.token"), Integer.parseInt(config.get("vk.groupid")));
        TelegramPlatform telegram = new TelegramPlatform("test", config.get("telegram.token"));
        Linker vk2tg = new Linker(vk, telegram, "-218522752", "-495016909");
        Linker tg2vk = new Linker(telegram, vk, "-495016909", "-218522752");
        var manager = new LinkerManager();
        manager.startLinker(vk2tg);
        manager.startLinker(tg2vk);
    }
}
