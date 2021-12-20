package com.example.frontend_app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

@SpringBootTest
public class OWASP_ControllerTests {
    @Autowired
    OWASP_Controller controller;

    @Autowired
    Model model = new ConcurrentModel();

//    @Test
//    public void returnsOwasp(){
//        Assertions.assertEquals(controller.owasp(model), "owasp");
//    }

}
