import beans.BaseTableInfo;
import beans.DBTableMap;
import beans.PatientAddress;
import beans.PatientChartMergeConflictsInfo;
import beans.PatientInfo;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vguriev on 5/6/2014.
 */
public class MergeChartTest {

    private PatientInfo p1;
    private PatientInfo p2;
    private ArrayList<PatientChartMergeConflictsInfo> conflicts = new ArrayList<PatientChartMergeConflictsInfo>();

    private void createPatients(){
        p1 = new PatientInfo();
        p2 = new PatientInfo();

        p1.setPatientID(1);
        p2.setPatientID(2);
        p1.setStringValue("1");
        p2.setStringValue("2");

        PatientAddress pa = new PatientAddress();
        pa.setStreet("street1");
        p1.getAddresses().add(pa);

        pa = new PatientAddress();
        pa.setStreet("street2");
        p2.getAddresses().add(pa);
    }

    private void seeDiff() throws InvocationTargetException, IllegalAccessException {
        Field[] fields = p1.getClass().getDeclaredFields();
        Object fieldValue1;
        Object fieldValue2;
        for (Field field : fields){
            Class fieldType = field.getType();

            fieldValue1 = invokeGetter(field, p1);
            fieldValue2 = invokeGetter(field, p2);

            //based on field type, do a comparison
            //if different, write result to the conflictInfo bean

            if(!fieldValue1.equals(fieldValue2) && !(fieldValue1 instanceof Iterable<?>)){
                PatientChartMergeConflictsInfo c = new PatientChartMergeConflictsInfo();
                c.setFieldName(extractFieldName(field));
                c.setFieldValueChart1(String.valueOf(fieldValue1));
                c.setFieldValueChart2(String.valueOf(fieldValue2));
                c.setFieldCaption(getFieldCaption(p1, c.getFieldName()));
                conflicts.add(c);
            }

            if((fieldValue1 instanceof Iterable<?>)){
                //do recursion here
            }
        }


    }

    private static String getFieldCaption(BaseTableInfo bti, String string){
        String result = null;
        Annotation [] ann = bti.getClass().getDeclaredAnnotations();
        for (Annotation a : ann){
            if(a instanceof DBTableMap){
                String tableName = ((DBTableMap) a).tableName();
                //with given table name and field name use 'audit_field_name' to fetch the localized caption for a field
                result = tableName + "_" + string;
            }
        }


        return result;
    }

    private static String extractFieldName(Field field) throws InvocationTargetException, IllegalAccessException{
        String fieldName = field.getName().replace("_", "");
        return fieldName.toLowerCase();
    }

    private static Object invokeGetter(Field field, Object obj) throws InvocationTargetException, IllegalAccessException {
        String fieldName = field.getName().replace("_", "");
        for (Method method : obj.getClass().getMethods()) {
            if ((method.getName().startsWith("get")) && (method.getName().length() == (fieldName.length() + 3))) {
                if (method.getName().toLowerCase().endsWith(fieldName.toLowerCase())) {
                    return method.invoke(obj);
                }
            }
        }
        return null;
    }

    private static void invokeSetter(Field field, Object obj, Object value) throws InvocationTargetException, IllegalAccessException {
        String fieldName = field.getName().replace("_", "");
        for (Method method : obj.getClass().getMethods()) {
            if ((method.getName().startsWith("set")) && (method.getName().length() == (fieldName.length() + 3))) {
                if (method.getName().toLowerCase().endsWith(fieldName.toLowerCase())) {
                    method.invoke(obj, value);
                }
            }
        }
    }


    private void applyDiff() throws InvocationTargetException, IllegalAccessException {
        //first fetch patients from the DB

        for (PatientChartMergeConflictsInfo bean : conflicts){
            if(bean.getFieldName().equals("patientid")){
                // get patientid value
                Field[] fields = PatientInfo.class.getDeclaredFields();
                Object patientID;
                for (Field field : fields){
                    Class fieldType = field.getType();
                    String fieldName = extractFieldName(field);
                    if(fieldName.equals("patientid")){
                        patientID = invokeGetter(field, p1);
                        System.out.println("PatientID = " + patientID);
                        //use patient id to fetch PatientInfo or Social history instance

                        break;
                    }



                }

            }
        }

        //code below is not fully implemented, so don't go there for now
        conflicts = new ArrayList<PatientChartMergeConflictsInfo>();

        //we here reuse created p2 and p1 records
        //now with both patient records set do the merge
        for (PatientChartMergeConflictsInfo bean : conflicts){
            String fieldName = bean.getFieldName();
            Field[] fields = PatientInfo.class.getDeclaredFields();
            Object patientID;
            for (Field field : fields){
                //look for getters/setters with names like get+fieldName or set+fieldName
                //when field is found get the value

                Object fieldValue1 = invokeGetter(field, p1);
                //and merge it to p2
                invokeSetter(field, p2, fieldValue1);
            }


        }


    }

    public static void main(String [] args) throws InvocationTargetException, IllegalAccessException, IOException, XMLStreamException, ParserConfigurationException, SAXException {
//        ReportParser1 parser = new ReportParser1();
//        parser.read("test.xml");


        ReportMetadataParser parser = new ReportMetadataParser("report1.xml", "WebReports.xml");
        parser.read();
////////////////////////////////////////////////////////////
//       MergeChartTest svc = new MergeChartTest();
//        svc.createPatients();
//        svc.seeDiff();
//
//
//        //did something on UI side, diff came back
//
//        svc.applyDiff();

    }

}
