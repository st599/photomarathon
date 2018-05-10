//******************************************************************************
//  Filename        : PhotoMarathon.java
//  Project         : PhotoMarathon
//  Description     : Provides Main Function
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

import com.github.jankroken.commandline.*;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import photomarathon.pdfHandler.xhtmlBufferWriter;
import photomarathon.sqlLiteHandler.sqliteFile;
import photomarathon.xmlHandler.writeXmlFile;
import photomarathon.textFileHandler.*;

/**
 *
 * @author Simon Thompson
 * @TODO  2) SQL Handler for event data 3) Fix output to console
 */
public class PhotoMarathon {

    static int version = 0;
    static String sqlFilename = "photomarathon.sqlite";
    static String xmlFilename = "photomarathon.xml";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Command Line Arguments
        
        String topicListFilename = "";
        String contestantListFilename = "";
        String logoFilename = "";
        boolean logo = false;
        boolean topicList = false;
        boolean topicListFromFile = false;
        boolean contestantListFromFile = false;
        boolean printXml = false;
        boolean verbose = false;
        boolean contestants = false;
        boolean outputToPDF = false;
        boolean eventData = false;
        boolean generateTopics = false;
        
        // Print Header
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("  PHOTOMARATHON ver:" + version);
        System.out.println("  Released without warranty under a GPL3 Licence");
        System.out.println("  (c) Simon Thompson MMXIV");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("");
        
        
        // Parse Command Line
        try {
                System.out.println("PARSING COMMAND LINE");
                CLIarguments arguments = CommandLineParser.parse(CLIarguments.class, args, OptionStyle.SIMPLE);
                //If No Arguments
                if(args.length == 0) {
                    CLIarguments.usage();
                }
                verbose = arguments.isVerbose();
                printXml = arguments.isPrintXml();
                logo = arguments.isLogo();
                if(logo)  {
                    logoFilename = arguments.getLogoFilename();
                }
                topicList = arguments.isTopicList();
                topicListFromFile = arguments.isTopicListFromFile();
                if(topicListFromFile)  {
                    topicListFilename = arguments.getTopicListFilename();
                }
                contestantListFromFile = arguments.isContestantListFromFile();
                if(contestantListFromFile)  {
                    contestantListFilename = arguments.getContestantListFilename();
                }
                contestants = arguments.isContestants();
                outputToPDF = arguments.isOutputPdf();
                eventData = arguments.isEventData();
                generateTopics = arguments.isGenerateTopics();
        } catch (IllegalAccessException ex) {
                Logger.getLogger(PhotoMarathon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
                Logger.getLogger(PhotoMarathon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
                Logger.getLogger(PhotoMarathon.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        System.out.println("INITIALISING DATA STRUCTURES");
        // Initialise SQL File
        sqliteFile sqf = new sqliteFile(sqlFilename);
        sqf.createSqlFile();
        
        // Initialise Data Structures
        topicList tl = new topicList(sqf);
        tl.testAndCreateTable();
        contestantList cl = new contestantList(sqf);
        cl.testAndCreateTable();
        eventData event = new eventData(sqf);
        event.testAndCreateTable();
        event.toString();
        
        
        // Event details from console
        if(eventData)  {
            System.out.println("GETTING EVENT DATA FROM CONSOLE\n\n");
            String eventName;
            String eventLocation;
            String eventAddress;
            String eventLogoFilename;
            String competitionStart_S;
            String competitionEnd_S;
            String deliveryStart_S;
            String deliveryEnd_S;
            String deliveryAddress;
            String deliveryMapFilename;
            String organisers;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Scanner in = new Scanner(System.in);
            try {
                System.out.println("Enter Event Name: ");
                eventName = in.nextLine();
                System.out.println("Enter Event Location, e.g. London: ");
                eventLocation = in.nextLine();
                System.out.println("Enter Event Meeting Address: ");
                eventAddress = in.nextLine();
                System.out.println("Enter Event Logo Filename: ");
                eventLogoFilename = in.nextLine();
                System.out.println("Enter Event Start Date and Time dd-MM-yyyy HH:mm : ");
                competitionStart_S = in.nextLine();
                Date sc = sdf.parse(competitionStart_S);
                System.out.println("Enter Event End Date and Time dd-MM-yyyy HH:mm : ");
                competitionEnd_S = in.nextLine();
                Date ec = sdf.parse(competitionEnd_S);
                System.out.println("Enter Delivery Start Date and Time dd-MM-yyyy HH:mm : ");
                deliveryStart_S = in.nextLine();
                Date sd = sdf.parse(deliveryStart_S);
                System.out.println("Enter Delivery Start Date and Time dd-MM-yyyy HH:mm : ");
                deliveryEnd_S = in.nextLine();
                Date ed = sdf.parse(deliveryEnd_S);
                System.out.println("Enter Delivery Address: ");
                deliveryAddress = in.nextLine();
                System.out.println("Enter Delivery Map Filename: ");
                deliveryMapFilename = in.nextLine();
                System.out.println("Enter Organisers: ");
                organisers = in.nextLine();
                event.addEventData(eventName, eventLocation, eventAddress, eventLogoFilename, sc, ec, sd,ed,deliveryAddress,deliveryMapFilename, organisers);
                event.addToSql();
            }
            catch(Exception e) {
                System.err.println(e);
            }
        }
        
       
        
        // Topics From Console
        if(topicList)  {
            System.out.println("GETTING TOPIC LIST FROM CONSOLE\n\n");
            String topic = "xxx";
            Scanner in = new Scanner(System.in);

            System.out.println("leave blank to exit");
            while(!topic.equals(""))  {
                System.out.println("Enter Topic: ");
                topic = in.nextLine();
                if(topic.equals("")) {}
                else {
                    tl.addTopicSql(topic);
                }
            }
            in.close();
        }
        
        
        // Topics From File
        if(topicListFromFile)  {
            System.out.println("GETTING TOPIC LIST FROM FILE\n\n");
            topicListFromFile tlff = new topicListFromFile(topicListFilename, tl);
            tlff.readFile();
        }

        tl.printFullTopicList();
        
        // Contestants from Console
        if(contestants)  {
            System.out.println("GETTING CONTESTANT LIST FROM CONSOLE\n\n");
            String name = "xxx";
            String email = "xxx";
            String phone = "xxx";
            Scanner in = new Scanner(System.in);

            System.out.println("leave blank to exit");
            while(!name.equals(""))  {
                System.out.println("Enter name: ");
                name = in.nextLine();
                System.out.println("Enter email: ");
                email = in.nextLine();
                System.out.println("Enter phone: ");
                phone = in.nextLine();
                if(name.equals("")) {}
                else {
                    contestant c = new contestant(name, email, phone);
                    cl.addNewContestant(c);
                }
            }
            in.close();
        }
        //Contestant From File
        if(contestantListFromFile)  {
            System.out.println("GETTING CONTESTANT LIST FROM FILE\n\n");
            contestantListFromFile clff = new contestantListFromFile(contestantListFilename, cl);
            clff.readFile();
        }
        
        cl.printFullContestantList();

        
        if(generateTopics) {
            System.out.println("GENERATING RANDOM TOPICS FOR EACH CONTESTANT\n\n");
            generateRandomTopics grt = new generateRandomTopics(cl, tl, 12);
            grt.generate();
        }
        
        if(printXml) {
            System.out.println("WRITING XML FILE\n\n");
            writeXmlFile xml = new writeXmlFile(xmlFilename, tl, cl);
        }
        
        if(outputToPDF)  {
            if(event.getEventLocation().equalsIgnoreCase(""))
            {
                System.out.println("ERROR: Add event data before creating PDF");
            }
            else
            {
                System.out.println("WRITING CONTESTANT PDF FILES\n\n");
                xhtmlBufferWriter xbw = new xhtmlBufferWriter(cl, version, event);
                xbw.createContestantDataSheets();
            }
        }
        
        System.out.println("CLOSING DATA STRUCTURES\n\n");
        sqf.closeFile();
        
        System.out.println("EXITING\n\n");
    }
}
