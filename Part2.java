
import javax.swing.*;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.*;



public class Part2 {

    public static void CHAT(String username) {

        JOptionPane.showMessageDialog(null, "Hello " + username + ", welcome to QuickChat!");
    }

    public static void main(String[] args) {

        boolean run = true;
//Source: Bro Code,2021
        //https://www.youtube.com/watch?v=BuW7y21FcYI&t=83s
        //In this section i used JOptionPANE for messages which i learnt from the video above
        while (run) {
            String option = JOptionPane.showInputDialog(
                    "Choose what you would like to do:\n" +
                            "(1): Send Messages\n" +
                            "(2): Show recent messages\n" +
                            "(3): Quit\n");

//Source: Bro code,2024
            // https://www.youtube.com/watch?v=6q2JKiynteM
            // For this section i decided to use enhanced switches which i learnt from the video above
            switch (option) {
                case "1" -> {
                    String MSGNO = JOptionPane.showInputDialog("How many messages would you like to send?");
                    try {
                        int messageCount = Integer.parseInt(MSGNO);
                        int totalSentMessages = 0;
// simple looping
                        for (int i = 0; i < messageCount; i++) {
                            Login loginInstance = new Login();
                            // Bro code,2024
                            // https://www.youtube.com/watch?v=Ntl3DxhyrQQ
//In this section i used string methods for length,toUpper,and trim which i learnt from the video above
                            String Pnum = JOptionPane.showInputDialog("Enter a recipient phone number (Must be ≤10 digits and start with country code +27):");
                            boolean isValidPhoneNumber = loginInstance.checkPhonenumber(Pnum);

                            if (!isValidPhoneNumber) {
                                JOptionPane.showMessageDialog(null, "Cell phone number is incorrectly formatted or missing +27 code.");
                                i--;// retry  message
                                continue;
                            }

                            String text = JOptionPane.showInputDialog("Enter your message (250 character limit)");

                            if (text.length() > 250) {
                                int excess = text.length() - 250;
                                JOptionPane.showMessageDialog(null, "Message exceeds 250 characters by " + excess + ", please reduce size.");
                                i--;
                                continue;
                            }


                            String ID = String.valueOf((int)(Math.random() * 1000000000));
                            if (ID.length() > 10) {
                                ID = ID.substring(0, 10);
                            }

                            //  Using hash
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
// Reused enhanced switches
                            switch (info) {
                                case "1" -> JOptionPane.showMessageDialog(null, "Message successfully sent.");
                                case "2" -> {
                                    String confirmDelete = JOptionPane.showInputDialog("Press 0 to delete message:");
                                    switch (confirmDelete) {
                                        case "0" -> JOptionPane.showMessageDialog(null, "Message successfully deleted.");
                                        default -> JOptionPane.showMessageDialog(null, "Message not deleted.");
                                    }
                                }
                                case "3" -> {
                                    JOptionPane.showMessageDialog(null, "Message successfully stored.");
                                    storeMessage(ID, hash, Pnum, text);
                                }
                            }

                            totalSentMessages++;
                        }


                        JOptionPane.showMessageDialog(null, "Total messages sent: " + totalSentMessages);

                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid number entered.");
                    }
                }

                case "2" -> JOptionPane.showMessageDialog(null, "Sorry, this option is not currently available.");

                case "3" -> {
                    JOptionPane.showMessageDialog(null, "Goodbye! Thank you for using QuickChat.");
                    run = false;
                }
            }
        }
    }

    public static void storeMessage(String id, String hash, String recipient, String message) {
        try {
            // OpenAI.ChatGpt,2024
            //https://chatgpt.com/c/682e5613-cc10-8002-9bea-f2bf5b8ef9fa
            //Used Json from open AI as a baseline
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
//Reference list
// Bro Code,2021. Java JOptionPane 🛑. [video online] Available at <https://www.youtube.com/watch?v=BuW7y21FcYI&t=83s> [Accessed 20 May 2025].
// Bro Code,2024. Java ENHANCED SWITCHES are easy! 💡. [video online] Available at <https://www.youtube.com/watch?v=6q2JKiynteM> [Accessed 20 May 2025].
// Bro Code,2024. Useful beginner string methods in Java! 🧵. [video online] Available at <https://www.youtube.com/watch?v=Ntl3DxhyrQQ> [Accessed 21 May 2025]
// OpenAI,2025.ChatGPT Available at <https://chatgpt.com/c/682e5613-cc10-8002-9bea-f2bf5b8ef9fa> [Accessed 21 March 2025].

        }
    }
}
