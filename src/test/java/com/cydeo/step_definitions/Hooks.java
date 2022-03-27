package com.cydeo.step_definitions;

import com.cydeo.utilities.ConfigurationReader;
import io.cucumber.java.Before;


import static io.restassured.RestAssured.*;

public class Hooks {
    @Before
    public void setUpTest(){
        baseURI= ConfigurationReader.get("spartan.apiUrl");
    }
}
