package catalogContacts.servlet;

import catalogContacts.controller.ControllerHTML;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@Controller
public class AuthorizationServlet extends HttpServlet {

    private ControllerHTML controllerHTML;
    @Override
    @RequestMapping(value = "/home", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
             synchronized(this) {
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
