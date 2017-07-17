package catalogContacts;

import catalogContacts.controller.*;
import catalogContacts.service.*;

import java.io.*;
import java.util.Map;


/**
 * Created by iren on 13.07.2017.
 */
public class Main {
    public static void main(String[] args) {
        //восстановим списки контактов и групп если они сериализованы
        ContactService contactService = null;
        GroupService groupService = null;
        SavAndRestoreData srData = new SavAndRestoreData();
        try {
            Map<String, Object> mapService = srData.deserialize();
            contactService = (ContactServiceImpl) mapService.get("ContactService");
            groupService = (GroupServiceImpl) mapService.get("GroupService");

        } catch (Exception e) {
            //e.printStackTrace(System.err);
            System.out.println("Не удалось восстановить список контактов и групп");
            contactService = new ContactServiceImpl();
            groupService = new GroupServiceImpl();
        }

        //Запустим основное меню программы
        Controller controller = setParametersController(contactService,groupService);
        controller.showMenu();

        //Сериализуем список контактов и групп
        try {
            srData.serialize(contactService, groupService);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Не удалось сохранить списки");
        }
    }

    //Установим нужные параметры в контроллерах
    public static Controller setParametersController(ContactService contactService,GroupService groupService){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Valid valid = new ValidImpl();

        ContactController contactController = new ContactController();
        contactController.setContactService(contactService);
        contactController.setGroupService(groupService);
        contactController.setReader(reader);
        contactController.setValid(valid);

        GroupController groupController = new GroupController();
        groupController.setGroupService(groupService);
        groupController.setContactService(contactService);
        groupController.setContactController(contactController);
        groupController.setReader(reader);
        groupController.setValid(valid);

        Controller controller = new Controller();
        controller.setContactController(contactController);
        controller.setGroupController(groupController);
        controller.setReader(reader);
        controller.setValid(valid);
        return controller;
    }
}

