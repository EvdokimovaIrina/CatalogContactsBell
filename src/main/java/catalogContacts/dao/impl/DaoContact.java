package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.dao.mappers.impl.ModelMapperContact;
import catalogContacts.dao.mappers.impl.ModelMapperContactDetails;
import catalogContacts.dao.mappers.impl.ModelMapperGroup;
import catalogContacts.model.*;
import catalogContacts.context.SecurityContextHolder;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.apache.log4j.*;

/**
 *
 */
public class DaoContact extends DaoGeneral implements CrudDAO<Contact> {

    public DaoContact() throws DaoException {
        super();
    }

    public void create(Contact contact) throws DaoException {
         contact.setUserByUserId(getObjectFromBDById(User.class,SecurityContextHolder.getLoggedUserID()));
        saveObgectToBD(contact);
    }

    public void update(Contact contact) throws DaoException {
        saveObgectToBD(contact);
    }

    public void delete(int number) throws DaoException {
       deleteObgectFromBD(Contact.class,number);
    }

    public Contact getObject(int id) throws DaoException {
        Transaction transaction = null;
        try {
            Contact contact;
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            contact = (Contact) session.load(Contact.class,id);
            contact.getContactDetailsList().size();
            contact.getGroupList().size();
            transaction.commit();
            return contact;
        } catch (Exception e) {
            transaction.rollback();
            throw new DaoException("Ошибка при получении данных ", e);
        }
    }

    public List<Contact> getAll() throws DaoException {
        int userID = SecurityContextHolder.getLoggedUserID();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            List<Contact>  contactList;
            transaction = session.beginTransaction();
            User user = (User) session.load(User.class,userID);
            user.getContactsByUserId().size(); //для инициализации
            contactList = user.getContactsByUserId();
            transaction.commit();
            return contactList;
        } catch (Exception e) {
            transaction.rollback();
            throw new DaoException("Ошибка получения списка контактов ", e);
        }
    }

    public List<Contact> findByName(String name) throws DaoException {
        return null;
    }

}
