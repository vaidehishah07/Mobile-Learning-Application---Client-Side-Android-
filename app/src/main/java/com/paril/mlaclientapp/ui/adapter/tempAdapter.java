package com.paril.mlaclientapp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.paril.mlaclientapp.model.MLAFriendRequest;

import java.util.List;

/**
 * Created by Vaidehi Shah on 04-21-2020.
 */

public class tempAdapter extends BaseAdapter
{

    private Context context1;
    private List<MLAFriendRequest> switchList;
    public MLAAcceptRejectRequestAdapter.MLAHandleAcceptOrReject userSwitchCheck;

    public tempAdapter(Context context1, List<MLAFriendRequest> switchList, MLAAcceptRejectRequestAdapter.MLAHandleAcceptOrReject userSwitchCheck) {
        this.context1 = context1;
        this.switchList = switchList;
        this.userSwitchCheck = userSwitchCheck;
    }

    @Override
    public int getCount() {
        return switchList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public interface RequestViewHolder
    {
        void AcceptRejectReuqest(MLAFriendRequest model);
    }
}
