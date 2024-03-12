package hit.club.buoi1;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class IPad implements Phone{
    @Override
    public void display() {
        System.out.println("Ipad");
    }
}
