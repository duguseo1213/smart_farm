package com.ssafy.WeCanDoFarm.server.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import com.ssafy.WeCanDoFarm.server.core.entity.BaseEntity;
import java.util.Date;

@Entity
@Getter
@Table(name="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="gender")
    private char gender;

    @Column(name="age")
    private Integer age;

    @Column(name="name")
    private String name;

    @Column(name="nickname")
    private String nickname;

    @Column(name="email")
    private String email;

    @Column(name = "provider_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Column(name="role_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType = RoleType.USER;

    @Column(name="provider_id")
    private String providerId;

    @Column(name="profile_image")
    private String profileImage;

    @Column(name="fcm_token")
    private String fcmToken;


    public static User createUser(String username, String name, char gender, ProviderType providerType,
                                  String providerId, String profileImage) {
        User user = new User();
        user.username = username;
        user.name = name;
        user.nickname = name;
        user.email = username;
        user.gender = gender;
        user.providerType = providerType;
        user.providerId = providerId;
        user.profileImage = profileImage;
        return user;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
