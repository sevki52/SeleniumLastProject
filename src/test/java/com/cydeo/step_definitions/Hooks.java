package com.cydeo.step_definitions;

import com.cydeo.utilities.ConfigurationReader;
import io.cucumber.java.Before;


import static io.restassured.RestAssured.*;

public class Hooks {

    @Before(order=0)
    public void setUpMock(){
        baseURI= ConfigurationReader.get("mock.apiUrl");
    }
    @Before(order=1)
    public void setUpTest(){
        baseURI= ConfigurationReader.get("spartan.apiUrl");
    }
}
