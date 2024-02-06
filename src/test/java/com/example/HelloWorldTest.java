package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class HelloWorldTest {

    @Test
    public void testHelloWorld() {
        HelloWorld helloWorld = new HelloWorld();
        String result = helloWorld.getMessage();
        assertEquals("Hello world!", result);
    }
}
