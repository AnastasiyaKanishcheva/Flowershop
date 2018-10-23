package com.accenture.flowershop.be.business.user;

import com.accenture.flowershop.be.DAO.UserDAO;
import com.accenture.flowershop.be.entity.user.User;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserBusinessServiceImpl implements UserBusinessService {
    @Autowired
    UserDAO dao;

    @Override
    public User login(String username, String password) {
        if (dao.getByUsernameAndPassword(username, password) != null) {
            return dao.getByUsername(username);
        }
        return null;
    }

    @Override
    public User register(@NotEmpty String username, @NotEmpty String password, String fullName, String phone, String address) {
        try {
            User u = new User(username, password, fullName, phone, address);
            if (dao.getByUsername(username) == null) {
                dao.create(u);
                return u;
            }
        } catch (Exception ex) {
            System.out.println("ERROR");
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }
}
