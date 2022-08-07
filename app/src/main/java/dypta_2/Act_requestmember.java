package dypta_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.dypta_2.R;

public class Act_requestmember extends Activity implements View.OnClickListener{
    Button bt_scanner,bt_split,bt_kirim;
    TextView tx_msisdn_mk, tx_cluster, tx_msisdn_plgz, t_juml_msd;
    String s_cluster,s_user;
    private static String dyam_url_outlet = "http://www.own-youth.com/dd_json/simp_req_member.php";
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    String S_MSISDN_MKIOS, S_CLUSTER, S_MSISDN_PLG,S_JUMLAH, S_ID_COM, s_LAT, s_LONG;
    Integer jml_msd;
    EditText ed_msisdn_plg,ed_idcom;
    GPSTracker gps;
    ProgressDialog pDialog;
    String db_datamsisdn;
    Act_set_get a = new Act_set_get();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.lay_requestmember);
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
        tx_msisdn_plgz = (TextView) findViewById(R.id.tx_msisdn_plg);
        t_juml_msd = (TextView) findViewById(R.id.tx_jml_msisd);
        tx_cluster.setText(s_cluster);
        tx_msisdn_mk.setText(s_user);


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
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                // String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                // Handle successful scan
                //Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format , Toast.LENGTH_LONG);
                //toast.setGravity(Gravity.TOP, 25, 400);
                //toast.show();

                Integer l_contents = contents.length();
                Integer l_cont2 = l_contents - 12;

                String nomor_scan = contents.substring(l_cont2, l_contents);
                String a = tx_msisdn_plgz.getText().toString();
                jml_msd = Integer.parseInt(t_juml_msd.getText().toString());

                if (a.contains(nomor_scan)) {
                    AlertDialog dyam_dialog = new AlertDialog.Builder(Act_requestmember.this).create();
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
                    AlertDialog dyam_dialog = new AlertDialog.Builder(Act_requestmember.this).create();
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
            S_ID_COM = ed_idcom.getText().toString();

            gps = new GPSTracker(Act_requestmember.this);

            if ((S_MSISDN_PLG.length() > 5) && (S_ID_COM.length() > 0)) {
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
                                                simp_db(S_MSISDN_MKIOS, S_CLUSTER, S_MSISDN_PLG, S_JUMLAH, S_ID_COM, s_LAT, s_LONG);
                                                    /*Toast.makeText(Act_outlet.this,"Nama Outlet: "+s_NAMA_OUTLET+"" +
                                                            "\nData MSISDN: "+s_DATA_MSISDN+"\nJumlah Data: "+s_JUMLAH_DATA+"" +
                                                            "\nID SCC: "+s_ID_SCC+"\nKeterangan: "+s_KETERANGAN+"\n"+"Lat: "+s_LAT+"" +
                                                            "\nLong: "+s_LONG,Toast.LENGTH_LONG).show();*/
                                                break;
                                        }
                                    }
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(Act_requestmember.this);
                                builder.setMessage("Yakin ingin menyimpan?").setPositiveButton("Tidak", dd_dialog).setNegativeButton("Ya", dd_dialog).show();
                            } else {
                                pesanUnkn();
                            }
                        } catch (Exception e) {
                            Toast.makeText(Act_requestmember.this, "Error: " + e, Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    gps.showSettingsAlert();
                }
            } else {
                AlertDialog dyam_dialog = new AlertDialog.Builder(Act_requestmember.this).create();
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
        android.app.AlertDialog dd_dialog = new android.app.AlertDialog.Builder(Act_requestmember.this).create();
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
        AlertDialog dd_dialog = new AlertDialog.Builder(Act_requestmember.this).create();
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
    private void simp_db(String S_MSISDN_MKIOSz, String S_CLUSTERz, String S_MSISDN_PLGz, String S_JUMLAHz, String s_KETS_ID_COMz,
                         String s_LATz, String s_LONGz) {
        class SendPostReqAsyncTask extends AsyncTask< String, Void, String > {@
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Act_requestmember.this);
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
            String s_KETS_ID_COMy = params[4];
            String s_LATy = params[5];
            String s_LONGy = params[6];
            List< NameValuePair > nameValuePairs = new ArrayList< NameValuePair >();
            nameValuePairs.add(new BasicNameValuePair("MSISDN_MKIOSx", S_MSISDN_MKIOSy));
            nameValuePairs.add(new BasicNameValuePair("CLUSTERx", S_CLUSTERy));
            nameValuePairs.add(new BasicNameValuePair("MSISDN_PLGx", S_MSISDN_PLGy));
            nameValuePairs.add(new BasicNameValuePair("JUMLAHx", S_JUMLAHy));
            nameValuePairs.add(new BasicNameValuePair("KETS_ID_COMx", s_KETS_ID_COMy));
            nameValuePairs.add(new BasicNameValuePair("LAT_x", s_LATy));
            nameValuePairs.add(new BasicNameValuePair("LONG_x", s_LONGy));

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(dyam_url_outlet);
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
        sendPostReqAsyncTask.execute(S_MSISDN_MKIOS, S_CLUSTER, S_MSISDN_PLG, S_JUMLAH, S_ID_COM, s_LAT, s_LONG);

     }

}
