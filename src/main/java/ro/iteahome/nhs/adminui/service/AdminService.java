package ro.iteahome.nhs.adminui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.adminui.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.adminui.model.dto.AdminCreationDTO;
import ro.iteahome.nhs.adminui.model.dto.AdminDTO;
import ro.iteahome.nhs.adminui.model.entity.Admin;

import java.util.Base64;

@Service
public class AdminService implements UserDetailsService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

// FIELDS: -------------------------------------------------------------------------------------------------------------

    private final String CREDENTIALS = "NHS_ADMIN_UI:P@ssW0rd!";
    private final String ENCODED_CREDENTIALS = new String(Base64.getEncoder().encode(CREDENTIALS.getBytes()));
    private final String ADMINS_URL = "https://nhsbackendstage.myserverapps.com/admins";

// AUTHENTICATION FOR REST REQUESTS: -----------------------------------------------------------------------------------

    private HttpHeaders getAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + ENCODED_CREDENTIALS);
        return headers;
    }

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public AdminDTO add(AdminCreationDTO adminCreationDTO) {
        ResponseEntity<AdminDTO> responseAdminDTO =
                restTemplate.exchange(
                        ADMINS_URL,
                        HttpMethod.POST,
                        new HttpEntity<>(adminCreationDTO, getAuthHeaders()),
                        AdminDTO.class);
        AdminDTO adminDTO = responseAdminDTO.getBody();
        if (adminDTO != null) {
            return responseAdminDTO.getBody();
        } else {
            throw new GlobalAlreadyExistsException("ADMIN");
        }
    }

    public AdminDTO findById(int id) {
        ResponseEntity<AdminDTO> responseAdminDTO =
                restTemplate.exchange(
                        ADMINS_URL + "/by-id/" + id,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        AdminDTO.class);
        AdminDTO adminDTO = responseAdminDTO.getBody();
        if (adminDTO != null) {
            return adminDTO;
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public AdminDTO findByEmail(String email) {
        ResponseEntity<AdminDTO> responseAdminDTO =
                restTemplate.exchange(
                        ADMINS_URL + "/by-email/" + email,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        AdminDTO.class);
        AdminDTO adminDTO = responseAdminDTO.getBody();
        if (adminDTO != null) {
            return adminDTO;
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public Admin findSensitiveById(int id) {
        ResponseEntity<Admin> adminResponse =
                restTemplate.exchange(
                        ADMINS_URL + "/sensitive/by-id/" + id,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Admin.class);
        Admin admin = adminResponse.getBody();
        if (admin != null) {
            return admin;
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public Admin findSensitiveByEmail(String email) {
        ResponseEntity<Admin> adminResponse =
                restTemplate.exchange(
                        ADMINS_URL + "/sensitive/by-email/" + email,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Admin.class);
        Admin admin = adminResponse.getBody();
        if (admin != null) {
            return admin;
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public AdminDTO update(Admin admin) { // TODO: This works in Edge. Chrome, however, does not populate the "password" field... Find a solution.
        AdminDTO adminDTO = findById(admin.getId());
        if (adminDTO != null) {
            return restTemplate.exchange(
                    ADMINS_URL,
                    HttpMethod.PUT,
                    new HttpEntity<>(admin, getAuthHeaders()),
                    AdminDTO.class).getBody();
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public AdminDTO deleteById(int id) {
        AdminDTO adminDTO = findById(id);
        if (adminDTO != null) {
            ResponseEntity<AdminDTO> responseAdminDTO =
                    restTemplate.exchange(
                            ADMINS_URL + "/by-id/" + id,
                            HttpMethod.DELETE,
                            new HttpEntity<>(getAuthHeaders()),
                            AdminDTO.class);
            return responseAdminDTO.getBody();
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public AdminDTO deleteByEmail(String email) {
        AdminDTO adminDTO = findByEmail(email);
        if (adminDTO != null) {
            ResponseEntity<AdminDTO> responseAdminDTO =
                    restTemplate.exchange(
                            ADMINS_URL + "/by-email/" + email,
                            HttpMethod.DELETE,
                            new HttpEntity<>(getAuthHeaders()),
                            AdminDTO.class);
            return responseAdminDTO.getBody();
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

// OVERRIDDEN "UserDetailsService" METHODS: ----------------------------------------------------------------------------

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ResponseEntity<UserDetails> responseAdmin =
                restTemplate.exchange(
                        ADMINS_URL + "/sensitive/by-email/" + email,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        UserDetails.class);
        UserDetails admin = responseAdmin.getBody();
        if (admin != null) {
            return admin;
        } else {
            throw new UsernameNotFoundException(email);
        }
    }
}
