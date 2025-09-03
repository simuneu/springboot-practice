package spring.spring_boot_mybatis.mapper;

import org.apache.ibatis.annotations.*;
import spring.spring_boot_mybatis.domain.User;

import java.util.List;

@Mapper //해당 인터페이스가 MyBatis mapper임을 나타냄
public interface UserMapper {
    //1.어노테이션 기반
    //간단한 쿼리의 경우 간결하게 표현
    //Java코드 내 SQL문을 직접 볼 수 있어 즉각적 이해 가능
    // @select @insert @update @delete어노테이션 사용
   /* @Select("SELECT *FROM users")
    List<User> findAll();

    @Select("SELECT * FROM users where id = #{id}")
    User findById(Long id);

    @Insert("INSERT INTO users(username, email) values(#{username}, #{email})")

    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);
    //insert 작업에 대한 추가 옵션 설정
    //useGeneratedKeys = true, 데이터베이스에서 자동 생성되는 키(auto - increment pk)를 사용
    //keyProperty = "id"' 생성된 키 값을 User객체의 id필드에 저장하겠다는 의미
    // insert 메서드 호출 후 전달된 User객체의 id필드는 데이터베이스에서 생성된 실제 id값으로 업데이트
    //새로 삽입된 행의 id를 알 수 있게 되어 이후 작업에 해당 id를 사용 가능
    // 만약 users 테이블의 id(pk)를 수동으로 설정한다면 Option

    @Update("Update users SET username = #{username}, email = #{email} WHERE id = #{id}")
    void update(User user);


    @Delete("DELETE FROM users WHERE id = #{id}")
    void delete(Long id);*/


    //2. XML기반 매퍼
    /*복잡한 SQL 쿼리를 쉽게 관리
    * 동적 SQL작성 편리
    * SQL과 Java코드를 분리하여 관리
    * 대규모 프로젝트나 복잡한 데이터를 조작하는데 적합
    */
    List<User> findAll();
    User findById(Long id);
    void insert(User user);
    void update(User user);
    void delete(Long id);

}
