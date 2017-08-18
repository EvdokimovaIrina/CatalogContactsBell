package catalogContacts.controller.impl;

import catalogContacts.context.SecurityContextHolder;
import catalogContacts.controller.Controller;
import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.impl.DaoContact;
import catalogContacts.dao.impl.DaoGroup;
import catalogContacts.dao.impl.DaoUser;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.Group;
import catalogContacts.model.TypeContact;
import catalogContacts.service.ContactService;
import catalogContacts.service.GroupService;
import catalogContacts.service.UserService;
import catalogContacts.service.impl.ContactServiceImpl;
import catalogContacts.service.impl.GroupServiceImpl;
import catalogContacts.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class ControllerHTMLImpl implements Controller {
    private ContactService contactService;
    private GroupService groupService;
    private UserService userService;

    public ControllerHTMLImpl() throws DaoException {
        this.contactService = ContactServiceImpl.getInstance();

        CrudDAO<Group> crudDAOGroup = new DaoGroup();

        this.contactService.setCrudDAOContact(new DaoContact());
        this.contactService.setCrudDAOGroup(new DaoGroup());

        this.groupService = GroupServiceImpl.getInstance();
        this.groupService.setCrudDAOGroup(crudDAOGroup);

        this.userService = UserServiceImpl.getInstance();
        this.userService.setCrudDAOUser(new DaoUser());
    }

    public void addContact(String name) {

    }

    public void addGroup(String name) {

    }

    public void addDetails(int number, Map<TypeContact, String> mapDetails) {

    }

    public void addGroupToContact(int numberContact, int numberGroup) {

    }

    public void deleteGroupToContact(int numberContact, int numberGroup) {

    }

   public String showContactListStr(int idUser, Integer numberGroup) throws DaoException {
        userService.setUserThread(idUser);
        List<Contact> contactList = contactService.showContactList(numberGroup);
        String strHtml = "<!DOCTYPE HTML>" +
                "<html><body><p>Список контактов</p></body></html>" +
                "<table>" +
                "<tbody>";
        for (Contact contact:contactList) {
            strHtml = strHtml+"<tr> " +
                    "<td>"+contact.getFio()+"</td>" +
                    "<td><input type=\"submit\" value=\"Просмотреть\" /></td>" +
                    "<td><input type=\"hidden\" name=\"idcontact\" value=\""+contact.getNumber()+"\"/></td>"+
                    "</tr>" ;
        }
        strHtml = strHtml+"</tbody>" +
                "</table>";
        return strHtml;
    }

    public String showDetails(Integer numberContact) throws DaoException {

        List<ContactDetails> contactDetailsList = contactService.showContactDetails(numberContact);
        String strHtml = "<!DOCTYPE HTML>" +
                "<html><body><p>Список контактов</p></body></html>" +
                "<table>" +
                "<tbody>" +
                "<form action=\"datacontact\" method=\"POST\">";
        for (ContactDetails contactDetails:contactDetailsList) {
            strHtml = strHtml+"<tr> " +
                    "<td>"+contactDetails.getType()+"</td>" +
                    "<td>"+contactDetails.getValue()+"</td>" +
                    "<td><input type=\"hidden\" name=\"id\" value=\""+contactDetails.getId()+"\"/></td>"+
                    "<td><input type=\"hidden\" name=\"idcontact\" value=\""+numberContact+"\"/></td>"+
                    "</tr>" ;
        }
        strHtml = strHtml+"</form></tbody>" +
                "</table>";
        return strHtml;
    }

    public String showGroupList(int idUser) throws DaoException {
        userService.setUserThread(idUser);
        List<Group> groupList = groupService.showGroupList();
        String strHtml = "<!DOCTYPE HTML>" +
                "<html><body><p>Список групп</p></body></html>" +
                "<table>" +
                "<tbody>";
        for (Group group:groupList) {
            strHtml = strHtml+"<tr> " +
                    "<td>"+group.getName()+"</td>" +
                    "<input type=\"hidden\" name=\"idcontact\" value=\""+group.getNumber()+"\"/>"+
                    "</tr>" ;
        }
        strHtml = strHtml+"</tbody>" +
                "</table>";
        return strHtml;
    }

    public void deletContact(int numberContact) {

    }

    public void deletContactDetails(int numberContact, int numberContactDetails) {

    }

    public void ChangeSelectedContactDetails(int numberContact, int numberContactDetails, String value) {

    }

    public void deletGroup(int numberGroup) {

    }

    public void changeGroup(int numberGroup, String value) {

    }

    public void changeContact(int numberContact, String value) {

    }

    public ContactService getContactService() {
        return null;
    }

    public GroupService getGroupService() {
        return null;
    }

    public void findByName(String name) {

    }

    public boolean isSetUserThread(String login, String password) throws DaoException {
        userService.setUserThread(login,password);
        if (SecurityContextHolder.getLoggedUser()== null){
            return false;
        }else {
            return true;
        }
    }



    public String getAuthorizationHTML(){
        String strHtml = "<!DOCTYPE HTML>" +
                "<html><head>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
                "<title>Авторизация</title></head>" +
                "<body>" +
                "<form action=\"menu\" method=\"POST\">" +
                "<p>Для просмотра контактов необходимо авторизоваться</p>" +
                "<p>Введите имя пользователя: <input required type=\"text\" name=\"login\"></p>" +
                "<p>Введите пароль: <input required type=\"password\" name=\"password\"></p>" +
                "<input type=\"submit\" value=\"Войти\" />" +
                "<p><a href=userStatistic.jsp> Посмотреть статистику </a></p>" +
                "</form>" +
                "</body></html>";
        return strHtml;
    }

    public String getMainMenuHTML(){
        int idUser = SecurityContextHolder.getLoggedUser().getId();
        String strHtml = "<!DOCTYPE HTML>" +
                "<html><head>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
                "<title>Меню</title></head>" +
                "<body>" +
                "<form action=\"contacts\" method=\"POST\">" +
                "<input type=\"hidden\" name=\"iduser\" value=\""+idUser+"\"/>"+
                "<input type=\"submit\" value=\"Показать список контактов\" />" +
                "</form>" +
                "<form action=\"groups\" method=\"POST\">" +
                "<input type=\"submit\" value=\"Показать список групп\" />" +
                "</form>" +
                "</body></html>";
        return strHtml;
    }
}
