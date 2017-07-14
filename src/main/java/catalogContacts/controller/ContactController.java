package catalogContacts.controller;

import catalogContacts.TypeContact;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.service.ContactService;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by iren on 13.07.2017.
 */
public class ContactController {
    ContactService contactService;

    public void AddContact() throws IOException {
        System.out.println("Введите имя контакта: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name = reader.readLine();
        Contact contact = new Contact(name);

        List<ContactDetails> contactDetailList = new ArrayList<>();

        int selectedAction = 1;
        while (true) {
            System.out.println("Выберите тип контакта: ");

            TypeContact[] typeContactArray = TypeContact.values();

            for (TypeContact type : typeContactArray) {
                System.out.println((type.ordinal() + 1) + " - " + type.name());
            }
            System.out.println("для отмены выберите 0:");

            String strReader = reader.readLine();
            selectedAction = Integer.parseInt(strReader);

            if (selectedAction == 0) break;
            if (selectedAction > typeContactArray.length) continue;

            TypeContact typeContact = typeContactArray[selectedAction - 1];
            System.out.println("Введите данные " + typeContact.name() + ":");
            String strDetails = reader.readLine();

            ContactDetails contactDetail = new ContactDetails(contact, typeContact, strDetails);
            contactDetailList.add(contactDetail);
        }
        contact.setContactDetailsList(contactDetailList);
        contactService.saveContact(contact);

    }

    //вывод меню для редактирования контакта
    public void changeContact(Contact contact) throws IOException {
        boolean continueCycle=true;
        while (continueCycle) {
            System.out.println("Выберите действие:");
            System.out.println("1 - редактировать имя");
            System.out.println("2 - редактировать контактную информацию");
            System.out.println("3 - редактировать принадлежность к группам");
            System.out.println("4 - удалить контакт");
            System.out.println("0 - выход");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String strReader = reader.readLine();
            int selectedAction = Integer.parseInt(strReader);


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
                    while (true) {
                        List<ContactDetails> contactDetailsList = contact.getContactDetailsList();
                        System.out.println("Укажите номер контактной информации для редактирования. Для выхода наберите 0:");
                        strReader = reader.readLine();
                        int numberContactDetails = Integer.parseInt(strReader);
                        if (numberContactDetails > 0 & numberContactDetails < contactDetailsList.size()) {
                            System.out.println("Укажите новое значение:");

                            ContactDetails contactDetails = contactDetailsList.get(numberContactDetails - 1);
                            contactDetails.setValue(reader.readLine());
                            break;
                        } else if (numberContactDetails == 0) {
                            break;
                        }
                    }
                    showContact(contact);
                    continue;
                case 3:
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

    //выводит информацию о контакте
    public void showContact(Contact contact) {

        System.out.println(contact.getNumber() + " " + contact.getFio());
        for (ContactDetails contactDetails : contact.getContactDetailsList()) {
            System.out.println(contactDetails.getType() + ": " + contactDetails.getValue());
        }
        System.out.println("*************************");
    }

    //выводит список контактов
    public void showContactList() throws IOException {
        List<Contact> contactList = contactService.getContactsList();
        System.out.println("Список контактов:");

        System.out.println("*************************");

        for (Contact contact : contactList) {
            showContact(contact);
        }

        while (true) {
            System.out.println("Для редактирования контакта укажите его номер, для возврата в главное меню наберите 0: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String strReader = reader.readLine();
            int selectedAction = Integer.parseInt(strReader);
            if (selectedAction > 0) {
                //найдем контакт и выведем информацию по нему
                if (selectedAction >= 0 & selectedAction < contactList.size()) {
                    Contact contact = contactList.get(selectedAction - 1);
                    //выведем информацию по контакту
                    showContact(contact);

                    //предложим дальнейшие действия с найденым контактом
                    changeContact(contact);
                }
                for (Contact contact : contactList) {
                    if (contact.getNumber() == selectedAction) {

                        break;
                    }
                }
            } else {
                if (selectedAction == 0) {
                    //т.к. выбрали возврат в главное меню, то закончим вечный цикл и выполнение вернется к главному циклу
                    break;
                }
            }
        }
    }

    public void showContactListGroup() {

    }

    public ContactService getContactService() {
        return contactService;
    }

    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }
}
