/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unesp.rc.bibsys.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
}
