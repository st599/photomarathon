//******************************************************************************
//  Filename        : imageFileDirectoryList.java
//  Project         : PhotoMarathon
//  Description     : Lists the image files in a directory
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

import java.io.File;
import java.util.Vector;
import javax.activation.MimetypesFileTypeMap;

/**
 * Lists the image files in a directory
 * @author Simon Thompson
 */
public class imageFileDirectoryList {
    
    String imageDirectory;
    File dir;
    File[] files; 
    Vector imgFiles;

    public imageFileDirectoryList(String imageDirectory) {
        this.imageDirectory = imageDirectory;
        this.dir = new File(imageDirectory);
    }
    
    public Vector parseDir() {
        files = dir.listFiles();
        
        for (File file : files) {
            if(file.isFile()) {
                String fileName = file.getName();
                String mimetype= new MimetypesFileTypeMap().getContentType(file);
                String type = mimetype.split("/")[0];
                if(type.equals("image"))  {
                    System.out.println("Image: " + file.getName());
                    imgFiles.add(file.getName());
                }
            }
        }
        
        return imgFiles;
    }
    
}
