package bridge.chats;

import bridge.chats.Platform.TelegramPlatform;
import bridge.chats.Platform.VkPlatform;

import bridge.chats.Utils.*;

class BridgeOfChats {
    private final static Config config = new Config();

    public static void run() {
        VkPlatform vk = new VkPlatform(config.get("vk.token"));
        TelegramPlatform telegram = new TelegramPlatform("test", config.get("telegram.token"));
        PlatformLinker linker = new PlatformLinker(vk, telegram);
        linker.start();
    }
}
