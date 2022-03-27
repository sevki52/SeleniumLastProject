package com.cydeo.step_definitions;


import com.cydeo.pages.pojo.Spartan;
import com.cydeo.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.*;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;

public class SpartanFlowSteps {

    String spartanUrl = ConfigurationReader.get("spartan.apiUrl");
    String mockUrl = ConfigurationReader.get("mock.apiUrl");
    Response mockSpartanJSON;
    Response postResponse;
    Response getResponse;
    Spartan mySpartan;

    int idFromPost;

    @When("User sends a request to Mock API for a mock Spartan Data")
    public void userSendsARequestToMockAPIForAMockSpartanData() {
        mockSpartanJSON = given().accept(ContentType.JSON)
                .and().header("X-API-Key","cb98a4c0")  // I am sending authorization with headers
                .get(mockUrl);
        Assert.assertEquals(200,mockSpartanJSON.statusCode());
      }

    @When("User uses Mock Data to create a Spartan")
    public void userUsesMockDataToCreateASpartan() {

        mySpartan = new Spartan();
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

        Assert.assertEquals(201,postResponse.statusCode());
    }


    @When("User sends a request to Spartan API with id {int}")
    public void user_sends_a_request_to_spartan_api_with_id(int id) {

        if(id==0){
            id = postResponse.path("data.id");
            System.out.println("id = " + id);
        }
            getResponse = given().accept(ContentType.JSON)
                    .and().pathParam("id", id)
                    .when().get(spartanUrl + "/api/spartans/{id}");

            Assert.assertEquals(200,getResponse.statusCode());
    }


    @Then("Created Spartan has same information with Post Request")
    public void createdSpartanHasSameInformationWithPostRequest() {
        String expectedName = postResponse.path("data.name");
        String actualName = getResponse.path("name");
        Assert.assertEquals(expectedName,actualName);
   }

    @And("User Updates all the fields of created Spartan")
    public void userUpdatesAllTheFieldsOfCreatedSpartan() {
        mySpartan.setName("Oscar");
        mySpartan.setGender("Male");
        mySpartan.setPhone(555123456L);
        
        Response putResponse = given().accept(ContentType.JSON)
                .and().pathParam("id",postResponse.path("data.id"))
                .body(mySpartan)
                .put(spartanUrl+"/api/spartans/{id}");
        Assert.assertEquals(204,putResponse.statusCode());
        putResponse.prettyPrint();
        
    }
}
