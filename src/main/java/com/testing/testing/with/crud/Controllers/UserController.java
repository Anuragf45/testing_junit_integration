package com.testing.testing.with.crud.Controllers;

import com.testing.testing.with.crud.Model.UserModel;
import com.testing.testing.with.crud.Repositories.UserRepository;
import com.testing.testing.with.crud.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class UserController {
    @Autowired
  private UserRepository UserRepository;
    @Autowired
    private UserService userService;

    Logger logger= LoggerFactory.getLogger(UserController.class);
//    @GetMapping("/getUser")
//    public List getUser(UserModel model){
//       return UserRepository.findAll();
//    }

    @GetMapping("/getUser")
public ResponseEntity<?> getAllUsers(){
        logger.info("Giving all the users");
        return userService.getUsers();
    }

    @GetMapping("/getUser/{id}")
    public  ResponseEntity<?> getUserById(@PathVariable String id){
        logger.info("Getting user with the given id");
        return userService.getUserById(id);
    }
//    @PostMapping("/createUser")
//   public UserModel createUser(@RequestBody UserModel model){
//        UserRepository.save(model);
//        return model;
//    }
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserModel model){
        logger.info("Creating an user");
        return userService.createUser(model);
    }
    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable String id, UserModel model){
        UserRepository.deleteById(id);
        return "User deleted Successfully";
    }

    @PutMapping("/editDetails/{userName}")
    public  String updateUser(@PathVariable String userName, UserModel model) {
        Optional<UserModel> optional1 = UserRepository.findByUserName(userName);
        System.out.println(userName);
        UserModel modelData = optional1.get();
        String res;
        if (!optional1.isPresent()) {
            return "No user found with this userName " + userName;
        } else {
            res = "";
            String str = modelData.getFullName();
            char[] specialChars = {'!', '@', '#', '%','*','&'};
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == 'a' || str.charAt(i) == 'e' || str.charAt(i) == 'i' || str.charAt(i) == 'o' || str.charAt(i) == 'u') {
                    int randomNum = (int) Math.floor(Math.random() * 10) % specialChars.length;

                    res += specialChars[randomNum];

                } else {
                    res += str.charAt(i);
                }
            }
            System.out.println(res);

        }
        return res;

    }

//    @PutMapping("/updateUser/{id}")
//    public ResponseEntity<?> updateUserData(@PathVariable String id, @RequestBody UserModel model){
//        return repository.
//
//    }
}
