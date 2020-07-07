package ro.iteahome.nhs.adminui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.iteahome.nhs.adminui.model.dto.InstitutionDTO;
import ro.iteahome.nhs.adminui.model.form.InstitutionCreationForm;
import ro.iteahome.nhs.adminui.model.form.InstitutionCuiForm;
import ro.iteahome.nhs.adminui.model.form.InstitutionUpdateForm;
import ro.iteahome.nhs.adminui.service.InstitutionService;

import javax.validation.Valid;

@Controller
@RequestMapping("/medical-institutions")
public class InstitutionController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private ModelMapper modelMapper;

// LINK "GET" REQUESTS: ------------------------------------------------------------------------------------------------

    @GetMapping("/add-form")
    public String showAddForm(InstitutionCreationForm institutionCreationForm, Model model) {
        model.addAttribute("institutionTypes", institutionService.getInstitutionTypes());
        return "institution/add-form";
    }

    @GetMapping("/get-form")
    public String showGetForm(InstitutionCuiForm institutionCuiForm) {
        return "institution/get-form";
    }

    @GetMapping("/update-search-form")
    public String showUpdateSearchForm(InstitutionCuiForm institutionCuiForm) {
        return "institution/update-search-form";
    }

    @GetMapping("/update-form")
    public String showUpdateFormByCui(@Valid InstitutionCuiForm institutionCuiForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "institution/update-search-form";
        } else {
            try {
                InstitutionDTO institutionDTO = institutionService.findByCui(institutionCuiForm.getCui());
                InstitutionUpdateForm institutionUpdateForm = modelMapper.map(institutionDTO, InstitutionUpdateForm.class);
                model.addAttribute("institutionTypes", institutionService.getInstitutionTypes());
                model.addAttribute("institutionUpdateForm", institutionUpdateForm);
                return "institution/update-form";
            } catch (Exception ex) {
                model.addAttribute("errorMessage", ex.getMessage());
                return "institution/update-search-form";
            }
        }
    }

    @GetMapping("/delete-form")
    public String showDeleteForm(InstitutionCuiForm institutionCuiForm) {
        return "institution/delete-form";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------

    @PostMapping
    public String add(@Valid InstitutionCreationForm institutionCreationForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("institutionTypes", institutionService.getInstitutionTypes());
            return "institution/add-form";
        } else {
            try {
                InstitutionDTO institutionDTO = institutionService.add(institutionCreationForm);
                model.addAttribute("institutionDTO", institutionDTO);
                return "institution/home-institution";
            } catch (Exception ex) {
                model.addAttribute("institutionTypes", institutionService.getInstitutionTypes());
                model.addAttribute("errorMessage", ex.getMessage());
                return "institution/add-form";
            }
        }
    }

    @GetMapping("/by-cui")
    public String getByCui(@Valid InstitutionCuiForm institutionCuiForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "institution/get-form";
        } else {
            try {
                model.addAttribute("institutionDTO", institutionService.findByCui(institutionCuiForm.getCui()));
                return "institution/home-institution";
            } catch (Exception ex) {
                model.addAttribute("errorMessage", ex.getMessage());
                return "institution/get-form";
            }
        }
    }

    @PostMapping("/updated-institution")
    public String update(@Valid InstitutionUpdateForm institutionUpdateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("institutionTypes", institutionService.getInstitutionTypes());
            return "institution/update-form";
        } else {
            try {
                model.addAttribute("institutionDTO", institutionService.update(institutionUpdateForm));
                return "institution/home-institution";
            } catch (Exception ex) {
                model.addAttribute("institutionTypes", institutionService.getInstitutionTypes());
                model.addAttribute("errorMessage", ex.getMessage());
                return "institution/update-form";
            }
        }
    }

    @PostMapping("/delete-by-cui")
    public String deleteByCui(@Valid InstitutionCuiForm institutionCuiForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "institution/delete-form";
        } else {
            try {
                model.addAttribute("institutionDTO", institutionService.deleteByCui(institutionCuiForm.getCui()));
                return "institution/home-institution";
            } catch (Exception ex) {
                model.addAttribute("errorMessage", ex.getMessage());
                return "institution/delete-form";
            }
        }
    }
}
