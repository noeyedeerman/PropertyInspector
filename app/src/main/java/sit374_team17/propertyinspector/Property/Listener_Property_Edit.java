package sit374_team17.propertyinspector.Property;

import sit374_team17.propertyinspector.Note.Note;

/**
 * Created by callu on 25/08/2017.
 */

public interface Listener_Property_Edit {
    void onContinue();
    void save();
    void setDetails_1(Property property);
    void setDetails_2(Property property);
    void goBackTo_Details();
    void goBackTo_Address();
    void goTo_NoteEditActivity(Note note, String openCamera);

}
