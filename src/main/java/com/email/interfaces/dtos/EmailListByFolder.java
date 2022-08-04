package com.email.interfaces.dtos;

import com.email.interfaces.entities.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmailListByFolder {

    private List<Email> emails;

    private String folderName;

}
