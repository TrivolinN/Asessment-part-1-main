import org.junit.Test;
import static org.junit.Assert.*;

public class UnitTes {

    // Username check

    @Test
    public void testCheckUserNameCorrectlyFormatted() {
        Login login = new Login();
        boolean actual = login.checkUserName("kyl_1");
        assertTrue(actual);
    }

    @Test
    public void testCheckUserNamePoorlyFormatted() {
        Login login = new Login();
        boolean actual = login.checkUserName("kyle!!!!!");
        assertFalse(actual);
    }

    // Password check

    @Test
    public void testCheckPasswordComplexitySuccess() {
        Login login = new Login();
        boolean actual = login.checkPasswordComplexity("Ch@ssw0rD");
        assertTrue(actual);
    }

    @Test
    public void testCheckPasswordComplexityFailure() {
        Login login = new Login();
        boolean actual = login.checkPasswordComplexity("pass");
        assertFalse(actual);
    }
}
