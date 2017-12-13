package com.thopv.projects.ikariam.fight.presentation.views;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.thopv.projects.ikariam.R;
import com.thopv.projects.ikariam.supports.utils.ViewUtils;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;
import com.thopv.projects.ikariam.data.schema.units.units.Unit;
import com.thopv.projects.ikariam.data.schema.units.units.UnitFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by thopv on 11/15/2017.
 */

public class UnitsAmountSelectAdapter extends RecyclerView.Adapter<UnitsAmountSelectAdapter.MyViewholder>{
    private List<BaseTroop> troops;
    private Map<String, Integer> unitsAmount;
    public UnitsAmountSelectAdapter(){
        troops = new ArrayList<>();
        unitsAmount = new HashMap<>();
    }
    public UnitsAmountSelectAdapter(List<BaseTroop> troops){
        this.troops = troops == null ? new LinkedList<>() : troops;
        unitsAmount = new HashMap<>();
    }

    public void setTroops(List<BaseTroop> troops) {
        this.troops = troops != null ? troops : new ArrayList<>();
        notifyDataSetChanged();
    }

    public Map<String, Integer> getUnitsAmount() {
        for(String uN : unitsAmount.keySet()){
            Log.e("UnitsAmountSelect: ", "Unit name = " + uN + ", amount = " + unitsAmount.get(uN));
        }
        return unitsAmount;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_amount_selection_cardview_with_seekbar, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
        BaseTroop troop = troops.get(position);

        holder.setTroop(troop, unitsAmount.containsKey(troop.getUnitName()) ? unitsAmount.get(troop.getUnitName()) : 0);
    }

    @Override
    public int getItemCount() {
        return troops.size();
    }
    class MyViewholder extends RecyclerView.ViewHolder{
        ImageView unitIconView;
        TextView unitNameView, maxAmountView ;
        TextView amountView;
        SeekBar seekBarView;
        View view;
        BaseTroop baseTroop;
        public MyViewholder(View itemView) {
            super(itemView);
            this.view = itemView;
            unitIconView =  itemView.findViewById(R.id.unitIconView);
            unitNameView =  itemView.findViewById(R.id.unitNameView);
            amountView = itemView.findViewById(R.id.amountView);
            maxAmountView = itemView.findViewById(R.id.maxAmountView);

            seekBarView = itemView.findViewById(R.id.seekBarView);
            seekBarView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(baseTroop != null && fromUser){
                        if(progress == 0 && unitsAmount.containsKey(baseTroop.getUnitName())) {
                            unitsAmount.remove(baseTroop.getUnitName());
                            ViewUtils.TextViewUtils.setTextBefore(500, "0", amountView);
                        }
                        else {
                            unitsAmount.put(baseTroop.getUnitName(), progress);
                            ViewUtils.TextViewUtils.setTextBefore(500, String.valueOf(progress), amountView);
                        }
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
        public void setTroop(BaseTroop troop, int currentAmount){
            this.baseTroop = troop;
            Unit unit = UnitFactory.getUnit(troop.getUnitName());
            unitIconView.setImageDrawable(view.getResources().getDrawable(unit.getDrawable()));
            unitNameView.setText(unit.getName());

            int maxAmount = troop.getAmount();

            seekBarView.setMax(maxAmount);
            seekBarView.setProgress(currentAmount);

            amountView.setText(String.valueOf(currentAmount));
            maxAmountView.setText(String.valueOf(maxAmount));
        }
    }
}
