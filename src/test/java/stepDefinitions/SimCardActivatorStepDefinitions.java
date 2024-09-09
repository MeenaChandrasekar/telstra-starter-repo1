/*
 * package stepDefinitions;
 * 
 * import au.com.telstra.simcardactivator.SimCardActivator; import
 * io.cucumber.spring.CucumberContextConfiguration; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.context.SpringBootContextLoader; import
 * org.springframework.boot.test.context.SpringBootTest; import
 * org.springframework.boot.test.web.client.TestRestTemplate; import
 * org.springframework.test.context.ContextConfiguration;
 * 
 * @CucumberContextConfiguration
 * 
 * @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
 * 
 * @ContextConfiguration(classes = SimCardActivator.class, loader =
 * SpringBootContextLoader.class) public class SimCardActivatorStepDefinitions {
 * 
 * @Autowired private TestRestTemplate restTemplate;
 * 
 * }
 */
 package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import au.com.telstra.simcardactivator.SimActivationRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SimCardActivatorStepDefinitions {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;

    @When("^I send a POST request to activate SIM card with ICCID \"([^\"]*)\" and email \"([^\"]*)\"$")
    public void sendPostRequest(String iccid, String email) {
        SimActivationRequest request = new SimActivationRequest();
        request.setIccid(iccid);
        request.setCustomerEmail(email);
        response = restTemplate.postForEntity("/api/sim/activate", request, String.class);
    }

    @Then("^I should receive a response indicating success$")
    public void verifySuccessResponse() {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Activation successful", response.getBody());
    }
    
    @Then("^I should receive a response indicating failure$")
    public void verifyFailureResponse() {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Activation failed", response.getBody());
    }
}
