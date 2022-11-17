package fio;

import business.ExLog;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import business.FileName;
import business.GetterFunctionName;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Fio <T>{

    T object;

    public Fio(T object){
        this.object = object;
    }

    public void save(ArrayList<T> list){
        try{
            File f = new File(object.getClass().getAnnotation(FileName.class).fileName());
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document xml = db.newDocument();
            Element root = xml.createElement(object.getClass().getSimpleName() + "s");
            xml.appendChild(root);
            Field[] superFields = object.getClass().getSuperclass().getDeclaredFields();
            Field[] Fields = object.getClass().getDeclaredFields();
            for(T t : list) {
                Element nameElement = xml.createElement(object.getClass().getSimpleName());
                root.appendChild(nameElement);
                for (Field superField : superFields) {
                    if (superField.getAnnotation(GetterFunctionName.class).name() != null) {
                        String gfn = superField.getAnnotation(GetterFunctionName.class).name();
                        Method gm = object.getClass().getSuperclass().getMethod(gfn);
                        String value = gm.invoke(t).toString();
                        String name = superField.getName();
                        Element element = xml.createElement(name);
                        element.setTextContent(value);
                        nameElement.appendChild(element);
                    }
                }
                for (Field Field : Fields) {
                    if (Field.getAnnotation(GetterFunctionName.class).name() != null) {
                        String gfn = Field.getAnnotation(GetterFunctionName.class).name();
                        Method gm = object.getClass().getMethod(gfn);
                        String value = gm.invoke(t).toString();
                        String name = Field.getName();
                        Element element = xml.createElement(name);
                        element.setTextContent(value);
                        nameElement.appendChild(element);
                    }
                }
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            DOMSource s = new DOMSource(xml);
            StreamResult r = new StreamResult(f);
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(s, r);
        }
        catch(Exception ex){
            ExLog.handleException(ex);
        }
    }

    public ArrayList<ArrayList> read(){
        ArrayList<ArrayList> result = new ArrayList<>();
        Path path = Paths.get(object.getClass().getAnnotation(FileName.class).fileName());
        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            File f = new File(object.getClass().getAnnotation(FileName.class).fileName());
            Document document = documentBuilder.parse(f);
            Element rootElement = document.getDocumentElement();
            NodeList childNodesList = rootElement.getChildNodes();
            Node node;
            
            for(int i=0;i<childNodesList.getLength();i++) {
                node = childNodesList.item(i);
                ArrayList<String> nodes = new ArrayList<>();
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    NodeList childNodesOfTag = node.getChildNodes();
                    for (int j = 0; j < childNodesOfTag.getLength(); j++) {
                        if (childNodesOfTag.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            nodes.add(childNodesOfTag.item(j).getTextContent());
                        }

                    }
                    result.add(nodes);
                }

            }
        }
        catch(Exception ex){
            System.err.println(ex);
        }
        return result;
    }


}
