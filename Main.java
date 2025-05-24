import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        Login Register = new Login();
        String Username;
        String Password;
        String phoneNumber;

        // First Name and Surname
        String firstName = JOptionPane.showInputDialog("Please enter your first name:");
        Register.firstName = firstName;

        String Surname = JOptionPane.showInputDialog("Please enter your surname:");
        Register.Surname = Surname;

        // Username
        while (true) {
            Username = JOptionPane.showInputDialog("To begin your registration, please enter a username:");
            Register.Username = Username;
            if (Register.checkUserName(Username)) {
                JOptionPane.showMessageDialog(null, "Username successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Username incorrectly formatted. It must include an underscore and be no longer than 5 characters.");
            }
        }

        // Password
        while (true) {
            Password = JOptionPane.showInputDialog("Create and enter a password:");
            Register.Password = Password;
            if (Register.checkPasswordComplexity(Password)) {
                JOptionPane.showMessageDialog(null, "Password successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Password is incorrectly formatted. It must contain at least 8 characters, a capital letter, a number, and a special character.");
            }
        }

        // Phone Number
        while (true) {
            phoneNumber = JOptionPane.showInputDialog("Please enter your cellphone number (e.g., +27831234567):");
            if (Register.checkPhonenumber(phoneNumber)) {
                Register.phoneNumber = phoneNumber;
                JOptionPane.showMessageDialog(null, "Phone number successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Phone number incorrectly formatted. It must include the international code (+27).");
            }
        }

        // Registration status
        JOptionPane.showMessageDialog(null, Register.registerUser());

        // Login
        JOptionPane.showMessageDialog(null, "You are now ready to log in.");
        String usernameLog = JOptionPane.showInputDialog("Enter your username:");
        String passwordLog = JOptionPane.showInputDialog("Enter your password:");

        boolean loggedIn = Register.loginUser(usernameLog, passwordLog);
        if (loggedIn) {
            JOptionPane.showMessageDialog(null, "Login successful.");
            Part2.CHAT(Register.Username);
        } else {
            JOptionPane.showMessageDialog(null, "Login failed.");
        }

        JOptionPane.showMessageDialog(null, Register.returnLoginStatus(loggedIn));
    }
}






