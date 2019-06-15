package project.com.eventmaster.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private String token;
    private String email;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    public LoggedInUserView(String token, String email) {
        this.token = token;
        this.email = email;
    }

    String getDisplayName() {
        return displayName;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }
}
