package com.ample.dumi.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ample.dumi.Activity.MainActivity;
import com.ample.dumi.Activity.AddQRActivity;
import com.ample.dumi.Adapter.GalleryAdapter;
import com.ample.dumi.Model.NameCard;
import com.ample.dumi.R;
import com.ample.dumi.Utils.Utility;
import com.ample.dumi.Utils.LoginSession;
import com.ample.dumi.Utils.util;
import com.ample.dumi.databinding.FragmentNameCardList1Binding;
import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.QRCode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static com.ample.dumi.Utils.Utility.POST2;
import static com.ample.dumi.Utils.Utility.encrypt;
import static com.ample.dumi.Utils.util.secretKey;

public class NameCardList1Fragment extends Fragment {

    FragmentNameCardList1Binding fragmentNameCardList1Binding;
    private GalleryAdapter mAdapter;
    View view;
    public static ArrayList<NameCard> allTags;
    AlertDialog QR_AlertDialog ;
    ImageView ivBarImage;
    Bitmap overlay;
    static Bitmap bitmap ;
    String User_name,UserId,profileId;
    LoginSession session;

    static String barName;
    QRCodeWriter writer = new QRCodeWriter();


    public NameCardList1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentNameCardList1Binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_name_card_list1, container, false);
        view = fragmentNameCardList1Binding.getRoot();

        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false);
        layoutManager.setMaxVisibleItems(3);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        fragmentNameCardList1Binding.recyclerView.setLayoutManager(layoutManager);
        fragmentNameCardList1Binding.recyclerView.setHasFixedSize(true);
        allTags = new ArrayList<>();

        new HttpAsyncTask().execute(Utility.BASE_URL+"GetFriendConnection");
        fragmentNameCardList1Binding.recyclerView.addOnScrollListener(new CenterScrollListener());

        fragmentNameCardList1Binding.lnrScanQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddQRActivity.class);
                startActivity(intent);
            }
        });

        session = new LoginSession(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        UserId = user.get(LoginSession.KEY_USERID);      // name
        profileId = user.get(LoginSession.KEY_PROFILEID);
        User_name = user.get(LoginSession.KEY_NAME);

        getqrcode();
        fragmentNameCardList1Binding.lnrApplyQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mergeBitmaps(overlay,bitmap) != null) {
                    QR_AlertDialog = new AlertDialog.Builder(getActivity(), R.style.AppTheme).create();
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.person_qrcode, null);
                    FrameLayout fl_QRFrame = (FrameLayout) dialogView.findViewById(R.id.fl_QrFrame);
                    TextView tvBarName = (TextView) dialogView.findViewById(R.id.tvBarName);
                    ivBarImage = (ImageView) dialogView.findViewById(R.id.ivBarImage);
//                tvBarName.setText(barName);
                    //  alertDialog.setFeatureDrawableAlpha(R.color.colorPrimary, 8);

                    ColorDrawable dialogColor = new ColorDrawable(getResources().getColor(R.color.colorPrimary));
                    dialogColor.setAlpha(70);
                    QR_AlertDialog.getWindow().setBackgroundDrawable(dialogColor);
                    QR_AlertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    // alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                    tvBarName.setText(User_name);
//                    bitmap = TextToImageEncode(barName);
                    ivBarImage.setImageBitmap(mergeBitmaps(overlay, bitmap));

                    fl_QRFrame.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            QR_AlertDialog.dismiss();
                        }
                    });

                    QR_AlertDialog.setView(dialogView);
                    QR_AlertDialog.show();
                }
            }
        });
        fragmentNameCardList1Binding.lnrTapCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NfcManager manager = (NfcManager) getContext().getSystemService(Context.NFC_SERVICE);
                NfcAdapter adapter = manager.getDefaultAdapter();
                if (adapter != null && adapter.isEnabled()) {
                    Toast.makeText(getContext(), "You can scan CircleOne NFC-Cards by holding them behind NFC sensor of your Android device. It could be either on top, middle or bottom.", Toast.LENGTH_LONG).show();
                    //Yes NFC available
                }
                else if (adapter != null && !adapter.isEnabled()){


                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("Please ensure that NFC has been enabled under your phone settings. You will still be able to add contacts with the QR scan feature if your device isn't NFC capable.");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Settings",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getContext(), "Please activate NFC and press Back to return to the application!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }
                else{
                    Toast.makeText(getContext(), "Your device does not support NFC. You will still be able to add contacts with the QR scan feature if your device isn't NFC capable.", Toast.LENGTH_LONG).show();
                    //Your device doesn't support NFC
                }
            }
        });
        return view;
    }

    private void getqrcode() {


        try
        {

            barName = encrypt("5", secretKey);
            //bitmap = TextToImageEncode(barName);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        BitMatrix bitMatrix = null;
        try {
            bitMatrix = writer.encode(barName, BarcodeFormat.QR_CODE, 600, 600);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? BLACK : WHITE;
                }
            }

            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            overlay = BitmapFactory.decodeResource(getResources(), R.drawable.ic_left_logo);

            //ivBarImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap mergeBitmaps(Bitmap overlay, Bitmap bitmap)
    {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        Bitmap combined = Bitmap.createBitmap(width, height, bitmap.getConfig());
        Canvas canvas = new Canvas(combined);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        canvas.drawBitmap(bitmap, new Matrix(), null);
        int centreX = (canvasWidth  - overlay.getWidth()) /2;
        int centreY = (canvasHeight - overlay.getHeight()) /2 ;
        canvas.drawBitmap(overlay, centreX, centreY, null);
        return combined;
    }
    public class HttpAsyncTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            String loading = "Fetching cards" ;
            dialog = new ProgressDialog(getContext());
            dialog.setMessage(loading);
            //dialog.setTitle("Saving Reminder");
            dialog.show();
            dialog.setCancelable(false);

        }

        @Override
        protected String doInBackground(String... urls) {
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.accumulate("Type", "desc");

                jsonObject.accumulate("numofrecords", "1000");
//            jsonObject.accumulate("pageno", pageno);
                jsonObject.accumulate("pageno", "1");
                jsonObject.accumulate("userid", "5");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return POST2(urls[0],jsonObject);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result)
        {
//            customProgressBarStatus = "stop";
          /*  String status = "false" ;
            String loading = "Fetching Cards..." ;
            CustomProgressBar(loading, status);*/
            dialog.dismiss();

            try
            {
                if (result != null)
                {
                    JSONObject jsonObject = new JSONObject(result);

                    //Toast.makeText(getContext(), jsonArray.toString(), Toast.LENGTH_LONG).show();
//                    nfcModel.clear();
                    allTags.clear();

                    JSONArray jsonArray;

                    jsonArray = jsonObject.getJSONArray("connection");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        //  Toast.makeText(getContext(), object.getString("Card_Back"), Toast.LENGTH_LONG).show();

                        NameCard nfcModelTag = new NameCard();
                        nfcModelTag.setName(object.getString("FirstName") + " " + object.getString("LastName"));
                        nfcModelTag.setCompany(object.getString("CompanyName"));
                        nfcModelTag.setEmail(object.getString("UserName"));
                        nfcModelTag.setWebsite(object.getString("Website"));
                        nfcModelTag.setPh_no(object.getString("Phone"));
                        nfcModelTag.setDesignation(object.getString("Designation"));
                        nfcModelTag.setCard_front(object.getString("Card_Front"));
                        nfcModelTag.setCard_back(object.getString("Card_Back"));
                        nfcModelTag.setUser_image(object.getString("UserPhoto"));
                        nfcModelTag.setProfile_id(object.getString("ProfileId"));
                        //nfcModelTag.setDateInitiated(object.getString("DateInitiated"));
                        nfcModelTag.setAddress(object.getString("Address1") + " " + object.getString("Address2")
                                + " " + object.getString("Address3") + " " + object.getString("Address4"));
//                        Toast.makeText(getActivity(),"Profile_id"+object.getString("ProfileId"),Toast.LENGTH_SHORT).show();
                        //  nfcModelTag.setLatitude(object.getString("Latitude"));
                        //  nfcModelTag.setLongitude(object.getString("Longitude"));
                        allTags.add(nfcModelTag);

                    }
                    mAdapter = new GalleryAdapter(getContext(), allTags);
                    fragmentNameCardList1Binding.recyclerView.setAdapter(mAdapter);

                }
                else
                {
                    Toast.makeText(getContext(), "Not able to load Cards..", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
