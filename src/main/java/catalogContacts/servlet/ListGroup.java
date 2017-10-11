package catalogContacts.servlet;

import catalogContacts.context.SpringUtils;
import catalogContacts.controller.html.ControllerHTML;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.service.GroupService;
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
    ControllerHTML controllerHTML;
    GroupService groupService;

    private static Logger logger = Logger.getLogger(DataContactServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();

        String buttonAction = request.getParameter("buttonaction");
        try {
            synchronized (this) {
                controllerHTML = (ControllerHTML) SpringUtils.getContext().getBean("controllerHTML");
                groupService = (GroupService) SpringUtils.getContext().getBean("groupServise");

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
                groupService.addGroup(request.getParameter("namegroup"));
                break;
            case "delete":
                groupService.deleteGroup(Integer.parseInt(request.getParameter("idgroup")));
                break;
            case "changegroupname":
                groupService.changeGroup(Integer.parseInt(request.getParameter("idgroup")),request.getParameter("namegroup"));
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public ControllerHTML getControllerHTML() {
        return controllerHTML;
    }

    public void setControllerHTML(ControllerHTML controllerHTML) {
        this.controllerHTML = controllerHTML;
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }
}
