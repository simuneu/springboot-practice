package spring.spring_boot_mongodb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.spring_boot_mongodb.model.Article;

//Article 조회 응담을 위한 DTO
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDTO {
    private String id;
    private String name;
    private String email;
    private String title;
    private String description;
    private Float score; //검색어와의 연관성을 나타내는 수치로 값이 높을수록 관련성이 높음
    private int age;

    //Entity > DTO변환하는 정적 팩토리 메서드
    //정적 팩토리 메서드 ?  디자인 패턴 중 한 종류
    public static  ArticleResponseDTO from(Article article){
        return ArticleResponseDTO.builder()
                .id(article.getId())
                .name(article.getName())
                .email(article.getEmail())
                .title(article.getTitle())
                .description(article.getDescription())
                .score(article.getScore())
                .build();
    }
}
