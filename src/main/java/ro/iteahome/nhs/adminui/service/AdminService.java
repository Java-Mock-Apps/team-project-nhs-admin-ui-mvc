package ro.iteahome.nhs.adminui.service;

import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.config.rest.RestConfig;
import ro.iteahome.nhs.adminui.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.adminui.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.adminui.exception.technical.GlobalRequestFailedException;
import ro.iteahome.nhs.adminui.model.dto.AdminCreationDTO;
import ro.iteahome.nhs.adminui.model.dto.AdminDTO;
import ro.iteahome.nhs.adminui.model.entity.Admin;

import java.util.Optional;

@Service
public class AdminService implements UserDetailsService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestConfig restConfig;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Logger logger;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public AdminDTO add(AdminCreationDTO adminCreationDTO) {
        Admin admin = buildAdmin(adminCreationDTO);
        ResponseEntity<AdminDTO> responseAdminDTO =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getADMINS_URI(),
                        HttpMethod.POST,
                        new HttpEntity<>(admin, restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        AdminDTO.class);
        AdminDTO adminDTO = responseAdminDTO.getBody();
        if (adminDTO != null) {
            return responseAdminDTO.getBody();
        } else {
            throw new GlobalAlreadyExistsException("ADMIN");
        }
    }

    public AdminDTO findById(int id) throws GlobalNotFoundException, GlobalRequestFailedException {
        try {
            ResponseEntity<AdminDTO> responseAdminDTO =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getADMINS_URI() + "/by-id/" + id,
                            HttpMethod.GET,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            AdminDTO.class);
            return responseAdminDTO.getBody();
        } catch (RestClientException ex) {
            if (ex instanceof HttpClientErrorException.NotFound) {
                throw new GlobalNotFoundException("ADMIN");
            } else {
                logger.warn("REST EXCEPTION FOR ADMIN: " + ex.getMessage());
                throw new GlobalRequestFailedException("ADMIN");
            }
        }
    }

    public AdminDTO findByEmail(String email) {
        ResponseEntity<AdminDTO> responseAdminDTO =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getADMINS_URI() + "/by-email/" + email,
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
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
                        restConfig.getSERVER_URL() + restConfig.getADMINS_URI() + "/sensitive/by-id/" + id,
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
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
                        restConfig.getSERVER_URL() + restConfig.getADMINS_URI() + "/sensitive/by-email/" + email,
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        Admin.class);
        Admin admin = adminResponse.getBody();
        if (admin != null) {
            return admin;
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public AdminDTO update(Admin admin) throws Exception {
        AdminDTO adminDTO = findById(admin.getId());
        if (adminDTO != null) {
            return restTemplate.exchange(
                    restConfig.getSERVER_URL() + restConfig.getADMINS_URI(),
                    HttpMethod.PUT,
                    new HttpEntity<>(admin, restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                    AdminDTO.class).getBody();
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public AdminDTO deleteById(int id) throws Exception {
        AdminDTO adminDTO = findById(id);
        if (adminDTO != null) {
            ResponseEntity<AdminDTO> responseAdminDTO =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getADMINS_URI() + "/by-id/" + id,
                            HttpMethod.DELETE,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
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
                            restConfig.getSERVER_URL() + restConfig.getADMINS_URI() + "/by-email/" + email,
                            HttpMethod.DELETE,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            AdminDTO.class);
            return responseAdminDTO.getBody();
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

// OVERRIDDEN "UserDetailsService" METHODS: ----------------------------------------------------------------------------

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ResponseEntity<Admin> responseAdmin =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getADMINS_URI() + "/sensitive/by-email/" + email,
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        Admin.class);
        Optional<Admin> optionalAdmin = Optional.ofNullable(responseAdmin.getBody());
        return optionalAdmin.orElseThrow(() -> new UsernameNotFoundException(email));
    }

// OTHER METHODS: ------------------------------------------------------------------------------------------------------

    private Admin buildAdmin(AdminCreationDTO adminCreationDTO) {
        Admin admin = modelMapper.map(adminCreationDTO, Admin.class);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setStatus(1);
        admin.setRole("ADMIN");
        return admin;
    }
}
