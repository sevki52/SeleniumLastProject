package com.cydeo.step_definitions;


import com.cydeo.pages.pojo.Spartan;
import com.cydeo.utilities.ConfigurationReader;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.*;


import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SpartanFlowSteps {

    String spartanUrl = ConfigurationReader.get("spartan.apiUrl");
    String mockUrl = ConfigurationReader.get("mock.apiUrl");
    Response mockSpartanJSON;
    Response postResponse;

    @When("User sends a request to Mock API for a mock Spartan Data")
    public void userSendsARequestToMockAPIForAMockSpartanData() {
        mockSpartanJSON = given().accept(ContentType.JSON)
                .and().header("X-API-Key","cb98a4c0")
                .get(mockUrl);
      }

    @When("User uses Mock Data to create a Spartan")
    public void userUsesMockDataToCreateASpartan() {

        Spartan mySpartan = new Spartan();
        mySpartan.setName(mockSpartanJSON.path("name")); // reading Mock API spartan response and path is name
        mySpartan.setGender(mockSpartanJSON.path("gender"));
        Long phone = Long.valueOf(mockSpartanJSON.path("phone").toString());
        mySpartan.setPhone(phone);
        System.out.println(mySpartan);

        postResponse = given()
                .accept(ContentType.JSON) // I want JSON response
                .contentType(ContentType.JSON) // I am sending JSON body
                .and().body(mySpartan)  // serialize from JAVA to JSON

                .when().post(spartanUrl+"/api/spartans");

        postResponse.prettyPrint();


    }


    @When("User sends a request to Spartan API with id {int}")
    public void user_sends_a_request_to_spartan_api_with_id(int id) {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id",id)
                .when().get(spartanUrl+"/api/spartans/{id}");
        response.prettyPrint();
    }



}
