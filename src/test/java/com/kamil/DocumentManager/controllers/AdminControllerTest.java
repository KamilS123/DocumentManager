package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.repository.UserRepository;
import com.kamil.DocumentManager.service.AdminService;
import com.kamil.DocumentManager.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Mock
    private AdminService adminService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void adminMainController() throws Exception {
        String choose = "showAllDocuments";
        when(adminService.adminRadioChoose(choose)).thenReturn(choose);
        this.mockMvc.perform(get("/adminMainController"))
                .andExpect(status().isOk())
                .andExpect(view().name("showAllDocuments"));
    }

    @Test
    public void showAllDocuments() {
    }

    @Test
    public void showAllUsers() {
    }

    @Test
    public void displayMessagesFromUsers() {
    }

    @Test
    public void deleteMessageFromUsers() {
    }

    @Test
    public void displayAdminMainContent() {
    }
}