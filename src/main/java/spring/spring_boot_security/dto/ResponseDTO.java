package spring.spring_boot_security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
    //error 발생시 에러 메시지를 반환하도록
    private String error;
    //200번대 응답시 해강 type의 리스트를 반환하도록
    private List<T> data;
}

/*ResponseDTO 사용하는 이유??
 - 응답 형식의 일관성을 유지하기 위해
 - HTTP Response할 때 사용하게 될 DTO
 - 서버에서 클라이언트로 응답할 때 사용할 데이터 구조 정의
* */