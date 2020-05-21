package com.paril.mlaclientapp.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.paril.mlaclientapp.R;
import com.paril.mlaclientapp.model.PModel;
import com.paril.mlaclientapp.ui.activity.MLACreatePost;
import com.paril.mlaclientapp.ui.activity.MLAHomeActivity;
import com.paril.mlaclientapp.ui.adapter.MLAPostAdapter;
import com.paril.mlaclientapp.util.CommonUtils;
import com.paril.mlaclientapp.util.KeyManager;
import com.paril.mlaclientapp.util.PrefsManager;
import com.paril.mlaclientapp.webservice.Api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Response;



public class MLAPostFragment extends Fragment {

    com.paril.mlaclientapp.ui.view.EmptyRecyclerView recyclerViewPosts;
    MLAPostAdapter postDisplayAdapter;
    private PrefsManager manager;
    View view;
    String Vaidehi;
    List<PModel> postDetails = new ArrayList<>();
    String[] Post_text;
    String[] SessionKey;
    String[] PublicKey;
    String[] GroupKey;
    Integer[] GroupId;
    String[] DigitalSignature;
    String[] lastname;
    String[] firstname;
    String[] originalfirstname;
    String[] originallastname;
    Integer[] originalPostID;
    Integer [] Post_id;
    Integer [] postType;
    KeyManager keysForPost;
    ListView listViewShowPost;
    FloatingActionButton btnNewPost;
    boolean verifiedSign;
    PrefsManager prefsManager;
    String Pk;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        prefsManager = new PrefsManager(getActivity());
        view = inflater.inflate(R.layout.fragment_mla_post, container, false);
        listViewShowPost = (ListView) view.findViewById(R.id.mla_show_post_listView);
        btnNewPost = (FloatingActionButton)view.findViewById(R.id.fragment_display_post_fabAddUser);
        /*recyclerViewPosts = (com.paril.mlaclientapp.ui.view.EmptyRecyclerView) view.findViewById(R.id.mla_post_display_recyyclerView);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewPosts.addItemDecoration(new VerticalSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.divider_list)));
*/
        manager = new PrefsManager(getActivity());

        intentService();

        MLAGetAllPostDetailsAPI getPostDetails = new MLAGetAllPostDetailsAPI(this.getActivity());
        getPostDetails.execute();


        view.findViewById(R.id.fragment_display_post_fabAddUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                Intent previous = getIntent();
//                Bundle bundle = previous.getExtras();
//                if (bundle != null)
// {
//                    user.userId = (String) bundle.get("userId");
//                    user.userName = (String) bundle.get("userName");
//                    user.userType = (String) bundle.get("userType");
                //}

                final Intent intent = new Intent(getActivity(), MLACreatePost.class);
                startActivity(intent);
            }
        });


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (CommonUtils.checkInternetConnection(getActivity())) {

            MLAGetAllPostDetailsAPI getPostDetails = new MLAGetAllPostDetailsAPI(this.getActivity());
            getPostDetails.execute();

        } else
            {
            //((MLAHomeActivity) getActivity()).showSnackBar(getString(R.string.check_connection), view.findViewById(R.id.fragment_display_admin_coordinatorLayout));
        }
    }



    void intentService() {
        /*Intent previous = this.getActivity().getIntent();
        Bundle bundle = previous.getExtras();
        if (bundle != null)
        {

            Vaidehi = (String)bundle.get("userId");
            System.out.println("USER ID ="+Vaidehi);

            //user.userName = (String) bundle.get("userName");
            //user.userType = (String) bundle.get("userType");

        }*/

        Vaidehi = prefsManager.getStringData("userId");
        Pk= prefsManager.getStringData("publicKey");
    }



/*
    @Override
    public void onResume()
    {
        super.onResume();
        if (CommonUtils.checkInternetConnection(getActivity())) {
            MLAGetAllPostDetailsAPI getPostDetails = new MLAGetAllPostDetailsAPI(this.getActivity());
            getPostDetails.execute();
        } else {
            ((MLAHomeActivity) getActivity()).showSnackBar(getString(R.string.check_connection), view.findViewById(R.id.fragment_display_post_coordinatorLayout));
        }

    }*/

    class MLAGetAllPostDetailsAPI extends AsyncTask<Void, Void, List<PModel>> {
        Context context;
        byte[] grpKeyDec;
        String[] postText;

        public MLAGetAllPostDetailsAPI(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {

            try {
                keysForPost = new KeyManager(1024);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            }

            ((MLAHomeActivity) getActivity()).showProgressDialog("Getting Post Data...");
        }

        @Override
        protected void onPostExecute(List<PModel> apiResponse)
        {
            ((MLAHomeActivity) getActivity()).hideProgressDialog();


            if (apiResponse.size() != 0)
            {
                postDetails = apiResponse;
                Post_id = new Integer[postDetails.size()];
                Post_text = new String[postDetails.size()];
                postType = new Integer[postDetails.size()];
                GroupKey = new String[postDetails.size()];
                GroupId = new Integer[postDetails.size()];
                SessionKey = new String[postDetails.size()];
                DigitalSignature = new String[postDetails.size()];
                PublicKey = new String[postDetails.size()];
                firstname = new String[postDetails.size()];
                lastname = new String[postDetails.size()];
                originalfirstname = new String[postDetails.size()];
                originallastname = new String[postDetails.size()];
                originalPostID = new Integer[postDetails.size()];


                for (int i = 0; i < postDetails.size(); i++)
                {
                    try {
                        Post_id[i] = postDetails.get(i).getPostId();
                        Post_text[i] = postDetails.get(i).getPostText();
                        postType[i] = postDetails.get(i).getPostType();
                        GroupKey[i] = postDetails.get(i).getGroupkey();
                        GroupId[i] = postDetails.get(i).getGroupId();
                        SessionKey[i] = postDetails.get(i).getSessionKey();
                        DigitalSignature[i] = postDetails.get(i).getDigitalSignature();
                        PublicKey[i] = postDetails.get(i).getPublickey();
                        firstname[i] = postDetails.get(i).getFirstname();
                        lastname[i] = postDetails.get(i).getLastname();
                        originalfirstname[i] = postDetails.get(i).getOriginalfirstname();
                        originallastname[i] = postDetails.get(i).getOriginallastname();
                        originalPostID[i] = postDetails.get(i).getOriginalPostId();


                        if (postType[i] == 4)
                        {
                            Post_text[i] = postDetails.get(i).getPostText();
                        }
                        else
                            {
                            // get the Private key of current user
                            KeyManager.Keys keyPair = KeyManager.getKeysFromKeyStore(Vaidehi, this.context.getApplicationContext());
                            PrivateKey prk = keyPair.getPrivateKey();

                            // first decrypt_with_PrivateKey the group key with user's private key
                            grpKeyDec = keysForPost.decrypt_with_PrivateKey(keysForPost.sTob(GroupKey[i]), prk);

                            //byte[] encodedKey    = Base64.decode(grpKeyDec, Base64.DEFAULT);
                            SecretKey grpKeyDec1 = new SecretKeySpec(grpKeyDec, 0, grpKeyDec.length, "AES");

                            // second step - decrypt_with_PrivateKey the session key with the  orignal group key grpKeyDec
                            String sessionKeyDec = keysForPost.postDecryptionusingSessionKey(SessionKey[i], grpKeyDec1);

                            SecretKey sessionKeySk = new SecretKeySpec(keysForPost.sTob(sessionKeyDec), 0, keysForPost.sTob(sessionKeyDec).length, "AES");

                            // decrypt_with_PrivateKey the post with the session key
                            String tpostText = keysForPost.postDecryptionusingSessionKey(Post_text[i], sessionKeySk);
                            System.out.println(tpostText);
                            Post_text[i] = tpostText;

                        }
                    }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                }

                List<PModel> listUserDisplayCheckb = new ArrayList<>();
                for (int i = 0; i < postDetails.size(); i++)
                {
                    if(postType[i] == 4)
                    {
                        final PModel usersDisplayProvider = new PModel(Post_id[i],Post_text[i],firstname[i],lastname[i],originalfirstname[i],originallastname[i],originalPostID[i],postType[i],GroupId[i],GroupKey[i],SessionKey[i]);
                        listUserDisplayCheckb.add(usersDisplayProvider);
                    }

                    else
                        {
                        try {
                            byte[] pk = keysForPost.sTob(PublicKey[i]);
                            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(pk));
                            verifiedSign = keysForPost.verifySign(Post_text[i].getBytes(), keysForPost.sTob(DigitalSignature[i]), publicKey);
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        if (verifiedSign == true)
                        {
                            final PModel usersDisplayProvider = new PModel(Post_id[i], Post_text[i], firstname[i], lastname[i], originalfirstname[i], originallastname[i], originalPostID[i], postType[i],GroupId[i],GroupKey[i],SessionKey[i]);
                            listUserDisplayCheckb.add(usersDisplayProvider);
                        }
                    }
                }

                /*postDisplayAdapter = new MLAPostAdapter(context, listUserDisplayCheckb);
                recyclerViewPosts.setAdapter(postDisplayAdapter);
                recyclerViewPosts.setEmptyView(getView().findViewById(R.id.fragment_display_post_relEmptyView));
*/
                postDisplayAdapter = new MLAPostAdapter(context, listUserDisplayCheckb);
                listViewShowPost = (ListView) view.findViewById(R.id.mla_show_post_listView);
                //listViewAddGroup = (ListView)view.findViewById(R.id.mla_create_group_display_listView);
                listViewShowPost.setAdapter(postDisplayAdapter);

            }


            else
                {
                postDisplayAdapter = new MLAPostAdapter(context, new ArrayList<PModel>());
                listViewShowPost = (ListView) view.findViewById(R.id.mla_show_post_listView);
                listViewShowPost.setAdapter(postDisplayAdapter);
            }

        }

        @Override
        protected List<PModel> doInBackground(Void... params) {

            try {

                Call<List<PModel>> callPostData = Api.getClient().getAllPosts(Integer.parseInt(Vaidehi));
                Response<List<PModel>> responsePostData = callPostData.execute();
                if (responsePostData.isSuccessful() && responsePostData.body() != null) {
                    return responsePostData.body();
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

}
