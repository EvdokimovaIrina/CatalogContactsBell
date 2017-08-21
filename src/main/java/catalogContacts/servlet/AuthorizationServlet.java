package catalogContacts.servlet;

import catalogContacts.controller.impl.ControllerHTMLImpl;
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

            ControllerHTMLImpl controllerHTML = ControllerHTMLImpl.getInstance();
            synchronized(this) {
                response.getWriter().println(controllerHTML.getAuthorizationHTML());
            }
    }
}
