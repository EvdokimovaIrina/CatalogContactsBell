package catalogContacts.view.impl;

import catalogContacts.controller.*;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.*;
import catalogContacts.context.SecurityContextHolder;
import catalogContacts.view.ValidView;
import catalogContacts.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by iren on 20.07.2017.
 */
public class ViewController extends View {
    private Controller controller;
    private BufferedReader reader;
    private ValidView valid;

    public ViewController() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        // Запускаем поток
    }

    @Override
    public void run() {
        authorizationUser();
    }

    public void authorizationUser() {
        while (true) {

            System.out.println("Введите свой логин, и затем пароль. Для отмены укажите 0:");

            try {
                String strReader = reader.readLine();
                if (strReader.equals("0")) {
                    System.exit(1);
                }
                String login = strReader;
                strReader = reader.readLine();

                String passwor = strReader;

                controller.setUserThread(login,passwor);

                if (SecurityContextHolder.getLoggedUser()== null){
                    System.out.println("Авторизация не выполнена");
                }else {
                    displayMainMenu();
                }
            } catch (DaoException|IOException e){
                System.out.println("Ошибка при авторизации "+e.getMessage());
            }
        }
    }

    ////////////////////////
    public void displayMainMenu() {
        boolean continueCycle = true;
        while (continueCycle) {
            //выбор действия главного меню
            System.out.println("Выберите пункт меню");
            System.out.println("1 - создать новый контакт");
            System.out.println("2 - создать новую группу");
            System.out.println("3 - вывести список контактов");
            System.out.println("4 - вывести список контактов по группе");
            System.out.println("5 - найти контакт по наименованию");
            System.out.println("6 - вывести список групп");
            System.out.println("7 - изменить существующий контакт");
            System.out.println("8 - изменить существующую группу");
            System.out.println("9 - удалить контакт");
            System.out.println("10 - удалить группу");
            System.out.println("0 - выход");
            try {

                String strReader = reader.readLine();
                int selectedAction = valid.actionValid(strReader);
                continueCycle = action(selectedAction);

            } catch (IOException e) {
                System.out.println("Не известная команда!");
            }
        }
    }

    public boolean action(int selectedAction) throws IOException {
        switch (selectedAction) {
            case 1:
                addContact();
                return true;
            case 2:
                addGroup();
                return true;
            case 3:
                showContactList();
                return true;
            case 4:
                showContactListFromGroup();
                return true;
            case 5:
                findByName();
                return true;
            case 6:
                showGroupList();
                return true;
            case 7:
                showContactList();
                changeContact();
                return true;
            case 8:
                changeGroup();
                return true;
            case 9:
                deletContact();
                return true;
            case 10:
                deletGroup();
                return true;
            case 0:
                return false;
            default:
                return true;
        }
    }

    private void findByName() throws IOException {
        System.out.println("Введите имя для поиска: ");
        String name = reader.readLine();
        controller.findByName(name);
        changeContact();
    }

    public void addContact() throws IOException {
        System.out.println("Введите имя контакта: ");
        String name = reader.readLine();
        controller.addContact(name);
        changeContact();
    }

    public void addGroup() throws IOException {
        System.out.println("Введите название группы: ");
        String name = reader.readLine();
        controller.addGroup(name);
    }

    public void showContactList() {
        controller.showContactList(null);
    }

    public void showContactListFromGroup() throws IOException {
        controller.showGroupList();
        System.out.println("Укажите номер группы:");
        String strReader = reader.readLine();
        Integer numberGroup = valid.actionValid(strReader);
        controller.showContactList(numberGroup);

    }

    public void showGroupList() {
        controller.showGroupList();
    }

    public void changeContact() throws IOException {
        while (true) {

            System.out.println("Укажите номер контакта:");
            System.out.println("для отмены выберите 0:");
            String strReader = reader.readLine();
            int numberContact = valid.actionValid(strReader);

            if (numberContact == 0) break;

            controller.showDetails(numberContact);
            showMenuContact(numberContact);
        }
    }


    public void showMenuContact(int numberContact) {
        boolean continueCycle = true;
        while (continueCycle) {
            System.out.println("Выберите действие:");
            System.out.println("1 - редактировать имя");
            System.out.println("2 - редактировать контактную информацию");
            System.out.println("3 - редактировать принадлежность к группам");
            System.out.println("0 - выход");

            try {
                String strReader = reader.readLine();
                int selectedAction = valid.actionValid(strReader);
                continueCycle = actionMenuContact(selectedAction, numberContact);

            } catch (IOException e) {
                System.out.println("Не известная команда!");
            }
        }
    }

    public boolean actionMenuContact(int selectedAction, int numberContact) throws IOException {
        switch (selectedAction) {
            case 1:
                changeContactName(numberContact);
                return true;
            case 2:
                showMenuContactDetails(numberContact);
                return true;
            case 3:
                selecteActionGroupListContact(numberContact);
                return true;
            case 0:
                return false;
            default:
                return true;
        }
    }


    public void changeContactName(int numberContact) throws IOException {
        System.out.println("Укажите новое значение:");
        controller.changeContact(numberContact, reader.readLine());
    }

    public void AddContactDetails(int numberContact) throws IOException {

        while (true) {
            Map<TypeContact, String> mapDetails = new HashMap<>();
            System.out.println("Выберите тип контакта: ");

            TypeContact[] typeContactArray = TypeContact.values();

            for (TypeContact type : typeContactArray) {
                System.out.println((type.ordinal() + 1) + " - " + type.name());
            }
            System.out.println("для отмены выберите 0:");

            String strReader = reader.readLine();
            int selectedAction = valid.actionValid(strReader);

            if (selectedAction == 0) break;
            if (selectedAction > typeContactArray.length || selectedAction < 0) continue;

            TypeContact typeContact = typeContactArray[selectedAction - 1];
            System.out.println("Введите данные " + typeContact.name() + ":");
            String strDetails = reader.readLine();

            mapDetails.put(typeContact, strDetails);
            controller.addDetails(numberContact, mapDetails);
        }

    }

    public void selecteActionGroupListContact(int numberContact) throws IOException {
        boolean continueCycle = true;
        while (continueCycle) {
            controller.showGroupList();
            System.out.println("Выберите пункт меню:");
            System.out.println("1 - добавить принадлежность к группе");
            System.out.println("2 - удалить принадлежность к группе");
            System.out.println("0 - выход");

            String strReader = reader.readLine();
            int selectedAction = valid.actionValid(strReader);
            switch (selectedAction) {
                case 1:
                    changeGroupListForContact(numberContact, false);
                    break;
                case 2:
                    changeGroupListForContact(numberContact, true);
                    break;
                case 0:
                    continueCycle = false;
                    break;
            }
        }
    }

    public void changeGroupListForContact(int numberContact, boolean delete) throws IOException {

        while (true) {
            System.out.println("Укажите номера групп. Чтобы закончить наберите 0:");
            String strReader = reader.readLine();
            int numberGroup = valid.actionValid(strReader);
            if (numberGroup == 0) break;
            if (numberGroup < 0) continue;
            if (delete) {
                controller.deleteGroupToContact(numberContact, numberGroup);
            } else {
                controller.addGroupToContact(numberContact, numberGroup);
            }

        }
    }

    public void changeGroup() throws IOException {

        while (true) {
            controller.showGroupList();
            System.out.println("Для редактирования группы укажите её номер, для возврата в главное меню наберите 0: ");
            String strReader = reader.readLine();
            int selectedAction = valid.actionValid(strReader);
            if (selectedAction == 0) break;
            System.out.println("Укажите новое значение:");
            controller.changeGroup(selectedAction, reader.readLine());

        }
    }

    public void deletContact() throws IOException {

        while (true) {
            controller.showContactList(null);
            System.out.println("Укажите номер контакта. Чтобы закончить наберите 0:");
            String strReader = reader.readLine();
            int numberContact = valid.actionValid(strReader);
            if (numberContact == 0) break;
            if (numberContact < 0) continue;

            controller.deletContact(numberContact);
        }

    }

    public void deletGroup() throws IOException {
        while (true) {
            controller.showGroupList();
            System.out.println("Укажите номер группы. Чтобы закончить наберите 0:");
            String strReader = reader.readLine();
            int numberGroup = valid.actionValid(strReader);
            if (numberGroup == 0) break;
            if (numberGroup < 0) continue;

            controller.deletGroup(numberGroup);
        }

    }


    public void showMenuContactDetails(int numberContact) throws IOException {
        boolean continueCycle = true;
        while (continueCycle) {
            System.out.println("Выберите пункт меню:");
            System.out.println("1 - добавить контактную информацию");
            System.out.println("2 - удалить контактную информацию");
            System.out.println("3 - изменить контактную информацию");
            System.out.println("0 - выход");

            String strReader = reader.readLine();
            int selectedAction = valid.actionValid(strReader);
            switch (selectedAction) {
                case 1:
                    AddContactDetails(numberContact);
                    continue;
                case 2:
                    deleteContactDetails(numberContact);
                    continue;
                case 3:
                    ChangeSelectedContactDetails(numberContact);
                    continue;
                case 0:
                    continueCycle = false;
                    break;
            }
        }
    }

    public void deleteContactDetails(int numberContact) throws IOException {
        controller.showDetails(numberContact);
        while (true) {
            System.out.println("Укажите номер контактной информации для удаления. Для выхода наберите 0:");
            String strReader = reader.readLine();
            int numberContactDetails = valid.actionValid(strReader);
            if (numberContactDetails == 0) break;
            controller.deletContactDetails(numberContact, numberContactDetails);
            break;

        }
    }

    public void ChangeSelectedContactDetails(int numberContact) throws IOException {
        controller.showDetails(numberContact);
        while (true) {
            System.out.println("Укажите номер контактной информации для редактирования. Для выхода наберите 0:");
            String strReader = reader.readLine();
            int numberContactDetails = valid.actionValid(strReader);
            if (numberContactDetails == 0) break;
            System.out.println("Укажите новое значение:");

            controller.ChangeSelectedContactDetails(numberContact, numberContactDetails, reader.readLine());
            break;

        }
    }
    ////////////////

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public ValidView getValid() {
        return valid;
    }

    public void setValid(ValidView valid) {
        this.valid = valid;
    }
}
