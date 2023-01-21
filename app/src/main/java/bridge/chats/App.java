package bridge.chats;

import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    private static final String VK_TOKEN = "your_vk_token";
    private static final String TELEGRAM_TOKEN = "your_telegram_token";
    private static final String TELEGRAM_CHANNEL_ID = "your_telegram_channel_id";
    private static final int VK_GROUP_ID = 123456;

    public static void main(String[] args) throws Exception {
        while (true) {
            // Get the latest messages from the VK group
            JSONObject vkResponse = new JSONObject(sendGet("https://api.vk.com/method/messages.get", "out=0&count=1&group_id=" + VK_GROUP_ID + "&access_token=" + VK_TOKEN));
            JSONArray vkMessages = vkResponse.getJSONObject("response").getJSONArray("items");

            // Forward each message to the Telegram channel
            for (int i = 0; i < vkMessages.length(); i++) {
                JSONObject vkMessage = vkMessages.getJSONObject(i);
                String vkText = vkMessage.getString("body");
                String senderName = vkMessage.getJSONObject("user_id").getString("first_name") + " " + vkMessage.getJSONObject("user_id").getString("last_name");
                sendPost("https://api.telegram.org/bot" + TELEGRAM_TOKEN + "/sendMessage", "chat_id=" + TELEGRAM_CHANNEL_ID + "&text=" + senderName + ": " + vkText);
            }

            // Wait for a bit before checking for new messages again
            Thread.sleep(5000);
        }
    }

    private static String sendGet(String url, String params) throws Exception {
        URL obj = new URL(url + "?" + params);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private static String sendPost(String url, String params) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(params.getBytes());
        os.flush();
        os.close();
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}

