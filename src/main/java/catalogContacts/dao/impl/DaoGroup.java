package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.dao.mappers.impl.ModelMapperGroup;
import catalogContacts.model.Group;
import catalogContacts.context.SecurityContextHolder;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 */
public class DaoGroup extends DaoParsing implements CrudDAO<Group> {
    private ModelMapper<Group> modelMapperGroup;
    private final String selectInsertGroup = "SELECT insertgroup(?,?)";
    private final String selectChangeGroup = "SELECT changegroup(?,?)";
    private final String selectDeleteGroup = "SELECT deletegroup(?)";
    private final String selectGetGroup = "SELECT * FROM getgroup(?)";
    private final String selectGetListGroup = "SELECT * FROM getlistgroup(?,?)";

    public DaoGroup() throws DaoException {
        super();
        modelMapperGroup = new ModelMapperGroup();
    }

    public void create(Group group) throws DaoException {
        executionQuery(selectInsertGroup, SecurityContextHolder.getLoggedUser().getId(),group.getName());
    }

    public void update(Group group) throws DaoException {
        executionQuery(selectChangeGroup,group.getNumber(),group.getName());
    }

    public void delete(int number) throws DaoException {
        executionQuery(selectDeleteGroup,number);
    }

    public Group getObject(int id) throws DaoException {
        ResultSet result = executionQuery(selectGetGroup,id);
        return modelMapperGroup.creatObject(result);
    }

    public List<Group> getAll() throws DaoException {
        ResultSet result = executionQuery(selectGetListGroup,SecurityContextHolder.getLoggedUser().getId(),"");
        return modelMapperGroup.creatObjectList(result);
    }

    public List<Group> findByName(String name) throws DaoException {
        ResultSet result = executionQuery(selectGetListGroup,SecurityContextHolder.getLoggedUser().getId(),name);
        return modelMapperGroup.creatObjectList(result);
    }

}
