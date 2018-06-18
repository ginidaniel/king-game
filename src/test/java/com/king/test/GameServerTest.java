package com.king.test;

import com.king.test.api.game.GameServer;
import com.king.test.api.game.GameServerImpl;
import com.king.test.api.exception.InvalidSessionException;
import com.king.test.api.model.UserScore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameServerTest {

    @Test
    void simpleLoginTest() {
        GameServer gameServer = new GameServerImpl();

        String u1 = "userName1";
        String sessionKey = gameServer.login(u1);

        assertNotEquals(sessionKey, u1);
    }

    @Test
    void doubleLoginTest() {
        GameServer gameServer = new GameServerImpl();

        String u1 = "userName1";
        String sessionKey1 = gameServer.login(u1);
        String u2 = "userName2";
        String sessionKey2 = gameServer.login(u2);

        assertNotEquals(sessionKey1, sessionKey2);
    }

    @Test
    void submitInvalidScoreTimeoutTest() {
        GameServer gameServer = new GameServerImpl(1);

        String u1 = "userName1";
        String sessionKey1 = gameServer.login(u1);

        UserScore userScore = new UserScore(u1, 1, 56);
        try {
            gameServer.submitScore(sessionKey1, userScore);
            assertTrue(false);
        } catch (InvalidSessionException ise) {
            assertTrue(true);
        }
    }

    @Test
    void submitValidScoreTest() {
        GameServer gameServer = new GameServerImpl();

        String u1 = "userName1";
        String sessionKey1 = gameServer.login(u1);

        UserScore userScore = new UserScore(u1, 1, 56);
        try {
            gameServer.submitScore(sessionKey1, userScore);
            assertTrue(true);
        } catch (InvalidSessionException ise) {
            assertTrue(false);
        }

    }

    @Test
    void submitInvalidScoreTest() {
        GameServer gameServer = new GameServerImpl();

        String u1 = "userName1";
        String sessionKey1 =  "sessionKey1";

        UserScore userScore = new UserScore(u1, 1, 56);

        try {
            gameServer.submitScore(sessionKey1, userScore);
            assertTrue(false);
        } catch (InvalidSessionException ise) {
            assertTrue(true);
        }

    }

    @Test
    void getEmptyHighScoreListTest() {
        GameServer gameServer = new GameServerImpl();

        String u1 = "userName1";
        String sessionKey1 = gameServer.login(u1);

        try {

            String highScores = gameServer.getHighScores(sessionKey1, 0);
            assertTrue(highScores.isEmpty());

        } catch (InvalidSessionException ise) {
            assertTrue(false);
        }
    }

    @Test
    void getHighScoreListTest_SingleScore() {
        GameServer gameServer = new GameServerImpl();

        String u1 = "userName1";
        String sessionKey1 = gameServer.login(u1);

        try {

            UserScore userScore = new UserScore(u1, 1, 56);
            gameServer.submitScore(sessionKey1, userScore);

            String highScores = gameServer.getHighScores(sessionKey1, 1);
            assertTrue(!highScores.isEmpty());

            assertEquals(1, highScores.split(";").length);

        } catch (InvalidSessionException ise) {
            assertTrue(false);
        }
    }

    @Test
    void getHighScoreListTest_2Scores() {
        GameServer gameServer = new GameServerImpl();

        String u1 = "userName1";
        String sessionKey1 = gameServer.login(u1);
        String u2 = "userName2";
        String sessionKey2 = gameServer.login(u2);

        try {

            UserScore userScore = new UserScore(u1, 1, 56);
            gameServer.submitScore(sessionKey1, userScore);
            userScore = new UserScore(u2, 1, 569);
            gameServer.submitScore(sessionKey2, userScore);

            String highScores = gameServer.getHighScores(sessionKey1, 1);
            assertTrue(!highScores.isEmpty());

            assertEquals(2, highScores.split(";").length);
            assertEquals("userName2-569;userName1-56", highScores);

        } catch (InvalidSessionException ise) {
            assertTrue(false);
        }
    }

    @Test
    void getHighScoreListTest_3Scores() {
        GameServer gameServer = new GameServerImpl();

        String u1 = "userName1";
        String sessionKey1 = gameServer.login(u1);
        String u2 = "userName2";
        String sessionKey2 = gameServer.login(u2);
        String u3 = "userName3";
        String sessionKey3 = gameServer.login(u3);

        try {

            UserScore userScore = new UserScore(u1, 1, 56);
            gameServer.submitScore(sessionKey1, userScore);
            userScore = new UserScore(u2, 1, 569);
            gameServer.submitScore(sessionKey2, userScore);
            userScore = new UserScore(u3, 1, 69);
            gameServer.submitScore(sessionKey3, userScore);

            String highScores = gameServer.getHighScores(sessionKey1, 1);
            assertTrue(!highScores.isEmpty());

            assertEquals(3, highScores.split(";").length);
            assertEquals("userName2-569;userName3-69;userName1-56", highScores);

        } catch (InvalidSessionException ise) {
            assertTrue(false);
        }
    }

    @Test
    void getHighScoreListTest_2ScoresSameUserDiffLevel() {
        GameServer gameServer = new GameServerImpl();

        String u1 = "userName1";
        String sessionKey1 = gameServer.login(u1);

        try {

            UserScore userScore = new UserScore(u1, 1, 56);
            gameServer.submitScore(sessionKey1, userScore);
            userScore = new UserScore(u1, 2, 569);
            gameServer.submitScore(sessionKey1, userScore);

            String highScores = gameServer.getHighScores(sessionKey1, 1);
            assertTrue(!highScores.isEmpty());
            assertEquals(1, highScores.split(";").length);

            highScores = gameServer.getHighScores(sessionKey1, 2);
            assertTrue(!highScores.isEmpty());
            assertEquals(1, highScores.split(";").length);

        } catch (InvalidSessionException ise) {
            assertTrue(false);
        }
    }

    @Test
    void getHighScoreListTest_3ScoresSameUserAndLevel() {
        GameServer gameServer = new GameServerImpl();

        String u1 = "userName1";
        String sessionKey1 = gameServer.login(u1);

        try {

            UserScore userScore = new UserScore(u1, 1, 56);
            gameServer.submitScore(sessionKey1, userScore);
            userScore = new UserScore(u1, 1, 569);
            gameServer.submitScore(sessionKey1, userScore);
            userScore = new UserScore(u1, 1, 69);
            gameServer.submitScore(sessionKey1, userScore);

            String highScores = gameServer.getHighScores(sessionKey1, 1);
            assertTrue(!highScores.isEmpty());
            assertEquals(1, highScores.split(";").length);
            assertEquals("userName1-569", highScores);

        } catch (InvalidSessionException ise) {
            assertTrue(false);
        }
    }

    @Test
    void getHighScoreListTest_DiffScoresAndLeveles() {
        GameServer gameServer = new GameServerImpl();

        String u1 = "userName1";
        String sessionKey1 = gameServer.login(u1);
        String u2 = "userName2";
        String sessionKey2 = gameServer.login(u2);

        try {

            UserScore userScore = new UserScore(u1, 1, 56);
            gameServer.submitScore(sessionKey1, userScore);
            userScore = new UserScore(u1, 1, 569);
            gameServer.submitScore(sessionKey1, userScore);
            userScore = new UserScore(u1, 0, 69);
            gameServer.submitScore(sessionKey1, userScore);
            userScore = new UserScore(u1, 0, 59);
            gameServer.submitScore(sessionKey1, userScore);

            userScore = new UserScore(u2, 1, 564);
            gameServer.submitScore(sessionKey2, userScore);
            userScore = new UserScore(u2, 0, 789);
            gameServer.submitScore(sessionKey2, userScore);
            userScore = new UserScore(u2, 1, 19);
            gameServer.submitScore(sessionKey2, userScore);
            userScore = new UserScore(u2, 2, 39);
            gameServer.submitScore(sessionKey2, userScore);

            String highScores = gameServer.getHighScores(sessionKey1, 0);
            assertTrue(!highScores.isEmpty());
            assertEquals(2, highScores.split(";").length);
            assertEquals("userName2-789;userName1-69", highScores);

            highScores = gameServer.getHighScores(sessionKey1, 1);
            assertTrue(!highScores.isEmpty());
            assertEquals(2, highScores.split(";").length);
            assertEquals("userName1-569;userName2-564", highScores);

            highScores = gameServer.getHighScores(sessionKey1, 2);
            assertTrue(!highScores.isEmpty());
            assertEquals(1, highScores.split(";").length);
            assertEquals("userName2-39", highScores);

            highScores = gameServer.getHighScores(sessionKey1, 3);
            assertTrue(highScores.isEmpty());


        } catch (InvalidSessionException ise) {
            assertTrue(false);
        }
    }

    @Test
    void getHighScoreListTest_MaxLimit15() {
        GameServer gameServer = new GameServerImpl();

        String u1 = "userName1";
        String sessionKey = gameServer.login(u1);
        String u2 = "userName2";
        gameServer.login(u2);
        String u3 = "userName3";
        gameServer.login(u3);
        String u4 = "userName4";
        gameServer.login(u4);
        String u5 = "userName5";
        gameServer.login(u5);
        String u6 = "userName6";
        gameServer.login(u6);
        String u7 = "userName7";
        gameServer.login(u7);
        String u8 = "userName8";
        gameServer.login(u8);
        String u9 = "userName9";
        gameServer.login(u9);
        String u10 = "userName10";
        gameServer.login(u10);
        String u11 = "userName11";
        gameServer.login(u11);
        String u12 = "userName12";
        gameServer.login(u12);
        String u13 = "userName13";
        gameServer.login(u13);
        String u14 = "userName14";
        gameServer.login(u14);
        String u15 = "userName15";
        gameServer.login(u15);
        String u16 = "userName16";
        gameServer.login(u16);

        try {

            UserScore userScore = new UserScore(u1, 1, 56);
            gameServer.submitScore(sessionKey, userScore);
            userScore = new UserScore(u2, 1, 569);
            gameServer.submitScore(sessionKey, userScore);
            userScore = new UserScore(u3, 1, 569);
            gameServer.submitScore(sessionKey, userScore);
            userScore = new UserScore(u4, 1, 569);
            gameServer.submitScore(sessionKey, userScore);
            userScore = new UserScore(u5, 1, 569);
            gameServer.submitScore(sessionKey, userScore);
            userScore = new UserScore(u6, 1, 569);
            gameServer.submitScore(sessionKey, userScore);
            userScore = new UserScore(u7, 1, 569);
            gameServer.submitScore(sessionKey, userScore);
            userScore = new UserScore(u8, 1, 569);
            gameServer.submitScore(sessionKey, userScore);
            userScore = new UserScore(u9, 1, 569);
            gameServer.submitScore(sessionKey, userScore);
            userScore = new UserScore(u10, 1, 569);
            gameServer.submitScore(sessionKey, userScore);
            userScore = new UserScore(u11, 1, 569);
            gameServer.submitScore(sessionKey, userScore);
            userScore = new UserScore(u12, 1, 569);
            gameServer.submitScore(sessionKey, userScore);
            userScore = new UserScore(u13, 1, 569);
            gameServer.submitScore(sessionKey, userScore);
            userScore = new UserScore(u14, 1, 569);
            gameServer.submitScore(sessionKey, userScore);
            userScore = new UserScore(u15, 1, 569);
            gameServer.submitScore(sessionKey, userScore);
            userScore = new UserScore(u16, 1, 569);
            gameServer.submitScore(sessionKey, userScore);

            String highScores = gameServer.getHighScores(sessionKey, 1);
            assertTrue(!highScores.isEmpty());
            assertEquals(15, highScores.split(";").length);

        } catch (InvalidSessionException ise) {
            assertTrue(false);
        }
    }
}
