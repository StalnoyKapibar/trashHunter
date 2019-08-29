package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.abstraction.UserDao;
import org.bootcamp.trashhunter.dao.abstraction.VoteDao;
import org.bootcamp.trashhunter.models.Vote;
import org.bootcamp.trashhunter.services.AbstractServiceImpl;
import org.bootcamp.trashhunter.services.abstraction.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VoteServiceImpl extends AbstractServiceImpl<Vote> implements VoteService {

    @Autowired
    private VoteDao voteDaoImpl;

    @Autowired
    private UserDao userDao;

    @Override
    public Vote getByUserFromIdAndUserToId(long userFromId, long userToId) {
        return voteDaoImpl.getByUserFromIdAndUserToId(userFromId, userToId);
    }

    @Override
    public List<Vote> countVotes(long userToId) {
        return voteDaoImpl.countVotes(userToId);
    }

    @Override
    public long[] getCountLikeDislikeByUser(long userToId) {
        List<Vote> votes = voteDaoImpl.countVotes(userToId);
        long countLike = votes.stream().filter(Vote::isVote).count();
        long countDislike = votes.stream().filter(vote -> !vote.isVote()).count();
        long[] countLikeDislike = new long[2];
        countLikeDislike[0] = countLike;
        countLikeDislike[1] = countDislike;
        return countLikeDislike;
    }

    @Override
    public int getLimit(long userToId) {
        List<Vote> votes = voteDaoImpl.countVotes(userToId);
        if (votes.size() >= 4) {
            long countLike = votes.stream().filter(Vote::isVote).count();
            long countDislike = votes.stream().filter(vote -> !vote.isVote()).count();
            if (countDislike > 0 && countLike / countDislike <= 1 / 3) {
                setLimitToUser(userToId, 2);
                return 2;
            } else if (countLike == countDislike) {
                setLimitToUser(userToId, 1);
                return 1;
            }
        }
        return 0;
    }

    private void setLimitToUser(long userToId, int limit) {
        userDao.setLimit(userToId, limit);
    }
}
