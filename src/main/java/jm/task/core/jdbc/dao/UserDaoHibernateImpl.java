package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    @Override
    public void createUsersTable() {
        try (SessionFactory sessionFactory = Util.getConnection();
             Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("create table IF NOT EXISTS user (id int NOT NULL AUTO_INCREMENT PRIMARY KEY, name varchar(100), lastname varchar(100), age int)")
                    .executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (SessionFactory sessionFactory = Util.getConnection();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("drop table if exists user").executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (SessionFactory sessionFactory = Util.getConnection();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (SessionFactory sessionFactory = Util.getConnection();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (SessionFactory sessionFactory = Util.getConnection();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<User> list = session.createCriteria(User.class).list();
            session.getTransaction().commit();
            return list;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (SessionFactory sessionFactory = Util.getConnection();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE user").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
