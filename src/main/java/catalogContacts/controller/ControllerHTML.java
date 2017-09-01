package catalogContacts.controller;

import catalogContacts.dao.exception.DaoException;

/**
 * Redirects actions from view to service to work with models
 */
public interface ControllerHTML {

    /**
     * Gets the list of contacts
     * @param numberGroup Group number, if you want to list the contacts of the group. If the entire list, then the null value
     */
    String showContactListStr(Integer numberGroup) throws DaoException;

    /**
     * Redirect to the service for displaying contact information
     * @param numberContact Number of the contact whose personal information needs to be displayed
     */
    String showDetails(Integer numberContact) throws DaoException;

    /**
     *Gets the list of group
     */
    String showGroupList() throws DaoException ;

    String findByName(String name) throws DaoException;

    boolean isSetUserThread(String login, String password) throws DaoException ;

    String getMainMenuHTML();

    String getAuthorizationHTML();
}
