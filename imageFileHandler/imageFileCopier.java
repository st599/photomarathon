//******************************************************************************
//  Filename        : imageFileCopier.java
//  Project         : PhotoMarathon
//  Description     : Copies Files across Filesystem
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
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Copies Files across Filesystem
 * @author Simon Thompson
 * @version 1.0
 */
public class imageFileCopier {
    String fromPath;
    String toPath;

    /**
     * 
     * @param fromPath
     * @param toPath 
     */
    public imageFileCopier(String fromPath, String toPath) {
        this.fromPath = fromPath;
        this.toPath = toPath;
    }
    
    /**
     * 
     */
    public void copyFile()  {
        
        CopyOption[] options = new CopyOption[]{
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.COPY_ATTRIBUTES
        }; 
        
        File from = new File(fromPath);
        File to = new File(toPath);
        
        try {
            Files.copy(from.toPath(), to.toPath(), options);
        } catch (IOException ex) {
            Logger.getLogger(imageFileCopier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
