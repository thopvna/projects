package com.thopv.projects.ikariam.data.schema.units.units;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thopv.projects.ikariam.R;
import com.thopv.projects.ikariam.data.schema.units.properties.Weapon;

import java.util.List;

/**
 * Created by thopv on 10/31/2017.
 */

public class UnitAttributesDialog {
    private Context context;
    private AlertDialog alertDialog;
    public UnitAttributesDialog(Context context, String unitName){
        this.context = context;
        View container = LayoutInflater.from(context).inflate(R.layout.unit_information_view, null, false);
        ImageView unitIconView = container.findViewById(R.id.unitIconView);
        TextView unitNameView = container.findViewById(R.id.unitNameView);
        TextView weaponValueView = container.findViewById(R.id.weaponValueView);
        TextView armorValueView = container.findViewById(R.id.armorValueView);
        TextView hitpointsValueView = container.findViewById(R.id.hitpointsValueView);
        TextView shipTypeValueView = container.findViewById(R.id.shipTypeValueView);
        TextView sizeValueView = container.findViewById(R.id.sizeValueView);

        Unit unit = UnitFactory.getUnit(unitName);

        StringBuilder weaponInform = new StringBuilder();

        List<Weapon> weapons= unit.getUnitAttributes().getWeapons();

        for(int i =0; i < weapons.size(); i++){
            Weapon weapon = weapons.get(i);
            weaponInform.append(weapon.getName()).append("(")
                    .append("Dame: ").append(weapon.getDame())
                    .append(", Accuracy: ").append(weapon.getAccuracy())
                    .append(", Munitions: ").append(weapon.getMunition())
                    .append(")");
            if(i != weapons.size() - 1) {
                    weaponInform.append("\n");
            }
        }

        Picasso.with(context).load(unit.getDrawable()).fit().into(unitIconView);
        unitNameView.setText(unitName);
        weaponValueView.setText(weaponInform.toString());
        armorValueView.setText(String.valueOf(unit.getUnitAttributes().getArmour()));
        hitpointsValueView.setText(String.valueOf(unit.getUnitAttributes().getHitpoints()));
        shipTypeValueView.setText(unit.getShipType().toString());
        sizeValueView.setText(String.valueOf(unit.getUnitAttributes().getSize()));

        alertDialog = new AlertDialog.Builder(context)
                .setTitle("Thông tin thuộc tính Tàu ")
                .setView(container)
                .setCancelable(false)
                .setNegativeButton("Đóng", (a,b ) -> { a.cancel();})
                .create();
    }
    public void show(){
        if(alertDialog != null && !alertDialog.isShowing())
            alertDialog.show();
    }
}
