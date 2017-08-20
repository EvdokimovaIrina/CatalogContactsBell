package catalogContacts.servlet;

import catalogContacts.controller.impl.ControllerHTMLImpl;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.service.GroupService;
import catalogContacts.service.impl.ContactServiceImpl;
import catalogContacts.service.impl.GroupServiceImpl;
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
@WebServlet("/groups")
public class ListGroup extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        ControllerHTMLImpl controllerHTML = new ControllerHTMLImpl();

        PrintWriter out = response.getWriter();
        String iduserStr = request.getParameter("iduser");
        if (iduserStr==null){
            out.println("Ошибка авторизации");
            return;
        }
        String buttonAction = request.getParameter("buttonaction");
        try {
            UserServiceImpl.getInstance().setUserThread(Integer.parseInt(iduserStr));
            if (buttonAction != null) {
                selectingTheActionForTheButton(buttonAction, request);
            }
            out.println(controllerHTML.showGroupList());

        } catch (DaoException|NumberFormatException e) {
            out.println(e.getMessage());
        }
    }

    private void selectingTheActionForTheButton(String buttonAction, HttpServletRequest request) throws DaoException, NumberFormatException {
        switch (buttonAction){
            case "add":
                GroupServiceImpl.getInstance().addGroup(request.getParameter("namegroup"));
                break;
            case "delete":
                GroupServiceImpl.getInstance().deleteGroup(Integer.parseInt(request.getParameter("idgroup")));
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
