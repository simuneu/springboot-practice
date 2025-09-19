package spring.spring_boot_jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.spring_boot_jpa.dto.UserDTO;
import spring.spring_boot_jpa.entity.User;
import spring.spring_boot_jpa.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //모든 사용자의 정보를 반환하는 getAllUsers() UserDTO 리스트로 반환
    public List<UserDTO> getAllUsers(){
        //1.repository 계층에서 모든 User리스트를 가져옴
        List<User> users = userRepository.findAll();

        //2. 새로운 DTO 객체 리스트 생성
        List<UserDTO> userDTOs = new ArrayList<>();

        //3. 반복문을 이용해 User객체를 UserDTO객체로 변환하고 리스트에 추가
        for(User user:users){
             UserDTO userDTO = convertToDTO(user);
             userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    public UserDTO getUserById(Long id){
        //JPA 기본 제공 findById메서드로 특정 유저를 찾을 때
        //있으면 User객체, 없으면 null
        User user = userRepository.findById(id).orElse(null);

        if(user == null){
            throw new RuntimeException("User not found");
        }

        return convertToDTO(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    //새 사용자 생성
    public void createUser(UserDTO userDTO){
        //1.클라이언트에게 추가해야 할 정보를 받음

        User user = convertToEntity(userDTO);

       //2. repository에게 추가 요청
        userRepository.save(user);
    }

    //사용자 정보 수정
    public void updateUser(Long id,UserDTO userDTO){
        User user = convertToEntityWithId(id, userDTO);
        userRepository.save(user);
    }
    ////////////////////////////////////////
    public List<UserDTO> getUserByUsername(String username){
        List<User> users = userRepository.findByUsername(username);
        List<UserDTO> userDTOS = new ArrayList<>();
        for(User user:users){
            userDTOS.add(convertToDTO(user));
        }
        return userDTOS;
    }

    public  List<UserDTO> searchUsers(String keyword){
//        List<User> users = userRepository.findByUsernameContainingOrEmailContaining(keyword, keyword);
        List<User> users = userRepository.findByUsernameContainingOrEmailContaining( keyword);
        List<UserDTO> userDTOS = new ArrayList<>();
        for(User user: users){
            userDTOS.add(convertToDTO(user));
        }
        return userDTOS;
    }

    public boolean isUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    //entity(domain) to dto
    private UserDTO convertToDTO(User user){
        // builder패턴을 이용해 DTO객체 생성
        return UserDTO.builder()
                .no((int)(user.getId()+100))
                .email(user.getEmail())
                .username(user.getUsername())
                .id(user.getId())
                .build();
    }

    private User convertToEntity(UserDTO dto){
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build();
    }

    //dto to entity with id
    private User convertToEntityWithId(Long id, UserDTO dto){
        return User.builder()
                .id(id)
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build();
    }
}
