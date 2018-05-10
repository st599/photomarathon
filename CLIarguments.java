//******************************************************************************
//  Filename        : CLIarguments.java
//  Project         : PhotoMarathon
//  Description     : Commandline Parser
//  Author          : Simon Thompson
//  Requires        : Java 1.7 or later
//                  : commandline 1.7.0 or later
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

import com.github.jankroken.commandline.annotations.*;

/**
 *
 * @author Simon Thompson
 */
public class CLIarguments {
    
    private String topicListFilename;
    private boolean topicList = false;
    private boolean topicListFromFile = false;
    
    private String contestantListFilename;
    private boolean contestantListFromFile = false;
    
    private String logoFilename;
    private boolean logo = false;

    private boolean printXml = false;

    private boolean verbose = false;
    
    private boolean contestants = false;
    
    private boolean outputPdf = false;
    
    private boolean eventData = false;
    
    private boolean generateTopics = false;

    @Option
    @LongSwitch("topiclistfile")
    @SingleArgument
    public void setTopicListFilename(String topicListFilename) {
        this.topicListFilename = topicListFilename;
        this.topicListFromFile = true;
    }
    
    @Option
    @LongSwitch("contestantlistfile")
    @SingleArgument
    public void setContestantListFilename(String contestantListFilename) {
        this.contestantListFilename = contestantListFilename;
        this.contestantListFromFile = true;
    }
    
    @Option
    @LongSwitch("logofile")
    @SingleArgument
    public void setLogoFilename(String logoFilename) {
        this.logoFilename = logoFilename;
        this.logo = true;
    }
   
    @Option
    @LongSwitch("topiclist")
    @ShortSwitch("t")
    @Toggle(true)
    public void setTopicList(boolean topicList) {
        this.topicList = topicList;
    }

    @Option
    @LongSwitch("printxml")
    @ShortSwitch("x")
    @Toggle(true)
    public void setPrintXml(boolean printXml) {
        this.printXml = printXml;
    }
    
    @Option
    @LongSwitch("eventdata")
    @ShortSwitch("e")
    @Toggle(true)
    public void setEventData(boolean eventData) {
        this.eventData = eventData;
    }
    
    @Option
    @LongSwitch("generatetopics")
    @ShortSwitch("g")
    @Toggle(true)
    public void setGenerateTopics(boolean generateTopics) {
        this.generateTopics = generateTopics;
    }

    @Option
    @LongSwitch("verbose")
    @ShortSwitch("v")
    @Toggle(true)
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
    @Option
    @LongSwitch("contestants")
    @ShortSwitch("c")
    @Toggle(true)
    public void setContestants(boolean contestants) {
        this.contestants = contestants;
    }
    
    @Option
    @LongSwitch("printpdf")
    @ShortSwitch("p")
    @Toggle(true)
    public void setOutputPdf(boolean outputPdf) {
        this.outputPdf = outputPdf;
    }
    
    public static void usage()  {
        
        System.out.println("USAGE:");
        System.out.println("");
        System.out.println("\tjava -jar photoMarathon.jar <options>");
        System.out.println("\tOptions:");
        System.out.println("\t\t-topiclistfile filename");
        System.out.println("\t\t-contestantlistfile filename");
        System.out.println("\t\t-logofile filename");
        System.out.println("\t\t-topiclist / -t");
        System.out.println("\t\t-contestants / -c");
        System.out.println("\t\t-g Generate Random Topics List");
        System.out.println("\t\t-eventdata / -e");
        System.out.println("\t\t-printxml / -x");
        System.out.println("\t\t-printpdf / -p");
        
        
    }

    public boolean isPrintXml() {
        return printXml;
    }

    public boolean isTopicList() {
        return topicList;
    }

    public String getTopicListFilename() {
        return topicListFilename;
    }

    public boolean isTopicListFromFile() {
        return topicListFromFile;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public boolean isLogo() {
        return logo;
    }

    public String getLogoFilename() {
        return logoFilename;
    }

    public boolean isContestants() {
        return contestants;
    }

    public String getContestantListFilename() {
        return contestantListFilename;
    }

    public boolean isContestantListFromFile() {
        return contestantListFromFile;
    }

    public boolean isOutputPdf() {
        return outputPdf;
    }

    public boolean isEventData() {
        return eventData;
    }

    public boolean isGenerateTopics() {
        return generateTopics;
    }
}
