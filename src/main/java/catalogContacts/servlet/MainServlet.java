package catalogContacts.servlet;

import catalogContacts.context.SpringUtils;
import catalogContacts.controller.html.ControllerHTML;
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
    ControllerHTML controllerHTML;
    private static Logger logger = Logger.getLogger(MainServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        try {
            synchronized (this) {
                controllerHTML = (ControllerHTML) SpringUtils.getContext().getBean("controllerHTML");

                out.println(controllerHTML.getMainMenuHTML());
            }

        } catch (Exception e) {
            logger.error("Ошибка " + e.getMessage());
            out.println("Ошибка данных");
        }
    }

    public ControllerHTML getControllerHTML() {
        return controllerHTML;
    }

    public void setControllerHTML(ControllerHTML controllerHTML) {
        this.controllerHTML = controllerHTML;
    }
}
