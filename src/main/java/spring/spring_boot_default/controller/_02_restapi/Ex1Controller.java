package spring.spring_boot_default.controller._02_restapi;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
public class Ex1Controller {

    @GetMapping("/introduce")
    public String getIntroduce(){return "_03_introduce/introduce";}

    //1
    //http://localhost:8080/introduce/lee
    @GetMapping("/introduce/{name}")
    @ResponseBody
    public String getEx1(@PathVariable(value = "name")String name){

        return "내 이름은 "+name;
    }

    //2
    //http://localhost:8080/introduce2?name=lee&age=20
    @GetMapping("/introduce2")
    @ResponseBody
    public String getEx1(@RequestParam String name,
                         @RequestParam  int age){
        return "내 이름은 "+name+" 내 나이는 "+age;
    }

    //3
    //http://localhost:8080/introduce
    @PostMapping("/post/introduce1")
    @ResponseBody
    public String postEx1(@RequestParam String name,
                          @RequestParam String gender,
                          @RequestParam int year,
                          @RequestParam int month,
                          @RequestParam int day,
                          @RequestParam String[] interest, Model model){
        model.addAttribute("name", name);
        model.addAttribute("gender", gender);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("day", day);
        model.addAttribute("interest", interest);

        String interestString = Arrays.toString(interest);
        return "이름 : "+name+ "<br>"
                +"성별:"+gender+"<br>"
                +"생년월일 : "+year+"-"+month+"-"+day+"<br>"
                +"관심사 : "+interestString;
    }
}

