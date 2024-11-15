package com.ssafy.WeCanDoFarm.server.domain.crop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.WeCanDoFarm.server.domain.crop.entitiy.QCrop.crop;
import static com.ssafy.WeCanDoFarm.server.domain.garden.entity.QGarden.garden;

@Repository
@RequiredArgsConstructor
public class CropQueryRepositoryImpl implements CropQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteCropByName(String CropName) {
        long deletedCount = queryFactory
                .delete(crop)
                .where(crop.cropName.eq(CropName))
                .execute();

        System.out.println("Deleted count: " + deletedCount);
    }

    @Override
    public List<String> getCropNamesByGardenId(Long gardenId) {
        return queryFactory.select(crop.cropName)
                .from(crop)
                .join(crop.garden, garden)
                .where(garden.gardenId.eq(gardenId))
                .fetch();
    }
}
