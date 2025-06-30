package rest.api.com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.api.com.example.entity.Course;

import java.util.List;

@RestController
public class controller {

    @GetMapping("/home")
    public String home(){
        return "This is home page";
    }

    public List<Course> getcourse(){
        return

    }

}
