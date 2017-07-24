package catalogContacts.service.impl;

import catalogContacts.event.Event;
import catalogContacts.event.Observer;
import catalogContacts.model.Contact;
import catalogContacts.model.Group;
import catalogContacts.event.TypeEvent;
import catalogContacts.model.PhoneBook;
import catalogContacts.service.GroupService;
import catalogContacts.view.impl.ViewOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EvdokimovaIS on 13.07.2017.
 */
public final class GroupServiceImpl implements GroupService, Observer.Observable {
    private static GroupServiceImpl instance;
    private List<Contact> contactList;
    private List<Group> groupList;
    private List<Observer> ObserversList = new ArrayList<>();

    // Singleton
    private GroupServiceImpl() {
        this.contactList = PhoneBook.getPhoneBook().getContactsList();
        this.groupList = PhoneBook.getPhoneBook().getGroupsList();
    }

    public static synchronized GroupServiceImpl getInstance() {
        if (instance == null) {
            instance = new GroupServiceImpl();
            instance.addObserver(new ViewOutput());
        }
        return instance;
    }
    //////

    //работа с наблюдателями
    public void addObserver(Observer observer) {
        if (!ObserversList.contains(observer)) {
            ObserversList.add(observer);
        }
    }

    public void removeObserver(Observer observer) {
        if (ObserversList.contains(observer)) {
            ObserversList.remove(observer);
        }
    }


    public void notifyObserver(TypeEvent typeEvent, Object mainObject, Object value) {
        for (Observer observer : ObserversList) {
            observer.handleEvent(new Event(typeEvent, mainObject,value));
        }
    }

    ////////
    public void addGroup(String name) {
        Group group = new Group(name);
        saveGroup(group);
    }

    public void saveGroup(Group group) {
        if (!groupList.contains(group)) {
            //т.к. такой еще нет, то установим ему номер и добавим в список
            groupList.add(group);
            group.setNumber(groupList.indexOf(group) + 1);
        }
        notifyObserver(TypeEvent.showGroupList, groupList,null);
    }

    public void deleteGroup(int numberGroup) {
        // если номер в пределах, то удалим контакт с выбранным номером
        if (numberWithinBorders(numberGroup, groupList.size())){
            groupList.remove(numberGroup);
        }
        //после удаления группы переберем список и изменим номера в контактах
        for (int i = 0; i < groupList.size(); i++) {
            Group group1 = groupList.get(i);
            group1.setNumber(i + 1);
        }
        notifyObserver(TypeEvent.showGroupList, groupList,null);
    }

    public void changeGroup(int numberGroup,String value) {
        if (numberWithinBorders(numberGroup, groupList.size())){
            groupList.get(numberGroup).setName(value);
        }
    }

    public void showGroupList() {
        notifyObserver(TypeEvent.showGroupList, groupList,null);
    }


    public boolean numberWithinBorders(int number, int max) {
        if (number >= 0 && number < max) {
            return true;
        } else {
            notifyObserver(TypeEvent.errorNumber,"Номер за пределами границы",null);
            return false;
        }

    }
    public Group findByNumber(int number) {
        Group group = null;
        for (Group group2 : groupList) {
            if (group2.getNumber() == number) {
                group = group2;
            }
        }
        return group;
    }

}
