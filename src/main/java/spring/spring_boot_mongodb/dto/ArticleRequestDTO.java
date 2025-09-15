package spring.spring_boot_mongodb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Article 생성/수정 요청을 위한 DTO
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequestDTO {
    private String name;
    private String email;
    private String title;
    private String description;
}
