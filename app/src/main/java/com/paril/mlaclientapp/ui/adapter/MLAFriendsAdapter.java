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
import com.paril.mlaclientapp.model.FriendCheckboxModel;

import java.util.List;



public class MLAFriendsAdapter extends BaseAdapter {
    
    List<FriendCheckboxModel> list;
    Context mContext;

    public MLAFriendsAdapter(Context context, List<FriendCheckboxModel> list)
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
        staticFriendAdapterClass data;

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.rowlayout_mla_friend_chckbx, parent, false);
            data = new staticFriendAdapterClass();
            data.txtName = (TextView) rowView.findViewById(R.id.mla_friend_chckbxusers_txtname);
            data.txtType = (TextView) rowView.findViewById(R.id.mla_friend_chckbxusers_txttype);
            data.chckBox = (CheckBox) rowView.findViewById(R.id.mla_friend_chckbxusers_checkBx);
            data.status = (TextView) rowView.findViewById(R.id.mla_friend_chckbxusers_status);
            rowView.setTag(data);
            rowView.setTag(R.id.mla_friend_chckbxusers_txtname, data.txtName);
            rowView.setTag(R.id.mla_friend_chckbxusers_txttype, data.txtType);
            rowView.setTag(R.id.mla_friend_chckbxusers_checkBx, data.chckBox);
            rowView.setTag(R.id.mla_friend_chckbxusers_status, data.status);

            FriendCheckboxModel friendCheckboxModel = list.get(position);
            if (friendCheckboxModel.getStatus()!= "")
            {
                data.chckBox.setVisibility(View.INVISIBLE);
                data.txtName.setVisibility(View.INVISIBLE);
                data.txtType.setVisibility(View.INVISIBLE);
                data.status.setVisibility(View.INVISIBLE);
            }

            else
                {
                data.chckBox.setVisibility(View.VISIBLE);
                data.chckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        int getPosition = (Integer) buttonView.getTag();

                        FriendCheckboxModel friendchckboxmodel;
                        friendchckboxmodel =  list.get(getPosition);
                        friendchckboxmodel.setCheck(buttonView.isChecked());
                    }
                });
            }
        }
        else
            {
            data = (staticFriendAdapterClass) rowView.getTag();

                /*if (list.get(position).getStatus()!= "")
                {
                    data.chckBox.setVisibility(View.INVISIBLE);
                    data.txtName.setVisibility(View.INVISIBLE);
                    data.txtType.setVisibility(View.INVISIBLE);
                    data.status.setVisibility(View.INVISIBLE);
                }*/
/*else if (list.get(position).getStatus().equals("Approve friend Request"))
                {
                    data.chckBox.setVisibility(View.INVISIBLE);
                    data.txtName.setVisibility(View.INVISIBLE);
                    data.txtType.setVisibility(View.INVISIBLE);

                }*/


            }

        data.chckBox.setTag(position);
        FriendCheckboxModel friendchckboxmodel1;
        friendchckboxmodel1 = (FriendCheckboxModel) this.getItem(position);
        data.txtName.setText(friendchckboxmodel1.getFullName());
        data.txtType.setText(friendchckboxmodel1.getEmailid());
        data.status.setText(friendchckboxmodel1.getStatus());

        data.chckBox.setChecked(friendchckboxmodel1.getCheck());

        return rowView;
    }

    static class staticFriendAdapterClass {
        TextView txtName;
        TextView txtType;
        CheckBox chckBox;
        TextView status;
    }
}
