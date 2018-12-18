package com.github.zhangkaitao.shiro.spring;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Title: RedisSessionDao</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: www.hanshow.com</p>
 *
 * @author guolin
 * @version 1.0
 * @date 2018-12-18 15:42
 */
@Component
public class RedisSessionDao extends AbstractSessionDAO {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisManager redisManager;

    /**
     * The Redis key prefix for the sessions
     */
    private static final String KEY_PREFIX = "shiro_redis_session:";

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        redisManager.del(KEY_PREFIX + session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();
        Set<byte[]> keys = redisManager.keys(KEY_PREFIX + "*");
        if (keys != null && keys.size() > 0) {
            for (byte[] key : keys) {
                Session s = (Session) SerializeUtils.deserialize(redisManager.get(SerializeUtils.deserialize(key)));
                sessions.add(s);
            }
        }
        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            logger.error("session id is null");
            return null;
        }

        Session s = (Session) redisManager.get(KEY_PREFIX + sessionId);
        return s;
    }

    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        //设置过期时间
        long expireTime = 1800000l;
        session.setTimeout(expireTime);
        redisManager.setEx(KEY_PREFIX + session.getId(), session, expireTime);
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }
}