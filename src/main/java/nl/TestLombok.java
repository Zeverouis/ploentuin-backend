package nl;

import nl.ploentuin.ploentuin.model.User;

public class TestLombok {
    public static void main(String[] args) {
        User user = new User();
        user.setUsername("testuser");
        System.out.println(user.getUsername());
    }
}