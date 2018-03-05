package org.redrock.christmas.service;
import org.redrock.christmas.domain.User;
import java.util.Map;

public interface UserService {
    void updateUser(String openid, String nickname, String imgurl);

    User findUser(String openid);

    int getUserRank(String openid);

    Map getRank();

    void saveScore(User user);

    void updateShare(User user);
}
