import beans.ReportConfigItem;
import beans.ReportFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vguriev on 10/6/2014.
 */
public class ReportMetadataParser {

    private static final String ENTITY = "entity";
    private static final String NAME = "entity_name";
    private static final String FILTER = "filter";
    private static final String FILTER_NAME = "filter_name";
    private static final String FILTER_ORDER = "order_num";
    private static final String FILTER_OPERATOR = "operator";
    private static final String FILTER_PROMPT_FLAG = "prompt_flat";
    private static final String FILTER_AND_FLAG = "and_flag";
    private static final String FILTER_GROUP_WITH_NEXT_FLAG = "group_with_next_flag";
    private static final String FILTER_VALUES = "values";
    private static final String FILTER_VALUE = "value";

    private static final String DB_NAME = "db_name";
    private static final String DATASOURCE_ID = "datasource_id";
    private static final String OBJECT_TYPE = "object_type";
    private static final String SCEMA_ACCESS_TYPE = "schema_access_type";
    private static final String SQL_STMT = "SQL_STMT";
    private static final String ID = "id";


    private Document documentReport;
    private Document documentConfig;

    public ReportMetadataParser(String reportXml, String configXml) throws ParserConfigurationException, IOException, SAXException {

        InputStream is = this.getClass().getResourceAsStream(reportXml);
        documentReport = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);

        InputStream isc = this.getClass().getResourceAsStream(configXml);
        documentConfig = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(isc);

    }

    public ReportConfigItem read(){
        NodeList list = documentReport.getDocumentElement().getChildNodes();
        ReportConfigItem root = new ReportConfigItem();
        for (int i = 0; i < list.getLength(); i++){

            Node node = list.item(i);

            if(node instanceof Element && node.getNodeName().equals(ENTITY)) {
                setReportContent(node, root);
            }
            if(node instanceof Element && node.getNodeName().equals(FILTER)){
                addFilter(node, root);
            }

        }

        list = documentConfig.getDocumentElement().getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);

            if (node instanceof Element && node.getNodeName().equals(ENTITY)) {
                setReportContent(node, root);
            }
        }
        return root;
    }


    private void setReportContent(Node node, ReportConfigItem item){
        NodeList childNodes = node.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++) {
            Node cNode = childNodes.item(j);
            if(cNode instanceof Element) {
                String content = cNode.getLastChild().getTextContent().trim();
                if (cNode.getNodeName().equals(ID)) {
                    if(!item.getId().equals(content)){
                        continue;
                    }
                }
                if (cNode.getNodeName().equals(NAME)) {
                    if(item.getId() == null) {
                        item.setId(content);
                    } else {
                        item.setName(content);
                    }
                }
                if (cNode.getNodeName().equals(DB_NAME)) {
                    item.setDbName(content);
                }
                if (cNode.getNodeName().equals(DATASOURCE_ID)) {
                    item.setDatasourceId(content);
                }
                if (cNode.getNodeName().equals(OBJECT_TYPE)) {
                    item.setObjectType(content);
                }
                if (cNode.getNodeName().equals(SCEMA_ACCESS_TYPE)) {
                    item.setSchemaAccessType(content);
                }
                if (cNode.getNodeName().equals(SQL_STMT)) {
                    item.setSqlStatement(content);
                }
            }
        }
    }

    private void addFilter(Node node, ReportConfigItem item){
        ReportFilter filter = new ReportFilter();
        item.getFilters().add(filter);
        NodeList childNodes = node.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++) {
            Node cNode = childNodes.item(j);
            if (cNode instanceof Element) {

                String content = cNode.getLastChild().getTextContent().trim();
                if (cNode.getNodeName().equals(FILTER_NAME)) {
                    filter.setName(content);
                }
                if (cNode.getNodeName().equals(FILTER_ORDER)) {
                    filter.setOrder(Integer.parseInt(content));
                }
                if (cNode.getNodeName().equals(FILTER_OPERATOR)) {
                    filter.setOperator(content);
                }
                if (cNode.getNodeName().equals(FILTER_PROMPT_FLAG)) {
                    filter.setPrompt(Boolean.parseBoolean(content));
                }
                if (cNode.getNodeName().equals(FILTER_AND_FLAG)) {
                    filter.setAndFlag(Boolean.parseBoolean(content));
                }
                if (cNode.getNodeName().equals(FILTER_GROUP_WITH_NEXT_FLAG)) {
                    filter.setGroupWithNext(Boolean.parseBoolean(content));
                }
                if (cNode.getNodeName().equals(FILTER_VALUES)) {
                    addValuesToFilter(cNode, filter);
                }
            }
        }
    }



    private void addValuesToFilter(Node node, ReportFilter filter){
        NodeList childNodes = node.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++) {
            Node cNode = childNodes.item(j);
            if (cNode instanceof Element) {
                String content = cNode.getLastChild().getTextContent().trim();
                if (cNode.getNodeName().equals(FILTER_VALUE)) {
                    filter.getValues().add(content);
                }
            }
        }

    }

}
