package spring.spring_boot_mongodb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;
import spring.spring_boot_mongodb.model.Article;
import spring.spring_boot_mongodb.repository.ArticleRepository;

import java.util.List;

@Service
@Slf4j
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    //====================================기본CRUD================================================
    //새로운 Article 생성
    //Repository.save() : MongoDB에 문서 생성
    //MongoDB가 자동으로 ObjectId생성
    public Article createArticle(Article article){
        log.info("Creating article: {}");
        return articleRepository.save(article);
    }

    //ID로 Article조회
    public Article getArticleById(String id){
        log.info("Getting article by id:{}", id);
        return articleRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Article not found with id: " + id));
    }

    //모든 Article조회(페이징 없음)
    //성능 주의 : 대용량 데이터에서는 메모리 부족 가능성
    //페이징 처리하는 것이 권장됨
    public List<Article> getAllArticle(){
        log.info("Getting all article");
        return articleRepository.findAll();
    }
    
    //모든 Article 조회(페이징 적용)
    //Pageable : 페이징 정보(페이지 번호, 크기, 정렬)
    //Page<T> : 페이징된 결과랑 메타 데이터 포함
    //필요한 만큼만 데이터를 로드하기에 메모리 효율성
    public Page<Article> getAllArticles(Pageable pageable){
        log.info("Getting all articles with pagination: {}", pageable);
        return articleRepository.findAll(pageable);
    }

    //Article 수정
    public Article updateArticle(String id, Article article){
        log.info("Updating article with id:{}", id, article);
        Article existingARticle = getArticleById(id);  //기존 데이터 조회(예외 처리 포함됨)
        
        existingARticle.setName(article.getName());
        existingARticle.setEmail(article.getEmail());
        existingARticle.setTitle(article.getTitle());
        existingARticle.setDescription(article.getDescription());
        
        return articleRepository.save(existingARticle); //수정 데이터 저장
    }

    //Article 삭제
    public void deleteArticle(String id){
        log.info("Deleting article with id:{}", id);
        Article article = getArticleById(id); //기존 데이터 조회(예외 처리 포함)
        articleRepository.delete(article); //데이터 삭제
    }


    //====================================검색관련================================================
    //텍스트 검색(사용자 정의 정렬)
    public List<Article> searchArticleWithTextCriteria(String keyword, Sort sort){
        log.info("Searching articles with keyword:{} and sort{}", keyword, sort);
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(keyword);
        //TextCriteria :Spring data MongoDB 에서 텍스트 검색을 위해 사용하는 객체
        //forDefaultLanguage : MongoDB의 기본 언어를 사용
        //matchingAny : 해당 키워드가 텍스트 인덱스가 걸린 필드 중 하나라도 포함되면 검색
        return articleRepository.findBy(criteria, sort);
    }

    //


    //====================================필드별 검색 메서드================================================
    //이름으로 정확한 매칭 검색
    public List<Article> findByName(String name){
        log.info("finding article by name:{}", name);
        return articleRepository.findByName(name);
    }

    //이메일, 이름 AND
    public List<Article> findByNameAndEmail(String name, String email){
        log.info("finding article by name:{} and email:{}", name, email);
        return articleRepository.findByNameAndEmail(name, email);
    }
    //이메일, 이름 OR
    public List<Article> findByNameOrEmail(String name, String email){
        log.info("finding article by name:{} or email:{}", name, email);
        return articleRepository.findByNameOrEmail(name, email);
    }

    //이름이 특정 문자열로 시작
    public List<Article> findByNameStartingWith(String name){
        log.info("finding article by name starting with : {}", name);
        return articleRepository.findByNameStartingWith(name);
    }

    //이름이 특정 문자열로 끝
    public List<Article> findByNameEndingWith(String name){
        log.info("finding article by name ending with : {}", name);
        return articleRepository.findByNameEndingWith(name);
    }

    //이름이 특정 문자열 포함
    public List<Article> findByNameContaining(String name){
        log.info("finding article by name containing: {}", name);
        return articleRepository.findByNameContaining(name);
    }

    //이름이 like 패턴과 일치하는 문서 검색
    public List<Article> findByNameLike(String name){
        log.info("finding article by name like: {}", name);
        return articleRepository.findByNameLike(name);
    }


    //====================================업데이트 메서드================================================
    public int updateEmailByName(String name, String email){
        log.info("Updating email for name:{} to {}", name, email);
        return articleRepository.updateEmailByName(name, email);
    }
}
