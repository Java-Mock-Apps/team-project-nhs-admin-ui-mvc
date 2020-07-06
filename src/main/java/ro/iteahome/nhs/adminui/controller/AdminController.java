package ro.iteahome.nhs.adminui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ro.iteahome.nhs.adminui.model.dto.AdminDTO;
import ro.iteahome.nhs.adminui.model.entity.Admin;
import ro.iteahome.nhs.adminui.model.form.AdminCreationForm;
import ro.iteahome.nhs.adminui.model.form.AdminEmailForm;
import ro.iteahome.nhs.adminui.service.AdminService;

import javax.validation.Valid;

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
    public String showAddForm(AdminCreationForm adminCreationForm) {
        return "admin/add-form";
    }

    @GetMapping("/get-form")
    public String showGetForm(AdminEmailForm adminEmailForm) {
        return "admin/get-form";
    }

    @GetMapping("/update-search-form")
    public String showUpdateSearchForm(AdminDTO adminDTO) {
        return "admin/update-search-form";
    }

    @GetMapping("/update-form")
    public ModelAndView showUpdateForm(@Valid AdminEmailForm adminEmailForm) {
        Admin admin = adminService.findSensitiveByEmail(adminEmailForm.getEmail());
        return new ModelAndView("admin/update-form").addObject(admin);
    }

    @GetMapping("/delete-form")
    public String showDeleteForm(AdminDTO adminDTO) {
        return "admin/delete-form";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------

    // TODO: Incorporate exception handling. Leaving form fields empty is an issue.

//    @PostMapping
//    public ModelAndView add(/*@Valid*/ AdminCreationForm adminCreationForm) throws Exception {
//        AdminDTO adminDTO = adminService.add(adminCreationForm);
//        return new ModelAndView("admin/home-admin").addObject(adminDTO);
//    }

    @PostMapping
    public String add(@Valid AdminCreationForm adminCreationForm, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            return "admin/add-form";
        } else {
            try {
                AdminDTO adminDTO = adminService.add(adminCreationForm);
                model.addAttribute("adminDTO", adminDTO);
                return "admin/home-admin";
            } catch (Exception ex) {
                model.addAttribute("errorMessage", ex.getMessage());
                return "admin/add-form";
            }
        }
    }

    @GetMapping("/by-email")
    public String getByEmail(@Valid AdminEmailForm adminEmailForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/get-form";
        } else {
            try {
                model.addAttribute(adminService.findByEmail(adminEmailForm.getEmail()));
                return "admin/home-admin";
            } catch (Exception ex) {
                model.addAttribute("errorMessage", ex.getMessage());
                return "admin/get-form";
            }
        }
    }

    @PostMapping("/updated-admin")
    public ModelAndView update(@Valid Admin admin) throws Exception {
        AdminDTO adminDTO = adminService.update(admin);
        return new ModelAndView("admin/home-admin").addObject(adminDTO);
    }

    @PostMapping("/delete-by-email")
    public ModelAndView deleteByEmail(AdminDTO adminDTO) {
        AdminDTO targetAdminDTO = adminService.findByEmail(adminDTO.getEmail());
        adminService.deleteByEmail(adminDTO.getEmail());
        return new ModelAndView("admin/home-admin").addObject(targetAdminDTO);
    }
}
