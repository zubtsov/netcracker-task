package entities;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Semester
{
    //Параллельные массивы далеко не самое элегантное решение, но пока в голову ничего лучше не пришло.
    private Date begDate;
    private Date expDate;

    private List<Exam> exams;
    private List<Discipline> disciplines;

    public Semester()
    {
        exams = new ArrayList<>();
        disciplines = new ArrayList<>();
    }

    public Semester(Date begDate, Date expDate)
    {
        this.begDate = begDate;
        this.expDate = expDate;
    }

    @XmlElement(name = "begDate", required = true)
    public Date getBegDate()
    {
        return begDate;
    }

    public void setBegDate(Date begDate)
    {
        this.begDate = begDate;
    }
    @XmlElement(name = "expDate", required = true)
    public Date getExpDate()
    {
        return expDate;
    }

    public void setExpDate(Date expDate)
    {
        this.expDate = expDate;
    }

    public List<Exam> getExams()
    {
        return exams;
    }
    @XmlElement(name = "exam")
    public void setExams(List<Exam> exams)
    {
        this.exams = exams;
    }

    @XmlElement(name = "disciplines")
    public List<Discipline> getDisciplines()
    {
        return disciplines;
    }

    public void setDisciplines(List<Discipline> disciplines)
    {
        this.disciplines = disciplines;
    }

    @Override
    public boolean equals(Object obj)
    {
        //Написан вообще говоря неверно
        Semester s = (Semester) obj;
        return begDate.equals(s.begDate) && expDate.equals(s.expDate) && (exams.equals(s.exams)) && (disciplines.equals(s.disciplines));
    }
}
