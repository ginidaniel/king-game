package com.king.test.api.login;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Random;

public class SessionManagerImpl implements SessionManager {

    private HashMap<String, Long> sessionKeys;
    private long timeout;

    public SessionManagerImpl(long timeout) {
        this.timeout = timeout;
        sessionKeys = new HashMap<>();
    }

    @Override
    public String generateSessionKey() {
        long nano = LocalTime.now().toNanoOfDay();
        Random r = new Random();
        String sessionKey = r.ints(nano).toString();
        sessionKeys.put(sessionKey, nano);
        return sessionKey;
    }

    @Override
    public boolean validSessionKey(String sessionKey) {
        Long nano = sessionKeys.get(sessionKey);
        if (nano==null) return false;

        long difference = LocalTime.now().toNanoOfDay() - nano;
        if (difference<=timeout) return true;

        sessionKeys.remove(sessionKey);
        return false;
    }
}
