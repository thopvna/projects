package com.thopv.projects.ikariam.fight.presentation.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thopv.projects.ikariam.R;
import com.thopv.projects.ikariam.supports.utils.ViewUtils;
import com.thopv.projects.ikariam.data.schema.houses.House;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/15/2017.
 */
public class HouseSelectAdapter extends RecyclerView.Adapter<HouseSelectAdapter.MyViewholder>{
    private List<House> houses;
    public HouseSelectAdapter(){
        houses = new LinkedList<>();
    }

    public void setHouses(List<House> houses) {
        this.houses = houses != null ? houses : new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public HouseSelectAdapter.MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.house_selection_cardview, parent, false);
        return new MyViewholder(view);
    }
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(HouseSelectAdapter.MyViewholder holder, int position) {
        House house = houses.get(position);
        holder.setHouse(house, itemClickListener);
    }

    @Override
    public int getItemCount() {
        return houses.size();
    }
    public interface ItemClickListener{
        void onClicked(House house);
    }
    public class MyViewholder extends RecyclerView.ViewHolder{
        ImageView houseImageView;
        TextView houseNameView;
        TextView townHallView;
        TextView shipyardView;
        TextView portView;
        TextView distanceView;
        View view;
        public MyViewholder(View itemView) {
            super(itemView);
            view = itemView;
            houseImageView = itemView.findViewById(R.id.houseImageView);
            houseNameView = itemView.findViewById(R.id.houseNameView);
            townHallView = itemView.findViewById(R.id.townHallView);
            shipyardView = itemView.findViewById(R.id.shipyardView);
            portView = itemView.findViewById(R.id.portView);
            distanceView = itemView.findViewById(R.id.distanceView);
        }

        public void setHouse(House house, ItemClickListener listener){
            String townHallLevel = "TownHall: cấp " + house.getTownHall().getLevel();
            String shipyardLevel = "Shipyard: cấp " + house.getShipyard().getLevel();
            String portLevel = "Port: cấp " + house.getPort().getLevel();

            houseImageView.setImageDrawable(view.getResources().getDrawable(house.getMeDrawableId()));
            houseNameView.setText(house.isBlue() ? "Blue Team" : "Red Team");
            townHallView.setText(townHallLevel);
            shipyardView.setText(shipyardLevel);
            portView.setText(portLevel);
           // distanceView.setText(distance);
            view.setOnClickListener((view) -> {
                ViewUtils.disableView(view);
                if(listener != null)
                    listener.onClicked(house);
            });
        }
    }
}