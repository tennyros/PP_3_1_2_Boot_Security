package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.AppUserDetailsService;

@Component
public class UserValidator implements Validator {

    private final AppUserDetailsService appUserDetailsService;

    @Autowired
    public UserValidator(AppUserDetailsService appUserDetailsService) {
        this.appUserDetailsService = appUserDetailsService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        try {
            appUserDetailsService.loadUserByUsername(user.getUsername());
        } catch (UsernameNotFoundException ingored) {
            return;
        }
        errors.rejectValue("username", "", "User with such username is already exists!");
    }
}
