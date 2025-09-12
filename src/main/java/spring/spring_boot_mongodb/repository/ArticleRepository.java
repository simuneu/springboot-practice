package spring.spring_boot_mongodb.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import spring.spring_boot_mongodb.model.Article;

import java.util.List;

/*MongoRepository
* :Spring Data mongoDB의 기본 Repository 인터페이스
* 쿼리 메서드 네이밍 - 메서드 이름으로 쿼리 자동 생성
*  @Query: 복잡한 쿼리를 직접 작성할 수 있는 어노테이션  */
@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {
    //======텍스트 검색 관련 메서드

    //텍스트 검색 결과를 정렬하여 조회
    //TextCriteria : MongoDB의 텍스트 검색 조건
    //Sort : 검색 결과 정렬 방식 지정
    List<Article> findBy(TextCriteria criteria, Sort sort);

    //텍스트 검색 결과를 페이징하여 조회
    //Pageable: 페이징 정보(페이지 번호, 크기 , 정렬)
    List<Article> findBy(TextCriteria criteria, Pageable pageable);

    //텍스트검색 결과를 점수 내림차로 조회
    //점수 기반 정렬 : 검색 관련성이 높은 순서 정렬
    List<Article> findByOrderByScoreDesc(TextCriteria criteria);

    //======기본 쿼리 메서드

    //이름으로 정확한 매칭 검색
    List<Article> findByName(String name);

    //이름과 이메일로 AND조건 검색-> name=? AND email=?
    List<Article> findByNameAndEmail(String name, String email);

    //이름과 이메일로 OR 검색-> name=? OR email=?
    List<Article> findByNameOrEmail(String name, String email);

    //이름이 특정 문자열로 시작하는 문서 검색 / name LIKE 'prefix%'
    List<Article> findByNameStartingWith(String name);

    //이름이 특정 문자열로 끝나는 문서 검색 / name LIKE '%suffix'
    List<Article> findByNameEndingWith(String name);

    //이름에 특정 문자열이 포함된 문서 감색 /name LIKE '%substrings%'
    List<Article> findByNameContaining(String name);

    //이름이 LIKE 패턴과 일치하는 문서 검색
    //정규식 기반의 패턴 매칭
    List<Article> findByNameLike(String name);

    
    //===커스텀 쿼리
    @Query("{name:?0}") //검색조건 : name이 첫 번째 매개변수와 일치
    @Update("{$set: {email: ?1}}") //@Update : MongoDB의 업데이트 쿼리 작성, email필드 두 번째 매개변수 설정($set은 필드값을 설정하는 연산자)
    int updateEmailByName(String name, String email); //반환값 int ? 일괄 업데이트된 문서 개수
    
        
}
