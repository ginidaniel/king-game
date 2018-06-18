package com.king.test.api.game;

import com.king.test.api.exception.InvalidSessionException;
import com.king.test.api.model.UserScore;

/**
 * Basic game server interface
 */
public interface GameServer {

    /**
     * Generates a unique session key for a given user which is valid for 10 minutes.
     * @param username username of the user
     * @return session key
     */
    String login(String username);

    /**
     * Submits a user's score
     * @param sessionKey a valid session key
     * @param userScore the user's score for the level
     */
    void submitScore(String sessionKey, UserScore userScore) throws InvalidSessionException;

    /**
     * Obtains the high scores for a given level. The format should be a comma separated list of the top 15 scores
     * in descending order and with only one top score per user.
     *
     * @param sessionKey a valid session key
     * @param level the level you want to see the top scores for
     * @return a csv in a string format as described above
     */
    String getHighScores(String sessionKey, int level) throws InvalidSessionException;
}