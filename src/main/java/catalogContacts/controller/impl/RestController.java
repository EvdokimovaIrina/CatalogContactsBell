package catalogContacts.controller.impl;

import catalogContacts.dao.exception.DaoException;
import catalogContacts.service.ContactService;
import catalogContacts.service.GroupService;
import catalogContacts.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 *
 */

@Controller
@RequestMapping(value = "/main")
public class RestController {
    protected static Logger logger = Logger.getLogger(RestController.class.getName());
    @Resource(name="contactService")
    private ContactService contactService;
    @Resource(name="groupService")
    private GroupService groupService;
    @Resource(name="userService")
    private UserService userService;

    @RequestMapping(value = "/contacts/add", method = RequestMethod.POST)
    public String addContact(@ModelAttribute("contactAttribute") String contactName) {
        logger.debug("Добавление нового сонтакта");

        try {
            contactService.addContact(contactName);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        return "contacts";
    }
}
