package APIs;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ExShowroomProdAPI
{
    public static Response GetExshowroomPriceProd(String VehicleName, String stateCode) {
        //String authToken = getTokenResp(getAuthToken());
        //System.out.println("tokenres-"+authToken);
        Response response = given()
                .header("Content-Type", "application/json")
                .queryParam("vehicle", VehicleName)
                .queryParam("stateCode", stateCode)
                .when().get("https://www.tvsmotor.com/api/GetVehicleDetails")
                .then().extract().response();
        return response;
    }
}
