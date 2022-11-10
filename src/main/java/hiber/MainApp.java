package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      CarService carService = context.getBean(CarService.class);
      carService.add(new Car("Skoda", 123));
      carService.add(new Car("Subaru", 223));
      carService.add(new Car("ВMW", 323));
      carService.add(new Car("Toyota", 423));

      UserService userService = context.getBean(UserService.class);

      List<Car> cars = carService.listCars();

      userService.add(new User("User1", "Lastname1", "user1@mail.ru", cars.get(0)));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru", cars.get(1)));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru", cars.get(2)));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru", cars.get(3)));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = " + user.getId());
         System.out.println("First Name = " + user.getFirstName());
         System.out.println("Last Name = " + user.getLastName());
         System.out.println("Email = " + user.getEmail());
         System.out.println("Car = " + user.getUserCar());
         System.out.println();
      }
      //userService.deleteAllUsers();
      try {
         System.out.println(userService.find("ВMW", 323));
      } catch (NoResultException e) {
         e.printStackTrace();
      }

      context.close();
   }
}
