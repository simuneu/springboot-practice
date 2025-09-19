package spring.spring_boot_mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.spring_boot_mybatis.domain.User;
import spring.spring_boot_mybatis.dto.UserDTO;
import spring.spring_boot_mybatis.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

@Service //이 클래스가 서비스 계층의 컴포넌트임을 나타냄
public class UserService {
    //UserService가 UserMapper를 사용하기에
    //UserMapper 인터페이스의 구현체 자동주입
    @Autowired
    private UserMapper userMapper;

    //모든 사용자의 정보(DB로부터 읽어온 domain.user를 userDTO로 반환
    public List<UserDTO> getAllUsers(){
        //1.모든 domain.user객체 가져옴
        List<User> users = userMapper.findAll(); //서비스계층 > 매퍼 계층

        //2 새로운 DTO객체 생성
        List<UserDTO> userDTOs = new ArrayList<>();

        //3. 반복문을 아용해 각 User객체를 UserDTO로 변환하고 리스트에 추가
        for(User user:users){
            UserDTO userDTO = convertToDto(user);
            userDTOs.add(userDTO);
        }

        //4. UserDTO리스트 반환
        return userDTOs;
    }

    //특정 ID의 사용자 정보를 UserDTO로 반환
    public UserDTO getUserById(Long id){
        User user = userMapper.findById(id);

        return convertToDto(user);
    }
    //새 사용자 생성 코드
    public void createUser(UserDTO userDTO){
        User user  = convertToEntity(userDTO); //dto > domain
        userMapper.insert(user); //domain기반 mapper에게 insert요청
    }

    //사용자 정보 업데이트
    public void updateUser(UserDTO userDTO){
        User user = convertToEntity(userDTO);
        userMapper.update(user);
    }

    public void deleteUser(Long id){
        userMapper.delete(id);
    }

    //domain > dto 변환
    private UserDTO convertToDto(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setNo((int)(user.getId()+100));

        return dto;
    }

    //dto > domain
    private User convertToEntity(UserDTO dto){
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        return user;
    }

    //domain.User와 dto.UserDTO를 서로 반환하는 이유
    //domain.User : 데이터베이스 엔티티 표현
    //dto.User : 클라이언트와 데이터 전송에 사용
    //DTO를 사용하게 되면 클라이언트의 요구사항에 맞춰서 구조를 쉽게 변경
    //=>필요한 데이터만 클라이언트에 전송해서 네트워크 부하도 줄일 수 있음
    //=>API버전 관리 용이(엔티티(도메인) 변경 시 DTO를  통해 이전 버전과의 호환성 유지 가능)
}
