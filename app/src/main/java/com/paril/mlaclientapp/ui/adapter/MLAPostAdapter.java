package com.paril.mlaclientapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.paril.mlaclientapp.R;
import com.paril.mlaclientapp.model.PModel;
import com.paril.mlaclientapp.ui.activity.MLARepostActivity;

import java.util.List;



public class MLAPostAdapter extends BaseAdapter
{

    List<PModel> list;
    Context mContext;
    PModel mlapostobj;

    public MLAPostAdapter(Context context, List<PModel> list) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView;
        rowView = convertView;
        MLAPostAd dataAdapter;
        //Button sharePost;

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.rowlayout_mla_post_adapter, parent, false);
            //sharePost = (Button)rowView.findViewById(R.id.btn_share);
            dataAdapter = new MLAPostAd();
            dataAdapter.name = (TextView) rowView.findViewById(R.id.post_owner_name);
            dataAdapter.sharedbyname = (TextView) rowView.findViewById(R.id.post_sharedby_name);
            dataAdapter.tvsharedbyname = (TextView) rowView.findViewById(R.id.tv_sharedby_name);

            dataAdapter.temp = (TextView) rowView.findViewById(R.id.tv_post_By_Name);

            dataAdapter.txtPost = (TextView) rowView.findViewById(R.id.post_details);
            dataAdapter.tvprivatepost = (TextView) rowView.findViewById(R.id.tv_privatepost);
            dataAdapter.tvpublicpost = (TextView) rowView.findViewById(R.id.tv_publicpost);
            dataAdapter.tvalreadysharedpost = (TextView) rowView.findViewById(R.id.tv_already_shared_post);
            dataAdapter.btnshare = (Button) rowView.findViewById(R.id.btn_share);

            dataAdapter.btnshare.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.d("Error","INSIDE ON CLICK");
                    Toast.makeText(mContext,"On button click ",Toast.LENGTH_LONG);

                    Intent ii = new Intent(mContext, MLARepostActivity.class);

                    ii.putExtra("post_id",list.get(position).getPostId());
                    ii.putExtra("post_text",list.get(position).getPostText());
                    ii.putExtra("post_groupKey",list.get(position).getGroupkey());
                    ii.putExtra("post_sessionKey",list.get(position).getSessionKey());
                    ii.putExtra("post_groupId",list.get(position).getGroupId());

                    mContext.startActivity(ii);

                notifyDataSetChanged();
                    // = getItem(position);
                }
            });

            rowView.setTag(dataAdapter);
            rowView.setTag(R.id.post_owner_name, dataAdapter.name);
            rowView.setTag(R.id.post_sharedby_name, dataAdapter.sharedbyname);
            rowView.setTag(R.id.tv_sharedby_name, dataAdapter.tvsharedbyname);
            rowView.setTag(R.id.btn_share, dataAdapter.btnshare);
            rowView.setTag(R.id.post_details, dataAdapter.txtPost);

            // this post is a shared post, so make the name of the shared person visible


             if (list.get(position).getOriginalPostId() != 0 && list.get(position).getOriginalfirstname() == null )
            {
                dataAdapter.temp.setVisibility(View.GONE);
                dataAdapter.sharedbyname.setVisibility(View.VISIBLE);
                dataAdapter.tvsharedbyname.setVisibility(View.VISIBLE);
                dataAdapter.btnshare.setVisibility(View.GONE);
                dataAdapter.tvalreadysharedpost.setVisibility(View.VISIBLE);

            }

            else if (list.get(position).getOriginalPostId() != 0 && list.get(position).getOriginalfirstname() != "")
            {

                dataAdapter.sharedbyname.setVisibility(View.VISIBLE);
                dataAdapter.tvsharedbyname.setVisibility(View.VISIBLE);
                dataAdapter.btnshare.setVisibility(View.GONE);
                dataAdapter.tvalreadysharedpost.setVisibility(View.VISIBLE);

            }



                // else if (list.get(position).getPostType() == 3)
//            {
//                dataAdapter.sharedbyname.setVisibility(View.GONE);
//                dataAdapter.tvsharedbyname.setVisibility(View.GONE);
//                dataAdapter.btnshare.setVisibility(View.GONE);
//                dataAdapter.tvprivatepost.setVisibility(View.VISIBLE);
//            }

            else if (list.get(position).getPostType() == 4)
            {
                dataAdapter.sharedbyname.setVisibility(View.GONE);
                dataAdapter.tvsharedbyname.setVisibility(View.GONE);
                dataAdapter.btnshare.setVisibility(View.GONE);
                dataAdapter.tvpublicpost.setVisibility(View.VISIBLE);
            }

            else
            {
                dataAdapter.btnshare.setVisibility(View.VISIBLE);
            }
        }


        else
        {
            dataAdapter = (MLAPostAd) rowView.getTag();
            if(list.get(position).getOriginalPostId() != 0)
            {
                dataAdapter.sharedbyname.setVisibility(View.VISIBLE);
                dataAdapter.tvsharedbyname.setVisibility(View.VISIBLE);
                dataAdapter.btnshare.setVisibility(View.GONE);
            }

            /*else if (list.get(position).getPostType() == 3)
            {
                dataAdapter.sharedbyname.setVisibility(View.GONE);
                dataAdapter.tvsharedbyname.setVisibility(View.GONE);
                dataAdapter.btnshare.setVisibility(View.GONE);
            }*/
            else if (list.get(position).getPostType() == 4)
            {
                dataAdapter.sharedbyname.setVisibility(View.GONE);
                dataAdapter.tvsharedbyname.setVisibility(View.GONE);
                dataAdapter.btnshare.setVisibility(View.GONE);
            }
        }

        try
        {
            PModel userDisplayCheckbxProvider;
            userDisplayCheckbxProvider = (PModel) this.getItem(position);
            dataAdapter.txtPost.setText(userDisplayCheckbxProvider.getPostText());


            // if we get the value of original post id it means the post is shared by someone so now we have to show the shared details
            if(list.get(position).getOriginalPostId() != 0)
            {
                dataAdapter.sharedbyname.setVisibility(View.VISIBLE);
                dataAdapter.tvsharedbyname.setVisibility(View.VISIBLE);
                dataAdapter.sharedbyname.setText(userDisplayCheckbxProvider.getFirstname());
                dataAdapter.name.setText(userDisplayCheckbxProvider.getOriginalfirstname());
                dataAdapter.btnshare.setVisibility(View.GONE);
            }
            else
            {
                dataAdapter.name.setText(userDisplayCheckbxProvider.getFirstname());
                dataAdapter.sharedbyname.setText("NO ONE");
                dataAdapter.sharedbyname.setVisibility(View.GONE);
                dataAdapter.tvsharedbyname.setVisibility(View.GONE);
            }
        }

        catch (Exception e)
        {
            System.out.println(e);
        }

        return rowView;
    }

    static class MLAPostAd
    {
        TextView name;
        TextView sharedbyname;
        TextView txtPost;
        TextView tvsharedbyname;
        Button btnshare;
        TextView tvprivatepost;
        TextView tvpublicpost;
        TextView tvalreadysharedpost;
        TextView temp;
        TextView tvpostedByname;

    }
}

