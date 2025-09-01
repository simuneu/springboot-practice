package spring.spring_boot_default.dto_vo;

public class UserExample {
    public static void main(String[] args) {
        UserDTO u1 = new UserDTO();
        u1.setId(1L);
        u1.setUsername("lee");
        u1.setEmail("lee@example.com");
        u1.setAge(20);
        System.out.println("u1="+u1);

        UserDTO u2 = new UserDTO(2L, "park", "park@gmail.com", 21);
        System.out.println("u2="+u2);

        //getter를 이용한 특정 정보 접근
        System.out.println(u2.getUsername());
        System.out.println(u2.getEmail());

        u2.setAge(30);
        System.out.println(u2.getAge());

    }
}
