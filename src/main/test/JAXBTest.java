import entities.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JAXBTest
{
    //Для себя, пока что никаких JUnit
    public static void main(String[] args)
    {
        Student st = new Student(new Date(), new Group(12345, "faculty"), "student's name");
        List<Semester> semesterList = new ArrayList<>();

        Semester semester = new Semester(new Date(), new Date());

        List<Exam> exams = new ArrayList<>();
        exams.add(new Exam(new Discipline("mathematica", "difficult"), 5, new Date()));
        exams.add(new Exam(new Discipline("computer science", "easy"), 10, new Date()));
        semester.setExams(exams);

        semesterList.add(semester);
        st.setSemesters(semesterList);

        try
        {
            JAXBContext context = JAXBContext.newInstance(Student.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(st, System.out);
        } catch (Exception e)
        {

        }
    }
}
