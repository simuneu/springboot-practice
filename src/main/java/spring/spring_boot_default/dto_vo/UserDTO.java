package spring.spring_boot_default.dto_vo;

import lombok.*;

//DTO
//단순히 데이터를 전송하기 위한 목적으로 사용되니 getter, setter정도만 가짐(비즈니스 로직 미포함)
@Getter
@Setter
@ToString
//@Data //getter, setter, toString한꺼번에..
@NoArgsConstructor //기본 생성자
@AllArgsConstructor //모든 필드를 갖는 생성자
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private int age;
}
