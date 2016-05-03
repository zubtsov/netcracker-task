package views;

import dao.MyDAO;
import entities.Group;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean
@SessionScoped
public class GroupsView
{
    private static final Logger logger = LogManager.getLogger(GroupsView.class);

    @ManagedProperty(value = "#{myDAO}")
    private MyDAO myDAO;

    private List<Group> groups;
    private List<Group> filteredGroups;

    @PostConstruct
    public void updateGroups()
    {
        groups = myDAO.getGroups();
        logger.info("UPDATING GROUPS LIST. LIST SIZE = " + groups.size());
    }

    public void deleteGroup(Group g)
    {
        logger.info("DELETING GROUP. GROUP ID = " + g.getId());
        myDAO.deleteGroupById(g.getId());
        groups.remove(g);
    }

    public List<Group> getGroups()
    {
        return groups;
    }

    public void setGroups(List<Group> groups)
    {
        this.groups = groups;
    }

    public List<Group> getFilteredGroups()
    {
        return filteredGroups;
    }

    public void setFilteredGroups(List<Group> filteredGroups)
    {
        this.filteredGroups = filteredGroups;
    }

    public MyDAO getMyDAO()
    {
        return myDAO;
    }

    public void setMyDAO(MyDAO myDAO)
    {
        this.myDAO = myDAO;
    }
}
