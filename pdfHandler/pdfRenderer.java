//******************************************************************************
//  Filename        : pdfRenderer.java
//  Project         : PhotoMarathon
//  Description     : Renders XHTM to PDF
//  Author          : Simon Thompson
//  Requires        : Java 1.7 or later
//                  : iText XHTML Renderer
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
package photomarathon.pdfHandler;

import com.lowagie.text.DocumentException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringBufferInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.SAXException;

/**
 *
 * @author cy
 */
public class pdfRenderer {
    
    StringBuffer buffer;
    String outputFilename;

    public pdfRenderer(StringBuffer buffer, String outputFilename) {
        this.buffer = buffer;
        this.outputFilename = outputFilename;
    }
    
    public void render()  {
        try {
            System.out.println("Rendering PDF");
            // parse the markup into an xml Document
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new StringBufferInputStream(buffer.toString()));
            
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(doc, null);
                        
            System.out.println("...Filename: " + outputFilename);
            OutputStream os = new FileOutputStream(outputFilename);
            renderer.layout();
            
            System.out.println("......Opening for Writing ");
            renderer.createPDF(os);
            
            os.close();

            System.out.println("......Closing");
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(pdfRenderer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(pdfRenderer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(pdfRenderer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(pdfRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
