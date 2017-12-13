package com.thopv.projects.ikariam.fight.presentation.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thopv.projects.ikariam.R;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;

import java.util.LinkedList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/14/2017.
 */

public class ReverseTroopsAdapter extends RecyclerView.Adapter<TroopHolder> {
    private List<BaseTroop> attackTroops;
    private List<BaseTroop> extraTroops;
    private List<BaseTroop> troops;
    public ReverseTroopsAdapter(){
        attackTroops = new LinkedList<>();
        extraTroops = new LinkedList<>();
        troops = new LinkedList<>();
    }

    public void setTroops(List<BaseTroop> attackTroops) {
        this.attackTroops = attackTroops != null ? attackTroops : new LinkedList<>();
        mergeTroops();
        notifyDataSetChanged();
    }
    public void setExtraTroops(List<BaseTroop> troops){
        this.extraTroops = troops != null ? troops : new LinkedList<>();
        mergeTroops();
        notifyDataSetChanged();
    }
    private void mergeTroops(){
        troops = new LinkedList<>();
        troops.addAll(attackTroops);
        for(BaseTroop extraTroop : extraTroops){
            if(!troops.contains(extraTroop)){
                troops.add(extraTroop);
            }
            else{
                BaseTroop old = troops.get(troops.indexOf(extraTroop));
                old.merge(extraTroop);
            }
        }
    }

    @Override
    public TroopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reverse_ship_cardview, parent, false);
        return new TroopHolder(view);
    }

    @Override
    public void onBindViewHolder(TroopHolder holder, int position) {
        BaseTroop troop = troops.get(position);
        holder.setTroopInform(troop);
    }

    @Override
    public int getItemCount() {
        return troops.size();
    }
}
