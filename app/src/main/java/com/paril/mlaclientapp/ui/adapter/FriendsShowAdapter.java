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
//import com.paril.mlaclientapp.model.FriendCheckboxModel;
import com.paril.mlaclientapp.model.GroupModel;

import java.util.List;



public class FriendsShowAdapter extends BaseAdapter {
    List<GroupModel> list;
    Context mContext;

    public FriendsShowAdapter(Context context, List<GroupModel> list)
    {
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
        MLAFriendAdapter dataAdapter;
        if (convertView == null)
        {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.rowlayout_mla_friend_display_chckbxusers, parent, false);
            dataAdapter = new MLAFriendAdapter();
            dataAdapter.txtName = (TextView) rowView.findViewById(R.id.mla_friend_display_chckbxusers_txtname);
            dataAdapter.txtEmail = (TextView) rowView.findViewById(R.id.mla_friend_display_chckbxusers_txtemail);
            dataAdapter.chckBox = (CheckBox) rowView.findViewById(R.id.mla_friend_display_chckbxusers_checkBx);

            rowView.setTag(dataAdapter);
            rowView.setTag(R.id.mla_friend_display_chckbxusers_txtname, dataAdapter.txtName);
            rowView.setTag(R.id.mla_friend_display_chckbxusers_txtemail, dataAdapter.txtEmail);
            rowView.setTag(R.id.mla_friend_display_chckbxusers_checkBx, dataAdapter.chckBox);


            dataAdapter.chckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    int getPosition = (Integer) buttonView.getTag();
                    GroupModel userDisplayCheckbxProvider;
                    userDisplayCheckbxProvider =  list.get(getPosition);
                    userDisplayCheckbxProvider.setCheck(buttonView.isChecked());
                }
            });

        } else {
            dataAdapter = (MLAFriendAdapter) rowView.getTag();
        }
        dataAdapter.chckBox.setTag(position);

        GroupModel groupModel;
        groupModel = (GroupModel) this.getItem(position);

        dataAdapter.txtName.setText(String.valueOf(groupModel.getFirstName()));
        dataAdapter.txtEmail.setText(String.valueOf(groupModel.getEmailId()));
        dataAdapter.chckBox.setChecked(groupModel.getCheck());

        return rowView;
    }

    static class MLAFriendAdapter {
        TextView txtName;
        TextView txtEmail;
        CheckBox chckBox;



    }
}
