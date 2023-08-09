package StandAlone;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;

public class Basics {
	public static void main(String[] args)
	{
		AddPlace p=new AddPlace();
		p.setAccuracy(50);
		p.setName("Frontline house");
		p.setPhone_number("(+91) 983 893 3937");
		p.setAddress("29, side layout, cohen 09");
		p.setWebsite("http://google.com");
		p.setLanguage("French-IN");
		List<String> ll=new ArrayList<String>();
		ll.add("shoe park");
		ll.add("shop");
		p.setTypes(ll);
		Location loc=new Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);
		
		// to validate if Add Place is working as expected
		RequestSpecification addPlaceBaseReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).addQueryParam("key", "qaclick123").build();
		
		RequestSpecification addPlaceReq=given().log().all().spec(addPlaceBaseReq).body(p);
		
		ResponseSpecification addPlaceBaseres=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
				
		Response addPlaceRes=addPlaceReq.when().post("/maps/api/place/add/json").then().log().all().extract().response();
		
		addPlaceRes.getStatusCode();
		assertEquals(addPlaceRes.getStatusCode(),200);
		
		String addPlaceResStr=addPlaceRes.toString();
		JsonPath js=new JsonPath(addPlaceResStr);
		String placeId=js.getString("place_id");
		System.out.println(placeId);
			
		// to validate Get Place API
		RequestSpecification getPlaceBaseReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").addQueryParam("place_id", placeId).build();
		
		RequestSpecification getPlaceReq=given().log().all().spec(getPlaceBaseReq);
		String getPlaceRes=getPlaceReq.when().get("/maps/api/place/get/json")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		// to validate put place API
		String newAddress="70 winter walk, USA";
		
		RequestSpecification putPlaceBaseReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).addQueryParam("key", "qaclick123").build();
		
		RequestSpecification putPlaceReq=given().log().all().queryParam("place_id", placeId).spec(putPlaceBaseReq);
		
		Response putPlaceRes=putPlaceReq.when().put("/maps/api/place/update/json").then().assertThat().statusCode(200)
				.extract().response();
		
	}

}
