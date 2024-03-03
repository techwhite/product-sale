package com.ecom.productsale.repository;

import com.ecom.productsale.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/*
current use configuration instead of db
 */
@Repository
public class UserRepository {

    @Value("${user.authentication.password}")
    private String encodedPassword;

    public User findByUsername(String userName) throws AccessException {
        User user = null;
        try {
            // todo: retrieve from db by username

            user = new User();
            user.setName(userName);
            user.setPassword(encodedPassword);
        } catch (Exception e) {
            throw new AccessException("Error accessing database", e);
        }

        return user;
    }

    /*
    might need to update multi dbs, so use Transactional and EnableTransactionManagement
     */
    @Transactional
    public boolean saveRegisterUser(String userName, String encodedPassword) {
        // todo: save into db
        // currently can just use configuration in application.properties

        return true;
    }
}
