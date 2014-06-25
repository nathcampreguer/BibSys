/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unesp.rc.bibsys.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;
import org.jbibtex.ParseException;
import org.jbibtex.Value;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author nathalia
 */
public class FileManager {
    
    /**
    * Helper variable for BibtexFilter
    */
    public final static String bib = "bib";
    
    /**
     * Get the extension of a file.
     * @param f
     * @return file extension without the dot
     */  
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
    
    /**
     * Read the full text of a file.
     * @param path The full path for the file location 
     * @return The full text from f converted into String
     */
    public static StringBuffer readFile(String path) {
        StringBuffer text = new StringBuffer();
        BufferedReader br = null;
        try {
                String sCurrentLine;
                br = new BufferedReader(new FileReader(path));
                while ((sCurrentLine = br.readLine()) != null) {
                        text.append(sCurrentLine);
                        text.append("\n");
                }
        } catch (IOException ex) {
                System.out.println("Mensagem: \n" + ex.getMessage());
        } finally {
                try {
                        if (br != null) {
                                br.close();
                        }
                } catch (IOException ex) {
                        System.out.println("Mensagem: \n" + ex.getMessage());
                }
        }
        return text;
	}
    
    public static String BibtoXML(File file) throws IOException, ParseException {
        String xml = "";
        
        BibTeXDatabase database = BibtexUtils.parseBibTeX(file);
        Collection<BibTeXEntry> entries = (database.getEntries()).values();
        Collection<Key> keys = new ArrayList<>();
        keys.add(new Key("address"));
	keys.add(new Key("annote"));
        keys.add(new Key("author"));
        keys.add(new Key("booktitle"));
        keys.add(new Key("chapter"));
        keys.add(new Key("crossref"));
        keys.add(new Key("edition"));
        keys.add(new Key("editor"));
        keys.add(new Key("eprint"));
        keys.add(new Key("howpublished"));
        keys.add(new Key("institution"));
        keys.add(new Key("journal"));
        keys.add(new Key("key"));
        keys.add(new Key("month"));
        keys.add(new Key("note"));
        keys.add(new Key("number"));
        keys.add(new Key("organization"));
        keys.add(new Key("pages"));
        keys.add(new Key("publisher"));
        keys.add(new Key("school"));
        keys.add(new Key("series"));
        keys.add(new Key("title"));
        keys.add(new Key("type"));
        keys.add(new Key("url"));
        keys.add(new Key("volume"));
        keys.add(new Key("year"));
        
        xml = "<root>";
        for(BibTeXEntry entry : entries){
            String type = entry.getType().toString();
            String bibkey = entry.getKey().toString();

            xml += "  <"+type+" id=\""+bibkey+"\">\n";
            for(Key key : keys) {
                Value value = entry.getField(key);
                // The field is not defined
                if(value == null){
                        continue;
                }
                xml += "    <"+key.toString()+">";
                xml += value.toUserString();
                xml += "</"+key.toString()+">\n";
                
            }
            xml += "  </"+type+">\n";
        }
        xml += "</root>";
        
        return xml;
    }
    
    public static Document stringToDom(String xmlSource) 
            throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlSource)));
    }
    
    public static File createFile(String path, String content) throws FileNotFoundException, IOException {
        FileOutputStream fop;
        File file;
        file = new File(path);
        fop = new FileOutputStream(file);

        // if file doesnt exists, then create it
        if (!file.exists()) {
                file.createNewFile();
        }

        // get the content in bytes
        byte[] contentInBytes = content.getBytes();

        fop.write(contentInBytes);
        fop.flush();
        fop.close();
        return file;
    }
    
}
