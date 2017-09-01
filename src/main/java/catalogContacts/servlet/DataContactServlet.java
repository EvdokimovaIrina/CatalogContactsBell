package catalogContacts.servlet;

import catalogContacts.controller.ControllerHTML;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.TypeContact;
import catalogContacts.service.ContactService;
import catalogContacts.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Controller
public class DataContactServlet extends HttpServlet {
    ControllerHTML controllerHTML;
    UserService userService;
    ContactService contactService;

    private static Logger logger = Logger.getLogger(DataContactServlet.class.getName());

    @Override
    @RequestMapping(value = "/datacontact", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();

        String iduserStr = request.getParameter("iduser");
        if (iduserStr == null) {
            logger.error("Ошибка авторизации");
            out.println("Ошибка авторизации");
            return;
        }
        String buttonAction = request.getParameter("buttonaction");

        try {
            synchronized (this) {
                userService.setUserThread(Integer.parseInt(iduserStr));
                if (buttonAction != null) {
                    selectingTheActionForTheButton(buttonAction, request);
                }

                out.println(controllerHTML.showDetails(Integer.parseInt(request.getParameter("idcontact"))));
            }
        } catch (DaoException | NumberFormatException e) {
            out.println("Ошибка получения данных");
        } catch (Exception e) {
            logger.error("Ошибка " + e.getMessage());
            out.println("Ошибка данных");
        }

    }

    private void selectingTheActionForTheButton(String buttonAction, HttpServletRequest request) throws DaoException, NumberFormatException {
        int idContact = Integer.parseInt(request.getParameter("idcontact"));
        switch (buttonAction) {
            case "addgroup":
                contactService.addGroupToContact(idContact, Integer.parseInt(request.getParameter("idgroup")));
                break;
            case "deletegroup":
                contactService.deleteGroupToContact(idContact, Integer.parseInt(request.getParameter("idgroup")));
                break;
            case "adddetails":
                Map<TypeContact, String> mapDetails = new HashMap<>();
                mapDetails.put(TypeContact.valueOf(request.getParameter("type")), request.getParameter("value"));
                contactService.addContactDetails(idContact, mapDetails);
                break;
            case "deletedetails":
                contactService.deleteContactDetails(idContact, Integer.parseInt(request.getParameter("iddetails")));
                break;
            case "changecontactname":
                contactService.changeContact(idContact, request.getParameter("newnamecontact"));
                break;
        }

    }


    @Override
    @RequestMapping(value = "/datacontact", method = RequestMethod.GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public ControllerHTML getControllerHTML() {
        return controllerHTML;
    }

    public void setControllerHTML(ControllerHTML controllerHTML) {
        this.controllerHTML = controllerHTML;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ContactService getContactService() {
        return contactService;
    }

    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }
}
