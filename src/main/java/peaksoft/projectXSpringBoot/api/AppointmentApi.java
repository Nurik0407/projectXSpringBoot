package peaksoft.projectXSpringBoot.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.projectXSpringBoot.entity.Appointment;
import peaksoft.projectXSpringBoot.service.*;


/**
 * Zholdoshov Nuradil
 * peaksoft.api
 * 18.02.2023
 **/

@Controller
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentApi {
    private final AppointmentService appointmentService;
    private final DepartmentService departmentService;
    private final DoctorService doctorService;

    private final PatientService patientService;
    private final HospitalService hospitalService;


    @GetMapping("/{hospitalId}")
    public String getAll(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("appointments", appointmentService.getAll(hospitalId));
        model.addAttribute("hospitalName", hospitalService.findById(hospitalId).getName());
        return "appointment/mainPage";
    }

    @GetMapping("/{hospitalId}/new")
    public String newAppointment(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("patients", patientService.getAll(hospitalId));
        model.addAttribute("doctors", doctorService.getAllByHospitalId(hospitalId));
        model.addAttribute("departments", departmentService.getAllByHospitalId(hospitalId));
        model.addAttribute("hospId", hospitalId);
        return "appointment/new";
    }

    @PostMapping("/{hospitalId}/save")
    public String save(@PathVariable Long hospitalId, @ModelAttribute("appointment") Appointment appointment) {
        appointmentService.save(appointment, hospitalId);
        return "redirect:/appointment/" + hospitalId;
    }

    @GetMapping("/{hospitalId}/{id}/edit")
    public String edit(@PathVariable Long hospitalId, @PathVariable Long id, Model model) {
        model.addAttribute("appointment", appointmentService.findById(id).get());
        model.addAttribute("patients", patientService.getAll(hospitalId));
        model.addAttribute("doctors", doctorService.getAllByHospitalId(hospitalId));
        model.addAttribute("departments", departmentService.getAllByHospitalId(hospitalId));
        model.addAttribute("hospId", hospitalId);
        return "appointment/edit";
    }

    @PostMapping("/{hospitalId}/{id}/update")
    public String update(@PathVariable Long hospitalId, @PathVariable Long id
            , @ModelAttribute Appointment appointment) {
        appointmentService.update(id, appointment);
        return "redirect:/appointment/" + hospitalId;
    }

    @GetMapping("/{hospitalId}/{id}/delete")
    public String delete(@PathVariable Long hospitalId, @PathVariable Long id) {
        appointmentService.delete(id,hospitalId);
        return "redirect:/appointment/" + hospitalId;
    }
}
