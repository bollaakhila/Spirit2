package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.gherkin.internal.com.eclipsesource.json.Json;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;
import models.Student;
import org.json.simple.JSONObject;
import utils.EndPoints;
import utils.TestngListener;
import static utils.JsonInputParser.userData;



import static io.restassured.RestAssured.given;
import static utils.JsonInputParser.userData;

public class MyStepdefs {
    ObjectMapper objectMapper =new ObjectMapper();
    Student student;
    Response response;
    JSONObject testData;
    Student responseStud;
    JsonPath jsonpath;
    @Given("the body")
    public void theBody() {
       testData = (JSONObject) userData.get("createStudent");
    }

    @When("creating the student data")
    public void creatingTheStudentData() {
        student = new Student((Long) testData.get("id"), (String) testData.get("name"),
                (Long) testData.get("age"), (String) testData.get("email"));

        response = given()
                .body(student)
                .when().post("student")
                .then().statusCode(200).extract().response();
    }

    @Then("student data is created")
    public void studentDataIsCreated() throws JsonProcessingException {
        Student responseStud = objectMapper.readValue(response.asString(), Student.class);
        Assert.assertEquals(student.getId(), responseStud.getId());
        Assert.assertEquals(student.getName(), responseStud.getName());
        Assert.assertEquals(student.getAge(), responseStud.getAge());
        Assert.assertEquals(student.getEmail(), responseStud.getEmail());
    }


    @When("Student is created")
    public void studentIsCreated() throws JsonProcessingException {
        testData = (JSONObject) TestngListener.data.get("createStudent");

        student =new Student(

                (Long) testData.get("id"),
                (String) testData.get("name"),
                (Long) testData.get("age"),
                (String) testData.get("email"));
        response=given()
                .body(student)
                .when().post(EndPoints.studentEndPoint)
                .then()
                .statusCode(200).extract().response();
        student=objectMapper.readValue(response.asString(),Student.class);

    }

    @And("update the Age field")
    public void updateTheAgeField() throws JsonProcessingException {
        testData = (JSONObject) TestngListener.data.get("UpdateStudent");

        student = new Student((Long) testData.get("id"), (String) testData.get("name"),
                (Long) testData.get("age"), (String) testData.get("email"));

        response=given()
                .body(student)
                .when().put(EndPoints.studentEndPoint + "/" +student.getId())
                .then()
                .statusCode(200).extract().response();
    }

    @Then("Student age is Updated")
    public void studentDataIsUpdated() throws JsonProcessingException {
        Student responseStud =  objectMapper.readValue(response.asString(),Student.class);
        Assert.assertEquals(student.getAge(),responseStud.getAge());
    }

    @When("Student data is delete")
    public void studentDataIsDelete() throws JsonProcessingException {

        student =new Student(

                (Long) testData.get("id"),
                (String) testData.get("name"),
                (Long) testData.get("age"),
                (String) testData.get("email"));
        response=given()
                .body(student)
                .when().post(EndPoints.studentEndPoint)
                .then()
                .statusCode(200).extract().response();
        student=objectMapper.readValue(response.asString(),Student.class);
    }

    @Then("Deleted Student data")
    public void deletedStudentData() {

        response=given()

                .when().delete(EndPoints.studentEndPoint + "/" +student.getId())
                .then()
                .statusCode(200).extract().response();
    }

    @When("created a student data")
    public void createdAStudentData() throws JsonProcessingException {
        student =new Student(

                (Long) testData.get("id"),
                (String) testData.get("name"),
                (Long) testData.get("age"),
                (String) testData.get("email"));
        response=given()
                .body(student)
                .when().post(EndPoints.studentEndPoint)
                .then()
                .statusCode(200).extract().response();
        student=objectMapper.readValue(response.asString(),Student.class);

    }

    @And("update the name field")
    public void updateTheNameField() throws JsonProcessingException {
        testData = (JSONObject) TestngListener.data.get("UpdateStudent");

        student = new Student((Long) testData.get("id"), (String) testData.get("name"),
                (Long) testData.get("age"), (String) testData.get("email"));

        response=given()
                .body(student)
                .when().put(EndPoints.studentEndPoint + "/" +student.getId())
                .then()
                .statusCode(200).extract().response();
        student=objectMapper.readValue(response.asString(),Student.class);
    }

    @Then("Student name is update")
    public void studentNameIsUpdate() throws JsonProcessingException {
        Student responseUser =  objectMapper.readValue(response.asString(),Student.class);
        Assert.assertEquals(student.getName(),responseUser.getName());
    }

    @When("created a student with no name")
    public void createdAStudentWithNoName() {
        student = new Student((Long) testData.get("id"),
                null,
                (Long) testData.get("age"),
                (String) testData.get("email"));
        response=given()
                .body(student)
                .when().post(EndPoints.studentEndPoint )
                .then()
                .statusCode(400).extract().response();

    }

    @Then("Name is required error")
    public void nameIsRequiredError() {
        jsonpath = new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("message"),"Name is required");
    }

    @When("created a student with no age")
    public void createdAStudentWithNoAge() {
        student = new Student((Long) testData.get("id"),
                (String) testData.get("name"),
                0,
                (String) testData.get("email"));
        response=given()
                .body(student)
                .when().post(EndPoints.studentEndPoint )
                .then()
                .statusCode(400).extract().response();
    }

    @Then("Age is required error")
    public void ageIsRequiredError() {
        jsonpath = new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("message"),"Age is required");
    }

    @When("created a student with no email")
    public void createdAStudentWithNoEmail() {
        student =new Student(

                (Long) testData.get("id"),
                (String) testData.get("name"),
                (Long) testData.get("age"),
                null);
        response=given()
                .body(student)
                .when().post(EndPoints.studentEndPoint )
                .then()
                .statusCode(400).extract().response();
    }

    @Then("email is required error")
    public void emailIsRequiredError() {
        jsonpath = new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("message"),"Email is required");
    }

    @When("created a student with no id")
    public void createdAStudentWithNoId() {
        student =new Student(
                0,
                (String) testData.get("name"),
                (Long) testData.get("age"),
                (String) testData.get("email"));
        response=given()
                .body(student)
                .when().post(EndPoints.studentEndPoint )
                .then()
                .statusCode(500).extract().response();
    }

    @Then("id is required error")
    public void idIsRequiredError() {
        jsonpath = new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("error"),"Internal Server Error");
    }

    @Then("student data is display")
    public void studentDataIsDisplay() {
        response = given()
                .body(student)
                .when().get("student")
                .then().statusCode(200).extract().response();
    }

    @And("duplicate delete")
    public void duplicateDelete() throws JsonProcessingException {
        response=given()
                .body(student)
                .when().delete(EndPoints.studentEndPoint+"/"+student.getId())
                .then()
                .statusCode(500).extract().response();

    }


}
