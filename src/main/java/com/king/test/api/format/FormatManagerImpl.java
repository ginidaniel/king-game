package com.king.test.api.format;

import com.king.test.api.model.UserScore;

import java.util.List;

public class FormatManagerImpl implements FormatManager {

    @Override
    public String format(List<UserScore> userScoreList) {
        return userScoreList.stream().map(u -> u.getUsername() + "-" + u.getScore()).reduce((a, b) -> a + ";" + b).orElse("");
    }

}
