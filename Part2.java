import javax.swing.*;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.*;
//Part 3 - line 68
public class Part2 {
//ARRAY LIST FOR PART 3
   public static String[] sentMessages = new String[90];
   public static String[] disregardedMessages = new String[90];
 public    static String[] storedMessages = new String[90];
  public   static String[] messageHashes = new String[90];
 public    static String[] messageIDs = new String[90];
//Test data for junit
    public static void loadTestData() {
        sentMessages[0] = "Did you get the cake? (Recipient: +27834557896)";
        messageIDs[0] = "+27834557896";
        messageHashes[0] = "AA:0:DidCake";
        sentCount = 1;

        sentMessages[1] = "It is dinner time ! (Recipient: 0838884567)";
        messageIDs[1] = "0838884567";
        messageHashes[1] = "BB:1:DinnerTime";
        sentCount = 2;

        storedMessages[0] = "Where are you? You are late! I have asked you to be on time. (Recipient: +27838884567)";
        storedMessages[1] = "It is dinner time ! (Recipient: 0838884567)";
        storedCount = 2;

        disregardedMessages[0] = "This message is disregarded.";
        disregardedCount = 1;
    }


    public    static int sentCount = 0;
 public    static int disregardedCount = 0;
    public static int storedCount = 0;

    public static void CHAT(String username) {
        JOptionPane.showMessageDialog(null, "Hello " + username + ", welcome to QuickChat!");
    }

    public static void main(String[] args) {
        boolean run = true;

        while (run) {
            String option = JOptionPane.showInputDialog(
                    "Choose what you would like to do:\n" +
                            "(1): Send Messages\n" +
                            "(2): Show recent messages\n" +
                            "(3): Quit\n");

            if (option == null) { //
                run = false;
                continue;
            }

            switch (option) {
                case "1" -> sendMessages();
                case "2" -> manageMessagesMenu();
                case "3" -> {
                    JOptionPane.showMessageDialog(null, "Goodbye! Thank you for using QuickChat.");
                    run = false;
                }
                default -> JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
            }
        }
    }
//PART 3 OPTIONS
    public static void manageMessagesMenu() {
        int choice = 0;
//Joption part 3 menu
        while (choice != 8) {
            String input = JOptionPane.showInputDialog(
                    "Choose what you would like to do:\n" +
                            "1) Display All Sent Messages\n" +
                            "2) Display Longest Sent Message\n" +
                            "3) Search for Message by ID\n" +
                            "4) Search for Messages by Recipient\n" +
                            "5) Delete Message by Hash\n" +
                            "6) Display Full Report\n" +
                            "7) Display Stored Messages from File\n" +
                            "8) Go Back"
            );

            if (input == null || input.isEmpty()) break;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                continue;
            }
//Enhanced switches for part 3 menu
            switch (choice) {
                case 1 -> displaySentMessages();
                case 2 -> displayLongestMessage();
                case 3 -> searchByMessageID();
                case 4 -> searchByRecipient();
                case 5 -> deleteByMessageHash();
                case 6 -> displayReport();
                case 7 -> displayStoredMessagesFromFile();
                case 8 -> JOptionPane.showMessageDialog(null, "Returning to main menu...");
                default -> JOptionPane.showMessageDialog(null, "Invalid option. Try again.");
            }
        }
    }

    public static void sendMessages() {
        String MSGNO = JOptionPane.showInputDialog("How many messages would you like to send?");
        if (MSGNO == null) return; // user cancelled

        try {
            int messageCount = Integer.parseInt(MSGNO);
            int totalSentMessages = 0;

            for (int i = 0; i < messageCount; i++) {
                Login loginInstance = new Login();

                String Pnum = JOptionPane.showInputDialog("Enter a recipient phone number (Must be ≤10 digits and start with country code +27):");
                if (Pnum == null) break; // cancel

                boolean isValidPhoneNumber = loginInstance.checkPhonenumber(Pnum);

                if (!isValidPhoneNumber) {
                    JOptionPane.showMessageDialog(null, "Cell phone number is incorrectly formatted or missing +27 code.");
                    i--; // retry this message
                    continue;
                }

                String text = JOptionPane.showInputDialog("Enter your message (250 character limit)");
                if (text == null) break; // cancel

                if (text.length() > 250) {
                    int excess = text.length() - 250;
                    JOptionPane.showMessageDialog(null, "Message exceeds 250 characters by " + excess + ", please reduce size.");
                    i--; // retry this message
                    continue;
                }

                String ID = String.valueOf((int) (Math.random() * 1000000000));
                if (ID.length() > 10) {
                    ID = ID.substring(0, 10);
                }

                String[] words = text.trim().split(" ");
                String firstWord = words[0];
                String lastWord = words[words.length - 1];

                String hash = ID.substring(0, 2) + ":" + i + ":" + firstWord + lastWord;
                hash = hash.toUpperCase();

                JOptionPane.showMessageDialog(null, "Message sent.");

                JOptionPane.showMessageDialog(null,
                        "Message ID: " + ID +
                                "\nMessage Hash: " + hash +
                                "\nRecipient: " + Pnum +
                                "\nMessage: " + text);

                String info = JOptionPane.showInputDialog("What would you like to do with your message?\n" +
                        "(1) Send\n" +
                        "(2) Disregard\n" +
                        "(3) Store\n");

                if (info == null) break;

                switch (info) {
                    case "1" -> {
                        JOptionPane.showMessageDialog(null, "Message successfully sent.");
                        if (sentCount < sentMessages.length) {
                            sentMessages[sentCount] = text + " (Recipient: " + Pnum + ")";
                            messageHashes[sentCount] = hash;
                            messageIDs[sentCount] = ID;
                            sentCount++;
                        }
                        totalSentMessages++;
                    }
                    case "2" -> {
                        String confirmDelete = JOptionPane.showInputDialog("Press 0 to delete message:");
                        if ("0".equals(confirmDelete)) {
                            JOptionPane.showMessageDialog(null, "Message successfully deleted.");
                            if (disregardedCount < disregardedMessages.length) {
                                disregardedMessages[disregardedCount] = text + " (Recipient: " + Pnum + ")";
                                disregardedCount++;
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Message not deleted.");
                        }
                    }
                    case "3" -> {
                        storeMessage(ID, hash, Pnum, text);
                        if (storedCount < storedMessages.length) {
                            storedMessages[storedCount] = text + " (Recipient: " + Pnum + ")";
                            storedCount++;
                        }
                        // ALSO add stored message to sent arrays and counters
                        if (sentCount < sentMessages.length) {
                            sentMessages[sentCount] = text + " (Recipient: " + Pnum + ")";
                            messageHashes[sentCount] = hash;
                            messageIDs[sentCount] = ID;
                            sentCount++;
                            totalSentMessages++;
                        }
                        JOptionPane.showMessageDialog(null, "Message successfully stored and counted as sent.");
                    }
                    default -> JOptionPane.showMessageDialog(null, "Invalid option. Message not saved.");
                }
            }

            JOptionPane.showMessageDialog(null, "Total messages processed: " + totalSentMessages);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number entered.");
        }
    }

    public static void displaySentMessages() {
        if (sentCount == 0) {
            JOptionPane.showMessageDialog(null, "No messages have been sent yet.");
            return;
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < sentCount; i++) {
            output.append("Message ").append(i + 1).append(": ")
                    .append(sentMessages[i]).append("\n");
        }

        JOptionPane.showMessageDialog(null, output.toString());
    }

    public static void displayLongestMessage() {
        if (sentCount == 0) {
            JOptionPane.showMessageDialog(null, "No messages have been sent yet.");
            return;
        }
//Show longest message or just message if there is 1
        String longestMessage = sentMessages[0];

        for (int i = 1; i < sentCount; i++) {
            if (sentMessages[i].length() > longestMessage.length()) {
                longestMessage = sentMessages[i];
            }
        }

        JOptionPane.showMessageDialog(null, "Longest Sent Message:\n" + longestMessage);
    }

    public static void searchByMessageID() {
        if (sentCount == 0) {
            JOptionPane.showMessageDialog(null, "No messages to search.");
            return;
        }

        String idToSearch = JOptionPane.showInputDialog("Enter the Message ID to search:");
        if (idToSearch == null || idToSearch.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No ID entered. Returning to menu.");
            return;
        }

        boolean found = false;

        for (int i = 0; i < sentCount; i++) {
            if (messageIDs[i].equals(idToSearch)) {
                String result = "Message Found:\n" +
                        "ID: " + messageIDs[i] + "\n" +
                        "Hash: " + messageHashes[i] + "\n" +
                        "Text: " + sentMessages[i];
                JOptionPane.showMessageDialog(null, result);
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "No message found with that ID.");
        }
    }

    public static void searchByRecipient() {
        if (sentCount == 0) {
            JOptionPane.showMessageDialog(null, "No messages to search.");
            return;
        }

        String recipient = JOptionPane.showInputDialog("Enter a recipient phone number to search:");
        if (recipient == null || recipient.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No phone number entered. Returning to menu.");
            return;
        }

        StringBuilder output = new StringBuilder("Messages sent to " + recipient + ":\n\n");
        boolean found = false;

        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].contains(recipient)) {
                output.append("Message ").append(i + 1).append(": ").append(sentMessages[i]).append("\n");
                found = true;
            }
        }

        if (found) {
            JOptionPane.showMessageDialog(null, output.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No messages found for this recipient.");
        }
    }

    public static void deleteByMessageHash() {
        if (sentCount == 0) {
            JOptionPane.showMessageDialog(null, "No messages to delete.");
            return;
        }
//hash deletion
        String hashToDelete = JOptionPane.showInputDialog("Enter the hash of the message you want to delete:");
        if (hashToDelete == null || hashToDelete.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hash entered. Returning to menu.");
            return;
        }

        boolean found = false;

        for (int i = 0; i < sentCount; i++) {
            if (messageHashes[i].equals(hashToDelete)) {
                for (int j = i; j < sentCount - 1; j++) {
                    sentMessages[j] = sentMessages[j + 1];
                    messageHashes[j] = messageHashes[j + 1];
                    messageIDs[j] = messageIDs[j + 1];
                }
                sentMessages[sentCount - 1] = null;
                messageHashes[sentCount - 1] = null;
                messageIDs[sentCount - 1] = null;
                sentCount--;
                found = true;
                break;
            }
        }

        if (found) {
            JOptionPane.showMessageDialog(null, "Message successfully deleted.");
        } else {
            JOptionPane.showMessageDialog(null, "No message found with that hash.");
        }
    }

    public static void displayReport() {
        String report = "Report:\n" +
                "Sent Messages: " + sentCount + "\n" +
                "Disregarded Messages: " + disregardedCount + "\n" +
                "Stored Messages: " + storedCount;

        JOptionPane.showMessageDialog(null, report);
    }

    public static void displayStoredMessagesFromFile() {
        File file = new File("messages.json");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "No stored messages found.");
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            char[] chars = new char[(int) file.length()];
            reader.read(chars);

            String content = new String(chars);
            JSONObject rootObj = new JSONObject(content);
            JSONArray messagesArray = rootObj.optJSONArray("messages");

            if (messagesArray == null || messagesArray.length() == 0) {
                JOptionPane.showMessageDialog(null, "No stored messages available in the file.");
                return;
            }

            StringBuilder output = new StringBuilder();
            for (int i = 0; i < messagesArray.length(); i++) {
                JSONObject msg = messagesArray.getJSONObject(i);
                output.append("ID: ").append(msg.getString("id")).append(", ")
                        .append("Recipient: ").append(msg.getString("recipient")).append(", ")
                        .append("Message: ").append(msg.getString("message")).append("\n");
            }

            JOptionPane.showMessageDialog(null, output.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reading stored messages from file.");
            e.printStackTrace();
        }
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
                int read = reader.read(chars);
                reader.close();

                String existingData = new String(chars, 0, read);
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

            JOptionPane.showMessageDialog(null, "Message stored successfully.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error storing message.");
            e.printStackTrace();
        }
    }
}
// References:
// Bro Code, 2021. Java JOptionPane 🛑. [video online] Available at: <https://www.youtube.com/watch?v=BuW7y21FcYI&t=83s> [Accessed 20 May 2025]
// Bro Code, 2024. Java ENHANCED SWITCHES are easy! 💡. [video online] Available at: <https://www.youtube.com/watch?v=6q2JKiynteM> [Accessed 20 May 2025]
// Bro Code, 2024. Useful beginner string methods in Java! 🧵. [video online] Available at: <https://www.youtube.com/watch?v=Ntl3DxhyrQQ> [Accessed 21 May 2025]
// OpenAI, 2025. How to read and display JSON files in Java using JSONObject. ChatGPT. Available at: <https://chatgpt.com/c/685762ff-b538-8002-a534-022ac7d1affc> [Accessed 22 May 2025]
// Part 3      // Read JSON with JOption. ChatGPT. Available at <https://chatgpt.com/c/685762ff-b538-8002-a534-022ac7d1affc> [ Accessed 22 June 2025]