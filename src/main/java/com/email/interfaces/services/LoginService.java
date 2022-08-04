package com.email.interfaces.services;

import com.email.interfaces.dtos.EmailListResponse;
import com.email.interfaces.dtos.LoginForm;
import com.email.interfaces.dtos.SignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    public String verifyLoginForm(LoginForm loginForm, HttpSession httpSession, Model model) {

        if( loginForm.getEmail() != null
                && !loginForm.getEmail().isEmpty()
                &&  loginForm.getPassword() != null
                && !loginForm.getPassword().isEmpty())
        {

            if(!loginForm.getEmail().contains("@")){
                return "invalid-data";
            }


            if (userService.verifyExist(loginForm.getEmail())){
                httpSession.setAttribute("user", loginForm.getEmail());

                EmailListResponse emailListResponse = emailService.getAllEmails(loginForm.getEmail());

                model.addAttribute("emails",emailListResponse);

                return "home";
            } else {
                httpSession.setAttribute("user", loginForm.getEmail());

                return "login-fail";
            }

        } else {
            return "bad-credentials";
        }
    }


    public String verifySignUpForm(SignUpForm signUpForm, HttpSession httpSession, Model model) {

        if      (signUpForm.getEmail() != null
                && !signUpForm.getEmail().isEmpty()
                && signUpForm.getPassword() != null
                && !signUpForm.getPassword().isEmpty()
                && signUpForm.getEmail().contains("@")) {

            if(!userService.verifyExist(signUpForm.getEmail())){

                userService.registerNewUser(signUpForm);

                httpSession.setAttribute("user", signUpForm.getEmail());

                EmailListResponse emailListResponse = emailService.getAllEmails(signUpForm.getEmail());

                model.addAttribute("emails", emailListResponse);

                return "home";
            }else{
                return "user-already-exist";
            }

        } else {

            return "invalid-data";
        }

    }

}
