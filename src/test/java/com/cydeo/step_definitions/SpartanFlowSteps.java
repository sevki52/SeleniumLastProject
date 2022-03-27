package com.cydeo.step_definitions;


import com.cydeo.utilities.ConfigurationReader;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.*;


import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SpartanFlowSteps {

    String spartanUrl = ConfigurationReader.get("spartan.apiUrl");
    String mockUrl = ConfigurationReader.get("mock.apiUrl");
    Response mockSpartanJSON;

    @When("User sends a request to Mock API for a mock Spartan Data")
    public void userSendsARequestToMockAPIForAMockSpartanData() {
        mockSpartanJSON = given().accept(ContentType.JSON)
                .and().header("X-API-Key","cb98a4c0")
                .get(mockUrl);
      }

    @When("User uses Mock Data to create a Spartan")
    public void userUsesMockDataToCreateASpartan() {


    }


    @When("User sends a request to Spartan API with id {int}")
    public void user_sends_a_request_to_spartan_api_with_id(int id) {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id",id)
                .when().get(spartanUrl+"/api/spartans/{id}");
        response.prettyPrint();
    }



}
