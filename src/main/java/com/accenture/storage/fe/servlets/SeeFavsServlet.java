package com.accenture.storage.fe.servlets;

import com.accenture.storage.be.business.item.ItemBusinessService;
import com.accenture.storage.be.business.user.UserBusinessService;
import com.accenture.storage.be.entity.order.Item;
import com.accenture.storage.be.entity.user.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/seefav")
public class SeeFavsServlet extends HttpServlet {

    @Autowired
    UserBusinessService ubs;

    @Autowired
    ItemBusinessService ibs;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String username = session.getAttribute("un").toString();
        User u = ubs.getInfo(username);
        req.setAttribute("un", u.getFullName());
        req.setAttribute("fav", 0);
        String favs = u.getFavs();
        List<Item> favList = new ArrayList<>();
        if (!StringUtils.isEmpty(favs)) {
            String[] parts = favs.split(",");
            for (String str : parts) {
                favList.add(ibs.getById(Long.parseLong(str)));
            }
            req.setAttribute("favList", favList);
            session.setAttribute("favList", favList);
            req.setAttribute("fav", parts.length);
        }
        getServletContext().getRequestDispatcher("/favs.jsp").forward(req, resp);
    }
}