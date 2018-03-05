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

@WebServlet(name = "userinfo" , value = "/servlet/userinfo")
public class UserInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserService userService = new UserServiceImpl();
        String openid = (String) session.getAttribute("openid");
        User user = userService.findUser(openid);
        Map body = new HashMap();
        body.put("nickname", URLDecoder.decode(user.getNickname(), "UTF-8"));
        body.put("rank", 0);
        body.put("share", 0);
        //由于前端先是调用了用户信息接口，再保存成绩，故此处需要对总次数减1
        body.put("count", user.getCount()-1);
        body.put("imgurl", user.getImgurl());
        Map data = new HashMap();
        data.put("status", 200);
        data.put("msg", "ok");
        data.put("data", body);
        JsonUtil.json(resp, data);
    }
}