package ro.iteahome.nhs.adminui.service;

import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.config.rest.RestConfig;
import ro.iteahome.nhs.adminui.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.adminui.exception.technical.GlobalRequestFailedException;
import ro.iteahome.nhs.adminui.model.dto.AdminDTO;
import ro.iteahome.nhs.adminui.model.entity.Admin;
import ro.iteahome.nhs.adminui.model.form.AdminCreationForm;
import ro.iteahome.nhs.adminui.model.form.AdminEmailForm;
import ro.iteahome.nhs.adminui.model.form.AdminUpdateForm;

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

    public AdminDTO add(AdminCreationForm adminCreationForm) throws Exception {
        Admin admin = buildAdmin(adminCreationForm);
        try {
            ResponseEntity<AdminDTO> responseAdminDTO =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getADMINS_URI(),
                            HttpMethod.POST,
                            new HttpEntity<>(admin, restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            AdminDTO.class);
            return responseAdminDTO.getBody();
        } catch (RestClientException ex) {
            if (ex instanceof HttpClientErrorException.BadRequest && (causedByInvalid(ex) || causedByDuplicate(ex))) {
                throw new Exception(parseInvalidOrDuplicate(ex));
            } else {
                logTechnicalWarning("ADMIN", ex);
                throw new GlobalRequestFailedException("ADMIN");
            }
        }
    }

    public AdminDTO findByEmail(String email) {
        try {
            ResponseEntity<AdminDTO> responseAdminDTO =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getADMINS_URI() + "/by-email/" + email,
                            HttpMethod.GET,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            AdminDTO.class);
            return responseAdminDTO.getBody();
        } catch (RestClientException ex) {
            if (ex instanceof HttpClientErrorException.NotFound) {
                throw new GlobalNotFoundException("ADMIN");
            } else {
                logTechnicalWarning("ADMIN", ex);
                throw new GlobalRequestFailedException("ADMIN");
            }
        }
    }

    public AdminUpdateForm findSensitiveByEmail(String email) {
        try {
            ResponseEntity<AdminUpdateForm> responseAdminDTO =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getADMINS_URI() + "/sensitive/by-email/" + email,
                            HttpMethod.GET,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            AdminUpdateForm.class);
            return responseAdminDTO.getBody();
        } catch (RestClientException ex) {
            if (ex instanceof HttpClientErrorException.NotFound) {
                throw new GlobalNotFoundException("ADMIN");
            } else {
                logTechnicalWarning("ADMIN", ex);
                throw new GlobalRequestFailedException("ADMIN");
            }
        }
    }

    public AdminDTO update(AdminUpdateForm adminUpdateForm) throws Exception {
        try {
            ResponseEntity<AdminDTO> responseAdminDTO =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getADMINS_URI(),
                            HttpMethod.PUT,
                            new HttpEntity<>(adminUpdateForm, restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            AdminDTO.class);
            return responseAdminDTO.getBody();
        } catch (RestClientException ex) {
            if (ex instanceof HttpClientErrorException.NotFound) {
                throw new GlobalNotFoundException("ADMIN");
            } else if (ex instanceof HttpClientErrorException.BadRequest && (causedByInvalid(ex) || causedByDuplicate(ex))) {
                throw new Exception(parseInvalidOrDuplicate(ex));
            } else {
                logTechnicalWarning("ADMIN", ex);
                throw new GlobalRequestFailedException("ADMIN");
            }
        }
    }

    public AdminDTO deleteByEmail(AdminEmailForm adminEmailForm) throws Exception {
        try {
            ResponseEntity<AdminDTO> responseAdminDTO =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getADMINS_URI() + "/by-email/" + adminEmailForm.getEmail(),
                            HttpMethod.DELETE,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            AdminDTO.class);
            return responseAdminDTO.getBody();
        } catch (RestClientException ex) {
            if (ex instanceof HttpClientErrorException.NotFound) {
                throw new GlobalNotFoundException("ADMIN");
            } else if (ex instanceof HttpClientErrorException.BadRequest && causedByInvalid(ex)) {
                throw new Exception(parseInvalid(ex));
            } else {
                logTechnicalWarning("ADMIN", ex);
                throw new GlobalRequestFailedException("ADMIN");
            }
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

    private Admin buildAdmin(AdminCreationForm adminCreationForm) {
        Admin admin = modelMapper.map(adminCreationForm, Admin.class);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setStatus(1);
        admin.setRole("ADMIN");
        return admin;
    }

    private boolean causedByInvalid(Exception ex) {
        return ex.getMessage().contains("VALIDATION ERROR IN FIELD");
    }

    private boolean causedByDuplicate(Exception ex) {
        return ex.getMessage().contains("Duplicate entry");
    }

    private String parseInvalid(Exception ex) {
        if (causedByInvalid(ex)) {
            return getInvalidMessages(ex.getMessage());
        } else {
            return null;
        }
    }

    private String parseInvalidOrDuplicate(Exception ex) {
        if (causedByInvalid(ex)) {
            return getInvalidMessages(ex.getMessage());
        } else if (causedByDuplicate(ex)) {
            return getDuplicateMessage(ex.getMessage());
        } else {
            return null;
        }
    }

    private String getInvalidMessages(String message) {
        StringBuilder parsedMessageBuilder = new StringBuilder();
        String[] quotedParts = message.split("\"");
        for (String part : quotedParts) {
            if (part.contains("INVALID "))
                parsedMessageBuilder.append(part).append("\n");
        }
        return parsedMessageBuilder
                .delete(parsedMessageBuilder.length() - 2, parsedMessageBuilder.length())
                .toString();
    }

    private String getDuplicateMessage(String message) {
        return "\"" + message.split("\'")[1] + "\" ALREADY EXISTS";
    }

    private void logTechnicalWarning(String entityName, Exception ex) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.warn("TECHNICAL REST EXCEPTION IN REQUEST BY \"" + userEmail + "\" FOR ENTITY \"" + entityName + "\": \"" + ex.getMessage() + "\"");
    }
}
