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
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
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
    
}
