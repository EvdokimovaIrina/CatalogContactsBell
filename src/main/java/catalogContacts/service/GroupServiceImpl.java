package catalogContacts.service;

import catalogContacts.model.Contact;
import catalogContacts.model.Group;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by EvdokimovaIS on 13.07.2017.
 */
public class GroupServiceImpl implements Serializable, GroupService {

    List<Group> groupsList = new ArrayList<>();

    public void saveGroup(Group group) {
        if (!groupsList.contains(group)) {
            //т.к. такой еще нет, то установим ему номер и добавим в список
            groupsList.add(group);
            group.setNumber(groupsList.indexOf(group) + 1);
        }
    }

    public void deleteGroup(Group group) {
        // найдем нужный контакт и удалим его
       /* Iterator<Group> iterContact = groupsList.iterator();
        while (iterContact.hasNext()) {
            Group group1 = iterContact.next();
            if (group1.equals(group1)) {
                iterContact.remove();
            }
        }*/
        groupsList.remove(group);
        //после удаления контакта переберем список и изменим номера в контактах
        for (int i = 0; i < groupsList.size(); i++) {
            Group group1 = groupsList.get(i);
            group1.setNumber(i + 1);
        }
    }

    public void changeContactListAdd(Contact contact, List<Group> groupList) {
        //List<Contact> newListContact = new ArrayList<>();

        for (Group group : groupList) {
            List<Contact> contactListGroup = new ArrayList<>();
            contactListGroup.addAll(group.getContactList());
            if (!contactListGroup.contains(contact)) {
                contactListGroup.add(contact);
                group.setContactList(contactListGroup);

                  int i=0;
            }
        }

    }

    public void changeContactListDelete(Contact contact, List<Group> groupList) {

        for (Group group : groupList) {
            List<Contact> contactListGroup = new ArrayList<>();
            contactListGroup.addAll(group.getContactList());
            if (contactListGroup.contains(contact)) {
                contactListGroup.remove(contact);
                group.setContactList(contactListGroup);
            }
        }
    }

    public Group findByNumber(int number) {
        Group group=null;
        for (Group group2: groupsList) {
            if (group2.getNumber()==number){
                group=group2;
            }
        }
        return group;
    }

    public List<Group> getGroupsList() {
        return groupsList;
    }
}
