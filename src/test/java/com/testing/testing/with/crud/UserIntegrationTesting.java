package com.testing.testing.with.crud;

import com.testing.testing.with.crud.Controllers.UserController;
import com.testing.testing.with.crud.Model.UserModel;
import com.testing.testing.with.crud.Repositories.UserRepository;
import com.testing.testing.with.crud.Services.UserService;
import org.json.JSONException;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTesting {
    @LocalServerPort
    private int port;

    @Autowired
     WebClient.Builder webClient;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserController userController;

    @InjectMocks
    UserService userService;


    @BeforeEach
    public void setUp() {
        Mockito.reset(userRepository);
    }
    @Test
    public void testUser() throws JSONException {

        UserModel newUser = new UserModel(
                "1",
                "Anurag Srivastava",
                "anuragf455",
                "93157319225",
                "anuragf45@gmail.com5",
                "78270013605",
                "Albanero5"
        );

        given(userRepository.save(newUser)).willReturn(newUser);
        given(userRepository.findAll()).willReturn(List.of(newUser));
        given(userRepository.findById(newUser.getId())).willReturn(Optional.of(newUser));
        given(userRepository.findByUserName(newUser.getUserName())).willReturn(Optional.of(newUser));


        //Creating a user data
        ResponseEntity<UserModel> userCreationResponse = webClient.baseUrl("http://localhost:" + port)
                .build()
                .post()
                .uri("/createUser")
                .bodyValue(newUser)
                .retrieve().toEntity(UserModel.class).block();

        assertEquals(userCreationResponse.getStatusCode(), HttpStatus.CREATED);
        assertEquals(userCreationResponse.getBody().getUserName(), newUser.getUserName());

//        getting all the users
        ResponseEntity<List<UserModel>> users = webClient.baseUrl("http://localhost:" + port)
                .build()
                .get()
                .uri("/getUser")
                .retrieve().toEntityList(UserModel.class).block();;
        System.out.println(users.getBody());

        assertEquals(users.getStatusCode(), HttpStatus.OK);
//        assertEquals(users.getBody().size(),1);
//
//        //get user details with id
        ResponseEntity<UserModel> userWIthId = webClient.baseUrl("http://localhost:" + port)
                .build()
                .get()
                .uri("getUser/" + userCreationResponse.getBody().getId())
                .retrieve().toEntity(UserModel.class).block();
        assertEquals(userWIthId.getStatusCode(), HttpStatus.OK);
        assertEquals(userWIthId.getBody().getUserName(), userCreationResponse.getBody().getUserName());
////
////
//        //update the full name of the user
//        ResponseEntity<UserModel> updatedNameUser = webClient.baseUrl("http://localhost:" + port)
//                .build()
//                .put()
//                .uri("/editDetails/" + newUser.getUserName())
//                .retrieve().toEntity(UserModel.class).block();
//        assertEquals(updatedNameUser.getStatusCode(), HttpStatus.OK);
//        assertEquals(updatedNameUser.getBody().getFullName().length(), newUser.getFullName().length());
////
//        //deleting the user
        ResponseEntity<String> deleteUser = webClient.baseUrl("http://localhost:" + port)
                .build()
                .delete()
                .uri("/deleteUser/" + userCreationResponse.getBody().getUserName())
                .retrieve().toEntity(String.class).block();
        assertEquals(deleteUser.getStatusCode(), HttpStatus.OK);
        assertEquals(deleteUser.getBody(), "User deleted Successfully");

    }

}


