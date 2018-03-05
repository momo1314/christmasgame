package org.redrock.christmas.domain;

import lombok.Data;
/**
 * @author momo
 * 用户实体类
 */
@Data
public class User {
    String openid;
    String nickname;
    int score;
    String imgurl;
    int count;
    String date;
    int share;
    String rank;
    int id;
}
