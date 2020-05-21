package com.paril.mlaclientapp.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.paril.mlaclientapp.R;
import com.paril.mlaclientapp.model.GroupModel;

import com.paril.mlaclientapp.ui.activity.MLAHomeActivity;
import com.paril.mlaclientapp.ui.adapter.FriendsShowAdapter;
import com.paril.mlaclientapp.util.KeyManager;
import com.paril.mlaclientapp.util.PrefsManager;
import com.paril.mlaclientapp.webservice.Api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class MLAAddFriendsToGroupFragment extends Fragment
{


    String [] emailId;
    String [] publicKey;
    String[] userName;
    String [] firstName;
    String [] lastName;
    KeyManager keys;
    
    ArrayList<String> pkArray;
   
    FriendsShowAdapter friendsShowAdapter;
    
    String Vaidehi;
    String PkVaidehi;
    View view;
    
     EditText groupName;
    Context ctx;
    Button addFriendTogrpbtn;
    ListView listViewAddGroup;
 
    ArrayList<Integer> unArray;
    List<GroupModel> detailsOfFriendList = new ArrayList<>();
    Integer[] userId;
    PrefsManager prefsManager;
    
    void getCUserAndPk()
    {
        Vaidehi = prefsManager.getStringData("userId");
        PkVaidehi = prefsManager.getStringData("publicKey");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        prefsManager = new PrefsManager(getActivity());
        getCUserAndPk();
        view = inflater.inflate(R.layout.fragment_create_group, container, false);
        listViewAddGroup = (ListView) view.findViewById(R.id.mla_create_group_display_listView);
        groupName = (EditText)view.findViewById(R.id.mla_group_name);
        addFriendTogrpbtn = (Button)view.findViewById(R.id.mla_create_group_btn);

        ShowFriendsToAddInGroupAPI showFriendsToAddInGroupAPI = new ShowFriendsToAddInGroupAPI(MLAAddFriendsToGroupFragment.this.getActivity());
        showFriendsToAddInGroupAPI.execute();

        addFriendTogrpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(groupName.getText()!=null && !groupName.getText().toString().isEmpty())
                {
                    if (friendsShowAdapter != null && friendsShowAdapter.getCount() > 0)
                    {
                        int listSize = friendsShowAdapter.getCount();
                        GroupModel grpModelitem;
                        unArray = new ArrayList<>();
                        pkArray = new ArrayList<>();

                        for (int i = 0; i < listSize; i++)
                        {

                            grpModelitem = (GroupModel) friendsShowAdapter.getItem(i);
                            String groupname = grpModelitem.getGroupName();

                            if (grpModelitem.getCheck())
                            {
                                unArray.add(grpModelitem.getUserId()); // userId= id of selected Friend
                                //unArray.add(Integer.valueOf(userDisplayCheckbxProvider.getPublicKey()));
                                pkArray.add(grpModelitem.getPublicKey());

                            }
                        }


                        if(unArray.size()>0)
                        {
                            MLAAddGroupAPI mlaAddGroupAPI = new MLAAddGroupAPI(MLAAddFriendsToGroupFragment.this.getActivity());
                            mlaAddGroupAPI.execute();
                        }
                    }
                }
                else
                {
                    Toast.makeText(MLAAddFriendsToGroupFragment.this.getActivity(),"Enter valid group name", Toast.LENGTH_SHORT).show();
                }

            }
        });

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
            List<GroupModel> listgroup = new ArrayList<>();

            ((MLAHomeActivity) getActivity()).hideProgressDialog();
            if (apiResponse != null)
            {
                detailsOfFriendList = apiResponse;
                userId = new Integer[detailsOfFriendList.size()];
                firstName = new String[detailsOfFriendList.size()];
                lastName = new String[detailsOfFriendList.size()];
                emailId = new String[detailsOfFriendList.size()];
                publicKey = new String[detailsOfFriendList.size()];
                
                for (int i = 0; i < detailsOfFriendList.size(); i++)
                {
                    userId[i] = detailsOfFriendList.get(i).getUserId();
                    emailId[i] = detailsOfFriendList.get(i).getEmailId();
                    firstName[i] = detailsOfFriendList.get(i).getFirstName();
                    lastName[i] = detailsOfFriendList.get(i).getLastName();
                    publicKey[i] = detailsOfFriendList.get(i).getPublicKey();

                    //userName[i] = String.valueOf(friendsList.get(i).getFriend_id());
                    //userType[i] = registerDetails.get(i).getUserType();
                }



                for (int i = 0; i < detailsOfFriendList.size(); i++) {

                    final GroupModel grpConstructor = new GroupModel( false, userId[i],emailId[i], firstName[i],lastName[i], publicKey[i]);
                    //final GroupModel friendDisplayProvider = new GroupModel( false, userId[i],"Shah", "Shah","Shah", "Shah");
                    listgroup.add(grpConstructor);
                }

                friendsShowAdapter = new FriendsShowAdapter(context, listgroup);
                //listViewAddGroup = (ListView)view.findViewById(R.id.mla_create_group_display_listView);
                listViewAddGroup.setAdapter(friendsShowAdapter);

            }
            else
                {
                friendsShowAdapter = new FriendsShowAdapter(context, new ArrayList<GroupModel>());
                //listViewAddGroup = (ListView)view.findViewById(R.id.mla_create_group_display_listView);
                listViewAddGroup.setAdapter(friendsShowAdapter);
                }
            
            groupName.setText("");
        }

        @Override
        protected List<GroupModel> doInBackground(Void... params) {
            try {


               // Integer userId = Integer.parseInt(Vaidehi);
                Call<List<GroupModel>> callFriendData = Api.getClient().getListofFriendsToaddInGroup(Integer.parseInt(Vaidehi));
                Response<List<GroupModel>> responseFriendData = callFriendData.execute();

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

    class MLAAddGroupAPI extends AsyncTask<Void, Void, String> {

        Context context;
        byte[] groupKeyBytes;
        GroupModel groupObj = new GroupModel();
        GroupModel groupObject = new GroupModel();
        String groupKey;
        ArrayList<Integer> uIds;
        ArrayList<String> publicKeys;
        Integer grpId;
        String grpName;


       public MLAAddGroupAPI(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute()
        {

            try {
                keys = new KeyManager(1024);
                uIds = unArray;
                grpName = groupName.getText().toString();
                publicKeys = pkArray;
                groupKeyBytes = keys.newGrpKeyGenerate();
            }
            catch (Exception e)
            {

            }

        }

        @Override
        protected void onPostExecute(String statusCode)
        {

            if (statusCode.equals("Group Created")) //the item is created
            {


              ShowFriendsToAddInGroupAPI showFriendsToAddInGroupAPI = new ShowFriendsToAddInGroupAPI(MLAAddFriendsToGroupFragment.this.getActivity());
              showFriendsToAddInGroupAPI.execute();

                //getActivity().finish();
               ((MLAHomeActivity) getActivity()).showSnackBar("Added in group.", view.findViewById(R.id.mla_addgroup_linerlayout));
                Toast.makeText(context, "Group created", Toast.LENGTH_SHORT).show();
            }
            else

                {
                //((MLAHomeActivity) getActivity()).showSnackBar("Error while adding group.", view.findViewById(R.id.fragment_add_friend_coordinatorLayout));
            }
        }

        @Override
        protected String doInBackground(Void... params)
        {

            groupKeyBytes = keys.newGrpKeyGenerate();

            try
            {
                byte[] publicKeyByte = keys.sTob(PkVaidehi);
                PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyByte));
                byte[] grpKeyByte = keys.encrypt_with_PubKey(groupKeyBytes, publicKey);
                String grpKey = keys.bTos(grpKeyByte);

                groupObj.setGroupKey(grpKey);
                groupObj.setOwnerId(Integer.parseInt(Vaidehi));
                groupObj.setGroupName(grpName);

                Call<Integer> grpIdInt = Api.getClient().addGroup(groupObj);
                Response<Integer> grpIdResponse = grpIdInt.execute();
                grpId = grpIdResponse.body();
                System.out.print(" RESPONSE FROM OWNERID  "+grpId);

            }

            catch (MalformedURLException e) {
                return null;

            } catch (IOException e) {
                return null;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


            for (int i = 0; i < uIds.size(); i++)
            {
                byte[] pkbytes = keys.sTob(publicKeys.get(i));
                try
                {
                    PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(pkbytes));
                    byte[] grpKeyByte = keys.encrypt_with_PubKey(groupKeyBytes, publicKey);
                    groupKey = keys.bTos(grpKeyByte);

                }

                catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                groupObject.setGroupId(grpId);
                groupObject.setGroupName(grpName);
                groupObject.setUserId(uIds.get(i));
                groupObject.setGroupKey(groupKey);

                //alias = "groupKeyBytes";
               // String groupKeyBytes = newGrpKeyGenerate();
                //encryptString(groupKeyBytes);
               // grpData.setGroupKey(encryptedText);

                try
                {
                    Call<Integer> addtogrp = Api.getClient().addGroupWithFriends(groupObject);
                    Response<Integer> response = addtogrp.execute();
                    System.out.print(" RESPONSE FROM OWNERID  "+response);
                }

                catch (MalformedURLException e)
                {
                    return null;

                } catch (IOException e) {
                    return null;
                }
            }
            return "Group Created";
        }
    }



}
