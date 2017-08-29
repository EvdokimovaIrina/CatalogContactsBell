package catalogContacts.servlet;

import catalogContacts.controller.impl.ControllerHTMLImpl;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.service.impl.GroupServiceImpl;
import catalogContacts.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

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
    private static Logger logger = Logger.getLogger(DataContactServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        ControllerHTMLImpl controllerHTML = new ControllerHTMLImpl();

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
                UserServiceImpl.getInstance().setUserThread(Integer.parseInt(iduserStr));
                if (buttonAction != null) {
                    selectingTheActionForTheButton(buttonAction, request);
                }
                out.println(controllerHTML.showGroupList());
            }
        } catch (DaoException e) {
            out.println("Ошибка получения данных");
        }catch (Exception e) {
            logger.error("Ошибка " + e.getMessage());
            out.println("Ошибка данных");
        }

    }

    private void selectingTheActionForTheButton(String buttonAction, HttpServletRequest request) throws DaoException, NumberFormatException {
        switch (buttonAction) {
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
        doPost(request, response);
    }
}
