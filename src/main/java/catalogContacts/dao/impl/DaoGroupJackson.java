package catalogContacts.dao.impl;


import catalogContacts.dao.entityForJackson.GroupListJackson;
import catalogContacts.dao.exception.DaoXmlException;
import catalogContacts.model.Group;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 */
public class DaoGroupJackson extends DaoGroup{

    public Group getObject(int id) throws DaoXmlException {

        GroupListJackson groupListJackson = getGroupListJackson();
        for (Group group: groupListJackson.getGroup()) {
            if (group.getNumber()==id){
                return group;
            }
        }
        return null;
    }

    public List<Group> getAll() throws DaoXmlException {
        List<Group> groupList = xmlToListObject();
        return  groupList;
    }

    public List<Group> findByName(String name) throws DaoXmlException {
        List<Group> groupList = null;
        GroupListJackson groupListJackson = getGroupListJackson();
        for (Group group: groupListJackson.getGroup()) {
            if (group.getName().contains(name)){
                groupList.add(group);
            }
        }
        return groupList;
    }

    public int toFormANewId() throws DaoXmlException {
        GroupListJackson groupListJackson = getGroupListJackson();
        int max = 0;
        for (Group group : groupListJackson.getGroup()) {
            int id = group.getNumber();
            if (max < id) {
                max = id;
            }
        }
        return max;
    }

    public List<Group> xmlToListObject() throws DaoXmlException {
        List<Group> groupList = groupListJacksonToGroupList();
        return groupList;
    }

    private List<Group> groupListJacksonToGroupList() throws DaoXmlException {

        return getGroupListJackson().getGroup();
    }

    private GroupListJackson getGroupListJackson() throws DaoXmlException {
        GroupListJackson groupListJackson = null;
        try {
            ObjectMapper objectMapper = new XmlMapper();
            groupListJackson = objectMapper.readValue(StringUtils.toEncodedString(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8), GroupListJackson.class);

        } catch (IOException | NullPointerException e) {
            throw new DaoXmlException("Ошибка чтения данных");
        }
        return groupListJackson;
    }
}
