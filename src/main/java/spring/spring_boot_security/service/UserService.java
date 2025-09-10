package spring.spring_boot_security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.spring_boot_security.entity.UserEntity;
import spring.spring_boot_security.repository.UserRepository;

import java.util.EnumMap;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository repository;

    //회원가입
    public UserEntity create(final UserEntity userEntity){
        //유효성 검사1. userEntity혹은 email이  null인 경우 예외
        if(userEntity == null || userEntity.getEmail() == null){
            throw new RuntimeException("Invalid argument");
        }
        final String email = userEntity.getEmail();
        //2. 이메일이 이미 존재하는 경우 예외(email= unique)
        if(repository.existsByEmail(email)){
            log.warn("Email already exists{}", email);
            throw new RuntimeException("Email already exists");
        }
        return repository.save(userEntity); //userEntity를 db에 저장
    }

    //인증 : 이메일과 비번으로 사용자 조회
    public UserEntity getByCredentials(final String email, final String password){
        //db에서 해당 정보가 일치하는 유저가 있는지 조회
        return repository.findByEmailAndPassword(email, password);
    }
}
