/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import business.Doctor;
import business.ExLog;
import business.Patient;
import jakarta.json.JsonObject;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author varni
 */
public class service {
    
    Doctor logged;
    
    public Doctor login(String id,String pass){
        ArrayList<Doctor> doctors = Doctor.read();
        if(id.equals("AAAAA") && pass.equals("12345")){
            this.logged = new Doctor();
            return new Doctor();
        }
        else{
            for(int i=0;i<doctors.size();i++){
                if(id.equals(doctors.get(i).getID()) && pass.equals(doctors.get(i).getPassword())){
                    this.logged = doctors.get(i);
                    return doctors.get(i);
                }
            }
        }
        return null;
    }
    
    public JSONArray getPatients(JsonObject o){
        String id = o.getString("id");
        ArrayList<Doctor> doctors = Doctor.read();
        for(Doctor doctor : doctors){
            if(doctor.getID().equals(id)){
                this.logged = doctor;
            }
        }
        ArrayList<Patient> patients = Patient.read();
        ArrayList<Patient> result= new ArrayList<>();
        for(Patient patient : patients){
            
            for(int i=0;i<logged.getPatients().size();i++){
                if(patient.getID().equals(this.logged.getPatients().get(i).getID())){
                    result.add(patient);
                }
            }
        }
        JSONArray value = new JSONArray();
        for(int i=0;i<patients.size();i++){
            JSONObject json = new JSONObject()
                    .put("check-in", patients.get(i).getCheckInDate().toString())
                    .put("birthYear", patients.get(i).getBirthYear().toString())
                    .put("name", patients.get(i).getName())
                    .put("id", patients.get(i).getID())
                    .put("bloodType",patients.get(i).getBloodType().getShortName())
                    .put("diseases", patients.get(i).getDiseasesAsString());
            value.put(json);
        }
        return value;
    }
    
    public Boolean newPatient(JsonObject o){
        String id = o.getString("id");
        String name = o.getString("name");
        String birthYear = o.getString("birthYear");
        String bloodType = o.getString("bloodType");
        String diseases = o.getString("diseases");
        ArrayList<String> newPatient = new ArrayList<>();
        Date now = Date.valueOf(LocalDate.now());
        newPatient.add(name);newPatient.add(birthYear);newPatient.add(bloodType);newPatient.add(now.toString());newPatient.add(diseases);
        ArrayList<Patient> patients = Patient.read();
        Patient patient = new Patient(newPatient, patients);
        ArrayList<Doctor> doctors = Doctor.read();
        Integer size = patients.size();
        for(Doctor doctor : doctors){
            if(doctor.getID().equals(id)){
                doctor.addNewPatient(patient);
                patients.add(patient);
            }
        }
        if(patients.size()>size){
            Patient.save(patients);
            Doctor.save(doctors);
            return true;
        }
        return false;
    }
    
    public Boolean removePatient(JsonObject o){
        
        String doctorID = o.getString("id");
        String patientID = o.getString("patientID");
        ArrayList<Patient> patients = Patient.read();
        Integer size = patients.size();
        for(Patient patient : patients){
            if(patient.getID().equals(patientID)){
                patients.remove(patient);
                Patient.save(patients);
                ArrayList<Doctor> doctors = Doctor.read();
                for(Doctor doctor : doctors){
                    if(doctor.getID().equals(doctorID)){
                        doctor.removePatient(patient);
                    }
                }
            }
        }
        if(size>patients.size()){
            return true;
        }
        
        return false;
    }
    
    public Boolean modifyPatient(JsonObject o){
        String id = o.getString("patientID");
        String name = o.getString("name");
        String birthYear = o.getString("birthYear");
        String bloodType = o.getString("bloodType");
        String checkIn = o.getString("checkIn");
        String diseases = o.getString("diseases");
        
        ArrayList<Patient> patients = Patient.read();
        for(Patient patient: patients){
            if(patient.getID().equals(id)){
                patients.remove(patient);
                Patient newPatient = new Patient(id, name, birthYear, bloodType,checkIn, diseases);
                patients.add(newPatient);
                Patient.save(patients);
                return true;
            }
        }
        return false;
    }
    
    public Boolean newDoctor(ArrayList<String> newDoctor){
        Boolean result = false;
        ArrayList<Doctor> doctors = Doctor.read();
        Integer size = doctors.size();
        doctors.add(new Doctor(newDoctor, doctors));
        if(doctors.size()>size){
            Doctor.save(doctors);
            result = true;
        }
        return result;
    }
    
    public Boolean deleteDoctor(String id){
        Boolean result = false;
        ArrayList<Doctor> doctors = Doctor.read();
        Integer size = doctors.size();
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getID().equals(id)) {
                doctors.remove(i);
            }
        }
        if(doctors.size()<size){
            Doctor.save(doctors);
            result = true;
        }
        return result;
    }
    
    public Boolean modifyDoctor(String id,String name,String pass,String access){
        ArrayList<Doctor> doctors = Doctor.read();
        Doctor logged = null;
        for(Doctor doctor : doctors){
            if(doctor.getID().equals(id)){
                logged = doctor;
            }
        }
        if(logged!=null){
        String patients = logged.getPatientsAsString();
        Doctor modifyDoctor = new Doctor(id,name,pass,patients,access);
        deleteDoctor(id);
        doctors = Doctor.read();
        doctors.add(modifyDoctor);
        Doctor.save(doctors);
        
        return true;
        }
        return false;
    }
}
