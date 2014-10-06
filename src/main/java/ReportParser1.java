import beans.ReportItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vguriev on 9/24/2014.
 */
public class ReportParser1 {

    private static final String ENTITY = "entity";
    private static final String NAME = "name";
    private static final String LEAF = "leaf_flag";
    private static final String READ_ONLY = "readonly_flag";

    public ReportItem read(String path) throws IOException, XMLStreamException, ParserConfigurationException, SAXException {
        InputStream is = this.getClass().getResourceAsStream(path);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(is);

        NodeList list = document.getDocumentElement().getChildNodes();

        ReportItem root = new ReportItem();
        for (int i = 0; i < list.getLength(); i++){
            Node node = list.item(i);
            parseEntity(node, root);
        }

        return root;
    }

    private void parseEntity(Node node, ReportItem root){

        NodeList childNodes = node.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++){
            Node cNode = childNodes.item(j);
            if(cNode instanceof Element){
                if(!cNode.getNodeName().equals(ENTITY)){
                    setReportContent(cNode, root);
                } else {
                    ReportItem item = new ReportItem();
                    parseEntity(cNode, item);
                    root.getChildren().add(item);
                }
            }
        }
    }

    private void setReportContent(Node cNode, ReportItem item){
        String content = cNode.getLastChild().getTextContent().trim();
        if(cNode.getNodeName().equals(NAME)){
            item.setName(content);
        }
        if(cNode.getNodeName().equals(LEAF)){
            item.setLeaf(Boolean.parseBoolean(content));
        }
        if(cNode.getNodeName().equals(READ_ONLY)){
            item.setReadOnly(Boolean.parseBoolean(content));
        }
    }


}
