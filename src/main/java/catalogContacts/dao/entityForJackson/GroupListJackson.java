package catalogContacts.dao.entityForJackson;

import catalogContacts.model.Group;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

/**
 *
 */
public class GroupListJackson {
    @JacksonXmlElementWrapper(localName = "group", useWrapping = false)
    private List<Group> group;

    public GroupListJackson() {
    }

    public GroupListJackson(List<Group> group) {
        this.group = group;
    }

    public List<Group> getGroup() {
        return group;
    }

    public void setGroup(List<Group> group) {
        this.group = group;
    }
}
