package peaksoft.projectXSpringBoot.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.projectXSpringBoot.entity.Doctor;
import peaksoft.projectXSpringBoot.service.DepartmentService;
import peaksoft.projectXSpringBoot.service.DoctorService;
import peaksoft.projectXSpringBoot.service.HospitalService;

/**
 * Zholdoshov Nuradil
 * peaksoft.api
 * 18.02.2023
 **/
@Controller
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorApi {

    private final DoctorService doctorService;

    private final HospitalService hospitalService;

    private final DepartmentService departmentService;



    @GetMapping("/{hospitalId}")
    public String getAll(Model model, @PathVariable("hospitalId") Long hospitalId) {
        model.addAttribute("doctors", doctorService.getAllByHospitalId(hospitalId));
        model.addAttribute(hospitalId);
        model.addAttribute("hospital", hospitalService.findById(hospitalId));
        return "doctor/mainPage";
    }

    @GetMapping("/{hospitalId}/new")
    public String newDoctor(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("hospitalId", hospitalId);
        model.addAttribute("departments", departmentService.getAllByHospitalId(hospitalId));
        return "doctor/new";
    }

    @PostMapping("/{hospitalId}/save")
    public String save(@PathVariable Long hospitalId, @ModelAttribute("doctor") @Valid Doctor doctor,
                       BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.getAllByHospitalId(hospitalId));
            return "doctor/new";
        }
        try {
            doctorService.save(doctor, hospitalId);
            return "redirect:/doctor/" + hospitalId;
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("hospitalId", hospitalId);
            model.addAttribute("departments", departmentService.getAllByHospitalId(hospitalId));
            model.addAttribute("Email", "This email already exists in the database");
            return "doctor/new";
        }

    }

    @GetMapping("/{hospitalId}/{id}/delete")
    public String delete(@PathVariable("id") Long id, @PathVariable("hospitalId") Long hospitalId) {
        doctorService.delete(id);
        return "redirect:/doctor/" + hospitalId;
    }

    @GetMapping("/{hospitalId}/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model, @PathVariable("hospitalId") Long hospitalId) {
        model.addAttribute("doctor", doctorService.findById(id));
        model.addAttribute("departments", departmentService.getAllByHospitalId(hospitalId));
        return "doctor/edit";
    }

    @PostMapping("/{hospitalId}/{id}/update")
    public String update(@PathVariable("hospitalId") Long hospitalId, @PathVariable("id") Long id, @ModelAttribute("doctor") @Valid Doctor doctor,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctor", doctor);
            model.addAttribute("departments", departmentService.getAllByHospitalId(hospitalId));
            return "doctor/edit";
        }
        try {
            doctor.setHospitalId(hospitalId);
            doctorService.update(id, doctor);
            return "redirect:/doctor/" + hospitalId;
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("departments",departmentService.getAllByHospitalId(hospitalId));
            model.addAttribute("Email", "This email already exists in the database");
            return "doctor/edit";
        }

    }
}
