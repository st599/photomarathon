//******************************************************************************
//  Filename        : topicList.java
//  Project         : PhotoMarathon
//  Description     : List of topics
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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import photomarathon.sqlLiteHandler.sqliteFile;

/**
 *
 * @author cy
 */
public class topicList {
    
    ArrayList<String> list;
    sqliteFile sqf;
    
    Statement stmt = null;
    int maxID = 0;
    String tableName = "TOPICS";
    Connection c;
    boolean tableExists = false;

    public topicList(sqliteFile sqf) {
        
        list = new ArrayList<String>();
        this.sqf = sqf;
        this.c = sqf.getC();
        
    }
    
    public void addTopic(String topic)  {      
        list.add(topic);   
    }
    
    public void addTopicSql(String topic)  {      
        list.add(topic);   
        
        maxID++;
        
        try{
            stmt = c.createStatement();
            String sql = "INSERT INTO " + tableName + " (ID,TOPIC) " +
                   "VALUES (" + maxID + ", '" + topic + "');";
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
    
    public void printFullTopicList()  {
        System.out.println("FULL TOPIC LIST");
        
        for(int i = 0; i < list.size(); i++) {
            System.out.println(i + "\t" + list.get(i));
        }
        System.out.println("");
    }
    
    public void printTopic(int id)  {
        System.out.println(list.get(id));
    }
    
    public String[] generateRandomList(int listSize)  {
        
        ArrayList<String> randomised = new ArrayList<String>();
        
        Random randomGenerator = new Random();
        
        for(int i = 0; i < listSize; i++) {
            int randomInt;
            randomInt = randomGenerator.nextInt(list.size());
            while(randomised.contains(list.get(randomInt))) {
                //System.out.println("duplicate");
                randomInt = randomGenerator.nextInt(list.size());
            }
            randomised.add(list.get(randomInt));
        }
        
        String[] randList = new String[listSize];
        for(int k = 0; k < listSize; k++) {
            randList[k] = randomised.get(k);
        }
        
        return randList;
        
    }

    public ArrayList<String> getList() {
        return list;
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
                        " TOPIC           CHAR(200)    NOT NULL); ";
                
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
                    String sqlTopic = rs.getString("topic");
                    addTopic(sqlTopic);
                    if (id > maxID) {
                        maxID = id;
                        System.out.println("............" + sqlTopic);
                    }
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

    public int getMaxID() {
        return maxID;
    }
    
    
    
}
