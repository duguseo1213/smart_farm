package com.ssafy.WeCanDoFarm.server.domain.garden.repository;

import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GardenRepositoryImpl implements GardenRepository{
    private final EntityManager em;

    @Override
    public List<Garden> getGardens(int userId) throws SQLException {
        String jpql = "SELECT g FROM Garden g WHERE g.gardenId IN (SELECT utg.garden.id FROM UserToGarden utg WHERE utg.user.id = :userId)";

        Query query = em.createQuery(jpql, Garden.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public void registerGarden(Garden garden) throws SQLException {

    }
}
