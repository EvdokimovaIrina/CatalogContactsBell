package catalogContacts.controller.toRemove;

import catalogContacts.TypeContact;
import catalogContacts.controller.ValidController;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.Group;
import catalogContacts.model.PhoneBook;
import catalogContacts.service.ContactService;
import catalogContacts.service.GroupService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by iren on 13.07.2017.
 */
public class ContactController {
    private ContactService contactService;
    private GroupService groupService;
    private BufferedReader reader;
    private ValidController valid;

    public void AddContact() throws IOException {
        System.out.println("Введите имя контакта: ");
        String name = reader.readLine();
        Contact contact = new Contact(name);

        AddContactDetails(contact);
        contactService.saveContact(contact);
    }

    //Добавление новой контактной информации
    public void AddContactDetails(Contact contact) throws IOException {
        List<ContactDetails> contactDetailList = new ArrayList<>();
        contactDetailList.addAll(contact.getContactDetailsList());

        int selectedAction = 1;
        while (true) {
            System.out.println("Выберите тип контакта: ");

            TypeContact[] typeContactArray = TypeContact.values();

            for (TypeContact type : typeContactArray) {
                System.out.println((type.ordinal() + 1) + " - " + type.name());
            }
            System.out.println("для отмены выберите 0:");

            String strReader = reader.readLine();
            selectedAction = valid.actionValid(strReader);

            if (selectedAction == 0) break;
            if (selectedAction > typeContactArray.length || selectedAction < 0) continue;

            TypeContact typeContact = typeContactArray[selectedAction - 1];
            System.out.println("Введите данные " + typeContact.name() + ":");
            String strDetails = reader.readLine();

            ContactDetails contactDetail = new ContactDetails(contact, typeContact, strDetails);
            contactDetailList.add(contactDetail);
        }
        contact.setContactDetailsList(contactDetailList);
    }


    //вывод меню для редактирования контакта
    public void changeContact(Contact contact) throws IOException {
        boolean continueCycle = true;
        while (continueCycle) {
            System.out.println("Выберите действие:");
            System.out.println("1 - редактировать имя");
            System.out.println("2 - редактировать контактную информацию");
            System.out.println("3 - редактировать принадлежность к группам");
            System.out.println("4 - удалить контакт");
            System.out.println("0 - выход");

            String strReader = reader.readLine();
            int selectedAction = valid.actionValid(strReader);
            switch (selectedAction) {
                case 1:
                    //редактируем имя контакта
                    System.out.println("Укажите новое имя:");
                    strReader = reader.readLine();
                    contact.setFio(strReader);
                    showContact(contact);
                    continue;
                case 2:
                    //редактируем контактную информацию
                    changeContactDetails(contact);
                    showContact(contact);
                    continue;
                case 3:
                    //List<Group> groupList = contact.getGroupList();
                    selecteActionGroupListContact(contact);
                    continue;
                case 4:
                    contactService.deleteContact(contact);
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

    //Редактирование контактной информации
    public void changeContactDetails(Contact contact) throws IOException {
        boolean continueCycle = true;
        while (continueCycle) {
            System.out.println("Выберите пункт меню:");
            System.out.println("1 - добавить контактную информацию");
            System.out.println("2 - удалить контактную информацию");
            System.out.println("3 - изменить контактную информацию");
            System.out.println("0 - выход");

            String strReader = reader.readLine();
            int selectedAction = valid.actionValid(strReader);
            List<Group> lisGroupForChange;
            switch (selectedAction) {
                case 1:
                    AddContactDetails(contact);
                    continue;
                case 2:
                    deleteContactDetails(contact);
                    continue;
                case 3:
                    ChangeSelectedContactDetails(contact);
                    continue;
                case 0:
                    continueCycle = false;
                    break;
            }
        }
    }


    public void deleteContactDetails(Contact contact) throws IOException {
        showContact(contact);
        while (true) {
            List<ContactDetails> contactDetailsList = contact.getContactDetailsList();
            System.out.println("Укажите номер контактной информации для удаления. Для выхода наберите 0:");
            String strReader = reader.readLine();
            int numberContactDetails = valid.actionValid(strReader);
            if (numberContactDetails > 0 & numberContactDetails < contactDetailsList.size()) {
                ContactDetails contactDetails = contactDetailsList.get(numberContactDetails);
                contactDetailsList.remove(contactDetails);
                contact.setContactDetailsList(contactDetailsList);
                break;
            } else if (numberContactDetails == 0) {
                break;
            }
        }
    }

    public void ChangeSelectedContactDetails(Contact contact) throws IOException {
        while (true) {
            List<ContactDetails> contactDetailsList = contact.getContactDetailsList();
            System.out.println("Укажите номер контактной информации для редактирования. Для выхода наберите 0:");
            String strReader = reader.readLine();
            int numberContactDetails = valid.actionValid(strReader);
            if (numberContactDetails > 0 & numberContactDetails < contactDetailsList.size()) {
                System.out.println("Укажите новое значение:");

                ContactDetails contactDetails = contactDetailsList.get(numberContactDetails - 1);
                contactDetails.setValue(reader.readLine());
                break;
            } else if (numberContactDetails == 0) {
                break;
            }
        }
    }

    //редактирование состава групп
    public void selecteActionGroupListContact(Contact contact) throws IOException {
        showGroupListContact(contact);

        boolean continueCycle = true;
        while (continueCycle) {
            System.out.println("Выберите пункт меню:");
            System.out.println("1 - добавить принадлежность к группе");
            System.out.println("2 - удалить принадлежность к группе");
            System.out.println("0 - выход");

            String strReader = reader.readLine();
            int selectedAction = valid.actionValid(strReader);
            List<Group> lisGroupForChange;
            switch (selectedAction) {
                case 1:
                    lisGroupForChange = CreatelisGroupForChange(contact, PhoneBook.groupsList);
                    contactService.changeGroupListAdd(contact, lisGroupForChange);
                    groupService.changeContactListAdd(contact, lisGroupForChange);
                    break;
                case 2:
                    lisGroupForChange = CreatelisGroupForChange(contact, contact.getGroupList());
                    contactService.changeGroupListDelete(contact, lisGroupForChange);
                    groupService.changeContactListDelete(contact, lisGroupForChange);
                    break;
                case 0:
                    continueCycle = false;
                    break;
            }
        }
    }

    public List<Group> CreatelisGroupForChange(Contact contact, List<Group> groupList) throws IOException {
        showGroupListContact(contact);
        List<Group> listGroupForChange = new ArrayList<>();
        for (Group group : groupList) {
            System.out.println(group.getNumber() + " " + group.getName());
        }
        System.out.println("Укажите номера групп. Чтобы закончить наберите 0:");
        while (true) {
            String strReader = reader.readLine();
            int selectedAction = valid.actionValid(strReader);
            if (selectedAction == 0) break;
            if (selectedAction < 0) continue;
            try {
                Group group = groupService.findByNumber(selectedAction);
                if (!listGroupForChange.contains(group) & group != null) {
                    listGroupForChange.add(group);
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Не верный номер группы");
                continue;
            }
        }

        return listGroupForChange;
    }

    //выводит информацию о контакте
    public void showContact(Contact contact) {

        System.out.println(contact.getNumber() + " " + contact.getFio());
        for (ContactDetails contactDetails : contact.getContactDetailsList()) {
            System.out.println(contactDetails.getType() + ": " + contactDetails.getValue());
        }

        showGroupListContact(contact);
    }

    public void showGroupListContact(Contact contact) {
        System.out.println("Группы:");
        for (Group group : contact.getGroupList()) {
            System.out.println(group.getNumber() + " " + group.getName());
        }
        System.out.println("*************************");
    }

    public void showGroupList() {
        System.out.println("Все группы:");
        for (Group group : PhoneBook.groupsList) {
            System.out.println(group.getNumber() + " " + group.getName());
        }
        System.out.println("*************************");
    }

    //выводит список контактов
    public void showContactList(Group group) throws IOException {
        List<Contact> contactList = new ArrayList();
        if (group == null) {
            contactList = PhoneBook.contactsList;
        } else {
            contactList = group.getContactList();
        }

        System.out.println("Список контактов " + ((group == null) ? "" : group.getName()) + ":");

        System.out.println("*************************");
        for (Contact contact : contactList) {
            showContact(contact);
        }

        while (true) {
            System.out.println("Для редактирования контакта укажите его номер, для возврата в главное меню наберите 0: ");
            String strReader = reader.readLine();
            int selectedAction = valid.actionValid(strReader);

            if (selectedAction == 0) break;
            if (selectedAction > contactList.size() || selectedAction < 0) continue;

            //найдем контакт и выведем информацию по нему
            Contact contact = contactList.get(selectedAction - 1);
            //выведем информацию по контакту
            showContact(contact);
            //предложим дальнейшие действия с найденым контактом
            changeContact(contact);

            continue;
        }
    }

    public void showContactListGroup() throws IOException {
        showGroupList();
        while (true) {
            System.out.println("Укажите номер группы, для отмены наберите 0: ");
            String strReader = reader.readLine();
            int selectedAction = valid.actionValid(strReader);

            if (selectedAction == 0) break;
            List<Group> groupList = PhoneBook.groupsList;
            if (selectedAction > groupList.size() || selectedAction < 0) continue;
            showContactList(groupList.get(selectedAction - 1));
        }

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

    public GroupService getGroupService() {
        return groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public ValidController getValid() {
        return valid;
    }

    public void setValid(ValidController valid) {
        this.valid = valid;
    }
}
