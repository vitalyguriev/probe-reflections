package beans;

import java.util.ArrayList;

/**
 * Created by vguriev on 5/6/2014.
 */
@DBTableMap (tableName="pat_info")
public class PatientInfo extends BaseTableInfo {

    private int patientid;

    private String stringValue;

    private ArrayList<PatientAddress> addresses = new ArrayList<PatientAddress>();

    public int getPatientID() {
        return patientid;
    }

    public void setPatientID(int patientid) {
        this.patientid = patientid;
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
