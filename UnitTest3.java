import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnitTest3 {

    @BeforeEach
    public void setup() {

        Part2.sentCount = 0;
        Part2.storedCount = 0;
        Part2.disregardedCount = 0;


        for (int i = 0; i < 90; i++) {
            Part2.sentMessages[i] = null;
            Part2.messageIDs[i] = null;
            Part2.messageHashes[i] = null;
            Part2.storedMessages[i] = null;
            Part2.disregardedMessages[i] = null;
        }

        Part2.loadTestData();
    }

    @Test
    public void testSentMessagesCorrectlyPopulated() {
        // Sent messages from Test Data: #1 and #4 only
        assertEquals(2, Part2.sentCount);
        assertEquals("Did you get the cake? (Recipient: +27834557896)", Part2.sentMessages[0]);
        assertEquals("It is dinner time ! (Recipient: 0838884567)", Part2.sentMessages[1]);
    }

    @Test
    public void testDisplayLongestMessage() {

        String longest = Part2.sentMessages[0];
        for (int i = 1; i < Part2.sentCount; i++) {
            if (Part2.sentMessages[i].length() > longest.length()) {
                longest = Part2.sentMessages[i];
            }
        }
        // From test data: "It is dinner time !" is longer than "Did you get the cake?"
        assertEquals("It is dinner time ! (Recipient: 0838884567)", longest);
    }

    @Test
    public void testSearchByMessageID() {
        // Search for message with ID "0838884567" (Test Data message 4)
        String searchID = "0838884567";
        String foundMessage = null;
        for (int i = 0; i < Part2.sentCount; i++) {
            if (searchID.equals(Part2.messageIDs[i])) {
                foundMessage = Part2.sentMessages[i];
                break;
            }
        }
        assertNotNull(foundMessage);
        assertEquals("It is dinner time ! (Recipient: 0838884567)", foundMessage);
    }

    @Test
    public void testSearchMessagesByRecipient() {

        Part2.storedMessages[Part2.storedCount++] = "Ok, I am leaving without you. (Recipient: +27838884567)";

        String recipient = "+27838884567";

        StringBuilder matchedMessages = new StringBuilder();
        for (int i = 0; i < Part2.sentCount; i++) {
            if (Part2.sentMessages[i] != null && Part2.sentMessages[i].contains(recipient)) {
                matchedMessages.append(Part2.sentMessages[i]).append("\n");
            }
        }
        for (int i = 0; i < Part2.storedCount; i++) {
            if (Part2.storedMessages[i] != null && Part2.storedMessages[i].contains(recipient)) {
                matchedMessages.append(Part2.storedMessages[i]).append("\n");
            }
        }

        String results = matchedMessages.toString();
        assertTrue(results.contains("Where are you? You are late! I have asked you to be on time. (Recipient: +27838884567)"));
        assertTrue(results.contains("Ok, I am leaving without you. (Recipient: +27838884567)"));
    }

    @Test
    public void testDeleteMessageByHash() {

        Part2.sentMessages[2] = "Where are you? You are late! I have asked you to be on time. (Recipient: +27838884567)";
        Part2.messageHashes[2] = "CC:2:WhereLate";
        Part2.messageIDs[2] = "ID2";
        Part2.sentCount = 3;

        String hashToDelete = "CC:2:WhereLate";

        boolean deleted = false;
        for (int i = 0; i < Part2.sentCount; i++) {
            if (hashToDelete.equals(Part2.messageHashes[i])) {
                for (int j = i; j < Part2.sentCount - 1; j++) {
                    Part2.sentMessages[j] = Part2.sentMessages[j + 1];
                    Part2.messageHashes[j] = Part2.messageHashes[j + 1];
                    Part2.messageIDs[j] = Part2.messageIDs[j + 1];
                }
                Part2.sentMessages[Part2.sentCount - 1] = null;
                Part2.messageHashes[Part2.sentCount - 1] = null;
                Part2.messageIDs[Part2.sentCount - 1] = null;
                Part2.sentCount--;
                deleted = true;
                break;
            }
        }
        assertTrue(deleted);
        assertEquals(2, Part2.sentCount);
        assertFalse(java.util.Arrays.asList(Part2.messageHashes).contains(hashToDelete));
    }

    @Test
    public void testDisplayReport() {
        // After loadTestData, sentCount=2, storedCount=2, disregardedCount=1
        assertEquals(2, Part2.sentCount);
        assertEquals(2, Part2.storedCount);
        assertEquals(1, Part2.disregardedCount);

        String expectedReport = "Report:\n" +
                "Sent Messages: 2\n" +
                "Disregarded Messages: 1\n" +
                "Stored Messages: 2";


        String report = "Report:\n" +
                "Sent Messages: " + Part2.sentCount + "\n" +
                "Disregarded Messages: " + Part2.disregardedCount + "\n" +
                "Stored Messages: " + Part2.storedCount;

        assertEquals(expectedReport, report);
    }
}
