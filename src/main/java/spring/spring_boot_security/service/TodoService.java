package spring.spring_boot_security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.spring_boot_security.entity.TodoEntity;
import spring.spring_boot_security.repository.TodoRepository;

import java.util.List;

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
}
