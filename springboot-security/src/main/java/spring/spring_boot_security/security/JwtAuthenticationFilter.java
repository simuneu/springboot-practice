package spring.spring_boot_security.security;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Service
@Component //스프링 컨테이너에 Bean으로 등록해서 의존성 주입
public class JwtAuthenticationFilter  extends OncePerRequestFilter {
    //OncePerRequestFilter 클래스를 상속받는 JwtAuthenticationFilter클래스 구현
    //OncePerRequestFilter 클레스 : 한 요청 당 반드시 한 번만 실행됨

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try{
            //req에서 token꺼내오기
            String token = parseBearerToken(req);
            log.info("JwtAuthenticationFilter is running");

            if(token !=null && !token.equalsIgnoreCase("null")){
                String userId = tokenProvider.validateAndGetUserId(token);
                log.info("Authenticated user id: "+userId);

                //직전에 추출한 userId로 인증 객체 생성
                AbstractAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(
                            userId, null, AuthorityUtils.NO_AUTHORITIES);//userID,자격증명 , 권한목록
                authentication.setDetails((new WebAuthenticationDetailsSource().buildDetails(req)));

                //SecurityContextHolder - Spring security에서 인증된 사용자 정보를 저장하는 곳
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication); //컨텍스트에 authentication으로 심으면 이후부터는 인증된 사용자로 인식
                SecurityContextHolder.setContext(securityContext);
            }
        }catch (Exception e){
            logger.error("Could not set user authentication id security context", e);
            //spring security filter 클래스가 기본적으로 가지고 있음 - logger
        }
        //다음 필터나 컨트롤러로 실행을 넘김
        filterChain.doFilter(req, res);
    }

    //요청의 헤더에서 Bearer토큰을 가져옴
    //http요청의 헤더를 파싱해서 Bearer 토큰을 리턴
    private String parseBearerToken(HttpServletRequest req){
        //Authorization :Bearer <token>
        String bearerToken = req.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){ //조건식의 마지막에 공백 추가
            return bearerToken.substring(7); //"Bearer "문자열의 길이가 7
        }
        return null;
    }
}
