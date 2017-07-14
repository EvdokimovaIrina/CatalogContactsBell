package catalogContacts.service;

import catalogContacts.model.Contact;
import catalogContacts.model.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EvdokimovaIS on 13.07.2017.
 */
public class GroupService {

    List<Group> groupsList = new ArrayList<>();

    public void saveGroup(Group group){
        groupsList.add(group);

    }

    public List<Group> getGroupsList() {
        return groupsList;
    }
}
