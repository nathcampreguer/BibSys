/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unesp.rc.bibsys.utils;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Filter model for a JFileChooser that will accept only .bib files
 * @author Nathalia
 */
public class BibtexFilter extends FileFilter{
    //Accept all directories and .bib files.
    
    /**
    * Sets the rules for this model.
    * Accepts only .bib files and allows folders.
    * @param f 
    */
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = FileManager.getExtension(f);
        if (extension != null) {
            return extension.equals(FileManager.bib);
        }

        return false;
    }

    /**
    * returns this filter description
    * to be displayed on the JFileChooser
    * @return String
    */
    @Override
    public String getDescription() {
        return ".bib files only";
    }
}
