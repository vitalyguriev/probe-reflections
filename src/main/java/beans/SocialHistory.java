package beans;

import java.util.ArrayList;

/**
 * Created by vguriev on 5/6/2014.
 */
@DBTableMap (tableName="pat_history")
public class SocialHistory extends BaseTableInfo {
    private int intValue;

    private String stringValue;

    private ArrayList<PatientAddress> addresses;

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public ArrayList<PatientAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<PatientAddress> addresses) {
        this.addresses = addresses;
    }
}
