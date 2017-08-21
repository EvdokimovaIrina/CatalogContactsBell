package catalogContacts.servlet;

import catalogContacts.controller.impl.ControllerHTMLImpl;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.TypeContact;
import catalogContacts.service.ContactService;
import catalogContacts.service.impl.ContactServiceImpl;
import catalogContacts.service.impl.UserServiceImpl;

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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();
        ControllerHTMLImpl controllerHTML = ControllerHTMLImpl.getInstance();

        String iduserStr = request.getParameter("iduser");
        if (iduserStr == null) {
            out.println("Ошибка авторизации");
            return;
        }
        String buttonAction = request.getParameter("buttonaction");

        try {
            synchronized (this) {
                UserServiceImpl.getInstance().setUserThread(Integer.parseInt(iduserStr));
                if (buttonAction != null) {
                    selectingTheActionForTheButton(buttonAction, request);
                }

                out.println(controllerHTML.showDetails(Integer.parseInt(request.getParameter("idcontact"))));
            }
        } catch (DaoException | NumberFormatException e) {
            out.println(e.getMessage() + ". " + e.getCause().getMessage());
        }

    }

    private void selectingTheActionForTheButton(String buttonAction, HttpServletRequest request) throws DaoException, NumberFormatException {
        ContactService contactService = ContactServiceImpl.getInstance();

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
}
