package com.ptm.clinicpa.Fragments;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import android.widget.TextView;

import com.meg7.widget.CircleImageView;
import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Adapters.ReservationsAdapter2;
import com.ptm.clinicpa.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import com.dcoret.beautyclient.API.APICallCall;


public class DepositReservationFragment extends Fragment {

    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    String filterFromNotification="";

    public static RecyclerView service_select;
    public static ReservationsAdapter2 reservationsAdapter2;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    Bitmap myBitmap;
    Uri picUri;


    private final static int ALL_PERMISSIONS_RESULT = 107;

    static String[] items={"Service 1","Service 2","Service 3","Service 4","Service 5","Service 6"};

    TextView incom_reservation,accept_reservation,deposited_reservation;
    String filter,sort;
    ImageView sortbtn;
    int layout;
    public String tmp="2";

    Boolean isScrolling=false;
    int curentItems,totalItems,scrollOutItems;
    LinearLayoutManager manager;
    public static int pageNum=1;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incom_reservatiom_fragment, container, false);
        pageNum=1;

        filterFromNotification="";
        BeautyMainPage.FRAGMENT_NAME="MYRESERVATIONFRAGMENT";
        MyReservationFragment.tab="2";
        MyReservationFragment.groupbooking="";
       // MyReservationFragment.note_cancel.setVisibility(View.VISIBLE);

       // MyReservationFragment.reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,APICall.reservationModels);
        service_select=view.findViewById(R.id.incom_ree);
        MyReservationFragment.progressBar=view.findViewById(R.id.progress);
        sortbtn= MyReservationFragment.view.findViewById(R.id.sort);
        manager=new LinearLayoutManager(BeautyMainPage.context);
        service_select.setLayoutManager(manager);
//        reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,APICall.reservationModels,0);

        service_select.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling=true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                curentItems=manager.getChildCount();
                totalItems=manager.getItemCount();
                scrollOutItems=manager.findFirstVisibleItemPosition();
                if (isScrolling && (curentItems+scrollOutItems==totalItems))
                {
                    //-------- fetch data
                    isScrolling=false;
                    getdata();
                }
            }
        });

        MyReservationFragment.filterbtn.setImageResource(R.drawable.filter_off);
        MyReservationFragment.sortbtn.setImageResource(R.drawable.sort_off);
        MyReservationFragment.sortbtn.setClickable(false);
        MyReservationFragment.filterbtn.setClickable(false);


//        MyReservationFragment.reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,);

//        service_select.setAdapter(reservationsAdapter2);

        APICall.layout= R.layout.incom_reservation_layout;
        try {
            APICall.arrayAB.remove(0);
        }catch (Exception e){
            e.printStackTrace();
        }
//        if (APICall.arrayAB==null)

        //region CHECK_NOTIFICATIONS
        Bundle bundle = this.getArguments();
        String book_id="";
        try {
            if (bundle != null) {
                book_id = bundle.getString("book_id");
                Log.e("NotifDepoif",book_id);

            }

            if(!book_id.equals(""))
            {
                bundle.putString("book_id", book_id);
                Log.e("NotifDepo",book_id);
                //    MyReservationFragment.reservationsAdapter2.book_id=book_id;
                Log.e("BookID",book_id);
                Intent intent=new Intent(BeautyMainPage.context, ReservatoinDetailsActivity.class);
                intent.putExtra("book_id",book_id);
                startActivity(intent);
            }

        }
        catch (Exception e)
        {

        }

        String book_id_for_filter="";
        try {
            if (bundle != null) {
                book_id_for_filter = bundle.getString("book_id_for_filter");
                Log.e("NotifDepoif",book_id_for_filter);
                Log.e("GOINGTODEPO","NotifDepoif");


            }

            if(!book_id_for_filter.equals(""))
            {
                filterFromNotification=APICall.Filter("56",book_id_for_filter,"0")+",";
            }

        }
        catch (Exception e){}


        //endregion





        //---------cancel---------
//        filter=APICall.bookingFilter("1","4","0");

        APICall.filter= APICall.bookingFilterV1("1","7","0");
        filter="\"Filter\":["+filterFromNotification+APICall.Filter("1","7","0")

//        {\"num\":"+filterNum+",\"value1\":"+val1+",\"value2\":"+val2+"}
                +"]";
        Log.e("GOINGTODEPO","NotifDepoif"+filter);

        APICall.appointmentsAutomatedBrowse(APICall.ln, "20", MyReservationFragment.serviceId, "1", filter, "", BeautyMainPage.context, APICall.layout,tmp,true);

        //---------wait confirm by provider
//        service_select.setAdapter(MyReservationFragment.reservationsAdapter2);
        return view;
    }


        private void getdata() {
            MyReservationFragment.progressBar.setVisibility(View.VISIBLE);
            pageNum++;
            APICall.appointmentsAutomatedBrowseScrolling("en", "20", MyReservationFragment.serviceId, pageNum+"", APICall.filter, "", BeautyMainPage.context, APICall.layout,tmp,true);


        }

    public static Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = BeautyMainPage.context.getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    private static Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = BeautyMainPage.context.getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap bitmap;
        if (resultCode == Activity.RESULT_OK) {

            ImageView imageView = MyReservationFragment.checkInImg;
            Log.e("onActivityResult","true");

            if (getPickImageResultUri(data) != null) {
                picUri = getPickImageResultUri(data);

                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(BeautyMainPage.context.getContentResolver(), picUri);
                    myBitmap = rotateImageIfRequired(myBitmap, picUri);
                    myBitmap = getResizedBitmap(myBitmap, 500);

                   // CircleImageView croppedImageView = (CircleImageView) findViewById(R.id.img_profile);
                   // croppedImageView.setImageBitmap(myBitmap);
                    imageView.setImageBitmap(myBitmap);
                    imageView.setVisibility(View.VISIBLE);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {


                bitmap = (Bitmap) data.getExtras().get("data");

                myBitmap = bitmap;
                //CircleImageView croppedImageView = (CircleImageView) findViewById(R.id.img_profile);
                /*if (croppedImageView != null) {
                    croppedImageView.setImageBitmap(myBitmap);
                }*/

                imageView.setImageBitmap(myBitmap);
                imageView.setVisibility(View.VISIBLE);


            }

        }

    }
    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }




}
