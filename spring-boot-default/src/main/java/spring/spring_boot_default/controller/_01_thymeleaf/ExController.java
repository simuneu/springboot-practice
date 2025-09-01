package spring.spring_boot_default.controller._01_thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class ExController {
    @GetMapping("/people")
    public String getPeople(Model model){
        model.addAttribute("age", 20);

        List<Person> people = Arrays.asList(
                new Person(20,"lee"),
                new Person(21,"park"),
                new Person(22,"song"),
                new Person(23,"lim")
        );

        model.addAttribute("people", people);
        return "_01_thymeleaf/people";
    }

    class Person{
        private int age;
        private String name;
        public Person(int age, String name) {
            this.age =age;this.name=name;
        }
        public int getAge(){
            return age;
        }
        public String getName(){
            return name;
        }
    }
}
