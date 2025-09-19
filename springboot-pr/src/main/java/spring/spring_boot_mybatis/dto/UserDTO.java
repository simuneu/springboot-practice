package spring.spring_boot_mybatis.dto;
//데이터 전송 객체로 블라이언트와 서버 간 데이터 교환에 사용
//클라이언트에게 노출하고 싶지 않은 정보를 domain에만 포함하고, DTO변환 과정에서는 제외할 수 있음
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    //실제 데이터베이스에 존재하는 컬럼은 아니지만 서비스로직에서 no정보를 활용할 예정
    private int no;
}
