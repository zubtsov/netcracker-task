package entities;

import org.xml.sax.SAXException;

import javax.persistence.*;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "students")
@NamedQuery(name = "findAllStudents",
        query = "SELECT s FROM Student s ORDER BY s.id")
@XmlRootElement(name = "student")
public class Student implements Serializable
{
    private int id;
    private String name;
    private Group group;
    private Date enrollDate;

    private List<Semester> semesters;
    //Возможно даже как вариант делать их статическими
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    private ByteArrayOutputStream baos;
    private ByteArrayInputStream bais;

    public Student()
    {
        enrollDate = new Date();
        semesters = new ArrayList<>();
        //В случае исключения предотвратить создание объекта не получится ибо они ловятся здесь же...
        try
        {
            JAXBContext context = JAXBContext.newInstance(Student.class);
            marshaller = context.createMarshaller();
            unmarshaller = context.createUnmarshaller();
            baos = new ByteArrayOutputStream();
        } catch (JAXBException e)
        {

        }
    }

    public Student(Date enrollDate, Group group, String name)
    {
        this();

        this.enrollDate = enrollDate;
        this.group = group;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stud_id_seq")
    @SequenceGenerator(name = "stud_id_seq", sequenceName = "stud_id_seq", allocationSize = 1)
    @Column(name = "id")
    @XmlTransient
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Column(name = "stud_name")
    @XmlTransient
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "group_id")
    @XmlTransient
    public Group getGroup()
    {
        return group;
    }

    public void setGroup(Group group)
    {
        this.group = group;
    }

    @Column(name = "enroll_date")
    @XmlTransient
    public Date getEnrollDate()
    {
        return enrollDate;
    }

    public void setEnrollDate(Date enrollDate)
    {
        this.enrollDate = enrollDate;
    }

    @Transient
    @XmlElement(name = "semesters")
    public List<Semester> getSemesters()
    {
        return semesters;
    }

    public void setSemesters(List<Semester> semesters)
    {
        this.semesters = semesters;
    }

    @Column(name = "semesters")
    @XmlTransient
    public String getXMLSemesters()
    {
        String result = null;

        try
        {
            marshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(getClass().getResource("/schema.xsd")));
            marshaller.marshal(this, baos);
            result = new String(baos.toByteArray());
            baos.reset();
        } catch (Exception e)
        {

        }

        return result;
    }

    public void setXMLSemesters(String semesters)
    {
        try
        {
            unmarshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(getClass().getResource("/schema.xsd")));
            bais = new ByteArrayInputStream(semesters.getBytes(StandardCharsets.UTF_8));
            Student st = (Student) unmarshaller.unmarshal(bais);

            this.semesters = st.getSemesters();
        } catch (JAXBException e)
        {
            e.printStackTrace();
        } catch (SAXException e)
        {

        }
    }

    @XmlTransient
    @Transient
    public double getAverageMark()
    {
        if (semesters.size() == 0)
        {
            return Double.NaN;
        }

        double average = 0;

        for (Semester s : semesters)
        {
            for (Exam e : s.getExams())
            {
                average += e.getMark();
            }

            int d = s.getExams().size() > 0 ? s.getExams().size() : 1;
            average /= d;
        }
        int d = semesters.size();
        average /= d;

        return average;
    }
}
