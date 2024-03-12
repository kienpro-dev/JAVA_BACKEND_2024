package hit.club.buoi1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class User {
    @Autowired
    @Qualifier("IPhone")
    private Phone phone;

    public void displayUser() {
        phone.display();
    }
}
