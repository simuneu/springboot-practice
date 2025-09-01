package spring.spring_boot_default.controller._02_restapi;

import com.sun.tools.jconsole.JConsoleContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.spring_boot_default.vo.FormVO;

@Controller
public class Ex2Controller {
    @GetMapping("/form")
    public String showForm() {return "_03_introduce/form";}

    @PostMapping("/form/axios/vo")
    @ResponseBody
    public String axiosFormVo(@RequestBody FormVO formVO){
        System.out.println(formVO.getName()+"님 회원가입 성공");
        return formVO.getName()+"님 회원가입 성공";
    }

}
