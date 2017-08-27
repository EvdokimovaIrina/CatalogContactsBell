package catalogContacts.servlet;

import catalogContacts.controller.impl.ControllerHTMLImpl;
import catalogContacts.dao.exception.DaoException;
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
@WebServlet("/menu")
public class MainServlet extends HttpServlet {
    private static Logger logger=Logger.getLogger("simple");
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("log message from TestLogServlet");
        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();
        try {
            ControllerHTMLImpl controllerHTML = ControllerHTMLImpl.getInstance();
            String iduserStr = request.getParameter("iduser");
            synchronized (this) {
                if (iduserStr == null) {
                    String login = request.getParameter("login");
                    String password = request.getParameter("password");
                    if (login != null & password != null) {
                        if (controllerHTML.isSetUserThread(login, password)) {
                            out.println(controllerHTML.getMainMenuHTML());
                        } else {
                            out.println("Авторизация не выполнена");
                        }
                    }
                } else {
                    out.println(controllerHTML.getMainMenuHTML());
                }
            }
        } catch (DaoException e) {
            out.println("Ошибка получения данных");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
