package sit374_team17.propertyinspector.Property;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAutoGeneratedKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;

/**
 * Created by lenovo on 14/09/2017.
 */

@DynamoDBTable(tableName = "InspectionCriteria")
public class Inspection {

    private String PropertyId;
    private List<String>Attic_Basement;
    private List <String>Backyard;
    private List<String> Bathroom;
    private List<String> Exterior;
    private List<String> Grounds;
    private List<String> Heating_Cooling;
    private List<String> Interior;
    private List<String> Kitchen;
    private List<String> Miscellaneous;
    private List<String> Plumbing_Electrical;
    private List<String> Roof;
    private List<String> Structure;
    private List<String> Windows_Doors_Trim;

    @DynamoDBHashKey(attributeName = "PropertyID")
    @DynamoDBAutoGeneratedKey
    public String getPropertyId() {
        return PropertyId;
    }
    @DynamoDBAttribute(attributeName = "Attic/Basement")
    public List<String> getAttic_Basement() {
        return Attic_Basement;
    }
    @DynamoDBAttribute(attributeName = "Backyard")
    public List<String> getBackyard() {
        return Backyard;
    }
    @DynamoDBAttribute(attributeName = "Bathroom")
    public List<String> getBathroom() {
        return Bathroom;
    }
    @DynamoDBAttribute(attributeName = "Exterior")
    public List<String> getExterior() {
        return Exterior;
    }
    @DynamoDBAttribute(attributeName = "Grounds")
    public List<String> getGrounds() {
        return Grounds;
    }
    @DynamoDBAttribute(attributeName = "Heating/Cooling")
    public List<String> getHeating_Cooling() {
        return Heating_Cooling;
    }
    @DynamoDBAttribute(attributeName = "Interior")
    public List<String> getInterior() {
        return Interior;
    }
    @DynamoDBAttribute(attributeName = "Kitchen")
    public List<String> getKitchen() {
        return Kitchen;
    }
    @DynamoDBAttribute(attributeName = "Miscellaneous")
    public List<String> getMiscellaneous() {
        return Miscellaneous;
    }
    @DynamoDBAttribute(attributeName = "Plumbing/Electrical")
    public List<String> getPlumbing_Electrical() {
        return Plumbing_Electrical;
    }
    @DynamoDBAttribute(attributeName = "Roof")
    public List<String> getRoof() {
        return Roof;
    }
    @DynamoDBAttribute(attributeName = "Structure")
    public List<String> getStructure() {
        return Structure;
    }
    @DynamoDBAttribute(attributeName = "Windows/Doors/Trim")
    public List<String> getWindows_Doors_Trim() {
        return Windows_Doors_Trim;
    }

    public void setPropertyId(String propertyId) {
        PropertyId = propertyId;
    }

    public void setAttic_Basement(List<String> attic_Basement) {
        Attic_Basement = attic_Basement;
    }

    public void setBackyard(List<String> backyard) {
        Backyard = backyard;
    }

    public void setBathroom(List<String> bathroom) {
        Bathroom = bathroom;
    }

    public void setExterior(List<String> exterior) {
        Exterior = exterior;
    }

    public void setGrounds(List<String> grounds) {
        Grounds = grounds;
    }

    public void setHeating_Cooling(List<String> heating_Cooling) {
        Heating_Cooling = heating_Cooling;
    }

    public void setInterior(List<String> interior) {
        Interior = interior;
    }

    public void setKitchen(List<String> kitchen) {
        Kitchen = kitchen;
    }

    public void setMiscellaneous(List<String> miscellaneous) {
        Miscellaneous = miscellaneous;
    }

    public void setPlumbing_Electrical(List<String> plumbing_Electrical) {
        Plumbing_Electrical = plumbing_Electrical;
    }

    public void setRoof(List<String> roof) {
        Roof = roof;
    }

    public void setStructure(List<String> structure) {
        Structure = structure;
    }

    public void setWindows_Doors_Trim(List<String> windows_Doors_Trim) {
        Windows_Doors_Trim = windows_Doors_Trim;
    }

    public  interface Actions{
        public void addLikeDislike(int position,int values,String conditions);
    }
}
