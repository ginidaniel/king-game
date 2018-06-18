package com.king.test.api.login;

public interface SessionManager {

    /**
     * Generates a session key
     * @return sessionKey
     */
    String generateSessionKey();

    /**
     * Given a session key, it returns if this session key was generated and still valid.
     * @param sessionKey
     * @return valid or not
     */
    boolean validSessionKey(String sessionKey);
}
