package com.sda.OnlineShop.validators;

import com.sda.OnlineShop.dto.RegistrationDto;
import com.sda.OnlineShop.entities.User;
import com.sda.OnlineShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;

@Service
public class RegistrationDtoValidator {
    @Autowired
    private UserRepository userRepository;

    public void validate(RegistrationDto registrationDto, BindingResult bindingResult) {
        Optional<User> optionalUser = userRepository.findByEmail(registrationDto.getEmail());
        if (optionalUser.isPresent()) {
            FieldError fieldError = new FieldError("registrationDto", "email", " The email is already in use!");
            bindingResult.addError(fieldError);

        }
        if (registrationDto.getEmail().length() == 0) {
            FieldError fieldError = new FieldError("registrationDto", "email", " The email is empty!");
            bindingResult.addError(fieldError);
        }

            if (registrationDto.getPassword().length() == 0) {
                FieldError fieldError = new FieldError("registrationDto", "password", " The password is empty!");
                bindingResult.addError(fieldError);

            }
            if (registrationDto.getPassword().equals(registrationDto.getConfirmPassword())!=true) {
                FieldError fieldError = new FieldError("registrationDto", "confirmPassword", " The Confirm Password is different!");
                bindingResult.addError(fieldError);
            }


    }
}
