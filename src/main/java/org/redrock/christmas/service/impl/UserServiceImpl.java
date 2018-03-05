package org.redrock.christmas.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.redrock.christmas.dao.UserDao;
import org.redrock.christmas.domain.User;
import org.redrock.christmas.service.UserService;
import org.redrock.christmas.util.DatabaseUtil;
import org.redrock.christmas.util.DateUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.redrock.christmas.util.StringUtil.urlDecode;
import static org.redrock.christmas.util.StringUtil.urlEncode;

public class UserServiceImpl implements UserService {

    public void updateUser(String openid, String nickname, String imgurl) {
        SqlSession sqlSession = DatabaseUtil.getSqlsession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = userDao.find(openid);
        if (user == null) {
            user = new User();
            user.setOpenid(openid);
            user.setNickname(urlEncode(nickname));
            user.setShare(0);
            user.setCount(40);
            user.setImgurl(imgurl);
            user.setScore(0);
            userDao.add(user);
        } else if (user.getDate().equalsIgnoreCase(DateUtil.getdate())) {
            User updateUser = new User();
            updateUser.setOpenid(openid);
            updateUser.setCount(40);
            updateUser.setDate(DateUtil.getdate());
            updateUser.setScore(user.getScore());
            userDao.update(updateUser);
        }
        sqlSession.close();
    }

    @Override
    public User findUser(String openid) {
        SqlSession sqlSession = DatabaseUtil.getCachedSqlsession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = userDao.find(openid);
        sqlSession.close();
        return user;
    }

    @Override
    public int getUserRank(String openid) {
        SqlSession sqlSession = DatabaseUtil.getCachedSqlsession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        int rank = userDao.getUserRank(openid);
        sqlSession.close();
        return rank;
    }

    @Override
    public Map getRank() {
        SqlSession sqlSession = DatabaseUtil.getCachedSqlsession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> users = userDao.getRank();
        sqlSession.close();
        Map result = new HashMap();
        for (int i = 0; i < users.size(); i++) {
            Map item = new HashMap();
            User user = users.get(i);
            item.put("imgurl", user.getImgurl());
            item.put("nickname", urlDecode(user.getNickname()));
            item.put("rank", i+1);
            result.put((i+1) + "", item);
        }
        return result;
    }

    @Override
    public void saveScore(User user) {
        SqlSession sqlSession = DatabaseUtil.getSqlsession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setScore(user.getScore());
        updateUser.setCount(user.getCount());
        userDao.update(updateUser);
        sqlSession.close();
    }

    @Override
    public void updateShare(User user) {
        SqlSession sqlSession = DatabaseUtil.getSqlsession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User updateUser = new User();
        updateUser.setShare(user.getShare() + 1);
        updateUser.setCount(user.getCount() + 10);
        updateUser.setId(user.getId());
        updateUser.setScore(user.getScore());
        userDao.update(updateUser);
        sqlSession.close();
    }
}