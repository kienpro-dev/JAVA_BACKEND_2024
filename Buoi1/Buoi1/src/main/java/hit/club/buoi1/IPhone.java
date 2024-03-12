package hit.club.buoi1;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class IPhone implements Phone{
    @Override
    public void display() {
        System.out.println("Iphone");
    }
}
