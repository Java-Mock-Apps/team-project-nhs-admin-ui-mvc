package ro.iteahome.nhs.adminui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ro.iteahome.nhs.adminui.model.dto.AdminCreationDTO;
import ro.iteahome.nhs.adminui.model.dto.AdminDTO;
import ro.iteahome.nhs.adminui.model.entity.Admin;
import ro.iteahome.nhs.adminui.service.AdminService;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admins")
public class AdminController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private AdminService adminService;

    @Autowired
    private ModelMapper modelMapper;

// LINK "GET" REQUESTS: ------------------------------------------------------------------------------------------------

    @GetMapping("/add-form")
    public String showAddForm(AdminCreationDTO adminCreationDTO) {
        return "admin/add-form";
    }

    @GetMapping("/get-form")
    public String showGetForm(AdminDTO adminDTO) { return "admin/get-form"; }

    @GetMapping("/update-search-form")
    public String showUpdateSearchForm(AdminDTO adminDTO) {
        return "admin/update-search-form";
    }

    @GetMapping("/delete-form")
    public String showDeleteForm(AdminDTO adminDTO) {
        return "admin/delete-form";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------

    // TODO: Incorporate exception handling. Leaving form fields empty is an issue.

    @PostMapping
    public ModelAndView add(@Valid AdminCreationDTO adminCreationDTO) {
        AdminDTO adminDTO = adminService.add(adminCreationDTO);
        return new ModelAndView("admin/home-admin").addObject(adminDTO);
    }

    @GetMapping("/by-id")
    public ModelAndView getById(AdminDTO adminDTO) {
        AdminDTO databaseAdminDTO = adminService.findById(adminDTO.getId());
        return new ModelAndView("admin/home-admin").addObject(databaseAdminDTO);
    }

    @GetMapping("/by-email")
    public ModelAndView getByEmail(AdminDTO adminDTO) {
        AdminDTO databaseAdminDTO = adminService.findByEmail(adminDTO.getEmail());
        return new ModelAndView("admin/home-admin").addObject(databaseAdminDTO);
    }

    @PostMapping("/update-form-by-id")
    public ModelAndView showUpdateFormById(AdminDTO adminDTO) {
        Admin admin = adminService.findSensitiveById(adminDTO.getId());
        return new ModelAndView("admin/update-form").addObject(admin);
    }

    @PostMapping("/update-form-by-email")
    public ModelAndView showUpdateFormByEmail(AdminDTO adminDTO) {
        Admin admin = adminService.findSensitiveByEmail(adminDTO.getEmail());
        return new ModelAndView("admin/update-form").addObject(admin);
    }

    @PostMapping("/updated-admin")
    public ModelAndView update(@Valid Admin admin) {
        AdminDTO adminDTO = adminService.update(admin);
        return new ModelAndView("admin/home-admin").addObject(adminDTO);
    }

    @PostMapping("/delete-by-id")
    public ModelAndView deleteById(AdminDTO adminDTO) {
        AdminDTO targetAdminDTO = adminService.findById(adminDTO.getId());
        adminService.deleteById(adminDTO.getId());
        return new ModelAndView("admin/home-admin").addObject(targetAdminDTO);
    }

    @PostMapping("/delete-by-email")
    public ModelAndView deleteByEmail(AdminDTO adminDTO) {
        AdminDTO targetAdminDTO = adminService.findByEmail(adminDTO.getEmail());
        adminService.deleteByEmail(adminDTO.getEmail());
        return new ModelAndView("admin/home-admin").addObject(targetAdminDTO);
    }
}
