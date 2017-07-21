package catalogContacts;

import catalogContacts.controller.toRemove.ContactController;
import catalogContacts.controller.toRemove.ControllerOld;
import catalogContacts.controller.toRemove.GroupController;
import catalogContacts.dao.SavAndRestoreDataImpl;
import catalogContacts.controller.*;
import catalogContacts.event.Observer;
import catalogContacts.service.ContactService;
import catalogContacts.service.ContactServiceImpl;
import catalogContacts.service.GroupService;
import catalogContacts.service.GroupServiceImpl;
import catalogContacts.view.ValidViewImpl;
import catalogContacts.view.ViewInput;

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


        //Запустим основное меню программы
        ViewInput viewInput = setParametersController();
        SavAndRestoreDataImpl srData = new SavAndRestoreDataImpl();

        try {
            if (!srData.deserialize()){
                System.out.println("Не удалось восстановить список контактов и групп");
            }
        } catch (Exception e) {
            //e.printStackTrace(System.err);
            System.out.println("Не удалось восстановить список контактов и групп");
        }

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
    public static ViewInput setParametersController(){

        Controller controller = new Controller();
        ContactService contactService = ContactServiceImpl.getInstance();
        controller.setContactService(contactService);
        controller.setGroupService(GroupServiceImpl.getInstance());
        controller.setValidController(new ValidControllerImpl());

        ViewInput viewInput = new ViewInput();
        viewInput.addObserver(new Controller());
        viewInput.setController(controller);
        viewInput.setReader(new BufferedReader(new InputStreamReader(System.in)));
        viewInput.setValid(new ValidViewImpl());

        /*ContactController contactController = new ContactController();
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

        ControllerOld controller = new ControllerOld();
        controller.setContactController(contactController);
        controller.setGroupController(groupController);
        controller.setReader(reader);
        controller.setValid(valid);*/
        return viewInput;
    }
}

