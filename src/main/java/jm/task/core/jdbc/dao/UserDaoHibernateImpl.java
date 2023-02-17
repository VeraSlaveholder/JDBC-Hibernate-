package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getConnection();

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.createNativeQuery("create table IF NOT EXISTS user (id int NOT NULL AUTO_INCREMENT PRIMARY KEY, name varchar(100), lastname varchar(100), age int)")
                    .executeUpdate();
            transaction.commit();
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.createNativeQuery("drop table if exists user").executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try (session) {
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try (session) {
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try (session) {
            List<User> list = session.createCriteria(User.class).list();
            session.getTransaction().commit();
            return list;
        }

    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try (session) {
            session.createNativeQuery("TRUNCATE TABLE user").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
