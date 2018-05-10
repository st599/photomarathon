//******************************************************************************
//  Filename        : topicListFromFile.java
//  Project         : PhotoMarathon
//  Description     : Gets contestant list from file
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
package photomarathon.textFileHandler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import photomarathon.topicList;

/**
 *
 * @author cy
 */
public class topicListFromFile {
    String filename;
    topicList tl;

    public topicListFromFile(String filename, topicList tl) {
        this.filename = filename;
        this.tl = tl;
    }
    
    public void readFile()  {
        try {
            InputStream fis;
            BufferedReader br;
            String line = "";
            
            fis = new FileInputStream(filename);
            br = new BufferedReader(new InputStreamReader(fis));
            while ((line = br.readLine()) != null) {
                if (line.trim().length() > 0)  {
                    System.out.println("......" + line);
                    tl.addTopicSql(line);
                }
            }

            // Done with the file
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(topicListFromFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
