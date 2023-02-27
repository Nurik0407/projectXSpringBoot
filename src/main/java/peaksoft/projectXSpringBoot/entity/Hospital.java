package peaksoft.projectXSpringBoot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

/**
 * Zholdoshov Nuradil
 * peaksoft.models
 * 17.02.2023
 **/
@Entity
@Table(name = "hospitals")
@Getter
@Setter
@NoArgsConstructor
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hospital_id_gen")
    @SequenceGenerator(name = "hospital_id_gen", allocationSize = 1)
    private Long id;
    @NotEmpty(message = "Name must not be empty!")
    @Size(max = 50,message = "Name cannot be longer than 50 characters!")
    @Column(unique = true)
    private String name;
    @NotEmpty(message = "Address must not be empty!")
    @Column(nullable = false)
    private String address;
    @NotEmpty(message = "Insert photo!")
    private String image;
    @OneToMany(cascade = ALL, mappedBy = "hospital", fetch = FetchType.EAGER)
    private List<Doctor> doctorList;
    @OneToMany(mappedBy = "hospital", cascade = ALL, fetch = FetchType.EAGER)
    private List<Patient> patientList;
    @OneToMany(cascade = ALL, mappedBy = "hospital")
    private List<Department> departmentList;
    @OneToMany(cascade = ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "hospitals_appointments",
    joinColumns = @JoinColumn(name = "hospital_id")
    ,inverseJoinColumns = @JoinColumn(name = "appointmentList_id"))
    private List<Appointment> appointmentList;

    public void addAppointment(Appointment appointment){
        if (appointmentList == null){
            appointmentList = new ArrayList<>();
        }
        appointmentList.add(appointment);
    }
    public Hospital(String name, String address, String image) {
        this.name = name;
        this.address = address;
        this.image = image;
    }
}
