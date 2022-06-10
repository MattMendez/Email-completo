package com.email.interfaces.controllers;

import com.email.interfaces.entities.Email;
import com.email.interfaces.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequestMapping("/emails")
@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/new-email")
    public String newEmail(HttpSession httpSession, Model model){
        model.addAttribute("email", new Email());

        return "new-email";
    }

    @PostMapping("/new-email")
    public String saveNewEmail(@ModelAttribute Email email, HttpSession httpSession, Model model){

        return emailService.saveNewEmail(email, httpSession, model);
    }

    @GetMapping("/received")
    public String emailsReceived(HttpSession httpSession, Model model){

        return emailService.emailsReceived(httpSession,model);
    }

    @GetMapping("/sent")
    public String emailsSent(HttpSession httpSession, Model model){

        return emailService.emailsSent(httpSession,model);
    }

    @GetMapping("/email-view/{id}")
    public String emailView(@PathVariable("id") String id, HttpSession httpSession, Model model){

        return emailService.emailView(id, httpSession, model);
    }

    @GetMapping("/answer-email/{id}")
    public String answerEmail(@PathVariable("id") String id, HttpSession httpSession, Model model){

        return emailService.answerEmail(id, httpSession, model);
    }

    @PostMapping("/answer-email")
    public String answerEmail2(@ModelAttribute Email email, HttpSession httpSession, Model model){

        return emailService.saveAnswerEmail(email, httpSession, model);
    }
}