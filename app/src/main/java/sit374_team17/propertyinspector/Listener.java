package sit374_team17.propertyinspector;

/**
 * Created by callu on 1/05/2017.
 */

public interface Listener {
    void onHomeInteraction();
    void onItemClicked(DB_Property property);
    void onSaveProperty();
    void onBackPressed();
    void addToBackStack();

    void onSaveComment();
    // void onPropertySelected();
}
