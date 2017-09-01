package catalogContacts.controller;

import catalogContacts.context.SecurityContextHolder;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.Contact;
import catalogContacts.service.ContactService;
import catalogContacts.service.GroupService;
import catalogContacts.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */

@RestController("restController")
@RequestMapping(value = "/rest")
public class RestControllerImpl {
    protected static Logger logger = Logger.getLogger(RestControllerImpl.class.getName());
    @Resource(name = "contactServise")
    private ContactService contactService;
    @Resource(name = "groupServise")
    private GroupService groupService;
    @Resource(name = "userServise")
    private UserService userService;


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addContact2(@PathVariable(value = "name") String contactName) {
        logger.debug("Добавление нового сонтакта");

        try {
            contactService.addContact(contactName);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        return "redirect:/contactList";
    }

    @RequestMapping(value = "/contactList", method = RequestMethod.GET)
    public Collection<Contact> showContactList(@PathVariable(value = "id") Integer id) {
        logger.debug("Выод списка контактов");
        List <Contact> contactList1 = new ArrayList<>();
        try {
            SecurityContextHolder.setLoggedUserID(id);
            contactList1 = contactService.showContactList(null);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        return contactList1;
    }

    public ContactService getContactService() {
        return contactService;
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

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
