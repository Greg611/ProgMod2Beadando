package agreg.progmod2beadando.resources;

import Service.LogService;
import Service.service;
import business.Doctor;
import business.ExLog;
import business.Patient;
import fio.Fio;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author 
 */
@Path("jakartaee9")
public class JakartaEE9Resource{
    
    service s = new service();
    
    @POST
    @Path("login")
    public Response login(JsonObject o){
        String id = o.getString("id");
        String pass = o.getString("pass");
        Doctor result = s.login(id, pass);
        
        if(result != null){
            return Response.ok("Sikeres bejelentkezés " + result.getName()).build();
        }
        return Response.ok("Sikertelen bejelentkezés").build();
    }
    
    @GET
    @Path("getDoctors")
    public Response getDoctors(){
        ArrayList<Doctor> doctors = Doctor.read();
        String result = "";
        for(int i=0;i<doctors.size();i++){
            result = result + doctors.get(i).toString() + "\n";
        }
        return Response
                .ok(result)
                .build();
    }
    
    @POST
    @Path("getPatients")
    public Response getPatients(JsonObject o){
        JSONArray value = s.getPatients(o);
        return Response.ok(value.toString()).build();
    }
    
    @POST
    @Path("newPatient")
    public Response newPatient(JsonObject o){
        Boolean b = s.newPatient(o);
        if(b){
        return Response.ok("Páciens sikeres felvéve.").build();
        }
        return Response.ok("Páciens felvétele sikertelen.").build();
    }
    
    @POST
    @Path("removePatient")
    public Response removePatient(JsonObject o){
        Boolean b = s.removePatient(o);
        if(b){
        return Response.ok("Páciens sikeres törölve.").build();
        }
        return Response.ok("Páciens törlése sikertelen.").build();
    }
    
    @POST
    @Path("modifyPatient")
    public Response modifyPatient(JsonObject o){
        Boolean b = s.modifyPatient(o);
        if(b){
            return Response.ok("Páciens adatai sikeresen módosítva.").build();
        }
        return Response.ok("A páciens adatait nem sikerült módosítani.").build();
    }
    
    @POST
    @Path("newDoctor")
    public Response newDoctor(JsonObject o){
        String name = o.getString("name");
        String pass = o.getString("pass");
        Boolean access = o.getBoolean("access");
        ArrayList<String> newDoctor = new ArrayList<>();
        newDoctor.add(name);newDoctor.add(pass);newDoctor.add(access.toString());
        Boolean b = s.newDoctor(newDoctor);
        if(b){
        return Response.ok("Orvos sikeresen felvéve.").build();
        }
        return Response.ok("Nem sikerült az orvos felvétele.").build();
    }
    
    @POST
    @Path("deleteDoctor")
    public Response deleteDoctor(JsonObject o){
        String id = o.getString("id");
        Boolean b = s.deleteDoctor(id);
        if(b){
            return Response.ok("Orvos sikeresen törölve").build();
        }
        return Response.ok("Nincs ilyen azanosítójú orvos.").build();
    }
    
    @POST
    @Path("modifyDoctor")
    public Response modifyDoctor(JsonObject o){
        String id = o.getString("id");
        String name = o.getString("name");
        String pass = o.getString("pass");
        String access = String.valueOf(o.getBoolean("access"));
        Boolean b = s.modifyDoctor(id,name,pass,access);
        if(b){
            return Response.ok("Orvos Adatai sikeresen módosítva.").build();
        }
        return Response.ok("Az orvos adatait nem sikerült módosítani.").build();
    }
}
