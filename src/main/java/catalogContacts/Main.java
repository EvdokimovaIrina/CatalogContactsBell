package catalogContacts;

import catalogContacts.controller.impl.ControllerImpl;
import catalogContacts.controller.*;
import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.CrudDAOUser;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.impl.DaoContact;
import catalogContacts.dao.impl.DaoGroup;
import catalogContacts.dao.impl.DaoUser;
import catalogContacts.model.Contact;
import catalogContacts.model.Group;
import catalogContacts.model.User;
import catalogContacts.service.ContactService;
import catalogContacts.service.GroupService;
import catalogContacts.service.UserService;
import catalogContacts.service.impl.ContactServiceImpl;
import catalogContacts.service.impl.GroupServiceImpl;
import catalogContacts.service.impl.UserServiceImpl;
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
        Thread thread = new Thread(viewInput);
        thread.start();

       /* ViewController viewInput2 = setParametersController();
        Thread thread2 = new Thread(viewInput);
        thread2.start();

        ViewController viewInput3 = setParametersController();
        Thread thread3 = new Thread(viewInput);
        thread3.start();
        */

    }

    //Установим нужные параметры в контроллерах
    public static ViewController setParametersController() {


        ViewController viewInput = new ViewController();

        ContactServiceImpl contactService = ContactServiceImpl.getInstance();
        CrudDAO<Contact> crudDAOContact = null;

        CrudDAO<Group> crudDAOGroup = null;
        CrudDAOUser<User> crudDAOUser = null;

        try {
            crudDAOContact = new DaoContact();
            crudDAOGroup = new DaoGroup();
            crudDAOUser = new DaoUser();

        } catch (DaoException e) {
            e.getMessage();
        }


        contactService.setCrudDAOContact(crudDAOContact);
        contactService.setCrudDAOGroup(crudDAOGroup);

        GroupServiceImpl groupService = GroupServiceImpl.getInstance();
        groupService.setCrudDAOGroup(crudDAOGroup);

        UserService userService = UserServiceImpl.getInstance();
        userService.setCrudDAOUser(crudDAOUser);

        contactService.addObserver(new ViewOutput());
        Controller controller = new ControllerImpl(contactService,groupService,userService);

        viewInput.setController(controller);
        viewInput.setValid(new ValidViewImpl());

        return viewInput;
    }
}

