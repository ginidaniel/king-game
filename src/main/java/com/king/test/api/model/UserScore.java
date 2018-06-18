package com.king.test.api.model;

import java.util.Objects;

public class UserScore {

    private final String username;
    private final int level;
    private final int score;

    public UserScore(String username, int level, int score) {
        this.username = username;
        this.level = level;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserScore userScore = (UserScore) o;
        return level == userScore.level &&
            score == userScore.score &&
            Objects.equals(username, userScore.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, level, score);
    }
}
