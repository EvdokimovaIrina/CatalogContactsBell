package catalogContacts.controller;

import catalogContacts.context.SecurityContextHolder;
import catalogContacts.controller.evenResult.EventType;
import catalogContacts.controller.evenResult.FactoryRestResult;
import catalogContacts.controller.evenResult.RestResult;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.Group;
import catalogContacts.model.TypeContact;
import catalogContacts.service.ContactService;
import catalogContacts.service.GroupService;
import catalogContacts.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Resource(name = "factoryRestResult")
    private FactoryRestResult factoryRestResult;

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public RestResult addContact(@RequestParam(value="userid") int userid,
                                  @RequestParam(value="name") String contactName) {
        logger.debug("Добавление нового контакта");
        try {
            userService.setUserThread(userid);
            return factoryRestResult.getSuccessResult(EventType.CONTACT,contactService.addContact(contactName));
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/contact", method = RequestMethod.PUT)
    public RestResult changeContact(@RequestParam(value="userid") int userid,
                                  @RequestParam(value="id") int id,
                                  @RequestParam(value="name") String newName) {
        logger.debug("Изменение наименования контакта");
        try {
            userService.setUserThread(userid);
            return factoryRestResult.getSuccessResult(EventType.CONTACT,contactService.changeContact(id,newName));
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/contact", method = RequestMethod.DELETE)
    public RestResult deleteContact(@RequestParam(value="userid") int userid,
                                  @RequestParam(value="id") int id) {
        logger.debug("Удаление контакта");
        try {
            userService.setUserThread(userid);
            contactService.deleteContact(id);
            return factoryRestResult.getSuccessResult(EventType.SUCCESS,true);
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public RestResult addGroup(@RequestParam(value="userid") int userid,
                                  @RequestParam(value="name", required=true) String groupName) {
        logger.debug("Добавление нового контакта");
        try {
            userService.setUserThread(userid);
            return factoryRestResult.getSuccessResult(EventType.GROUP,groupService.addGroup(groupName));
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/group", method = RequestMethod.PUT)
    public RestResult changeGroup(@RequestParam(value="userid") int userid,
                                  @RequestParam(value="id") int id,
                                  @RequestParam(value="name") String newName) {
        logger.debug("Изменение наименования группы");
        try {
            userService.setUserThread(userid);
            return factoryRestResult.getSuccessResult(EventType.GROUP,groupService.changeGroup(id,newName));
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/group", method = RequestMethod.DELETE)
    public RestResult deleteGroup(@RequestParam(value="userid") int userid,
                                  @RequestParam(value="id") int id) {
        logger.debug("Удаление группы");
        try {
            userService.setUserThread(userid);
            groupService.deleteGroup(id);
            return factoryRestResult.getSuccessResult(EventType.SUCCESS,true);
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/contactDetails", method = RequestMethod.POST)
    public RestResult addContactDetails(@RequestParam(value="contactid") int contactid,
                               @RequestParam(value="type") String type,
                               @RequestParam(value="value") String value) {
        logger.debug("Добавление детальной информации");
        try {
            Map<TypeContact, String> mapDetails = new HashMap<>();
            mapDetails.put(TypeContact.valueOf(type), value);
            return factoryRestResult.getSuccessResult(EventType.DETAILS,contactService.addContactDetails(contactid,mapDetails));
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/contactDetails", method = RequestMethod.PUT)
    public RestResult ChangeContactDetails(@RequestParam(value="contactid") int contactid,
                                  @RequestParam(value="id") int id,
                                  @RequestParam(value="value") String value) {
        logger.debug("Изменение детальной информации");
        try {
            return factoryRestResult.getSuccessResult(EventType.DETAILS,contactService.ChangeSelectedContactDetails(contactid,id,value));
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/contactDetails", method = RequestMethod.DELETE)
    public RestResult addContact2(@RequestParam(value="contactid") int contactid,
                                  @RequestParam(value="id") int id) {
        logger.debug("Удаление детальной информации");
        try {
            contactService.deleteContactDetails(contactid,id);
            return factoryRestResult.getSuccessResult(EventType.SUCCESS,true);
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/contacts/{contactId}", method = RequestMethod.GET)
    public RestResult showContact(@RequestParam(value="userid") int userId,
                                  @PathVariable(value="contactId")int id) {
        try {
            SecurityContextHolder.setLoggedUserID(userId);
            Contact contact = contactService.getContactByNumber(id);
            return factoryRestResult.getSuccessResult(EventType.CONTACT,contact);
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public RestResult findByNameContact(@RequestParam(value="userid") int userId,
                                        @RequestParam(value="name") String name) {
        try {
            SecurityContextHolder.setLoggedUserID(userId);
            List<Contact> contactList = contactService.findByName(name);
            return factoryRestResult.getSuccessResult(EventType.CONTACTLIST,contactList);
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public RestResult showContactList(@RequestParam(value="userid") int userId,
                                      @RequestParam(value="groupid",required = false) Integer groupId) {
        try {
            SecurityContextHolder.setLoggedUserID(userId);
            List <Contact> contactList = contactService.showContactList(groupId);
            return factoryRestResult.getSuccessResult(EventType.CONTACTLIST,contactList);
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public RestResult showGroup(@RequestParam(value="userid") int userId,
                                @RequestParam(value="id")int id) {
        try {
            SecurityContextHolder.setLoggedUserID(userId);
            Group group = groupService.findByNumber(id);
            return factoryRestResult.getSuccessResult(EventType.GROUP,group);
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }
    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public RestResult showGroupList(@RequestParam(value="id")int id) {
        try {
            SecurityContextHolder.setLoggedUserID(id);
            List <Group> groupList = groupService.showGroupList();
            return factoryRestResult.getSuccessResult(EventType.GROUPLIST,groupList);
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/contactDetailses", method = RequestMethod.GET)
    public RestResult showContactDetailses(@RequestParam(value="contactid")int contactid) {
        try {
            Contact contact = contactService.getContactByNumber(contactid);
            List <ContactDetails> contactDetails = contactService.showContactDetails(contact);
            return factoryRestResult.getSuccessResult(EventType.DETAILSLIST,contactDetails);
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
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

    public FactoryRestResult getFactoryRestResult() {
        return factoryRestResult;
    }

    public void setFactoryRestResult(FactoryRestResult factoryRestResult) {
        this.factoryRestResult = factoryRestResult;
    }
}
