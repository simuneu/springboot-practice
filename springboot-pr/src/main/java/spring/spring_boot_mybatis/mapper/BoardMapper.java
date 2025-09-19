package spring.spring_boot_mybatis.mapper;

import org.apache.ibatis.annotations.*;
import spring.spring_boot_mybatis.domain.Board;

import java.util.List;

@Mapper
public interface BoardMapper {
 /*   @Select("SELECT * FROM board")
    List<Board> findAll();

    @Select("SELECT * FROM board WHERE writer = #{writer}")
    Board findByWriter(String writer);

    @Update("update board set title=#{title},content=#{content}")
    void update(Board board);


    @Delete("DELETE FROM board WHERE id= #{id}")
    void delete (int id );

    @Insert("INSERT INTO board(title, content, writer) values(#{title}, #{content}, #{writer})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Board board);*/

    List<Board> findAll();
    Board findByWriter(String writer);
    void update(Board board);
    void delete (int id );
    void insert(Board board);

}
