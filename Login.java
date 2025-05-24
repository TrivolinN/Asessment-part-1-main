public class Login {
    String firstName;
    String Surname;
    String Username;
    String Password;
    String phoneNumber;

    boolean checkUserName(String Username) {
        return Username.contains("_") && Username.length() <= 5;
    }

    boolean checkPasswordComplexity(String Password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Password.matches(passwordPattern);
    }

    boolean checkPhonenumber(String phoneNumber) {
        String PhoneNumberPat = "^\\+27[0-9]{9}$";
        return phoneNumber.matches(PhoneNumberPat);
    }

    String registerUser() {
        if (checkUserName(Username) && checkPasswordComplexity(Password)) {
            return "Conditions met. You have successfully registered.";
        } else if (!checkUserName(Username) && !checkPasswordComplexity(Password)) {
            return "Username and password incorrectly formatted.";
        } else if (!checkUserName(Username)) {
            return "Username incorrectly formatted.";
        } else {
            return "Password incorrectly formatted.";
        }
    }

    public boolean loginUser(String A, String B) {
        return Username.equals(A) && Password.equals(B);
    }

    String returnLoginStatus(boolean loggedIn) {
        if (loggedIn) {
            return "Welcome " + firstName + " " + Surname + ". It is great to see you!";
        } else {
            return "Username or password incorrect. Please try again.";
        }
    }
}






