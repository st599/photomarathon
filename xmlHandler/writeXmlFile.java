//******************************************************************************
//  Filename        : writeXmlFile.java
//  Project         : PhotoMarathon
//  Description     : Writes XML file
//  Author          : Simon Thompson
//  Requires        : Java 1.7 or later
//  Licence         : GPL v3
//  Copyright       : Simon Thompson MMXIV
//
//  This file is part of PhotoMarathon.
//
//  PhotoMarathon is free software: you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  PhotoMarathon is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
//******************************************************************************
package photomarathon.xmlHandler;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Vector;
import photomarathon.contestant;
import photomarathon.contestantList;
import photomarathon.topicList;

/**
 * Writes XML file
 * @author Simon Thompson
 */
public class writeXmlFile {
    
    String filename;
    FileOutputStream fout;
    PrintStream ps;

    public writeXmlFile(String filename, topicList tl, contestantList cl) {
        this.filename = filename;
        
        try{
            fout = new FileOutputStream (filename);
            PrintStream ps = new PrintStream(fout);
            ps.println("<?xml version=\"1.0\"?>");
            ps.println("<photomarathon>");
            String top = printTopicListToXml(tl);
            ps.print(top);
            top = printContestantListToXml(cl);
            ps.print(top);
            ps.println("</photomarathon>");
            ps.println("</xml>");
            ps.close();
            fout.close();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        
    }
    
    
    private String introTag(String name, int indent)  {
        
        
        String pp = "";

        switch (indent) {
            case 1:  pp = "\t";
                     break;
            case 2:  pp = "\t\t";
                     break;
            case 3:  pp = "\t\t\t";
                     break;
            case 4:  pp = "\t\t\t\t";
                     break;
            case 5:  pp = "\t\t\t\t\t";
                     break;
            case 6:  pp = "\t\t\t\t\t\t";
                     break;
            case 7:  pp = "\t\t\t\t\t\t\t";
                     break;
            case 8:  pp = "\t\t\t\t\t\t\t\t";
                     break;
            default: pp = "";
                     break;
        }

        pp = pp + "<" + name + ">";

        System.out.println("...XML intro: " + pp);
        
        return pp;
        
    }
    
    private String outroTag(String name, int indent)  {
        String pp = "";
        
        switch (indent) {
            case 1:  pp = "\t";
                     break;
            case 2:  pp = "\t\t";
                     break;
            case 3:  pp = "\t\t\t";
                     break;
            case 4:  pp = "\t\t\t\t";
                     break;
            case 5:  pp = "\t\t\t\t\t";
                     break;
            case 6:  pp = "\t\t\t\t\t\t";
                     break;
            case 7:  pp = "\t\t\t\t\t\t\t";
                     break;
            case 8:  pp = "\t\t\t\t\t\t\t\t";
                     break;
            default: pp = "";
                     break;
        }
        
        pp = pp + "</" + name + ">";
        
        System.out.println("...XML outro: " + pp);
        
        return pp;
        
    }
    
    private String fullTag(String name, String content, int indent)  {
        String pp = "";
        
        switch (indent) {
            case 1:  pp = "\t";
                     break;
            case 2:  pp = "\t\t";
                     break;
            case 3:  pp = "\t\t\t";
                     break;
            case 4:  pp = "\t\t\t\t";
                     break;
            case 5:  pp = "\t\t\t\t\t";
                     break;
            case 6:  pp = "\t\t\t\t\t\t";
                     break;
            case 7:  pp = "\t\t\t\t\t\t\t";
                     break;
            case 8:  pp = "\t\t\t\t\t\t\t\t";
                     break;
            default: pp = "";
                     break;
        }
        
        pp = pp + "<" + name + ">" + content + "</" + name + ">";
        
        System.out.println("...XML full: " + pp);
        
        return pp;
        
    }
    
    private String printTopicListToXml(topicList tl)  {
        
        ArrayList<String> list = tl.getList();
        
        String ss = "";
        
        ss = ss + introTag("topic-list", 1) + "\n";
        
        for(int i = 0; i < list.size(); i++)  {
            ss = ss + fullTag("topic", list.get(i), 2) + "\n";
        }
        
        ss = ss + outroTag("topic-list", 1) + "\n";
        
        return ss;
        
    }
    
    private String printContestantListToXml(contestantList cl) {
        Vector list = cl.getContestants();
        
        String ss = "";
        
        ss = ss + introTag("contestant-list", 1) + "\n";
        
        for(int i = 0; i < list.size(); i++)  {
            contestant c = (contestant)list.elementAt(i);
            ss = ss + introTag("contestant", 2) + "\n";
            ss = ss + fullTag("name", c.getName(), 3) + "\n";
            ss = ss + fullTag("email", c.getEmail(), 3) + "\n";
            ss = ss + fullTag("phone", c.getPhone(), 3) + "\n";
            ss = ss + introTag("contestant-topic-list", 3) + "\n";
            Vector topics = c.getTopics();
            for(int j = 0; j < topics.size(); j++)  {
                ss = ss + fullTag("contestant-topic", (String)topics.get(j), 4) + "\n";
            }
            ss = ss + outroTag("contestant-topic-list", 3) + "\n";
            ss = ss + outroTag("contestant", 2) + "\n";
        }
        
        ss = ss + outroTag("contestant-list", 1) + "\n";
        
        return ss;
    }
}
