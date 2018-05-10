//******************************************************************************
//  Filename        : generateRandomTopics.java
//  Project         : PhotoMarathon
//  Description     : Generates a random topic list for each contestant
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

import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author cy
 */
public class generateRandomTopics {
    
    contestantList cl;
    topicList tl;
    int numTopics;

    public generateRandomTopics(contestantList cl, topicList tl, int numTopics) {
        this.cl = cl;
        this.tl = tl;
        this.numTopics = numTopics;
    }
    
    public void generate()  {
        
        Vector cons = cl.getContestants();
        
        int nc = cons.size();
        
        cl.printFullContestantList();
        
        System.out.println("NUM Contestants" + nc);
        
        for(int i = 0; i < nc; i++)  {
            
            cl.printFullContestantList();
            
            contestant c = (contestant)cons.elementAt(i);
            
            Vector v = new Vector();
            
            System.out.println("Generating Random Topics for Contestant: " + i + " " + c.getName());
            
            String[] list = tl.generateRandomList(numTopics);

            for(int j = 0; j < list.length; j++)  {
                System.out.println("...Adding topic " + j + " - " +list[j]);
                //c.addTopic(list[j]);
                v.add(list[j]);
            } 
            c.setTopics(v);
            cl.addContestantTopicsSql(c);
            
           // cons.insertElementAt(c, i);
        }
    }
    
}
