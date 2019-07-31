package com.kamil.DocumentManager.service;

import com.kamil.DocumentManager.models.AdminMessage;
import com.kamil.DocumentManager.models.User;
import com.kamil.DocumentManager.repository.AdminMessageRepository;
import com.kamil.DocumentManager.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest {
    @Mock
    private Principal principal;
    @Spy
    private AdminService adminService;
    @InjectMocks
    private AdminService adminService2;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AdminMessageRepository adminMessageRepository;

    @Test
    public void should_return_redirect_to_showAllDocuments_passed_by_user() {
        String passedData = "showAllDocuments";
        Assert.assertEquals("redirect:" + passedData, adminService.adminRadioChoose(passedData));
    }

    @Test
    public void should_return_redirect_to_showAllUsers_passed_by_user() {
        String passedData = "showAllUsers";
        Assert.assertEquals("redirect:" + passedData, adminService.adminRadioChoose(passedData));
    }

    @Test
    public void should_return_redirect_to__switch_default_value() {
        //given
        String passedData = "anyValue";
        //when
        //then
        Assert.assertEquals("redirect:default value", adminService.adminRadioChoose(passedData));
    }

    @Test
    public void should_return_saved_AdminMessage() {
        //given
        AdminMessage adminMessage = new AdminMessage("This is my message");
        List<User> userList = mockListOfUsers();
        //when
        when(userRepository.findAll()).thenReturn(userList);
        when(principal.getName()).thenReturn("Marek");
        //then
        Assert.assertEquals(adminService2.sendMessage(adminMessage.getMessage(), principal), "This is my message");
    }

    private List<User> mockListOfUsers() {
        User user = new User("Marek", "Surname", "password", "user", "active");
        user.setId(11L);
        User user1 = new User("Mike", "Owen", "pass", "admin", "active");
        user.setId(21L);
        User user2 = new User("Frank", "Owen", "pass", "moderator", "active");
        user.setId(31L);
        User user3 = new User("Olo", "Bony", "password", "user", "active");
        user.setId(411L);
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        return users;
    }
}