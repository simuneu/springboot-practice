package spring.spring_boot_mongodb.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;

@Document //MongoDB Collection mapping(jpa @entity와 유사)
@Data  //getter, setter, toString, equals, hashcode
@Builder //builder pattern
public class Article {
    @Id //mongoDB의 id field로 사용되며, String 타입으로 선언하면 자동으로 ObjectId가 문자열로 변환(JPA의 @Id와 동일한 역할)
    private String id;
    
    private  String name;
    
    private String email;
    
    @TextIndexed(weight = 2) //텍스트 검색 인덱스 생성, 가중치 2
    //가중치 ? -가중치가 높을 수록 검색 결과에서 더 중요한 필드로 취급
    //제목이 내용보다 중요하므로 가중치를 2로 설정
    private String title;
    
    @TextIndexed //텍스트 검색 인덱스 생성, 가중치1(기본값)
    //제목보다 덜 중요하지만 검색 대상에 포함됨
    private  String description;
    
    @TextScore //MongoDB 텍스트 검색시 자동으로 계산되는 점수
    //검색어와의 관련성을 나타내는 수치(높을수록 관련성이 높음)
    //실제 데이터베이스에는 저장되지 않고, 검색 시에만 계산됨
    //검색 결과 정렬이나 필터링할 때 좋음
    private  Float score;
}
