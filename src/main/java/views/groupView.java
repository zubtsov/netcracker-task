package views;

import dao.MyDAO;
import entities.Group;
import entities.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@ManagedBean
@SessionScoped
public class GroupView
{
    private static final Logger logger = LogManager.getLogger(GroupView.class);

    @ManagedProperty(value = "#{myDAO}")
    private MyDAO myDAO;

    private boolean newGroup;
    private Group currentGroup;

    public String update()
    {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String groupId = req.getParameter("groupId");
        newGroup = groupId == null ? true : false;

        if (newGroup)
        {
            currentGroup = new Group();
        } else
        {
            currentGroup = myDAO.getGroupById(Integer.parseInt(groupId));
        }

        logger.info("UPDATING CURRENT GROUP. GROUP ID = " + currentGroup.getId());

        return "group";
    }

    public String saveGroup()
    {
        logger.info("SAVING GROUP. GROUP ID = " + currentGroup.getId());

        if (newGroup)
        {
            myDAO.add(currentGroup);
        } else
        {
            myDAO.merge(currentGroup);
        }
        return "groups";
    }

    public void generateReport()
    {
        logger.info("GENERATING REPORT");

        List<Student> studentList = currentGroup.getStudentList();

        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("Отчет по группе");

        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue("Группа");
        row.createCell(1).setCellValue(currentGroup.getNumber());

        row = sheet.createRow(0);
        row.createCell(0).setCellValue("Студент");
        row.createCell(1).setCellValue("Средний балл");

        //На секунду показалось, что то же самое удобно сделать с помощью HQL... Но XML мешает.
        double average = 0.0; //средний балл по группе
        double avmark; //средний балл по студенту
        int counter = 0; //количество средних баллов
        int i;

        for (i = 0; i<studentList.size(); i++)
        {
            row = sheet.createRow(i+2);
            row.createCell(0).setCellValue(studentList.get(i).getName());

            avmark = studentList.get(i).getAverageMark();

            row.createCell(1).setCellValue(avmark);

            if (avmark != Double.NaN)
            {
                average+=avmark;
                counter++;
            }
        }

        row = sheet.createRow(i+2);
        row.createCell(0).setCellValue("Средний балл:");
        row.createCell(1).setCellValue(average/counter);

        try
        {
            //Через System.getProperty(user.home) не вышло
            FileOutputStream fileOut = new FileOutputStream("D:\\\\report.xls");
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e)
        {

        }
    }

    public Group getCurrentGroup()
    {
        return currentGroup;
    }

    public void setCurrentGroup(Group currentGroup)
    {
        this.currentGroup = currentGroup;
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
