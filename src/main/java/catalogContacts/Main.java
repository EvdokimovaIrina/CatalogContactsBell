package catalogContacts;

import catalogContacts.controller.impl.ControllerImpl;
import catalogContacts.dao.impl.SavAndRestoreDataImpl;
import catalogContacts.controller.*;
import catalogContacts.model.PhoneBook;
import catalogContacts.service.impl.ContactServiceImpl;
import catalogContacts.service.impl.GroupServiceImpl;
import catalogContacts.view.impl.ValidViewImpl;
import catalogContacts.view.impl.ViewController;
import catalogContacts.view.impl.ViewOutput;

import java.io.*;


/**
 * Created by iren on 13.07.2017.
 */
public class Main {
    public static void main(String[] args) {

        //востановим данные
        SavAndRestoreDataImpl srData = new SavAndRestoreDataImpl();

        try {
            if (!srData.deserialize()) {
                System.out.println("Не удалось восстановить список контактов и групп");
            }
        } catch (Exception e) {
               System.out.println("Не удалось восстановить список контактов и групп");
        }

        //Запустим основное меню программы

        ViewController viewInput = setParametersController();

        viewInput.displayMainMenu();

        //Сериализуем список контактов и групп
        try {
            srData.serialize();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Не удалось сохранить списки");
        }
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

