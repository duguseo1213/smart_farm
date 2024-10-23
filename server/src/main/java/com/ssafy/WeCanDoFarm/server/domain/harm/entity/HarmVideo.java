package com.ssafy.WeCanDoFarm.server.domain.harm.entity;

import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Table(name = "harm_video")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HarmVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="harm_video_id")
    private Long harmVideoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="harm_picture_id")
    private HarmPicture harmPicture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="garden_id")
    private Garden garden;

    @Column(name="harm_video")
    private String harmVideo;

    @CreatedDate
    @Column(name="created_Date")
    private Date createdDate;

    public static HarmVideo create(HarmPicture harmPicture, Garden garden, String harmVideo) {
        HarmVideo video = new HarmVideo();
        video.harmPicture = harmPicture;
        video.garden = garden;
        video.harmVideo = harmVideo;
        return video;
    }
}
