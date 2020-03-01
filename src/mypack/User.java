package mypack;
//登録されたユーザ情報
public class User {
    private String userID; 
    private String password;       
    private String name;   
    private String mailaddress;

    public User() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMailaddress() {
        return mailaddress;
    }

    public void setMailaddress(String mailaddress) {
        this.mailaddress = mailaddress;
    }

    @Override
    public String toString() {
        return "User [userID=" + userID + ", password=" + password + ", name="
                + name + ", mailaddress=" + mailaddress + "]";
    }
}
/*

*/
