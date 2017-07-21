package catalogContacts.controller;

import catalogContacts.TypeContact;
import catalogContacts.event.ObservableViewInput;
import catalogContacts.event.Observer;
import catalogContacts.event.ObserverForViewImpl;
import catalogContacts.service.ContactService;
import catalogContacts.service.ContactServiceImpl;
import catalogContacts.service.GroupService;
import catalogContacts.service.GroupServiceImpl;

/**
 * Created by iren on 20.07.2017.
 */
public class Controller implements ObserverForViewImpl{
    private ContactService contactService;
    private GroupService groupService;
    private ValidController validController;

    public ContactService getContactService() {
        return contactService;
    }

    public void handleEventMenuSelection(int item) {

    }

    public Controller(){
        this.contactService = ContactServiceImpl.getInstance();
        this.groupService = GroupServiceImpl.getInstance();
        this.validController = new ValidControllerImpl();
    }

    public void handleEventAddContact(String name) {
        contactService.addContact(name);
    }

    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public ValidController getValidController() {
        return validController;
    }

    public void setValidController(ValidController validController) {
        this.validController = validController;
    }



}
