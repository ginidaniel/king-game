package com.king.test.api.score;

import com.king.test.api.model.UserScore;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface ScoreManager {

    /**
     * Submits a user's score
     * @param userScore the user's score for the level
     */
    void submitScore(UserScore userScore);

    /**
     * Obtains the high scores for a given level.
     *
     * @param level the level of the top scores for
     * @param comparator to retrieve sorted elements
     * @param limit max size of the results returned (0 to retrieve all values)
     * @return a list with the highest scores per level.
     */
    List<UserScore> getTopHighSocores(int level, Comparator<UserScore> comparator, int limit);
}
