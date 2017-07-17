package catalogContacts.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by EvdokimovaIS on 13.07.2017.
 */
public class Controller {
    private ContactController contactController;
    private GroupController groupController;
    private BufferedReader reader;
    private Valid valid;

    public void showMenu() {
        boolean continueCycle = true;
        while (continueCycle) {
            //При запуске выведем вопрос о необходимых действиях
            System.out.println("Выберите пункт меню");
            System.out.println("1 - создать новый контакт");
            System.out.println("2 - создать новую группу");
            System.out.println("3 - вывести список");
            System.out.println("4 - вывести список по группе");
            System.out.println("5 - вывести список групп");
            System.out.println("0 - выход");
            try {

                String strReader = reader.readLine();
                int selectedAction = valid.actionValid(strReader);
                continueCycle = Action(selectedAction);

            } catch (IOException e) {
                System.out.println("Не известная команда!");
            }
        }
    }

    public boolean Action(int selectedAction) throws IOException {
        switch (selectedAction) {
            case 1:
                contactController.AddContact();
                return true;
            case 2:
                groupController.AddGroup();
                return true;
            case 3:
                contactController.showContactList(null);
                return true;
            case 4:
                contactController.showContactListGroup();
                return true;
            case 5:
                groupController.showGroupList();
                return true;
            case 0:
                return false;
            default:
                return true;
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

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public Valid getValid() {
        return valid;
    }

    public void setValid(Valid valid) {
        this.valid = valid;
    }
}
