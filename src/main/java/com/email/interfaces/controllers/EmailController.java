package com.email.interfaces.controllers;

import com.email.interfaces.dtos.NewFolderRequest;
import com.email.interfaces.dtos.UpdateFolder;
import com.email.interfaces.entities.Email;
import com.email.interfaces.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

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

    @GetMapping("/newfolder")
    public String newFolder (HttpSession httpSession, Model model){
        model.addAttribute("newFolder", new  NewFolderRequest() );

        return "new-folder";
    }

    @PostMapping("/newfolder")
    public String newFolder2 (@ModelAttribute NewFolderRequest newFolderRequest ,HttpSession httpSession, Model model){


        return emailService.saveNewFolder(newFolderRequest, httpSession, model) ;
    }

    @GetMapping("/byfolder/{folderName}")
    public String viewByFolder(@PathVariable("folderName") String folderName, HttpSession httpSession, Model model){

        return emailService.viewByFolder(folderName, httpSession, model);
    }

    @GetMapping("/home")
    public String home(HttpSession httpSession, Model model){

        return emailService.home(httpSession,model);
    }

    @PostMapping("/updatefolder")
    public String updateFolder (@ModelAttribute UpdateFolder updateFolder , HttpSession httpSession, Model model){


        return emailService.updateFolder(updateFolder, httpSession, model) ;
    }

    @GetMapping("/erase/{id}")
    public String eraseEmail(@PathVariable("id") Integer id, HttpSession httpSession, Model model){

        return emailService.eraseEmail(id, httpSession, model);
    }

    @GetMapping("/resend-email/{id}")
    public String resendEmail(@PathVariable("id") Integer id, HttpSession httpSession, Model model){

        return emailService.resendEmail(id, httpSession, model);
    }

    @PostMapping("/resend-email")
    public String resendEmail2(@ModelAttribute Email email, HttpSession httpSession, Model model){

        return emailService.saveResendEmail(email, httpSession, model);
    }

    @DeleteMapping("/deleteall")
    public void deleteAll(@RequestParam List<String> ids, HttpSession httpSession, Model model){
        emailService.deleteAll(ids, httpSession, model);
    }


    @GetMapping("/answer-all/{id}")
    public String answerAllEmail(@PathVariable("id") Integer id, HttpSession httpSession, Model model){

        return emailService.answerAllEmailLoad(id, httpSession, model);
    }

    @PostMapping("/answer-all")
    public String answerAllEmail2(Email email, HttpSession httpSession, Model model){

        return emailService.answerAllEmail(email, httpSession, model);
    }


}