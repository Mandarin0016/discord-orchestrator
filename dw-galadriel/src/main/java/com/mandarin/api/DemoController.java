package com.mandarin.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping
    public String getName(@RequestParam String service) {
        log.info("I was called from " + service + " service. At " + OffsetDateTime.now());
        return "Galadriel";
    }

}
