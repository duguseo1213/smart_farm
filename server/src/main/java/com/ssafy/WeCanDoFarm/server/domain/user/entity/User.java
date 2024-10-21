package com.ssafy.WeCanDoFarm.server.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Getter
@Table(name="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name="age")
    private Integer age;

    @Column(name="name")
    private String name;

    @Column(name="nickname")
    private String nickname;

    @Column(name="email")
    private String email;

    @Column(name="role_type")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(name="provider_id")
    private String providerId;

    @Column(name="profile_image")
    private String profileImage;

    @Column(name="fcm_token")
    private String fcmToken;

    @Column(name="created_date")
    @CreatedDate
    private Date createdDate;

    public static User create(String username, Gender gender, Integer age, String name, String nickname, String email, RoleType roleType, String providerId, String profileImage) {
        User user = new User();
        user.username = username;
        user.gender = gender;
        user.age = age;
        user.name = name;
        user.nickname = nickname;
        user.email = email;
        user.roleType = roleType;
        user.providerId = providerId;
        user.profileImage = profileImage;
        return user;
    }
}
