package org.vistula.restassured.information;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;

public class TestWork extends RestAssuredTest{


    @Test
    public void shouldAllPlayer() {
        given().get("/information")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", is(27));
    }


    @Test
    public void shouldCreatePlayer() {

        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic(6);
        String myNationality = "Poland";
        int Salary = 60000;

        requestParams.put("name", myName);
        requestParams.put("nationality", myNationality);
        requestParams.put("salary", Salary);

        Object idNewPlayer = given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information")
                .then()
                .log().all()
                .statusCode(201)
                .body("nationality", equalTo(myNationality))
                .body("name", equalTo(myName))
                .body("salary", equalTo(Salary))
                .body("id", greaterThan(0))
                .extract().path("id");

        String newName = RandomStringUtils.randomAlphabetic(6);
        requestParams.put("name", newName);

        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .patch("/information/" + idNewPlayer)
                .then()
                .log().all()
                .statusCode(200)
                .body("nationality", equalTo(myNationality))
                .body("name", equalTo(newName))
                .body("salary", equalTo(Salary))
                .body("id", greaterThan(0));

        given().delete("/information/" +idNewPlayer)
                .then()
                .log().all();
    }

    @Test
    public void shouldCreateNewPlayer() {

        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic(6);
        String myNationality = "Poland";
        int Salary = 60000;

        requestParams.put("name", myName);
        requestParams.put("nationality", myNationality);
        requestParams.put("salary", Salary);

        Object idNewPlayer = given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information")
                .then()
                .log().all()
                .statusCode(201)
                .body("nationality", equalTo(myNationality))
                .body("name", equalTo(myName))
                .body("salary", equalTo(Salary))
                .body("id", greaterThan(0))
                .extract().path("id");

        int newSalary = 800000;
        String newName = RandomStringUtils.randomAlphabetic(6);
        String newNationality = "Deutschland";

        requestParams.put("name", newName);
        requestParams.put("nationality", newNationality);
        requestParams.put("salary", newSalary);

        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .put("/information/" + idNewPlayer)
                .then()
                .log().all()
                .statusCode(200)
                .body("nationality", equalTo(newNationality))
                .body("name", equalTo(newName))
                .body("salary", equalTo(newSalary))
                .body("id", greaterThan(0));

        given().delete("/information/" +idNewPlayer)
                .then()
                .log().all();
    }

}