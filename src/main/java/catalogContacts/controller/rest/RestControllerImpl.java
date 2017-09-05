package catalogContacts.controller.rest;

import catalogContacts.controller.rest.evenResult.EventType;
import catalogContacts.controller.rest.evenResult.FactoryRestResult;
import catalogContacts.controller.rest.evenResult.RestResult;
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

    @RequestMapping(value = "/contacts", method = RequestMethod.POST)
    public RestResult addContact(@RequestParam(value="name") String contactName) {
        logger.debug("Добавление нового контакта");
        try {
           return factoryRestResult.getSuccessResult(EventType.CONTACT,contactService.addContact(contactName));
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/contacts/{contactId}", method = RequestMethod.PUT)
    public RestResult changeContact(@PathVariable(value="contactId") int id,
                                  @RequestParam(value="name") String newName) {
        logger.debug("Изменение наименования контакта");
        try {
            return factoryRestResult.getSuccessResult(EventType.CONTACT,contactService.changeContact(id,newName));
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/contacts/{contactId}", method = RequestMethod.DELETE)
    public RestResult deleteContact(@PathVariable(value="contactId") int id) {
        logger.debug("Удаление контакта");
        try {
            contactService.deleteContact(id);
            return factoryRestResult.getSuccessResult(EventType.SUCCESS,true);
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/contacts/{contactId}", method = RequestMethod.GET)
    public RestResult showContact(@PathVariable(value="contactId")int id) {
        try {
            Contact contact = contactService.getContactByNumber(id);
            return factoryRestResult.getSuccessResult(EventType.CONTACT,contact);
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public RestResult findByNameContact(@RequestParam(value="name",required = false) String name,
                                        @RequestParam(value="groupid",required = false) Integer groupId) {
        try {
            List<Contact> contactList;
            if (name!=null){
                contactList = contactService.findByName(name);
            }else{
                contactList = contactService.showContactList(groupId);
            }
            return factoryRestResult.getSuccessResult(EventType.CONTACTLIST,contactList);
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/groups", method = RequestMethod.POST)
    public RestResult addGroup(@RequestParam(value="name") String groupName) {
        logger.debug("Добавление новой группы");
        try {
            return factoryRestResult.getSuccessResult(EventType.GROUP,groupService.addGroup(groupName));
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/groups/{groupId}", method = RequestMethod.PUT)
    public RestResult changeGroup(@PathVariable(value="groupId") int id,
                                  @RequestParam(value="name") String newName) {
        logger.debug("Изменение наименования группы");
        try {
              return factoryRestResult.getSuccessResult(EventType.GROUP,groupService.changeGroup(id,newName));
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/groups/{groupId}", method = RequestMethod.DELETE)
    public RestResult deleteGroup(@PathVariable(value="groupId") int id) {
        logger.debug("Удаление группы");
        try {
            groupService.deleteGroup(id);
            return factoryRestResult.getSuccessResult(EventType.SUCCESS,true);
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/groups/{groupId}", method = RequestMethod.GET)
    public RestResult showGroup(@PathVariable(value="groupId")int id) {
        try {
            Group group = groupService.findByNumber(id);
            return factoryRestResult.getSuccessResult(EventType.GROUP,group);
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }
    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public RestResult showGroupList() {
        try {
            List <Group> groupList= groupService.showGroupList();
            return factoryRestResult.getSuccessResult(EventType.GROUPLIST,groupList);
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/contactDetailses/{contactid}", method = RequestMethod.POST)
    public RestResult addContactDetails(@PathVariable(value="contactid") int contactid,
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

    @RequestMapping(value = "/contactDetails/{contactid}", method = RequestMethod.PUT)
    public RestResult ChangeContactDetails(@PathVariable(value="contactid") int contactid,
                                           @RequestParam(value="id") int id,
                                           @RequestParam(value="value") String value) {
        logger.debug("Изменение детальной информации");
        try {
            return factoryRestResult.getSuccessResult(EventType.DETAILS,contactService.ChangeSelectedContactDetails(contactid,id,value));
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }
    @ModelAttribute

    @RequestMapping(value = "/contactDetailses/{detailsID}/contacts/{contactid}", method = RequestMethod.DELETE)
    public RestResult deleteDetails(@PathVariable(value="contactid") int contactid,
                                    @PathVariable(value="detailsID")int id) {
        logger.debug("Удаление детальной информации");
        try {
            contactService.deleteContactDetails(contactid,id);
            return factoryRestResult.getSuccessResult(EventType.SUCCESS,true);
        } catch (DaoException e) {
            return factoryRestResult.getFailResult(e);
        }
    }

    @RequestMapping(value = "/contactDetailses/{contactid}", method = RequestMethod.GET)
    public RestResult showContactDetailses(@PathVariable(value="contactid")int contactid) {
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
