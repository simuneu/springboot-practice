package spring.spring_boot_default.controller._02_restapi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RestController {
    //GET localhost:PORT 요청 시 _02_restapi/req.html파일을 브라우저에 렌더링
    @GetMapping("/")
    public String getReq(){return "_02_restapi/req";}

    //GET요청
    //#1
    @GetMapping("/get/res1") //action의 경로
    public String getRes1(@RequestParam(value = "name")String name,
                          @RequestParam(value = "age") int age, Model model){
        //@RequestParam
        //HTTP요청 파라미터를 메서드 매개변수에 바인딩
        //query string중에서 name key에 대한 value를 String name에 매핑
        //required = true가 기본값이므로 요청 url에서 key를 필수로 보내야 함 (age도 마찬가지)
        //int는 primitive type, String은 빈 문자열도 유효한 값으로 취급되기 때문에 age를 빈칸으로 넣으면 error
        System.out.println("name = "+name);
        System.out.println("age = "+age);

        //view에 전달할 데이터를 모델 객체에 추가
        model.addAttribute("name", name);
        model.addAttribute("age", age);

        //응답 결과를 보여줄 뷰 이름 반환
        return "_02_restapi/res";
    }

    //#2
    @GetMapping("/get/res2")
    public String getRes2(@RequestParam(value="name", required = false)String name, Model model){
        //String은 빈 문자열도 유효한 값으로 취급 null이 들어감. required = false로 선택적임을 명시
        System.out.println("name="+name);
        model.addAttribute("name", name);
        return "_02_restapi/res";
    }

    //#3
    @GetMapping("/get/res3/{param1}/{param2}")
    public String getRes3(@PathVariable String param1,
                          @PathVariable(value="param2") int age, Model model){
        //@PathVariable
        //url path variable을 사용할 때 필요한 어노테이션
        //기본적으로 path variable은 값을 가져야 함.(값이 없으면 404)
        //path variable과 해당 메서드의 매개변수명을 다르게 사용하고 싶다면?
        //ex. @PathVariable(value="param2") int age
        System.out.println("name="+param1);
        System.out.println("age="+age);

        model.addAttribute("name", param1);
        model.addAttribute("age", age);

        return "_02_restapi/res";
    }

    //#4
    //선택적으로 받아오는 path variable이 있으면 {}안에 경로 여러 개 설정}
    @GetMapping({"get/res4/{name}/{age}","get/res4/{name}"})
    public String getRes4(@PathVariable String name,
                          @PathVariable(required = false)Integer age, Model model){ //age가 옵션임. required = false
                        //PathVariable중에서 name은 필수 경로변수,  age는 선택 경로변수라면 optional한 경로변수가 뒤에 와야 함
        System.out.println("name="+name);
        System.out.println("age="+age);

        model.addAttribute("name", name);
        model.addAttribute("age", age);

        return "_02_restapi/res";
    }
}