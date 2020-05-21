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
import com.paril.mlaclientapp.model.GDModel;

import java.util.List;



public class GListInPostAdapter extends BaseAdapter
{
        List<GDModel> list;
        Context mContext;
        Integer pt;

        public GListInPostAdapter(Context context, List<GDModel> list, Integer pt)
        {
            this.list = list;
            this.mContext = context;
            this.pt = pt;
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
                rowView = inflater.inflate(R.layout.rowlayout_mla_showgroup_for_post, parent, false);

                dataAdapter = new MLADataAdapter();
                dataAdapter.txtgroupName = (TextView) rowView.findViewById(R.id.mla_addpost_chckbxusers_txtgrouppname);
                dataAdapter.groupnametxtbox = (TextView) rowView.findViewById(R.id.mla_grp_name_txt);
                dataAdapter.chckBox = (CheckBox) rowView.findViewById(R.id.mla_addgroup_forpost_checkBx);

                rowView.setTag(dataAdapter);
                rowView.setTag(R.id.mla_addpost_chckbxusers_txtgrouppname, dataAdapter.txtgroupName);
                //rowView.setTag(R.id.mla_addfriend_chckbxusers_txtusertype, dataAdapter.txtuserType);
                rowView.setTag(R.id.mla_addgroup_forpost_checkBx, dataAdapter.chckBox);


                // for public post and private post
                if(pt == 1 ||  pt == 3)
                {
                    dataAdapter.chckBox.setVisibility(View.GONE);
                    dataAdapter.txtgroupName.setVisibility(View.GONE);
                    dataAdapter.groupnametxtbox.setVisibility(View.GONE);


                }


                // if post type == group
                else if (pt == 2)
                {
                    dataAdapter.chckBox.setVisibility(View.VISIBLE);
                    dataAdapter.txtgroupName.setVisibility(View.VISIBLE);
                    dataAdapter.groupnametxtbox.setVisibility(View.VISIBLE);

                    dataAdapter.chckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            int getPosition = (Integer) buttonView.getTag();
                            GDModel userDisplayCheckbxProvider;
                            userDisplayCheckbxProvider = list.get(getPosition);
                            userDisplayCheckbxProvider.setCheck(buttonView.isChecked());
                        }
                    });
                }
            }

            else
            {
                dataAdapter = (MLADataAdapter) rowView.getTag();
            }

            dataAdapter.chckBox.setTag(position);

            GDModel userDisplayCheckbxProvider;
            userDisplayCheckbxProvider = (GDModel) this.getItem(position);
            dataAdapter.txtgroupName.setText(userDisplayCheckbxProvider.getGroupname());

            return rowView;
        }

        static class MLADataAdapter {
            TextView txtgroupName;
            TextView groupnametxtbox;
            CheckBox chckBox;

        }



    }
