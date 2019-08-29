package org.bootcamp.trashhunter.dao.abstraction;

import org.bootcamp.trashhunter.models.Vote;

import java.util.List;

public interface VoteDao extends AbstractDao<Vote> {

    Vote getByUserFromIdAndUserToId(long userFromId, long userToId);

    List<Vote> countVotes(long userToId);
}
