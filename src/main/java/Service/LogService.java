/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;


import business.ExLog;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author varni
 */
public class LogService {
    
    public JSONArray getAllException(){
        JSONArray exceptions = new JSONArray();
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            File f = new File("log.xml");
            Document xml = builder.parse(f);
            xml.normalize();
            NodeList allEx = xml.getElementsByTagName("exception");
            for(int i=0;i<allEx.getLength();i++){
                Node node = allEx.item(i);
                Element ex = (Element)node;
                JSONObject exception = new JSONObject();
                String message = ex.getElementsByTagName("message").item(0).getTextContent();
                String _class = ex.getElementsByTagName("class").item(0).getTextContent();
                String stack = ex.getElementsByTagName("stack").item(0).getTextContent();
                String datetime = ex.getElementsByTagName("datetime").item(0).getTextContent();
                exception.put("message", message);
                exception.put("class", _class);
                exception.put("stack", stack);
                exception.put("datetime",datetime);
                exceptions.put(exception);
            }
        }
        catch(Exception ex){
            ExLog.handleException(ex);
        }
        return exceptions;
    } 
    
}
