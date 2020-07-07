package ro.iteahome.nhs.adminui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.config.rest.RestConfig;
import ro.iteahome.nhs.adminui.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.adminui.exception.technical.GlobalRequestFailedException;
import ro.iteahome.nhs.adminui.model.dto.InstitutionDTO;
import ro.iteahome.nhs.adminui.model.form.InstitutionCreationForm;
import ro.iteahome.nhs.adminui.model.form.InstitutionUpdateForm;

import java.util.List;

@Service
public class InstitutionService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestConfig restConfig;

    @Autowired
    private ServiceUtil serviceUtil;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public InstitutionDTO add(InstitutionCreationForm institutionCreationForm) throws Exception {
        try {
            ResponseEntity<InstitutionDTO> responseInstitutionDTO =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getINSTITUTIONS_URI(),
                            HttpMethod.POST,
                            new HttpEntity<>(institutionCreationForm, restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            InstitutionDTO.class);
            return responseInstitutionDTO.getBody();
        } catch (RestClientException ex) {
            if ((ex instanceof HttpClientErrorException.BadRequest && serviceUtil.causedByInvalid(ex)) ||
                    (ex instanceof HttpClientErrorException.BadRequest && serviceUtil.causedByDuplicate(ex))) {
                throw new Exception(serviceUtil.parseInvalidOrDuplicate(ex));
            } else {
                serviceUtil.logTechnicalWarning("INSTITUTION", ex);
                throw new GlobalRequestFailedException("INSTITUTION");
            }
        }
    }

    public List<InstitutionDTO> findAll() {
        try {
            ResponseEntity<List<InstitutionDTO>> responseInstitutionDTOList =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getINSTITUTIONS_URI() + "/all",
                            HttpMethod.GET,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            new ParameterizedTypeReference<List<InstitutionDTO>>() {
                            });
            return responseInstitutionDTOList.getBody();
        } catch (RestClientException ex) {
            serviceUtil.logTechnicalWarning("INSTITUTION", ex);
            throw new GlobalRequestFailedException("INSTITUTION");
        }
    }

    public InstitutionDTO findByCui(String cui) {
        try {
            ResponseEntity<InstitutionDTO> responseInstitutionDTO =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getINSTITUTIONS_URI() + "/by-cui/" + cui,
                            HttpMethod.GET,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            InstitutionDTO.class);
            return responseInstitutionDTO.getBody();
        } catch (RestClientException ex) {
            if (ex instanceof HttpClientErrorException.NotFound) {
                throw new GlobalNotFoundException("INSTITUTION");
            } else {
                serviceUtil.logTechnicalWarning("INSTITUTION", ex);
                throw new GlobalRequestFailedException("INSTITUTION");
            }
        }

    }

    public InstitutionDTO update(InstitutionUpdateForm institutionUpdateForm) throws Exception {
        try {
            ResponseEntity<InstitutionDTO> responseInstitutionDTO =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getINSTITUTIONS_URI(),
                            HttpMethod.PUT,
                            new HttpEntity<>(institutionUpdateForm, restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            InstitutionDTO.class);
            return responseInstitutionDTO.getBody();
        } catch (RestClientException ex) {
            if (ex instanceof HttpClientErrorException.NotFound) {
                throw new GlobalNotFoundException("INSTITUTION");
            } else if (ex instanceof HttpClientErrorException.BadRequest && (serviceUtil.causedByInvalid(ex) || serviceUtil.causedByDuplicate(ex))) {
                throw new Exception(serviceUtil.parseInvalidOrDuplicate(ex));
            } else {
                serviceUtil.logTechnicalWarning("INSTITUTION", ex);
                throw new GlobalRequestFailedException("INSTITUTION");
            }
        }
    }

    public InstitutionDTO deleteByCui(String cui) throws Exception {
        try {
            ResponseEntity<InstitutionDTO> responseInstitutionDTO =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getINSTITUTIONS_URI() + "/by-cui/" + cui,
                            HttpMethod.DELETE,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            InstitutionDTO.class);
            return responseInstitutionDTO.getBody();
        } catch (RestClientException ex) {
            if (ex instanceof HttpClientErrorException.NotFound) {
                throw new GlobalNotFoundException("INSTITUTION");
            } else if (ex instanceof HttpClientErrorException.BadRequest && serviceUtil.causedByInvalid(ex)) {
                throw new Exception(serviceUtil.parseInvalid(ex));
            } else {
                serviceUtil.logTechnicalWarning("INSTITUTION", ex);
                throw new GlobalRequestFailedException("INSTITUTION");
            }
        }
    }

// OTHER METHODS: ---------------------------------------------------------------------------------------------------

    public String[] getInstitutionTypes() {
        try {
            ResponseEntity<String[]> institutionResponse =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getINSTITUTIONS_URI() + "/types",
                            HttpMethod.GET,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            String[].class);
            return institutionResponse.getBody();
        } catch (RestClientException ex) {
            serviceUtil.logTechnicalWarning("INSTITUTION", ex);
            throw new GlobalRequestFailedException("INSTITUTION");
        }
    }
}
