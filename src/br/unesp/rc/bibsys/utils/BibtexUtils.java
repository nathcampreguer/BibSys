/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unesp.rc.bibsys.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.BibTeXFormatter;
import org.jbibtex.BibTeXParser;
import org.jbibtex.BibTeXString;
import org.jbibtex.Key;
import org.jbibtex.ParseException;

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
                        System.err.println("String: \"" + key.getValue() + "\"");
                    }
                }

                @Override
                public void checkCrossReferenceResolution(Key key, BibTeXEntry entry){

                    if(entry == null){
                        System.err.println("Cross-referência: \"" + key.getValue() + "\"");
                    }
                }
          };

                return parser.parse(reader);
        } finally {
            reader.close();
        }
    }
    //Correct bibkey formatting and change output to BibTex format
    public static void bibkeyFormat(String path) throws IOException, ParseException {
        File file = new File(path);
        BibTeXDatabase database = BibtexUtils.parseBibTeX(file);
        Collection<BibTeXEntry> entries = (database.getEntries()).values();
        String xml = "";
        
        for(BibTeXEntry entry : entries){
            String year = entry.getField(new Key("year")).toUserString();
            String author = entry.getField(new Key("author")).toUserString();
            String bibkey;
            //separate string with all authors into an array
            if (author.contains("and")) {
                String[] authors = author.split("and");
                authors[0] = removeComma(authors[0]);
                authors[1] = removeComma(authors[1]);
                if (authors.length == 2) {
                    bibkey = authors[0]+"."+authors[1]+":"+year;
                } else {
                     bibkey = authors[0]+".et.al:"+year;
                }
            } else {
                bibkey = removeComma(author)+":"+year;
            }
           
            bibkey = bibkey.replaceAll(" ", "");
            //Correct bibkey formatting
            entry.setKey(new Key(bibkey));
            formatBibtex(database, file);
        }
    }
    
    private static void formatBibtex(BibTeXDatabase database, File file) throws IOException {
        Writer writer = (file != null ? new FileWriter(file) : new OutputStreamWriter(System.out));

        try {
            BibTeXFormatter formatter = new BibTeXFormatter();

            formatter.format(database, writer);
        } finally {
            writer.close();
        }
    }
       
    private static String removeComma(String author) {
        if (author.contains(","))
            return author.substring(0,author.indexOf(","));
        return author;
    }
}
