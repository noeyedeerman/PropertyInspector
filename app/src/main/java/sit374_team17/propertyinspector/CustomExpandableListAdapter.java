package sit374_team17.propertyinspector;

/**
 * Created by rileymills on 24/5/17.
 */

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextWatcher;
import java.util.ArrayList;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.RatingBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.widget.LinearLayout;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    private ArrayList<EditText> editTextList = new ArrayList<EditText>();

    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        ImageButton commentButton = (ImageButton) convertView
                .findViewById(R.id.commentButton);
        commentButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                builder.setMessage("Write a comment you wish to Flag: ")
                        .setCancelable(false)
                        .setPositiveButton("Post", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        })
                        .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                final EditText input = new EditText(context);
                alert.setView(input);
                alert.show();

            }
        });



        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
