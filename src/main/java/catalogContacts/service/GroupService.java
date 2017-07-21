package catalogContacts.service;

import catalogContacts.model.Contact;
import catalogContacts.model.Group;

import java.util.List;

/**
 * Created by iren on 16.07.2017.
 */
public interface GroupService {
    public void saveGroup(Group group);
    public void deleteGroup(Group group);
    public void changeContactListAdd(Contact group, List<Group> contact);
    public void changeContactListDelete(Contact contact, List<Group> groupList);
    public Group findByNumber(int number);
}
