package com.thopv.projects.ikariam.home.presentation.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.thopv.projects.ikariam.R;
import com.thopv.projects.ikariam.data.schema.houses.ModelHouse;
import com.thopv.projects.ikariam.data.schema.units.properties.Coordinate;
import com.thopv.projects.ikariam.supports.adapters.TextWatcherAdapter;

/**
 * Created by thopv on 10/11/2017.
 */

public class HouseBuilderView {
    private View contentContainer;
    private EditText townHallLevelView, shipyardLevelView, portLevelView;
    private int townHallLevel, shipyardLevel, portLevel;
    private AlertDialog alertDialog;
    private RadioGroup radioGroup;
    private final RadioButton redTeamView;
    private final RadioButton blueTeamView;

    public interface HouseBuilderEventsListener {
        void onAcceptBuildHouse(ModelHouse modelHouse);
    }
    public HouseBuilderView(Context context, int party, Coordinate coordinate, HouseBuilderEventsListener listener) {
        contentContainer = LayoutInflater.from(context).inflate(R.layout.build_house_content,null,false);
        alertDialog = new AlertDialog.Builder(context)
                .setTitle("Tạo nhà mới")
                .setView(contentContainer)
                .setNegativeButton("", null)
                .setPositiveButton("", null)
                .setCancelable(false)
                .create();

        townHallLevelView = contentContainer.findViewById(R.id.townHallLevelLabel);
        shipyardLevelView = contentContainer.findViewById(R.id.shipyardLevelLabel);
        portLevelView = contentContainer.findViewById(R.id.portLevelLabel);
        radioGroup = contentContainer.findViewById(R.id.partyView);
        blueTeamView = radioGroup.findViewById(R.id.blueTeamView);
        redTeamView = radioGroup.findViewById(R.id.redTeamView);

        townHallLevelView.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    townHallLevel = Integer.parseInt(s.toString());
                    Log.e("Error: ", String.valueOf(townHallLevel));
                }
                catch (Exception e){
                    Log.e("Error: ", e.getMessage());
                }
            }
        });

        shipyardLevelView.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    shipyardLevel = Integer.parseInt(s.toString());
                    Log.e("Error: ", String.valueOf(shipyardLevel));
                }
                catch (Exception e){
                    Log.e("Error: ", e.getMessage());
                }
            }
        });
        portLevelView.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    portLevel = Integer.parseInt(s.toString());
                    Log.e("Error: ", String.valueOf(portLevel));
                }
                catch (Exception e){
                    Log.e("Error: ", e.getMessage());
                }
            }
        });

        if(party == 0) {
            blueTeamView.setChecked(true);
            redTeamView.setChecked(false);
        }
        else if(party == 1) {
            blueTeamView.setChecked(false);
            redTeamView.setChecked(true);
        }

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Chấp nhận", (dialog, which) ->{
            listener.onAcceptBuildHouse(new ModelHouse(shipyardLevel, portLevel, townHallLevel, coordinate, party));
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Hủy bỏ", (dialog, which) -> {
            dialog.cancel();
        });
    }
    public void show(){
        if(!alertDialog.isShowing())
            alertDialog.show();
    }
}
