package com.kamil.DocumentManager.service;

        import com.kamil.DocumentManager.models.AdminMessage;
        import com.kamil.DocumentManager.models.User;
        import com.kamil.DocumentManager.repository.AdminMessageRepository;
        import com.kamil.DocumentManager.repository.UserRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.security.Principal;
        import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminMessageRepository adminMessageRepository;


    public String adminRadioChoose(String adminChoose) {
        String declaredOption = "";
        switch (adminChoose) {
            case "showAllDocuments":
                declaredOption = "showAllDocuments";
                break;
            case "showAllUsers":
                declaredOption = "showAllUsers";
                break;
            default:
                declaredOption = "default value";
                break;
        }
        return "redirect:" + declaredOption;
    }

    public String sendMessage(String message, Principal principal) {
        AdminMessage adminMessage = new AdminMessage();
        adminMessage.setMessage(message);
        String loggedName = principal.getName();
        List<User> userList = (List<User>) userRepository.findAll();
        for (User u : userList) {
            if (u.getName().equals(loggedName)) {
                adminMessage.setUser(u);
            }
        }
        adminMessageRepository.save(adminMessage);
        return adminMessage.getMessage();
    }
}
