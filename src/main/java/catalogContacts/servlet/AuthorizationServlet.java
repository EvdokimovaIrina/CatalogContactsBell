package catalogContacts.servlet;

import catalogContacts.context.SpringUtils;
import catalogContacts.controller.ControllerHTML;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@WebServlet("/home")
public class AuthorizationServlet extends HttpServlet {
    ControllerHTML controllerHTML;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        synchronized (this) {
            controllerHTML = (ControllerHTML) SpringUtils.getContext().getBean("controllerHTML");
            response.getWriter().println(controllerHTML.getAuthorizationHTML());
        }
    }

    public ControllerHTML getControllerHTML() {
        return controllerHTML;
    }

    public void setControllerHTML(ControllerHTML controllerHTML) {
        this.controllerHTML = controllerHTML;
    }

    public AuthorizationServlet() {

    }

}
