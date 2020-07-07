package ro.iteahome.nhs.adminui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ro.iteahome.nhs.adminui.model.dto.ClientAppDTO;
import ro.iteahome.nhs.adminui.model.dto.RoleDTO;
import ro.iteahome.nhs.adminui.model.entity.ClientApp;
import ro.iteahome.nhs.adminui.model.entity.Role;
import ro.iteahome.nhs.adminui.model.form.RoleUpdateForm;
import ro.iteahome.nhs.adminui.service.ClientAppService;
import ro.iteahome.nhs.adminui.service.RoleService;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/client-apps")
public class ClientAppController {

    // DEPENDENCIES: -------------------------------------------------------------------------------------------------------
    @Autowired
    private ClientAppService clientAppService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModelMapper modelMapper;

// LINK "GET" REQUESTS: ------------------------------------------------------------------------------------------------

    @GetMapping("/add-form")
    public ModelAndView showAddForm(ClientAppDTO clientAppDTO) {
        List<Role> rolesList = roleService.getRolesList();
        clientAppDTO.setRolesList(rolesList);
        return new ModelAndView("client-app/add-form")
                .addObject(clientAppDTO);
    }

    @GetMapping("/get-form")
    public String showGetForm(ClientApp clientApp) {
        return "client-app/get-form";
    }

    @GetMapping("/update-search-form")
    public String showUpdateSearchForm(ClientApp clientApp) {
        return "client-app/update-search-form";
    }

    @GetMapping("/delete-form")
    public String showDeleteForm(ClientApp clientApp) {
        return "client-app/delete-form";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------

    // TODO: Incorporate exception handling. Leaving form fields empty is an issue.

    @PostMapping
    public ModelAndView add(ClientAppDTO clientAppDTO) {
        RoleDTO databaseRole = roleService.findByName(clientAppDTO.getSelectedRoleName());
        ClientApp databaseClientApp = clientAppService.add(clientAppDTO, databaseRole.getName());
        return new ModelAndView("client-app/home-client-app").addObject(databaseClientApp);
    }

    @GetMapping("/by-id")
    public ModelAndView getById(ClientApp clientApp) {
        ClientApp databaseClientApp = clientAppService.findById(clientApp.getId());
        return new ModelAndView("client-app/home-client-app").addObject(databaseClientApp);
    }

    @GetMapping("/by-name")
    public ModelAndView getByName(ClientApp clientApp) {
        ClientApp databaseClientApp = clientAppService.findByName(clientApp.getName());
        return new ModelAndView("client-app/home-client-app").addObject(databaseClientApp);
    }

    @GetMapping("/update-form-by-name")
    public ModelAndView showUpdateFormByName(ClientApp clientApp) {
        ClientApp databaseClientApp = clientAppService.findByName(clientApp.getName());
        ClientAppDTO clientAppDTO = modelMapper.map(databaseClientApp, ClientAppDTO.class);
        List<Role> rolesList = roleService.getRolesList();
        clientAppDTO.setRolesList(rolesList);
        return new ModelAndView("client-app/update-form").addObject(clientAppDTO);
    }

    @PostMapping("/updated-client-app")
    public ModelAndView update(ClientAppDTO clientAppDTO) {
        RoleDTO databaseRole = roleService.findByName(clientAppDTO.getSelectedRoleName());
        ClientApp clientApp = modelMapper.map(clientAppDTO, ClientApp.class);
        ClientApp databaseClientApp = clientAppService.update(clientApp, databaseRole.getName());
        return new ModelAndView("client-app/home-client-app").addObject(databaseClientApp);
    }

    @PostMapping("/updated-client-app/role")
    public ModelAndView updateRole(ClientApp clientApp, @Valid RoleUpdateForm roleroleUpdateForm) {
        RoleDTO databaseRole = roleService.findByName(roleroleUpdateForm.getName());
        ClientApp databaseClientApp = clientAppService.updateRole(clientApp, databaseRole.getId());
        return new ModelAndView("client-app/home-client-app").addObject(databaseClientApp)
                .addObject(databaseRole);
    }

    @PostMapping("/deleted-client-app-name")
    public ModelAndView deleteByName(ClientApp clientApp) {
        ClientApp databaseClientApp = clientAppService.deleteByName(clientApp.getName());
        return new ModelAndView("client-app/home-client-app").addObject(databaseClientApp);
    }

// OTHER METHODS: ------------------------------------------------------------------------------------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorCode", "ROL-00");
        errors.put("errorMessage", "CLIENT APP FIELDS HAVE VALIDATION ERRORS.");
        errors.putAll(ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage)));
        return new ResponseEntity<>(
                errors,
                HttpStatus.BAD_REQUEST);
    }
}
