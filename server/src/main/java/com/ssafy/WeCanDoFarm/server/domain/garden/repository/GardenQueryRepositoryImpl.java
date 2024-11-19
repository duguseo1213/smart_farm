package com.ssafy.WeCanDoFarm.server.domain.garden.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.WeCanDoFarm.server.domain.device.entity.QDevice.device;
import static com.ssafy.WeCanDoFarm.server.domain.garden.entity.QGarden.garden;

@Repository
@RequiredArgsConstructor
public class GardenQueryRepositoryImpl implements GardenQueryRepository{
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Garden> getGardens(String username){
        String jpql = "SELECT g FROM Garden g WHERE g.gardenId IN (SELECT utg.garden.id FROM UserToGarden utg WHERE utg.user.username = :username)";

        Query query = em.createQuery(jpql, Garden.class);
        query.setParameter("username", username);
        List<Garden> response = query.getResultList();
        return response;
    }
    @Override
    public Garden getGarden(Long deviceId){
        return queryFactory.select(garden)
                .from(garden)
                .join(garden.device,device)
                .where(device.deviceId.eq(deviceId))
                .fetchOne();

    }

}
