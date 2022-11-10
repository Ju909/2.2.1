package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }
   @Override
   @SuppressWarnings("unchecked")
   public void deleteAllUsers() {
      List<User> users = listUsers();
      for (User user: users) {
         sessionFactory.getCurrentSession().delete(user);
      }
   }

   @Transactional
   @Override
   public User find(String model, int series) {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("FROM User as p INNER JOIN fetch p.car as pc where pc.model =: model and pc.series =: series");
      query.setParameter("model", model).setParameter("series", series);
      return query.setMaxResults(1).getSingleResult();
   }
}
