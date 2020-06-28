package ro.iteahome.nhs.adminui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.adminui.model.entity.Nurse;

import java.util.Base64;

@Service
public class NurseService {
    // DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

// FIELDS: -------------------------------------------------------------------------------------------------------------

    private final String CREDENTIALS = "NHS_ADMIN_UI:P@ssW0rd!";
    private final String ENCODED_CREDENTIALS = new String(Base64.getEncoder().encode(CREDENTIALS.getBytes()));
    private final String NURSES_URL = "https://nhsbackend.myserverapps.com/nurses";
    //private final String NURSES_URL = "http://localhost:8081/nurses";


// AUTHENTICATION FOR REST REQUESTS: -----------------------------------------------------------------------------------

    private HttpHeaders getAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + ENCODED_CREDENTIALS);
        return headers;
    }

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public Nurse add(Nurse Nurse) {
        ResponseEntity<Nurse> nurseResponse =
                restTemplate.exchange(
                        NURSES_URL,
                        HttpMethod.POST,
                        new HttpEntity<>(Nurse, getAuthHeaders()),
                        Nurse.class);
        return nurseResponse.getBody();
    }


    public Object getSpecialties(){
        ResponseEntity<Object> nurseResponse =
                restTemplate.exchange(
                        NURSES_URL + "/retrieve-nurse-specialty",
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Object.class);

        return nurseResponse.getBody();
    }

    public Object getTitles(){
        ResponseEntity<Object> nurseResponse =
                restTemplate.exchange(
                        NURSES_URL + "/retrieve-nurse-title",
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Object.class);

        return nurseResponse.getBody();
    }


    public boolean existsByCnpAndLicenseNo(String cnp,String licenseNo) {
        ResponseEntity<Boolean> nurseExists =
                restTemplate.exchange(
                        NURSES_URL + "/existence/by-cnp-and-license-number?cnp="+cnp+"&licenseNo="+licenseNo,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Boolean.class);
        return nurseExists.getBody();
    }

    public Nurse findById(int id) {
        ResponseEntity<Nurse> nurseResponse =
                restTemplate.exchange(
                        NURSES_URL + "/by-id/" + id,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Nurse.class);
        Nurse nurseDTO = nurseResponse.getBody();
        if (nurseDTO != null) {
            return nurseDTO;
        } else {
            throw new GlobalNotFoundException("NURSES");
        }
    }

    public Nurse findByEmail(String Email) {
        ResponseEntity<Nurse> nurseResponse =
                restTemplate.exchange(
                        NURSES_URL + "/by-email/" + Email,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Nurse.class);
        Nurse nurseDTO = nurseResponse.getBody();
        if (nurseDTO != null) {
            return nurseDTO;
        } else {
            throw new GlobalNotFoundException("NURSES");
        }
    }

    public Nurse update(Nurse newNurse) {
        Nurse nurseDTO = findById(newNurse.getId());
        if (nurseDTO != null) {
            restTemplate.exchange(
                    NURSES_URL,
                    HttpMethod.PUT,
                    new HttpEntity<>(newNurse, getAuthHeaders()),
                    Nurse.class);
            return findById(nurseDTO.getId());
        } else {
            throw new GlobalNotFoundException("NURSES");
        }
    }

    public Nurse deleteById(int id) {
        Nurse nurseDTO = findById(id);
        if (nurseDTO != null) {
            ResponseEntity<Nurse> nurseResponse =
                    restTemplate.exchange(
                            NURSES_URL + "/by-id/" + id,
                            HttpMethod.DELETE,
                            new HttpEntity<>(getAuthHeaders()),
                            Nurse.class);
            return nurseResponse.getBody();
        } else {
            throw new GlobalNotFoundException("NURSES");
        }
    }

    public Nurse deleteByEmail(String Email) {
        Nurse nurseDTO = findByEmail(Email);
        if (nurseDTO != null) {
            ResponseEntity<Nurse> nurseResponse =
                    restTemplate.exchange(
                            NURSES_URL + "/by-email/" + Email,
                            HttpMethod.DELETE,
                            new HttpEntity<>(getAuthHeaders()),
                            Nurse.class);
            return nurseResponse.getBody();
        } else {
            throw new GlobalNotFoundException("NURSE");
        }
    }

}