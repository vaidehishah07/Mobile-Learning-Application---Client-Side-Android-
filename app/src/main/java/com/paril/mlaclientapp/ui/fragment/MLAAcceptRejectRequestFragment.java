package com.paril.mlaclientapp.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.paril.mlaclientapp.R;
import com.paril.mlaclientapp.model.MLAFriendRequest;
import com.paril.mlaclientapp.ui.activity.MLAHomeActivity;
import com.paril.mlaclientapp.ui.adapter.MLAAcceptRejectRequestAdapter;
import com.paril.mlaclientapp.util.KeyManager;
import com.paril.mlaclientapp.util.PrefsManager;
import com.paril.mlaclientapp.webservice.Api;

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


public class MLAAcceptRejectRequestFragment extends Fragment

{
    MLAAcceptRejectRequestAdapter mlaAcceptRejectRequestAdapter;
    List<MLAFriendRequest> listfriendsmodel = new ArrayList<>();
    Integer[] userId;
    String[] userName;
    String[] userfirstName;
    String[] userlastName;
    String[] email;
    String[] publicKey;
    PrefsManager prefsManager;
    View view;
    RecyclerView friendrecyclerview;
    byte[] publicKeyByteArrayForAcceptReq;
    PublicKey publicKeyforAccptReq;
    byte[] grpKeyByteForAcceptReq;
    String grpKeyOffriend;
    String friendgrpId;
    //TextView tv_nodata;
    String grpKey;
    Integer grpId;
    Boolean[] check;
    PrivateKey prk;
    KeyManager keyManager;
    String Vaidehi;
    String Pk;

    List<MLAFriendRequest> friendsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        prefsManager = new PrefsManager(getActivity());
        view = inflater.inflate(R.layout.fragment_friendrequest, container, false);
        friendrecyclerview = (RecyclerView) view.findViewById(R.id.friendrequest_list_recylcer_view);
       // tv_nodata = (TextView)view.findViewById(R.id.nodata);
        getcurrentuserId();

        ListRequestAPI listRequestAPI = new ListRequestAPI(MLAAcceptRejectRequestFragment.this.getActivity());
        listRequestAPI.execute();

        return view;
    }

  /*  @Override
    public void onResume() {
        super.onResume();

    }
*/
    void getcurrentuserId()
    {

        Vaidehi = prefsManager.getStringData("userId");
        Pk= prefsManager.getStringData("publicKey");

    }

    class ListRequestAPI extends AsyncTask<Void, Void, List<MLAFriendRequest>> {
        Context context;

        public ListRequestAPI(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute()
        {

            ((MLAHomeActivity) getActivity()).showProgressDialog("Fetching Friend request Details...");
        }

        @Override
        protected void onPostExecute(List<MLAFriendRequest> apiResponse)
        {
            ((MLAHomeActivity) getActivity()).hideProgressDialog();

            if (apiResponse != null ) {
                if (apiResponse.size() > 0)
                {
                    //tv_nodata.setVisibility(tv_nodata.GONE);

                    friendsList = apiResponse;
                    userId = new Integer[friendsList.size()];
                    email = new String[friendsList.size()];
                    userfirstName = new String[friendsList.size()];
                    userlastName = new String[friendsList.size()];
                    publicKey = new String[friendsList.size()];
                    //check = new Boolean[(Integer.valueOf(friendsList.size()))];

                    for (int i = 0; i < friendsList.size(); i++)
                    {
                        userId[i] = friendsList.get(i).getUserId();
                        email[i] = friendsList.get(i).getEmail();
                        userfirstName[i] = friendsList.get(i).getUserfirstName();
                        userlastName[i] = friendsList.get(i).getUserlastName();
                        publicKey[i] = friendsList.get(i).getPublicKey();
                        //check[i] = friendsList.get(i).getCheck();
                    }

                    grpId = friendsList.get(0).getGroupNo();
                    grpKey = friendsList.get(0).getGroupKey();

                    for (int i = 0; i < friendsList.size(); i++)
                    {

                        final MLAFriendRequest friendRequestConstructor = new MLAFriendRequest(userId[i], email[i], userfirstName[i], userlastName[i], publicKey[i], grpId, grpKey);
                        listfriendsmodel.add(friendRequestConstructor);
                    }

                    mlaAcceptRejectRequestAdapter = new MLAAcceptRejectRequestAdapter(MLAAcceptRejectRequestFragment.this.getActivity(), listfriendsmodel, new MLAAcceptRejectRequestAdapter.MLAHandleAcceptOrReject()
                    {

                        @Override
                        public void AcceptRejectRequest(MLAFriendRequest objFriends) {

                            if (objFriends.getCheck())
                            {

                                AcceptFriendRequestAPI mlaacceptFriendRequestAPIRequestAPI = new AcceptFriendRequestAPI(MLAAcceptRejectRequestFragment.this.getActivity(), objFriends);
                                mlaacceptFriendRequestAPIRequestAPI.execute();

                                listfriendsmodel.remove(objFriends);
                                Toast.makeText(MLAAcceptRejectRequestFragment.this.getActivity(), "Accepted", Toast.LENGTH_LONG).show();
                            } else
                                {

                                RejectFriendRequestAPI mlarejectFriendRequestAPIRequestAPI = new RejectFriendRequestAPI(MLAAcceptRejectRequestFragment.this.getActivity(), objFriends);
                                mlarejectFriendRequestAPIRequestAPI.execute();

                                listfriendsmodel.remove(objFriends);
                                Toast.makeText(MLAAcceptRejectRequestFragment.this.getActivity(), "Rejected", Toast.LENGTH_SHORT).show();
                            }

                            friendrecyclerview.getAdapter().notifyDataSetChanged();
                        }
                    });

                    friendrecyclerview.setAdapter(mlaAcceptRejectRequestAdapter);
                    friendrecyclerview.setHasFixedSize(true);
                    friendrecyclerview.setLayoutManager(new LinearLayoutManager(MLAAcceptRejectRequestFragment.this.getActivity()));

                }
                else
                {
                     //tv_nodata.setVisibility(tv_nodata.VISIBLE);
                }
            }
            else
            {
                //tv_nodata.setVisibility(tv_nodata.VISIBLE);
            }
        }

        @Override
        protected List<MLAFriendRequest> doInBackground(Void... params) {
            try {

                Call<List<MLAFriendRequest>> callFriendData = Api.getClient().gettherequesteddata(Integer.parseInt(Vaidehi));
                Response<List<MLAFriendRequest>> responseFriendData = callFriendData.execute();

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


    class AcceptFriendRequestAPI extends AsyncTask<Void, Void, String>
    {
        Context context;

        byte[] grpKey;
        byte[] grpKeyDec;

        MLAFriendRequest objectOfFriend;
        Integer FrienduserId;
        Integer FriendgroupId;
        String Friendgroupkey;
        String Friendpublickey;

        public AcceptFriendRequestAPI(Context ctx, MLAFriendRequest obj)
        {
            context = ctx;
            objectOfFriend = obj;
        }

        @Override
        protected void onPreExecute()
        {
            try
            {
                keyManager = new KeyManager(1024);
                FrienduserId = objectOfFriend.userId;
                FriendgroupId = objectOfFriend.groupNo;
                Friendgroupkey = objectOfFriend.groupKey;
                Friendpublickey = objectOfFriend.publicKey;
                grpKey = Base64.decode(Friendgroupkey,Base64.DEFAULT);

                KeyManager.Keys keyPair = KeyManager.getKeysFromKeyStore(Vaidehi,this.context.getApplicationContext());
                prk = keyPair.getPrivateKey();
                grpKeyDec = keyManager.decrypt_with_PrivateKey(grpKey, prk);

                publicKeyByteArrayForAcceptReq = keyManager.sTob(Friendpublickey);
                publicKeyforAccptReq = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyByteArrayForAcceptReq));

                 grpKeyByteForAcceptReq = keyManager.encrypt_with_PubKey(grpKeyDec, publicKeyforAccptReq);
                 grpKeyOffriend = keyManager.bTos(grpKeyByteForAcceptReq);
                  friendgrpId= FriendgroupId.toString();


            }

            catch (Exception e)
            {
                System.out.print("EXCEPTION IN PRE EXECUTE" + e);
            }
        }

        @Override
        protected void onPostExecute(String apiResponse) {
            ((MLAHomeActivity) getActivity()).hideProgressDialog();

            if (apiResponse.equals("Request Accepted"))
            {
                Toast.makeText(MLAAcceptRejectRequestFragment.this.getActivity(), "Accepted", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {

           /*byte[] publicKeyByteArrayForAcceptReq = keyManager.sTob(Friendpublickey);
           PublicKey publicKeyforAccptReq = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyByteArrayForAcceptReq));

          byte[] grpKeyByte = keyManager.encrypt_with_PubKey(grpKeyDec, publicKeyforAccptReq);
                String grpKeyOffriend = keyManager.bTos(grpKeyByte);
                String friendgrpId= FriendgroupId.toString();

                //Integer currentuserId = Integer.parseInt(Vaidehi);
*/
                Call<String> callAddFriend = Api.getClient().acptRequest(Integer.parseInt(Vaidehi),FrienduserId,grpKeyOffriend,friendgrpId);
                Response<String> responseSubjectData = callAddFriend.execute();

                System.out.println(responseSubjectData);

            } catch (MalformedURLException e)
            {
                return null;

            }  catch (Exception e)
            {
                e.printStackTrace();
            }

            return "Request Accepted";
        }


    }

    class RejectFriendRequestAPI extends AsyncTask<Void, Void, String> {
        Context context;
        MLAFriendRequest objectOfFriend;
        Integer uIdOfFriend;

        public RejectFriendRequestAPI(Context ctx, MLAFriendRequest obj)
        {
            context = ctx;
            objectOfFriend = obj;

        }

        @Override
        protected void onPreExecute()
        {
            try {

                ((MLAHomeActivity) getActivity()).showProgressDialog("selecting the accept button ...");

                uIdOfFriend = objectOfFriend.userId;

            }

            catch (Exception e)
            {
                System.out.print(e);
            }
        }

        @Override
        protected void onPostExecute(String apiResponse) {
            ((MLAHomeActivity) getActivity()).hideProgressDialog();

            if (apiResponse.equals("Request Rejected"))
            {

            }

        }

        @Override
        protected String doInBackground(Void... params) {

            try {


                //Integer currentuser = Integer.parseInt(Vaidehi);
                //String gid= groupNo.toString();
                Call<String> callAddFriendData = Api.getClient().rejRequest(uIdOfFriend,Integer.parseInt(Vaidehi));
                Response<String> responseSubjectData = callAddFriendData.execute();

                System.out.println(responseSubjectData);

            } catch (MalformedURLException e) {
                return null;

            } catch (IOException e) {
                return null;
            }

            return "Request Rejected";
        }


    }




}

































   /* MLAAcceptRejectRequestAdapter mlaAcceptRejectRequestAdapter;

    Context context;
    mlaAcceptRejectRequestAdapter = new MLAAcceptRejectRequestAdapter(context;, mSwitchList, new SwitchAdapter.UserSwitchCheckHandler()
{
    @Override
    public void HandleSwitchCheckEvent(DESwitchDetails deSwitch)
    {
        mSwitchList.remove(deSwitch);
        rvSwitchLayout.getAdapter().notifyDataSetChanged();
        //Toast.makeText(SwitchActivity.this, "" + deSwitch.getSwitchName()+"hello", Toast.LENGTH_LONG).show();
//                if(deSwitch.getCheck())
//                {
//                    Toast.makeText(SwitchActivity.this, "Switch On", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(SwitchActivity.this, "Switch Off", Toast.LENGTH_SHORT).show();
//                }
    }
});
    mlaAcceptRejectRequestAdapter = new FriendsShowAdapter(context, listfriendsmodel, new MLAAcceptRejectRequestAdapter.MLAHandleAcceptOrReject()
{


});



}
*/