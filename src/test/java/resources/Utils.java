package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
// class to store all the reusable methods
	
	// making the variable static will create only one instance of the variable for entire execution
	static RequestSpecification baseReq;
	
	public RequestSpecification requestSpecification() throws IOException
	{
		// if baseReq is created for the 1st time then set baseUri and create logging.txt files
		if(baseReq==null)
		{
			// to write something in output file
			PrintStream log=new PrintStream(new FileOutputStream("logging.txt"));
			
			baseReq=new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log))
					.setContentType(ContentType.JSON).addQueryParam("key", "qaclick123").build();
			
			return baseReq;
		}
		// if baseReq is already created and test is running for 1+ time then return the baseReq object
		return baseReq;	
	}
	
	public static String getGlobalValue(String key) throws IOException
	{
		Properties prop=new Properties();
		// to read the values from the file
		FileInputStream fis=new FileInputStream("C:\\Users\\MADHURA\\EclipseProjects\\RestfulAPIFramework\\src\\test\\java\\resources\\global.properties");
		// Properties class object need to know the path of the file to read-which FileInputStream can provide
		prop.load(fis);
		return prop.getProperty(key);
	}
	
	public String getJsonPath(Response response, String key)
	{
		String resStr=response.asString();
		JsonPath js=new JsonPath(resStr);
		return js.get(key).toString();
	}
}
