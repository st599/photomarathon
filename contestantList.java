//******************************************************************************
//  Filename        : contestantList.java
//  Project         : PhotoMarathon
//  Description     : List of all contestants
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
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import photomarathon.sqlLiteHandler.sqliteFile;

/**
 *
 * @author cy
 */
public class contestantList {
    
    Vector contestants;
    
    sqliteFile sqf;
    
    Statement stmt = null;
    int maxID = 0;
    String tableName = "CONTESTANTS";
    Connection c;
    boolean tableExists = false;

    public contestantList(sqliteFile sqf) {
        contestants = new Vector();
        this.sqf = sqf;
        this.c = sqf.getC();
    }

    public Vector getContestants() {
        return contestants;
    }
    
    public void addNewContestant(contestant con)  {
        contestants.add(con);
    }
    
    public void addNewContestantSql(contestant con)  {
        maxID++;
        
        contestants.add(con);
        
        Vector topics = con.getTopics();
        String topicList = "";
        for(int i = 0; i < topics.size(); i++)  {
            topicList = topicList + topics.get(i) + "-";
        }
        
        try{
            stmt = c.createStatement();
            String sql = "INSERT INTO " + tableName + " (ID,NAME,EMAIL,PHONE,TOPICS) " +
                   "VALUES (" + maxID + ",'" + con.getName() + "','" + con.getEmail() + "','" + con.getPhone() + "','" + topicList + "');";
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
    
    public void addContestantTopicsSql(contestant con)  {
        
        int id = 0;
        String name = con.getName();
        
        Vector topics = con.getTopics();
        String topicList = "";
        for(int i = 0; i < topics.size(); i++)  {
            topicList = topicList + (String)topics.get(i) + ",";
        }
        
        try{
            stmt = c.createStatement();
            System.out.println("SELECT * FROM " + tableName + " WHERE NAME=\"" + name + "\";");
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE NAME=\"" + name + "\";");
            while ( rs.next() ) {
                id = rs.getInt("id");
                System.out.println(id);
            }
            rs.close();
            stmt.close();
            stmt = c.createStatement();
            String sql = "UPDATE " + tableName + " set TOPICS = \"" + topicList + "\" where ID=" + id +";";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            c.commit();
        }
        catch(Exception e){
            System.err.println( e );
            System.exit(0);
        }
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
                        " NAME           CHAR(200)    NOT NULL, " +
                        " EMAIL          CHAR(200)    NOT NULL, " +
                        " PHONE          CHAR(200)    NOT NULL, " +
                        " TOPICS         CHAR(500)); ";
                
                System.out.println(sql);
                        
                stmt.executeUpdate(sql);
                stmt.close();
                c.commit();
            }
            catch (Exception e) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
            
            
        }
        else if(tableExists) {
            System.out.println(".........Reading Table");
            try {
                stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery( "SELECT * FROM " + tableName + ";" );
                while ( rs.next() ) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String topics = rs.getString("topics");
                    System.out.println("............" + id + " " + name + " " + email + " " + phone);
                    Vector topicList = new Vector();
                    StringTokenizer st = new StringTokenizer(topics, "-,._|"); 
                    while(st.hasMoreTokens()){  
                        topicList.add(st.nextToken());
                    }
                    contestant cont = new contestant(name, email, phone, topicList);
                    contestants.add(cont);
                    if (id > maxID) {
                        maxID = id;
                        //System.out.println(maxID);
                    }
                }
                rs.close();
                stmt.close();
                c.commit();
            } catch (SQLException ex) {
                Logger.getLogger(contestantList.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public void printFullContestantList()  {
        System.out.println("FULL CONTESTANT LIST");
        
        for(int i = 0; i < contestants.size(); i++) {
            contestant con = (contestant)contestants.elementAt(i);
            System.out.println(i + "\t" + con.getName() + " " + con.getEmail() + " " + con.getPhone());
        }
        System.out.println("");
    }

    public int getMaxID() {
        return maxID;
    }
    
    
}
