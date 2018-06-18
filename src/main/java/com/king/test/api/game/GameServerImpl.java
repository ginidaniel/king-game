package com.king.test.api.game;

import com.king.test.api.comparators.ScoreComparator;
import com.king.test.api.exception.InvalidSessionException;
import com.king.test.api.model.UserScore;
import com.king.test.api.format.FormatManager;
import com.king.test.api.format.FormatManagerImpl;
import com.king.test.api.login.SessionManager;
import com.king.test.api.login.SessionManagerImpl;
import com.king.test.api.score.ScoreManager;
import com.king.test.api.score.ScoreManagerImpl;

import java.util.List;

public class GameServerImpl implements GameServer {

    private SessionManager sessionManager;
    private ScoreManager scoreManager;
    private FormatManager formatManager;
    private static final long TIMEOUT = 100000000;
    private static final int MAX_LENGTH = 15;

    public GameServerImpl() {
        init(TIMEOUT);
    }

    public GameServerImpl(long timeout) {
        init(timeout);
    }

    private void init(long timeout) {
        sessionManager = new SessionManagerImpl(timeout);
        scoreManager = new ScoreManagerImpl();
        formatManager = new FormatManagerImpl();
    }

    @Override
    public String login(String username) {
        return sessionManager.generateSessionKey();
    }

    @Override
    public void submitScore(String sessionKey, UserScore userScore) throws InvalidSessionException {
        if (!sessionManager.validSessionKey(sessionKey))
            throw new InvalidSessionException();

        scoreManager.submitScore(userScore);
    }

    @Override
    public String getHighScores(String sessionKey, int level) throws InvalidSessionException {
        if (!sessionManager.validSessionKey(sessionKey))
            throw new InvalidSessionException();

        List<UserScore> highScores = scoreManager.getTopHighSocores(level, new ScoreComparator(), MAX_LENGTH);

        return formatManager.format(highScores);
    }
}
