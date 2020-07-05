package ro.iteahome.nhs.adminui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.config.rest.RestConfig;
import ro.iteahome.nhs.adminui.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.adminui.model.form.RoleCreationForm;
import ro.iteahome.nhs.adminui.model.dto.RoleDTO;
import ro.iteahome.nhs.adminui.model.entity.Role;

@Service
public class RoleService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestConfig restConfig;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public RoleDTO add(RoleCreationForm roleCreationForm) {
        ResponseEntity<RoleDTO> roleResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getROLES_URI(),
                        HttpMethod.POST,
                        new HttpEntity<>(roleCreationForm, restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        RoleDTO.class);
        return roleResponse.getBody();
    }

    public RoleDTO findById(int id) {
        ResponseEntity<RoleDTO> roleResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getROLES_URI() + "/by-id/" + id,
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        RoleDTO.class);
        RoleDTO roleDTO = roleResponse.getBody();
        if (roleDTO != null) {
            return roleDTO;
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }

    public RoleDTO findByName(String name) {
        ResponseEntity<RoleDTO> roleResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getROLES_URI() + "/by-name/" + name,
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        RoleDTO.class);
        RoleDTO roleDTO = roleResponse.getBody();
        if (roleDTO != null) {
            return roleDTO;
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }

    public RoleDTO update(Role role) {
        RoleDTO roleDTO = findById(role.getId());
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

    public RoleDTO deleteById(int id) {
        RoleDTO roleDTO = findById(id);
        if (roleDTO != null) {
            ResponseEntity<RoleDTO> roleResponse =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getROLES_URI() + "/by-id/" + id,
                            HttpMethod.DELETE,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
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
