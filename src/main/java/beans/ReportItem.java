package beans;

import java.util.ArrayList;

/**
 * Created by vguriev on 9/24/2014.
 */
public class ReportItem {

    private String name;
    private boolean leaf;
    private boolean readOnly;
    private ArrayList<ReportItem> children = new ArrayList<ReportItem>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public ArrayList<ReportItem> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ReportItem> children) {
        this.children = children;
    }

    public boolean hasChildren(){
        return !leaf && children.size() > 0;
    }
}
