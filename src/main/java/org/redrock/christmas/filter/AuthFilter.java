package org.redrock.christmas.filter;
import org.redrock.christmas.service.UserService;
import org.redrock.christmas.service.impl.UserServiceImpl;
import org.redrock.christmas.util.PropertyUtil;
import org.redrock.christmas.util.StringUtil;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

import static org.redrock.christmas.util.StringUtil.urlDecode;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.jsp", "/index", "/servlet/*", "/"})
public class AuthFilter implements Filter {
    private String apiUrl = "https://wx.idsbllp.cn/MagicLoop/index.php?s=/addon/Api/Api/oauth&redirect=";

    public void destroy() {}

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        boolean status = this.check(request, response);
        if (status) chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {}

    private boolean check(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String openid = (String) session.getAttribute("openid");
        String nickname = (String) session.getAttribute("nickname");
        String imgurl = (String) session.getAttribute("imgurl");
        if (StringUtil.hasBlank(openid, nickname, imgurl)) {
            if (PropertyUtil.getProperty("weixin.debug").equalsIgnoreCase("true")) {
                openid = PropertyUtil.getProperty("weixin.openid");
                nickname = PropertyUtil.getProperty("weixin.nickname");
                imgurl = PropertyUtil.getProperty("weixin.imgurl");
            } else {
                openid = request.getParameter("openid");
                nickname = request.getParameter("nickname");
                imgurl = request.getParameter("headimgurl");
                if (StringUtil.hasBlank(openid, nickname, imgurl)) {
                    String indexUrl = PropertyUtil.getProperty("url.index");
                    String redirectUrl = apiUrl + URLEncoder.encode(indexUrl, "UTF-8");
                    response.sendRedirect(redirectUrl);
                    return false;
                }
            }
            nickname = urlDecode(nickname);
            imgurl = urlDecode(imgurl);
            session.setAttribute("openid", openid);
            session.setAttribute("nickname", nickname);
            session.setAttribute("imgurl", imgurl);
        }
        UserService userService = new UserServiceImpl();
        userService.updateUser(openid, nickname, imgurl);
        return true;
    }
}
