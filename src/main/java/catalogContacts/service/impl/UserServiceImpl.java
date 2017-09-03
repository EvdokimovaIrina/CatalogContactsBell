package catalogContacts.service.impl;

import catalogContacts.context.SecurityContextHolder;
import catalogContacts.dao.CrudDAOUser;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.User;
import catalogContacts.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Map;


public class UserServiceImpl implements UserService, UserDetailsService {

    private CrudDAOUser<User> crudDAOUser;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    public void setUserThread(String login, String password) throws DaoException {
        User user;
        synchronized (this) {
            user = crudDAOUser.authorizationUser(login, password);
        }
        if (user != null) {
            SecurityContextHolder.setLoggedUserID(user.getId());
        }
    }

    public void setUserThread(int id) throws DaoException {
        SecurityContextHolder.setLoggedUserID(id);
    }

    public int countingUsers() throws DaoException {
        int quantity;
        synchronized (this) {
            quantity = crudDAOUser.countingUsers();
        }
        return quantity;
    }

    public float averageUserContact() throws DaoException {
        float quantity;
        synchronized (this) {
            quantity = crudDAOUser.averageUserContact();
        }
        return quantity;
    }

    public float averageUserGroup() throws DaoException {
        float quantity;
        synchronized (this) {
            quantity = crudDAOUser.averageUserGroup();
        }
        return quantity;
    }

    public List<User> inactiveUsersList(int n) throws DaoException {
        List<User> userList;
        synchronized (this) {
            userList = crudDAOUser.inactiveUsers(n);
        }
        return userList;
    }

    public List<Map<User, Integer>> countingUserContact() throws DaoException {
        List<Map<User, Integer>> userListcountingContact;
        synchronized (this) {
            userListcountingContact = crudDAOUser.CountingUserContact();
        }
        return userListcountingContact;
    }

    public List<Map<User, Integer>> countingUserGroup() throws DaoException {
        List<Map<User, Integer>> userListcountingContact;
        synchronized (this) {
            userListcountingContact = crudDAOUser.CountingUserGroup();
        }
        return userListcountingContact;
    }

    public void setCrudDAOUser(CrudDAOUser<User> crudDAOUser) {
        this.crudDAOUser = crudDAOUser;
    }


    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
