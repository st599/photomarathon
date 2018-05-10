//******************************************************************************
//  Filename        : sqliteFile.java
//  Project         : PhotoMarathon
//  Description     : Interface to SQLite
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
package photomarathon.sqlLiteHandler;


import java.sql.*;
import java.util.Vector;


/**
 *
 * @author cy
 */
public class sqliteFile {
    
    String filename;
    String connectionName;
    Connection c = null;

    public sqliteFile(String filename) {
        this.filename = filename;
        this.connectionName= "jdbc:sqlite:" + filename;
    }
    
    public void createSqlFile()  {

        System.out.println("...SQLlite Database: " + filename);
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(connectionName);
            c.setAutoCommit(false);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("...Opened database successfully");
    }
    
    public Vector listTables(boolean verbose)  {
        
        System.out.println("...List Tables");
        
        Vector tables = new Vector();
        
        try {
            DatabaseMetaData md = c.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                System.out.println("......Table: " + rs.getString(3));
                tables.add(rs.getString(3));
            }
        }
        catch (Exception e)  {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        
        return tables;   
    }
    
    public void closeFile()  {
        try{
            c.close();
        }
        catch(Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public Connection getC() {
        return c;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public String getFilename() {
        return filename;
    }
    
}
