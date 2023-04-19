package firesea.testserver.respository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class QueryDslTest {


    @Test
    public void test() {
        String content = "1_2,3,4123.12;";
        String[] split = content.split("[^\uAC00-\uD7A3xfe0-9a-zA-Z)]");
        System.out.println("split = " + split);
    }
}
