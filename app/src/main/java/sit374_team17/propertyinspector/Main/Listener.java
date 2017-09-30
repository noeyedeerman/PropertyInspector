package sit374_team17.propertyinspector.Main;

import sit374_team17.propertyinspector.Note.Note;
import sit374_team17.propertyinspector.Property.Property;

/**
 * Created by callu on 1/05/2017.
 */

public interface Listener {
    void onHomeInteraction();
    void onPropertyClicked(Property property);
    void onNoteClicked(Note note);
    void goTo_NoteEditActivity(Note note, String openCamera);
    void onSaveProperty();
    void onBackPressed();
    void addToBackStack();
void viewPagerCount(int count);
    void popBackStack(boolean bool);
    void onSaveComment();
    // void onPropertySelected();
    void refreshNotes();
}
