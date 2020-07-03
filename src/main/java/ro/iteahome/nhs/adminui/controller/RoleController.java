package ro.iteahome.nhs.adminui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.iteahome.nhs.adminui.model.dto.RoleCreationDTO;
import ro.iteahome.nhs.adminui.model.dto.RoleDTO;
import ro.iteahome.nhs.adminui.model.entity.Role;
import ro.iteahome.nhs.adminui.service.RoleService;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    public String showAddForm(RoleCreationDTO roleCreationDTO) {
        return "role/add-form";
    }

    @GetMapping("/get-form")
    public String showGetForm(RoleDTO roleDTO) { return "role/get-form"; }

    @GetMapping("/update-search-form")
    public String showUpdateSearchForm(RoleDTO roleDTO) {
        return "role/update-search-form";
    }

    @GetMapping("/delete-form")
    public String showDeleteForm(RoleDTO roleDTO) {
        return "role/delete-form";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------

    // TODO: Incorporate exception handling. Leaving form fields empty is an issue.

    @PostMapping
    public ModelAndView add(@Valid RoleCreationDTO roleCreationDTO) {
        RoleDTO roleDTO = roleService.add(roleCreationDTO);
        return new ModelAndView("role/home-role").addObject(roleDTO);
    }

    @GetMapping("/by-id")
    public ModelAndView getById(RoleDTO roleDTO) {
        RoleDTO databaseRoleDTO = roleService.findById(roleDTO.getId());
        return new ModelAndView("role/home-role").addObject(databaseRoleDTO);
    }

    @GetMapping("/by-name")
    public ModelAndView getByName(RoleDTO roleDTO) {
        RoleDTO databaseRoleDTO = roleService.findByName(roleDTO.getName());
        return new ModelAndView("role/home-role").addObject(databaseRoleDTO);
    }

    @GetMapping("/update-form-by-id")
    public ModelAndView showUpdateFormById(RoleDTO roleDTO) {
        RoleDTO foundRoleDTO = roleService.findById(roleDTO.getId());
        return new ModelAndView("role/update-form").addObject(foundRoleDTO);
    }

    @GetMapping("/update-form-by-name")
    public ModelAndView showUpdateFormByName(RoleDTO roleDTO) {
        RoleDTO foundRoleDTO = roleService.findByName(roleDTO.getName());
        return new ModelAndView("role/update-form").addObject(foundRoleDTO);
    }

    @PostMapping("/updated-role")
    public ModelAndView update(RoleDTO foundRoleDTO) {
        Role role = modelMapper.map(foundRoleDTO, Role.class);
        RoleDTO roleDTO = roleService.update(role);
        return new ModelAndView("role/home-role").addObject(roleDTO);
    }

    @PostMapping("/deleted-role-id")
    public ModelAndView deleteById(RoleDTO roleDTO) {
        RoleDTO targetRoleDTO = roleService.deleteById(roleDTO.getId());
        return new ModelAndView("role/home-role").addObject(targetRoleDTO);
    }

    @PostMapping("/deleted-role-name")
    public ModelAndView deleteByName(RoleDTO roleDTO) {
        RoleDTO targetRoleDTO = roleService.deleteByName(roleDTO.getName());
        return new ModelAndView("role/home-role").addObject(targetRoleDTO);
    }
}
