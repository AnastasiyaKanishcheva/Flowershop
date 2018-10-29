package com.accenture.flowershop.fe.servlets;

import com.accenture.flowershop.be.business.user.UserBusinessService;
import com.accenture.flowershop.be.entity.order.Item;
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
import java.util.List;

@WebServlet(urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {
    @Autowired
    UserBusinessService userBusinessService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        session.setAttribute("total", 0);
        session.setAttribute("discount", 0);

//        String username = session.getAttribute("username").toString();
//
//        User user = userBusinessService.getInfo(username);
//        req.setAttribute("username", user.getUsername());
//        req.setAttribute("balance", user.getBalance());
//        req.setAttribute("discount", user.getDiscount());

        List<Item> cart = (List<Item>) session.getAttribute("cart");
        session.setAttribute("cart", cart);
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);


    }
}