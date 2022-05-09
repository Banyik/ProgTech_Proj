package BaseClasses;

public class Users {
    int id;
    String username;
    String auth;
    String email;
    String address;
    public Users(int id, String username, String auth, String email) {
        this.id = id;
        this.username = username;
        this.auth = auth;
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public String getAuth() {
        return auth;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", auth=" + auth +
                '}';
    }
}
