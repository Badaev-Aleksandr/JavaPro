package de.ait.javalessons.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "userpass";
        String encodetePassword = encoder.encode(password);
        System.out.println(password + "-->" + encodetePassword);
    }

    // userpass --> $2a$10$ejpsgRtPPQDqtU8LWPUyhO9G02.6UBgkhJyHigidPmRAf/4Wrm...
    // adminpass --> $2a$10$Y3Z/NsTgPIgydBEBwNn9COeidrRiWf6OOC1cyC54k3QIAf8hiacny
}
