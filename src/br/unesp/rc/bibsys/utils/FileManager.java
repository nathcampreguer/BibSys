/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unesp.rc.bibsys.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
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
 * Helper methods for a general File
 * @author Nathalia
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
    * Parses a .bib file to XML
    * @param file
    * @return 
    * @throws java.io.IOException
    * @throws org.jbibtex.ParseException
    */
    public static String BibtoXML(File file) throws IOException, ParseException {
        String xml = "";
        
        BibTeXDatabase database = BibtexUtils.parseBibTeX(file);
        Collection<BibTeXEntry> entries = (database.getEntries()).values();
        Collection<Key> keys = createKeyList();
        
        xml = "<root>\n";
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
    
    /**
    * Parses a .bib file into a reference key
    * @param file
    * @return 
    * @throws java.io.IOException
    * @throws org.jbibtex.ParseException
    */
    public static String BibtoTree(File file) throws IOException, ParseException {
        String xml = "";
        
        BibTeXDatabase database = BibtexUtils.parseBibTeX(file);
        Collection<BibTeXEntry> entries = (database.getEntries()).values();
        Collection<Key> keys = createKeyList();
        
        for(BibTeXEntry entry : entries){
            String type = entry.getType().toString();
            String bibkey = entry.getKey().toString();

            xml += "+ "+type+" - bibkey=\""+bibkey+"\"\n";
            for(Key key : keys) {
                Value value = entry.getField(key);
                // The field is not defined
                if(value == null){
                        continue;
                }
                xml += "    - "+key.toString();
                xml += " : ["+value.toUserString()+"]\n";
                
            }
        }
        
        return xml;
    }
    
    /**
    * Writes a XML string into a new Document
    * @param xmlSource
    * @return 
    */
    public static Document stringToDom(String xmlSource) 
            throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlSource)));
    }
    
    /**
    * returns this filter description
    * to be displayed on the JFileChooser
    */
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
    
    /**
    * Compares two .bib files and output the difference in a 
    * .bib formatted string
    * @param path1
    * @param path2
    * @return 
    * @throws java.io.IOException
    * @throws org.jbibtex.ParseException
    */
    public static String fileDiff(String path1, String path2) throws IOException, ParseException {
        File file1 = new File(path1);
        File file2 = new File(path2);
        
        BibTeXDatabase database1 = BibtexUtils.parseBibTeX(file1);
        BibTeXDatabase database2 = BibtexUtils.parseBibTeX(file2);
        BibTeXDatabase databaseDiff = new BibTeXDatabase();

        Map<Key, BibTeXEntry> entries1 = database1.getEntries();
        Map<Key, BibTeXEntry> entries2 = database2.getEntries();
        Collection<BibTeXEntry> diff = new ArrayList();
        
        for (Entry<Key, BibTeXEntry> entry : entries1.entrySet())
            {
                if(!entries2.containsKey(entry.getKey()))
                databaseDiff.addObject(entry.getValue());
                System.out.println(entry.getKey().toString());
            }
        for (Entry<Key, BibTeXEntry> entry : entries2.entrySet())
            {
                if(!entries1.containsKey(entry.getKey()))
                databaseDiff.addObject(entry.getValue());
            }
        
            File file = new File("bibtexHelper.bib");
            BibtexUtils.formatBibtex(databaseDiff, file);
            return readFile(file);
    }
    
    /**
    * returns all the possible keys a Bibtex item may have
    */
    private static Collection<Key> createKeyList() {
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
        return keys;
    }
    
    /**
    * Writes the content of a file to a string
    * @param file
    * @return 
    * @throws java.io.FileNotFoundException
    */
    public static String readFile(File file) throws FileNotFoundException {
        StringBuilder fileContents = new StringBuilder((int)file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");

        try {
            while(scanner.hasNextLine()) {        
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }
    
}
