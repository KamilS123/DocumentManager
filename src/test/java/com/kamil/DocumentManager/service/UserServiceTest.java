package com.kamil.DocumentManager.service;

import com.kamil.DocumentManager.models.User;
import com.kamil.DocumentManager.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Mock
    Principal principal;

    @Test
    public void should_return_userMainContent_when_user_status_equals_user() {
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("baseurl").with((RequestPostProcessor) user);
        //given
        List<User> userList = mockListOfUsers();
        //when
        when(principal.getName()).thenReturn("Marek");
        when(userRepository.findAll()).thenReturn(userList);
        //then
        Assert.assertEquals("userMainContent",userService.checkUserStatus(principal));
    }
    @Test
    public void should_return_adminMainContent_when_user_status_equals_admin() {
        //given
        List<User>userList = mockListOfUsers();
        //when
        when(principal.getName()).thenReturn("Mike");
        when(userRepository.findAll()).thenReturn(userList);
        //then
        Assert.assertTrue(userService.checkUserStatus(principal).equals("admin/adminMainContent"));
    }
    @Test
    public void should_return_moderatorMainContent_when_user_status_equals_admin() {
        //given
        Principal mockPrincipal = Mockito.mock(Principal.class);
        List<User>userList = mockListOfUsers();
        //when
        when(mockPrincipal.getName()).thenReturn("Frank");
        when(userRepository.findAll()).thenReturn(userList);
        //then
        Assert.assertSame("these two are not same",userService.checkUserStatus(mockPrincipal),"moderator/moderatorMainContent");
    }

    @Test
    public void getLoggedUserId() {
        //given
        Principal mockPrincipal = Mockito.mock(Principal.class);
        List<User> userList = mockListOfUsers();
        //when
        when(mockPrincipal.getName()).thenReturn("Frank");
        when(userRepository.findAll()).thenReturn(userList);
        //then
        Assert.assertEquals(31L,(long) userService.getLoggedUserId(mockPrincipal));
    }

    @Test
    public void should_return_size_of_collection_of_users_with_status_user() {
        //given
        List<User>userList = mockListOfUsers();
        //when
        when(userRepository.findAll()).thenReturn(userList);
        //then
        Assert.assertThat(userService.getUsersWithStatusUser().size(), is(2));
    }

    @Test
    public void changePassword() {
        //given
        List<User>userList = mockListOfUsers();
        //when
        when(userService.checkUserStatus(principal)).thenReturn("userMainContent");
        when(principal.getName()).thenReturn("Marek");
        when(userRepository.findAll()).thenReturn(userList);
        //then
        Assert.assertEquals(userService.changePassword(principal,"pass","pass"),"userMainContent");
    }

    private User mockSingleUser() {
        return new User("Marek","Surname","password","user","active");
    }
    private List<User>mockListOfUsers() {
        User user = new User("Marek","Surname","password","user","active");
        user.setId(11L);
        User user1 = new User("Mike","Owen","pass","admin","active");
        user.setId(21L);
        User user2 = new User("Frank","Owen","pass","moderator","active");
        user.setId(31L);
        User user3 = new User("Olo","Bony","password","user","active");
        user.setId(411L);
        List<User>users = new ArrayList<>();
        users.add(user);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        return users;
    }
}