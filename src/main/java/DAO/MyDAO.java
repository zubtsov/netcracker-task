package dao;

import entities.Group;
import entities.Student;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.util.List;

@Transactional
@SessionScoped
@ManagedBean(name = "myDAO")
public class MyDAO implements Serializable
{
    @PersistenceContext(name = "em")
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    public void add(Object obj)
    {
        try
        {
            utx.begin();
            em.persist(obj);
            utx.commit();
        } catch (Exception e)
        {

        }
    }

    public void merge(Object obj)
    {
        try
        {
            utx.begin();
            em.merge(obj);
            utx.commit();
        } catch (Exception e)
        {

        }
    }

    public Student getStudentById(int id)
    {
        List l = em.createQuery("SELECT s FROM Student s WHERE s.id = :id").setParameter("id", id).getResultList();
        if (l.size() > 0)
            return (Student) l.get(0);
        else
            return null;
    }

    public Group getGroupById(int id)
    {
        List l = em.createQuery("SELECT g FROM Group g WHERE g.id = :id").setParameter("id", id).getResultList();
        if (l.size() > 0)
            return (Group) l.get(0);
        else
            return null;
    }

    public void deleteStudentById(int id)
    {
        try
        {
            utx.begin();
            Student s = getStudentById(id);
            em.remove(s);
            utx.commit();
        } catch (Exception e)
        {

        }
    }

    public void deleteGroupById(int id)
    {
        try
        {
            utx.begin();
            Group g = getGroupById(id);
            em.remove(g);
            utx.commit();
        } catch (Exception e)
        {

        }
    }

    public List<Student> getStudents()
    {
        return em.createNamedQuery("findAllStudents").getResultList();
    }

    public List<Group> getGroups()
    {
        return em.createNamedQuery("findAllGroups").getResultList();
    }

    public EntityManager getEm()
    {
        return em;
    }
}
