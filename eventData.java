//******************************************************************************
//  Filename        : eventData.java
//  Project         : PhotoMarathon
//  Description     : Data Relating to Event
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
package photomarathon;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import photomarathon.sqlLiteHandler.sqliteFile;

/**
 *
 * @author cy
 */
public class eventData {
    
    String eventName = "PhotoMarathon";
    String eventLocation = "";
    String eventAddress = "";
    String eventLogoFilename = "";
    Date competitionStart;
    Date competitionEnd;
    Date deliveryStart;
    Date deliveryEnd;
    String deliveryAddress = "";
    String deliveryMapFilename = "";
    String organisers = "";
    
    sqliteFile sqf;
    Statement stmt = null;
    String tableName = "EVENTS";
    Connection c;
    boolean tableExists = false;
    int maxId = 0;
    

    public eventData(String eventName, String eventLocation, String eventAddress, String eventLogoFilename, Date competitionStart, Date competitionEnd, Date deliveryStart, Date deliveryEnd, String deliveryAddress, String deliveryMapFilename, String organisers, sqliteFile sqf) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventAddress = eventAddress;
        this.eventLogoFilename = eventLogoFilename;
        this.competitionStart = competitionStart;
        this.competitionEnd = competitionEnd;
        this.deliveryStart = deliveryStart;
        this.deliveryEnd = deliveryEnd;
        this.deliveryAddress = deliveryAddress;
        this.deliveryMapFilename = deliveryMapFilename;
        this.organisers = organisers;
        this.sqf = sqf;
        this.c = sqf.getC();
    }

    public eventData(sqliteFile sqf) {
        this.sqf = sqf;
        this.c = sqf.getC();
    }
    
    public void addEventData(String eventName, String eventLocation, String eventAddress, String eventLogoFilename, Date competitionStart, Date competitionEnd, Date deliveryStart, Date deliveryEnd, String deliveryAddress, String deliveryMapFilename, String organisers) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventAddress = eventAddress;
        this.eventLogoFilename = eventLogoFilename;
        this.competitionStart = competitionStart;
        this.competitionEnd = competitionEnd;
        this.deliveryStart = deliveryStart;
        this.deliveryEnd = deliveryEnd;
        this.deliveryAddress = deliveryAddress;
        this.deliveryMapFilename = deliveryMapFilename;
        this.organisers = organisers;
    }
    
     public void testAndCreateTable()  {
        
        Vector v = sqf.listTables(false);
        
        for (int i = 0; i < v.size(); i++) {
            
            String test = (String)v.elementAt(i);
            if(test.equalsIgnoreCase(tableName)) {
                tableExists = true;
                System.out.println("......TABLE FOUND: " + tableName);
            } 
        }
        
        if(!tableExists)  {
            
            System.out.println("......CREATING TABLE: " + tableName);
            try {
                stmt = c.createStatement();
                String sql = "CREATE TABLE " + tableName + " " +
                        "(ID INT PRIMARY KEY     NOT NULL," +
                        " NAME      CHAR(200)    NOT NULL," +
                        " LOCATION  CHAR(200)    NOT NULL," +
                        " ADDRESS   CHAR(200)    NOT NULL," +
                        " LOGO      CHAR(200)    NOT NULL," +
                        " START     CHAR(200)    NOT NULL," +
                        " END       CHAR(200)    NOT NULL," +
                        " DSTART    CHAR(200)    NOT NULL," +
                        " DEND      CHAR(200)    NOT NULL," +
                        " DADDRESS  CHAR(200)    NOT NULL," +
                        " DMAP      CHAR(200)    NOT NULL," +
                        " ORGANISER CHAR(200)    NOT NULL);";
                
                System.out.println(sql);
                        
                stmt.executeUpdate(sql);
                stmt.close();
                c.commit();
            } catch (SQLException ex) {
                Logger.getLogger(eventData.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
        }
        else if(tableExists) {
            System.out.println(".........Reading Table");
            try {
                //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery( "SELECT * FROM " + tableName + ";" );
                while ( rs.next() ) {
                    int id = rs.getInt("id");
                    this.eventName = rs.getString("name");
                    System.out.println("............Name: " + eventName);
                    this.eventLocation = rs.getString("location");
                    System.out.println("............Location: " + eventLocation);
                    this.eventAddress = rs.getString("address");
                    System.out.println("............Address: " + eventAddress);
                    this.eventLogoFilename = rs.getString("logo");
                    System.out.println("............Logo File: " + eventLogoFilename);
                    this.competitionStart = sdf.parse(rs.getString("start"));
                    System.out.println("............Start: " + competitionStart);
                    this.competitionEnd = sdf.parse(rs.getString("end"));
                    System.out.println("............End: " + competitionEnd);
                    this.deliveryStart = sdf.parse(rs.getString("dstart"));
                    System.out.println("............Delivery Start: " + deliveryStart);
                    this.deliveryEnd = sdf.parse(rs.getString("dend"));
                    System.out.println("............DeliveryEnd: " + deliveryEnd);
                    this.deliveryAddress = rs.getString("daddress");
                    System.out.println("............Delivery Address: " + deliveryAddress);
                    this.deliveryMapFilename = rs.getString("dmap");
                    System.out.println("............Delivery Map: " + deliveryMapFilename);
                    this.organisers = rs.getString("organiser");
                    System.out.println("............Organiser: " + organisers);
                }
                rs.close();
                stmt.close();
                c.commit();
            }
            catch (Exception e) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
        }
    }

    public void addToSql()  {      
     
        maxId++;
        
        try{
            stmt = c.createStatement();
            String sql = "INSERT INTO " + tableName + " (ID,NAME,LOCATION,ADDRESS,LOGO,START,END,DSTART,DEND,DADDRESS,DMAP,ORGANISER) " +
                   "VALUES (" + maxId + ",'" + eventName + "','"+eventLocation+"','"+eventAddress+"','"+eventLogoFilename+"','"+competitionStart +"','"+competitionEnd+"','"+deliveryStart+"','"+deliveryEnd+"','"+deliveryAddress+"','"+deliveryMapFilename +"','"+organisers + "');";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
        }
        catch(Exception e){
            System.err.println( e );
            System.exit(0);
        }
    }
     
    public Date getCompetitionEnd() {
        return competitionEnd;
    }

    public void setCompetitionEnd(Date competitionEnd) {
        this.competitionEnd = competitionEnd;
    }

    public Date getCompetitionStart() {
        return competitionStart;
    }

    public void setCompetitionStart(Date competitionStart) {
        this.competitionStart = competitionStart;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Date getDeliveryEnd() {
        return deliveryEnd;
    }

    public void setDeliveryEnd(Date deliveryEnd) {
        this.deliveryEnd = deliveryEnd;
    }

    public String getDeliveryMapFilename() {
        return deliveryMapFilename;
    }

    public void setDeliveryMapFilename(String deliveryMapFilename) {
        this.deliveryMapFilename = deliveryMapFilename;
    }

    public Date getDeliveryStart() {
        return deliveryStart;
    }

    public void setDeliveryStart(Date deliveryStart) {
        this.deliveryStart = deliveryStart;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventLogoFilename() {
        return eventLogoFilename;
    }

    public void setEventLogoFilename(String eventLogoFilename) {
        this.eventLogoFilename = eventLogoFilename;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getOrganisers() {
        return organisers;
    }

    public void setOrganisers(String organisers) {
        this.organisers = organisers;
    } 

    @Override
    public String toString() {
        return "eventData{" + "eventName=" + eventName + ", eventLocation=" + eventLocation + ", eventAddress=" + eventAddress + ", eventLogoFilename=" + eventLogoFilename + ", competitionStart=" + competitionStart + ", competitionEnd=" + competitionEnd + ", deliveryStart=" + deliveryStart + ", deliveryEnd=" + deliveryEnd + ", deliveryAddress=" + deliveryAddress + ", deliveryMapFilename=" + deliveryMapFilename + ", organisers=" + organisers + '}';
    }
    
    
}
