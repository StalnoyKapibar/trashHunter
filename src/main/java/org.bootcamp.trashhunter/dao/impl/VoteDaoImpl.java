package org.bootcamp.trashhunter.dao.impl;

import org.bootcamp.trashhunter.dao.abstraction.VoteDao;
import org.bootcamp.trashhunter.models.Vote;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository("voteDao")
public class VoteDaoImpl extends AbstractDAOImpl<Vote> implements VoteDao {

    @Override
    public Vote getByUserFromIdAndUserToId(long userFromId, long userToId) {
        Vote singleResult = null;
        try {
            singleResult = entityManager
                    .createQuery("SELECT v FROM Vote v WHERE v.userFromId =:userFromId AND v.userToId=:userToId", Vote.class)
                    .setParameter("userFromId", userFromId)
                    .setParameter("userToId", userToId)
                    .getSingleResult();
        } catch (NoResultException e) {
        }
        return singleResult;
    }

    @Override
    public List<Vote> countVotes(long userToId) {
        return entityManager
                    .createQuery("SELECT v FROM Vote v WHERE v.userToId=:userToId", Vote.class)
                    .setParameter("userToId", userToId)
                    .getResultList();
    }


}
