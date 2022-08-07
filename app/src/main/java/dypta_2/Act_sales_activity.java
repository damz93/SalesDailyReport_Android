//Act_update_lokasi ada upload gambar
package dypta_2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import app.dypta_2.R;

public class Act_sales_activity extends Activity implements View.OnClickListener{
    Button bt_scanner,bt_split,bt_kirim;
    TextView tx_msisdn_mk, tx_cluster, tx_msisdn_plgz, t_juml_msd;
    String s_cluster,s_user,fix_lokasi;
    private static final String TAG = Act_sales_activity.class.getSimpleName();
    private static String dyam_url_sales_act = "http://www.own-youth.com/dd_json/simp_sales_activity.php";

    //buat script php di bawah!
    public static String UPLOAD_URL = "http://www.own-youth.com/dd_json/upl_foto_sales.php";
    public static String UPD_PJP = "http://www.own-youth.com/dd_json/upd_status_pjp.php";
    Boolean isInternetPresent = false;
    Bitmap rotateBMP;
    ConnectionDetector cd;
    String status_pjp="DONE";
    String S_MSISDN_MKIOS, S_CLUSTER, S_MSISDN_PLG,S_JUMLAH, s_LAT, s_LONG, s_KUNJUNGAN, s_PJP;
    String selectedImagePath, nm_loksss;
    Integer jml_msd;
    Integer countzz=0;
    EditText ed_msisdn_plg,ed_idcom, ed_lokasi_pjpp;
    public static String IMAGE_DIRECTORY_NAME = "gambar";
    public static final int MEDIA_TYPE_IMAGE = 4;
    public static final String UPLOAD_KEY = "image";
    private static final int PICK_IMAGE_REQUEST = 3;
    public static final int MEDIA_TYPE_VIDEO = 2;
    GPSTracker gps;
    ImageView img_cam, img_cams, img_lok;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String cek;
    Spinner spn_kunjungan, spn_pjp;
    final List< String > list4 = new ArrayList < String > ();
    JSONArray contacts = null;
    String pilih_kunjungan,pilih_pjp;
    ProgressDialog pDialog;
    String db_datamsisdn, harus_upd ="y";
    String item_kegiatan[] = { "Kunjungan", "Event", "Konsinyasi" };
    Act_set_get a = new Act_set_get();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_sales_activity);
        s_cluster = a.getcluster();
        s_user = a.getusnme();
        bt_split = (Button)findViewById(R.id.bt_split2);
        bt_split.setOnClickListener(this);
        bt_kirim = (Button)findViewById(R.id.bt_kr_request);
        bt_kirim.setOnClickListener(this);
        tx_cluster = (TextView) findViewById(R.id.tx_clustern);
        tx_msisdn_mk = (TextView) findViewById(R.id.tx_msisdnmkios);
        ed_msisdn_plg = (EditText)findViewById(R.id.ed_msisdn_pl);
        ed_idcom = (EditText)findViewById(R.id.ed_id_com);
        ed_lokasi_pjpp = (EditText)findViewById(R.id.ed_lokasi_pjp);
        tx_msisdn_plgz = (TextView) findViewById(R.id.tx_msisdn_plg);
        t_juml_msd = (TextView) findViewById(R.id.tx_jml_msisd);
        img_cam = (ImageView) findViewById(R.id.im_kamera);
        img_cams = (ImageView) findViewById(R.id.im_kameraS);
        img_cam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cek = "camera";
                //Listing 1
                dispatchTakePictureIntent();
            }
        });
        tx_cluster.setText(s_cluster);
        tx_msisdn_mk.setText(s_user);

        spn_kunjungan = (Spinner)findViewById(R.id.spn_kunjungan);

        ArrayAdapter<String> adapter_kunjungan = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, item_kegiatan);

        adapter_kunjungan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_kunjungan.setAdapter(adapter_kunjungan);
        spn_kunjungan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Object item = parent.getItemAtPosition(position);
                pilih_kunjungan = spn_kunjungan.getSelectedItem().toString();
                //TextView.setTextColor(Color.WHITE);

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spn_pjp = (Spinner)findViewById(R.id.spn_pjp);
        new AmbilPJP().execute();
        try {
            bt_scanner = (Button) findViewById(R.id.bt_qrcode1);
            bt_scanner.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                }
            });
        } catch (ActivityNotFoundException anfe) {
            Log.e("onCreate", "Scanner Not Found", anfe);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");

                Integer l_contents = contents.length();
                Integer l_cont2 = l_contents - 12;

                String nomor_scan = contents.substring(l_cont2, l_contents);
                String a = tx_msisdn_plgz.getText().toString();
                jml_msd = Integer.parseInt(t_juml_msd.getText().toString());

                if (a.contains(nomor_scan)) {
                    AlertDialog dyam_dialog = new AlertDialog.Builder(Act_sales_activity.this).create();
                    dyam_dialog.setTitle("Peringatan");
                    dyam_dialog.setIcon(R.drawable.warning);
                    dyam_dialog.setMessage("Maaf, MSISDN sudah terscan..\nScan yang lain...");
                    dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dyam_dialog.show();
                } else {
                    if (a.length() > 5) {
                        //t_msisdn.setText(a + "\n" + nomor_scan);
                        tx_msisdn_plgz.setText(a + "|" + nomor_scan);
                        db_datamsisdn = (a + "|" + nomor_scan).toString();
                        jml_msd++;
                    } else {
                        tx_msisdn_plgz.setText(nomor_scan);
                        db_datamsisdn = (nomor_scan).toString();
                        jml_msd = 1;
                    }
                    t_juml_msd.setText(jml_msd.toString());
                }

            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
                Toast toast = Toast.makeText(this, "Scan was Cancelled!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                setpic();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && intent != null && intent.getData() != null) {
            Uri selectedImageUri = intent.getData();
            selectedImagePath = getImagePath(selectedImageUri); //getPath(selectedImageUri);
            img_cams.setImageURI(selectedImageUri);
            if (selectedImagePath == null) {
                Log.d("Selected", "Null");
            } else {
                Log.d("Selected", selectedImagePath);
            }
            cek = "galery";
            try {
                setpic();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == bt_split){
            if(ed_msisdn_plg.getText().length()>9) {
                String nomor_input;
                nomor_input = ed_msisdn_plg.getText().toString();
                String a = tx_msisdn_plgz.getText().toString();
                jml_msd = Integer.parseInt(t_juml_msd.getText().toString());

                if (a.contains(nomor_input)) {
                    AlertDialog dyam_dialog = new AlertDialog.Builder(Act_sales_activity.this).create();
                    dyam_dialog.setTitle("Peringatan");
                    dyam_dialog.setIcon(R.drawable.warning);
                    dyam_dialog.setMessage("Maaf, MSISDN sudah ada..\nMasukkan MSISDN yang lain...");
                    dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dyam_dialog.show();
                } else {
                    if (a.length() > 5) {
                        //t_msisdn.setText(a + "\n" + nomor_scan);
                        tx_msisdn_plgz.setText(a + "|" + nomor_input);
                        db_datamsisdn = (a + "|" + nomor_input).toString();
                        jml_msd++;
                    } else {
                        tx_msisdn_plgz.setText(nomor_input);
                        db_datamsisdn = (nomor_input).toString();
                        jml_msd = 1;
                    }
                    t_juml_msd.setText(jml_msd.toString());
                    ed_msisdn_plg.setText("");
                }
            }
            else{
                msisdn_kurang();
            }
        }
        else if(v == bt_kirim) {
            //S_MSISDN_MKIOS, S_CLUSTER, S_MSISDN_PLG,S_JUMLAH, S_ID_COM, slat, slong
            S_MSISDN_MKIOS = tx_msisdn_mk.getText().toString();
            S_CLUSTER = tx_cluster.getText().toString();
            S_MSISDN_PLG = tx_msisdn_plgz.getText().toString();
            S_JUMLAH = t_juml_msd.getText().toString();
            s_PJP = ed_lokasi_pjpp.getText().toString();
            s_KUNJUNGAN = pilih_kunjungan;
            gps = new GPSTracker(Act_sales_activity.this);

            if ((S_MSISDN_PLG.length() > 5)&&(ed_lokasi_pjpp.length()>2)) {
                if (gps.canGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    s_LAT = (String.valueOf(latitude));
                    s_LONG = (String.valueOf(longitude));
                    if ((latitude == 0.0) || (longitude == 0.0)) {
                        Toast.makeText(getApplicationContext(), "Lokasi sementera direload, mohon dicoba lagi!!!", Toast.LENGTH_LONG).show();
                    } else {
                        cd = new ConnectionDetector(getApplicationContext());
                        isInternetPresent = cd.isConnectingToInternet();
                        try {
                            if (isInternetPresent) {
                                DialogInterface.OnClickListener dd_dialog = new DialogInterface.OnClickListener() {

                                    @
                                            Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                break;
                                            case DialogInterface.BUTTON_NEGATIVE:
                                                if (null != img_cams.getDrawable()) {
                                                    fix_lokasi = s_PJP;
                                                    simp_db(S_MSISDN_MKIOS, S_CLUSTER, S_MSISDN_PLG, S_JUMLAH, s_LAT, s_LONG, s_PJP, s_KUNJUNGAN);

                                                    //Toast.makeText(Act_sales_activity.this, "Isi s_PJP adalah: " + s_PJP+"\nIsi fix lokasi: "+fix_lokasi, Toast.LENGTH_LONG).show();
                                                    try {
                                                        if (harus_upd.equals("ya")) {
                                                            upd_pjp(fix_lokasi, status_pjp);
                                                            upd_gambar();
                                                        }
                                                        else{
                                                            upd_gambar();

                                                        }
                                                    }
                                                    catch(Exception e){
                                                        Toast.makeText(Act_sales_activity.this, "Errornya: " + e, Toast.LENGTH_LONG).show();
                                                    }
                                                } else {
                                                    AlertDialog dyam_dialog = new AlertDialog.Builder(Act_sales_activity.this).create();
                                                    dyam_dialog.setTitle("Peringatan");
                                                    dyam_dialog.setIcon(R.drawable.warning);
                                                    dyam_dialog.setMessage("Pilih Gambar terlebih dahulu");
                                                    dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {

                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });
                                                    dyam_dialog.show();
                                                }

                                                break;
                                        }
                                    }
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(Act_sales_activity.this);
                                builder.setMessage("Yakin ingin menyimpan?").setPositiveButton("Tidak", dd_dialog).setNegativeButton("Ya", dd_dialog).show();
                            } else {
                                pesanUnkn();
                            }
                        } catch (Exception e) {
                            Toast.makeText(Act_sales_activity.this, "Error: " + e, Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    gps.showSettingsAlert();
                }
            } else {
                AlertDialog dyam_dialog = new AlertDialog.Builder(Act_sales_activity.this).create();
                dyam_dialog.setTitle("Peringatan");
                dyam_dialog.setIcon(R.drawable.warning);
                dyam_dialog.setMessage("Mohon dilengkapi field yang disediakan");
                dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dyam_dialog.show();
            }
        }

    }

    public void onBackPressed(){
        DialogInterface.OnClickListener dd_dialog = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        dd_kembali();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Yakin Ingin Kembali?").setPositiveButton("Tidak", dd_dialog).setNegativeButton("Ya", dd_dialog).show();
    }
    public void dd_kembali(){
        finish();
    }
    public void msisdn_kurang(){
        android.app.AlertDialog dd_dialog = new android.app.AlertDialog.Builder(Act_sales_activity.this).create();
        dd_dialog.setTitle("Peringatan");
        dd_dialog.setIcon(R.drawable.warning);
        dd_dialog.setMessage("Mohon Input MSISDN 10 sampai 12 Digit");
        dd_dialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dd_dialog.show();
    }
    public void pesanUnkn() {
        AlertDialog dd_dialog = new AlertDialog.Builder(Act_sales_activity.this).create();
        dd_dialog.setTitle("Peringatan");
        dd_dialog.setIcon(R.drawable.warning);
        dd_dialog.setMessage("Aplikasi ini memakai koneksi internet, mohon diperiksa koneksinya...");
        dd_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dd_dialog.show();
    }
    private void simp_db(String S_MSISDN_MKIOSz, String S_CLUSTERz, String S_MSISDN_PLGz, String S_JUMLAHz,
                         String s_LATz, String s_LONGz, String  s_PJPz,String s_KUNJUNGANz) {
        class SendPostReqAsyncTask extends AsyncTask< String, Void, String > {@
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Act_sales_activity.this);
            pDialog.setMessage("Proses Penyimpanan");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }@
                Override
        protected String doInBackground(String...params) {
            String S_MSISDN_MKIOSy = params[0];
            String S_CLUSTERy = params[1];
            String S_MSISDN_PLGy = params[2];
            String S_JUMLAHy = params[3];
            String s_LATy = params[4];
            String s_LONGy = params[5];
            String s_PJPy = params[6];
            String s_KUNJUNGANy = params[7];

            List< NameValuePair > nameValuePairs = new ArrayList< NameValuePair >();
            nameValuePairs.add(new BasicNameValuePair("MSISDN_MKIOSx", S_MSISDN_MKIOSy));
            nameValuePairs.add(new BasicNameValuePair("CLUSTERx", S_CLUSTERy));
            nameValuePairs.add(new BasicNameValuePair("MSISDN_PLGx", S_MSISDN_PLGy));
            nameValuePairs.add(new BasicNameValuePair("JUMLAHx", S_JUMLAHy));
            nameValuePairs.add(new BasicNameValuePair("LAT_x", s_LATy));
            nameValuePairs.add(new BasicNameValuePair("LONG_x", s_LONGy));
            nameValuePairs.add(new BasicNameValuePair("lokasi", s_PJPy));
            nameValuePairs.add(new BasicNameValuePair("keg_kunjunganx", s_KUNJUNGANy));

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(dyam_url_sales_act);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();

            } catch (ClientProtocolException e) {

            } catch (IOException e) {

            }
            return "success";
        }

            @
                    Override
            protected void onPostExecute(String result) {
                pDialog.dismiss();
                super.onPostExecute(result);
                Toast.makeText(getApplicationContext(), "Data Request sukses dikirim", Toast.LENGTH_LONG).show();


                finish();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(S_MSISDN_MKIOS, S_CLUSTER, S_MSISDN_PLG, S_JUMLAH, s_LAT, s_LONG, s_PJP,s_KUNJUNGAN);

    }

    public class AmbilPJP extends AsyncTask< String, String, String > {
        String[] str2;

        @
                Override
        protected String doInBackground(String...arg0) {
            String url = "http://own-youth.com/dd_json/tugas.php";
            JSONParser jParser = new JSONParser();
            JSONObject json1 = null;
            int i;
            String aa = a.getusnme();
            JSONObject json = jParser.ambilURL(url + "?ID=" + aa);
            //JSONObject json = jParser.ambilURL(url);
            try {
                contacts = json.getJSONArray("tugas");
                str2 = new String[contacts.length()];
                for (int aai = 0; aai < contacts.length(); aai++) {
                    JSONObject c = contacts.getJSONObject(aai);
                    json1 = contacts.getJSONObject(aai);
                    str2[aai] = c.getString("NAMA_LOKASI");
                }
            } catch (JSONException e) {

            }

            return null;
        }@
                Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            for (int i = 0; i < str2.length; i++) {
                list4.add(str2[i]);
            }
            ArrayAdapter < String > dataAdapter2 = new ArrayAdapter < String >(getApplicationContext(),
                    android.R.layout.simple_spinner_item, list4);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_pjp.setAdapter(dataAdapter2);
            spn_pjp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Object item = parent.getItemAtPosition(position);
                    pilih_pjp = spn_pjp.getSelectedItem().toString();
                    if (pilih_pjp.length()>0) {
                        ed_lokasi_pjpp.setText(pilih_pjp);
                        ed_lokasi_pjpp.setEnabled(false);
                        fix_lokasi = ed_lokasi_pjpp.getText().toString();
                        harus_upd = "ya";
                    }
                    else{
                        ed_lokasi_pjpp.setEnabled(true);
                        harus_upd = "tidak";
                    }
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

    }



    @
            SuppressLint("ShowToast")
    private void setpic() throws IOException {
        int targetH = img_cams.getHeight() * 2;
        int targetW = img_cams.getWidth() * 2;
        String url_foto;
        if (cek.equals("galery")) {
            url_foto = selectedImagePath;
            if (url_foto == null) {
                Toast.makeText(getApplicationContext(), "URL FOTONYA NULL", Toast.LENGTH_LONG);
                url_foto = "/storage/emulated/0/DCIM/Facebook/FB_IMG_1442847270929.jpg";
            } else {
                Toast.makeText(getApplicationContext(), "URL : " + url_foto, Toast.LENGTH_LONG);
                Log.d("url_foto", url_foto);
            }
        } else {
            url_foto = mCurrentPhotoPath;
        }
        url_foto = mCurrentPhotoPath;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(url_foto, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor << 1;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(url_foto, bmOptions);
        Matrix mtx = new Matrix();
        ExifInterface ei = new ExifInterface(url_foto);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                mtx.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                mtx.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                mtx.postRotate(270);
                break;
        }
        rotateBMP = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mtx, true);
        img_cams.setImageBitmap(rotateBMP);

    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {}
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    String mCurrentPhotoPath;
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    private static File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }
    private String getImagePath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                uri, null, android.provider.MediaStore.Images.Media._ID + " = ? ", new String[] {
                        document_id
                }, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;

    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String storageDir = Environment.getExternalStorageDirectory() + "/pic_penjualan";
        File dir = new File(storageDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File image = new File(storageDir + "/" + imageFileName + ".jpg");
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.i(TAG, "photo path = " + mCurrentPhotoPath);
        return image;
    }

    private void sendphoto(Bitmap bitmap) throws Exception {
        try {
            new Uploadz().execute(bitmap);
        } catch (Exception e) {}
    }

    private class Uploadz extends AsyncTask < Bitmap, Void, Void > {
        RequestHandler rh = new RequestHandler();

        @
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            //    loading = ProgressDialog.show(Act_sales_activity.this, "Sedang Mengupload Gambar", "Mohon Tunggu...", true, true);
        }

        @
                Override
        protected Void doInBackground(Bitmap...params) {
            if (params[0] == null)
                return null;
            setProgress(0);
            Bitmap bitmap = params[0];
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            InputStream in = new ByteArrayInputStream(stream.toByteArray());
            try {
                //skripku
                String uploadImage = getStringImage(bitmap);
                HashMap< String, String > data = new HashMap < String, String > ();
                data.put(UPLOAD_KEY, uploadImage);
                data.put("nm_lokasi", fix_lokasi);
                String result = rh.sendPostRequest(UPLOAD_URL, data);
            } finally {

            }

            if ( in != null) {
                try { in .close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @
                Override
        protected void onProgressUpdate(Void...values) {
            super.onProgressUpdate(values);
        }

        @
                Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }
    }
    private void upd_pjp(String nm_lokasi, String stats) {

        class SendPostReqAsyncTask extends AsyncTask < String, Void, String > {

            @
                    Override
            protected String doInBackground(String...params) {
                for (int ai = 0; ai < fix_lokasi.length(); ai++) {
                    if (Character.isWhitespace(fix_lokasi.charAt(ai)))
                        countzz++;
                }
                if (countzz > 0) {
                    nm_loksss = fix_lokasi.replace(' ', '+');
                } else {
                    nm_loksss = fix_lokasi;
                }

                String nm_lokasix = params[0];
                String statsx = params[1];

                List < NameValuePair > nameValuePairs = new ArrayList < NameValuePair > ();
                nameValuePairs.add(new BasicNameValuePair("nm_lokasi", fix_lokasi));
                nameValuePairs.add(new BasicNameValuePair("statusnya", statsx));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    UPD_PJP = UPD_PJP + "?NAMA_LOKASI=" + nm_loksss;
                    HttpPost httpPost = new HttpPost(UPD_PJP);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "success";
            }

            @
                    Override
            protected void onPostExecute(String result) {
                pDialog.dismiss();
                super.onPostExecute(result);
                //  Toast.makeText(getApplicationContext(), "Simpan Sukses", Toast.LENGTH_LONG).show();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(fix_lokasi, status_pjp);

    }
    public void upd_gambar(){
        try {
            if (cek.equals("galery")) {
                sendphoto(rotateBMP);
            }
            else {
                sendphoto(rotateBMP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}