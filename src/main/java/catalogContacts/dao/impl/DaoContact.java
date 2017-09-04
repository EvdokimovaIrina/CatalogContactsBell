package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.CrudDAOUser;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.Contact;
import catalogContacts.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


public class DaoContact extends DaoGeneral implements CrudDAO<Contact> {

    private SessionFactory sessionFactory;
    private CrudDAOUser<User> userCrudDAO;
    private static Logger logger = Logger.getLogger(DaoContact.class.getName());


    public DaoContact() throws DaoException {
        super();
    }

    public void create(Contact contact) throws DaoException {
        logger.debug("Добавление нового контакта: " + contact.getFio());

        contact.setUserByUserId(userCrudDAO.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName()));
        saveObgectToBD(contact);

    }

    public void update(Contact contact) throws DaoException {

        saveObgectToBD(contact);

    }

    public void delete(int number) throws DaoException {
        logger.debug("Удален контакта.");
        deleteObgectFromBD(Contact.class, number);

    }

    @Transactional(readOnly = true)
    public Contact getObject(int id) throws DaoException {
        try {
            Contact contact;
            Session session = sessionFactory.getCurrentSession();
            contact = (Contact) session.load(Contact.class, id);
            contact.getContactDetailsList().size();
            contact.getGroupList().size();
            return contact;
        } catch (Exception e) {
               logger.error("Ошибка при получении данных ", e);
            throw new DaoException("Ошибка при получении данных ", e);
        }
    }

    public List<Contact> getAll() throws DaoException {
        try {
            List<Contact> contactList;
            User user = userCrudDAO.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
            user.getContactsByUserId().size(); //для инициализации
            contactList = user.getContactsByUserId();
            return contactList;
        } catch (Exception e) {
            logger.error("Ошибка получения списка контактов ", e);
            throw new DaoException("Ошибка получения списка контактов ", e);
        }
    }


    public List<Contact> findByName(String name) throws DaoException {
           try {
            List<Contact> contactList = new ArrayList<>();
            User user = userCrudDAO.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
            if (user==null){
                logger.error("Ошибка авторизации, пользователь не найден");
                throw new DaoException("Ошибка авторизации, пользователь не найден");
            }
            user.getContactsByUserId().size();
            for (Contact contact : user.getContactsByUserId()) {
                if (contact.getFio().contains(name)) {
                    contactList.add(contact);
                }
            }
            return contactList;
        } catch (Exception e) {
            logger.error("Ошибка получения списка контактов ", e);
            throw new DaoException("Ошибка получения списка контактов ", e);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        super.setSessionFactory(sessionFactory);
    }

    public CrudDAOUser<User> getUserCrudDAO() {
        return userCrudDAO;
    }

    public void setUserCrudDAO(CrudDAOUser<User> userCrudDAO) {
        this.userCrudDAO = userCrudDAO;
    }
}
