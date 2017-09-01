package catalogContacts.servlet;

import catalogContacts.controller.ControllerHTML;
import catalogContacts.dao.exception.DaoException;
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

/**
 *
 */
@Controller
public class MainServlet extends HttpServlet {
    ControllerHTML controllerHTML;
    private static Logger logger = Logger.getLogger(MainServlet.class.getName());

    @Override
    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();
        try {
            String iduserStr = request.getParameter("iduser");
            synchronized (this) {
                if (iduserStr == null) {
                    String login = request.getParameter("login");
                    String password = request.getParameter("password");
                    if (login != null & password != null) {
                        if (controllerHTML.isSetUserThread(login, password)) {
                            logger.info("Авторизация пользователя");
                            out.println(controllerHTML.getMainMenuHTML());
                        } else {
                            logger.info("Авторизация не выполнена, пользователь не найден");
                            out.println("Авторизация не выполнена");
                        }
                    }
                } else {
                    out.println(controllerHTML.getMainMenuHTML());
                }
            }
        } catch (DaoException e) {
            out.println("Ошибка получения данных");
        } catch (Exception e) {
            logger.error("Ошибка " + e.getMessage());
            out.println("Ошибка данных");
        }

    }

    @Override
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public ControllerHTML getControllerHTML() {
        return controllerHTML;
    }

    public void setControllerHTML(ControllerHTML controllerHTML) {
        this.controllerHTML = controllerHTML;
    }
}
