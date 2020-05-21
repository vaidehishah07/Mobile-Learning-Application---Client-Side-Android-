package com.paril.mlaclientapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.paril.mlaclientapp.R;
import com.paril.mlaclientapp.model.MLAAddfriendUserWithCheckbox;

import java.util.List;

/**
 * Created by Vaidehi Shah on 04-12-2020.
 */

public class MLAAddFriendChckbxAdapter extends BaseAdapter {
    List<MLAAddfriendUserWithCheckbox> list;
    Context mContext;

    public MLAAddFriendChckbxAdapter(Context context, List<MLAAddfriendUserWithCheckbox> list) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        rowView = convertView;
       MLADataAdapter dataAdapter;

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.rowlayout_mla_addfriend_chckbxusers, parent, false);
            dataAdapter = new MLADataAdapter();
            dataAdapter.txtName = (TextView) rowView.findViewById(R.id.mla_addfriend_chckbxusers_txtname);
            dataAdapter.txtuserType = (TextView) rowView.findViewById(R.id.mla_addfriend_chckbxusers_txtusertype);
            dataAdapter.chckBox = (CheckBox) rowView.findViewById(R.id.mla_addfriend_chckbxusers_checkBx);

            rowView.setTag(dataAdapter);
            rowView.setTag(R.id.mla_addfriend_chckbxusers_txtname, dataAdapter.txtName);
            rowView.setTag(R.id.mla_addfriend_chckbxusers_txtusertype, dataAdapter.txtuserType);
            rowView.setTag(R.id.mla_addfriend_chckbxusers_checkBx, dataAdapter.chckBox);

            dataAdapter.chckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();
                    MLAAddfriendUserWithCheckbox userDisplayCheckbxProvider;
                    userDisplayCheckbxProvider =  list.get(getPosition);
                    userDisplayCheckbxProvider.setCheck(buttonView.isChecked());
                }
            });

        }
        else
        {
            dataAdapter = (MLADataAdapter) rowView.getTag();
        }

        dataAdapter.chckBox.setTag(position);

        MLAAddfriendUserWithCheckbox userDisplayCheckbxProvider;
        userDisplayCheckbxProvider = (MLAAddfriendUserWithCheckbox) this.getItem(position);
        dataAdapter.txtName.setText(userDisplayCheckbxProvider.getUsername());
        dataAdapter.txtuserType.setText(userDisplayCheckbxProvider.getUsertype());
        dataAdapter.chckBox.setChecked(userDisplayCheckbxProvider.getCheck());

        return rowView;
    }

    static class MLADataAdapter {
        TextView txtName;
        TextView txtuserType;
        CheckBox chckBox;
    }

}
