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
    private GroupService groupService;

    public void AddGroup(BufferedReader reader) throws IOException{
        System.out.println("Введите название группы: ");
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
