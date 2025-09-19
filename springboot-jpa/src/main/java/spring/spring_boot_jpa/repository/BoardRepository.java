package spring.spring_boot_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.spring_boot_jpa.entity.Board;

public interface BoardRepository extends JpaRepository<Board,Integer> {
}
