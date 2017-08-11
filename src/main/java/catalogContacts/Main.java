package catalogContacts;

import catalogContacts.controller.impl.ControllerImpl;
import catalogContacts.controller.*;
import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.impl1.DaoContact;
import catalogContacts.dao.impl1.DaoGroup;
import catalogContacts.dao.GetDataFromBD;
import catalogContacts.dao.impl1.GetDataFromBDImpl;
import catalogContacts.dao.sql.DBConnectionPool;
import catalogContacts.model.Contact;
import catalogContacts.model.Group;
import catalogContacts.service.impl.ContactServiceImpl;
import catalogContacts.service.impl.GroupServiceImpl;
import catalogContacts.view.impl.ValidViewImpl;
import catalogContacts.view.impl.ViewController;
import catalogContacts.view.impl.ViewOutput;

import java.sql.SQLException;

/**
 * Created by iren on 13.07.2017.
 */
public class Main {
    public static void main(String[] args) {



        ViewController viewInput = setParametersController();


        //Запустим основное меню программы
        viewInput.displayMainMenu();

    }

    //Установим нужные параметры в контроллерах
    public static ViewController setParametersController() {

        GetDataFromBD getDataFromBD = new GetDataFromBDImpl();

        try {
            getDataFromBD.setConnection(DBConnectionPool.getInstance().getConnectionPool().getConnection());
        } catch (SQLException e) {
            e.getMessage();
            System.exit(1);
        }

        ViewController viewInput = new ViewController();
        //Авторизация пользователя
        try {
            viewInput.selectingAUser();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        Integer userID = getDataFromBD.getUserID();
        if(userID==null | userID==0){
            System.exit(1);
        }

        ContactServiceImpl contactService = ContactServiceImpl.getInstance();
        CrudDAO<Contact> crudDAOContact = new DaoContact();
        crudDAOContact.setGetDataFromBD(getDataFromBD);
        CrudDAO<Group> crudDAOGroup = new DaoGroup();
        crudDAOGroup.setGetDataFromBD(getDataFromBD);
        contactService.setCrudDAOContact(crudDAOContact);
        contactService.setCrudDAOGroup(crudDAOGroup);

        GroupServiceImpl groupService = GroupServiceImpl.getInstance();
        groupService.setCrudDAOGroup(crudDAOGroup);

        contactService.addObserver(new ViewOutput());
        Controller controller = new ControllerImpl(contactService,groupService);


        viewInput.setController(controller);
        viewInput.setValid(new ValidViewImpl());

        return viewInput;
    }
}

