package com.testing.testing.with.crud.Services;


import com.testing.testing.with.crud.Model.UserModel;
import com.testing.testing.with.crud.Repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;


   UserModel user;
    @BeforeEach
    public void setUp(){
        user = new UserModel("1","Anuragf45","anurag","9315731922","hyderabad","albanero","anuragf45@gmail.com");
        System.out.println(user.getUserName());
    }

    @AfterEach
    public void tearDown(){
        user = null;
    }

    @Test
    @DisplayName("Test for creation of user")
    public void test_createUser(){
        given(userRepository.save(user)).willReturn(user);
        ResponseEntity<?> savedUser = userService.createUser(user);
        assertThat(savedUser).isNotNull();
    }

    @Test
    @DisplayName("Test for get all users")
    public void test_getAllUsers(){
        UserModel user1 = new UserModel("45","Rohit Sharma","Rohit45","4545454545","rohit45@mumbaiIndians.com","ICT","Mumbai");
        UserModel user2 = new UserModel("18","Virat Kohli","Virat18","1818181818","virat@rcb.com","ICT","Delhi");
        given(userRepository.findAll()).willReturn(List.of(user1,user2));
        ResponseEntity<List<UserModel>> users = userService.getUsers();
        assertThat(users).isNotNull();
        assertThat(users.getBody().size()).isEqualTo(2);
    }


    @Test
    @DisplayName("test to get all users (Negative test case)")
    public void test_getAllUsers_Neg(){
        given(userRepository.findAll()).willReturn(Collections.emptyList());
        ResponseEntity<?> users = userService.getUsers();

    }

    @Test
    @DisplayName("test to get a single user using id")
    public void test_getUserById(){
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        UserModel foundUser = (UserModel) userService.getUserById(user.getId()).getBody();
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("test to get a single user using id(Negetive test case)")
    public void test_getUserById_Neg(){
        given(userRepository.findById("123")).willReturn(Optional.ofNullable(null));
        String foundUser = (String) userService.getUserById("123").getBody();
        assertThat(foundUser).isEqualTo("No such User exist with id: 123");
    }





    @Test
    @DisplayName("Test for deleting userDate")
    public void test_deleteByUserName(){
        //given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

       given(userRepository.findByUserName(user.getUserName())).willReturn(Optional.of(user));
        willDoNothing().given(userRepository).deleteById(user.getId());
        userService.deleteByUserName(user.getUserName());
        verify(userRepository,times(1)).deleteById(user.getId());
    }

}
