package views;

import dao.MyDAO;
import entities.Group;
import entities.Semester;
import entities.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ManagedBean
@SessionScoped
public class StudentView implements Converter
{
    private static final Logger logger = LogManager.getLogger(StudentView.class);

    @ManagedProperty(value = "#{myDAO}")
    private MyDAO myDAO;

    private Student currentStudent;

    private List<Group> groupList;

    private boolean newStudent;

    public String update()
    {
        logger.info("UPDATING STUDENT");

        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String studId = req.getParameter("studId");

        newStudent = studId == null ? true : false;

        groupList = myDAO.getGroups();

        if (newStudent)
        {
            currentStudent = new Student();
        } else
        {
            currentStudent = myDAO.getStudentById(Integer.parseInt(studId));
            currentStudent.setGroup(groupList.stream().filter(g -> g.getId() == currentStudent.getGroup().getId()).findFirst().get());
        }

        return "student";
    }

    public String saveStudent()
    {
        logger.info("SAVING STUDENT");

        if (newStudent)
        {
            myDAO.add(currentStudent);
        } else
        {
            myDAO.merge(currentStudent);
        }

        newStudent=false;

        return "student";
    }

    public String getSemesterNumber(Semester s)
    {
        for (int i = 0; i < currentStudent.getSemesters().size(); i++)
        {
            if (currentStudent.getSemesters().get(i).equals(s))
            {
                return String.valueOf(i);
            }
        }
        return null;
    }

    public void removeSemester(Semester semester)
    {
        logger.info("REMOVING STUDENT");
        currentStudent.getSemesters().remove(semester);
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s)
    {
        return groupList.stream().filter(g -> g.getId() == Integer.parseInt(s)).findFirst().get();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o)
    {
        Group group = (Group) o;
        return String.valueOf(group.getId());
    }

    public MyDAO getMyDAO()
    {
        return myDAO;
    }

    public void setMyDAO(MyDAO myDAO)
    {
        this.myDAO = myDAO;
    }

    public Student getCurrentStudent()
    {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent)
    {
        this.currentStudent = currentStudent;
    }

    public List<Group> getGroupList()
    {
        return groupList;
    }

    public void setGroupList(List<Group> groupList)
    {
        this.groupList = groupList;
    }

}
