package com.ssafy.WeCanDoFarm.server.domain.garden.repository;

import com.ssafy.WeCanDoFarm.server.domain.garden.dto.RegisterGardenRequest;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GardenQueryRepositoryImpl implements GardenQueryRepository{
    private final EntityManager em;


    @Override
    public List<Garden> getGardens(String username){
//        String jpql = "SELECT g FROM Garden g WHERE g.gardenId IN (SELECT utg.garden.id FROM UserToGarden utg WHERE utg.user.username = :username)";
//
//        Query query = em.createQuery(jpql, Garden.class);
//        query.setParameter("username", username);
//        List<Garden> response = query.getResultList();
        return null;
    }


}
