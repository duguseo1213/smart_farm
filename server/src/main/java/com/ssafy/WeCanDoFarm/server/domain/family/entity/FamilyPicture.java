package com.ssafy.WeCanDoFarm.server.domain.family.entity;

import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Getter
@Table(name="family_picture")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FamilyPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="family_picture_id")
    private Long familyPictureId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "garden_id")
    private Garden garden;

    @Column(name="family_image")
    private String familyImage;

    @CreatedDate
    @Column(name = "crated_date")
    private Date createdDate;

    public static FamilyPicture create(Garden garden, String familyImage) {
        FamilyPicture fp = new FamilyPicture();
        fp.garden = garden;
        fp.familyImage = familyImage;
        return fp;
    }
}
