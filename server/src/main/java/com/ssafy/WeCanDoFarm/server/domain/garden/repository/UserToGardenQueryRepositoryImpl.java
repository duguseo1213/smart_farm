package com.ssafy.WeCanDoFarm.server.domain.garden.repository;

import com.ssafy.WeCanDoFarm.server.domain.garden.dto.GetUserFromGardenRequest;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.GetUserFromGardenResponse;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.UserToGarden;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class UserToGardenQueryRepositoryImpl implements UserToGardenQueryRepository{
    private final EntityManager em;
    @Override
    public List<UserToGarden> findByGardenId(Long gardenId) {
        String jpql = "SELECT u FROM UserToGarden u WHERE u.garden.gardenId = :gardenId";
        Query query = em.createQuery(jpql, UserToGarden.class);
        query.setParameter("gardenId", gardenId);
        List<UserToGarden> response = query.getResultList();
        return response;
    }
}
