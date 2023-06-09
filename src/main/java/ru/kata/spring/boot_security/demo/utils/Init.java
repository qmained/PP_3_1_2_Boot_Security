package ru.kata.spring.boot_security.demo.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Component
@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
public class Init {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    private void postConstruct() {

        Role role_user = new Role("ROLE_USER");
        userService.addRole(role_user);
        Role role_admin = new Role("ROLE_ADMIN");
        userService.addRole(role_admin);

        User user = new User("username", "password", "firstName", "lastName", 20, Collections.singleton(role_user));
        userService.add(user);
        User admin = new User("admin", "root", "administrator", "programmer", 19
                , new HashSet<>(Arrays.asList(role_user, role_admin)));
        userService.add(admin);

    }

}
