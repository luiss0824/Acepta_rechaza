/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aceptacion_rechazo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author ISCesarMartinez
 * Document   : DOMManage.java
 * Created on : 13-07-2012 04:16
 * Author     : ISC Cesar Martinez poseidon24@hotmail.com
 * 
 * Clase para el manejo de objetos DOM del paquete org.w3c.dom
 * Usado comunmente para manipulación de objetos XML y HTML
 */
public class DOMManage {
    
    public static Document createSOAPEnvelope(Document contenido) throws SAXException, IOException{
        String _soapEnvelope = "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">";
        String ensobretado = _soapEnvelope + documentToString(contenido,true) + "</s:Body></s:Envelope>";
        Document docEnsobretado = loadXMLFrom(ensobretado);
        
        return docEnsobretado;
    }

    public static org.w3c.dom.Document loadXMLFrom(String xml)
            throws org.xml.sax.SAXException, java.io.IOException {
        //return loadXMLFrom(new java.io.ByteArrayInputStream(xml.getBytes()));
        return loadXMLFrom(new java.io.ByteArrayInputStream(xml.getBytes("UTF-8")));
    }

    public static org.w3c.dom.Document loadXMLFrom(java.io.InputStream is)
            throws org.xml.sax.SAXException, java.io.IOException {
        javax.xml.parsers.DocumentBuilderFactory factory =
                javax.xml.parsers.DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        javax.xml.parsers.DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (javax.xml.parsers.ParserConfigurationException ex) {
        }
        org.w3c.dom.Document doc = builder.parse(is);
        is.close();
        return doc;
    }
    
    public static String documentToString(Document node, boolean omitXmlDeclaration) {
         StringWriter sw = new StringWriter();
         try {
           Transformer t = TransformerFactory.newInstance().newTransformer();
           if (omitXmlDeclaration)
                t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
           t.transform(new DOMSource(node), new StreamResult(sw));
         } catch (TransformerException te) {
           System.out.println("documentToString Transformer Exception");
           te.printStackTrace();
         }
         return sw.toString();
    }
    
    public static String nodeToString(Node node) {
         StringWriter sw = new StringWriter();
         try {
           Transformer t = TransformerFactory.newInstance().newTransformer();
           t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
           t.transform(new DOMSource(node), new StreamResult(sw));
         } catch (TransformerException te) {
           System.out.println("nodeToString Transformer Exception");
           te.printStackTrace();
         }
         return sw.toString();
    }
    
    public static ByteArrayInputStream getInputStreamFromDocument(Document doc) throws Exception{
        DOMSource source = new DOMSource(doc);  
        StringWriter xmlAsWriter = new StringWriter();  

        StreamResult result = new StreamResult(xmlAsWriter);  

        TransformerFactory.newInstance().newTransformer().transform(source, result);  

        // write changes  
        ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlAsWriter.toString().getBytes("UTF-8"));  
        return inputStream;
    }
    
    public static void printDocument(Document doc) throws TransformerConfigurationException, TransformerException{
        
        System.out.println("\n-----------IMPRESION DE DOCUMENT-----------------");
        OutputStream os;
        os = System.out;

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer trans = tf.newTransformer();
        //trans.setOutputProperty(OutputKeys.INDENT, "yes");
        trans.transform(new DOMSource(doc), new StreamResult(os));
        
        System.out.println("\n------------FIN DE IMPRESION----------------\n");
        
    }
    
    /** 
     * Serializa un objeto Document en un archivo 
     */  
    public static void outputDocToFile(Document doc, File file) throws Exception {  
        FileOutputStream    f              = new FileOutputStream(file);  
        TransformerFactory factory         = TransformerFactory.newInstance();  
        Transformer        transformer     = factory.newTransformer();  
          
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");  
          
        transformer.transform(new DOMSource(doc), new StreamResult(f));  
  
        f.close();  
    }  
      
    /** 
     * Lee un Document desde un archivo 
     */  
    public static Document loadDocumentFromFile(java.io.File file) throws Exception {  
        DocumentBuilderFactory  factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder         builder = null;  
          
        factory.setNamespaceAware(true);  
          
        builder = factory.newDocumentBuilder();  
          
        return builder.parse(file);  
    }   
      
    /** 
     * @return Crea un elemento <tag>value</tag> 
     */  
    public static Element createNode(Document document, String tag, String value){  
        Element node = document.createElement(tag);  
        if (value != null){  
            node.appendChild(document.createTextNode(value));  
        }  
        return node;  
    }  
    
}
