package soma.everyonepick.api.user.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String nickname;

    @Column(unique = true, nullable = false)
    private String clientId;

    @Column
    private String thumbnailImageUrl;

    @Column
    private String fcmDeviceToken;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isPushActive = true;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isRegistered = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
