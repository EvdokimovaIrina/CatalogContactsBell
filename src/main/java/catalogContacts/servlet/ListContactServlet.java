package catalogContacts.servlet;

import catalogContacts.controller.impl.ControllerHTMLImpl;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.service.impl.ContactServiceImpl;
import catalogContacts.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 */
@WebServlet("/contacts")
public class ListContactServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();
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
                    out.println(selectingTheActionForTheButton(buttonAction, request, response));
                } else {

                    out.println(ControllerHTMLImpl.getInstance().showContactListStr(null));
                }
            }
        } catch (DaoException | NumberFormatException e) {
            out.println(e.getMessage());
        }
    }

    private String selectingTheActionForTheButton(String buttonAction, HttpServletRequest request, HttpServletResponse response) throws DaoException, NumberFormatException, IOException {

            switch (buttonAction) {
                case "add":
                    ContactServiceImpl.getInstance().addContact(request.getParameter("namecontact"));
                    return ControllerHTMLImpl.getInstance().showContactListStr(null);
                case "delete":
                    ContactServiceImpl.getInstance().deleteContact(Integer.parseInt(request.getParameter("idcontact")));
                    return ControllerHTMLImpl.getInstance().showContactListStr(null);
                case "searchForContacts":
                    return ControllerHTMLImpl.getInstance().findByName(request.getParameter("searchnamecontact"));

            }

        return "";
    }
}
