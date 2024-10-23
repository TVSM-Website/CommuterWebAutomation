package APIs;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ORPProdAPI
{
    public static String getAuthToken()
    {
        RestAssured.baseURI="https://corp-api.tvsmotor.com";
        String response = given().header("Content-Type","application/json")
                .body("{\n" +
                        "  \"UserName\": \"getapitoken\",\n" +
                        "  \"Password\": \"Y4tgXk=W6$!m8st6\"\n" +
                        "}").when()
                .post("/api/Authentication/Token")
                .then().extract().response().asString();

        return response;

    }

    public static String getTokenResp(String response)
    {
        JsonPath json= new JsonPath(response);
        String token=json.getString("Token");
        //System.out.println("token-"+token);
        return token;
    }

    public static Response OrpDetailsPROD(String Vehicle, String stateCode)
    {
        String authToken = getTokenResp(getAuthToken());
        //System.out.println("tokenres-"+authToken);
        Response response = given()
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " + authToken)
                .body("{\n" +
                "  \"VehicleName\": \""+Vehicle+"\",\n" +
                "  \"BrandStateCode\": "+stateCode+"\n" +
                "}").when()
                .post("https://corp-api.tvsmotor.com/api/TVSORP/TVSORPs")
                .then().extract().response();
        return response;
    }


//    public static void main(String[] args)
//    {
//        String response=OrpDetails("JUPITER 125","420000008");
//        System.out.println(response);
//    }
}
