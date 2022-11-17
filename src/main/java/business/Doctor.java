/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import fio.Fio;

@ReadMethodName(name="read")
@SaveMethodName(name="save")
@FileName(fileName = "doctors.xml")
public class Doctor extends Person {
    @GetterFunctionName(name="getPassword")
    private String password;
    @GetterFunctionName(name="getPatientsAsString")
    private ArrayList<Patient> patients;
    @GetterFunctionName(name="getAccessAsString")
    private Boolean access;

    public Doctor() {
        this.ID="AAAAA";
        this.password = "12345";
        this.name = "admin";
        this.patients = new ArrayList<>();
        this.access = true;
    }

    public Doctor(ArrayList<String> list, ArrayList<Doctor> doctors){
        this.ID = newDoctorId(doctors);
        this.name = list.get(0);
        this.password = list.get(1);
        this.patients = new ArrayList<>();
        this.access = Boolean.valueOf(list.get(2));
    }

    public Doctor(String name, String password, ArrayList<String> patients,String access, ArrayList<Doctor> doctors){
        this.ID = newDoctorId(doctors);
        this.name = name;
        this.password = password;
        ArrayList<Patient> patientslist = Patient.read();
        ArrayList<Patient> patientList = new ArrayList<>();
        for(int i=0;i<patients.size();i++){
            for(int j = 0;j<patientslist.size();j++){
                if(patients.get(i).equals(patientslist.get(j).getID())){
                    patientList.add(patientslist.get(j));
                }
            }
        }
        this.patients = patientList;
        this.access = Boolean.valueOf(access);
    }

    public Doctor(String ID, String name,String password, String patients, String access){
        this.ID = ID;
        this.name = name;
        this.password = password;
        ArrayList<String> patient = getListFromString(patients);
        ArrayList<Patient> patientslist = Patient.read();
        ArrayList<Patient> patientList = new ArrayList<>();
        for(int i=0;i<patient.size();i++){
            for(int j = 0;j<patientslist.size();j++){
                if(patient.get(i).equals(patientslist.get(j).getID())){
                    patientList.add(patientslist.get(j));
                }
            }
        }
        this.patients = patientList;
        this.access = Boolean.valueOf(access);
    }

    public static void save(ArrayList<Doctor> list){
        Fio<Doctor> doctorFio = new Fio<>(new Doctor());
        doctorFio.save(list);
    }

    public static ArrayList<Doctor> read(){
        ArrayList<Doctor> result = new ArrayList<>();
        Fio<Doctor> doctorFio = new Fio<>(new Doctor());
        ArrayList<ArrayList> list = doctorFio.read();
        for(int i=0;i<list.size();i++){
            result.add(new Doctor(list.get(i).get(0).toString(),list.get(i).get(1).toString(),list.get(i).get(2).toString(),list.get(i).get(3).toString(),list.get(i).get(4).toString()));
        }
        return result;
    }
    
    public String getPassword(){
        return this.password;
    }

    public ArrayList<Patient> getPatients(){
        return this.patients;
    }

    public String getPatientsAsString(){
        String result = "";
        for(int i=0;i<patients.size();i++){
            if(i==patients.size()-1){
                result = result + patients.get(i).getID();
            }
            else{
                result = result + patients.get(i).getID() + ';';
            }
        }
        return result;
    }

    public String getAccessAsString() {
        return access.toString();
    }

    public Boolean getAccess(){
        return this.access;
    }

    private ArrayList<String> getListFromString(String string){
        ArrayList result = new ArrayList<>();
        ArrayList<Integer> tokens = new ArrayList<>();
        for(int i=0;i<string.length();i++){
            if(string.charAt(i)==';'){
                tokens.add(i);
            }
        }
        tokens.add(string.length());
        for(int i = 0;i<tokens.size();i++){
            if(i==0){
                result.add(string.substring(0, tokens.get(i)));
            }
            else{
                result.add(string.substring((tokens.get(i-1)+1),(tokens.get(i))));
            }
        }
        return result;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setAccess(){
        this.access=!this.access;
    }

    private static String newDoctorId(ArrayList<Doctor> list){
        String ID = "";
        Boolean B = true;
        while (B) {
            for (int i = 0; i < 5; i++) {
                Random rnd = new Random();
                Character IDElement;
                    IDElement =(char) (rnd.nextInt(26)+'A');
                ID = ID + IDElement;
            }
            int i;
            for(i=0;i<list.size();i++){
                if(ID.equals(list.get(i).getID())){
                    break;
                }
            }
            if(i==list.size()){
                B=false;
            }
        }
        return ID;
    }

    public static Doctor newObject(ArrayList<String> list,ArrayList<Doctor> doctors){
        return new Doctor(list,doctors);
    }

    public void addNewPatient(Patient patient){
        this.patients.add(patient);
    }
    
    public void modifyAccess(Boolean access){
        this.access = access;
    }

    public void removePatient(Patient patient){
        this.patients.remove(patient);
    }

    @Override
    public String toString(){
        return "ID: " + this.ID + "\t" + "Név: " + this.name + " Jelszó: " + this.password + " Hozzáférés: " + this.access;
    }
}
