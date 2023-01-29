package bridge.chats;

import bridge.chats.Platform.TelegramPlatform;
import bridge.chats.Platform.VkPlatform;

class BridgeOfChats {
    private final static String VK_TOKEN = System.getenv("VK_TOKEN");
    private final static String TELEGRAM_TOKEN = System.getenv("TELEGRAM_TOKEN");

    public static void run() {
        VkPlatform vk = new VkPlatform(VK_TOKEN);
        TelegramPlatform telegram = new TelegramPlatform("test", TELEGRAM_TOKEN);
        PlatformLinker linker = new PlatformLinker(vk, telegram);
        linker.start();
    }
}
