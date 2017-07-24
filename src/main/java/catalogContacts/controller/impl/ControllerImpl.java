package catalogContacts.controller.impl;

import catalogContacts.controller.Controller;
import catalogContacts.model.TypeContact;
import catalogContacts.service.ContactService;
import catalogContacts.service.impl.ContactServiceImpl;
import catalogContacts.service.GroupService;
import catalogContacts.service.impl.GroupServiceImpl;

import java.util.Map;

/**
 * Created by iren on 20.07.2017.
 */
public class ControllerImpl implements Controller{
    private ContactService contactService;
    private GroupService groupService;

    public ControllerImpl(ContactServiceImpl contactService,GroupServiceImpl groupService) {
        this.contactService = contactService;
        this.groupService =groupService;

    }

    public ControllerImpl() {

    }
    ////////////////

    public void addContact(String name) {
        contactService.addContact(name);
    }

    public void addGroup(String name) {
        groupService.addGroup(name);
    }

    public void addDetails(int number, Map<TypeContact, String> mapDetails) {
        contactService.addContactDetails(number-1,mapDetails);
    }

    public void addGroupToContact(int numberContact, int numberGroup) {
        contactService.addGroupToContact(numberContact-1,numberGroup-1);
    }

    public void deleteGroupToContact(int numberContact, int numberGroup) {
        contactService.deleteGroupToContact(numberContact-1,numberGroup-1);
    }

    public void showContactList(Integer numberGroup) {
        if (!(numberGroup==null)){
            contactService.showContactList(numberGroup - 1);
        }else {
            contactService.showContactList(null);
        }

    }

    public void showDetails(Integer numberContact) {
        contactService.showContactDetails(numberContact - 1);
    }

    public void showGroupList() {
        groupService.showGroupList();
    }

    public void deletContact(int numberContact) {
        contactService.deleteContact(numberContact - 1);
    }

    public void deletContactDetails(int numberContact,int numberContactDetails) {
        contactService.deleteContactDetails(numberContact-1,numberContactDetails - 1);
    }

    public void ChangeSelectedContactDetails(int numberContact,int numberContactDetails,String value){
        contactService.ChangeSelectedContactDetails(numberContact-1,numberContactDetails - 1,value);
    }

    public void deletGroup(int numberGroup) {
        groupService.deleteGroup(numberGroup - 1);
    }

    public void changeGroup(int numberGroup,String value) {
        groupService.changeGroup(numberGroup - 1,value);
    }


    public void changeContact(int numberContact,String value){
        contactService.changeContact(numberContact,value);
    }

    ////////////////////

    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }


}
