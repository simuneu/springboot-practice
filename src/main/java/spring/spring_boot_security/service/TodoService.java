package spring.spring_boot_security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import spring.spring_boot_security.dto.TodoDTO;
import spring.spring_boot_security.entity.TodoEntity;
import spring.spring_boot_security.repository.TodoRepository;

import java.util.List;
import java.util.Optional;

@Slf4j //simple logging facade for java
/*로그 라이브러리
* info, debug, warn, error로 용도에 따라 나눠서 로깅
* 로깅하고 싶은 클래스에 해당 어노테이션을 붙이면 됨*/
@Service
public class TodoService {
    @Autowired
    private TodoRepository repository;

    //create todo
    public List<TodoEntity> create(final TodoEntity entity){
        validate(entity);
        repository.save(entity);
        log.warn("Entity Id:{} is saved", entity.getId()); //로그찍기
        return repository.findByUserId(entity.getUserId()); //db select 수행(추가한 행을 바로 다시 보여주기)
    }
    //read todo
    public List<TodoEntity> retrieve(final String userId){
        return repository.findByUserId(userId);
    }

    //유효성 검사
    private void validate(final TodoEntity entity){
        if(entity==null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }
        if(entity.getUserId()==null){
            log.warn("unknown user");
            throw new RuntimeException("Unknown user");
        }
    }

    public TodoEntity update(TodoDTO dto){
        TodoEntity entity  = new TodoEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDone(dto.isDone());

        //orElseThrow - 값이 존재하지 않을 때 지정한 예외를 발생시키는 역할
        TodoEntity oEntity = repository.findById(entity.getId())
                .orElseThrow(()-> new RuntimeException("Todo not found with id: " + entity.getId()));
        if (!oEntity.getUserId().equals(entity.getUserId())) {
            throw new RuntimeException("권한X.");
        }

        if (entity.getTitle() != null) {
            oEntity.setTitle(entity.getTitle());
        }
        if (entity.isDone() != false) {
            oEntity.setDone(entity.isDone());
        }

        return repository.save(oEntity);
    }

    public TodoDTO delete(final String userId, Long id){
        Optional<TodoEntity> Oentity = repository.findById(id);

        if(Oentity.isEmpty()){
            throw new RuntimeException("id not found");
        }
        TodoEntity entity = Oentity.get();

        if(!entity.getUserId().equals(userId)){
            log.warn("권한X ", userId, id);
            throw new RuntimeException("error");
        }
        try{
            repository.delete(entity);
        }catch (Exception e){
            log.error("error2", e);
            throw new RuntimeException("error");
        }
        return new TodoDTO(entity);
    }
}