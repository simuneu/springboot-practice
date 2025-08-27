package spring.spring_boot_default.controller._02_restapi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.spring_boot_default.dto.UserDTO;
import spring.spring_boot_default.vo.UserVO;

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

    //#5
    @PostMapping("/post/res1")
    public String postRes1(@RequestParam String name, @RequestParam int age, Model model){
        System.out.println("name="+name);
        System.out.println("age="+age);

        model.addAttribute("name", name);
        model.addAttribute("age", age);

        return "_02_restapi/res";
    }

    //#6
    @PostMapping("/post/res2")
    public String postRes2(@RequestParam String name,
                           @RequestParam(required = false) Integer age, Model model){
        System.out.println("name="+name);
        System.out.println("age="+age);

        model.addAttribute("name", name);
        model.addAttribute("age", age);

        return "_02_restapi/res";
    }

    //#7
    //1-6까지는 template view를 반환했지만 api서버를 활용, 데이터 자체를 응답하고자 한다면?
    @PostMapping("/post/res3")
    @ResponseBody //메서드의 반환값을 응답 본문(response body)에 직접 쓰도록 지시
    public String PostRes3(@RequestParam String name, @RequestParam int age, Model model){
        //응답 시 객체를 JSON으로 리턴할 때 사용
        //즉 응답 객체를 전달(node express res.send()와 유사
        System.out.println("name="+name);
        System.out.println("age="+age);

        model.addAttribute("name", name);
        model.addAttribute("age", age);

        //문자열 그 자체를 응답
        return name+" "+age;
    }

    //#8
    @GetMapping("/dto/res1")
    @ResponseBody
    //req ex /dto/res1?name=lee&age=1
    // @ModelAttribute UserDTO userDTO
    // 요청 파라미터를 DTO 객체에 바인딩
    // 폼 input name속성들(name, age)이 userDTO 필드명과  일치하면 자동 매핑
    // 매핑 dto의 setter사용
    // ?name=s&age=1 > setName("s"), setAge(1)
    public String dtoRes1(@ModelAttribute UserDTO userDTO) {
        System.out.println("[GET] userDTO (name) = "+userDTO.getName());
        System.out.println("[GET] userDTO (age) = "+userDTO.getAge());

        return userDTO.getName()+" "+userDTO.getAge();
    }

    //#9
    @PostMapping("/dto/res2")
    @ResponseBody
    /*
    * @ModelAttribute 어노테이션 생략 가능
    * 파라미터의 UserDTO 타입 앞에 아무것도 없으면 @ModelAttribute자동 추가
    * POST 방식이므로 폼 데이터를 자동으로 UserDTO에 바인딩
    * */
    public String dtoRes2(UserDTO userDTO) {
        System.out.println("[POST] userDTO (name) = "+userDTO.getName());
        System.out.println("[POST] userDTO (age) = "+userDTO.getAge());

        return userDTO.getName()+" "+userDTO.getAge();
    }

    //#10
    @PostMapping("/dto/res3")
    @ResponseBody
    public String dtoRes22(@RequestBody UserDTO userDTO) {
        /*
        * @RequestBody어노테이션
        * 요청의 본문(req.body)에 있는 데이터를 읽어와서 객체에 매핑
        * 매핑? 필드에 값을 주입
        * 반환 값을 HTTP본문에 직접 작설
        * 단 요청의 형식이 JSON 또는 XML일 때(지금은 일반 폼 전송)
        * 415에러 : 서버가 클라이언트로부터 받은 요청의 미디어타입을 지원하지 않거나 이해할 수 없는 경우 발생
        * 즉 해당 요청은 MIME Type이 application/x-www-form-urlencoded
        * @RequestBody어노테이션 사용시 오류 발생
        * 오류가 안나려면?
        * @ModelAttribute 사용
        * 클라이언트 측에서 js코드를 사용해 폼데이터를 json변환하여 전송 > 동적 폼 전송
        * */
        System.out.println("[POST] userDTO (name) = "+userDTO.getName());
        System.out.println("[POST] userDTO (age) = "+userDTO.getAge());

        return userDTO.getName()+" "+userDTO.getAge();
    }

    //#11
    @GetMapping("/vo/res1")
    @ResponseBody
    public String voRes1(@ModelAttribute UserVO userVO) {
        /*
        * @ModelAttribute UserVO userVO - 요청파라미터를 VO객체에 바인딩
        * 브라우저에서 응답이 null과 0으로 도착하는 이유?
        * @ModelAttribute는 Setter를 이용해 객체에 값을 주입 > VO는 Setter가 없음
        * 즉 폼에서 전송된 데이터가 주입되지 않음. name, age필드는 초기화되지 않은 상태인 null과 0으로 남게 됨
        * */
        System.out.println("[GET] userVO(name)= " + userVO.getName());
        System.out.println("[GET] userVO(age)= " + userVO.getAge());
        return userVO.getName() + " " + userVO.getAge();
    }

    // #12
    @PostMapping("/vo/res2")
    @ResponseBody
    public String voRes2(UserVO userVO) {
    //@ModelAttribute가 생략되어 기본으로 있어도 VO는 setter가 없어 앞의 이유로 null과 0
        System.out.println("[POST] userVO(name)= " + userVO.getName());
        System.out.println("[POST] userVO(age)= " + userVO.getAge());
        return userVO.getName() + " " + userVO.getAge();
    }

    // #13
    @PostMapping("/vo/res3")
    @ResponseBody
    public String voRes3(@RequestBody UserVO userVO) {
        /*
        * @RequestBody UserVO userVO > 요청 본문 데이터를 vo객체로 변환 시도
        * #10과 동일한 이유
        * 올바르게 사용하려면 @RequestBody제거 후 @ModelAttribute사용 혹은
        * 클라이언트 픅에서 js를 이용해 동적 폼 전송 구현
        * */
        System.out.println("[POST] userVO(name)= " + userVO.getName());
        System.out.println("[POST] userVO(age)= " + userVO.getAge());
        return userVO.getName() + " " + userVO.getAge();
    }
}