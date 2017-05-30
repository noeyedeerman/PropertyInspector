package sit374_team17.propertyinspector;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAutoGeneratedKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;


@DynamoDBTable(tableName = "Photos")
public class DB_photos {
    private String PropertyId;
    private String id;
    private String PicDetails;

    @DynamoDBHashKey(attributeName = "PhotoID")
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @DynamoDBAttribute(attributeName = "PropertyID")
    public String getPropertyId() {
        return PropertyId;
    }

    public void setPropertyId(String propertyId) {
        PropertyId = propertyId;
    }
    @DynamoDBAttribute(attributeName = "Photo Description")
    public String getPicDetails() {
        return PicDetails;
    }

    public void setPicDetails(String picDetails) {
        PicDetails = picDetails;
    }
}
