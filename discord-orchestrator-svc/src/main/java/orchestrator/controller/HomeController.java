package orchestrator.controller;

import jakarta.annotation.security.PermitAll;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @PermitAll
    @GetMapping("/")
    public String index(){

        return "index";
    }

}
