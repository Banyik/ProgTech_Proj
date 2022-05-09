import BaseClasses.Users;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @org.junit.jupiter.api.Test
    void returnedUsernameShouldBeTestUser() {
        Users testUser = new Users(1, "TestUser", "user", "user@user.com");
        String expected = "TestUser";
        String actual = testUser.getUsername();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void returnedAuthShouldBeUser() {
        Users testUser = new Users(1, "TestUser", "user", "user@user.com");
        String expected = "user";
        String actual = testUser.getAuth();
        assertEquals(expected, actual);
    }


    @org.junit.jupiter.api.Test
    void toStringTest() {
        Users testUser = new Users(1, "TestUser", "user", "user@user.com");
        String expected = "User{id=1, username='TestUser\', auth=user}";
        String actual = testUser.toString();
        assertEquals(expected, actual);
    }
}