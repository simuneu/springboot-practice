package spring.spring_boot_mongodb.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.spring_boot_mongodb.dto.ArticleRequestDTO;
import spring.spring_boot_mongodb.dto.ArticleResponseDTO;
import spring.spring_boot_mongodb.model.Article;
import spring.spring_boot_mongodb.service.ArticleService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleResponseDTO> createArticle(@RequestBody ArticleRequestDTO requestDTO){
        log.info("creating article:{}", requestDTO);

        //dto > entity
        Article article = Article.builder()
            .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .build();

        //service 계층을 이용해 비즈니스 로직(게시글 생성) 처리
        Article createdArticle = articleService.createArticle(article);
        //entity > dto
        ArticleResponseDTO responseDTO = ArticleResponseDTO.from(createdArticle);
        //201 Created상태 코드와 함께 응답
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponseDTO>> getArticles(
            @RequestParam(value = "page", defaultValue = "0") int page, //0부터 시작하는 페이지 번호
            @RequestParam(value = "size", defaultValue = "10") int size, //한번에 몇개의 데이터를 가져올 것인지
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy, //정렬 기준 필드
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir //정렬 방향(오름차순, 내림차순)
    ){
        log.info("Getting all articles - page:{}, size:{}, sortBy:{}, sortDir:{}", page, size, sortBy, sortDir);

        //정렬 객체 생성
        Sort sort = sortDir.equalsIgnoreCase("desc")?
                Sort.by(sortBy).descending():Sort.by(sortBy).ascending();

        //페이지 객체 생성 : 페이지 번호, 크기, 정렬을 하나의 Pageable로 묶어 repository계층으로 넘기고자 함
        Pageable pageable = PageRequest.of(page, size, sort);

        //서비스 계층 호출
        Page<Article> articles = articleService.getAllArticles(pageable);

        //articles.getContent():현재 페이지의 실제 데이터 리스크만 꺼내서 dto로 변환
        List<ArticleResponseDTO> responseDTOS = articles.getContent().stream()
                .map(ArticleResponseDTO::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDTOS);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDTO> getArticleById(@PathVariable String id){
        log.info("Getting article by id: {}", id);
        Article article = articleService.getArticleById(id);
        ArticleResponseDTO responseDTO = ArticleResponseDTO.from(article);
        //200ok
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponseDTO> updateArticle(
            @PathVariable String id,
            @RequestBody ArticleRequestDTO requestDTO
            ){
        log.info("Updating article with id:{}, data :{} ", id, requestDTO);
        Article  article = Article.builder()
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .build();
        Article updatedArticle = articleService.updateArticle(id, article);
        ArticleResponseDTO responseDTO = ArticleResponseDTO.from(updatedArticle);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable String id){
        log.info("Deleting article with id:{}", id);

        articleService.deleteArticle(id);

        //204 no content 상태코드반환완료 : 응답 본문이 없음
        return ResponseEntity.noContent().build();
    }

    //============================검색api===================================
    
    //MongoDB Text Search를 활용해 에러 필드 동시 검색
    //관련도 점수(@TextScore)를 활용해 검색어와 가까운 document를 우선으로 노출
    //http://localhost:8080/api/articles/search?keyword=영등포&sortBy=score&sortDir=desc
    @GetMapping("/search")
    public ResponseEntity<List<ArticleResponseDTO>> searchArticles(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "sortBy", defaultValue = "score") String sortBy, //정렬기준 필드
            @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir //정렬 방향
    ) {
        log.info("Searching articles with keyword: {}, sortBy: {}, sortDir: {}", keyword, sortBy, sortDir);

        //정렬객체 생성
        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        //서비스 계층 호출
        List<Article> articles = articleService.searchArticleWithTextCriteria(keyword, sort);

        //entity > dto
        List<ArticleResponseDTO> responseDTOS = articles.stream()
                .map(ArticleResponseDTO::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDTOS);
    }

    //http://localhost:8080/api/articles/search/name?name=박지훈
    @GetMapping("/search/name")
    public ResponseEntity<List<ArticleResponseDTO>> findByName(@RequestParam("name")String name){
        log.info("Finding articles by name:{}", name);
        List<Article> articles = articleService.findByName(name);

        List<ArticleResponseDTO> responseDTOS = articles.stream()
                .map(ArticleResponseDTO::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDTOS);
    }

    //http://localhost:8080/api/articles/search/name?name=박지훈&email=JihoonPark@sesac.seoul.kr
    @GetMapping("/search/name-email")
    public ResponseEntity<List<ArticleResponseDTO>> findByNameAndEmail(
            @RequestParam("name") String name,
            @RequestParam("email") String email
    ){
        log.info("finding articles by name:{} and email:{}", name, email);

        List<Article> articles = articleService.findByNameAndEmail(name, email);

        List<ArticleResponseDTO> responseDTOS = articles.stream()
                .map(ArticleResponseDTO::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOS);
    }

    //http://localhost:8080/api/articles/search/pattern?name=박&patternType=starting
    @GetMapping("/search/pattern")
    public ResponseEntity<List<ArticleResponseDTO>> searchByPattern(
            @RequestParam("name") String name,
            @RequestParam("patternType") String patternType
    ){
        log.info("Searching articles by pattern - name:{}, patternType:{}", name, patternType);
        List<Article> articles;

        switch (patternType.toLowerCase()){
            case "starting": //name LIKE 'prefix'
                articles = articleService.findByNameStartingWith(name);
                break;
            case "ending":
                articles = articleService.findByNameEndingWith(name);
                break;
            case "containing":
                articles = articleService.findByNameContaining(name);
                break;
            case "like":
                articles = articleService.findByNameLike(name);
                break;
            default:
                return ResponseEntity.badRequest().build();
        }
        List<ArticleResponseDTO> responseDTOS = articles.stream()
                .map(ArticleResponseDTO::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDTOS);
    }

    //이름으로 이메일을 일괄 업데이트 API
    //http://localhost:8080/api/articles/update-email?name=박지훈&email=hoon@naver.com
    @PatchMapping("/update-email")
    public ResponseEntity<String> updateEmailByName(
            @RequestParam("name") String name,
            @RequestParam("email") String email
    ){
        log.info("Updating email for name:{} to :{}", name, email);
        int updateCount = articleService.updateEmailByName(name, email);

        String message = String.format("Updated %d article for name:%s", updateCount, name);

        return ResponseEntity.ok(message);
    }
}
