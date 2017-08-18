package catalogContacts.servlet;

import catalogContacts.controller.impl.ControllerHTMLImpl;
import catalogContacts.dao.exception.DaoException;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        try {
            ControllerHTMLImpl controllerHTML = new ControllerHTMLImpl();
            response.getWriter().println(controllerHTML.getAuthorizationHTML());
        } catch (DaoException e) {
            throw new ServletException(e);
        }
    }
}
