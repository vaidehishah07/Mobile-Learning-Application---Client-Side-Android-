package com.paril.mlaclientapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paril.mlaclientapp.R;
import com.paril.mlaclientapp.model.MLALeaveGroup;

import java.util.List;



public class MLADeleteGroupAdapter extends RecyclerView.Adapter<MLADeleteGroupAdapter.MyRequestViewHolder>
{

    private Context context1;
    private List<MLALeaveGroup> switchList;
    public MLAHandleLeaveGroup userSwitchCheck;

    public MLADeleteGroupAdapter(Context context1, List<MLALeaveGroup> switchList, MLAHandleLeaveGroup userSwitchCheck) {
        this.context1 = context1;
        this.switchList = switchList;
        this.userSwitchCheck = userSwitchCheck;
    }

    @Override
    public MyRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        LayoutInflater inflater = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fragment_mla_leavegroup_item, parent, false);
        //View view = ((LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).from(context1).inflate(R.layout.fragment_friendrequest_single_request, parent, false);
        return new MyRequestViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(MLADeleteGroupAdapter.MyRequestViewHolder holder, int position)
    {

        final MLALeaveGroup modelObj = switchList.get(position);
        holder.tv_group_name1.setText(modelObj.getGroupname());

        holder.imgReject.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                modelObj.setCheck(true);
                userSwitchCheck.LeaveGroup(modelObj);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return switchList.size();
    }

    public class MyRequestViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_group_name1;
        //ImageView imgAccept;
        ImageView imgReject;
        LinearLayout leavegroup_linearlayout1;
        //Make views objects

        public MyRequestViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv_group_name1 = (TextView) itemView.findViewById(R.id.tv_group_name);
            imgReject = (ImageView) itemView.findViewById(R.id.mla_leave_group);
            leavegroup_linearlayout1 = (LinearLayout) itemView.findViewById(R.id.leavegroup_linearlayout);
            //Register you views here
        }
    }

    public interface MLAHandleLeaveGroup
    {

        //TO HANDLE CLCIK EVENT OF THE ACCEPT REJECT BUTTON
        //This model object will be the object you will send to API
        void LeaveGroup(MLALeaveGroup model);
    }

}
