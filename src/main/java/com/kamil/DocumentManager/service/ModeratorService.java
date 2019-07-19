package com.kamil.DocumentManager.service;

import org.springframework.stereotype.Service;

@Service
public class ModeratorService {

    public String moderatorRadioChoose(String moderatorChoose) {
        String option = "";
        switch(moderatorChoose) {
            case "showAllDocs":
                option = "showAllDocs";
                break;
            case "showUsers":
                option = "showUsers";
                break;
        }
        return "redirect:" + option;
    }
}
