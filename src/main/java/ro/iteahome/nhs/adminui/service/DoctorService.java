package ro.iteahome.nhs.adminui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.config.rest.RestConfig;
import ro.iteahome.nhs.adminui.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.adminui.model.entity.Doctor;

@Service
public class DoctorService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestConfig restConfig;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public Doctor add(Doctor doctor) {
        ResponseEntity<Doctor> doctorResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getDOCTORS_URI(),
                        HttpMethod.POST,
                        new HttpEntity<>(doctor, restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        Doctor.class);
        return doctorResponse.getBody();
    }

    public String[] getSpecialties() {
        ResponseEntity<String[]> doctorResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getDOCTORS_URI() + "/specialty",
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        String[].class);

        return doctorResponse.getBody();
    }

    public String[] getTitles() {
        ResponseEntity<String[]> doctorResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getDOCTORS_URI() + "/title",
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        String[].class);

        return doctorResponse.getBody();
    }

    public boolean existsByCnp(String cnp) {
        ResponseEntity<Boolean> doctorExists =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getDOCTORS_URI() + "/existence/by-cnp/" + cnp,
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        Boolean.class);
        return doctorExists.getBody();
    }

    public Doctor findByCnp(String Cnp) {
        ResponseEntity<Doctor> doctorResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getDOCTORS_URI() + "/by-cnp/" + Cnp,
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        Doctor.class);
        Doctor doctorDTO = doctorResponse.getBody();
        if (doctorDTO != null) {
            return doctorDTO;
        } else {
            throw new GlobalNotFoundException("DOCTORS");
        }
    }

    public Doctor update(Doctor newDoctor) {
//        Doctor doctorDTO = findByCnp(newDoctor.getCnp());
        if (newDoctor != null) {
            restTemplate.exchange(
                    restConfig.getSERVER_URL() + restConfig.getDOCTORS_URI(),
                    HttpMethod.PUT,
                    new HttpEntity<>(newDoctor, restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                    Doctor.class);
            return findByCnp(newDoctor.getCnp());
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

    public Doctor deleteByCnp(String Cnp) {
        Doctor doctorDTO = findByCnp(Cnp);
        if (doctorDTO != null) {
            ResponseEntity<Doctor> doctorResponse =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getDOCTORS_URI() + "/by-cnp/" + Cnp,
                            HttpMethod.DELETE,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            Doctor.class);
            return doctorResponse.getBody();
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }
}
