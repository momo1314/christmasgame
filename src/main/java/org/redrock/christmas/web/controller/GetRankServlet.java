package org.redrock.christmas.web.controller;

import org.redrock.christmas.domain.User;
import org.redrock.christmas.service.UserService;
import org.redrock.christmas.service.impl.UserServiceImpl;
import org.redrock.christmas.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "Rank" , value = "/servlet/getrank")
public class GetRankServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String openid = (String) session.getAttribute("openid");
        UserService userService = new UserServiceImpl();
        User user = userService.findUser(openid);
        int rank = userService.getUserRank(openid);
        //所有人的排名
        Map ranks = userService.getRank();
        //个人排名
        Map my = new HashMap();
            my.put("nickname", URLDecoder.decode(user.getNickname(), "UTF-8"));
            my.put("imgurl", user.getImgurl());
            my.put("rank", rank);
        ranks.put("my", my);
        Map result = new HashMap();
            result.put("status", 200);
            result.put("msg", "ok");
            result.put("data", ranks);
        JsonUtil.json(resp, result);
    }
}
