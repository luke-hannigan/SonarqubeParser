package com.example.frontend_app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TextReaderTester {
    TextReader textReader = new TextReader();

    @Test
    void owaspInitTest(){
        textReader.owaspInit();
        Boolean isInit = true;
        for(int i = 0; i < textReader.owaspTestResults.length; i++)
            isInit = (textReader.owaspTestResults[i] == "NOT FOUND!");
        Assertions.assertEquals(isInit, true);
    }

    @Test
    void findResultsTest() throws Exception {
        textReader.findResults();
        Boolean isInit = true;
        for(String key: textReader.owasp.keySet())
            isInit = (textReader.owasp.get(key) != "not found");
        Assertions.assertEquals(isInit, true);
    }

    @Test
    void getOwaspTest(){
        textReader.getOwasp();
        Boolean isInit = true;
        for(int i = 0; i < textReader.owaspTestResults.length; i++)
            isInit = (textReader.owaspTestResults[i] != "NOT FOUND!");
        Assertions.assertEquals(isInit, true);
    }

}
