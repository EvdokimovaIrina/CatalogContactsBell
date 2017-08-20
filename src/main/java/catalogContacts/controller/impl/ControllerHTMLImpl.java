package catalogContacts.controller.impl;

import catalogContacts.context.SecurityContextHolder;
import catalogContacts.controller.Controller;
import catalogContacts.dao.exception.DaoException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ControllerHTMLImpl implements Controller {
    private ContactService contactService;
    private GroupService groupService;
    private UserService userService;


    // Singleton
    public ControllerHTMLImpl() {
        this.contactService = ContactServiceImpl.getInstance();
        this.groupService = GroupServiceImpl.getInstance();
        this.userService = UserServiceImpl.getInstance();
    }

    public static ControllerHTMLImpl getInstance() {
        return ControllerHTMLImplHolder.instance;
    }

    private static class ControllerHTMLImplHolder {
        private static final ControllerHTMLImpl instance = new ControllerHTMLImpl();
    }

    //////

    public String showContactListStr(Integer numberGroup) throws DaoException {
        List<Contact> contactList = contactService.showContactList(numberGroup);
        int idUser = SecurityContextHolder.getLoggedUser().getId();
        String strHtml = "<!DOCTYPE HTML>" +
                "<html><body>" +
                "<form action=\"menu\" method=\"POST\">" +
                "<input type=\"hidden\" name=\"iduser\" value=\"" + idUser + "\"/>" +
                "<input type=\"submit\" value=\"На главное меню\" />" +
                "</form><br>"+
                "<p><h3>Контакты</h3></p></body></html>" +
                "<table>" +
                "<tbody>";

        for (Contact contact : contactList) {
            strHtml = strHtml + "<tr> " +
                    "<form action=\"datacontact\" method=\"POST\">" +
                    "<td>" + contact.getFio() + "</td>" +
                    "<td><input type=\"submit\" value=\"Просмотреть\" /></td>" +
                    "<td><input type=\"hidden\" name=\"iduser\" value=\"" + idUser + "\"/></td>" +
                    "<td><input type=\"hidden\" name=\"idcontact\" value=\"" + contact.getNumber() + "\"/></td>" +
                    "</form>" +
                    "<form action=\"contacts\" method=\"POST\">" +
                    "<td><input type=\"hidden\" name=\"buttonaction\" value=\"delete\"/></td>" +
                    "<td><input type=\"hidden\" name=\"idcontact\" value=\"" + contact.getNumber() + "\"/></td>" +
                    "<td><input type=\"hidden\" name=\"iduser\" value=\"" + idUser + "\"/></td>" +
                    "<td><input type=\"submit\" value=\"Удалить\"></td>" +
                    "</form>" +
                    "</tr>";
        }
        strHtml = strHtml + "</tbody>" +
                "</table>" +
                "<form action=\"contacts\" method=\"POST\">" +
                "<input type=\"hidden\" name=\"iduser\" value=\"" + idUser + "\"/>" +
                "<input type=\"hidden\" name=\"buttonaction\"  value=\"add\"/>" +
                "<p></p>"+
                "<p><h4>Новый контакт:</h4></p>"+
                "<input type=\"text\" name=\"namecontact\"/>  " +
                "<input type=\"submit\" value=\"Добавить\" />" +
                "</form>";
        return strHtml;
    }


    private String showContactDetailsList(Contact contact, int idUser) throws DaoException {

        List<ContactDetails> contactDetailsList = contact.getContactDetailsList();
        String strHtml = "<p><h4>Контактная информация:</h4></p>" +
                "<table>" +
                "<tbody>";
        for (ContactDetails contactDetails : contactDetailsList) {
            strHtml = strHtml +
                    "<tr> " +
                    "<form action=\"datacontact\" method=\"POST\">" +
                    "<td>" + contactDetails.getType() + ": </td>" +
                    "<td>" + contactDetails.getValue() + "</td>" +
                    "<td><input type=\"hidden\" name=\"iddetails\" value=\"" + contactDetails.getId() + "\"/></td>" +
                    "<td><input type=\"hidden\" name=\"iduser\" value=\"" + idUser + "\"/></td>" +
                    "<td><input type=\"hidden\" name=\"idcontact\" value=\"" + contact.getNumber() + "\"/></td>" +
                    "<td><input type=\"hidden\" name=\"buttonaction\" value=\"deletedetails\"/></td>" +
                    "<td><input type=\"submit\" value=\"Удалить\"></td>" +
                    "</form>" +
                    "</tr>";
        }
        strHtml = strHtml +
                "</tbody>" +
                "</table>" +
                "<p>Добавить контактную информацию:</p>" +
                "<form action=\"datacontact\" method=\"POST\">" +
                "<p> Тип: <select required name=\"type\" >";
        TypeContact[] typeContactArray = TypeContact.values();
        for (TypeContact type : typeContactArray) {
            strHtml = strHtml + "<option value=\"" + type.name() + "\">" + type.name() + "</option>";
        }
        strHtml = strHtml +
                "</select>  Значение: <input type=\"text\" name=\"value\">"+
                "<input type=\"hidden\" name=\"iduser\" value=\"" + idUser + "\"/>" +
                "<input type=\"hidden\" name=\"idcontact\" value=\"" + contact.getNumber() + "\"/>" +
                "<td><input type=\"hidden\" name=\"buttonaction\" value=\"adddetails\"/></td>" +
                "<input type=\"submit\" value=\"Добавить\"></p>" +
                "</form>";


        return strHtml;
    }

    private String showGroupListContact(Contact contact, int idUser) throws DaoException {
        List<Group> groupList = contact.getGroupList();
        List<Integer> listId = new ArrayList<>();
        String strHtml = "<p><h4>Группы контактов:</h4></p>" +
                "<table>" +
                "<tbody>";
        for (Group group : groupList) {
            listId.add(group.getNumber());
            strHtml = strHtml +
                    "<tr> " +
                    "<form action=\"datacontact\" method=\"POST\">" +
                    "<td>" + group.getName() + "</td>" +
                    "<td><input type=\"hidden\" name=\"idgroup\" value=\"" + group.getNumber() + "\"/></td>" +
                    "<td><input type=\"hidden\" name=\"iduser\" value=\"" + idUser + "\"/></td>" +
                    "<td><input type=\"hidden\" name=\"idcontact\" value=\"" + contact.getNumber() + "\"/></td>" +
                    "<td><input type=\"hidden\" name=\"buttonaction\" value=\"deletegroup\"/></td>" +
                    "<td><input type=\"submit\" value=\"Удалить\"></td>" +
                    "</form>" +
                    "</tr>";
        }

        strHtml = strHtml +
                "</table>" +
                "</tbody>" +
                "<p>Добавить группу:</p>" +
                "<form id=\"groupAddForm\" action=\"datacontact\" method=\"POST\">" +
                "<td><input type=\"hidden\" name=\"iduser\" value=\"" + idUser + "\"/></td>" +
                "<td><input type=\"hidden\" name=\"idcontact\" value=\"" + contact.getNumber() + "\"/></td>" +
                "<td><input type=\"hidden\" name=\"buttonaction\" value=\"addgroup\"/></td>" +
                "<p><select required name=\"idgroup\">";

        List<Group> groupListALL = groupService.showGroupList();
        for (Group group : groupListALL) {
            if (!(listId.contains(group.getNumber()))) {
                strHtml = strHtml +
                        "<option value=\"" + group.getNumber() + "\">" + group.getName() + "</option>";
            }
        }
        strHtml = strHtml +
                "</select>" +
                "<input type=\"submit\" value=\"Добавить группу\"></p>" +
                "</form>" +
                "</tbody>" +
                "</table>";

        return strHtml;
    }

    public String showDetails(Integer numberContact) throws DaoException {
        Contact contact = contactService.getContactByNumber(numberContact);
        int idUser = SecurityContextHolder.getLoggedUser().getId();
        String strHtml = "<!DOCTYPE HTML>" +
                "<html><body>" +
                "<form action=\"menu\" method=\"POST\">" +
                "<input type=\"hidden\" name=\"iduser\" value=\"" + idUser + "\"/>" +
                "<input type=\"submit\" value=\"На главное меню\" />" +
                "</form><br>"+
                "<p><h2> " + contact.getFio() + " </h2></p></body></html>" +
                "<form action=\"datacontact\" method=\"POST\">" +
                "<input type=\"hidden\" name=\"iduser\" value=\"" + idUser + "\"/>" +
                "<input type=\"hidden\" name=\"idcontact\" value=\"" + contact.getNumber() + "\"/>" +
                "<input type=\"text\" name=\"newnamecontact\"/>" +
                "<td><input type=\"hidden\" name=\"buttonaction\" value=\"changecontactname\"/></td>" +
                "<input type=\"submit\" value=\"изменить наименование\" />" +
                "</form><br>"+


                showContactDetailsList(contact, idUser) +
                showGroupListContact(contact, idUser);
        return strHtml;
    }


    public String showGroupList() throws DaoException {
        int idUser = SecurityContextHolder.getLoggedUser().getId();
        List<Group> groupList = groupService.showGroupList();
        String strHtml = "<!DOCTYPE HTML>" +
                "<html><body>" +
                "<form action=\"menu\" method=\"POST\">" +
                "<input type=\"hidden\" name=\"iduser\" value=\"" + idUser + "\"/>" +
                "<input type=\"submit\" value=\"На главное меню\" />" +
                "</form><br>"+
                "<p><h3>Группы:</h3></p></body></html>" +
                "<form action=\"groups\" method=\"POST\">" +
                "<table>" +
                "<tbody>";
        for (Group group : groupList) {
            strHtml = strHtml + "<tr> " +
                    "<td>" + group.getName() + "</td>" +
                    "<input type=\"hidden\" name=\"idgroup\" value=\"" + group.getNumber() + "\"/>" +
                    "<input type=\"hidden\" name=\"iduser\" value=\"" + idUser + "\"/>" +
                    "<td><input type=\"hidden\" name=\"buttonaction\" value=\"delete\"/></td>" +
                    "<input type=\"submit\" value=\"Удалить\" />" +
                    "</tr>";
        }
        strHtml = strHtml + "</tbody>" +
                "</table>" +
                "<input type=\"text\" name=\"namegroup\"/>" +
                "<input type=\"hidden\" name=\"iduser\" value=\"" + idUser + "\"/>" +
                "<input type=\"hidden\" name=\"buttonaction\"  value=\"add\"/>" +
                "<input type=\"submit\" value=\"Добавить\" />" +
                "</form>";
        return strHtml;
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
        userService.setUserThread(login, password);
        if (SecurityContextHolder.getLoggedUser() == null) {
            return false;
        } else {
            return true;
        }
    }


    public String getAuthorizationHTML() {
        String strHtml = "<!DOCTYPE HTML>" +
                "<html><head>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
                "<title>Авторизация</title></head>" +
                "<body>" +
                "<form action=\"menu\" method=\"POST\">" +
                "<p><h3>Для просмотра контактов укажите регистрационные данные:</h3></p>" +
                "<p>Введите имя пользователя: <input required type=\"text\" name=\"login\"></p>" +
                "<p>Введите пароль: <input required type=\"password\" name=\"password\"></p>" +
                "<input type=\"submit\" value=\"Войти\" />" +
                "<p><a href=userStatistic.jsp> Посмотреть статистику </a></p>" +
                "</form>" +
                "</body></html>";
        return strHtml;
    }

    public String getMainMenuHTML() {
        int idUser = SecurityContextHolder.getLoggedUser().getId();
        String strHtml = "<!DOCTYPE HTML>" +
                "<html><head>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
                "<title>Меню</title></head>" +
                "<body>" +
                "<form action=\"contacts\" method=\"POST\">" +
                "<input type=\"hidden\" name=\"iduser\" value=\"" + idUser + "\"/>" +
                "<input type=\"submit\" value=\"Cписок контактов\" />" +
                "</form>" +
                "<form action=\"groups\" method=\"POST\">" +
                "<input type=\"hidden\" name=\"iduser\" value=\"" + idUser + "\"/>" +
                "<input type=\"submit\" value=\"Список групп\" />" +
                "</form>" +
                "</body></html>";
        return strHtml;
    }
}
