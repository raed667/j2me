package Handler;

import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import Entite.FeaturedProd;
/**
 *
 * @author Wael Mallek
 */

public class FeatProdHandler extends DefaultHandler {

    private Vector prodft;
    String idFeatTag = "close";
    String dateFeatTag = "close";
    String widgetTag = "close";
    String idProdTag = "close";

    public FeatProdHandler() {
        prodft = new Vector();
    }

    public FeaturedProd[] getPersonne() {
        FeaturedProd[] prodfts = new FeaturedProd[prodft.size()];
        prodft.copyInto(prodfts);
        return prodfts;
    }
    // VARIABLES TO MAINTAIN THE PARSER'S STATE DURING PROCESSING
    private FeaturedProd currentPersonne;

    // XML EVENT PROCESSING METHODS (DEFINED BY DefaultHandler)
    // startElement is the opening part of the tag "<tagname...>"
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("featuredprod")) {

            if (currentPersonne != null) {
                throw new IllegalStateException("already processing a prodft");
            }
            currentPersonne = new FeaturedProd();
        } else if (qName.equals("idFeat")) {
            idFeatTag = "open";
        } else if (qName.equals("dateFeat")) {
            dateFeatTag = "open";
        } else if (qName.equals("widget")) {
            widgetTag = "open";
        } else if (qName.equals("idProd")) {
            idProdTag = "open";
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("featuredprod")) {
            // we are no longer processing a <reg.../> tag
            prodft.addElement(currentPersonne);
            currentPersonne = null;
        } else if (qName.equals("idFeat")) {
            idFeatTag = "close";
        } else if (qName.equals("dateFeat")) {
            dateFeatTag = "close";
        } else if (qName.equals("widget")) {
            widgetTag = "close";
        } else if (qName.equals("idProd")) {
            idProdTag = "close";
        }
    }
    // "characters" are the text inbetween tags

    public void characters(char[] ch, int start, int length) throws SAXException {
        // we're only interested in this inside a <phone.../> tag
        if (currentPersonne != null) {
            // don't forget to trim excess spaces from the ends of the string
            if (idFeatTag.equals("open")) {
                String idprod = new String(ch, start, length).trim();
                currentPersonne.setIdFeat(Integer.parseInt(idprod));
            } else
 if (dateFeatTag.equals("open")) {
                String date = new String(ch, start, length).trim();
                currentPersonne.setDateFeat(date);
            } else
 if (idProdTag.equals("open")) {
     String idprod = new String(ch, start, length).trim();
     currentPersonne.setIdProd(Integer.parseInt(idprod));
            } else if (widgetTag.equals("open")) {
                String widget = new String(ch, start, length).trim();
                currentPersonne.setWidget(widget);
            }
        }
    }
    
}
