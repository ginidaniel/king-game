package com.king.test.api.comparators;

import com.king.test.api.model.UserScore;

import java.util.Comparator;

public class ScoreComparator implements Comparator<UserScore> {

    @Override
    public int compare(UserScore u1, UserScore u2) {
        if (u1.getScore()>u2.getScore())
            return -1;
        if (u2.getScore()>u1.getScore())
            return 1;
        return 0;
    }
}
