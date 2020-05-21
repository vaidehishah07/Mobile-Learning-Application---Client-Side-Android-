package com.paril.mlaclientapp.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.paril.mlaclientapp.R;
import com.paril.mlaclientapp.model.GroupModel;
import com.paril.mlaclientapp.ui.activity.MLAHomeActivity;
import com.paril.mlaclientapp.ui.adapter.ShowFriendsListAdapter;
import com.paril.mlaclientapp.util.PrefsManager;
import com.paril.mlaclientapp.webservice.Api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class ShowListOfFriendsFragment extends Fragment
{

    String[] userName;
    String [] firstName;
    String [] lastName;
    String [] emailId;
    String [] publicKey;
    Integer[] userId;
    View view;
    ListView showfriendslistview;
    String Vaidehi;
    String Pk;
    List<GroupModel> fDetails = new ArrayList<>();
    ShowFriendsListAdapter userDisplayAdapter;
    PrefsManager prefsManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        prefsManager = new PrefsManager(getActivity());
        view = inflater.inflate(R.layout.fragment_showfriendslist, container, false);
        showfriendslistview = (ListView) view.findViewById(R.id.showfriendslist_listView);
        ShowFriendsToAddInGroupAPI showFriendsToAddInGroupAPI = new ShowFriendsToAddInGroupAPI(ShowListOfFriendsFragment.this.getActivity());
        showFriendsToAddInGroupAPI.execute();

        intentService();
        return view;
    }
    class ShowFriendsToAddInGroupAPI extends AsyncTask<Void, Void, List<GroupModel>> {
        Context context;

        public ShowFriendsToAddInGroupAPI(Context ctx)
        {
            context = ctx;

        }

        @Override
        protected void onPreExecute()
        {
            ((MLAHomeActivity) getActivity()).showProgressDialog("Fetching Friends Details...");

        }

        @Override
        protected void onPostExecute(List<GroupModel> apiResponse)
        {
            ((MLAHomeActivity) getActivity()).hideProgressDialog();

            if (apiResponse != null)
            {
                fDetails = apiResponse;
                userId = new Integer[fDetails.size()];
                emailId = new String[fDetails.size()];
                firstName = new String[fDetails.size()];
                lastName = new String[fDetails.size()];
                publicKey = new String[fDetails.size()];

                for (int i = 0; i < fDetails.size(); i++)
                {
                    userId[i] = fDetails.get(i).getUserId();
                    emailId[i] = fDetails.get(i).getEmailId();
                    firstName[i] = fDetails.get(i).getFirstName();
                    lastName[i] = fDetails.get(i).getLastName();
                    publicKey[i] = fDetails.get(i).getPublicKey();

                }

                List<GroupModel> listFriendDisplayCheckb = new ArrayList<>();

                for (int i = 0; i < fDetails.size(); i++) {

                    final GroupModel friendDisplayProvider = new GroupModel( userId[i],firstName[i],lastName[i], emailId[i]);
                    //final GroupModel friendDisplayProvider = new GroupModel( false, userId[i],"Shah", "Shah","Shah", "Shah");
                    listFriendDisplayCheckb.add(friendDisplayProvider);
                }

                userDisplayAdapter = new ShowFriendsListAdapter(context, listFriendDisplayCheckb);
                //listViewAddGroup = (ListView)view.findViewById(R.id.mla_create_group_display_listView);
                showfriendslistview.setAdapter(userDisplayAdapter);

            }
            else
            {
                userDisplayAdapter = new ShowFriendsListAdapter(context, new ArrayList<GroupModel>());
                //listViewAddGroup = (ListView)view.findViewById(R.id.mla_create_group_display_listView);
                showfriendslistview.setAdapter(userDisplayAdapter);
            }

        }

        @Override
        protected List<GroupModel> doInBackground(Void... params) {
            try {

                Integer userId = Integer.parseInt(Vaidehi);
                Call<List<GroupModel>> callFriendData = Api.getClient().getListofFriendsToaddInGroup(Integer.parseInt(Vaidehi));
                Response<List<GroupModel>> responseFriendData = callFriendData.execute();

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

            } catch (IOException e)
            {
                return null;
            }
        }
    }



    void intentService() {
       /* Intent previous = this.getActivity().getIntent();
        Bundle bundle = previous.getExtras();
        if (bundle != null)
        {

            Vaidehi = (String)bundle.get("userId");
            System.out.println("USER ID ="+Vaidehi);

            PkVaidehi = (String)bundle.get("publicKey");
            System.out.println("Public Key ="+PkVaidehi);
        }*/



        Vaidehi = prefsManager.getStringData("userId");
        Pk= prefsManager.getStringData("publicKey");
    }


}
