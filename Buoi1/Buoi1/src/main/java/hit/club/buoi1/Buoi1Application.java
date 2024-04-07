package hit.club.buoi1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class Buoi1Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Buoi1Application.class, args);
        User user = context.getBean(User.class);
        user.displayUser();
    }

}
