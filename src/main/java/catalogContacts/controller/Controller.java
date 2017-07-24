package catalogContacts.controller;

import catalogContacts.model.TypeContact;

import java.util.Map;

/**
 * Redirects actions from view to service to work with models
 */
public interface Controller {
    /**
     * Redirects to the server to add a new contact
     *
     * @param name
     */
    void addContact(String name);

    /**
     * Redirects to the service to add a new group
     *
     * @param name Contact name entered by the user
     */
    void addGroup(String name);

    /**
     * Redirecting to a service to add new contactDetails to a contact
     *
     * @param number
     * @param mapDetails
     */
    void addDetails(int number, Map<TypeContact, String> mapDetails);

    /**
     * @param numberContact
     * @param numberGroup
     */
    void addGroupToContact(int numberContact, int numberGroup);

    /**
     * @param numberContact
     * @param numberGroup
     */
    void deleteGroupToContact(int numberContact, int numberGroup);

    /**
     * @param numberGroup
     */
    void showContactList(Integer numberGroup);

    /**
     * @param numberContact
     */
    void showDetails(Integer numberContact);

    /**
     *
     */
    void showGroupList();

    /**
     * @param numberContact
     */
    void deletContact(int numberContact);

    /**
     * @param numberContact
     * @param numberContactDetails
     */
    void deletContactDetails(int numberContact, int numberContactDetails);

    /**
     * @param numberContact
     * @param numberContactDetails
     * @param value
     */
    void ChangeSelectedContactDetails(int numberContact, int numberContactDetails, String value);

    /**
     * @param numberGroup
     */
    void deletGroup(int numberGroup);

    /**
     * @param numberGroup
     * @param value
     */
    void changeGroup(int numberGroup, String value);

    /**
     * @param numberContact
     * @param value
     */
    void changeContact(int numberContact, String value);

}
