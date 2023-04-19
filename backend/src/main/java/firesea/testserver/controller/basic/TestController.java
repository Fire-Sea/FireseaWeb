package firesea.testserver.controller.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestController {

    @GetMapping("/")
    public String mainPage() {
        return "mainPage";
    }

    @GetMapping("/backend")
    public String server() {
        return "backend";
    }

    @GetMapping("/front")
    public String front() {
        return "front";
    }



}
