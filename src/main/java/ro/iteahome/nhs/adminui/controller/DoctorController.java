package ro.iteahome.nhs.adminui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.iteahome.nhs.adminui.model.entity.Doctor;
import ro.iteahome.nhs.adminui.model.entity.Institution;
import ro.iteahome.nhs.adminui.service.DoctorService;
import ro.iteahome.nhs.adminui.service.InstitutionService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private ModelMapper modelMapper;

// LINK "GET" REQUESTS: ------------------------------------------------------------------------------------------------


    @GetMapping("/add-form")
    public ModelAndView showAddForm(Doctor doctor) {
        String[] doctorSpecialties = doctorService.getSpecialties();
        String[] doctorTitles = doctorService.getTitles();
        return new ModelAndView("doctor/add-form")
                .addObject("doctorSpecialties", doctorSpecialties)
                .addObject("doctorTitles", doctorTitles);
    }

    @GetMapping("/get-form")
    public String showGetForm(Doctor doctor) {
        return "doctor/get-form";
    }

    @GetMapping("/update-search-form")
    public String showUpdateSearchForm(Doctor doctor) {
        return "doctor/update-search-form";
    }

    @GetMapping("/delete-form")
    public String showDeleteForm(Doctor doctor) {
        return "doctor/delete-form";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------

    // TODO: Incorporate exception handling. Leaving form fields empty is an issue.

    @PostMapping
    public ModelAndView add(@Valid Doctor doctor) {
        Doctor databaseDoctor = doctorService.add(doctor);
        return new ModelAndView("doctor/home-doctor").addObject(databaseDoctor);
    }

    @GetMapping("/by-cnp")
    public ModelAndView getByCnp(Doctor doctor) {
        Doctor databaseDoctor = doctorService.findByCnp(doctor.getCnp());
        return new ModelAndView("doctor/home-doctor").addObject(databaseDoctor);
    }

    @GetMapping("/update-form-by-cnp")
    public ModelAndView showUpdateFormByCnp(Doctor doctor) {
        String[] doctorSpecialties = doctorService.getSpecialties();
        String[] doctorTitles = doctorService.getTitles();
        Doctor databaseDoctor = doctorService.findByCnp(doctor.getCnp());
        ArrayList<Institution> institutionArrayList = institutionService.getInstitutions();
        return new ModelAndView("doctor/update-form").addObject(databaseDoctor)
                .addObject("doctorSpecialties", doctorSpecialties)
                .addObject("doctorTitles", doctorTitles)
                .addObject("institutions", institutionArrayList);
    }

    @PostMapping("/updated-doctor")
    public ModelAndView update(@Valid Doctor doctor) {
        System.out.println(doctor.getInstitutionCUIs());
        Doctor databaseDoctor = doctorService.update(doctor);
        System.out.println(databaseDoctor.getInstitutionCUIs());
        databaseDoctor.setInstitutionCUIs(databaseDoctor.getInstitutions().stream()
                .map(Institution::getName).collect(Collectors.joining(",")));
        return new ModelAndView("doctor/home-doctor").addObject(databaseDoctor);
    }

    @PostMapping("/delete-by-cnp")
    public ModelAndView deleteByCnp(Doctor doctor) {
        Doctor databaseDoctor = doctorService.findByCnp(doctor.getCnp());
        doctorService.deleteByCnp(databaseDoctor.getCnp());

        return new ModelAndView("doctor/home-doctor").addObject(databaseDoctor);
    }

}