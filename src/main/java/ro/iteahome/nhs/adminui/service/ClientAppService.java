package ro.iteahome.nhs.adminui.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.config.rest.RestConfig;
import ro.iteahome.nhs.adminui.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.adminui.model.dto.ClientAppCredentials;
import ro.iteahome.nhs.adminui.model.dto.ClientAppDTO;
import ro.iteahome.nhs.adminui.model.dto.RoleCreationDTO;
import ro.iteahome.nhs.adminui.model.dto.RoleDTO;
import ro.iteahome.nhs.adminui.model.entity.ClientApp;
import ro.iteahome.nhs.adminui.model.entity.Role;

import javax.validation.Valid;
import java.util.Base64;

@Service
public class ClientAppService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestConfig restConfig;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

// FIELDS: -------------------------------------------------------------------------------------------------------------

    private final String CREDENTIALS = "NHS_ADMIN_UI:P@ssW0rd!";
    private final String ENCODED_CREDENTIALS = new String(Base64.getEncoder().encode(CREDENTIALS.getBytes()));

// AUTHENTICATION FOR REST REQUESTS: -----------------------------------------------------------------------------------

    private HttpHeaders getAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + ENCODED_CREDENTIALS);
        return headers;
    }

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public ClientApp add(ClientAppDTO clientAppDTO, String  roleName) {
        ClientAppCredentials clientAppCredentials = new ClientAppCredentials(
                clientAppDTO.getName(),
                clientAppDTO.getPassword()
        );
        ResponseEntity<ClientApp> clientAppResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getCLIENT_APPS_URI() + "/with-role-name/" + roleName,
                        HttpMethod.POST,
                        new HttpEntity<>(clientAppDTO,restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        ClientApp.class);
        return clientAppResponse.getBody();
    }

    public ClientApp findById(int id) {
        ResponseEntity<ClientApp> clientAppResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getCLIENT_APPS_URI() + "/by-id/" + id,
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        ClientApp.class);
        ClientApp clientApp = clientAppResponse.getBody();
        if (clientApp != null) {
            return clientApp;
        } else {
            throw new GlobalNotFoundException("CLIENT APP");
        }
    }

    public ClientApp findByName(String name) {
        ResponseEntity<ClientApp> clientAppResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getCLIENT_APPS_URI() + "/by-name/" + name,
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        ClientApp.class);
        ClientApp clientApp = clientAppResponse.getBody();
        if (clientApp != null) {
            return clientApp;
        } else {
            throw new GlobalNotFoundException("CLIENT APP");
        }
    }

    public ClientApp update(@Valid ClientApp clientApp,String roleName) {
        ClientApp databaseClientApp = findById(clientApp.getId());
        if (databaseClientApp != null) {
            ResponseEntity<ClientApp> clientAppResponse =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getCLIENT_APPS_URI() + "/with-role-name/" + roleName,
                            HttpMethod.PUT,
                            new HttpEntity<>(clientApp,restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            ClientApp.class);
            return clientAppResponse.getBody();
        } else {
            throw new GlobalNotFoundException("CLIENT APP");
        }
    }

    public ClientApp updateRole(ClientApp clientApp, int roleId) {
        ClientApp databaseClientApp = findById(clientApp.getId());
        if (databaseClientApp != null) {
            ResponseEntity<ClientApp> clientAppResponse =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getCLIENT_APPS_URI() +"/role/?clientAppId=" + clientApp.getId()+ "&roleId=" + roleId,
                            HttpMethod.PUT,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            ClientApp.class);
            return clientAppResponse.getBody();
        } else {
            throw new GlobalNotFoundException("CLIENT APP");
        }
    }

    public ClientApp deleteByName(String name) {
        ClientApp databaseClientApp = findByName(name);
        if (databaseClientApp != null) {
            ResponseEntity<ClientApp> clientAppResponse =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getCLIENT_APPS_URI() + "/by-name/" + name,
                            HttpMethod.DELETE,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            ClientApp.class);
            return clientAppResponse.getBody();
        } else {
            throw new GlobalNotFoundException("CLIENT APP");
        }
    }
}
