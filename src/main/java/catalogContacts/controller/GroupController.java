package catalogContacts.controller;

import catalogContacts.model.Group;
import catalogContacts.service.ContactService;
import catalogContacts.service.GroupService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EvdokimovaIS on 13.07.2017.
 */
public class GroupController {
    GroupService groupService;

    public void AddGroup() throws IOException{
        System.out.println("Введите название группы: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name = reader.readLine();
        Group group = new Group(name);

        groupService.saveGroup(group);
     }

    public void showGroupList(){

    }

    public GroupService getGroupService() {
        return groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }
}
