package com.email.interfaces.repositories;

import com.email.interfaces.entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email,String> {

    Email findById(Integer emailId);

    List<Email> findAllBySender(String sender);

    List<Email> findAllByReceivers(String receivers);

    List<Email> findAllBySenderAndFolder(String sender, String folder);

    List<Email> findAllByReceiversAndFolder(String receivers, String folder);

    void deleteById(Integer id);

}