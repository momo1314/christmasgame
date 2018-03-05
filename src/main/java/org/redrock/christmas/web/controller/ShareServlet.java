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

@WebServlet(name = "share" , value = "/servlet/share")
public class ShareServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String openid = (String) session.getAttribute("openid");
        UserService userService = new UserServiceImpl();
        User user = userService.findUser(openid);
        userService.updateShare(user);
        int rank = userService.getUserRank(openid);
        Map result = new HashMap();
        Map body = new HashMap();
            body.put("nickname", URLDecoder.decode(user.getNickname(),"UTF-8"));
            body.put("imgurl", user.getImgurl());
            body.put("count", user.getCount() + 10);
            body.put("rank", rank);
        result.put("status", 200);
        result.put("msg", "ok");
        result.put("data", body);
        JsonUtil.json(resp, result);
    }
}
