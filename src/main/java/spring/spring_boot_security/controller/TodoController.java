package spring.spring_boot_security.controller;

import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import spring.spring_boot_security.dto.ResponseDTO;
import spring.spring_boot_security.dto.TodoDTO;
import spring.spring_boot_security.entity.TodoEntity;
import spring.spring_boot_security.service.TodoService;

import java.util.ArrayList;
import java.util.Collections;
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
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
        //인증된 사용자가 정보에 접근할 수 있게
        //spring security는 security는context안에서 현재 인증된 사용자의 principal을 가져옴
        //jwtAuthenticationFilter클래스에서 userId를 바탕으로
        try{
            //Todo : 임시유저 하드코딩한 부분, 추후 로그인된 유저로 변경 필요
//            String temporaryUserId = "temporary-user";

            //1. DTO > entity 변환 과정
            TodoEntity entity = TodoDTO.toEntity(dto);

            //2. 생성하는 당시에는 id(pk)는 null로 초기화
            //새로 생성하는 에코드(행)이기 때문
            entity.setId(null);

            //3. 유저 아이디 설정 (누가 생성한 Todo인지 설정)
            //Todo : 임시 유저하드코딩한 부분으로 추후 로그인된 유저로 변결 필요
//            entity.setUserId(temporaryUserId);
            //기존 temporaryUserId대신 매개변수로 넘어온 userId를 설정
            entity.setUserId(userId);

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
    
    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){
        //임시유저 하드코딩, 추후 수정 필요
//        String temporaryUserId = "temporary-user";
        
        //1. 서비스 계층의 retrieve메서드를 사용해 투두리스트를 가져오기
//        List<TodoEntity> entities = service.retrieve(temporaryUserId);
        List<TodoEntity> entities = service.retrieve(userId);
        //2. 리턴된 엔티티 리스트를 todoDTO리스트로 변환
        List<TodoDTO> dtos = new ArrayList<>();
        for(TodoEntity tEntity : entities){
            TodoDTO tDto = new TodoDTO(tEntity);
            dtos.add(tDto);
        }
        //3. 변환된 todoDTO를 리스트를 이용해 responseDTO로 초기화
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        //4. ResponseDTO리턴
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, @PathVariable Long id, @RequestBody TodoDTO dto){
        try{
            dto.setId(id);
            TodoEntity updateEntity = service.update(dto);
            TodoDTO updateDTO = new TodoDTO(updateEntity);
            return ResponseEntity.ok().body(updateDTO);

        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            //badRequest():400 에러 응답을 전송
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId, @PathVariable Long id){
        try{
            TodoDTO dto = service.delete(userId, id);
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder()
                    .data(Collections.singletonList(dto))
                    .build();
            return ResponseEntity.ok().body(response);

        }catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
