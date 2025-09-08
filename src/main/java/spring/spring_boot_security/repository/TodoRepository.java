package spring.spring_boot_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.spring_boot_security.entity.TodoEntity;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    List<TodoEntity> findByUserId(String userId);


}

