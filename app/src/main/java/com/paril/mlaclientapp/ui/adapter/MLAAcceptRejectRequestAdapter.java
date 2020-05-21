package com.paril.mlaclientapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paril.mlaclientapp.R;
import com.paril.mlaclientapp.model.MLAFriendRequest;

import java.util.List;



public class MLAAcceptRejectRequestAdapter extends RecyclerView.Adapter<MLAAcceptRejectRequestAdapter.MyRequestViewHolder>
{
    private Context context1;
    private List<MLAFriendRequest> friendListInadapter;
    public MLAHandleAcceptOrReject handleacceptReject;

    public MLAAcceptRejectRequestAdapter(Context context1, List<MLAFriendRequest> friendListInadapter, MLAHandleAcceptOrReject handleacceptReject) {
        this.context1 = context1;
        this.friendListInadapter = friendListInadapter;
        this.handleacceptReject = handleacceptReject;
    }

    @Override
    public MyRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fragment_friendrequest_single_request, parent, false);
        return new MyRequestViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(MLAAcceptRejectRequestAdapter.MyRequestViewHolder holder, int position)
    {
        final MLAFriendRequest modelObj = friendListInadapter.get(position);
        holder.tvFriendName.setText(modelObj.getUserfirstName());

        holder.imgAccept.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                modelObj.setCheck(true);
                handleacceptReject.AcceptRejectRequest(modelObj);
            }
        });

        holder.imgReject.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                modelObj.setCheck(false);
                handleacceptReject.AcceptRejectRequest(modelObj);
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
        return friendListInadapter.size();
    }

    public class MyRequestViewHolder extends RecyclerView.ViewHolder
    {
        Button imgReject;
        LinearLayout friendsrequest_ll;
        TextView tvFriendName;
        Button imgAccept;
        TextView stativFriendName;


        public MyRequestViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tvFriendName = (TextView) itemView.findViewById(R.id.tv_friend_name);
            stativFriendName = (TextView) itemView.findViewById(R.id.static_tv_friend_name);
            imgAccept = (Button) itemView.findViewById(R.id.accept_request_btn);
            imgReject = (Button) itemView.findViewById(R.id.reject_request_btn);
            friendsrequest_ll = (LinearLayout) itemView.findViewById(R.id.friendsrequest_linearlayout);

        }
    }

    public interface MLAHandleAcceptOrReject
    {

        void AcceptRejectRequest(MLAFriendRequest model);
    }
}
