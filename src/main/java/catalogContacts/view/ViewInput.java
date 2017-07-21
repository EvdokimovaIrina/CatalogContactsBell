package catalogContacts.view;

import catalogContacts.TypeContact;
import catalogContacts.controller.Controller;
import catalogContacts.event.ObservableViewInput;
import catalogContacts.event.Observer;
import catalogContacts.event.ObserverForViewImpl;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.PhoneBook;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iren on 20.07.2017.
 */
public class ViewInput implements ObservableViewInput{
    private Controller controller;
    private BufferedReader reader;
    private ValidView valid;
    private List<ObserverForViewImpl> ObserversList = new ArrayList<>();


    //работа с наблюдателями

    public void addObserver(ObserverForViewImpl observer) {
        if (!ObserversList.contains(observer)) {
            ObserversList.add(observer);
        }
    }


    public void removeObserver(ObserverForViewImpl observer) {
        if (ObserversList.contains(observer)) {
            ObserversList.remove(observer);
        }
    }


    public void notifyMenuSelection() {

    }


    public void notifyAddContact(String name) {
        for (ObserverForViewImpl observer : ObserversList) {
            observer.handleEventAddContact(name);
        }
    }

    //////////
    public void displayMainMenu() {
        boolean continueCycle = true;
        while (continueCycle) {
            //выбор действия главного меню
            System.out.println("Выберите пункт меню");
            System.out.println("1 - создать новый контакт");
            System.out.println("2 - создать новую группу");
            System.out.println("3 - вывести список контактов");
            System.out.println("4 - вывести список контактов по группе");
            System.out.println("5 - вывести список групп");
            System.out.println("6 - изменить существующий контакт");
            System.out.println("7 - изменить существующую группу");
            System.out.println("8 - удалить контакт");
            System.out.println("9 - удалить группу");
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
                AddContact();
                return true;
            case 2:
               // controller.AddGroup();
                return true;
            case 3:
                //contactController.showContactList(null);
                return true;
            case 4:
                //contactController.showContactListGroup();
                return true;
            case 5:
                //groupController.showGroupList();
                return true;
            case 0:
                return false;
            default:
                return true;
        }
    }

    public void AddContact() throws IOException {
        System.out.println("Введите имя контакта: ");
        String name = reader.readLine();

        notifyAddContact(name);
       /* AddContactDetails(contact);
        contactService.saveContact(contact);*/
    }

    public void displayMenuContact() {
        System.out.println("Выберите действие:");
        System.out.println("1 - редактировать имя");
        System.out.println("2 - редактировать контактную информацию");
        System.out.println("3 - редактировать принадлежность к группам");
        System.out.println("4 - удалить контакт");
        System.out.println("0 - выход");
    }


    public void displayMenuGroup() {

    }


    public void displayMenuContactDetails() {
        System.out.println("Выберите пункт меню:");
        System.out.println("1 - добавить контактную информацию");
        System.out.println("2 - удалить контактную информацию");
        System.out.println("3 - изменить контактную информацию");
        System.out.println("0 - выход");
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public ValidView getValid() {
        return valid;
    }

    public void setValid(ValidView valid) {
        this.valid = valid;
    }
}
