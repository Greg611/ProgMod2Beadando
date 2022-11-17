/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author varni
 */
public class ExLog<T> {
    public static void handleException(Exception ex){
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            File f = new File("log.xml");
            Document xml = builder.parse(f);
            xml.normalize();
            Element root = xml.getDocumentElement();
            Element exe = xml.createElement("exception");
            Element message = xml.createElement("message");
            message.setTextContent(ex.getMessage());
            Element exClass = xml.createElement("class");
            exClass.setTextContent(ex.getClass().toString());
            Element stackTrace = xml.createElement("stackTrace");
            stackTrace.setTextContent(Arrays.toString(ex.getStackTrace()));
            Element datetime = xml.createElement("datetime");
            datetime.setTextContent(new Date().toString());
            exe.appendChild(exClass);
            exe.appendChild(message);
            exe.appendChild(stackTrace);
            exe.appendChild(datetime);
            root.appendChild(exe);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(xml);
            StreamResult result = new StreamResult(f);
            transformer.transform(source, result);
        }
        catch(Exception e){
            System.err.println(e.toString());
        }
    }
}



