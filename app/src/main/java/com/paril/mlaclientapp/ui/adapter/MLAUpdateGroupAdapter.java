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
import com.paril.mlaclientapp.model.GroupModel;

import java.util.List;

/**
 * Created by Vaidehi Shah on 04-27-2020.
 */

public class MLAUpdateGroupAdapter extends BaseAdapter {
    List<GroupModel> list;
    Context mContext;

    public MLAUpdateGroupAdapter(Context context, List<GroupModel> list) {
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
       MLAFriendForUpdateAdapter dataAdapter;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.rowlayout_mla_friends_for_update_group, parent, false);
            dataAdapter = new MLAFriendForUpdateAdapter();

            dataAdapter.txtName = (TextView) rowView.findViewById(R.id.mla_friends_for_update_group_txtname);
            dataAdapter.txtEmail = (TextView) rowView.findViewById(R.id.mla_friends_for_update_group_txtemail);
            dataAdapter.chckBox = (CheckBox) rowView.findViewById(R.id.mla_friends_for_update_group_checkBx);


            rowView.setTag(dataAdapter);
            rowView.setTag(R.id.mla_friends_for_update_group_txtname, dataAdapter.txtName);
            rowView.setTag(R.id.mla_friends_for_update_group_txtemail, dataAdapter.txtEmail);
            rowView.setTag(R.id.mla_friends_for_update_group_checkBx, dataAdapter.chckBox);

            dataAdapter.chckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    int getPosition = (Integer) buttonView.getTag();
                    GroupModel userDisplayCheckbxProvider;
                    userDisplayCheckbxProvider = list.get(getPosition);
                    userDisplayCheckbxProvider.setCheck(buttonView.isChecked());
                }
            });

        } else {
            dataAdapter = (MLAFriendForUpdateAdapter) rowView.getTag();
        }
        dataAdapter.chckBox.setTag(position);

        GroupModel userDisplayCheckbxProvider;
        userDisplayCheckbxProvider = (GroupModel) this.getItem(position);

        dataAdapter.txtName.setText(String.valueOf(userDisplayCheckbxProvider.getFirstName()));
        dataAdapter.txtEmail.setText(String.valueOf(userDisplayCheckbxProvider.getEmailId()));
        dataAdapter.chckBox.setChecked(userDisplayCheckbxProvider.getCheck());
        return rowView;
    }

    static class MLAFriendForUpdateAdapter {
        TextView txtName;
        TextView txtEmail;
        CheckBox chckBox;

    }
}
