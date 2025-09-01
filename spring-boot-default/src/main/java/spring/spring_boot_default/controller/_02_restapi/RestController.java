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
        * 반환 값을 HTTP본문에 직접 작성
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

    ////////////////////////
    //#14-1
    @GetMapping("/axios/res1")
    @ResponseBody
    public String axiosRes1(@RequestParam String name, @RequestParam String age){
        System.out.println("[GET]axios(name)="+name);
        System.out.println("[GET]axios(age)="+age);
        return "이름 : "+name+", 나이 : "+age;
    }

    //#14-2
    @GetMapping("/axios/res2")
    @ResponseBody
    public String axiosRes2(UserDTO userDTO){
        /*UserDTO객체를 파라미터로 받아 자동으로 데이터 바인딩
        * DTO를 사용하여 데이터 캡슐화
        * 14-1 폼 대비 DTO를 사용하니 코드가 깔끔해지고 데이터 구조를 쉽게 확장*/
        System.out.println("[GET]axios and dto(name)="+userDTO.getName());
        System.out.println("[GET]axios and dto(age)="+userDTO.getAge());

        return "이름 : "+userDTO.getName()+", 나이 : "+userDTO.getAge();
    }

    //#15 error 발생 코드
    @PostMapping("/axios/res3")
    @ResponseBody
    public String axiosRes3(@RequestParam String name, @RequestParam String age){
        /*에러발생 이유 :
         * 클라이언트에서는 데이터를 객체로 전송랬지만 서버에서는 @RequestParam으로 받으려고 함
         * axios는 기본적으로 json형식으로 전송
         * 하지만 서버측 코드는 @RequestParam을 사용해 데이터를 받아서 에러남
         * */
        System.out.println("[POST]axios(name)="+name);
        System.out.println("[POST]axios(age)="+age);
        return "이름 : "+name+", 나이 : "+age;
    }

    //#15-2
    @PostMapping("/axios/res4")
    @ResponseBody
    public String axiosRes4(UserDTO userDTO){
       /*@RequestParam어노테이션 없이 UserDTO객체로 파라미터를 받고자한 코드
       * json데이터가 요청의 본문에 있지만 @RequestBody 없는 UserDTO는 주로 폼 데이터나 쿼리 파라미터를 바인딩하는데 사용
       * 즉 @ResponseBody어노테이션이 없으면 Spring은 JSON 요청 본문을 자동으로  UserDTO에 바인딩 해주지 않음
       * */
        System.out.println("[POST]axios and dto(name)="+userDTO.getName());
        System.out.println("[POST]axios and dto(age)="+userDTO.getAge());

        return "이름 : "+userDTO.getName()+", 나이 : "+userDTO.getAge();
    }

    //#15-3
    @PostMapping("/axios/res5")
    @ResponseBody
    public String axiosRes5(@RequestBody UserDTO userDTO){
        /*15-2의 해결책
        * @ResponseBody UserDTO 코드가 요청 본문의 json데이터를 UserDTO객체로 직접 매핑
        * RESTful API설계에 적합하며 클라이언트- 서버 간 데이터 교환을 명확하게 함.
        * */
        System.out.println("[POST]axios and dto(name)="+userDTO.getName());
        System.out.println("[POST]axios and dto(age)="+userDTO.getAge());

        return "이름 : "+userDTO.getName()+", 나이 : "+userDTO.getAge();
    }

    /// ////////////vo
    ///
    //#16-1
    @GetMapping("/axios/vo/res1")
    @ResponseBody
    public String axiosVoRes1(@RequestParam String name, @RequestParam String age){
        System.out.println("[GET]axios(name)="+name);
        System.out.println("[GET]axios(age)="+age);
        return "이름 : "+name+", 나이 : "+age;
    }
    //#16-2
    @GetMapping("/axios/vo/res2")
    @ResponseBody
    public String axiosRes2(UserVO userVO){
        /*
        * @ModelAttribute 어노테이션 생략
        * 해당 어노테이션은 setter를 통해 객체에 값을 주입
        * vo객체에는 setter 없으니 데이터 바인딩이 제대로 이뤄지지 않음
        * 그러므로 모든 필드가 기본값으로 초기화
        * */
        System.out.println("[GET]axios and vo(name)="+userVO.getName());
        System.out.println("[GET]axios and vo(age)="+userVO.getAge());

        return "이름 : "+userVO.getName()+", 나이 : "+userVO.getAge();
    }
    //#17-1
    @PostMapping("/axios/vo/res3")
    @ResponseBody
    public String axiosVoRes3(@RequestParam String name, @RequestParam String age){
        /*
        * @RequestParam
        * 주로 application/x-www-form-urlencoded혈식의 데이터를 처리하는데 사용되는 어노테이션
        * 현태 프론트에서는 json형식으로
        * */
        System.out.println("[POST]axios(name)="+name);
        System.out.println("[POST]axios(age)="+age);
        return "이름 : "+name+", 나이 : "+age;
    }
    //#17-2
    @PostMapping("/axios/vo/res4")
    @ResponseBody
    public String axiosVoRes4(UserVO userVO){
        /*
        * @ModelAttribute생략
        * setter를 통해 객체에 값을 주입하나 vo객체에는 setter가 존재하지 않으므로 데이터 바인딩이 안됨
        * */
        System.out.println("[POST]axios and vo(name)="+userVO.getName());
        System.out.println("[POST]axios and vo(age)="+userVO.getAge());

        return "이름 : "+userVO.getName()+", 나이 : "+userVO.getAge();
    }
    //#5
    @PostMapping("/axios/vo/res5")
    @ResponseBody
    public String axiosVoRes5(@RequestBody UserVO userVO){
        /*RequestBody
        * json형식의 본문을 UserVO객체로 올바르게 변환하도록 함
        * @ModelAttribute vs @RequestBody
        * @ModelAttribute : setter 함수를 실행해서 값을 넣음
        * @RequestBody는 setter를 사용하지 않고도 각 필드에 직접 접근해서 값을 주입.
        * */
        System.out.println("[POST]axios and vo(name)="+userVO.getName());
        System.out.println("[POST]axios and vo(age)="+userVO.getAge());

        return "이름 : "+userVO.getName()+", 나이 : "+userVO.getAge();
    }
}