package peaksoft.projectXSpringBoot.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.projectXSpringBoot.entity.Department;
import peaksoft.projectXSpringBoot.service.DepartmentService;
import peaksoft.projectXSpringBoot.service.DoctorService;
import peaksoft.projectXSpringBoot.service.HospitalService;

/**
 * Zholdoshov Nuradil
 * peaksoft.api
 * 17.02.2023
 **/
@Controller
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentApi {

    private final DepartmentService departmentService;
    private final HospitalService hospitalService;
    private final DoctorService doctorService;



    @GetMapping("/{id}")
    public String getAll(Model model, @PathVariable("id") Long id) {
        model.addAttribute("departments", departmentService.getAllByHospitalId(id));
        model.addAttribute("hospitalId", id);
        model.addAttribute("hospital", hospitalService.findById(id));
        return "department/mainPage";
    }

    @GetMapping("/{hospitalId}/new")
    public String newDepartment(Model model, @PathVariable("hospitalId") Long id) {
        model.addAttribute("department", new Department());
        model.addAttribute("hospitalId", id);
        return "department/new";
    }

    @PostMapping("/{hospitalId}/save")
    public String save(@PathVariable Long hospitalId, @ModelAttribute("department") @Valid Department department
            , BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "department/new";
        }
        try {
            departmentService.save(department, hospitalId);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Department already exist!");
            model.addAttribute(hospitalId);
            return "department/new";
        }
        return "redirect:/department/" + hospitalId;
    }

    @GetMapping("/{hospitalId}/{id}/edit")
    public String edit(@PathVariable Long hospitalId, @PathVariable Long id, Model model) {
        model.addAttribute("department", departmentService.findById(id));
        model.addAttribute(hospitalId);
        return "department/edit";
    }

    @PostMapping("/{hospitalId}/{id}/update")
    public String update(@PathVariable Long hospitalId, @PathVariable Long id, @ModelAttribute("newDepartment") @Valid Department department
            , BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("department", department);
            return "department/edit";
        }
        try {
            departmentService.update(id, department);
            return "redirect:/department/" + hospitalId;
        }catch (RuntimeException e){
            model.addAttribute("department", departmentService.findById(id));
            model.addAttribute(hospitalId);
            model.addAttribute("errorMessage","Department already exist!");
            return "department/edit";
        }
    }

    @GetMapping("/{hospitalId}/{id}/delete")
    public String delete(@PathVariable Long hospitalId, @PathVariable Long id) {
        departmentService.delete(id);
        return "redirect:/department/" + hospitalId;
    }

    @GetMapping("/{hospitalId}/{departmentId}/doctors")
    public String getAllDoctorsByDepartmentId(@PathVariable Long departmentId, @PathVariable Long hospitalId, Model model) {
        model.addAttribute("department", departmentService.findById(departmentId).getName());
        model.addAttribute("doctors",doctorService.getAllByDepartmentId(departmentId));
        model.addAttribute(hospitalId);
        return "department/doctors";
    }

}

