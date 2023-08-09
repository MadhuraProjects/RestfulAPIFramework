package stepDefinitions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class StepDefinition extends Utils{

	RequestSpecification req;
	ResponseSpecification baseRes;
	Response response;
	String placeId;
	
	@Given("AddPlace Payload with {string} {string} {string}")
	public void add_place_payload(String name, String language, String address) throws IOException {	
		TestDataBuild data=new TestDataBuild();
		// to validate if Add Place is working as expected
		req=given().log().all().spec(requestSpecification()).body(data.addPlacePayload(name,language,address)); 
	}
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_post_http_request(String resource, String method) {
		//the valueOf method will call the enum constructor with the parametized resource
		APIResources resrc=APIResources.valueOf(resource);
		System.out.println("***********************************************************");
		System.out.println(resrc.getAPIResources());
		System.out.println("***********************************************************");
		
		baseRes=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(method.equalsIgnoreCase("POST"))
			response=req.when().post(resrc.getAPIResources());
		else if(method.equalsIgnoreCase("GET"))
			response=req.when().get(resrc.getAPIResources());

				//.then().spec(addPlaceBaseRes).log().all().extract().response();
	}
	
	@Then("API call gets success with status code {int}")
	public void api_call_gets_success_with_code(int code) {
		assertEquals(response.getStatusCode(),200);
	}
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
		
		 assertEquals(getJsonPath(response,keyValue),expectedValue);
	}
	
	@Then("verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
	    // first we need to prepare one requestSpec with baseURI query param etc 
		// then we can pass the place_id from the response fetched in addPlaceAPI and add as queryParam
		// then we can pass the resource using proper http method
		
		String placeId=getJsonPath(response,"place_id");
		req=given().spec(requestSpecification()).queryParam("place_id", placeId);
		user_calls_with_post_http_request(resource,"GET");
		String actualName=getJsonPath(response,"name");
		assertEquals(actualName,expectedName);
	}

	@Given("DeletePlace payload")
	public void delete_place_payload() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}


}
