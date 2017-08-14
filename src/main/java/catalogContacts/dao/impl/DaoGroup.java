package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.dao.mappers.impl.ModelMapperGroup;
import catalogContacts.model.Group;
import catalogContacts.context.SecurityContextHolder;
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
        return modelMapperGroup.creatObject(executionQuery(selectGetGroup,id));
    }

    public List<Group> getAll() throws DaoException {
        return modelMapperGroup.creatObjectList(executionQuery(selectGetListGroup,
                SecurityContextHolder.getLoggedUser().getId(),""));
    }

    public List<Group> findByName(String name) throws DaoException {
        return modelMapperGroup.creatObjectList(executionQuery(selectGetListGroup,
                SecurityContextHolder.getLoggedUser().getId(),name));
    }

}
