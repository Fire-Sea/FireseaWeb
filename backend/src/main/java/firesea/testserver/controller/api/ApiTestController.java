package firesea.testserver.controller.api;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiTestController {

    @GetMapping("/api/test")
    public String test() {
        return "hello firesea";
    }

    @GetMapping("/api/ssafy")
    public String ssafy() {

        return "[ssafy] check the console";
    }

}
