package ro.iteahome.nhs.adminui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.adminui.model.dto.RoleCreationDTO;
import ro.iteahome.nhs.adminui.model.dto.RoleDTO;
import ro.iteahome.nhs.adminui.model.entity.Role;

import java.util.Base64;

@Service
public class RoleService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

// FIELDS: -------------------------------------------------------------------------------------------------------------

    private final String CREDENTIALS = "NHS_ADMIN_UI:P@ssW0rd!";
    private final String ENCODED_CREDENTIALS = new String(Base64.getEncoder().encode(CREDENTIALS.getBytes()));
    private final String ROLES_URL = "https://nhsbackendstage.myserverapps.com/roles";

// AUTHENTICATION FOR REST REQUESTS: -----------------------------------------------------------------------------------

    private HttpHeaders getAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + ENCODED_CREDENTIALS);
        return headers;
    }

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public RoleDTO add(RoleCreationDTO roleCreationDTO) {
        ResponseEntity<RoleDTO> roleResponse =
                restTemplate.exchange(
                        ROLES_URL,
                        HttpMethod.POST,
                        new HttpEntity<>(roleCreationDTO, getAuthHeaders()),
                        RoleDTO.class);
        return roleResponse.getBody();
    }

    public RoleDTO findById(int id) {
        ResponseEntity<RoleDTO> roleResponse =
                restTemplate.exchange(
                        ROLES_URL + "/by-id/" + id,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
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
                        ROLES_URL + "/by-name/" + name,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
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
                            ROLES_URL,
                            HttpMethod.PUT,
                            new HttpEntity<>(role, getAuthHeaders()),
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
                            ROLES_URL + "/by-id/" + id,
                            HttpMethod.DELETE,
                            new HttpEntity<>(getAuthHeaders()),
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
                            ROLES_URL + "/by-name/" + name,
                            HttpMethod.DELETE,
                            new HttpEntity<>(getAuthHeaders()),
                            RoleDTO.class);
            return roleResponse.getBody();
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }
}
