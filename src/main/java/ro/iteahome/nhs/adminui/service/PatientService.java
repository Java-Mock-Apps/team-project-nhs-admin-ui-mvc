package ro.iteahome.nhs.adminui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.adminui.model.entity.Patient;

import java.util.Base64;

@Service
public class PatientService {
    // DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

// FIELDS: -------------------------------------------------------------------------------------------------------------

    private final String CREDENTIALS = "NHS_ADMIN_UI:P@ssW0rd!";
    private final String ENCODED_CREDENTIALS = new String(Base64.getEncoder().encode(CREDENTIALS.getBytes()));
    private final String PATIENTS_URL = "https://nhsbackend.myserverapps.com/patients";
    //private final String PATIENTS_URL = "http://localhost:8081/patients";


// AUTHENTICATION FOR REST REQUESTS: -----------------------------------------------------------------------------------

    private HttpHeaders getAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + ENCODED_CREDENTIALS);
        return headers;
    }

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public Patient add(Patient patient) {
        ResponseEntity<Patient> patientResponse =
                restTemplate.exchange(
                        PATIENTS_URL,
                        HttpMethod.POST,
                        new HttpEntity<>(patient, getAuthHeaders()),
                        Patient.class);
        return patientResponse.getBody();
    }


    public Patient findById(int id) {
        ResponseEntity<Patient> patientResponse =
                restTemplate.exchange(
                        PATIENTS_URL + "/by-id/" + id,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Patient.class);
        Patient patientDTO = patientResponse.getBody();
        if (patientDTO != null) {
            return patientDTO;
        } else {
            throw new GlobalNotFoundException("PATIENTS");
        }
    }

    public Patient findByCnp(String Cnp) {
        ResponseEntity<Patient> patientResponse =
                restTemplate.exchange(
                        PATIENTS_URL + "/by-cnp/" + Cnp,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Patient.class);
        Patient patientDTO = patientResponse.getBody();
        if (patientDTO != null) {
            return patientDTO;
        } else {
            throw new GlobalNotFoundException("PATIENTS");
        }
    }

    public Patient update(Patient newPatient) {
        Patient patientDTO = findById(newPatient.getId());
        if (patientDTO != null) {
            restTemplate.exchange(
                    PATIENTS_URL,
                    HttpMethod.PUT,
                    new HttpEntity<>(newPatient, getAuthHeaders()),
                    Patient.class);
            return findById(patientDTO.getId());
        } else {
            throw new GlobalNotFoundException("PATIENTS");
        }
    }

    public Patient deleteById(int id) {
        Patient patientDTO = findById(id);
        if (patientDTO != null) {
            ResponseEntity<Patient> patientResponse =
                    restTemplate.exchange(
                            PATIENTS_URL + "/by-id/" + id,
                            HttpMethod.DELETE,
                            new HttpEntity<>(getAuthHeaders()),
                            Patient.class);
            return patientResponse.getBody();
        } else {
            throw new GlobalNotFoundException("PATIENTS");
        }
    }

    public Patient deleteByCnp(String Cnp) {
        Patient patientDTO = findByCnp(Cnp);
        if (patientDTO != null) {
            ResponseEntity<Patient> patientResponse =
                    restTemplate.exchange(
                            PATIENTS_URL + "/by-cnp/" + Cnp,
                            HttpMethod.DELETE,
                            new HttpEntity<>(getAuthHeaders()),
                            Patient.class);
            return patientResponse.getBody();
        } else {
            throw new GlobalNotFoundException("PATIENTS");
        }
    }

}