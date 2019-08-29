package org.bootcamp.trashhunter.services.abstraction;

import org.bootcamp.trashhunter.models.Vote;

import java.util.List;

public interface VoteService extends AbstractService<Vote> {

    Vote getByUserFromIdAndUserToId(long userFromId, long userToId);

    List<Vote> countVotes(long userToId);

    int getLimit(long userToId);

    long[] getCountLikeDislikeByUser(long userToId);
}
