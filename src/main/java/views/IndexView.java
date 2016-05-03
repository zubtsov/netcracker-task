package views;

import dao.MyDAO;
import entities.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean
@SessionScoped
public class IndexView
{
    private static final Logger logger = LogManager.getLogger(IndexView.class);

    private List<Student> students;
    private String pattern;

    @ManagedProperty(value = "#{myDAO}")
    MyDAO myDAO;

    public void updateStudentsList()
    {
        students = myDAO.getEm().createQuery(
                "SELECT s FROM Student s WHERE lower(s.name) LIKE :pat"
        ).setParameter("pat","%" + pattern.toLowerCase() + "%").getResultList();

        logger.info("SEARCHING STUDENTS BY NAME. LIST SIZE: " + students.size() + ", PATTERN: %" + pattern + "%");

    }

    public List<Student> getStudents()
    {
        return students;
    }

    public void setStudents(List<Student> students)
    {
        this.students = students;
    }

    public MyDAO getMyDAO()
    {
        return myDAO;
    }

    public void setMyDAO(MyDAO myDAO)
    {
        this.myDAO = myDAO;
    }

    public String getPattern()
    {
        return pattern;
    }

    public void setPattern(String pattern)
    {
        this.pattern = pattern;
    }
}
