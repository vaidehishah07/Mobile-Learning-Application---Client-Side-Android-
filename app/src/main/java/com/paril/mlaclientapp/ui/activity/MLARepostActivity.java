package com.paril.mlaclientapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.paril.mlaclientapp.R;
import com.paril.mlaclientapp.model.GDModel;
import com.paril.mlaclientapp.model.PModel;
import com.paril.mlaclientapp.ui.adapter.GListInPostAdapter;
import com.paril.mlaclientapp.util.KeyManager;
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

public class MLARepostActivity extends BaseActivity {

String post_to_share_id;
Integer postIdObj;
KeyManager keysForPostShare;
String Vaidehi;
ListView lvGroup;
Button sharepost;
PModel postShareObj = new PModel();
String[] groupname;
Integer[] groupId;
Integer[] groupType;
String[] groupKey;
Integer postType;
Integer posttypep;
String posttextp;

GListInPostAdapter userDisplayAdapter;
    ArrayList<Integer> grpIdForShare;
    ArrayList<String> grpKeyForShare;
    PrivateKey prk;

    KeyManager keys;

    //String encryptedmsg;
    String postmsgObj;
    String grpKeyObj;
    String SessionKeyObj;
    Integer GroupIdObj;
    String Pk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlashared_post_details);
        lvGroup = (ListView) findViewById(R.id.mla_grouplist_forpostshare_display_listView);
        sharepost = (Button) findViewById(R.id.mla_share_post_ingroup_btn);
        Intent intent = getIntent();

        postIdObj = intent.getIntExtra("post_id",0);
        postmsgObj = intent.getStringExtra("post_text");
        grpKeyObj = intent.getStringExtra("post_groupKey");
        SessionKeyObj = intent.getStringExtra("post_sessionKey");
        GroupIdObj = intent.getIntExtra("post_groupId",0);

        Log.d("Error","Activity Shared");

        loadingUserInformation();
      /* MLAGetAllPostForShareAPI mlaGetAllPostForShareAPI = new MLAGetAllPostForShareAPI(MLARepostActivity.this);
        mlaGetAllPostForShareAPI.execute();
*/

        MLAGroupForPostShare getConversation = new MLAGroupForPostShare(MLARepostActivity.this, 2);
        getConversation.execute();

     /*   MLAGroupForPostShare getConversation = new MLAGroupForPostShare(MLARepostActivity.this, 2);
        getConversation.execute();
*/
        sharepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (userDisplayAdapter != null && userDisplayAdapter.getCount() > 0)
                {
                    int listSize = userDisplayAdapter.getCount();//this is the list size
                    GDModel userDisplayCheckbxProvider;
                    grpIdForShare = new ArrayList<>();
                    grpKeyForShare = new ArrayList<>();

                    for (int i = 0; i < listSize; i++)
                    {

                        userDisplayCheckbxProvider = (GDModel) userDisplayAdapter.getItem(i);
                        if (userDisplayCheckbxProvider.getCheck()) {
                            grpIdForShare.add(userDisplayCheckbxProvider.getGroupId());
                            grpKeyForShare.add(userDisplayCheckbxProvider.getGroupKey());
                        }
                    }

                    // if the friends are selected from the list then call the API.
                    if (grpIdForShare.size() > 0)
                    {
                        MLAReSharePost mlaSharePost = new MLAReSharePost(MLARepostActivity.this);
                        mlaSharePost.execute();
                   }
                }
            }
        });


    }

    void loadingUserInformation()
    {
        Vaidehi = prefsManager.getStringData("userId");
        Pk= prefsManager.getStringData("publicKey");
    }


 /*   class MLAGetAllPostForShareAPI extends AsyncTask<Void, Void, List<PModel>> {
        Context context;
        byte[] grpKeyDec;
        String[] postText;


        public MLAGetAllPostForShareAPI(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {

            try {

                keysForPostShare = new KeyManager(1024);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(List<PModel> apiResponse)
        {


            List<PModel> listPostDetailsforShare = new ArrayList<PModel>();


            if (apiResponse.size() != 0)
            {

                listPostDetailsforShare = apiResponse;

                for (int i = 0; i < listPostDetailsforShare.size(); i++)
                {
                    if (listPostDetailsforShare.get(i).getPostId() == Integer.parseInt(post_to_share_id))

                    {
                        postShareObj = listPostDetailsforShare.get(i);
                        encryptedmsg = postShareObj.getPostText();
                        grpKeyObj = postShareObj.getGroupkey();
                        SessionKeyObj = postShareObj.getSessionKey();
                        GroupIdObj = postShareObj.getGroupId();

                    }

                }
            }

            MLAGroupForPostShare getConversation = new MLAGroupForPostShare(MLARepostActivity.this, 2);
            getConversation.execute();

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
    }*/

    class MLAGroupForPostShare extends AsyncTask<Void, Void, List<GDModel>> {
        Context pcontext;
        Integer postType1;

        public MLAGroupForPostShare(Context ctx, Integer position) {
            pcontext = ctx;
            postType1 = position;
            System.out.println("POST TYPE" + postType1);
        }

        @Override
        protected void onPreExecute() {
            //showProgressDialog("Getting Subject Data...");
            Toast.makeText(pcontext, "In pre execute", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(List<GDModel> myGroups1) {

            //hideProgressDialog();

            if (myGroups1 != null) {

                //myGroups = filter(listSubjectDetail, filterPredicate);
                if (myGroups1.size() > 0) {

                    groupname = new String[myGroups1.size()];
                    groupId = new Integer[myGroups1.size()];
                    groupType = new Integer[myGroups1.size()];
                    groupKey = new String[myGroups1.size()];

                    for (int i = 0; i < myGroups1.size(); i++)
                    {

                        groupname[i] = myGroups1.get(i).groupname;
                        groupId[i] = myGroups1.get(i).groupId;
                        groupType[i] = myGroups1.get(i).groupType;
                        groupKey[i] = myGroups1.get(i).groupKey;
                    }

                    // if grp is selected
                    /*if (postType == 2)
                    {*/
                    List<GDModel> listUserDisplayCheckb = new ArrayList<>();

                    for (int i = 0; i < myGroups1.size(); i++) {

                        final GDModel usersDisplayProvider = new GDModel(groupId[i], groupname[i], groupKey[i]);
                        listUserDisplayCheckb.add(usersDisplayProvider);
                    }

                    userDisplayAdapter = new GListInPostAdapter(pcontext, listUserDisplayCheckb, postType1);
                    lvGroup.setAdapter(userDisplayAdapter);

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

                } else {
                    Toast.makeText(MLARepostActivity.this, "no groups ", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(MLARepostActivity.this, "error here in getting groups ", Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected List<GDModel> doInBackground(Void... params) {

            try {
                //postType = spnrGroup.getSelectedItemPosition();

                Integer user = Integer.parseInt(Vaidehi);

                Call<List<GDModel>> callSubjectData = Api.getClient().getGroupForPost(user, postType1,GroupIdObj); // False key is used, it indicates that we need to fetch all the subjects which does not have any schedule. So, we can add a schedule for it.
                Response<List<GDModel>> responseSubjectData = callSubjectData.execute();
                System.out.println(responseSubjectData);

                if (responseSubjectData.isSuccessful() && responseSubjectData.body() != null) {
                    return responseSubjectData.body();
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


    class MLAReSharePost extends AsyncTask<Void, Void, String> {

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

        byte[] grpKeyDec;
        String [] decPostMessage;
        String [] sessionKey1;
        PrivateKey prk;
        KeyManager.Keys keyPair1;


        public MLAReSharePost(Context ctx) {
            sharepostcontext = ctx;
        }

        @Override
        protected void onPreExecute() {

                try {

                  keys = new KeyManager(1024);
                  grpIdArray = grpIdForShare;
                  grpKeyArray = grpKeyForShare;
                  postType1 = postType;


                  KeyManager.Keys keyPair = KeyManager.getKeysFromKeyStore(Vaidehi, getApplicationContext());
                  PrivateKey prk = keyPair.getPrivateKey();

                  ownerId = Integer.parseInt(Vaidehi);
                  keyPair1 = KeyManager.getKeysFromKeyStore(Vaidehi, getApplicationContext());
                  prk = keyPair1.getPrivateKey();
                  PublicKey pk = keyPair1.getPublicKey();

                  byte[] SignData = keys.sign(postmsgObj, prk);
                  // boolean b = keys.verifySign(("Post").getBytes(),SignData,pk);

                  DS = keys.bTos(SignData);

                  // String decryptPost = keys.postDecryptionusingSessionKey(encrptPost, sk);
                  // System.out.println(encrptPost);


              } catch (Exception e) {
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
                //Toast.makeText(sharepostcontext, "Post shared", Toast.LENGTH_SHORT).show();
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

                    for (int i = 0; i < grpIdArray.size(); i++) {

                        sk = keys.newSessionKeyGenerate();
                        //msg1 = msg.getBytes();
                        // msg1 = keys.sTob(msg);
                        encrptPost = keys.postEncryptionusingSessionKey(postmsgObj, sk);
                        // encrypt_with_PubKey session key with grp key
                        // decode the base64 encoded string
                        byte[] grpKeyByte = keys.sTob(grpKeyArray.get(i));

                        keyPair1 = KeyManager.getKeysFromKeyStore(Vaidehi, getApplicationContext());
                        prk = keyPair1.getPrivateKey();

                        byte[] grpKeyDecrypt = keys.decrypt_with_PrivateKey(grpKeyByte, prk);
                        // rebuild key using SecretKeySpec
                        SecretKey grpKeySk = new SecretKeySpec(grpKeyDecrypt, 0, grpKeyDecrypt.length, "AES");

                        String sk1 = keys.bTos(sk.getEncoded());

                        SessionKey = keys.postEncryptionusingSessionKey(sk1, grpKeySk);
                        //SessionKey = keys.bTos(sessionKeyByte);

                        //Encrypt GroupKey with PublicKey
                        sharepost.setDigitalSignature(DS);
                        sharepost.setPostType(2);
                        sharepost.setPostText(encrptPost);
                        sharepost.setSessionKey(SessionKey);
                        sharepost.setGroupNo(grpIdArray.get(i));
                        sharepost.setOwnerId(ownerId);
                        sharepost.setOriginalPostId(postIdObj);

                        //alias = "grpKey";
                        // String grpKey = newGrpKeyGenerate();
                        //encryptString(grpKey);
                        // grpData.setGroupKey(encryptedText);

                        try {
                            Call<String> callAddFriendData = Api.getClient().addPost(sharepost);
                            Response<String> responseSubjectData = callAddFriendData.execute();
                            System.out.print(" RESPONSE FROM OWNERID  " + responseSubjectData);
                        } catch (MalformedURLException e) {
                            return null;

                        } catch (IOException e)
                        {
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
