package catalogContacts.controller.html.impl;

import catalogContacts.controller.html.ControllerHTML;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.Group;
import catalogContacts.model.TypeContact;
import catalogContacts.service.ContactService;
import catalogContacts.service.GroupService;
import catalogContacts.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class ControllerHTMLImpl implements ControllerHTML {

    private ContactService contactService;
    private GroupService groupService;
    private UserService userService;


    private String contactListHTML(List<Contact> contactList){
        String strHtml ="";
        for (Contact contact : contactList) {
            strHtml = strHtml + "<tr> " +
                    "<form action=\"datacontact\" method=\"POST\">" +
                    "<td>" + contact.getFio() + "</td>" +
                    "<td><input type=\"submit\" value=\"Просмотреть\" /></td>" +
                    "<td><input type=\"hidden\" name=\"idcontact\" value=\"" + contact.getNumber() + "\"/></td>" +
                    "</form>" +
                    "<form action=\"contacts\" method=\"POST\">" +
                    "<td><input type=\"hidden\" name=\"buttonaction\" value=\"delete\"/></td>" +
                    "<td><input type=\"hidden\" name=\"idcontact\" value=\"" + contact.getNumber() + "\"/></td>" +
                    "<td><input type=\"submit\" value=\"Удалить\"></td>" +
                    "</form>" +
                    "</tr>";
        }
        return strHtml;
    }

    private String searchForContactsHTML(){
        return "<form action=\"contacts\" method=\"POST\">" +
                "<input type=\"text\" name=\"searchnamecontact\"/>" +
                "<input type=\"hidden\" name=\"buttonaction\" value=\"searchForContacts\"/>" +
                "<input type=\"submit\" value=\"Найти\" />" +
                "</form><br>"+
                "<form action=\"contacts\" method=\"POST\">" +
                "  <input type=\"submit\" value=\"Показать весь список\" />" +
                "</form><br>";



    }

    public String showContactListStr(Integer numberGroup) throws DaoException {
        List<Contact> contactList;
        synchronized (this) {
            contactList = contactService.showContactList(numberGroup);
        }

        String strHtml = menuListHTML(contactList);
        return strHtml;
    }

    private String menuListHTML(List<Contact> contactList){

        String strHtml = "<!DOCTYPE HTML>" +
                "<html><body>" +
                "<form action=\"menu\" method=\"GET\">" +
                "<input type=\"submit\" value=\"На главное меню\" />" +
                "</form><br>" +
                "<p><h3>Контакты</h3></p></body></html>" +
                searchForContactsHTML()+
                "<table>" +
                "<tbody>"+
                contactListHTML(contactList)+
                "</tbody>" +
                "</table>" +
                "<form action=\"contacts\" method=\"POST\">" +
                "<input type=\"hidden\" name=\"buttonaction\"  value=\"add\"/>" +
                "<p></p>" +
                "<p><h4>Новый контакт:</h4></p>" +
                "<input type=\"text\" name=\"namecontact\"/>  " +
                "<input type=\"submit\" value=\"Добавить\" />" +
                "</form>";
        return strHtml;
    }

    private String showContactDetailsList(Contact contact) throws DaoException {
        List<ContactDetails> contactDetailsList = contactService.showContactDetails(contact);

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
                "</select>  Значение: <input type=\"text\" name=\"value\">" +
                 "<input type=\"hidden\" name=\"idcontact\" value=\"" + contact.getNumber() + "\"/>" +
                "<td><input type=\"hidden\" name=\"buttonaction\" value=\"adddetails\"/></td>" +
                "<input type=\"submit\" value=\"Добавить\"></p>" +
                "</form>";


        return strHtml;
    }

    private String showGroupListContact(Contact contact) throws DaoException {

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
        Contact contact;
        synchronized (this) {
            contact = contactService.getContactByNumber(numberContact);
        }
        String strHtml = "<!DOCTYPE HTML>" +
                "<html><body>" +
                "<form action=\"menu\" method=\"GET\">" +
                "<input type=\"submit\" value=\"На главное меню\" />" +
                "</form><br>" +
                "<p><h2> " + contact.getFio() + " </h2></p></body></html>" +
                "<form action=\"datacontact\" method=\"POST\">" +
                 "<input type=\"hidden\" name=\"idcontact\" value=\"" + contact.getNumber() + "\"/>" +
                "<input type=\"text\" name=\"newnamecontact\"/>" +
                "<td><input type=\"hidden\" name=\"buttonaction\" value=\"changecontactname\"/></td>" +
                "<input type=\"submit\" value=\"изменить наименование\" />" +
                "</form><br>" +


                showContactDetailsList(contact) +
                showGroupListContact(contact);
        return strHtml;
    }


    public String showGroupList() throws DaoException {
        List<Group> groupList;
        synchronized (this) {
            groupList = groupService.showGroupList();
        }
        String strHtml = "<!DOCTYPE HTML>" +
                "<html><body>" +
                "<form action=\"menu\" method=\"GET\">" +
                "<input type=\"submit\" value=\"На главное меню\" />" +
                "</form><br>" +
                "<p><h3>Группы:</h3></p></body></html>" +
                "<table>" +
                "<tbody>";
        for (Group group : groupList) {
            strHtml = strHtml + "<tr> " +
                    "<form action=\"groups\" method=\"POST\">" +
                    "<td>" + group.getName() + "</td>" +
                    "<input type=\"hidden\" name=\"idgroup\" value=\"" + group.getNumber() + "\"/>" +
                    "<td><input type=\"hidden\" name=\"buttonaction\" value=\"delete\"/>" +
                    "<input type=\"submit\" value=\"Удалить\" /></td>" +
                    "</form>" +
                    "</tr>";
        }
        strHtml = strHtml + "</tbody>" +
                "</table>" +
                "<form action=\"groups\" method=\"POST\">" +
                "<input type=\"text\" name=\"namegroup\"/>" +
                "<input type=\"hidden\" name=\"buttonaction\"  value=\"add\"/>" +
                "<input type=\"submit\" value=\"Добавить\" />" +
                "</form>";
        return strHtml;
    }


    public String findByName(String name) throws DaoException {
        List<Contact> contactList;
        synchronized (this) {
            contactList = contactService.findByName(name);
        }
        String strHtml = menuListHTML(contactList);
        return strHtml;
    }

    public String getMainMenuHTML() {

        String strHtml = "<!DOCTYPE HTML>" +
                "<html><head>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
                "<title>Меню</title></head>" +
                "<body>" +
                "<form action=\"contacts\" method=\"POST\">" +
                "<input type=\"submit\" value=\"Cписок контактов\" />" +
                "</form>" +
                "<form action=\"groups\" method=\"POST\">" +
                "<input type=\"submit\" value=\"Список групп\" />" +
                "<p><a href=userStatistic.jsp> Посмотреть статистику </a></p>" +
                "</form>" +
                "<form action=\"logout\">" +
                "<input type=\"submit\" value=\"Выйти\" />" +
                "</form>" +
                "</body></html>";
        return strHtml;
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
