package spring.spring_boot_default.controller._01_thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// @Controller 어노테이션
// - Spring MVC 에 Controller 클래스로 인식되어 Spring MVC 가 제공하는 다양한 어노테이션을 사용
@Controller
public class HelloController {

    // 클라이언트 요청에 대한 처리를 각 메서드에서 작성

    // GET /hi 요청에 대한 처리
    @GetMapping("/hi")
    public String getHi(Model model) {
        /*
        // Model
        // - Spring MVC 가 제공하는 타입 (참고로 MVC 패턴의 Model 과는 별개, 그저 View 에 값을 전달하는 상자 역할)
        // - View 에서 참조할 수 있는 객체 저장 가능
        model.addAttribute("msg", "Hi~");

        // 어떤 템플릿 엔진을 보여줄 것인지 (경로 기입)
        return "_01_thymeleaf/hi";
        */

        // Thymeleaf 표현식과 문법
        model.addAttribute("hello", "Spring World");
        model.addAttribute("helloo", "<strong>Spring</strong> <u>World</u>");
        model.addAttribute("hello2", "<strong>Spring</strong> <u>World</u>");
        model.addAttribute("iptvalue", "이름을 입력하세요~");
        model.addAttribute("iptvalue", "이름을 입력하세요~");
        model.addAttribute("withValue", "안뇽");
        model.addAttribute("link", "hi");
        model.addAttribute("imgSrc", "minion.webp");

        model.addAttribute("userRole", "admin");
        model.addAttribute("isAdmin", false);

        List<String> names = Arrays.asList("Kim", "Lee", "Hong", "Jang");
        model.addAttribute("names", names);

        Person person = new Person(20);
        model.addAttribute("classPerson", person);

        // 템플릿 파일명(View)을 리턴
        return "_01_thymeleaf/hi";
    }

    class Person {
        private int age;

        public Person(int age) { this.age = age; }

        public int getAge() { return age; }
    }
}

