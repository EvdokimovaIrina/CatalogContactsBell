package catalogContacts.controller.toRemove;

import catalogContacts.controller.ValidController;
import catalogContacts.model.Group;
import catalogContacts.model.PhoneBook;
import catalogContacts.service.ContactService;
import catalogContacts.service.GroupService;

import java.io.*;
import java.util.List;

/**
 * Created by EvdokimovaIS on 13.07.2017.
 */
public class GroupController {
    private GroupService groupService;
    private ContactService contactService;
    private BufferedReader reader;
    private ContactController contactController;
    private ValidController valid;

    public void AddGroup() throws IOException {
        System.out.println("Введите название группы: ");
        String name = reader.readLine();
        Group group = new Group(name);

        groupService.saveGroup(group);
    }


    public void showGroup(Group group) {

        System.out.println(group.getNumber() + " " + group.getName());
        System.out.println("*************************");
    }

    public void showGroupList() throws IOException {
        List<Group> groupsList = PhoneBook.groupsList;
        System.out.println("Список групп:");

        System.out.println("*************************");

        for (Group group : groupsList) {
            showGroup(group);
        }
        while (true) {
            System.out.println("Для редактирования группы укажите её номер, для возврата в главное меню наберите 0: ");
            String strReader = reader.readLine();
            int selectedAction = valid.actionValid(strReader);
            if (selectedAction > 0) {
                //найдем группу и выведем информацию по ней
                if (selectedAction == 0) break;
                if (selectedAction < 0) continue;
                try {
                    Group group = groupService.findByNumber(selectedAction);
                    if (groupsList.contains(group) & group != null) {
                        //выведем информацию по группе
                        showGroup(group);
                        //предложим дальнейшие действия с найденой группой
                        change(group);
                        break;
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Не верный номер группы");
                    continue;
                }
            }
        }
    }

    //вывод меню для редактирования контакта
    public void change(Group group) throws IOException {
        boolean continueCycle = true;
        while (continueCycle) {
            System.out.println("Выберите действие:");
            System.out.println("1 - редактировать наименование");
            System.out.println("2 - вывести список контактов данной группы");
            System.out.println("3 - удалить группу");
            System.out.println("0 - выход");
            String strReader = reader.readLine();
            int selectedAction = valid.actionValid(strReader);
            switch (selectedAction) {
                case 1:
                    //редактируем наименование
                    System.out.println("Укажите новое наименование:");
                    strReader = reader.readLine();
                    group.setName(strReader);
                    showGroup(group);
                    continue;
                case 2:
                    //выводим список контактов группы
                    contactController.setContactService(contactService);
                    contactController.showContactList(group);
                    showGroup(group);
                    continue;
                case 3:
                    groupService.deleteGroup(group);
                    continueCycle = false;
                    break;
                case 0:
                    continueCycle = false;
                    break;
                default:
                    continue;
            }
        }
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public ContactService getContactService() {
        return contactService;
    }

    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public ValidController getValid() {
        return valid;
    }

    public void setValid(ValidController valid) {
        this.valid = valid;
    }

    public ContactController getContactController() {
        return contactController;
    }

    public void setContactController(ContactController contactController) {
        this.contactController = contactController;
    }
}
