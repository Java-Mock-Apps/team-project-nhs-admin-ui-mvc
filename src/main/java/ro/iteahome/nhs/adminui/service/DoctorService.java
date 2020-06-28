package ro.iteahome.nhs.adminui.service;

import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.adminui.model.entity.Doctor;

import java.util.Base64;

@Service
public class DoctorService {
    // DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

// FIELDS: -------------------------------------------------------------------------------------------------------------

    private final String CREDENTIALS = "NHS_ADMIN_UI:P@ssW0rd!";
    private final String ENCODED_CREDENTIALS = new String(Base64.getEncoder().encode(CREDENTIALS.getBytes()));
    private final String DOCTORS_URL = "https://nhsbackendstage.myserverapps.com/doctors";
    //private final String DOCTORS_URL = "http://localhost:8081/doctors";


// AUTHENTICATION FOR REST REQUESTS: -----------------------------------------------------------------------------------

    private HttpHeaders getAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + ENCODED_CREDENTIALS);
        return headers;
    }

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public Doctor add(Doctor doctor) {
        ResponseEntity<Doctor> doctorResponse =
                restTemplate.exchange(
                        DOCTORS_URL,
                        HttpMethod.POST,
                        new HttpEntity<>(doctor, getAuthHeaders()),
                        Doctor.class);
        return doctorResponse.getBody();
    }

    public Object getSpecialties(){
        ResponseEntity<Object> doctorResponse =
                restTemplate.exchange(
                        DOCTORS_URL + "/retrieve-doctor-specialty",
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Object.class);

        return doctorResponse.getBody();
    }

    public Object getTitles(){
        ResponseEntity<Object> doctorResponse =
                restTemplate.exchange(
                        DOCTORS_URL + "/retrieve-doctor-title",
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Object.class);

        return doctorResponse.getBody();
    }


    public boolean existsByCnpAndLicenseNo(String cnp,String licenseNo) {
        ResponseEntity<Boolean> doctorExists =
                restTemplate.exchange(
                DOCTORS_URL + "/existence/by-cnp-and-license-number/?cnp="+cnp+"&licenseNo="+licenseNo,
                HttpMethod.GET,
                new HttpEntity<>(getAuthHeaders()),
                Boolean.class);
        return doctorExists.getBody();
    }

    public Doctor findById(int id) {
        ResponseEntity<Doctor> doctorResponse =
                restTemplate.exchange(
                        DOCTORS_URL + "/by-id/" + id,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Doctor.class);
        Doctor doctorDTO = doctorResponse.getBody();
        if (doctorDTO != null) {
            return doctorDTO;
        } else {
            throw new GlobalNotFoundException("DOCTORS");
        }
    }

    public Doctor findByEmail(String Email) {
        ResponseEntity<Doctor> doctorResponse =
                restTemplate.exchange(
                        DOCTORS_URL + "/by-email/" + Email,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Doctor.class);
        Doctor doctorDTO = doctorResponse.getBody();
        if (doctorDTO != null) {
            return doctorDTO;
        } else {
            throw new GlobalNotFoundException("DOCTORS");
        }
    }

    public Doctor update(Doctor newDoctor) {
        Doctor doctorDTO = findById(newDoctor.getId());
        if (doctorDTO != null) {
            restTemplate.exchange(
                    DOCTORS_URL,
                    HttpMethod.PUT,
                    new HttpEntity<>(newDoctor, getAuthHeaders()),
                    Doctor.class);
            return findById(doctorDTO.getId());
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

    public Doctor deleteById(int id) {
        Doctor doctorDTO = findById(id);
        if (doctorDTO != null) {
            ResponseEntity<Doctor> doctorResponse =
                    restTemplate.exchange(
                            DOCTORS_URL + "/by-id/" + id,
                            HttpMethod.DELETE,
                            new HttpEntity<>(getAuthHeaders()),
                            Doctor.class);
            return doctorResponse.getBody();
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

    public Doctor deleteByEmail(String Email) {
        Doctor doctorDTO = findByEmail(Email);
        if (doctorDTO != null) {
            ResponseEntity<Doctor> doctorResponse =
                    restTemplate.exchange(
                            DOCTORS_URL + "/by-email/" + Email,
                            HttpMethod.DELETE,
                            new HttpEntity<>(getAuthHeaders()),
                            Doctor.class);
            return doctorResponse.getBody();
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

}