package catalogContacts.dao.impl;

import catalogContacts.context.SecurityContextHolder;
import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.Contact;
import catalogContacts.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;


public class DaoContact extends DaoGeneral implements CrudDAO<Contact> {

    private SessionFactory sessionFactory;
    private static Logger logger = Logger.getLogger(DaoContact.class.getName());


    public DaoContact() throws DaoException {
        super();
    }

    public void create(Contact contact) throws DaoException {
        logger.debug("Добавление нового контакта: " + contact.getFio());
        contact.setUserByUserId(getObjectFromBDById(User.class, SecurityContextHolder.getLoggedUserID()));
        saveObgectToBD(contact);

    }

    public void update(Contact contact) throws DaoException {

        saveObgectToBD(contact);

    }

    public void delete(int number) throws DaoException {
        logger.debug("Удален контакта.");
        deleteObgectFromBD(Contact.class, number);

    }

    public Contact getObject(int id) throws DaoException {
        Transaction transaction = null;
        try {
            Contact contact;
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            contact = (Contact) session.load(Contact.class, id);
            contact.getContactDetailsList().size();
            contact.getGroupList().size();
            transaction.commit();
            return contact;
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Ошибка при получении данных ", e);
            throw new DaoException("Ошибка при получении данных ", e);
        }
    }

    public List<Contact> getAll() throws DaoException {
        int userID = SecurityContextHolder.getLoggedUserID();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            List<Contact> contactList;
            transaction = session.beginTransaction();
            User user = (User) session.load(User.class, userID);
            user.getContactsByUserId().size(); //для инициализации
            contactList = user.getContactsByUserId();
            transaction.commit();
            return contactList;
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Ошибка получения списка контактов ", e);
            throw new DaoException("Ошибка получения списка контактов ", e);
        }
    }

    public List<Contact> findByName(String name) throws DaoException {
        int userID = SecurityContextHolder.getLoggedUserID();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            List<Contact> contactList = new ArrayList<>();
            transaction = session.beginTransaction();
            User user = (User) session.load(User.class, userID);
            user.getContactsByUserId().size();
            for (Contact contact : user.getContactsByUserId()) {
                if (contact.getFio().contains(name)) {
                    contactList.add(contact);
                }
            }
            transaction.commit();
            return contactList;
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Ошибка получения списка контактов ", e);
            throw new DaoException("Ошибка получения списка контактов ", e);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
