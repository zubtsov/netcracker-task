package views;

import dao.MyDAO;
import entities.Discipline;
import entities.Exam;
import entities.Semester;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@SessionScoped
public class SemesterView implements Converter
{
    private static final Logger logger = LogManager.getLogger(SemesterView.class);

    @ManagedProperty(value = "#{myDAO}")
    MyDAO myDAO;

    @ManagedProperty(value = "#{studentView}")
    StudentView sv;

    private Semester semester;
    private boolean newSemester;

    public String update()
    {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String semesterNumber = req.getParameter("semesterNumber");

        if (semesterNumber != null)
        {
            int semNum = Integer.parseInt(semesterNumber);
            semester = sv.getCurrentStudent().getSemesters().get(semNum);
            newSemester = false;
        } else
        {
            newSemester = true;
            semester = new Semester();
        }

        logger.info("UPDATING SEMESTER" + semester.toString());

        return "semester";
    }

    public String saveSemester()
    {
        logger.info("SAVING SEMESTER "  + semester.toString());
        if (newSemester)
            sv.getCurrentStudent().getSemesters().add(semester);
        return "student";
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s)
    {
        for (Discipline d : semester.getDisciplines())
        {
            if (d.getName().equals(s))
                return d;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o)
    {
        return ((Discipline) o).getName();
    }

    public MyDAO getMyDAO()
    {
        return myDAO;
    }

    public void setMyDAO(MyDAO myDAO)
    {
        this.myDAO = myDAO;
    }

    public StudentView getSv()
    {
        return sv;
    }

    public void setSv(StudentView sv)
    {
        this.sv = sv;
    }

    public Semester getSemester()
    {
        return semester;
    }

    public void setSemester(Semester semester)
    {
        this.semester = semester;
    }

    public void addDiscipline()
    {
        logger.info("ADDING NEW DISCIPLINE IN SEMESTER " + semester.toString());
        semester.getDisciplines().add(new Discipline());
    }

    public void deleteDiscipline(Discipline disc)
    {
        logger.info("DELETING DISCIPLINE " + disc.toString() + " FROM SEMESTER " + semester.toString());
        semester.getDisciplines().remove(disc);
        //По правильному надо прикрутить валидатор на ссылку "Удалить",
        // который бы запрещал удаление использующихся предметов
        for(int i = 0; i<semester.getExams().size(); i++)
        {
            if (semester.getExams().get(i).getDisc().equals(disc))
            {
                semester.getExams().remove(i--);
            }
        }
    }

    public void addExam()
    {
        logger.info("ADDING NEW EXAM IN SEMESTER " + semester.toString());
        semester.getExams().add(new Exam());
    }

    public void deleteExam(Exam exam)
    {
        logger.info("DELETING EXAM " + exam.toString() + " FROM SEMESTER " + semester.toString());
        semester.getExams().remove(exam);
    }
}
