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



public class ShowFriendsListAdapter extends BaseAdapter {
    List<GroupModel> list;
    Context mContext;

    public ShowFriendsListAdapter(Context context, List<GroupModel> list)
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
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.rowlayout_showfriendslist, parent, false);
            dataAdapter = new MLAFriendAdapter();
            dataAdapter.txtName = (TextView) rowView.findViewById(R.id.showfriends_txtname);
            dataAdapter.txtEmail = (TextView) rowView.findViewById(R.id.showfriends_txtemail);

            rowView.setTag(dataAdapter);
            rowView.setTag(R.id.mla_friend_display_chckbxusers_txtname, dataAdapter.txtName);
            rowView.setTag(R.id.mla_friend_display_chckbxusers_txtemail, dataAdapter.txtEmail);


        } else

            {
            dataAdapter = (MLAFriendAdapter) rowView.getTag();
        }


        GroupModel userDisplayCheckbxProvider;
        userDisplayCheckbxProvider = (GroupModel) this.getItem(position);

        dataAdapter.txtName.setText(String.valueOf(userDisplayCheckbxProvider.getFirstName()));
        dataAdapter.txtEmail.setText(String.valueOf(userDisplayCheckbxProvider.getEmailId()));

        return rowView;
    }

    static class MLAFriendAdapter {
        TextView txtName;
        TextView txtEmail;


    }
}
