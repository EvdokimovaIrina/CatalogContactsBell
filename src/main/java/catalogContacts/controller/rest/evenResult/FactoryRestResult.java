package catalogContacts.controller.rest.evenResult;

import catalogContacts.controller.rest.dto.ContactDTO;
import catalogContacts.controller.rest.dto.ContactDetailsDTO;
import catalogContacts.controller.rest.dto.GroupDTO;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.Group;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Component
public class FactoryRestResult {
    public RestResult getSuccessResult(EventType eventType, Object object) {
        try {
            if (object != null) {
                switch (eventType) {
                    case CONTACT:
                        Contact contact = (Contact) object;
                        ContactDTO contactDTO = createContactDTO(contact);
                        return new RestResult(eventType, contactDTO);
                    case CONTACTLIST:
                        List<Contact> contactList = (List<Contact>) object;
                        List<ContactDTO> contactDTOList = new ArrayList<>();
                        for (Contact contact1 : contactList) {
                            contactDTOList.add(createContactDTO(contact1));
                        }
                        return new RestResult(eventType, contactDTOList);
                    case GROUP:
                        Group group = (Group) object;
                        GroupDTO groupDTO = createGroupDTO(group);
                        return new RestResult(eventType, groupDTO);
                    case GROUPLIST:
                        List<Group> groupList = (List<Group>) object;
                        List<GroupDTO> groupDTOList = new ArrayList<>();
                        for (Group group1 : groupList) {
                            groupDTOList.add(createGroupDTO(group1));
                        }
                        return new RestResult(eventType, groupDTOList);
                    case DETAILS:
                        ContactDetails contactDetails = (ContactDetails) object;
                        ContactDetailsDTO contactDetailsDTO = createContactDetailsDTO(contactDetails);
                        return new RestResult(eventType, contactDetailsDTO);
                    case DETAILSLIST:
                        List<ContactDetails> contactDetailsList = (List<ContactDetails>) object;
                        List<ContactDetailsDTO> contactDetailsDTOList = new ArrayList<>();
                        for (ContactDetails contactDetails1 : contactDetailsList) {
                            contactDetailsDTOList.add(createContactDetailsDTO(contactDetails1));
                        }
                        return new RestResult(eventType, contactDetailsDTOList);
                }

            }
        } catch (Exception e) {
            return new RestResult(EventType.ERROR, "Ошибка получения данных");
        }
        return new RestResult(EventType.ERROR, "Ошибка получения данных");
    }

    private ContactDTO createContactDTO(Contact contact) {
        return new ContactDTO(contact.getNumber(), contact.getFio(), contact.getUserByUserId().getLogin());
    }

    private GroupDTO createGroupDTO(Group group) {
        return new GroupDTO(group.getNumber(), group.getName(), group.getUserByUserId().getLogin());
    }

    private ContactDetailsDTO createContactDetailsDTO(ContactDetails contactDetails) {
        return new ContactDetailsDTO(contactDetails.getId(),
                contactDetails.getType(),
                contactDetails.getValue(),
                contactDetails.getContactByContactId().getNumber());
    }

    public RestResult getFailResult(Exception e) {
        return new RestResult(EventType.ERROR, "Ошибка получения данных");
    }
}
