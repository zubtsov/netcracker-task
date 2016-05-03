package views;

import dao.MyDAO;
import entities.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean
@SessionScoped
public class StudentsView
{
    private static final Logger logger = LogManager.getLogger(StudentsView.class);

    @ManagedProperty(value = "#{myDAO}")
    private MyDAO myDAO;

    private List<Student> students;
    private List<Student> filteredStudents;

    @PostConstruct
    public void updateStudents()
    {
        logger.info("UPDATING STUDENTS LIST");
        students = myDAO.getStudents();
    }

    public void deleteStudent(Student s)
    {
        logger.info("DELETING STUDENT " + s.getId());
        students.remove(s);
        myDAO.deleteStudentById(s.getId());
    }

    public List<Student> getStudents()
    {
        return students;
    }

    public void setStudents(List<Student> students)
    {
        this.students = students;
    }

    public List<Student> getFilteredStudents()
    {
        return filteredStudents;
    }

    public void setFilteredStudents(List<Student> filteredStudents)
    {
        this.filteredStudents = filteredStudents;
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
