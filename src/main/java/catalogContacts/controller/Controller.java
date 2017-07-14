package catalogContacts.controller;

import catalogContacts.model.Contact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EvdokimovaIS on 13.07.2017.
 */
public class Controller {
    ContactController contactController;
    GroupController groupController;
    public void showMenu() {
        boolean selectedItem = true;

        while (selectedItem)  {
            //При запуске выведем вопрос о необходимых действиях
            System.out.println("Выберите пункт меню");
            System.out.println("1 - создать новый контакт");
            System.out.println("2 - создать новую группу");
            System.out.println("3 - вывести список");
            System.out.println("4 - вывести список по группе");
            System.out.println("5 - вывести список групп");
            System.out.println("0 - выход");
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String strReader = reader.readLine();
                int selectedAction = Integer.parseInt(strReader);

                if (selectedAction==0) break;

                switch (selectedAction) {
                    case 1:
                        contactController.AddContact();
                        continue;
                    case 2:
                        groupController.AddGroup();
                        continue;
                    case 3:
                        contactController.showContactList();
                        continue;
                    case 4:
                        contactController.showContactListGroup();
                        continue;
                    case 5:
                        groupController.showGroupList();
                        continue;
                    default:
                            continue;
                }
            } catch (IOException e) {

            }


        }
    }

    public GroupController getGroupController() {
        return groupController;
    }

    public void setGroupController(GroupController groupController) {
        this.groupController = groupController;
    }

    public ContactController getContactController() {
        return contactController;
    }

    public void setContactController(ContactController contactController) {
        this.contactController = contactController;
    }
}
