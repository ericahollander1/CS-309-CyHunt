package grp_1_yn_4.Model;

/**
 * Login Object is an object used to help the login endpoints
 */
public class LoginObject {
    private String emailId;
    private String password;

    /**
     * LoginObject Constructor
     * @param emailId
     * @param password
     */
    public LoginObject(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    /**
     * default constructor
     */
    public LoginObject(){}

    /**
     * Get the email the user is logging in with
     * @return
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * Get the password the user is logging in with
     * @return
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
   }

}