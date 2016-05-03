package entities;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

public class Exam
{
    private Discipline disc;
    private Integer mark;
    private Date date;

    public Exam()
    {

    }

    public Exam(Discipline disc, Integer mark, Date date)
    {
        this.disc = disc;
        this.mark = mark;
        this.date = date;
    }

    @XmlElement(name = "date", required = true)
    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
    @XmlElement(name = "discipline", required = true)
    public Discipline getDisc()
    {
        return disc;
    }

    public void setDisc(Discipline disc)
    {
        this.disc = disc;
    }
    @XmlElement(name = "mark", required = true)
    public Integer getMark()
    {
        return mark;
    }

    public void setMark(Integer mark)
    {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object obj)
    {
        Exam e = (Exam) obj;
        return date.equals(e.date) && disc.equals(e.disc) && mark.equals(e.mark);
    }
}
