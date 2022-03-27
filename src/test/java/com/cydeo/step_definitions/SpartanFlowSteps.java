package com.cydeo.step_definitions;


import com.cydeo.utilities.ConfigurationReader;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.*;


import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SpartanFlowSteps {



    @When("User sends a request to Mock API for a mock Spartan Data")
    public void userSendsARequestToMockAPIForAMockSpartanData() {
        Response response = given().accept(ContentType.JSON)
                .and().header()

    }

    @When("User sends a request to Spartan API with id {int}")
    public void user_sends_a_request_to_spartan_api_with_id(int id) {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id",id)
                .when().get("/api/spartans/{id}");
        response.prettyPrint();
    }



}
