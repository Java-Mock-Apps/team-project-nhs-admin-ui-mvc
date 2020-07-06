package ro.iteahome.nhs.adminui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.iteahome.nhs.adminui.model.dto.AdminDTO;
import ro.iteahome.nhs.adminui.model.form.AdminCreationForm;
import ro.iteahome.nhs.adminui.model.form.AdminEmailForm;
import ro.iteahome.nhs.adminui.model.form.AdminUpdateForm;
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
    public String showUpdateSearchForm(AdminEmailForm adminEmailForm) {
        return "admin/update-search-form";
    }

    @GetMapping("/update-form")
    public String showUpdateForm(@Valid AdminEmailForm adminEmailForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/update-search-form";
        } else {
            try {
                AdminUpdateForm adminUpdateForm = adminService.findSensitiveByEmail(adminEmailForm.getEmail());
                model.addAttribute("adminUpdateForm", adminUpdateForm);
                return "admin/update-form";
            } catch (Exception ex) {
                model.addAttribute("errorMessage", ex.getMessage());
                return "admin/update-search-form";
            }
        }
    }

    @GetMapping("/delete-form")
    public String showDeleteForm(AdminEmailForm adminEmailForm) {
        return "admin/delete-form";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------

    @PostMapping
    public String add(@Valid AdminCreationForm adminCreationForm, BindingResult bindingResult, Model model) {
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
                model.addAttribute("adminDTO", adminService.findByEmail(adminEmailForm.getEmail()));
                return "admin/home-admin";
            } catch (Exception ex) {
                model.addAttribute("errorMessage", ex.getMessage());
                return "admin/get-form";
            }
        }
    }

    @PostMapping("/updated-admin")
    public String update(@Valid AdminUpdateForm adminUpdateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/update-form";
        } else {
            try {
                model.addAttribute("adminDTO", adminService.update(adminUpdateForm));
                return "admin/home-admin";
            } catch (Exception ex) {
                model.addAttribute("errorMessage", ex.getMessage());
                return "admin/update-form";
            }
        }
    }

    @PostMapping("/delete-by-email")
    public String deleteByEmail(@Valid AdminEmailForm adminEmailForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/delete-form";
        } else {
            try {
                model.addAttribute("adminDTO", adminService.deleteByEmail(adminEmailForm));
                return "admin/home-admin";
            } catch (Exception ex) {
                model.addAttribute("errorMessage", ex.getMessage());
                return "admin/delete-form";
            }
        }
    }
}
