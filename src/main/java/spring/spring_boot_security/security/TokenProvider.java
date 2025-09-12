package spring.spring_boot_security.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.spring_boot_security.config.jwt.JwtProperties;
import spring.spring_boot_security.entity.UserEntity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

// 사용자 정보를 박아 jwt생성
@Service //서비스 계층. 컴포넌트로 등록해서 다른 곳에서 주입받아 쓰기 위함
@Slf4j
public class TokenProvider {
    //jwt서명에 사용되는 비밀키로 지금은 하드코딩
//    private static final String SECRET_KEY="secret0001";

    //[after] JwtProperties클래스 이용해 설정 파일 값 가벼오기
    @Autowired
    private JwtProperties jwtProperties;
    //create(): 로그인 성공 시에 이 메서드가 호출되어 jwt토큰을 발급함
    public String create(UserEntity userEntity){
        //jwt토큰 만료 시간을 현재 시각으로부터 1일 뒤 만료되는 날짜로 계산
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        //jwt토큰 생성
        return Jwts.builder()
                //header에 들어갈 내용 및 서명하기 위한 SECRET_KEY
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                //payload
                .setSubject(String.valueOf(userEntity.getId())) //sub:토큰 제목(여기선 userId)
                .setIssuer(jwtProperties.getIssuer())//iss:토큰 발급자
                .setIssuedAt(new Date()) //iat : 토큰이 발급된 시간
                .setExpiration(expiryDate) //exp: 토큰 만료 시간
                .compact();//토큰 생성 "header.payload.signature"토큰 문자열 최종 생성
    }

    //validateAndGetUserId() : 토큰 디코딩 및 파싱하고 코튼 위조 여부를 확인 > 사용자의 아이디 리턴
    //클라이언트가 보낸 토큰이 유효한지 검증하고 userId를 반환
    public String validateAndGetUserId(String token){
        //parseClaimsJws 메소드가 Base64로 디코딩 및 파싱
        //header, payload를 setSigningKey로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
        //서명이 위조되거나 만료된 토큰이라면 예외 발생
        //위조되지 않았다면 페이로드(claims) 리턴
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey()) //서명 검증에 사용할 비밀키 지정
                .parseClaimsJws(token)//jwt 파싱 > h,p,s검증
                .getBody();
        return claims.getSubject();//jwt 생성 시 넣었던 sub(setId)값을 꺼냄
    }
}
