package spring.spring_boot_jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.spring_boot_jpa.dto.UserDTO;
import spring.spring_boot_jpa.entity.User;
import spring.spring_boot_jpa.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService; //service 의존

    @GetMapping
    public List<UserDTO> listUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @PostMapping
    public UserDTO createUser (@RequestBody UserDTO userDTO){
        userService.createUser(userDTO);
        return userDTO;
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        userService.updateUser(id, userDTO);
        return userDTO;
    }
    /// ////////////////////////////////////////////////////////
    @GetMapping("/byUsername")
    public List<UserDTO> getUsername(@RequestParam String username){
        return userService.getUserByUsername(username);
    }

    @GetMapping("/search")
    public List<UserDTO> searchUsers(@RequestParam String keyword){
        return userService.searchUsers(keyword);
    }

    @GetMapping("/exists")
    public boolean isUsernameExists(@RequestParam String username) {
        return userService.isUsernameExists(username);
    }
}
