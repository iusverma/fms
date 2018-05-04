package app;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.Connection;

@RestController
public class ServiceController {
	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/friend")
    public Connection friend(@RequestParam(value="name", defaultValue="World") String name) {
        return new Connection("ayush","verma");
    }
}
