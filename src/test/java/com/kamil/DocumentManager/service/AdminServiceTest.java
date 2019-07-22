package com.kamil.DocumentManager.service;

import com.kamil.DocumentManager.models.AdminMessage;
import com.kamil.DocumentManager.repository.AdminMessageRepository;
import com.kamil.DocumentManager.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.security.Principal;

import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest {

    private Principal principal;

    @Mock
    private AdminService adminService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AdminMessageRepository adminMessageRepository;

    @Test
    public void should_return_redirect_to_showAllDocuments_passed_by_user() {
        //given
        String passedData = "showAllDocuments";
        //when
        when(adminService.adminRadioChoose(passedData)).thenReturn("redirect:"+passedData);
        //then
        Assert.assertEquals("redirect:"+ passedData,adminService.adminRadioChoose(passedData));
    }
    @Test
    public void should_return_redirect_to_showAllUsers_passed_by_user() {
        //given
        String passedData = "showAllUsers";
        //when
        when(adminService.adminRadioChoose(passedData)).thenReturn("redirect:"+passedData);
        //then
        Assert.assertEquals("redirect:"+ passedData,adminService.adminRadioChoose(passedData));
    }
    @Test
    public void should_return_redirect_to__switch_default_value() {
        //given
        String passedData = "anyValue";
        //when
        when(adminService.adminRadioChoose(passedData)).thenReturn("redirect:default value");
        //then
        Assert.assertEquals("redirect:default value",adminService.adminRadioChoose(passedData));
    }

    @Test
    public void should_return_saved_AdminMessage(){
        //given
        AdminMessage adminMessage = new AdminMessage("This is my message");
        //when
        when(adminService.sendMessage("This is my message",getPrincipal())).thenReturn(adminMessage.getMessage());
        //then
        Assert.assertEquals(adminService.sendMessage(adminMessage.getMessage(),getPrincipal()),"This is my message");
    }
    private Principal getPrincipal() {
        return principal;
    }
}