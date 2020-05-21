package com.paril.mlaclientapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.paril.mlaclientapp.R;
import com.paril.mlaclientapp.model.GDModel;
import com.paril.mlaclientapp.model.PModel;
//import com.paril.mlaclientapp.model.MLAPublicKeyModel;
import com.paril.mlaclientapp.ui.adapter.GListInPostAdapter;
import com.paril.mlaclientapp.util.KeyManager;
import com.paril.mlaclientapp.util.PrefsManager;
import com.paril.mlaclientapp.webservice.Api;

import java.io.IOException;
import java.net.MalformedURLException;


import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Response;

public class MLAAddPost extends BaseActivity {

    View view;
    String userId;
    //ChipsInput chipsInput;
    //Context ctx;
    int owner;

    public String message;

    String userType;
    PublicKey publicKey;
    PrivateKey privateKey;
    byte[] encryptedMessage;
    String encryptedString;
    Spinner spnrGroup;

    String[] groupname;
    Integer[] groupId;
    Integer[] groupType;
    String[] groupKey;
    Integer postType;
    String Vaidehi;
    String Pk;
    Intent intent;
    KeyManager keys;

    ArrayList<Integer> grpId1;
    ArrayList<String> grpKey1;
    PrivateKey prk;

    List<GDModel> myGroups = new ArrayList<GDModel>();
    GListInPostAdapter userDisplayAdapter;
    ListView listViewGroups;
    Button sendpost;
    private CheckBox chkprivate, chkbetweenfriends, chkingroup,chkinpublic;
    PrefsManager prefsManager;
    //private boolean isToAdd = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        prefsManager = new PrefsManager(this);
        setContentView(R.layout.activity_mla_add_post);
        chkprivate = (CheckBox) findViewById(R.id.in_private_chckbx);
        chkbetweenfriends = (CheckBox) findViewById(R.id.between_friends_chckbx);
        chkingroup = (CheckBox) findViewById(R.id.in_group_chckbx);
        chkinpublic = (CheckBox) findViewById(R.id.in_public_chckbx);


       // spnrGroup = (Spinner) findViewById(R.id.mla_post_type_spnr);
        listViewGroups = (ListView) findViewById(R.id.mla_group_forpost_display_listView);
        sendpost = (Button) findViewById(R.id.mla_send_post_btn);

        intent = getIntent();
        message = intent.getStringExtra("Post_message");

        loadingUserInformation();

      /*  spnrGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                // for public
                if (position == 1) {
                    postType = 1;
                    MLAGroupForPost getConversation = new MLAGroupForPost(MLAAddPost.this, spnrGroup.getSelectedItemPosition());
                    getConversation.execute();
                }

                else if (position == 2)
                {
                    postType = 2;
                    MLAGroupForPost getConversation = new MLAGroupForPost(MLAAddPost.this, spnrGroup.getSelectedItemPosition());
                    getConversation.execute();
                }

                else if (position == 0)
                {
                    postType = 3;
                    MLAGroupForPost getConversation = new MLAGroupForPost(MLAAddPost.this, 3);
                    getConversation.execute();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });*/


        sendpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 0 = private, 1 = public, 2 = grp
                if (userDisplayAdapter != null && userDisplayAdapter.getCount() > 0 && postType ==2)
                {

                    int listSize = userDisplayAdapter.getCount();//this is the list size
                    GDModel userDisplayCheckbxProvider;
                    grpId1 = new ArrayList<>();
                    grpKey1 = new ArrayList<>();

                    //int temp1 = Integer.parseInt(Vaidehi);
                    //userNames1.add(temp1);

                    for (int i = 0; i < listSize; i++)
                    {

                        userDisplayCheckbxProvider = (GDModel) userDisplayAdapter.getItem(i);

                        if (userDisplayCheckbxProvider.getCheck()) {
                            grpId1.add(userDisplayCheckbxProvider.getGroupId()); // userId= id of selected Friend
                            //userNames1.add(Integer.valueOf(userDisplayCheckbxProvider.getPublicKey()));
                            grpKey1.add(userDisplayCheckbxProvider.getGroupKey());
                        }
                    }


                    if (grpId1.size() > 0)
                    {
                        MLASharePost mlaSharePost = new MLASharePost(MLAAddPost.this);
                        mlaSharePost.execute();
                    }

                }

                else if (postType == 1  || postType == 3)
                {

                    int listSize = userDisplayAdapter.getCount();//this is the list size
                    GDModel userDisplayCheckbxProvider;
                    grpId1 = new ArrayList<>();
                    grpKey1 = new ArrayList<>();

                    for (int i = 0; i < listSize; i++) {

                        userDisplayCheckbxProvider = (GDModel)userDisplayAdapter.getItem(i);

                            grpId1.add(userDisplayCheckbxProvider.getGroupId()); // userId= id of selected Friend
                            //userNames1.add(Integer.valueOf(userDisplayCheckbxProvider.getPublicKey()));
                            grpKey1.add(userDisplayCheckbxProvider.getGroupKey());

                    }

                    if (grpId1.size() > 0)
                    {
                        MLASharePost mlaSharePost = new MLASharePost(MLAAddPost.this);
                        mlaSharePost.execute();
                    }
                }

                else if (postType == 4)
                {
                    MLASharePost mlaSharePost = new MLASharePost(MLAAddPost.this);
                    mlaSharePost.execute();
                }

            }
        });

    }


    void loadingUserInformation() {
        Vaidehi = prefsManager.getStringData("userId");
        Pk = prefsManager.getStringData("publicKey");

    }


    public void onChckClick(View view)
    {
        switch(view.getId())
        {
            case R.id.in_private_chckbx:
                chkprivate.setChecked(true);
                chkbetweenfriends.setChecked(false);
                chkingroup.setChecked(false);
                chkinpublic.setChecked(false);

                postType = 3;
                MLAGroupForPost getConversation = new MLAGroupForPost(MLAAddPost.this, 3);
                getConversation.execute();
                break;

            case R.id.between_friends_chckbx:
                chkbetweenfriends.setChecked(true);
                chkprivate.setChecked(false);
                chkingroup.setChecked(false);
                chkinpublic.setChecked(false);

                postType = 1;
                MLAGroupForPost getbetweenfridns = new MLAGroupForPost(MLAAddPost.this, 1);
                getbetweenfridns.execute();

                break;

            case R.id.in_group_chckbx:
                chkingroup.setChecked(true);
                chkprivate.setChecked(false);
                chkbetweenfriends.setChecked(false);
                chkinpublic.setChecked(false);

                postType =2 ;
                MLAGroupForPost getInGroup = new MLAGroupForPost(MLAAddPost.this, 2);
                getInGroup.execute();

                break;

            case R.id.in_public_chckbx:
                chkinpublic.setChecked(true);
                chkingroup.setChecked(false);
                chkprivate.setChecked(false);
                chkbetweenfriends.setChecked(false);

                postType =4 ;
                MLAGroupForPost getInPublic = new MLAGroupForPost(MLAAddPost.this, 4);
                getInPublic.execute();

        }
    }


    class MLAGroupForPost extends AsyncTask<Void, Void, List<GDModel>> {
        Context pcontext;
        Integer postType1;

        public MLAGroupForPost(Context ctx, Integer position) {
            pcontext = ctx;
            postType1 = position;
            System.out.println("POST TYPE" + postType1);
        }

        @Override
        protected void onPreExecute() {
            //showProgressDialog("Getting Subject Data...");
            //Toast.makeText(pcontext, "In pre execute", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(List<GDModel> myGroups) {

            //hideProgressDialog();

            if (myGroups != null)
            {

                //myGroups = filter(listSubjectDetail, filterPredicate);
                if (myGroups.size() > 0) {
                    groupname = new String[myGroups.size()];
                    groupId = new Integer[myGroups.size()];
                    groupType = new Integer[myGroups.size()];
                    groupKey = new String[myGroups.size()];

                    for (int i = 0; i < myGroups.size(); i++)
                    {

                        groupname[i] = myGroups.get(i).groupname;
                        groupId[i] = myGroups.get(i).groupId;
                        groupType[i] = myGroups.get(i).groupType;
                        groupKey[i] = myGroups.get(i).groupKey;
                    }

                    // if grp is selected
                    /*if (postType == 2)
                    {*/
                    List<GDModel> listUserDisplayCheckb = new ArrayList<>();

                    for (int i = 0; i < myGroups.size(); i++) {
                        final GDModel usersDisplayProvider = new GDModel(groupId[i], groupname[i], groupKey[i]);
                        listUserDisplayCheckb.add(usersDisplayProvider);
                    }

                    userDisplayAdapter = new GListInPostAdapter(pcontext, listUserDisplayCheckb, postType1);
                    listViewGroups.setAdapter(userDisplayAdapter);

                    // if private is selected
                    /*else if (postType == 0)
                    {

                    }
                    // if public is selected
                    else if (postType == 1)
                    {

                    }*/
                    /*List<GDModel> listUserDisplayCheckb = new ArrayList<>();
                    for (int i = 0; i < myGroups.size(); i++) {
                        final GDModel usersDisplayProvider = new GDModel();
                        listUserDisplayCheckb.add(usersDisplayProvider);
                    }*/
                    //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, strSubjectId);
                    //spnrSubjectID.setAdapter(arrayAdapter);

                }
                else
                    {
                    Toast.makeText(MLAAddPost.this, "no groups ", Toast.LENGTH_LONG).show();
                    }

            } else {
                //Toast.makeText(MLAAddPost.this, "error here in getting groups ", Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected List<GDModel> doInBackground(Void... params) {

            try {
                //postType = spnrGroup.getSelectedItemPosition();

                if(postType1 != 4)
                {
                    Integer user = Integer.parseInt(Vaidehi);
                    Call<List<GDModel>> callSubjectData = Api.getClient().getGroupForPost(user, postType1,0); // False key is used, it indicates that we need to fetch all the subjects which does not have any schedule. So, we can add a schedule for it.
                    Response<List<GDModel>> responseSubjectData = callSubjectData.execute();
                    System.out.println(responseSubjectData);

                    if (responseSubjectData.isSuccessful() && responseSubjectData.body() != null) {
                        return responseSubjectData.body();
                    } else {
                        return null;
                    }
                }

            } catch (MalformedURLException e) {
                return null;

            } catch (IOException e) {
                return null;
            }
            return null;
        }
    }



    class MLASharePost extends AsyncTask<Void, Void, String> {
        Context sharepostcontext;
        //String idSubjectData;
        ArrayList<Integer> grpIdArray;
        Integer response;
        String grpName;
        ArrayList<String> grpKeyArray;
        String grpKey;
        Integer postType1;
        String SessionKey;
        String DS;
        String msg;
        Integer ownerId;
        PModel sharepost = new PModel();
        String encrptPost;
        SecretKey sk;
        byte[] msg1;


        public MLASharePost(Context ctx) {
            sharepostcontext = ctx;
        }

        @Override
        protected void onPreExecute() {

            try {

                    keys = new KeyManager(1024);
                    grpIdArray = grpId1;
                    grpKeyArray = grpKey1;
                    postType1 = postType;
                    msg = message;
                    //DS = "abc";

                    ownerId = Integer.parseInt(Vaidehi);
                    KeyManager.Keys keyPair = KeyManager.getKeysFromKeyStore(Vaidehi, getApplicationContext());
                    prk = keyPair.getPrivateKey();
                    PublicKey pk = keyPair.getPublicKey();
                    byte[] SignData = keys.sign(msg, prk);
                    // boolean b = keys.verifySign(("Post").getBytes(),SignData,pk);
                    DS = keys.bTos(SignData);




               /* sk = keys.newSessionKeyGenerate();
                //msg1 = msg.getBytes();
               // msg1 = keys.sTob(msg);
                encrptPost = keys.postEncryptionusingSessionKey(msg, sk);

                // String decryptPost = keys.postDecryptionusingSessionKey(encrptPost, sk);
                System.out.println(encrptPost);
*/
            }
            catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        protected void onPostExecute(String statusCode) {

            if (statusCode.equals("Group Created")) //the item is created
            {

                /*MLAPostFragment mlaPostFragment = new MLAPostFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mla_fragmentholder, mlaPostFragment);
                fragmentTransaction.commit();*/
                //finish();
                //((MLAHomeActivity) getActivity()).showSnackBar("Added in group.", view.findViewById(R.id.mla_addgroup_linerlayout));
                Toast.makeText(sharepostcontext, "Post shared", Toast.LENGTH_SHORT).show();
                /*Intent myIntent = new Intent(MLAAddPost.this, MLARepostActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);*/
                finish();

            } else

            {
                //((MLAHomeActivity) getActivity()).showSnackBar("Error while adding group.", view.findViewById(R.id.fragment_add_friend_coordinatorLayout));
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                if (postType1 !=4)
                {
                    for (int i = 0; i < grpIdArray.size(); i++) {
                        // encrypt_with_PubKey session key with grp key
                        // decode the base64 encoded string
                        byte[] grpKeyByte = keys.sTob(grpKeyArray.get(i));
                        byte[] grpKeyDecrypt = keys.decrypt_with_PrivateKey(grpKeyByte, prk);
                        // rebuild key using SecretKeySpec
                        SecretKey grpKeySk = new SecretKeySpec(grpKeyDecrypt, 0, grpKeyDecrypt.length, "AES");

                        sk = keys.newSessionKeyGenerate();
                        encrptPost = keys.postEncryptionusingSessionKey(msg, sk);

                        String sk1 = keys.bTos(sk.getEncoded());
                        SessionKey = keys.postEncryptionusingSessionKey(sk1, grpKeySk);
                        //SessionKey = keys.bTos(sessionKeyByte);

                        //Encrypt GroupKey with PublicKey
                        sharepost.setDigitalSignature(DS);
                        sharepost.setPostType(postType1);
                        sharepost.setPostText(encrptPost);
                        sharepost.setSessionKey(SessionKey);
                        sharepost.setGroupNo(grpIdArray.get(i));
                        sharepost.setOwnerId(ownerId);

                        try {
                            Call<String> callAddFriendData = Api.getClient().addPost(sharepost);
                            Response<String> responseSubjectData = callAddFriendData.execute();
                            System.out.print(" RESPONSE FROM OWNERID  " + responseSubjectData);
                        } catch (MalformedURLException e) {
                            return null;

                        } catch (IOException e) {
                            return null;
                        }
                    }
                }
                else
                {
                    sharepost.setDigitalSignature("");
                    sharepost.setPostType(postType);
                    sharepost.setPostText(msg);
                    sharepost.setSessionKey("");
                    Integer gid=null;
                    sharepost.setGroupNo(gid);
                    sharepost.setOwnerId(ownerId);

                    try
                    {
                        Call<String> callAddFriendData = Api.getClient().addPost(sharepost);
                        Response<String> responseSubjectData = callAddFriendData.execute();
                        System.out.print(" RESPONSE FROM OWNERID  " + responseSubjectData);
                    } catch (MalformedURLException e) {
                        return null;

                    } catch (IOException e) {
                        return null;
                    }
                }


            } catch (Exception e) {
                System.out.println("Exception"+ e);
            }
            return "Group Created";

        }



    /*class MLAAddPostAPI extends AsyncTask<Void, Void, PModel> {
        Context ctx;
        PModel postDetails = new PModel();

        public MLAAddPostAPI(Context context) {
            ctx=context;
        }

        @Override
        protected void onPreExecute() {

            //postDetails.post = "Vaidehi";
            //message = post_message.getText().toString();
            Toast.makeText(getApplicationContext(),"YOUR MESSAGE IS.."+postDetails.post1.toString(),Toast.LENGTH_LONG).show();

        }

        protected void onPostExecute(PModel mlaPost) {
            finish();
        }

        @Override
        protected PModel doInBackground(Void... voids) {


            Call<PModel> sendPost= Api.getClient().addPost(postDetails);
            try {
                sendPost.execute();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MLAAddPost.this, "error here in sending post ", Toast.LENGTH_LONG).show();
            }
            return null;
        }
    }*/
    /*public byte[] RSAEncrypt(final String plain, PublicKey publicKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plain.getBytes());
        return encryptedBytes;
    }

    public String RSADecrypt(final byte[] encryptedBytes,PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher1.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher1.doFinal(encryptedBytes);
        String decrypted = new String(decryptedBytes);
        return decrypted;
    }*/
/*

    class MLAGetPublicKeyAPI extends AsyncTask<Void, Void, List<MLAPublicKeyModel>> {
        Context ctx;

        public MLAGetPublicKeyAPI(Context context) {
            ctx=context;
        }

        @Override
        protected void onPreExecute() {

            Toast.makeText(getApplicationContext(),"Getting User Details...",Toast.LENGTH_LONG).show();
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        protected void onPostExecute(List<MLAPublicKeyModel> mlaPublicKeyModels) {
            List<MLAPublicKeyModel> x = new ArrayList<>();
            if (mlaPublicKeyModels != null) {
                x = mlaPublicKeyModels;
            }
            //publicKey = String.valueOf(x.get(0).getPublicKeys());

            // Log.d("public Key ::",publicKey+ "");

            //  int i = publicKey.indexOf("@");
            // int y = publicKey.length();
            //publicKey=publicKey.substring(i+1,y);
            //  Log.d("public Key :: ",publicKey+"" );
            //byte[] pkk=hexStringToByteArray(publicKey);
            //Log.d("public Key :: ",pkk+"" );
            try {

                KeyHelper.Keys keys = KeyHelper.getKeysFromKeyStore(String.valueOf(toUserId),ctx);
                publicKey = keys.getPublicKey();
                //Log.d("public Key :: ",publicKey+"" );
                message=messageBody.getText().toString();
                //  Log.d("Message ::",message );
                encryptedMessage = RSAEncrypt(message,publicKey);
                encryptedString = Base64.encodeToString(encryptedMessage,Base64.DEFAULT);

                //Toast.makeText(getApplicationContext(),encryptedMessage,Toast.LENGTH_LONG).show();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }


            Toast.makeText(getApplicationContext(),"here...",Toast.LENGTH_LONG).show();
        }


        @Override
        protected List<MLAPublicKeyModel> doInBackground(Void... params) {
            try {
                Call<List<MLAPublicKeyModel>> getkey = Api.getClient().getPublicKey(toUserId, userType.toLowerCase());
                Log.d("here toUSerID ::",String.valueOf(toUserId) );
                Response<List<MLAPublicKeyModel>> response = getkey.execute();
                if (response.isSuccessful() && response.body() != null) {
                    return response.body();
                } else {
                    return null;
                }
            }catch(MalformedURLException e) {
                return null;

            } catch (IOException e) {
                return null;
            }
        }



    }

*/
    }
}
