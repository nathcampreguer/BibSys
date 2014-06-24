/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unesp.rc.bibsys.beans;

/**
 *
 * @author nathalia
 */
public class BibIndexItem {
    
    /**
     * Item's key (@ARTICLE, @BOOK, etc)
     */
    private final String key;
     /**
     * Item's tag (@ARTICLE, @BOOK, etc)
     */
    private final String type;
    /**
     * Item's title
     */
    private final String title;
    
    public BibIndexItem(String _key, String _type, String _title) {
        key = _key;
        type = _type;
        title = _title;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    } 
        
}
