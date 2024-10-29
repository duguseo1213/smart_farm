package com.ssafy.WeCanDoFarm.server.domain.harm.entity;

import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Table(name = "harm_picture")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HarmPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="harm_picture_id")
    private Long harmPictureId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="garden_id")
    private Garden garden;

    @Column(name="harm_picture")
    private String harmPicture;

    @Column(name="created_date")
    @CreatedDate
    private Date createdDate;

    @Column(name="harm_target")
    private String harmTarget;

    public static HarmPicture crate(Garden garden, String harmPicture, String harmTarget) {
        HarmPicture picture = new HarmPicture();
        picture.garden = garden;
        picture.harmPicture = harmPicture;
        picture.harmTarget = harmTarget;
        return picture;
    }
}
