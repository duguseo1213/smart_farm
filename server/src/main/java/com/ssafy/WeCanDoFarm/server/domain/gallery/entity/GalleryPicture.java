package com.ssafy.WeCanDoFarm.server.domain.gallery.entity;

import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Getter
@Table(name="gallery_picture")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GalleryPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="gallery_picture_id")
    private Long galleryPictureId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garden_id")
    private Garden garden;

    @Column(name="gallery_image")
    private String galleryImage;

    @Column(name="gallery_image_description")
    private String galleryImageDescription;

    @CreatedDate
    @Column(name = "crated_date")
    private Date createdDate;

    public static GalleryPicture create(Garden garden, String galleryImage, String galleryImageDescription) {
        GalleryPicture fp = new GalleryPicture();
        fp.garden = garden;
        fp.galleryImage = galleryImage;
        fp.galleryImageDescription = galleryImageDescription;
        fp.createdDate = new Date();
        return fp;
    }
}
