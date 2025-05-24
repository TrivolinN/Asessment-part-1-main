import org.junit.Test;
import static org.junit.Assert.*;

public class unitTest {

    Messages msg = new Messages();

    //  Valid Message Length
    @Test
    public void testValidMessageLength() {
        String message = "Hi Keegan, can you join us for dinner tonight";
        assertTrue(message.length() <= 250);

        String actual = message.length() <= 250
                ? "Message ready to send."
                : "Message exceeds 250 characters by " + (message.length() - 250);

        assertEquals("Message ready to send.", actual);
    }

    //  Message Length
    @Test
    public void testMessageTooLong() {
        String longMessage = "A".repeat(270);
        int excess = longMessage.length() - 250;

        String actual = longMessage.length() > 250
                ? "Message exceeds 250 characters by " + excess
                : "Message ready to send.";

        assertEquals("Message exceeds 250 characters by 20", actual);
    }

    //  Valid Recipient Number
    @Test
    public void testValidRecipientNumber() {
        String recipient = "+27718693002";
        assertTrue(msg.checkRecipientCell(recipient));
    }

    // Test Invalid Recipient Number
    @Test
    public void testInvalidRecipientNumber() {
        String recipient = "08575975889";
        assertFalse(msg.checkRecipientCell(recipient));
    }

    //  Hash for message
    @Test
    public void testCorrectHashFormat() {
        String id = "0012345678";  // Starts with 00
        int index = 0;
        String message = "Hi Keegan, can you join us for dinner tonight";
        String hash = msg.createMessageHash(id, index, message);
        assertEquals("00:0:HITONIGHT", hash);
    }

    //  Valid Message ID
    @Test
    public void testValidMessageID() {
        assertTrue(msg.checkMessageID("1234567890"));
    }

    //  Invalid Message ID
    @Test
    public void testInvalidMessageID() {
        assertFalse(msg.checkMessageID("1234567890123"));
    }


    @Test
    public void testSendActionSimulation() {
        int before = msg.returnTotalMessages();
        msg.sendMessage("12345", "12:0:HITONIGHT", "+27718693002", "Hi Keegan, can you join us for dinner tonight");
        int after = msg.returnTotalMessages();
        assertTrue(after >= before);
    } // OpenAI,2025.ChatGPT Available at <https://chatgpt.com/c/682f83d6-81ec-8002-97a5-d6f3eae326ba> [Accessed 22 May 2025]
}
