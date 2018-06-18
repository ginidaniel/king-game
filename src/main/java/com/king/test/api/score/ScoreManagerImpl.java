package com.king.test.api.score;

import com.king.test.api.model.UserScore;

import java.util.*;
import java.util.stream.Collectors;

public class ScoreManagerImpl implements ScoreManager {

    private List<UserScore> userScores = new LinkedList<>();
    private Map<Integer, Map<String, UserScore>> highestScores = new HashMap<>();

    @Override
    public synchronized void submitScore(UserScore userScore) {
        userScores.add(userScore);

        Map<String, UserScore> mapLevel = highestScores.get(userScore.getLevel());
        if (mapLevel==null) {
            mapLevel = new HashMap<>();
            mapLevel.put(userScore.getUsername(), userScore);
            highestScores.put(userScore.getLevel(), mapLevel);
        }

        UserScore us = mapLevel.get(userScore.getUsername());
        if (us==null || us.getScore()<userScore.getScore()) mapLevel.put(userScore.getUsername(),userScore);
    }

    @Override
    public List<UserScore> getTopHighSocores(int level, Comparator<UserScore> comparator, int limit) {
        Map<String, UserScore> highestLevel = highestScores.get(level);
        if (highestLevel==null) return new ArrayList<>();

        List<UserScore> result = (comparator!=null)?highestLevel.values().stream().sorted(comparator).collect(Collectors.toList()):new ArrayList<>(highestLevel.values());

        return (limit!=0&&result.size()>limit)?result.subList(0,limit):result;
    }

}
