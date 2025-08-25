package spring.spring_boot_default.dto_vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private String title;
    private String author;
    private int price;

    @Override
    public String toString(){
        return "제목 : "+title+", 저자: "+author+"가격 : "+price+"원";
    }
}


