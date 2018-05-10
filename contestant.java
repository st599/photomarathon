//******************************************************************************
//  Filename        : contestant.java
//  Project         : PhotoMarathon
//  Description     : Details about individual contestants
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
public class contestant {
    
    String name;
    String email;
    String phone;
    Vector topics;

    public contestant(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        topics = new Vector();
    }

    public contestant(String name, String email, String phone, Vector topics) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.topics = topics;
    }
    
    
    
    public void addTopic(String topic)  {
        topics.add(topic);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Vector getTopics() {
        return topics;
    }

    public void setTopics(Vector topics) {
        this.topics = topics;
    }
    
}
