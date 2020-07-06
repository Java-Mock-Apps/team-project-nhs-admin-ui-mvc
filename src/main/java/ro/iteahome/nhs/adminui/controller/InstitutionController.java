package ro.iteahome.nhs.adminui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ro.iteahome.nhs.adminui.model.entity.Institution;
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
    public ModelAndView showAddForm(Institution institution) {
        String[] institutionTypes = institutionService.getInstitutionTypes();
        return new ModelAndView("institution/add-form").addObject("types", institutionTypes);
    }

    @GetMapping("/get-form")
    public String showGetForm(Institution institution) {
        return "institution/get-form";
    }

    @GetMapping("/update-search-form")
    public String showUpdateSearchForm(Institution institution) {
        return "institution/update-search-form";
    }

    @GetMapping("/delete-form")
    public String showDeleteForm(Institution institution) {
        return "institution/delete-form";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------

    // TODO: Incorporate exception handling. Leaving form fields empty is an issue.

    @PostMapping
    public ModelAndView add(@Valid Institution institution) {
        Institution databaseInstitution = institutionService.add(institution);
        return new ModelAndView("institution/home-institution").addObject(databaseInstitution);
    }

    @GetMapping("/by-cui")
    public ModelAndView getByCui(Institution institution) {
        Institution databaseInstitution = institutionService.findByCui(institution.getCui());
        return new ModelAndView("institution/home-institution").addObject(databaseInstitution);
    }

    @GetMapping("/update-form-by-cui")
    public ModelAndView showUpdateFormByCui(Institution institution) {
        String[] institutionTypes = institutionService.getInstitutionTypes();
        Institution databaseInstitution = institutionService.findByCui(institution.getCui());
        return new ModelAndView("institution/update-form")
                .addObject(databaseInstitution).addObject("types", institutionTypes);
    }

    @PostMapping("/updated-institution")
    public ModelAndView update(@Valid Institution institution) {
        Institution databaseInstitution = institutionService.update(institution);
        return new ModelAndView("institution/home-institution").addObject(databaseInstitution);
    }

    @PostMapping("/delete-by-cui")
    public ModelAndView deleteByCui(Institution institution) {
        Institution databaseInstitution = institutionService.findByCui(institution.getCui());
        institutionService.deleteByCui(institution.getCui());
        return new ModelAndView("institution/home-institution").addObject(databaseInstitution);
    }
}
