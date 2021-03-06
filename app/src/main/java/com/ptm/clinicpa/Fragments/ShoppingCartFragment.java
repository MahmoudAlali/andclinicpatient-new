package com.ptm.clinicpa.Fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ptm.clinicpa.Activities.Payment;
import com.ptm.clinicpa.Adapters.ShopCartAdapter;
import com.ptm.clinicpa.DataModel.DataService;
import com.ptm.clinicpa.DataModel.GiftitemPOJO;
import com.ptm.clinicpa.R;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity;
import com.paytabs.paytabs_sdk.utils.PaymentParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShoppingCartFragment extends Fragment {



    public static ArrayList<DataService> dataServices=new ArrayList<>();

    //-----------------------
    private static final String TAG = "PdfCreatorActivity";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private File pdfFile;
    ImageView imgdownload;
    //    ConnectionClass connectionClass;
    ArrayList<GiftitemPOJO> MyList1;
    static GiftitemPOJO giftitemPOJO;
    public static Context context;
    GiftitemPOJO name;
    GiftitemPOJO prices;
    GiftitemPOJO provider;
    GiftitemPOJO type;
    GiftitemPOJO date;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_shopping_cart, container, false);
        Toolbar toolbar=view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.shop_cart_re_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(new ShopCartAdapter(getActivity().getApplicationContext(), dataServices));

        MyList1 = new ArrayList<>();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pay, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.pay){
            try{

                createbill();
//            payment();
               topayment();
            createPdfWrapper();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }
        return super.onOptionsItemSelected(item);
    }

    private void topayment() {
    Intent i=new Intent(getActivity().getApplicationContext(), Payment.class);
    startActivity(i);
    }

    private void payment() {
        Intent in = new Intent(getActivity().getApplicationContext(), PayTabActivity.class);
        in.putExtra(PaymentParams.MERCHANT_EMAIL, "hazem.ali1466@gmail.com"); //this a demo account for testing the sdk
        in.putExtra(PaymentParams.SECRET_KEY,"wfF0RRqIQeQezZ72c5DUjLBjtcCwqTlW90X0SnJICzWcy4eKaSFuEhO7tqEUkZRu0OuScdBPNy1lCf6N8MhM46TxwxsRxjlx7Qpk");//Add your Secret Key Here
        in.putExtra(PaymentParams.LANGUAGE,PaymentParams.ENGLISH);
        in.putExtra(PaymentParams.TRANSACTION_TITLE, "Beauty Transaction");
        in.putExtra(PaymentParams.AMOUNT, 5.0);

        in.putExtra(PaymentParams.CURRENCY_CODE, "SAR");
        in.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, "009733");
        in.putExtra(PaymentParams.CUSTOMER_EMAIL, "customer-email@example.com");
        in.putExtra(PaymentParams.ORDER_ID, "123456");
        in.putExtra(PaymentParams.PRODUCT_NAME, "Product 1, Product 2");

//Billing Address
        in.putExtra(PaymentParams.ADDRESS_BILLING, "Flat 1,Building 123, Road 2345");
        in.putExtra(PaymentParams.CITY_BILLING, "MAKA");
        in.putExtra(PaymentParams.STATE_BILLING, "MAKA");
        in.putExtra(PaymentParams.COUNTRY_BILLING, "KSA");
        in.putExtra(PaymentParams.POSTAL_CODE_BILLING, "00973"); //Put Country Phone code if Postal code not available '00973'

//Shipping Address
        in.putExtra(PaymentParams.ADDRESS_SHIPPING, "Flat 1,Building 123, Road 2345");
        in.putExtra(PaymentParams.CITY_SHIPPING, "MAKA");
        in.putExtra(PaymentParams.STATE_SHIPPING, "MAKA");
        in.putExtra(PaymentParams.COUNTRY_SHIPPING, "KSA");
        in.putExtra(PaymentParams.POSTAL_CODE_SHIPPING, "00973"); //Put Country Phone code if Postal code not available '00973'

//Payment Page Style
        in.putExtra(PaymentParams.PAY_BUTTON_COLOR, "#A359C9");
//        in.putExtra(PaymentParams., "#2474bc");
        in.putExtra(PaymentParams.THEME, PaymentParams.THEME_LIGHT);

//Tokenization
        in.putExtra(PaymentParams.IS_TOKENIZATION, true);
        startActivityForResult(in, PaymentParams.PAYMENT_REQUEST_CODE);

    }

    private void createPdfWrapper() throws FileNotFoundException, DocumentException {

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        int hasWriteStoragePermission1 = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                                        ,Manifest.permission.READ_EXTERNAL_STORAGE
                                },
                                REQUEST_CODE_ASK_PERMISSIONS);
                    }

                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                                ,Manifest.permission.READ_EXTERNAL_STORAGE
                        },
                        REQUEST_CODE_ASK_PERMISSIONS);

                return;


            }
            return;
        } else {
            createPdf();
        }
    }
    SimpleDateFormat formatter;
    String dateString;
    private void createPdf() throws FileNotFoundException, DocumentException {


        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateString = formatter.format(new Date(Long.parseLong("10000")));

        String pdfname = "GiftItem"+dateString+".pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[]{3, 3, 3, 3, 3});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell("Name");
        table.addCell("Price");
        table.addCell("Provider Name");
        table.addCell("Offer/service");
        table.addCell("Date");
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        for (int i = 0; i < MyList1.size(); i++) {
            name = MyList1.get(i);
            prices = MyList1.get(i);
            provider = MyList1.get(i);
            type = MyList1.get(i);
            date = MyList1.get(i);




            String namen = name.getItem_name();
            String pricen = prices.getItem_price();
            String daten = date.getCreatedAt();
            String typen =
                    type.getItem_type_code();
            String provider_name =
                    provider.getItem_provider_name();

            table.addCell(String.valueOf(namen));
            table.addCell(String.valueOf(pricen));
            table.addCell(String.valueOf(provider_name));
            table.addCell(String.valueOf(typen));
            table.addCell(String.valueOf(daten.substring(0, 5)));

        }

//        System.out.println("Done");

        PdfWriter.getInstance(document, output);
        document.open();
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.UNDERLINE, BaseColor.BLUE);
        Font g = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.NORMAL, BaseColor.BLUE);
        document.add(new Paragraph("Invoice Beauty  \n\n", f));
//        document.add(new Paragraph("Pdf File Through Itext", g));
        document.add(table);

//        for (int i = 0; i < MyList1.size(); i++) {
//            document.add(new Paragraph(String.valueOf(MyList1.get(i))));
//        }
        document.close();
        Log.e("safiya", MyList1.toString());
        previewPdf();

    }
    private void previewPdf() {

        PackageManager packageManager = getActivity().getApplicationContext().getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            String filename = null;
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ "GiftItem.pdf");
            Intent target = new Intent(Intent.ACTION_VIEW);
            Uri baseUri = Uri.parse("content://"+Environment.getExternalStorageDirectory().getAbsolutePath()
                    +"/Documents/"+"GiftItem"+dateString+".pdf");
            target.setDataAndType(baseUri,"application/pdf");
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Intent intent = Intent.createChooser(target, "Open File");
            startActivity(intent);
        } else {
            Toast.makeText(context, "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void createbill(){
        double sum=0;
        MyList1 = new ArrayList<>();
        for (int j=0;j<dataServices.size();j++) {

            sum=sum+dataServices.get(j).getPrice();

            giftitemPOJO = new GiftitemPOJO();
            giftitemPOJO.setItem_name(dataServices.get(j).getName());
            giftitemPOJO.setItem_price(dataServices.get(j).getPrice()+"");
            giftitemPOJO.setItem_provider_name("provider name");
            if(dataServices.get(j).isIsoffer()) {
                giftitemPOJO.setItem_type_code("offer");
            }else {
                giftitemPOJO.setItem_type_code("service");
            }

            giftitemPOJO.setCreatedAt("13:00");
            MyList1.add(giftitemPOJO);
        }
        giftitemPOJO = new GiftitemPOJO();
        giftitemPOJO.setItem_name("Sum Price");
        giftitemPOJO.setItem_price(sum+"");
        giftitemPOJO.setItem_provider_name("-");
        giftitemPOJO.setItem_type_code("-");
        giftitemPOJO.setCreatedAt("13:00");
        MyList1.add(giftitemPOJO);
    }
}


