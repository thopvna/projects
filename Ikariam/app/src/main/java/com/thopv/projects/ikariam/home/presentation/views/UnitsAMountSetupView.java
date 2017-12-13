package com.thopv.projects.ikariam.home.presentation.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.thopv.projects.ikariam.R;
import com.thopv.projects.ikariam.supports.adapters.TextWatcherAdapter;
import com.thopv.projects.ikariam.supports.dialogs.TextInputDialog;
import com.thopv.projects.ikariam.supports.utils.ViewUtils;
import com.thopv.projects.ikariam.data.source.daos.UnitSetupPatternDAO;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;
import com.thopv.projects.ikariam.data.schema.units.units.Unit;
import com.thopv.projects.ikariam.data.schema.units.units.UnitFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by thopv on 10/27/2017.
 */

public class UnitsAMountSetupView {
    private View container;
    private RecyclerView unitsListView;
    private AlertDialog alertDialog;
    private UnitsAdditionalAdapter adapter;
    private Context context;
    private final UnitSetupPatternDAO unitSetupPatternDAO;

    /**
     */
    public interface SelectionUnitsAmountCallback{
        void onAcceptAdditionalUnits(List<BaseTroop> troops);
    }
    public UnitsAMountSetupView(String title, Context context, SelectionUnitsAmountCallback callback){
        this.context = context;
        unitSetupPatternDAO = UnitSetupPatternDAO.getInstance(context);
        container = LayoutInflater.from(context).inflate(R.layout.units_select_with_edittext, null, false);
        unitsListView = container.findViewById(R.id.unitAmountSelectionListView);
        adapter = new UnitsAdditionalAdapter();
        unitsListView.setAdapter(adapter);
        unitsListView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false));
        alertDialog = new AlertDialog.Builder(container.getContext())
                .setTitle(title)
                .setView(container)
                .setPositiveButton("", null)
                .setNegativeButton("", null)
                .setCancelable(false)
                .create();
        setupPatternSupport();
        setupButtonListener(callback);
    }

    private void setupPatternSupport() {
        container.findViewById(R.id.patternView).setOnClickListener(v -> {
            ViewUtils.disableView(v);
            Map<String, List<BaseTroop>> patterns = unitSetupPatternDAO.fetchAll();
            if(patterns.size() > 0) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                Menu menu = popupMenu.getMenu();

                for (String patternName : patterns.keySet()) {
                    int index = Integer.parseInt(patternName.substring(0, 1));
                    menu.add(0, index, index, patternName);
                }
                popupMenu.setOnMenuItemClickListener(item -> {
                    for (String patternName : patterns.keySet()) {
                        int index = Integer.parseInt(patternName.substring(0, 1));
                        if (index == item.getItemId()) {
                            List<BaseTroop> pattern = patterns.get(patternName);
                            setData(pattern);
                        }
                    }
                    return true;
                });
                popupMenu.show();
            }
            else{
                Toast.makeText(context, "Không có mẫu nào cả.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupButtonListener(SelectionUnitsAmountCallback callback) {
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Chấp nhận", (dialog, which) -> {
            callback.onAcceptAdditionalUnits(adapter.getTroops());
            dialog.cancel();
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Lưu mẫu", (dialog, which)
                -> {
            TextInputDialog.show(container.getContext(), "Thêm mẫu", "Nhập tên mẫu", input -> {
                unitSetupPatternDAO.addPattern(input, adapter.getTroops());
                Toast.makeText(context, "Đã thêm mẫu.", Toast.LENGTH_SHORT).show();
            });
        });
    }

    public void setData(List<BaseTroop> baseTroops){
        adapter.setTroops(baseTroops);
    }
    public void show(){
        if(alertDialog != null && !alertDialog.isShowing()){
            alertDialog.show();
        }
    }
    public static class UnitsAdditionalAdapter extends RecyclerView.Adapter<UnitsAdditionalAdapter.MyViewholder> {
        private List<BaseTroop> troops;
        public UnitsAdditionalAdapter(){
            List<Unit> units = UnitFactory.getAllUnits();
            troops = new LinkedList<>();
            for(Unit unit : units){
                troops.add(new BaseTroop(unit.getName(), 0));
            }
        }
        @Override
        public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_amount_selection_cardview, parent, false);
            return new MyViewholder(view);
        }

        public void setTroops(List<BaseTroop> troops) {
            if(troops != null && troops.size() > 0) {
                this.troops = troops;
                notifyDataSetChanged();
            }
        }

        @Override
        public void onBindViewHolder(MyViewholder holder, int position) {
            BaseTroop troop = troops.get(position);
            holder.setTroop(troop);
        }

        public List<BaseTroop> getTroops() {
            return troops;
        }
        @Override
        public int getItemCount() {
            return troops.size();
        }

        public class MyViewholder extends RecyclerView.ViewHolder{
            ImageView unitIconView;
            TextView unitNameView ;
            EditText unitAmountView;
            View view;
            BaseTroop baseTroop;
            public MyViewholder(View itemView) {
                super(itemView);
                this.view = itemView;
                unitIconView =  itemView.findViewById(R.id.unitImage);
                unitNameView =  itemView.findViewById(R.id.unitName);
                unitAmountView =  itemView.findViewById(R.id.unitAmountEditText);
                unitAmountView.addTextChangedListener(new TextWatcherAdapter() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            int newAmount = Integer.parseInt(s.toString());
                            if(baseTroop != null)
                                baseTroop.setAmount(newAmount);
                        } catch (Exception ignored) {
                            Toast.makeText(view.getContext(), "Hãy nhập số hợp lệ.", Toast.LENGTH_SHORT).show();
                            if(baseTroop != null){
                                baseTroop.setAmount(0);
                            }
                        }
                    }
                });
                unitAmountView.setOnFocusChangeListener((v, hasFocus) -> {
                    if(hasFocus){
                        if(unitAmountView.getText().toString().equalsIgnoreCase("0"))
                        unitAmountView.setText("");
                    }
                });
            }
            public void setTroop(BaseTroop troop){
                this.baseTroop = troop;
                Unit unit = UnitFactory.getUnit(troop.getUnitName());
                unitIconView.setImageDrawable(view.getResources().getDrawable(unit.getDrawable()));
                unitNameView.setText(unit.getName());
                unitAmountView.setText(String.valueOf(troop.getAmount()));

            }
        }
    }
}
