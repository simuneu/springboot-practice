package spring.spring_boot_jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="board")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @Column(nullable = false , length = 30)
    private String title;

    @Column(nullable = false, length = 100)
    private String content;

    @Column(nullable = false , length = 30)
    private String writer;

    @Column(name="created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime  createdAt;

    @PrePersist
    protected void  onCreate(){
        createdAt = LocalDateTime.now();
    }
}
