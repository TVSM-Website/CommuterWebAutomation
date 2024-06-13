package APIs;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PriceAPI
{
    public static String getAuthToken()
    {
        RestAssured.baseURI="https://uat-corp-api.tvsmotor.net";
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

    public static String OrpDetails(String Vehicle, String stateCode)
    {
        String authToken = getTokenResp(getAuthToken());
        //System.out.println("tokenres-"+authToken);
        String response = given()
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " + authToken)
                .body("{\n" +
                "  \"VehicleName\": \""+Vehicle+"\",\n" +
                "  \"BrandStateCode\": "+stateCode+"\n" +
                "}").log().all().when()
                .post("https://uat-corp-api.tvsmotor.net/api/TVSORP/TVSORPs")
                .then().log().all().extract().response().asString();
        return response;
    }


//    public static void main(String[] args)
//    {
//        String response=OrpDetails("JUPITER 125","420000008");
//        System.out.println(response);
//    }
}
