package beans;

/**
 * Created by vguriev on 5/6/2014.
 */
@DBTableMap (tableName="pat_address")
public class PatientAddress extends BaseTableInfo {

    private String street;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
