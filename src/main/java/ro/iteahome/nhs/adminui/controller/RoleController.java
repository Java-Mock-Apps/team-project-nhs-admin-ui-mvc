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
import ro.iteahome.nhs.adminui.model.dto.RoleDTO;
import ro.iteahome.nhs.adminui.model.form.RoleForm;
import ro.iteahome.nhs.adminui.model.form.RoleUpdateForm;
import ro.iteahome.nhs.adminui.service.RoleService;

import javax.validation.Valid;

@Controller
@RequestMapping("/roles")
public class RoleController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModelMapper modelMapper;

// LINK "GET" REQUESTS: ------------------------------------------------------------------------------------------------

    @GetMapping("/add-form")
    public String showAddForm(RoleForm roleForm) {
        return "role/add-form";
    }

    @GetMapping("/get-form")
    public String showGetForm(RoleForm roleForm) {
        return "role/get-form";
    }

    @GetMapping("/update-search-form")
    public String showUpdateSearchForm(RoleUpdateForm roleUpdateForm) {
        return "role/update-search-form";
    }

//    @GetMapping("/update-form-by-name")
//    public ModelAndView showUpdateFormByName(RoleDTO roleDTO) {
//        RoleDTO foundRoleDTO = roleService.findByName(roleDTO.getName());
//        return new ModelAndView("role/update-form").addObject(foundRoleDTO);
//    }

    @GetMapping("/update-form")
    public String showUpdateForm(@Valid RoleUpdateForm roleUpdateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "role/update-search-form";
        } else {
            try {
                RoleDTO targetRoleDTO = roleService.findByName(roleUpdateForm.getName());
                roleUpdateForm.setId(targetRoleDTO.getId());
                model.addAttribute("roleUpdateForm", roleUpdateForm);
                return "role/update-form";
            } catch (Exception ex) {
                model.addAttribute("errorMessage", ex.getMessage());
                return "role/update-search-form";
            }
        }
    }

    @GetMapping("/delete-form")
    public String showDeleteForm(RoleDTO roleDTO) {
        return "role/delete-form";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------

    @PostMapping
    public String add(@Valid RoleForm roleForm, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            return "role/add-form";
        } else {
            try {
                RoleDTO roleDTO = roleService.add(roleForm);
                model.addAttribute("roleDTO", roleDTO);
                return "role/home-role";
            } catch (Exception ex) {
                model.addAttribute("errorMessage", ex.getMessage());
                return "role/add-form";
            }
        }
    }

    @GetMapping("/by-name")
    public String getByName(@Valid RoleForm roleForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "role/get-form";
        } else {
            try {
                model.addAttribute("roleDTO", roleService.findByName(roleForm.getName()));
                return "role/home-role";
            } catch (Exception ex) {
                model.addAttribute("errorMessage", ex.getMessage());
                return "role/get-form";
            }
        }
    }

//    @PostMapping("/updated-role")
//    public ModelAndView update(RoleDTO foundRoleDTO) {
//        Role role = modelMapper.map(foundRoleDTO, Role.class);
//        RoleDTO roleDTO = roleService.update(role);
//        return new ModelAndView("role/home-role").addObject(roleDTO);
//    }

    @PostMapping("/updated-role")
    public String update(@Valid RoleUpdateForm roleUpdateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "role/update-form";
        } else {
            try {
                model.addAttribute("roleDTO", roleService.update(roleUpdateForm));
                return "role/home-role";
            } catch (Exception ex) {
                model.addAttribute("errorMessage", ex.getMessage());
                return "role/update-form";
            }
        }
    }

    @PostMapping("/deleted-role-name")
    public ModelAndView deleteByName(RoleDTO roleDTO) {
        RoleDTO targetRoleDTO = roleService.deleteByName(roleDTO.getName());
        return new ModelAndView("role/home-role").addObject(targetRoleDTO);
    }
}
