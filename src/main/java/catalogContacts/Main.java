package catalogContacts;

import catalogContacts.controller.ContactController;
import catalogContacts.controller.Controller;
import catalogContacts.controller.GroupController;
import catalogContacts.service.ContactService;
import catalogContacts.service.GroupService;
import catalogContacts.service.SavAndRestoreData;

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
            contactService = (ContactService) mapService.get("ContactService");
            groupService = (GroupService) mapService.get("GroupService");

        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.out.println("Не удалось восстановить список контактов и групп");
            contactService = new ContactService();
            groupService = new GroupService();
        }
        ContactController contactController = new ContactController();
        contactController.setContactService(contactService);

        GroupController groupController = new GroupController();
        groupController.setGroupService(groupService);

        Controller controller = new Controller();
        controller.setContactController(contactController);
        controller.setGroupController(groupController);

        //Запустим основное меню программы
        controller.showMenu();

        //Сериализуем список контактов и групп
        try {
            srData.serialize(contactService, groupService);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Не удалось сохранить списки");
        }

    }

}

