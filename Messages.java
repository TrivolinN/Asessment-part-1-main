import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Messages {

    private int totalMessagesSent = 0;

    public boolean checkMessageID(String ID) {
        return ID.length() <= 10;
    }

    // ChatGPT regex from part one
    public boolean checkRecipientCell(String phoneNumber) {
        return phoneNumber.matches("^\\+27[0-9]{9}$");
    }

    public String createMessageHash(String ID, int index, String message) {
        message = message.trim();
        String first = "";
        String last = "";

        int firstSpace = message.indexOf(' ');
        int lastSpace = message.lastIndexOf(' ');

        if (firstSpace == -1) {
            first = last = message;
        } else {
            first = message.substring(0, firstSpace);
            last = message.substring(lastSpace + 1);


        }

        String hash = ID.substring(0, Math.min(2, ID.length())) + ":" + index + ":" + first + last;
        return hash.toUpperCase();
    }

    public void sendMessage(String ID, String hash, String recipient, String message) {
        String choice = JOptionPane.showInputDialog("What would you like to do with your message?\n" +
                "(1) Send\n" +
                "(2) Disregard\n" +
                "(3) Store");

        switch (choice) {

            case "1" -> {



                JOptionPane.showMessageDialog(null, "Message successfully sent.");
                totalMessagesSent++;
                          }
            case "2" -> {
                String confirm = JOptionPane.showInputDialog("Press 0 to delete message:");
                if ("0".equals(confirm)) {
                    JOptionPane.showMessageDialog(null, "Message successfully deleted.");
                } else {
                    JOptionPane.showMessageDialog(null, "Message not deleted.");
                             }
            }
            case "3" -> {
                storeMessage(ID, hash, recipient, message);
                JOptionPane.showMessageDialog(null, "Message successfully stored.");
                totalMessagesSent++;
                    }
            default -> JOptionPane.showMessageDialog(null, "Invalid option. Message discarded.");
                        }
    }

    public void printMessages() {
        File file = new File("messages.json");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "No stored messages found.");
            return;
                  }

        try {
            FileReader reader = new FileReader(file);
            char[] buffer = new char[(int) file.length()];
            reader.read(buffer);
            reader.close();
//OpenAI.ChatGPT,2025
  //https://chatgpt.com/c/682f83d6-81ec-8002-97a5-d6f3eae326ba
//  Used ChatGPT for json message generation
            String content = new String(buffer);
            JSONObject data = new JSONObject(content);
            JSONArray messages = data.optJSONArray("messages");

            if (messages == null || messages.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No stored messages found.");
                return;
                    }

            StringBuilder output = new StringBuilder("Stored Messages:\n\n");
            for (int i = 0; i < messages.length(); i++) {
                JSONObject msg = messages.getJSONObject(i);
                output.append("Message ID: ").append(msg.getString("id")).append("\n");
                output.append("Hash: ").append(msg.getString("hash")).append("\n");
                output.append("To: ").append(msg.getString("recipient")).append("\n");
                output.append("Message: ").append(msg.getString("message")).append("\n\n");
                   }

            JTextArea area = new JTextArea(output.toString());
            area.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(area);
            scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));
            JOptionPane.showMessageDialog(null, scrollPane, "Stored Messages", JOptionPane.INFORMATION_MESSAGE);

                    } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reading stored messages.");
            e.printStackTrace();
         }
     }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    public static void storeMessage(String id, String hash, String recipient, String message) {
        try {
            JSONObject messageObj = new JSONObject();
            messageObj.put("id", id);
            messageObj.put("hash", hash);
            messageObj.put("recipient", recipient);
            messageObj.put("message", message);


            File file = new File("messages.json");
            JSONObject rootObj = new JSONObject();
            JSONArray messageList = new JSONArray();

            if (file.exists()) {
                FileReader reader = new FileReader(file);
                char[] chars = new char[(int) file.length()];
                reader.read(chars);
                reader.close();

                String existingData = new String(chars);
                if (!existingData.isBlank()) {
                    rootObj = new JSONObject(existingData);
                    messageList = rootObj.optJSONArray("messages");
                    if (messageList == null) {
                        messageList = new JSONArray();
                         }
                          }
            }

            messageList.put(messageObj);
            rootObj.put("messages", messageList);

            FileWriter writer = new FileWriter(file);
            writer.write(rootObj.toString(4));
            writer.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error storing message.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Messages msg = new Messages();
        String ID = "1234567890";

        if (msg.checkMessageID(ID)) {
            System.out.println("Valid ID");
        } else {
            System.out.println("Invalid ID: Too long");
        }

        String number = "+27831234567";
        if (msg.checkRecipientCell(number)) {
            System.out.println("Valid phone number");
        } else {
            System.out.println("Invalid phone number");
        }

        String text = "Hello this is a message";
        String hash = msg.createMessageHash(ID, 1, text);
        System.out.println("Generated Hash: " + hash);
    } //
}
