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

@WebServlet(name = "Save" , value = "/servlet/save")
public class SaveUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserService userService = new UserServiceImpl();
        String openid = (String) session.getAttribute("openid");
        int score = Integer.parseInt(req.getParameter("score"));
        User user = userService.findUser(openid);
        user.setCount(user.getCount()-1);
        System.out.println(user.getCount());
        if (user.getScore() < score) user.setScore(score);
        userService.saveScore(user);
        int rank = userService.getUserRank(openid);
        Map result = new HashMap();
        Map body = new HashMap();
            body.put("count", user.getCount());
            body.put("imgurl", user.getImgurl());
            body.put("nickname", URLDecoder.decode(user.getNickname(),"UTF-8"));
            body.put("rank", rank);
        result.put("status", 200);
        result.put("msg", "ok");
        result.put("data", body);
        JsonUtil.json(resp, result);
    }
}
