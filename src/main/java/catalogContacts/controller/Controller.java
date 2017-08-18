package catalogContacts.controller;

import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.TypeContact;
import catalogContacts.service.ContactService;
import catalogContacts.service.GroupService;

import java.util.Map;

/**
 * Redirects actions from view to service to work with models
 */
public interface Controller {
    /**
     * Redirects to the service to add a new contact
     *
     * @param name  Contact name entered by the user
     */
    void addContact(String name);

    /**
     * Redirects to the service to add a new group
     *
     * @param name Group name entered by the user
     */
    void addGroup(String name);

    /**
     * Redirecting to a service to add new contactDetails to a contact
     *
     * @param number Number in the list of the changed contact
     * @param mapDetails Data on contact information in Map, contact type and value
     */
    void addDetails(int number, Map<TypeContact, String> mapDetails);

    /**
     *  Redirecting to a service to add group to an existing contact
     * @param numberContact Number in the list of the changed contact
     * @param numberGroup Group number in the general list to add to the contact
     */
    void addGroupToContact(int numberContact, int numberGroup);

    /**
     *Redirect to a service to delete a group from existing contact
     * @param numberContact Group number in the general list to add to the contact
     * @param numberGroup Group number in the general list to remove from the contact
     */
    void deleteGroupToContact(int numberContact, int numberGroup);

    /**
     * Gets the list of contacts
     * @param numberGroup Group number, if you want to list the contacts of the group. If the entire list, then the null value
     */
    String showContactListStr(int idUser, Integer numberGroup) throws DaoException;

    /**
     * Redirect to the service for displaying contact information
     * @param numberContact Number of the contact whose personal information needs to be displayed
     */
    String showDetails(Integer numberContact) throws DaoException;

    /**
     *Gets the list of group
     */
    String showGroupList(int idUser) throws DaoException ;

    /**
     * Redirect to a service to remove a contact from the list
     * @param numberContact Number in the list of the contact to be deleted
     */
    void deletContact(int numberContact);

    /**
     * Redirect to a service to remove a contact information
     * @param numberContact Number in the list of the changed contact
     * @param numberContactDetails Number in the list contact of the contactDetails to be deleted
     */
    void deletContactDetails(int numberContact, int numberContactDetails);

    /**
     * Redirection to the service for the editing of contact information
     * @param numberContact
     * @param numberContactDetails
     * @param value
     */
    void ChangeSelectedContactDetails(int numberContact, int numberContactDetails, String value);

    /**
     * Redirect to a service to remove a group from the list
     * @param numberGroup Number in the list of the group to be deleted
     */
    void deletGroup(int numberGroup);

    /**
     * Redirect to a service for editing a group
     * @param numberGroup   Number in the list of the changed group
     * @param value New value for the name of the group
     */
    void changeGroup(int numberGroup, String value);

    /**
     * Redirect to a service for editing a contact
     * @param numberContact Number in the list of the changed contact
     * @param value New value for the name of the contact
     */
    void changeContact(int numberContact, String value);

    ContactService getContactService();
    GroupService getGroupService();

    void findByName(String name);

    boolean isSetUserThread(String login, String password) throws DaoException ;

    String getMainMenuHTML();

    String getAuthorizationHTML();
}
