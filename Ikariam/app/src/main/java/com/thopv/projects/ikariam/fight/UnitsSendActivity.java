package com.thopv.projects.ikariam.fight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.thopv.projects.ikariam.R;
import com.thopv.projects.ikariam.supports.dialogs.TimeChoosenDialog;
import com.thopv.projects.ikariam.config.UseCaseComponent;
import com.thopv.projects.ikariam.supports.utils.ToastUtils;
import com.thopv.projects.ikariam.supports.utils.ViewUtils;
import com.thopv.projects.ikariam.fight.presentation.contracts.UnitsSendContract;
import com.thopv.projects.ikariam.fight.presentation.presenters.UnitSendPresenter;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.fight.presentation.views.UnitsAmountSelectAdapter;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;

import junit.framework.Assert;

import java.util.List;

public class UnitsSendActivity extends AppCompatActivity implements UnitsSendContract.Controller {
    private UnitSendPresenter unitSendPresenter;
    private UnitsAmountSelectAdapter adapter;
    private RecyclerView unitsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_units_send);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Assert.assertNotNull(getIntent());
        Assert.assertNotNull(getIntent().getExtras());
        Assert.assertNotNull(getIntent().getExtras().getString("house"));

        House house = new Gson().fromJson(getIntent().getExtras().getString("house"), House.class);

        setResult(RESULT_OK, getIntent());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        unitsView = findViewById(R.id.unitsAmountSelectionListView);

        adapter = new UnitsAmountSelectAdapter();
        unitsView.setAdapter(adapter);
        unitsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        UseCaseComponent useCaseComponent = UseCaseComponent.getInstance(this);

        unitSendPresenter = new UnitSendPresenter(house, this, useCaseComponent.getSendUnits(),
                useCaseComponent.getLoadHomeTroops()
        );

        findViewById(R.id.confirmButton).setOnClickListener(v -> {
            ViewUtils.disableView(v);
            TimeChoosenDialog.show(this, timeInMillis -> {
                unitSendPresenter.sendUnits(timeInMillis, adapter.getUnitsAmount());
            });
        });
    }
    @Override
    public void showMessage(String msg) {
        ToastUtils.getInstance(this).show(msg);
    }

    @Override
    public void showUnits(List<BaseTroop> baseTroops) {

        adapter.setTroops(baseTroops);
    }

    @Override
    public void cancel() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /*public static class UnitsAmountSetupAdapter extends RecyclerView.Adapter<UnitsAmountSetupAdapter.MyViewholder> {
        private List<BaseTroop> troops;
        public UnitsAmountSetupAdapter(){
            List<String> unitsName = UnitFactory.getAllUnitsName();
            troops = new ArrayList<>();
            for(String unitName : unitsName){
                BaseTroop troop = new BaseTroop(unitName, 0);
                troops.add(troop);
            }
        }
        @Override
        public UnitsAmountSetupAdapter.MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_amount_selection_cardview, parent, false);
            return new UnitsAmountSetupAdapter.MyViewholder(view);
        }

        @Override
        public void onBindViewHolder(UnitsAmountSetupAdapter.MyViewholder holder, int position) {
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
                        }
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
    }*/
}
