package spring.spring_boot_default.controller._02_restapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.spring_boot_default.dto.User2DTO;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/members")
public class UserController {

    private final List<User2DTO> users = new ArrayList<>();

    @PostMapping("/signup")
    @ResponseBody
    public String signup(@RequestBody User2DTO userDTO){
        users.add(userDTO);
        System.out.println(userDTO.getId());
        System.out.println(userDTO.getPassword());
        return userDTO.getId()+"님 회원가입 완료";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody User2DTO userDTO){
        for(User2DTO user: users){
            if(user.getId().equals(userDTO.getId()) &&
                user.getPassword().equals(userDTO.getPassword())) {
                return userDTO.getId()+"님 로그인 완료";
            }
        }
        return "로그인 실패";
    }

    @PostMapping("/updateInfo")
    @ResponseBody
    public String updateInfo(@RequestBody User2DTO userDTO){
        for(User2DTO user: users){
            if(user.getId().equals(userDTO.getId())) {
                user.setPassword(userDTO.getPassword());
            }
            return userDTO.getId()+"님 정보 수정 완료";
        }
        return "누구세요";
    }

    @DeleteMapping("delete/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable("id") String id){
        boolean removed = users.removeIf(user -> id.equals(user.getId()));
        if (removed) {
            return id + "님 삭제 완료";
        } else {
            return id + "님을 찾을 수 없습니다";
        }
    }
}
