//******************************************************************************
//  Filename        : importContestantImages.java
//  Project         : PhotoMarathon
//  Description     : Imports a single contestant's image files
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
package photomarathon.imageFileHandler;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;
import photomarathon.contestant;
import photomarathon.contestantList;

/**
 * Imports a single contestants image files
 * @author Simon Thompson
 */
public class importContestantImages {
    
    contestantList cl;
    String baseDir;

    public importContestantImages(contestantList cl, String baseDir) {
        this.cl = cl;
        this.baseDir = baseDir;
    }
    
    public void importImages()  {
        
        Vector conts = cl.getContestants();
        HashMap cHM = new HashMap();
        int contestantKey;
        
        // Parse all contestants
        for(int i = 0; i < conts.size(); i++)  {
            contestant c = (contestant) conts.elementAt(i);
            String name = c.getName();
            cHM.put(i, name);
        }
        
        //User Interface
        System.out.println("IMPORT USER IMAGES\n\n");
        
        System.out.println("...Choose a contestant:");
        System.out.println("......KEY\tCONTESTANT\n");
        
        Scanner in = new Scanner(System.in);
        
        for(int j = 0; j < cHM.size(); j++)  {
            System.out.println("......" + j + "\t" + (String)cHM.get(j));
        }
        System.out.println("......\n Enter contestant key:");
        int key = in.nextInt();
        
        System.out.println("......\n Enter " + (String)cHM.get(key) + "'s camera directory:");
        String camDir = in.nextLine();
        
        String name = (String)cHM.get(key);
        name = name.replaceAll("\\s","-");
        
        String newDir = baseDir + "/" + name;
        
        imageFileDirectoryList ifdl = new imageFileDirectoryList(camDir);
        Vector imageFiles = ifdl.parseDir();
        
        imageFileDirectoryCreate ifdc = new imageFileDirectoryCreate(newDir);
        ifdc.createDir();
        
        for(int k=0; k < imageFiles.size(); k++)  {
            String filename = (String) imageFiles.elementAt(k);
            String input = camDir + "/" + filename;
            String output = newDir + "/" + filename;
            
            System.out.println("......COPYING:");
            System.out.println(".........Input: " + input);
            System.out.println(".........Output: " + output);
            
            imageFileCopier ifc = new imageFileCopier(input, output);
            ifc.copyFile(); 
            
            System.out.println(".........COMPLETE\n");
        }
    }
    
}
