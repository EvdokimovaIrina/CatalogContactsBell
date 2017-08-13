package catalogContacts.controller.impl;

import catalogContacts.controller.Controller;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.TypeContact;
import catalogContacts.service.ContactService;
import catalogContacts.service.UserService;
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
    private UserService userService;

    public ControllerImpl(ContactServiceImpl contactService,GroupServiceImpl groupService,UserService userService) {
        this.contactService = contactService;
        this.groupService =groupService;
        this.userService = userService;
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
        contactService.addContactDetails(number,mapDetails);
    }

    public void addGroupToContact(int numberContact, int numberGroup) {
        contactService.addGroupToContact(numberContact,numberGroup);
    }

    public void deleteGroupToContact(int numberContact, int numberGroup) {
        contactService.deleteGroupToContact(numberContact,numberGroup);
    }

    public void showContactList(Integer numberGroup) {
        if (!(numberGroup==null)){
            contactService.showContactList(numberGroup );
        }else {
            contactService.showContactList(null);
        }

    }

    public void showDetails(Integer numberContact) {
        contactService.showContactDetails(numberContact);
    }

    public void showGroupList() {
        groupService.showGroupList();
    }

    public void deletContact(int numberContact) {
        contactService.deleteContact(numberContact );
    }

    public void deletContactDetails(int numberContact,int numberContactDetails) {
        contactService.deleteContactDetails(numberContact,numberContactDetails );
    }

    public void ChangeSelectedContactDetails(int numberContact,int numberContactDetails,String value){
        contactService.ChangeSelectedContactDetails(numberContact,numberContactDetails,value);
    }

    public void deletGroup(int numberGroup) {
        groupService.deleteGroup(numberGroup);
    }

    public void changeGroup(int numberGroup,String value) {
        groupService.changeGroup(numberGroup,value);
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

    public ContactService getContactService() {
        return contactService;
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public void findByName(String name) {
        contactService.findByName(name);
    }

    public void setUserThread(String login,String password) throws DaoException {
        userService.setUserThread(login,password);

    }


}
