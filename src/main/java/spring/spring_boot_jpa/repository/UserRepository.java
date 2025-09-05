package spring.spring_boot_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.spring_boot_jpa.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    //기본적인 CRUD 작업을 위한 메서드들을 제공받음
    //findAll(), findById(), save(), deleteById() 등...
    //첫 제네릭 타입은 관련 테이블, 두 번째 제네릭 타입은 관련 테이블의 pk

    /*//case1. repository 메서드 사용
    //1. 사용자 이름으로 n명 조회
    //select * from user where username = ?
    List<User> findByUsername(String username);

    //2. 검색어 보냈을 때 사용자 이름/이메일에 특정 문자열이 포함된 모든 사용자 찾기
    //select * from user where username like %xx% or email like %xx%
    List<User>   findByUsernameContainingOrEmailContaining(String name, String email);

    //3. 이름이 존재하는지
    boolean existsByUsername(String username);*/

    //case2. @Query annotation
    @Query("SELECT u FROM  User u  WHERE u.username =:username")
    //User는 테이블이 아닌 엔티티이름
    //@Param : 해당 어노테이션을 이용해 바인딩
    List<User> findByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.username LIKE %:keyword% or  u.email LIKE %:keyword%")
    List<User>   findByUsernameContainingOrEmailContaining(@Param("keyword") String keyword);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username=:username")
    //참고 .CASE WHEN (조건식) then(결과1) else(결과2) end(sql문) => case when 구문
    boolean existsByUsername(String username);

    
}
