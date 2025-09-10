package spring.spring_boot_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.spring_boot_security.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    Boolean existsByEmail(String email);
    UserEntity findByEmailAndPassword(String email, String password);
}
