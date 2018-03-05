package org.redrock.christmas.dao;

import org.apache.ibatis.annotations.Param;
import org.redrock.christmas.domain.User;
import java.util.List;

public interface UserDao {

    boolean add(@Param("data") User user);

    User find(String openid);

    boolean update(@Param("data") User data);

    List getRank();

    int getUserRank(String openid);
}
