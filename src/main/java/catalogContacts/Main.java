package catalogContacts;

import catalogContacts.controller.impl.ControllerImpl;
import catalogContacts.controller.*;
import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.implDAO.DaoContact;
import catalogContacts.dao.implDAO.DaoGroup;
import catalogContacts.model.Contact;
import catalogContacts.model.Group;
import catalogContacts.service.impl.ContactServiceImpl;
import catalogContacts.service.impl.GroupServiceImpl;
import catalogContacts.view.impl.ValidViewImpl;
import catalogContacts.view.impl.ViewController;
import catalogContacts.view.impl.ViewOutput;


/**
 * Created by iren on 13.07.2017.
 */
public class Main {
    public static void main(String[] args) {



        ViewController viewInput = setParametersController();
        Thread thread = new Thread(viewInput);
        thread.start();

        ViewController viewInput2 = setParametersController();
        Thread thread2 = new Thread(viewInput);
        thread2.start();

        //Запустим основное меню программы
       // viewInput.displayMainMenu();

    }

    //Установим нужные параметры в контроллерах
    public static ViewController setParametersController() {


        ViewController viewInput = new ViewController();

        ContactServiceImpl contactService = ContactServiceImpl.getInstance();
        CrudDAO<Contact> crudDAOContact = new DaoContact();
        CrudDAO<Group> crudDAOGroup = new DaoGroup();
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

