/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unesp.rc.bibsys.utils;


import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

    /**
    * This is class was developed by a third part.
    * It parses a XML document to a TreeModel for a JTree
    * Code from:
    * http://www.java2s.com/Code/Java/XML/XMLTreeSimpleXMLutilityclass.htm
    */
public class VSX2 {

  public TreeModel parse(String filename) throws Exception {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    XMLIconTreeHandler handler = new XMLIconTreeHandler();
    SAXParser saxParser = factory.newSAXParser();
    saxParser.parse(new File(filename), handler);
    return new DefaultTreeModel(handler.getRoot());
  }

  public static class XMLIconTreeHandler extends DefaultHandler {
    private DefaultMutableTreeNode root, currentNode;

    public DefaultMutableTreeNode getRoot() {
      return root;
    }

    @Override
    public void startElement(String namespaceURI, String lName, String qName, Attributes attrs)
        throws SAXException {
      String eName = lName;
      if ("".equals(eName))
        eName = qName;
      ITag t = new ITag(eName, attrs);
      DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(t);
      if (currentNode == null) {
        root = newNode;
      } else {
        currentNode.add(newNode);
      }
      currentNode = newNode;
    }

    @Override
    public void endElement(String namespaceURI, String sName, String qName) throws SAXException {
      currentNode = (DefaultMutableTreeNode) currentNode.getParent();
    }

    @Override
    public void characters(char buf[], int offset, int len) throws SAXException {
      String s = new String(buf, offset, len).trim();
      ((ITag) currentNode.getUserObject()).addData(s);
    }
  }

  public static class ITag implements IconAndTipCarrier {
    private String name;

    private String data;

    private Attributes attr;

    private Icon icon;

    private String tipText;

    public ITag(String n, Attributes a) {
      name = n;
      attr = a;
      for (int i = 0; i < attr.getLength(); i++) {
        String aname = attr.getQName(i);
        String value = attr.getValue(i);
        if (aname.equals("icon")) {
          tipText = value;
          icon = new ImageIcon(value);
          break;
        }
      }
    }

    public String getName() {
      return name;
    }

    public Attributes getAttributes() {
      return attr;
    }

    public void setData(String d) {
      data = d;
    }

    public String getData() {
      return data;
    }

    @Override
    public String getToolTipText() {
      return tipText;
    }

    @Override
    public Icon getIcon() {
      return icon;
    }

    public void addData(String d) {
      if (data == null) {
        setData(d);
      } else {
        data += d;
      }
    }

    public String getAttributesAsString() {
      StringBuffer buf = new StringBuffer(256);
      for (int i = 0; i < attr.getLength(); i++) {
        buf.append(attr.getQName(i));
        buf.append("=\"");
        buf.append(attr.getValue(i));
        buf.append("\"");
      }
      return buf.toString();
    }

    @Override
    public String toString() {
      String a = getAttributesAsString();
      return name + ": " + a + (data == null ? "" : " (" + data + ")");
    }
  }
}

interface IconAndTipCarrier {
  public Icon getIcon();

  public String getToolTipText();
}

class IconAndTipRenderer extends JLabel implements TreeCellRenderer {
  Color backColor = new Color(0xFF, 0xCC, 0xFF);

  Icon openIcon, closedIcon, leafIcon;

  String tipText = "";

  public IconAndTipRenderer(Icon open, Icon closed, Icon leaf) {
    openIcon = open;
    closedIcon = closed;
    leafIcon = leaf;
    setBackground(backColor);
    setForeground(Color.black);
  }

  @Override
  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
      boolean expanded, boolean leaf, int row, boolean hasFocus) {
    setText(value.toString());
    if (selected) {
      setOpaque(true);
    } else {
      setOpaque(false);
    }

    IconAndTipCarrier itc = null;
    if (value instanceof DefaultMutableTreeNode) {
      Object uo = ((DefaultMutableTreeNode) value).getUserObject();
      if (uo instanceof IconAndTipCarrier) {
        itc = (IconAndTipCarrier) uo;
      }
    } else if (value instanceof IconAndTipCarrier) {
      itc = (IconAndTipCarrier) value;
    }
    if ((itc != null) && (itc.getIcon() != null)) {
      setIcon(itc.getIcon());
      tipText = itc.getToolTipText();
    } else {
      tipText = " ";
      if (expanded) {
        setIcon(openIcon);
      } else if (leaf) {
        setIcon(leafIcon);
      } else {
        setIcon(closedIcon);
      }
    }
    return this;
  }

  @Override
  public String getToolTipText() {
    return tipText;
  }
}