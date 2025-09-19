package spring.spring_boot_jpa.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BoardDTO {
    private int id;
    private String title;
    private String content;
    private String writer;
    private int no;
}
