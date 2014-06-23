/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unesp.rc.bibsys.utils;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author nathalia
 */
public class BibtexFilter extends FileFilter{
    //Accept all directories and .bib files.
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

    //The description of this filter
    @Override
    public String getDescription() {
        return ".bib files only";
    }
}
