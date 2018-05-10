//******************************************************************************
//  Filename        : xHtmlBufferWriter.java
//  Project         : PhotoMarathon
//  Description     : Generates xHTML buffer to convert to PDF
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
package photomarathon.pdfHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import photomarathon.contestant;
import photomarathon.contestantList;
import photomarathon.eventData;

/**
 *
 * @author cy
 */
public class xhtmlBufferWriter {
    
    StringBuffer buffer;
        
    contestantList cl;
    
    eventData ed;
    
    String footer = "Created with Photo Marathon version: ";
    double version;
    
    boolean eventData = false;

    public xhtmlBufferWriter(contestantList cl, double version, eventData ed) {
        this.cl = cl;
        this.version = version;
        buffer = new StringBuffer();
        footer = footer + version;
        this.ed = ed;
    }

    public void test() {
        
        buffer.append("<html>");
    
        // put in some style
        buffer.append("<head><style language='text/css'>");
        buffer.append("h3 { border: 1px solid #aaaaff; background: #ccccff; ");
        buffer.append("padding: 1em; text-transform: capitalize; font-family: sansserif; font-weight: normal;}");
        buffer.append("p { margin: 1em 1em 4em 3em; } p:first-letter { color: red; font-size: 150%; }");
        buffer.append("h2 { background: #5555ff; color: white; border: 10px solid black; padding: 3em; font-size: 200%; }");
        buffer.append("</style></head>");
    
        // generate the body
        buffer.append("<body>");
        //buffer.append("<p><img src='100bottles.jpg'/></p>");
        
        buffer.append("<h2>No more bottles of beer on the wall, no more bottles of beer. ");
        buffer.append("Go to the store and buy some more, 99 bottles of beer on the wall.</h2>");
        buffer.append("</body>");
        buffer.append("</html>");
        
    }  
    
    public void createContestantDataSheets()  {
        Vector v = cl.getContestants();
        if(ed.getEventLocation().equals(""))  {
            System.out.println("YOU HAVE NOT ENTERED EVENT DATA");
        }
        else {
            for(int i = 0; i < v.size(); i++)  {
                contestant c = (contestant) v.elementAt(i);
                System.out.println("C: " + c.getName());
                if(c.getTopics().equals("")) {
                    System.out.println("YOU HAVE NOT ENTERED TOPIC DATA");
                }
                else {
                    singleContestantDataSheet(c);
                }
            }
        }
    }
    
    private void singleContestantDataSheet(contestant cont)  {
        
        buffer = new StringBuffer();
        
        String filename = ed.getEventName() + "_" + cont.getName() + ".pdf";
        filename = filename.replace(" ", "-");
        
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        
        //HTML Head
        buffer.append("<html><head><title>Contestant Information</title><style>");
        buffer.append("table, th, td (");
        buffer.append("border: 0px;");
        buffer.append("border-collapse: collapse;");
        buffer.append("text-align: center;)");
        buffer.append("table.names (");
        buffer.append("width: 100%;  ");
        buffer.append("border: 0px;  ");
        buffer.append("background-color: #f1f1c1;)");
        buffer.append("table.names td(");
        buffer.append("text-align: left;");
        buffer.append(")");
        buffer.append("table.maps (");
        buffer.append("width: 100%;  ");
        buffer.append("border: 0px;  ");
        buffer.append("text-align: left;");
        buffer.append(")");
        buffer.append("table.maps td(");
        buffer.append("text-align: left;");
        buffer.append(")");
        buffer.append("</style>");
        buffer.append("</head>");
        
        //HTML Body
        buffer.append("<body>");
        buffer.append("<table style=\"width:80%\" text-align=\"center\">");
        buffer.append("<tr>");
        buffer.append("<td><img src=\"").append(ed.getEventLogoFilename()).append("\" width=\"40%\"></img></td>");
        buffer.append("</tr>");
        buffer.append("<tr>");
        buffer.append("<td><h2>").append(ed.getEventName()).append("</h2></td>");
        buffer.append("</tr>");
//        buffer.append("<tr>");
//        //buffer.append("<td><h2>").append(ed.getEventLocation()).append(" - ").append(ed.getCompetitionStart()).append("</h2></td>");
//        buffer.append("</tr>");
        buffer.append("</table>");
        buffer.append("<table style=\"width:50%\" class=\"names\">");
        buffer.append("<tr>");
        buffer.append("<td style=\"width:40%;\"><strong>Contestant Name</strong></td><td>").append(cont.getName()).append("</td>");
        buffer.append("</tr>");
        buffer.append("<tr>");
        buffer.append("<td style=\"width:40%;\"><strong>Contestant Email</strong></td><td>").append(cont.getEmail()).append("</td>");
        buffer.append("</tr>");
        buffer.append("<tr>");
        buffer.append("<td style=\"width:40%;\"><strong>Contestant Phone</strong></td><td>").append(cont.getPhone()).append("</td>");
        buffer.append("</tr>");
        buffer.append("</table>");
        buffer.append("<p>Hi ").append(cont.getName()).append("</p>");
        String start = sdf.format(ed.getCompetitionStart());
        String start1 = sdf1.format(ed.getCompetitionStart());
        String end = sdf.format(ed.getCompetitionEnd());
        buffer.append("<p>Thanks for taking part in this Photo Marathon competition.  Remember, photos must be taken on ").append(start1).append(" between ").append(start).append(" and ").append(end).append(".  We can't accept Raw files, please shoot JPEG.  Finally, check your camera clock is set correctly.  GOOD LUCK!</p>");
        buffer.append("<h3>Your Randomly Generated Topics:</h3>");
        buffer.append("<ul>");
        Vector als = cont.getTopics();
        for(int i = 0; i < als.size(); i++)  {
            buffer.append("<li>").append((String)als.get(i)).append("</li>");
        }
        buffer.append("</ul>");
        buffer.append("<h3>The Delivery Location</h3>");
        buffer.append("<table style=\"width:100%\" class=\"maps\">");
        buffer.append("<tr>");
        buffer.append("<td colspan=\"2\"><img src=\"").append(ed.getDeliveryMapFilename()).append("\" width=\"400\"></img></td>");
        buffer.append("</tr>");
        buffer.append("<tr>");
        buffer.append("<td><strong>Address</strong></td><td>").append(ed.getDeliveryAddress()).append("</td>");
        buffer.append("</tr>");
        buffer.append("<tr>");
        start = sdf.format(ed.getDeliveryStart());
        end = sdf.format(ed.getDeliveryEnd());
        buffer.append("<td><strong>Delivery Time</strong></td><td>Between ").append(start).append(" and ").append(end).append("</td>");
        buffer.append("</tr>");
        buffer.append("</table>");
        buffer.append("<p>").append(footer).append("</p>");
        buffer.append("</body>");
        buffer.append("</html>");
        
        //System.out.println(buffer);
        
        pdfRenderer pr = new pdfRenderer(buffer, filename);
        pr.render();
        
    }

    public StringBuffer getBuffer() {
        return buffer;
    }
    
    
}
