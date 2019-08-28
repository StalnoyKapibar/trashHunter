package org.bootcamp.trashhunter.controllers.rest;

import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.Vote;
import org.bootcamp.trashhunter.services.abstraction.UserService;
import org.bootcamp.trashhunter.services.abstraction.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/vote")
public class VoteRestController {

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserService userService;

    @GetMapping("/user/{userId}/vote/{vote}")
    public int vote(@PathVariable Long userId, @PathVariable boolean vote, Principal principal) {
        String email = principal.getName();
        User userFrom = userService.findByEmail(email);
        Vote previousVote = voteService.getByUserFromIdAndUserToId(userFrom.getId(), userId);
        if (previousVote == null) {
            voteService.add(new Vote(userFrom.getId(), userId, vote));
        } else {
            previousVote.setVote(vote);
            voteService.update(previousVote);
        }
        return getUserLimit(userId);
    }

    private int getUserLimit(long userIdTo) {
        return voteService.getLimit(userIdTo);
    }


}
