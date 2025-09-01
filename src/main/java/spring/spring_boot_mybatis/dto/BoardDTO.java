package spring.spring_boot_mybatis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO {
    private int id;
    private String title;
    private String content;
    private String writer;
    private int no;
}
