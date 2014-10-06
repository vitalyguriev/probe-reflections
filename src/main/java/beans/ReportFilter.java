package beans;

import java.util.ArrayList;

/**
 * Created by vguriev on 10/6/2014.
 */
public class ReportFilter {
    private String name;
    private int order;
    private String operator;
    private boolean prompt;
    private boolean andFlag;
    private boolean groupWithNext;
    private ArrayList<String> values = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public boolean isPrompt() {
        return prompt;
    }

    public void setPrompt(boolean prompt) {
        this.prompt = prompt;
    }

    public boolean isAndFlag() {
        return andFlag;
    }

    public void setAndFlag(boolean andFlag) {
        this.andFlag = andFlag;
    }

    public boolean isGroupWithNext() {
        return groupWithNext;
    }

    public void setGroupWithNext(boolean groupWithNext) {
        this.groupWithNext = groupWithNext;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }
}
