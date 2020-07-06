package ro.iteahome.nhs.adminui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.config.rest.RestConfig;
import ro.iteahome.nhs.adminui.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.adminui.model.entity.Institution;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstitutionService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestConfig restConfig;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public Institution add(Institution institution) {
        ResponseEntity<Institution> institutionResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getINSTITUTIONS_URI(),
                        HttpMethod.POST,
                        new HttpEntity<>(institution, restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        Institution.class);
        return institutionResponse.getBody();
    }

    public String[] getInstitutionTypes() {
        ResponseEntity<String[]> institutionResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getINSTITUTIONS_URI() + "/type",
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        String[].class);
        return institutionResponse.getBody();
    }

    public Institution findByCui(String cui) {
        ResponseEntity<Institution> institutionResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getINSTITUTIONS_URI() + "/by-cui/" + cui,
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        Institution.class);
        Institution institutionDTO = institutionResponse.getBody();
        if (institutionDTO != null) {
            return institutionDTO;
        } else {
            throw new GlobalNotFoundException("INSTITUTION");
        }
    }

    public Institution update(Institution newInstitution) {
        Institution institutionDTO = findByCui(newInstitution.getCui());
        if (institutionDTO != null) {
            restTemplate.exchange(
                    restConfig.getSERVER_URL() + restConfig.getINSTITUTIONS_URI(),
                    HttpMethod.PUT,
                    new HttpEntity<>(newInstitution, restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                    Institution.class);
            return findByCui(newInstitution.getCui());
        } else {
            throw new GlobalNotFoundException("INSTITUTION");
        }
    }

    public Institution deleteByCui(String cui) {
        Institution institutionDTO = findByCui(cui);
        if (institutionDTO != null) {
            ResponseEntity<Institution> institutionResponse =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getINSTITUTIONS_URI() + "/by-cui/" + cui,
                            HttpMethod.DELETE,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            Institution.class);
            return institutionResponse.getBody();
        } else {
            throw new GlobalNotFoundException("INSTITUTIONS");
        }
    }
    public ArrayList<Institution> getInstitutions () {
        ResponseEntity<ArrayList<Institution>> institutionResponseList =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getINSTITUTIONS_URI() + "/all",
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        new ParameterizedTypeReference<>() {});
        return institutionResponseList.getBody();
    }

}
