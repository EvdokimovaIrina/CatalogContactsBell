package catalogContacts.service.impl;

import catalogContacts.dao.CrudDAOUser;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.User;
import catalogContacts.model.UserRoles;
import catalogContacts.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UserServiceImpl implements UserService, UserDetailsService {

    private CrudDAOUser<User> crudDAOUser;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public int countingUsers() throws DaoException {
        int quantity;
        synchronized (this) {
            quantity = crudDAOUser.countingUsers();
        }
        return quantity;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public float averageUserContact() throws DaoException {
        float quantity;
        synchronized (this) {
            quantity = crudDAOUser.averageUserContact();
        }
        return quantity;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public float averageUserGroup() throws DaoException {
        float quantity;
        synchronized (this) {
            quantity = crudDAOUser.averageUserGroup();
        }
        return quantity;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<User> inactiveUsersList(int n) throws DaoException {
        List<User> userList;
        synchronized (this) {
            userList = crudDAOUser.inactiveUsers(n);
        }
        return userList;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<Map<User, Integer>> countingUserContact() throws DaoException {
        List<Map<User, Integer>> userListcountingContact;
        synchronized (this) {
            userListcountingContact = crudDAOUser.CountingUserContact();
        }
        return userListcountingContact;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<Map<User, Integer>> countingUserGroup() throws DaoException {
        List<Map<User, Integer>> userListcountingContact;
        synchronized (this) {
            userListcountingContact = crudDAOUser.CountingUserGroup();
        }
        return userListcountingContact;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user;
        try {
            user = crudDAOUser.getUserByName(name);
            if (user==null) {
               throw new UsernameNotFoundException("Пользователь не найден");
            }

        } catch (DaoException e) {
            throw new UsernameNotFoundException("Ошибка получения данных пользователя");
        }

        List<GrantedAuthority> authorities =
                buildUserAuthority(user.getUserRole());

        return buildUserForAuthentication(user, authorities);
    }


    // конвертируем User в UserDetails
    private UserDetails buildUserForAuthentication(User user,
                                            List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
                true, true, true, true, authorities);

    }

    private List<GrantedAuthority> buildUserAuthority(UserRoles userRoles) {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userRoles.toString()));

        return authorities;
    }

    public void setCrudDAOUser(CrudDAOUser<User> crudDAOUser) {
        this.crudDAOUser = crudDAOUser;
    }

}
