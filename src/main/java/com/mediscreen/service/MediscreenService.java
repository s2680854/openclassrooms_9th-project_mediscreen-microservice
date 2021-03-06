package com.mediscreen.service;

import com.mediscreen.dto.ReportEntriesDto;
import com.mediscreen.model.Note;
import com.mediscreen.model.Patient;
import com.mediscreen.proxy.NoteServiceProxy;
import com.mediscreen.proxy.PatientServiceProxy;
import com.mediscreen.proxy.ReportServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

@Service
public class MediscreenService {

    @Autowired
    private PatientServiceProxy patientServiceProxy;
    @Autowired
    private NoteServiceProxy noteServiceProxy;
    @Autowired
    private ReportServiceProxy reportServiceProxy;

    public void createPatient(Patient patient) {

        patientServiceProxy.createPatient(patient);
    }

    public void createNote(Note note) {

        noteServiceProxy.createNote(note);
    }

    public Patient readPatient(Long patientId) {

        return patientServiceProxy.readPatient(patientId);
    }

    public Collection<Patient> readPatientList() {

        return patientServiceProxy.readPatientList();
    }

    public Collection<Note> readNoteList(Long patientId) {

        return noteServiceProxy.readNoteList(patientId);
    }

    public void generateReport(Long patientId, Patient patient) {

        Collection<Note> noteList = readNoteList(patientId);
        ReportEntriesDto entries = new ReportEntriesDto();
        entries.setPatientId(patient.getPatientId());
        entries.setAge((int) ChronoUnit.YEARS.between(patient.getBirthDate(), LocalDate.now()));
        entries.setGender(patient.getGender());
        entries.setNoteList(noteList);
        String riskLevel = reportServiceProxy.generateReport(entries);
        patient.setRiskLevel(riskLevel);
        patientServiceProxy.updatePatient(patient);
    }

    public void updatePatient(Patient patient) {

        patientServiceProxy.updatePatient(patient);
    }

    public void deletePatient(Long patientId) {

        patientServiceProxy.deletePatient(patientId);
    }

    public void deleteNoteById(Long noteId) {

        noteServiceProxy.deleteNote(noteId);
    }

    public void deletePatientList() {

        patientServiceProxy.deletePatientList();
    }

    public void deleteNoteList() {

        noteServiceProxy.deleteNoteList();
    }
}
