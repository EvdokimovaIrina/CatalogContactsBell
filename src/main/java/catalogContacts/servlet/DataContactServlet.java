package catalogContacts.servlet;

import catalogContacts.context.SpringUtils;
import catalogContacts.controller.html.ControllerHTML;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.TypeContact;
import catalogContacts.service.ContactService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
@WebServlet("/datacontact")
public class DataContactServlet extends HttpServlet {
    ControllerHTML controllerHTML;
    ContactService contactService;

    private static Logger logger = Logger.getLogger(DataContactServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();

        String buttonAction = request.getParameter("buttonaction");

        try {
            synchronized (this) {
                controllerHTML = (ControllerHTML) SpringUtils.getContext().getBean("controllerHTML");

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
        contactService = (ContactService) SpringUtils.getContext().getBean("contactServise");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public ControllerHTML getControllerHTML() {
        return controllerHTML;
    }

    public void setControllerHTML(ControllerHTML controllerHTML) {
        this.controllerHTML = controllerHTML;
    }

    public ContactService getContactService() {
        return contactService;
    }

    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }
}
