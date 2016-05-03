package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "groups")
@NamedQuery(name = "findAllGroups",
        query = "SELECT g FROM Group g ORDER BY g.id")
public class Group implements Serializable
{
    private int id;
    private int number;
    private String faculty;
    private List<Student> studentList;

    public Group()
    {
    }

    public Group(int number, String faculty)
    {
        this.number = number;
        this.faculty = faculty;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_id_seq")
    @SequenceGenerator(name = "group_id_seq", sequenceName = "group_id_seq", allocationSize = 1)
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Column(name = "group_number")
    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    @Column(name = "faculty")
    public String getFaculty()
    {
        return faculty;
    }

    public void setFaculty(String faculty)
    {
        this.faculty = faculty;
    }

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    public List<Student> getStudentList()
    {
        return studentList;
    }

    public void setStudentList(List<Student> studentList)
    {
        this.studentList = studentList;
    }
}
