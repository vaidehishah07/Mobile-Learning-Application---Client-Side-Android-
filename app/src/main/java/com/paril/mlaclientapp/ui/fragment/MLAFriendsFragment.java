package com.paril.mlaclientapp.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.paril.mlaclientapp.R;
import com.paril.mlaclientapp.model.FriendCheckboxModel;
import com.paril.mlaclientapp.model.FriendEncyModel;
import com.paril.mlaclientapp.model.FriendModel;
//import com.paril.mlaclientapp.model.FriendEncyModel;
import com.paril.mlaclientapp.ui.activity.MLAHomeActivity;
import com.paril.mlaclientapp.ui.adapter.MLAFriendsAdapter;
import com.paril.mlaclientapp.util.PrefsManager;
import com.paril.mlaclientapp.webservice.Api;

import com.paril.mlaclientapp.util.KeyManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MLAFriendsFragment extends Fragment
{

    Context ctx;
    Button FriendsCreateBtn;
    Button btnFriendReq;
    ListView AddfriendLstView;
    ArrayList<Integer> userIds;
    ArrayList<String> publickey1;
    List<FriendModel> friendsList = new ArrayList<>();
    String[] publickey;
    String[] status;
    String[] fullName;
    String[] emailid;
    Integer[] userId1;
    String groupKey;
    View view;


    MLAFriendsAdapter mlaFriendsAdapter;
    String Vaidehi;
    String Pk;
    KeyManager keyManager;
    PrivateKey privateKey;
    PrefsManager prefsManager;

    boolean flag1 = true;
    TextView reqMessage;


    void getcurrentuserId()
    {
        Vaidehi = prefsManager.getStringData("userId");
        Pk= prefsManager.getStringData("publicKey");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        prefsManager = new PrefsManager(getActivity());

        view = inflater.inflate(R.layout.fragment_friend, container, false);
        getcurrentuserId();
        AddfriendLstView = (ListView) view.findViewById(R.id.mla_add_friend_listView);
        AddfriendLstView.setEmptyView(view.findViewById(R.id.empty_text_view));

        FriendsCreateBtn = (Button)view.findViewById(R.id.mla_add_friend_btn);
        reqMessage = (TextView)view.findViewById(R.id.messsage_accept_req_text_view);
        GetRegisteredUsersForFriendsAPI getRegisteredUsersForFriendsAPI = new GetRegisteredUsersForFriendsAPI(MLAFriendsFragment.this.getActivity());
        getRegisteredUsersForFriendsAPI.execute();

        FriendsCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mlaFriendsAdapter != null && mlaFriendsAdapter.getCount() > 0) {

                    int listSize = mlaFriendsAdapter.getCount();
                    FriendCheckboxModel friendCheckbox;
                    userIds = new ArrayList<>();
                    publickey1 = new ArrayList<>();
                    for (int i = 0; i < listSize; i++)
                    {
                        friendCheckbox = (FriendCheckboxModel) mlaFriendsAdapter.getItem(i);
                        if (friendCheckbox.getCheck()) {
                            userIds.add(friendCheckbox.getId());
                            publickey1.add(friendCheckbox.getPublickey());
                        }
                    }
                    if(userIds.size()>0){
                        AddFriendsAPI addFriendsAPI = new AddFriendsAPI(MLAFriendsFragment.this.getActivity());
                        addFriendsAPI.execute();
                    }
                }
            }
        });


        return view;
    }

    class GetRegisteredUsersForFriendsAPI extends AsyncTask<String, Void, List<FriendModel>> {
        Context context;

        public GetRegisteredUsersForFriendsAPI(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            ((MLAHomeActivity) getActivity()).showProgressDialog("Fetching Users ...");

        }

        @Override
        protected void onPostExecute(List<FriendModel> apiResponse) {
            ((MLAHomeActivity) getActivity()).hideProgressDialog();

            if (apiResponse != null) {
                friendsList = apiResponse;
                userId1 = new Integer[friendsList.size()];
                fullName = new String[friendsList.size()];
                emailid = new String[friendsList.size()];
                publickey = new String[friendsList.size()];
                status = new String[friendsList.size()];

                for (int i = 0; i < friendsList.size(); i++)
                {
                    userId1[i] = friendsList.get(i).getUserId();
                    fullName[i] = friendsList.get(i).getUserfirstName();
                    emailid[i] = friendsList.get(i).getEmail();
                    publickey[i] = friendsList.get(i).getPublicKey();
                    status[i] = friendsList.get(i).getFlag();
                }

                groupKey = friendsList.get(0).getGroupKey();
                List<FriendCheckboxModel> friendchck = new ArrayList<>();

                for (int i = 0; i < friendsList.size(); i++)
                {

                    if (status[i].equals(""))
                    {
                        final FriendCheckboxModel friendCheckboxModel = new FriendCheckboxModel(userId1[i], fullName[i], emailid[i], publickey[i], false, status[i]);
                        friendchck.add(friendCheckboxModel);
                    }
                    else
                    {
                        flag1 = false;
                    }
                }
                mlaFriendsAdapter = new MLAFriendsAdapter(context, friendchck);
                AddfriendLstView = (ListView)view.findViewById(R.id.mla_add_friend_listView);
                AddfriendLstView.setAdapter(mlaFriendsAdapter);

            } else

                {
                mlaFriendsAdapter = new MLAFriendsAdapter(context, new ArrayList<FriendCheckboxModel>());
                AddfriendLstView = (ListView)view.findViewById(R.id.mla_add_friend_listView);
                AddfriendLstView.setAdapter(mlaFriendsAdapter);
             }

            if (flag1 == false)
            {
                reqMessage.setVisibility(View.VISIBLE);

            }

        }

        @Override
        protected List<FriendModel> doInBackground(String... params) {
            try {

                Call<List<FriendModel>> notinfriendlist = Api.getClient().notinFriendList(Integer.parseInt(Vaidehi));
                Response<List<FriendModel>> responseStudentData = notinfriendlist.execute();
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


    class AddFriendsAPI extends AsyncTask<Void, Void, String> {

        Context context;
        byte[] grpKeyDecInbyte;
        byte[] grpKey;
        ArrayList<Integer> ids;
        ArrayList<String> publicKey;

        public AddFriendsAPI(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute()
        {
            ids = userIds;
            publicKey = publickey1;
            try {


                grpKey = Base64.decode(groupKey,Base64.DEFAULT);
                keyManager = new KeyManager(1024);
                KeyManager.Keys keyPair = KeyManager.getKeysFromKeyStore(Vaidehi,this.context.getApplicationContext());
                privateKey = keyPair.getPrivateKey();
                PublicKey pk = keyPair.getPublicKey();
                String pubKey = keyManager.bTos(pk.getEncoded());
                grpKeyDecInbyte = keyManager.decrypt_with_PrivateKey(grpKey, privateKey);


            }catch (Exception e){
                System.out.println(e);
            }
        }

        @Override
        protected void onPostExecute(String statusCode)
        {

            if (statusCode.equals("Friend added"))
            {
                GetRegisteredUsersForFriendsAPI getRegisteredUsersForFriendsAPI = new GetRegisteredUsersForFriendsAPI(MLAFriendsFragment.this.getActivity());
                getRegisteredUsersForFriendsAPI.execute();
                ((MLAHomeActivity) getActivity()).showSnackBar("Added as friend.", view.findViewById(R.id.fragment_add_friend_coordinatorLayout));
            } else {
                ((MLAHomeActivity) getActivity()).showSnackBar("Error while adding friend.", view.findViewById(R.id.fragment_add_friend_coordinatorLayout));
            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                for (int i = 0; i < ids.size(); i++) {

                    final FriendEncyModel friendObj = new FriendEncyModel();
                    friendObj.setFromuserid(Integer.parseInt(Vaidehi));
                    friendObj.setTouserid(ids.get(i));
                    byte[] publicKeyByte = keyManager.sTob(publicKey.get(i));
                    PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyByte));
                    byte[] grpKeyByte = keyManager.encrypt_with_PubKey(grpKeyDecInbyte, publicKey);
                    String gk1 = keyManager.bTos(grpKeyByte);
                    friendObj.setGroupkey(gk1);

                try {
                    Call<Integer> callAddFriendData = Api.getClient().addFriend(friendObj);
                    Response<Integer> responseSubjectData = callAddFriendData.execute();
                    System.out.println(responseSubjectData);
                }
                catch (MalformedURLException e) {
                    return null;
                } catch (IOException e) {
                    return null;
                } catch (Exception e) {
                    System.out.println("" + e);
                }
            }
            }catch (Exception e){}
            return "Friend added";
        }
    }


}
