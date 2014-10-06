import beans.ReportItem;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by vguriev on 9/24/2014.
 */
public class ReportsParser {


    XMLEventReader reader;


    public ReportItem read(String path) throws FileNotFoundException, XMLStreamException {
        ReportItem root = null;
        XMLInputFactory factory = XMLInputFactory.newFactory();
        InputStream is = this.getClass().getResourceAsStream(path);
        reader = factory.createXMLEventReader(is);
        while(reader.hasNext()){
           XMLEvent event = reader.nextEvent();
           if(event.isStartElement()) {
               StartElement se = event.asStartElement();
               //System.out.println(se.getName());
               if(se.getName().getLocalPart() == "entity") {
                   root = parseEntity();
                   //parseEntity(root);
//                   System.out.println(se.getName());
//                   if(se.getName().getLocalPart().equals("name")){
//                       String name = reader.nextEvent().asCharacters().getData();
//
//                       System.out.println(name);
//                   }
               }
           }
        }
        return root;
    }

    private ReportItem parseEntity() throws XMLStreamException {
        ReportItem root = new ReportItem();
        System.out.println("Parsing entity: ");

        StartElement se = getStartElement();
        if(null == se){
            return root;
        }
        if(se.getName().getLocalPart().equals("name")){
            String name = reader.nextEvent().asCharacters().getData();
            root.setName(name);
            System.out.println(name);
        }
        if(getStartElement().getName().getLocalPart().equals("leaf_flag")){
            String leaf = reader.nextEvent().asCharacters().getData();
            root.setLeaf(Boolean.parseBoolean(leaf));
            System.out.println(leaf);
        }
        if(getStartElement().getName().getLocalPart().equals("readonly_flag")){
            String ro = reader.nextEvent().asCharacters().getData();
            root.setReadOnly(Boolean.parseBoolean(ro));
            System.out.println(ro);
        }
        se = getStartElement();
        if(se != null && se.getName().getLocalPart().equals("entity")){
            System.out.println("*********** parse children entity");
            ReportItem node = parseEntity();
            if(!node.isLeaf()){
                root.getChildren().add(node);
            } else {
                return node;
            }

        }
        return root;

    }

    private StartElement getStartElement() throws XMLStreamException {
        XMLEvent event = reader.nextEvent();
        while(!event.isStartElement()){
            if(!reader.hasNext()){
                return null;
            }
            event = reader.nextEvent();
        }
        return event.asStartElement();
    }
}
