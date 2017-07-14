package catalogContacts;

import catalogContacts.controller.ContactController;
import catalogContacts.controller.Controller;
import catalogContacts.controller.GroupController;
import catalogContacts.service.ContactService;
import catalogContacts.service.GroupService;

import java.io.*;


/**
 * Created by iren on 13.07.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {


        ContactService contactService = new ContactService();
        GroupService groupService = new GroupService();

        ContactController contactController = new ContactController();
        contactController.setContactService(contactService);

        GroupController groupController = new GroupController();
        groupController.setGroupService(groupService);

        Controller controller = new Controller();
        controller.setContactController(contactController);
        controller.setGroupController(groupController);
        controller.showMenu();

    }

}

