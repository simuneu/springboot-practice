package spring.spring_boot_mybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 템플릿 엔진을 렌더링하는 코드들
@Controller
public class ViewController {

    // GET 요청시 /users 경로로 리다이렉트
    @GetMapping("/")
    public String redirectToUser() {
        return "redirect:/users";
    }

    // GET /users 요청 시 userList.html 템플릿 뷰 반환
    @GetMapping("/users")
    public String userList() {

        return "userList.html";
    }

    // GET /users/new 요청 시 userForm.html 템플릿 뷰 반환 ("Create User")
    @GetMapping("/users/new")
    public String newUserForm() {
        return "userForm.html";
    }

    // GET /users/유저아이디/edit 요청 시 userForm.html 템블릿 뷰 반환 ("Edit User")
    @GetMapping("/users/{id}/edit")
    public String editUserForm(@PathVariable int id) {
        return "userForm.html";
    }

}
