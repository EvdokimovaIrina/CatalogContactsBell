package catalogContacts;

import catalogContacts.controller.impl.ControllerImpl;
import catalogContacts.controller.*;
import catalogContacts.dao.Validator;
import catalogContacts.dao.impl.ValidatorImpl;
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

        //Валидация файлов хранилища посредством xsd
        Validator validXML = new ValidatorImpl();
        if (!validXML.isXmlCorrect()){
            System.exit(1);
        }


        ViewController viewInput = setParametersController();

        //Выбор реализацию xml-парсера
        if(!viewInput.isSelectingAParcer()){
            System.exit(1);
        }
        //Запустим основное меню программы
        viewInput.displayMainMenu();

    }

    //Установим нужные параметры в контроллерах
    public static ViewController setParametersController() {

        ContactServiceImpl contactService = ContactServiceImpl.getInstance();

        GroupServiceImpl groupService = GroupServiceImpl.getInstance();

        contactService.addObserver(new ViewOutput());
        Controller controller = new ControllerImpl(contactService,groupService);

        ViewController viewInput = new ViewController();
        viewInput.setController(controller);
        viewInput.setValid(new ValidViewImpl());

        return viewInput;
    }
}

