package com.king.test.api.format;

import com.king.test.api.model.UserScore;

import java.util.List;

public interface FormatManager {

    /**
     * Converts the list into a String.
     * @param userScoreList
     * @return a string formatted for the list given.
     */
    String format(List<UserScore> userScoreList);

}
