package spring.spring_boot_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.spring_boot_security.dto.ResponseDTO;
import spring.spring_boot_security.dto.TodoDTO;
import spring.spring_boot_security.entity.TodoEntity;
import spring.spring_boot_security.service.TodoService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {
    @Autowired
    private TodoService service;

    /*ResponseEntity란?
    * 해당 객체를 이용해 상태코드, 응답 본문 등을 설정해서 클라이언트 응답
    * HTTP응답의 상태코드와 헤더를 포함해 더 세부적으로 제어
    * 메서드
    * ok() : 성공
    * headers() : 응답 헤더 설정
    * body(): 응답본문 설정
    * */
    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto){
        try{
            //Todo : 임시유저 하드코딩한 부분, 추후 로그인된 유저로 변경 필요
            String temporaryUserId = "temporary-user";

            //1. DTO > entity 변환 과정
            TodoEntity entity = TodoDTO.toEntity(dto);

            //2. 생성하는 당시에는 id(pk)는 null로 초기화
            //새로 생성하는 에코드(행)이기 때문
            entity.setId(null);

            //3. 유저 아이디 설정 (누가 생성한 Todo인지 설정)
            //Todo : 임시 유저하드코딩한 부분으로 추후 로그인된 유저로 변결 필요
            entity.setUserId(temporaryUserId);

            //4. 서비스 계층을 이용해 todo entity를 생성
            List<TodoEntity> entities = service.create(entity);

            //5. return된 엔티티 리스트를 TodoDTO로 변환
            List<TodoDTO> dtos =  new ArrayList<>();
            for(TodoEntity tEntity :entities){
                TodoDTO tDto = new TodoDTO(tEntity);
                dtos.add(tDto);
            }

            //6. 변환된 todoDTO 리스트를 이용해 ResponseDTO초기화
            //TodoDTO타입을 담는 ResponseDTO객체를 빌드하겠습니다. 라는 의미
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            
            //7. ResponseDTO를 클라이언트에게 응답
            //ResponseEntity.ok(): http상태코드를 200으로 설정
            //body(response):응답의 body를 response 인스턴스로 설정
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            //8. 예외 발생항 경우, ResponseDTO의 data필드 대신, error필드에 에러 메시지를 넣어서 리턴
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();

            //badRequest():400 에러 응답을 전송
            return ResponseEntity.badRequest().body(response);
        }
    }
}
