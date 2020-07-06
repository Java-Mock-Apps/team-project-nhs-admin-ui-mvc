package ro.iteahome.nhs.adminui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ro.iteahome.nhs.adminui.model.entity.Patient;
import ro.iteahome.nhs.adminui.service.PatientService;

import javax.validation.Valid;

@Controller
@RequestMapping("/patients")
public class PatientController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private PatientService patientService;

    @Autowired
    private ModelMapper modelMapper;

// LINK "GET" REQUESTS: ------------------------------------------------------------------------------------------------

    @GetMapping("/add-form")
    public ModelAndView showAddForm(Patient patient) {
        return new ModelAndView("patient/add-form");
    }

    @GetMapping("/get-form")
    public String showGetForm(Patient patient) {
        return "patient/get-form";
    }

    @GetMapping("/update-search-form")
    public String showUpdateSearchForm(Patient patient) {
        return "patient/update-search-form";
    }

    @GetMapping("/delete-form")
    public String showDeleteForm(Patient patient) {
        return "patient/delete-form";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------

    // TODO: Incorporate exception handling. Leaving form fields empty is an issue.

    @PostMapping
    public ModelAndView add(@Valid Patient patient) {
        Patient databasePatient = patientService.add(patient);
        return new ModelAndView("patient/home-patient").addObject(databasePatient);
    }

    @GetMapping("/by-cnp")
    public ModelAndView getByCnp(Patient patient) {
        Patient databasePatient = patientService.findByCnp(patient.getCnp());
        return new ModelAndView("patient/home-patient").addObject(databasePatient);
    }

    @GetMapping("/update-form-by-cnp")
    public ModelAndView showUpdateFormByCnp(Patient patient) {
        Patient databasePatient = patientService.findByCnp(patient.getCnp());
        return new ModelAndView("patient/update-form").addObject(databasePatient);
    }

    @PostMapping("/updated-patient")
    public ModelAndView update(@Valid Patient patient) {
        Patient databasePatient = patientService.update(patient);
        return new ModelAndView("patient/home-patient").addObject(databasePatient);
    }

    @PostMapping("/delete-by-cnp")
    public ModelAndView deleteByCnp(Patient patient) {
        Patient databasePatient = patientService.findByCnp(patient.getCnp());
        patientService.deleteByCnp(databasePatient.getCnp());
        return new ModelAndView("patient/home-patient").addObject(databasePatient);
    }
}
