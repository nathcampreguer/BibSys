/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unesp.rc.bibsys.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.BibTeXParser;
import org.jbibtex.BibTeXString;
import org.jbibtex.Key;
import org.jbibtex.ParseException;
import org.jbibtex.Value;

/**
 *
 * @author nathalia
 */
public class BibtexUtils {
    
    public static BibTeXDatabase parseBibTeX(File file) throws IOException, ParseException {
        Reader reader = new FileReader(file);

        try {
                BibTeXParser parser = new BibTeXParser(){

                        @Override
                        public void checkStringResolution(Key key, BibTeXString string){

                                if(string == null){
                                        System.err.println("Unresolved string: \"" + key.getValue() + "\"");
                                }
                        }

                        @Override
                        public void checkCrossReferenceResolution(Key key, BibTeXEntry entry){

                                if(entry == null){
                                        System.err.println("Unresolved cross-reference: \"" + key.getValue() + "\"");
                                }
                        }
                };

                return parser.parse(reader);
        } finally {
                reader.close();
        }
    }
    //Correct bibkey formatting and change output to BibTex format
    public static String bibkeyFormat(String path) throws IOException, ParseException {
        File file = new File(path);
        BibTeXDatabase database = BibtexUtils.parseBibTeX(file);
        Collection<BibTeXEntry> entries = (database.getEntries()).values();
        String xml = "";
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
        
        for(BibTeXEntry entry : entries){
            Value year = entry.getField(new Key("year"));
            Value author = entry.getField(new Key("author"));
            String bibkey;
            
            bibkey = author.toUserString() + " : " + year.toUserString();
            //Correct bibkey formatting
            entry.setKey(new Key(bibkey));
            
            String type = entry.getType().toString();

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
    
}
