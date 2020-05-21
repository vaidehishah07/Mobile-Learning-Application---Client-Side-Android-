package com.paril.mlaclientapp.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.paril.mlaclientapp.R;
import com.paril.mlaclientapp.model.MLALeaveGroup;
import com.paril.mlaclientapp.ui.activity.MLAHomeActivity;
import com.paril.mlaclientapp.ui.adapter.MLADeleteGroupAdapter;
import com.paril.mlaclientapp.util.PrefsManager;
import com.paril.mlaclientapp.webservice.Api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DeleteGroupFragment extends Fragment {


    View view;
    RecyclerView deleteGrpRecyclerView;
    TextView tv_nodata;

    String Vaidehi;
    String Pk;


    Boolean[] check;
     Integer[] userId;
     Integer []groupId;
     Integer []groupType;
     String []groupKey;
     String []groupname;
     PrefsManager prefsManager;
     String PkVaidehi;

    ListView listViewAddGroup;
    Context ct;

    List<MLALeaveGroup> listFriendDisplayCheckb = new ArrayList<>();

    List<MLALeaveGroup> groupDetails = new ArrayList<>();

    MLADeleteGroupAdapter mlaDeleteGroupAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        prefsManager = new PrefsManager(getActivity());
        view = inflater.inflate(R.layout.fragment_mla_leavegroup, container, false);
        deleteGrpRecyclerView = (RecyclerView) view.findViewById(R.id.leavegroup_recycler_view);
        tv_nodata = (TextView)view.findViewById(R.id.no_leave_group_data);
        getCurrentUserandPk();

        MLAShowGroupsAPI mlaShowFriendRequestAPI = new MLAShowGroupsAPI(DeleteGroupFragment.this.getActivity());
        mlaShowFriendRequestAPI.execute();

        return view;
    }




    @Override
    public void onResume() {
        super.onResume();

    }

    void getCurrentUserandPk()
    {

        Vaidehi = prefsManager.getStringData("userId");
        PkVaidehi = prefsManager.getStringData("publicKey");

    }

    class MLAShowGroupsAPI extends AsyncTask<Void, Void, List<MLALeaveGroup>> {
        Context context;

        public MLAShowGroupsAPI(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute()
        {



        }

        @Override
        protected void onPostExecute(List<MLALeaveGroup> apiResponse) {


            if (apiResponse != null ) {

                if (apiResponse.size() > 0)
                {
                    tv_nodata.setVisibility(tv_nodata.GONE);
                    groupDetails = apiResponse;

                    for (int i = 0; i < groupDetails.size(); i++)
                    {
                        groupDetails = apiResponse;
                        userId = new Integer[groupDetails.size()];
                        groupname = new String[groupDetails.size()];
                        groupId = new Integer[groupDetails.size()];
                        groupType = new Integer[groupDetails.size()];
                        groupKey = new String[groupDetails.size()];
                    }

                    for (int i = 0; i < groupDetails.size(); i++)
                    {

                        userId[i] = groupDetails.get(i).getUserId();
                        groupname[i] = groupDetails.get(i).getGroupname();
                        groupId[i] = groupDetails.get(i).getGroupId();
                        groupType[i] = groupDetails.get(i).getGroupType();
                        groupKey[i] = groupDetails.get(i).getGroupKey();

                    }

                    for (int i = 0; i < groupDetails.size(); i++) {

                        final MLALeaveGroup friendDisplayProvider = new MLALeaveGroup(userId[i],groupname[i], groupId[i],groupType[i],groupKey[i]);
                        listFriendDisplayCheckb.add(friendDisplayProvider);
                    }

                    mlaDeleteGroupAdapter = new MLADeleteGroupAdapter(DeleteGroupFragment.this.getActivity(), listFriendDisplayCheckb, new MLADeleteGroupAdapter.MLAHandleLeaveGroup()
                    {

                        @Override
                        public void LeaveGroup(MLALeaveGroup objFriends) {

                            if (objFriends.getCheck()) {

                                // call API for accept -
                                MLARemoveGroup mlaacceptFriendRequestAPI = new MLARemoveGroup(DeleteGroupFragment.this.getActivity(), objFriends);
                                mlaacceptFriendRequestAPI.execute();

                                listFriendDisplayCheckb.remove(objFriends);

                            }

                            deleteGrpRecyclerView.getAdapter().notifyDataSetChanged();
                            //Toast.makeText(MLAFriendRequestFragment.this.getActivity(), "Accepted", Toast.LENGTH_LONG).show();
                        }
                    });

                    deleteGrpRecyclerView.setAdapter(mlaDeleteGroupAdapter);
                    deleteGrpRecyclerView.setHasFixedSize(true);
                    deleteGrpRecyclerView.setLayoutManager(new LinearLayoutManager(DeleteGroupFragment.this.getActivity()));

                }
                else
                {
                    tv_nodata.setVisibility(tv_nodata.VISIBLE);

                }
            }
            else
            {
                tv_nodata.setVisibility(tv_nodata.VISIBLE);
            }

            //else

            // userDisplayAdapter = new MLAFriendDisplayChckbxAdapter(context, new ArrayList<MLAFriendDisplayCheckbox>());
            //listViewAddGroup = (ListView)view.findViewById(R.id.mla_create_group_display_listView);
            //listViewAddGroup.setAdapter(userDisplayAdapter);


//            List<MLAFriendDisplayCheckbox> listFriendDisplayCheckb = new ArrayList<>();
//            for (int i = 0; i < 10; i++)
//            {
//
//                //final MLAFriendDisplayCheckbox friendDisplayProvider = new MLAFriendDisplayCheckbox( false, userId[i],emailId[i], firstName[i],lastName[i], publicKey[i]);
//                final MLAFriendDisplayCheckbox friendDisplayProvider = new MLAFriendDisplayCheckbox( false, 1,"Shah", "Shah","Shah", "Shah");
//                listFriendDisplayCheckb.add(friendDisplayProvider);
//                userDisplayAdapter = new MLAFriendDisplayChckbxAdapter(context, listFriendDisplayCheckb);
//                //listViewAddGroup = (ListView)view.findViewById(R.id.mla_create_group_display_listView);
//                listViewAddGroup.setAdapter(userDisplayAdapter);
//            }
        }

        @Override
        protected List<MLALeaveGroup> doInBackground(Void... params) {
            try {

                Call<List<MLALeaveGroup>> callFriendData = Api.getClient().getGroupDetails(Integer.parseInt(Vaidehi));
                Response<List<MLALeaveGroup>> responseFriendData = callFriendData.execute();

                System.out.println("Do Inbackground working....");
                if (responseFriendData.isSuccessful() && responseFriendData.body() != null)
                {
                    return responseFriendData.body();
                }
                else
                {
                    return null;
                }

            } catch (MalformedURLException e) {
                return null;

            } catch (IOException e) {
                return null;
            }
        }
    }


    class MLARemoveGroup extends AsyncTask<Void, Void, String> {

        Context context;
        MLALeaveGroup object;

        Integer userId;
        Integer groupId;
        String groupkey;
        String groupName;
        Integer groupType;

        MLALeaveGroup object1;
        // String currentUserId;


        public MLARemoveGroup(Context ctx, MLALeaveGroup obj)
        {
            context = ctx;
            object = obj;

        }

        @Override
        protected void onPreExecute()
        {

            try
            {
                userId = object.userId;
                groupName = object.groupname;
                groupId = object.groupId;
                groupType = object.groupType;
                groupkey = object.groupKey;

            }

            catch (Exception e)
            {
                System.out.print("EXCEPTION IN PRE EXECUTE" + e);
            }
        }

        @Override
        protected void onPostExecute(String apiResponse) {
            ((MLAHomeActivity) getActivity()).hideProgressDialog();

            if (apiResponse.equals("Group Deleted")) //the item is created
            {

            }


        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                Integer currentuser = Integer.parseInt(Vaidehi);
                String gid= groupId.toString();

                Call<String> callAddFriendData = Api.getClient().removeGrp(groupId);
                Response<String> responseSubjectData = callAddFriendData.execute();

            } catch (MalformedURLException e) {
                System.out.println(e);
                return null;

            } catch (IOException e) {
                return null;
            }

            return "Group Deleted";
        }


    }


}
