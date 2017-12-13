package com.thopv.projects.ikariam.fight.presentation.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thopv.projects.ikariam.R;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;
import com.thopv.projects.ikariam.data.schema.units.units.Unit;
import com.thopv.projects.ikariam.data.schema.units.units.UnitFactory;

/**
 * Created by thopv on 11/14/2017.
 */

public class TroopHolder extends RecyclerView.ViewHolder {
    private ImageView troopIcon;
    private TextView amountView;
    public TroopHolder(View itemView) {
        super(itemView);
        troopIcon = itemView.findViewById(R.id.unitIcon);
        amountView = itemView.findViewById(R.id.unitAmount);
    }
    public void setTroopInform(BaseTroop baseTroop){
        Unit unit = UnitFactory.getUnit(baseTroop.getUnitName());
        Picasso.with(troopIcon.getContext()).load(unit.getDrawable()).fit().into(troopIcon);
        amountView.setText(String.valueOf(baseTroop.getAmount()));
    }
}
