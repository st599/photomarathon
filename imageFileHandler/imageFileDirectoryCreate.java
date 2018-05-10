//******************************************************************************
//  Filename        : imageFileDirectoryCreate.java
//  Project         : PhotoMarathon
//  Description     : Creates a single contestant's import directory
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

/**
 * Creates a single contestants import directory
 * @author Simon Thompson
 */
public class imageFileDirectoryCreate {
    
    String dir;

    public imageFileDirectoryCreate(String dir) {
        this.dir = dir;
    }
    
    public void createDir() {
        File dirF = new File(dir);
        
        dirF.mkdirs();
    }
    
}
