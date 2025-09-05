package spring.spring_boot_jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 고유한 번호를 자동 생성/ AUTO: 데이터베이스의 설정에 따라 적절한 전략을 자동으로 선택
    private Long id;

    @Column(nullable = false, length = 59)
    private String username;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { //엔티티가 데이터베이스에 저장되기 전에 필요한 초기화 작업 수행
        //엔티티가 처음 저장될 떄 createdAt필드에 현재 시각 저장
        //메서드 이름은 자유롭게 설정 가능(메서드 타입은 void, 매개변수없음)
        createdAt = LocalDateTime.now();
    }
}
