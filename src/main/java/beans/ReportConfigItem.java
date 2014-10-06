package beans;

import java.util.ArrayList;

/**
 * Created by vguriev on 10/6/2014.
 */
public class ReportConfigItem {
    private String id;
    private String name;
    private String dbName;
    private String category;
    private String datasourceId;
    private String objectType;
    private String schemaAccessType;
    private String sqlStatement;
    private String schema;
    private String configItem;

    private ArrayList<ReportFilter> filters = new ArrayList<ReportFilter>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getSchemaAccessType() {
        return schemaAccessType;
    }

    public void setSchemaAccessType(String schemaAccessType) {
        this.schemaAccessType = schemaAccessType;
    }

    public String getSqlStatement() {
        return sqlStatement;
    }

    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public ArrayList<ReportFilter> getFilters() {
        return filters;
    }

    public void setFilters(ArrayList<ReportFilter> filters) {
        this.filters = filters;
    }

    public String getConfigItem() {
        return configItem;
    }

    public void setConfigItem(String configItem) {
        this.configItem = configItem;
    }
}
