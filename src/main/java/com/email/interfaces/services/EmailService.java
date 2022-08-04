package com.email.interfaces.services;

import com.email.interfaces.dtos.EmailListByFolder;
import com.email.interfaces.dtos.EmailListResponse;
import com.email.interfaces.dtos.NewFolderRequest;
import com.email.interfaces.dtos.UpdateFolder;
import com.email.interfaces.entities.Email;
import com.email.interfaces.entities.User;
import com.email.interfaces.repositories.EmailRepository;
import com.email.interfaces.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveNewEmail(Email email) {

        emailRepository.save(email);
    }

    public EmailListResponse getAllEmails(String email) {

        List<String> folders = userRepository.findByEmail(email).get().getFolders();

        if (folders ==null){
            folders = List.of();
        }

        EmailListResponse emailListResponse = EmailListResponse.builder()

                .receivedEmails(emailRepository.findAllByReceivers(email))

                .sentEmails(emailRepository.findAllBySender(email))

                .folders(folders)

                .build();

        return emailListResponse;
    }

    public String saveNewEmail(Email email, HttpSession httpSession, Model model){

        email.setSender((String) httpSession.getAttribute("user"));
        email.setRead(true);

        saveNewEmail(email);

        EmailListResponse emailListResponse = getAllEmails((String) httpSession.getAttribute("user"));

        model.asMap().remove("emails");

        model.addAttribute("emails", emailListResponse);

        return "home";
    }

    public String saveAnswerEmail(Email email, HttpSession httpSession, Model model){

        email.setSender((String) httpSession.getAttribute("user"));

        email.setId(null);

        email.setReceivers(List.of(email.getSender()));

        saveNewEmail(email);

        EmailListResponse emailListResponse = getAllEmails((String) httpSession.getAttribute("user"));

        model.addAttribute("emails", emailListResponse);

        model.asMap().remove("email");


        return "home";
    }

    public String emailsReceived( HttpSession httpSession, Model model) {

        EmailListResponse emailListResponse = getAllEmails((String) httpSession.getAttribute("user"));

        model.addAttribute("emails", emailListResponse);

        return "emails-received";
    }

    public String emailView(String emailId, HttpSession httpSession, Model model) {

        Email email = emailRepository.findById(Integer.valueOf(emailId));

        model.addAttribute("updateFolder", new UpdateFolder());

        model.addAttribute("email", email);

        return "one-email-view";
    }

    public String answerEmail(String emailId, HttpSession httpSession, Model model) {

        Email email = emailRepository.findById(Integer.valueOf(emailId));

        model.addAttribute("emailToAnswer", email);

        return "answer-email-view";

    }

    public String emailsSent(HttpSession httpSession, Model model) {
        EmailListResponse emailListResponse = getAllEmails((String) httpSession.getAttribute("user"));

        model.addAttribute("emails", emailListResponse);

        return "emails-sent";
    }

    public String saveNewFolder(NewFolderRequest newFolderRequest, HttpSession httpSession, Model model) {

        User user = userRepository.findByEmail((String) httpSession.getAttribute("user")).get();

        List<String> folders = user.getFolders();

        folders.add( newFolderRequest.getFolderName());

        user.setFolders(folders);

        userRepository.save(user);

        EmailListResponse emailListResponse = getAllEmails((String) httpSession.getAttribute("user"));

        model.addAttribute("emails", emailListResponse);

        return "home";
    }

    public String viewByFolder(String folderName, HttpSession httpSession, Model model) {

        EmailListByFolder emailListByFolder = new EmailListByFolder();

        List<Email> emailList = emailRepository.findAllBySenderAndFolder((String) httpSession.getAttribute("user"), folderName);

        emailList.addAll(emailRepository.findAllByReceiversAndFolder((String) httpSession.getAttribute("user"), folderName));

        emailListByFolder.setEmails(emailList);

        emailListByFolder.setFolderName(folderName);

        model.addAttribute("emailListByFolder", emailListByFolder);

        return "byfolder";
    }

    public String home(HttpSession httpSession, Model model) {

        EmailListResponse emailListResponse = getAllEmails((String) httpSession.getAttribute("user"));

        model.addAttribute("emails", emailListResponse);

        return "home";

    }

    public String updateFolder(UpdateFolder updateFolder, HttpSession httpSession, Model model) {

        Email email = emailRepository.findById(updateFolder.getEmailId());

        email.setFolder(updateFolder.getFolderName());

        emailRepository.save(email);

        model.addAttribute("updateFolder", new UpdateFolder());

        model.addAttribute("email", email);

        return "one-email-view";
    }

    @Transactional
    public String eraseEmail(Integer id, HttpSession httpSession, Model model) {

        emailRepository.deleteById(id);

        EmailListResponse emailListResponse = getAllEmails((String) httpSession.getAttribute("user"));

        model.addAttribute("emails", emailListResponse);

        return "home";

    }

    public String saveResendEmail(Email email, HttpSession httpSession, Model model) {

        email.setSender((String) httpSession.getAttribute("user"));

        email.setId(null);

        saveNewEmail(email);

        EmailListResponse emailListResponse = getAllEmails((String) httpSession.getAttribute("user"));

        model.addAttribute("emails", emailListResponse);

        model.asMap().remove("email");

        return "home";
    }

    public String resendEmail(Integer id, HttpSession httpSession, Model model) {

        Email email = emailRepository.findById(id);

        model.addAttribute("emailToResend", email);

        return "resend-email-view";
    }

    @Transactional
    public void deleteAll(List<String> ids, HttpSession httpSession, Model model) {

        ids.stream().forEach(id -> emailRepository.deleteById(Integer.parseInt(id)));

    }


    public String answerAllEmailLoad(Integer id, HttpSession httpSession, Model model) {

        Email email = emailRepository.findById(id);

        model.addAttribute("emailToAnswer", email);

        return "answer-all-email-view";
    }

    public String answerAllEmail(Email email, HttpSession httpSession, Model model) {

        Email nuevoEmail = email;

        email.setId(null);

        List<String> receivers = email.getReceivers();

        receivers.add(email.getSender());

        receivers.removeIf(x-> x.equals((String) httpSession.getAttribute("user")));

        nuevoEmail.setReceivers(receivers);

        nuevoEmail.setSender((String) httpSession.getAttribute("user"));

        saveNewEmail(nuevoEmail);

        EmailListResponse emailListResponse = getAllEmails((String) httpSession.getAttribute("user"));

        model.addAttribute("emails", emailListResponse);

        model.asMap().remove("email");

        return "home";
    }
}
