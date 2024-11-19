package com.ssafy.WeCanDoFarm.server.domain.timeLapse.entitiy;

import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Table(name = "time_lapse")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TimeLapse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeLapseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garden_id")
    private Garden garden;

    @Column(name = "time_lapse_image")
    private String timeLapseImage;


    @Column(name = "created_date")
    @CreatedDate
    private Date createdDate;

    public static TimeLapse create(Garden garden, String timeLapseImage) {
        TimeLapse timeLapse = new TimeLapse();
        timeLapse.garden = garden;
        timeLapse.timeLapseImage = timeLapseImage;
        timeLapse.createdDate = new Date();
        return timeLapse;
    }
}
