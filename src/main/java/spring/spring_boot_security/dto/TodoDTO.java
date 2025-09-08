package spring.spring_boot_security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.spring_boot_security.entity.TodoEntity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data //getter, setter, equals, hashcode, toString
public class TodoDTO {
    private Long id;
    private String  title;
    private boolean done;

    //TodoEntity 매개변수로 받아 DTO객체로 변환하는 생성자
    public TodoDTO(final TodoEntity entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }

    //DTO > entity
    public static TodoEntity toEntity(final TodoDTO dto){
        return TodoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.isDone())
                .build();
    }
}
