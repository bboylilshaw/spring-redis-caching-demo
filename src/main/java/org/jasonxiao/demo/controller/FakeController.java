package org.jasonxiao.demo.controller;

import org.jasonxiao.demo.service.FakeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jason Xiao
 */
@RestController
@RequestMapping("/api")
public class FakeController {

    private final FakeService fakeService;

    public FakeController(FakeService fakeService) {
        this.fakeService = fakeService;
    }

    @GetMapping("/success")
    public void doWorkSuccess() {
        fakeService.doWorkSuccess();
    }

    @GetMapping("/fail")
    public void doWorkFail() {
        fakeService.doWorkFail();
    }
}
