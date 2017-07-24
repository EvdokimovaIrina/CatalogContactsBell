package catalogContacts;

import catalogContacts.controller.controllerImpl.ControllerImpl;
import catalogContacts.dao.SavAndRestoreDataImpl;
import catalogContacts.controller.*;
import catalogContacts.model.PhoneBook;
import catalogContacts.service.serviceImpl.ContactServiceImpl;
import catalogContacts.service.serviceImpl.GroupServiceImpl;
import catalogContacts.view.validImpl.ValidViewImpl;
import catalogContacts.view.viewImpl.ViewController;
import catalogContacts.view.viewImpl.ViewOutput;

import java.io.*;


/**
 * Created by iren on 13.07.2017.
 */
public class Main {
    public static void main(String[] args) {
        //восстановим списки контактов и групп если они сериализованы
       /* ContactService contactService = null;
        GroupService groupService = null;
        SavAndRestoreData srData = new SavAndRestoreData();
        try {
            Map<String, Object> mapService = srData.deserialize();
            contactService = (ContactServiceImplOld) mapService.get("ContactService");
            groupService = (GroupServiceImpl) mapService.get("GroupService");

        } catch (Exception e) {
            //e.printStackTrace(System.err);
            System.out.println("Не удалось восстановить список контактов и групп");
            contactService = new ContactServiceImpl();
            groupService = new GroupServiceImpl();
        }
        */
        //востановим данные
        SavAndRestoreDataImpl srData = new SavAndRestoreDataImpl();

        try {
            if (!srData.deserialize()) {
                System.out.println("Не удалось восстановить список контактов и групп");
            }
        } catch (Exception e) {
            //e.printStackTrace(System.err);
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
        contactService.setContactList(PhoneBook.getPhoneBook().getContactsList());
        contactService.setGroupList(PhoneBook.getPhoneBook().getGroupsList());

        GroupServiceImpl groupService = GroupServiceImpl.getInstance();
        groupService.setContactList(PhoneBook.getPhoneBook().getContactsList());
        groupService.setGroupList(PhoneBook.getPhoneBook().getGroupsList());

        contactService.addObserver(new ViewOutput());
        Controller controller = new ControllerImpl(contactService,groupService);

        ViewController viewInput = new ViewController();
        viewInput.setController(controller);
        //viewInput.setReader(new BufferedReader(new InputStreamReader(System.in)));
        viewInput.setValid(new ValidViewImpl());


        return viewInput;
    }
}

