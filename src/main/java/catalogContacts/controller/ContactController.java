package catalogContacts.controller;

import catalogContacts.TypeContact;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.service.ContactService;

import java.io.*;
import java.util.ArrayList;
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
            System.out.println("для отмены выберите 0");

            String strReader = reader.readLine();
            selectedAction = Integer.parseInt(strReader);
            if (selectedAction == 0) break;

            TypeContact typeContact = typeContactArray[selectedAction - 1];
            System.out.println("Введите данные " + typeContact.name() + ":");
            String strDetails = reader.readLine();

            ContactDetails contactDetail = new ContactDetails(contact, typeContact, strDetails);
            contactDetailList.add(contactDetail);
        }
        contact.setContactDetailsList(contactDetailList);
        contactService.saveContact(contact);

    }

    // String name = reader.readLine();

    public void showContactList() {
        List<Contact> contactList = contactService.getContactsList();
        System.out.println("Список контактов:");
        for (Contact contact: contactList) {
            System.out.println("*************************");
            System.out.println(contact.getFio());
            for (ContactDetails contactDetails: contact.getContactDetailsList()) {
                System.out.println(contactDetails.getType()+": "+contactDetails.getValue());
            }
        }
        System.out.println("*************************");
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
