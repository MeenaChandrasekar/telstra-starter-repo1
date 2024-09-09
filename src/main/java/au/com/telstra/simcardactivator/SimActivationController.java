package au.com.telstra.simcardactivator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/sim")
public class SimActivationController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/activate")
    public String activateSim(@RequestBody SimActivationRequest request) {
        String actuatorUrl = "http://localhost:8444/actuate";
        
        // Create payload for actuator service
        String payload = "{\"iccid\":\"" + request.getIccid() + "\"}";

        // Send POST request to actuator service
        ResponseEntity<String> response = restTemplate.postForEntity(actuatorUrl, payload, String.class);

        // Check response and return result
        if (response.getBody() != null && response.getBody().contains("\"success\":true")) {
            return "Activation successful";
        } else {
            return "Activation failed";
        }
    }
}
