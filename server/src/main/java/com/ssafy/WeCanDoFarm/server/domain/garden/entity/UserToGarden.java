package com.ssafy.WeCanDoFarm.server.domain.garden.entity;

import com.ssafy.WeCanDoFarm.server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="user_to_garden")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserToGarden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_to_garden_id")
    private Long userToGardenId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garden_id", nullable = false)
    private Garden garden;

    @Column(name="garden_user_type")
    @Enumerated(EnumType.STRING)
    private GardenUserType gardenUserType;

    public static UserToGarden create(User user, Garden garden,GardenUserType gardenUserType){
        UserToGarden userToGarden = new UserToGarden();
        userToGarden.user = user;
        userToGarden.garden = garden;
        userToGarden.gardenUserType = gardenUserType;
        return userToGarden;
    }
}
