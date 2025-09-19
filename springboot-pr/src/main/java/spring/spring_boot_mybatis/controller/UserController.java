package spring.spring_boot_mybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.spring_boot_mybatis.dto.UserDTO;
import spring.spring_boot_mybatis.service.UserService;

import java.util.List;

@RestController // RESTful 웹 서비스의 컨트롤러
// - 해당 클래스 "모든 메서드"의 반환 값이 자동으로 JSON 형식으로 변환되어 HTTP 응답 본문에 포함
// - @Controller, @ResponseBody 를 결합한 어노테이션
@RequestMapping("/api/users") // 해당 클래스의 기본 요청 경로를 지정
public class UserController {

    // Controller 계층은 Service 계층을 사용 -> 의존
    @Autowired
    private UserService userService;

    // GET /api/users: 모든 사용자 정보를 리스트 반환
    @GetMapping
    public List<UserDTO> listUsers() {
        return userService.getAllUsers();
    }

    //GET/api/users/:id
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    //POST/api/users
    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO){
        //@RequestBody : HTTP요청 본문을 자바 객체로 변환
        userService.createUser(userDTO);
        return userDTO;
    }

    //PUT/api/users/:id
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        userDTO.setId(id);
        userService.updateUser(userDTO);
        return userDTO;
    }

    //DELETE/api/users/:id
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}