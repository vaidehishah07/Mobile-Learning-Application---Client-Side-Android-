package com.paril.mlaclientapp.ui.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.paril.mlaclientapp.R;
import com.paril.mlaclientapp.model.GroupModel;
import com.paril.mlaclientapp.ui.activity.MLAHomeActivity;
import com.paril.mlaclientapp.ui.adapter.MLAUpdateGroupAdapter;
import com.paril.mlaclientapp.util.KeyManager;
import com.paril.mlaclientapp.util.PrefsManager;
import com.paril.mlaclientapp.webservice.Api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MLAUpdateGroupFragment extends Fragment
{
    ListView listViewStudent;
    Spinner spnrGroup;
    Button btnAddtoGroup;
    String Vaidehi;
    String Pk;
    KeyManager keys;


    MLAUpdateGroupAdapter userDisplayAdapter;

    List<GroupModel> currentgroupDetails = new ArrayList<>();

    List<GroupModel> friendList = new ArrayList<>();

    Integer[] userId;
    String [] firstName;
    String [] lastName;
    String [] emailId;
    String [] publicKey;

    ArrayList<String> publicKey1;
    ArrayList<Integer> userIdArray1;
    ArrayList<Integer> groupId1;
    ArrayList<String> groupKey1;
    ArrayList<String> groupName1;
    PrefsManager prefsManager;
    GroupModel grpobj = new GroupModel();

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        prefsManager = new PrefsManager(getActivity());
        view = inflater.inflate(R.layout.fragment_mla_updategroup, container, false);
        listViewStudent = (ListView) view.findViewById(R.id.mla_updategroup_display_listView);
        listViewStudent.setEmptyView(view.findViewById(R.id.empty_updategroup_text_view));
        btnAddtoGroup = (Button) view.findViewById(R.id.mla_updategroup_btn);
        spnrGroup = (Spinner) view.findViewById(R.id.mla_updategroup_spnrgroupId);

        getCuserAndPk();


        MLAGetAllGroupForUpdateAPI mlaGetAllSubjectAPI = new MLAGetAllGroupForUpdateAPI(this.getActivity());
        mlaGetAllSubjectAPI.execute();

        spnrGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                GroupModel user = (GroupModel) parent.getSelectedItem();

                MLAGetFriendListNotIngroupAPI mlaGetDeEnrollBySubjectAPI = new MLAGetFriendListNotIngroupAPI(MLAUpdateGroupFragment.this.getActivity(),user);
                mlaGetDeEnrollBySubjectAPI.execute();
                //displayUserData(user);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAddtoGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//if()
                if (userDisplayAdapter != null && userDisplayAdapter.getCount() > 0)
                {

                    // the userDisplayadapter include the boolean values and the userName of the student.
                    int listSize = userDisplayAdapter.getCount();//this is the list size
                    GroupModel userDisplayCheckbxProvider;

                    userIdArray1 = new ArrayList<>();
                    publicKey1 = new ArrayList<>();
                    groupId1 = new ArrayList<>();
                    groupName1 = new ArrayList<>();
                    groupKey1 = new ArrayList<>();

                    //int temp1 = Integer.parseInt(Vaidehi);
                    //unArray.add(temp1);

                    for (int i = 0; i < listSize; i++)
                    {

                        userDisplayCheckbxProvider = (GroupModel) userDisplayAdapter.getItem(i);
                        //String groupname = userDisplayCheckbxProvider.getGroupName();

                        if (userDisplayCheckbxProvider.getCheck())
                        {
                            userIdArray1.add(userDisplayCheckbxProvider.getUserId()); // userId= id of selected Friend
                            publicKey1.add(userDisplayCheckbxProvider.getPublicKey());
                            groupId1.add(userDisplayCheckbxProvider.getGroupId());
                            groupName1.add(userDisplayCheckbxProvider.getGroupName());
                            groupKey1.add(userDisplayCheckbxProvider.getGroupKey());
                            // here  we will get the groupKey also

                        }
                    }

                    // if the friends are selected from the list then call the API.
                    if(userIdArray1.size()>0)
                    {
                        MLAAddFriendsToUpdatedGroupAPI mlaAddGroupAPI = new MLAAddFriendsToUpdatedGroupAPI(MLAUpdateGroupFragment.this.getActivity());
                        mlaAddGroupAPI.execute();
                    }

                }

            }
        });

        return view;
    }


    void getCuserAndPk() {
        /*Intent previous = this.getActivity().getIntent();
        Bundle bundle = previous.getExtras();
        if (bundle != null) {

            Vaidehi = (String) bundle.get("userId");
            System.out.println("USER ID =" + Vaidehi);

            PkVaidehi = (String) bundle.get("publicKey");
            System.out.println("Public Key =" + PkVaidehi);

        }*/

        Vaidehi = prefsManager.getStringData("userId");
        Pk= prefsManager.getStringData("publicKey");


    }




    class MLAGetAllGroupForUpdateAPI extends AsyncTask<Void, Void, List<GroupModel>> {
        Context context;
        Integer [] groupId;
        String [] groupName;
        String [] groupKey;


        public MLAGetAllGroupForUpdateAPI(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            ((MLAHomeActivity) getActivity()).showProgressDialog("Fetching Subjects...");
        }

        @Override
        protected void onPostExecute(List<GroupModel> listSubjectDetail) {

            ((MLAHomeActivity) getActivity()).hideProgressDialog();

            if (listSubjectDetail != null && listSubjectDetail.size() > 0)
            {
                currentgroupDetails = listSubjectDetail;
                groupId = new Integer[currentgroupDetails.size()];
                groupName = new String[currentgroupDetails.size()];
                groupKey = new String[currentgroupDetails.size()];


                for (int i = 0; i < currentgroupDetails.size(); i++)
                {
                    groupId[i] = currentgroupDetails.get(i).groupId;
                    groupName[i] = currentgroupDetails.get(i).groupName;
                    groupKey[i] = currentgroupDetails.get(i).groupKey;

                }
                ArrayAdapter<GroupModel> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,currentgroupDetails);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrGroup.setAdapter(arrayAdapter);
                 }

                 else
                {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, new String[]{});
                spnrGroup.setAdapter(arrayAdapter);
                ((MLAHomeActivity) getActivity()).showSnackBar("There is no subject to enroll.", getView().findViewById(R.id.fragment_mla_updategroup));
            }
        }

        @Override
        protected List<GroupModel> doInBackground(Void... params) {
            try {
                Call<List<GroupModel>> callSubjectData = Api.getClient().getlistofGroups(Integer.parseInt(Vaidehi));
                Response<List<GroupModel>> responseSubjectData = callSubjectData.execute();

                if (responseSubjectData.isSuccessful() && responseSubjectData.body() != null) {
                    return responseSubjectData.body();
                } else
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

   class MLAGetFriendListNotIngroupAPI extends AsyncTask<Void, Void, List<GroupModel>> {
        Context context;
       GroupModel grpdetails;
       Integer groupId;
        String groupKey;
        String groupName;


        public MLAGetFriendListNotIngroupAPI(Context ctx, GroupModel group) {
            context = ctx;
            //grpdetails = group;
            groupKey = group.groupKey;
            groupId = group.groupId;
            groupName = group.groupName;

        }

        @Override
        protected void onPreExecute() {
            ((MLAHomeActivity) getActivity()).showProgressDialog("Fetching Students ...");


        }

        @Override
        protected void onPostExecute(List<GroupModel> apiResponse) {
            ((MLAHomeActivity) getActivity()).hideProgressDialog();

            if (apiResponse != null)
            {

                friendList = apiResponse;
                userId = new Integer[friendList.size()];
                emailId = new String[friendList.size()];
                firstName = new String[friendList.size()];
                lastName = new String[friendList.size()];
                publicKey = new String[friendList.size()];


                for (int i = 0; i < friendList.size(); i++)
                {

                    userId[i] = friendList.get(i).getUserId();
                    emailId[i] = friendList.get(i).getEmailId();
                    firstName[i] = friendList.get(i).getFirstName();
                    lastName[i] = friendList.get(i).getLastName();
                    publicKey[i] = friendList.get(i).getPublicKey();

                }

                List<GroupModel> listUserDisplayCheckb = new ArrayList<>();
                for (int i = 0; i < friendList.size(); i++) {
                    final GroupModel usersDisplayProvider = new GroupModel( false, userId[i],emailId[i], firstName[i],lastName[i], publicKey[i],groupId,groupName,groupKey);
                    listUserDisplayCheckb.add(usersDisplayProvider);
                }

                userDisplayAdapter = new MLAUpdateGroupAdapter(context, listUserDisplayCheckb);
                //listViewStudent = (ListView) view.findViewById(R.id.mla_updategroup_display_listView);
                listViewStudent.setAdapter(userDisplayAdapter);

            }
            else {
                userDisplayAdapter = new MLAUpdateGroupAdapter(context, new ArrayList<GroupModel>());
                //listViewStudent = (ListView) view.findViewById(R.id.empty_updategroup_text_view);
                listViewStudent.setAdapter(userDisplayAdapter);
            }
        }

        @Override
        protected List<GroupModel> doInBackground(Void... params) {
            try {

                Call<List<GroupModel>> callStudentData = Api.getClient().getlistofFriendsforUpdate(groupId,Integer.parseInt(Vaidehi));
                Response<List<GroupModel>> responseStudentData = callStudentData.execute();
                if (responseStudentData.isSuccessful() && responseStudentData.body() != null) {
                    return responseStudentData.body();
                } else {

                    return null;
                }

            } catch (MalformedURLException e) {
                return null;

            } catch (IOException e) {
                return null;
            }
        }
    }


    class MLAAddFriendsToUpdatedGroupAPI extends AsyncTask<Void, Void, String> {

        Context context;
        ArrayList<String> publicKey2;
        ArrayList<Integer> userIdArray2;
        ArrayList<Integer> groupId2;
        ArrayList<String> groupKey2;
        ArrayList<String> groupName2;
        byte [] decGrpKey;
        String grpKeyStr;

        /*//String idSubjectData;
        ArrayList<Integer> uIds;
        Integer response;
        String grpName;
        ArrayList<String> publicKeys;
        byte[] groupKeyBytes;
        GroupModel groupObj = new GroupModel();
        GroupModel groupObject = new GroupModel();
        String groupKey;*/


        public MLAAddFriendsToUpdatedGroupAPI(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute()
        {

            try {

                publicKey2 = publicKey1;
                userIdArray2 = userIdArray1;
                groupId2 = groupId1;
                groupKey2 = groupKey1;
                groupName2 = groupName1;


                keys = new KeyManager(1024);
                KeyManager.Keys keyPair = KeyManager.getKeysFromKeyStore(Vaidehi,this.context.getApplicationContext());
                PrivateKey prk1 = keyPair.getPrivateKey();
                decGrpKey =  keys.decrypt_with_PrivateKey((keys.sTob(groupKey2.get(0))),prk1);

            }
            catch (Exception e){

            }

        }

        @Override
        protected void onPostExecute(String statusCode)
        {

            if (statusCode.equals("Group Created")) //the item is created
            {

                /*ShowFriendsToAddInGroupAPI mlaFriendDisplayAPI = new ShowFriendsToAddInGroupAPI(MLAAddFriendsToGroupFragment.this.getActivity());
                mlaFriendDisplayAPI.execute();
*/
               // getActivity().onBackPressed();

                MLAPostFragment mlaPostFragment = new MLAPostFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mla_fragmentholder, mlaPostFragment);
                fragmentTransaction.commit();


               /* getActivity().finish();*/
                //((MLAHomeActivity) getActivity()).showSnackBar("Added in group.", view.findViewById(R.id.mla_addgroup_linerlayout));
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

            for (int i = 0; i < userIdArray2.size(); i++)
            {

               byte[] publicKeyByte = keys.sTob(publicKey2.get(i));

                try
                {
                    PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyByte));
                    byte[] grpKeyByte = keys.encrypt_with_PubKey(decGrpKey, publicKey);
                    grpKeyStr = keys.bTos(grpKeyByte);

                }

                catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                grpobj.setGroupId(groupId2.get(i));
                grpobj.setGroupName(groupName2.get(i));
                grpobj.setUserId(userIdArray2.get(i));
                grpobj.setGroupKey(grpKeyStr);

          try
                {
                    Call<Integer> callAddFriendData = Api.getClient().addGroup(grpobj);
                    Response<Integer> responseSubjectData = callAddFriendData.execute();
                    System.out.print(" RESPONSE FROM OWNERID  "+responseSubjectData);
                }

                catch (MalformedURLException e) {
                    return null;

                } catch (IOException e) {
                    return null;
                }
            }
            return "Group Created";
        }
    }

}
