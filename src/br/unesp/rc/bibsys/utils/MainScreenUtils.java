/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unesp.rc.bibsys.utils;

import javax.swing.JFileChooser;

/**
 *
 * @author Nathalia
 */
public class MainScreenUtils {
    
    /**
    * Creates a JFileChooser that accepts only .bib files
     * @return 
    */
    public static JFileChooser createFileChooser() {
        final JFileChooser fileChooser = new JFileChooser();
        
        //Create and set a BibtexFilter to the File Chooser
        BibtexFilter filter = new BibtexFilter();
        fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
        fileChooser.setFileFilter(filter);
        
        return fileChooser;
    }
    
}
