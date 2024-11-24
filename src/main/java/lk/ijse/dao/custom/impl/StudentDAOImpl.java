package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public boolean save(Student student) {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Object isSaved = session.save(student);

        if(isSaved != null){
            transaction.commit();
            session.close();
            return true;
        }
        return false;
    }

    @Override
    public List<Student> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Student");
        List<Student> students = query.list();
        transaction.commit();
        session.close();
        return students;
    }

    @Override
    public String getCurrentID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("select student_id from Student ORDER BY student_id DESC LIMIT 1");
        String currentId = (String) query.uniqueResult();

        transaction.commit();
        session.close();
        return currentId;
    }

    @Override
    public boolean update(Student student) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(student);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("delete from Student where student_id = ?1");
        query.setParameter(1, id);
        boolean isDeleted = query.executeUpdate() > 0;

        if (isDeleted) {
            transaction.commit();
            session.close();
            return true;
        }
        return false;
    }

    @Override
    public Student search(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Student where student_id =?1");
        query.setParameter(1, id);
        Student student = (Student) query.uniqueResult();
        transaction.commit();
        return student;
    }

    @Override
    public List<Student> getStudent() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("select s.student_id,s.name,s.user from Student s JOIN Registration r on s.student_id = r.student JOIN Program p on r.program = p.program_id GROUP BY s.student_id HAVING COUNT(DISTINCT p.program_id) = (SELECT COUNT(DISTINCT program_id) FROM Program )");
        List<Student> students = query.list();
        transaction.commit();
        session.close();
        return students;
    }
}
