package ro.iteahome.nhs.adminui.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import ro.iteahome.nhs.adminui.model.dto.RoleDTO;
import ro.iteahome.nhs.adminui.model.entity.Role;
import ro.iteahome.nhs.adminui.model.form.RoleForm;

@Service
public class RoleService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestConfig restConfig;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ServiceUtil serviceUtil;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

//    public RoleDTO add(RoleForm roleCreationForm) {
//        ResponseEntity<RoleDTO> roleResponse =
//                restTemplate.exchange(
//                        restConfig.getSERVER_URL() + restConfig.getROLES_URI(),
//                        HttpMethod.POST,
//                        new HttpEntity<>(roleCreationForm, restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
//                        RoleDTO.class);
//        return roleResponse.getBody();
//    }

    public RoleDTO add(RoleForm roleForm) throws Exception {
        Role role = modelMapper.map(roleForm, Role.class);
        try {
            ResponseEntity<RoleDTO> responseRoleDTO =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getROLES_URI(),
                            HttpMethod.POST,
                            new HttpEntity<>(role, restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            RoleDTO.class);
            return responseRoleDTO.getBody();
        } catch (RestClientException ex) {
            if (ex instanceof HttpClientErrorException.BadRequest && (serviceUtil.causedByInvalid(ex) || serviceUtil.causedByDuplicate(ex))) {
                throw new Exception(serviceUtil.parseInvalidOrDuplicate(ex));
            } else {
                serviceUtil.logTechnicalWarning("ROLE", ex);
                throw new GlobalRequestFailedException("ROLE");
            }
        }
    }

    public RoleDTO findByName(String name) {
        try {
            ResponseEntity<RoleDTO> responseRoleDTO =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getROLES_URI() + "/by-name/" + name,
                            HttpMethod.GET,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            RoleDTO.class);
            return responseRoleDTO.getBody();
        } catch (RestClientException ex) {
            if (ex instanceof HttpClientErrorException.NotFound) {
                throw new GlobalNotFoundException("ROLE");
            } else {
                serviceUtil.logTechnicalWarning("ROLE", ex);
                throw new GlobalRequestFailedException("ROLE");
            }
        }
    }

//    public RoleDTO findByName(String name) {
//        ResponseEntity<RoleDTO> roleResponse =
//                restTemplate.exchange(
//                        restConfig.getSERVER_URL() + restConfig.getROLES_URI() + "/by-name/" + name,
//                        HttpMethod.GET,
//                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
//                        RoleDTO.class);
//        RoleDTO roleDTO = roleResponse.getBody();
//        if (roleDTO != null) {
//            return roleDTO;
//        } else {
//            throw new GlobalNotFoundException("ROLE");
//        }
//    }

    public RoleDTO update(Role role) {
        RoleDTO roleDTO = findByName(role.getName());
        if (roleDTO != null) {
            ResponseEntity<RoleDTO> roleResponse =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getROLES_URI(),
                            HttpMethod.PUT,
                            new HttpEntity<>(role, restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            RoleDTO.class);
            return roleResponse.getBody();
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }

    public RoleDTO deleteByName(String name) {
        RoleDTO roleDTO = findByName(name);
        if (roleDTO != null) {
            ResponseEntity<RoleDTO> roleResponse =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getROLES_URI() + "/by-name/" + name,
                            HttpMethod.DELETE,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            RoleDTO.class);
            return roleResponse.getBody();
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }
}
