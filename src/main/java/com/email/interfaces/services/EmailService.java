package com.email.interfaces.services;

import com.email.interfaces.dtos.EmailListResponse;
import com.email.interfaces.entities.Email;
import com.email.interfaces.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    public void saveNewEmail(Email email) {

        emailRepository.save(email);
    }

    public EmailListResponse getAllEmails(String email) {

        EmailListResponse emailListResponse = EmailListResponse.builder()

                .receivedEmails(emailRepository.findAllByReceivers(email))

                .sentEmails(emailRepository.findAllBySender(email))

                .build();

        return emailListResponse;
    }

    public String saveNewEmail(Email email, HttpSession httpSession, Model model){

        email.setSender((String) httpSession.getAttribute("user"));

        saveNewEmail(email);

        EmailListResponse emailListResponse = getAllEmails((String) httpSession.getAttribute("user"));

        model.addAttribute("emails", emailListResponse);

        return "home";
    }

    public String saveAnswerEmail(Email email, HttpSession httpSession, Model model){

        email.setSender((String) httpSession.getAttribute("user"));

        saveNewEmail(email);

        EmailListResponse emailListResponse = getAllEmails((String) httpSession.getAttribute("user"));

        model.addAttribute("emails", emailListResponse);

        return "home";
    }

    public String emailsReceived( HttpSession httpSession, Model model) {

        EmailListResponse emailListResponse = getAllEmails((String) httpSession.getAttribute("user"));

        model.addAttribute("emails", emailListResponse);

        return "emails-received";
    }

    public String emailView(String emailId, HttpSession httpSession, Model model) {

        Email email = emailRepository.findById(Integer.valueOf(emailId));

        model.addAttribute("email", email);

        return "one-email-view";
    }

    public String answerEmail(String emailId, HttpSession httpSession, Model model) {

        Email email = emailRepository.findById(Integer.valueOf(emailId));

        model.addAttribute("email", email);

        return "answer-email-view";

    }

    public String emailsSent(HttpSession httpSession, Model model) {
        EmailListResponse emailListResponse = getAllEmails((String) httpSession.getAttribute("user"));

        model.addAttribute("emails", emailListResponse);

        return "emails-sent";
    }
}
