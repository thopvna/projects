package com.thopv.projects.ikariam.home.presentation.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thopv.projects.ikariam.R;
import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.data.schema.armies.MovingTroop;
import com.thopv.projects.ikariam.data.schema.units.units.Unit;
import com.thopv.projects.ikariam.data.schema.units.units.UnitFactory;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;

import java.util.LinkedList;
import java.util.List;

public class ShowUnitsAdapter extends RecyclerView.Adapter<ShowUnitsAdapter.MyViewholder> {
        private List<BaseTroop> troops;
        public ShowUnitsAdapter(List<BaseTroop> troops) {
            this.troops = troops;
        }
        public static ShowUnitsAdapter createByMovingTroops(List<MovingTroop> troops){
            List<BaseTroop> baseTroops = new LinkedList<>();
            for (MovingTroop troop : troops) {
                baseTroops.add(new BaseTroop(troop.getUnitName(), troop.getAmount()));
            }
            return new ShowUnitsAdapter(baseTroops);
        }
        public static ShowUnitsAdapter createByTroops(List<HomeTroop> homeTroops){
            List<BaseTroop> baseTroops = new LinkedList<>();
            for (HomeTroop homeTroop : homeTroops) {
                baseTroops.add(new BaseTroop(homeTroop.getUnitName(), homeTroop.getAmount()));
            }
            return new ShowUnitsAdapter(baseTroops);
        }

        @Override
        public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_units_cardview, parent, false);
            return new MyViewholder(view);
        }

        @Override
        public void onBindViewHolder(MyViewholder holder, int position) {
            BaseTroop troop = troops.get(position);
            Unit unit = UnitFactory.getUnit(troop.getUnitName());
            int amount = troop.getAmount();

            holder.setUnitAmount(unit, amount);
        }

        @Override
        public int getItemCount() {
            return troops.size();
        }

        public class MyViewholder extends RecyclerView.ViewHolder{
            private ImageView unitIconView;
            private View view;
            private TextView unitAmountView;
            private TextView unitNameView;
            public MyViewholder(View itemView) {
                super(itemView);
                this.view = itemView;
                unitIconView =  itemView.findViewById(R.id.unitIcon);
                unitAmountView =  itemView.findViewById(R.id.unitAmount);
                unitNameView =  itemView.findViewById(R.id.unitName);
            }
            public void setUnitAmount(Unit unit, int amount){
                unitIconView.setImageDrawable(view.getResources().getDrawable(unit.getDrawable()));
                String amountString = "x" + amount;
                unitAmountView.setText(amountString);
                unitNameView.setText(unit.getName());
            }
        }
    }