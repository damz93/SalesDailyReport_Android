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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import app.dypta_2.R;

public class Act_outlet extends Activity {
    Button scanner, b_kirim, b_split;
    TextView t_us;
    Integer jml_msd;
    String db_datamsisdn;
    String outlet[];
    String clus;
    RadioButton rd_cash, rd_konsinyasi;
    RadioGroup rd_jns;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    String id_sef, nm_out, tes, cluster;
    ProgressDialog pDialog;
    GPSTracker gps;
    private static String dyam_url_outlet = "http://www.own-youth.com/dd_json/simp_outlet.php";
    private static String url = "http://own-youth.com/dd_json/outlet.php";
    //Objek yang disimpan
    EditText e_ket_outlet, e_inpt_msisdn;
    TextView t_msisdn, t_jml_msd;
    final List < String > list = new ArrayList < String > ();
    AutoCompleteTextView e_nm_outlet;
    //Simpan ke database
    String s_ID_SEFIA, s_NAMA_OUTLET, s_ID_SCC, s_DATA_MSISDN, s_JUMLAH_DATA, s_KETERANGAN, s_LAT, s_LONG,
            s_jns_Tr, s_jum_AS, s_jum_simPATI, s_jum_loop;
    Integer i_jum_AS = 0, i_jum_loop = 0, i_jum_simpati = 0;


    JSONArray contacts = null;
    Act_set_get xyz = new Act_set_get();

    /**
     * Called when the activity is first created.
     */
    @
            Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.lay_act_outlet);

        t_msisdn = (TextView) findViewById(R.id.tx_msisdn);
        t_jml_msd = (TextView) findViewById(R.id.tx_jml_msd);
        t_us = (TextView) findViewById(R.id.txt_us2);
        //  e_nm_outlet = (EditText) findViewById(R.id.ed_nm_outl2);
        e_ket_outlet = (EditText) findViewById(R.id.ed_ktr_otl);
        //     e_inpt_msisdn = (EditText) findViewById(R.id.ed_inpt_msisdn);
        cluster = xyz.getcluster().toString().trim();
        // t_us.setText(xyz.getnama() + "\n" + cluster);

        t_us.setText(xyz.getnama());
        e_nm_outlet = (AutoCompleteTextView) findViewById(R.id.ed_nm_outl);


        s_jns_Tr = "Belum";
        rd_cash = (RadioButton) findViewById(R.id.rdi_cash);
        rd_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rd_cash.isChecked()) {
                    s_jns_Tr = "Konsinyasi";
                }
            }
        });
        rd_konsinyasi = (RadioButton) findViewById(R.id.rdi_konsinyasi);
        rd_konsinyasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rd_cash.isChecked()) {
                    s_jns_Tr = "Cash";
                }
            }
        });
        new AmbilOutlet().execute();
        b_kirim = (Button) findViewById(R.id.bt_kr_outl);
        /*        b_split = (Button) findViewById(R.id.bt_split);
                b_split.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (t_msisdn.length()<=10){
                            t_msisdn.setText(e_inpt_msisdn.getText().toString());
                        }
                        else{
                            t_msisdn.setText(t_msisdn.getText()+","+e_inpt_msisdn.getText().toString());
                        }


                    }
                });*/
        b_kirim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s_NAMA_OUTLET = e_nm_outlet.getText().toString();
                //    s_NAMA_OUTLET = "aa";
                s_DATA_MSISDN = t_msisdn.getText().toString();
                s_JUMLAH_DATA = t_jml_msd.getText().toString();
                s_ID_SCC = xyz.getnama();
                s_KETERANGAN = e_ket_outlet.getText().toString();
                s_jum_AS = i_jum_AS.toString();
                s_jum_loop = i_jum_loop.toString();
                s_jum_simPATI = i_jum_simpati.toString();
                gps = new GPSTracker(Act_outlet.this);

                if ((s_NAMA_OUTLET.length() > 0) && (s_KETERANGAN.length() > 0) && ((s_jns_Tr.equals("Belum")))) {
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
                                                    simp_db(s_NAMA_OUTLET, db_datamsisdn, s_JUMLAH_DATA, s_ID_SCC, s_KETERANGAN, s_LAT, s_LONG, s_jns_Tr, s_jum_AS, s_jum_simPATI, s_jum_loop);
                                                    /*Toast.makeText(Act_outlet.this,"Nama Outlet: "+s_NAMA_OUTLET+"" +
                                                            "\nData MSISDN: "+s_DATA_MSISDN+"\nJumlah Data: "+s_JUMLAH_DATA+"" +
                                                            "\nID SCC: "+s_ID_SCC+"\nKeterangan: "+s_KETERANGAN+"\n"+"Lat: "+s_LAT+"" +
                                                            "\nLong: "+s_LONG,Toast.LENGTH_LONG).show();*/
                                                    break;
                                            }
                                        }
                                    };
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Act_outlet.this);
                                    builder.setMessage("Yakin ingin menyimpan?").setPositiveButton("Tidak", dd_dialog).setNegativeButton("Ya", dd_dialog).show();
                                } else {
                                    pesanUnkn();
                                }
                            } catch (Exception e) {
                                Toast.makeText(Act_outlet.this, "Error: " + e, Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        gps.showSettingsAlert();
                    }
                } else {
                    AlertDialog dyam_dialog = new AlertDialog.Builder(Act_outlet.this).create();
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
        });

        try {
            scanner = (Button) findViewById(R.id.bt_qrcode);
            scanner.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                }
            });
            /*Button scanner2 = (Button)findViewById(R.id.scanner2);
            scanner2.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
                    startActivityForResult(intent, 0);
                }

            });*/
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
                if (nomor_scan.substring(1, 5).equals("0852")) {
                    i_jum_AS = i_jum_AS++;
                } else if (nomor_scan.substring(1, 5).equals("0815")) {
                    i_jum_loop = i_jum_loop++;
                } else if (nomor_scan.substring(1, 5).equals("0813")) {
                    i_jum_simpati = i_jum_simpati++;
                }
                String a = t_msisdn.getText().toString();
                jml_msd = Integer.parseInt(t_jml_msd.getText().toString());

                if (a.contains(nomor_scan)) {
                    AlertDialog dyam_dialog = new AlertDialog.Builder(Act_outlet.this).create();
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
                    if (a.length() > 8) {
                        //t_msisdn.setText(a + "\n" + nomor_scan);
                        t_msisdn.setText(a + "|" + nomor_scan);
                        db_datamsisdn = (a + "|" + nomor_scan).toString();
                        jml_msd++;
                    } else {
                        t_msisdn.setText(nomor_scan);
                        db_datamsisdn = (nomor_scan).toString();
                        jml_msd = 1;
                    }
                    t_jml_msd.setText(jml_msd.toString());
                }

            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
                Toast toast = Toast.makeText(this, "Scan was Cancelled!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            }
        }
    }

    public class AmbilOutlet extends AsyncTask < String, String, String > {
        ArrayList < HashMap < String,
                String >> contactList = new ArrayList < HashMap < String,
                String >> ();
        HashMap < String,
                String > map = new HashMap < String,
                String > ();
        String aaz;
        int countz = 0;
        String[] str1;@
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Act_outlet.this);
            pDialog.setMessage("Loading Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @
                Override
        protected String doInBackground(String...arg0) {
            for (int ai = 0; ai < cluster.length(); ai++) {
                if (Character.isWhitespace(cluster.charAt(ai)))
                    countz++;
            }
            if (countz > 0) {
                aaz = cluster.replace(' ', '+');
                // clus = aaz;
            } else {
                aaz = cluster;
            }
            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.ambilURL(url + "?cluster=" + aaz);
            JSONObject json1 = null;
            try {
                contacts = json.getJSONArray("OUTLETT");
                str1 = new String[contacts.length()];
                for (int aai = 0; aai < contacts.length(); aai++) {
                    JSONObject c = contacts.getJSONObject(aai);

                    json1 = contacts.getJSONObject(aai);
                    str1[aai] = c.getString("nama_outlet");
                }
            } catch (JSONException e) {}
            return null;
        }
        @
                Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();

            for (int i = 0; i < str1.length; i++) {
                list.add(str1[i]);
            }
            Collections.sort(list);
            ArrayAdapter < String > dataAdapter = new ArrayAdapter < String >
                    (getApplicationContext(), android.R.layout.simple_spinner_item, list);

            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            e_nm_outlet.setThreshold(1);
            e_nm_outlet.setAdapter(dataAdapter);



            //    Toast.makeText(Act_outlet.this,"Isi nama Outlet: "+nm_out,Toast.LENGTH_LONG).show();
        }

    }
    public void onBackPressed() {
        DialogInterface.OnClickListener dd_dialog = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
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
    public void dd_kembali() {
        finish();
    }
    public void pesanUnkn() {
        AlertDialog dd_dialog = new AlertDialog.Builder(Act_outlet.this).create();
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
    private void simp_db(String s_NAMA_OUTLETz, String s_DATA_MSISDNz, String s_JUMLAH_DATAz, String s_ID_SCCz, String s_KETERANGANz,
                         String s_LATz, String s_LONGz, String s_jns_Trz, String s_jum_ASz, String s_jum_simPATIz, String s_jum_loopz) {
        class SendPostReqAsyncTask extends AsyncTask < String, Void, String > {
            @
                    Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(Act_outlet.this);
                pDialog.setMessage("Proses Penyimpanan");
                pDialog.setIndeterminate(false);
                pDialog.show();
            }
            @
                    Override
            protected String doInBackground(String...params) {
                String s_NAMA_OUTLETy = params[0];
                String s_DATA_MSISDNy = params[1];
                String s_JUMLAH_DATAy = params[2];
                String s_ID_SCCy = params[3];
                String s_KETERANGANy = params[4];
                String s_LATy = params[5];
                String s_LONGy = params[6];
                String s_jns_Try = params[7];
                String s_jum_ASy = params[8];
                String s_jum_simPATIy = params[9];
                String s_jum_loopy = params[10];
                List < NameValuePair > nameValuePairs = new ArrayList < NameValuePair > ();
                nameValuePairs.add(new BasicNameValuePair("NAMA_OUTLETx", s_NAMA_OUTLETy));
                nameValuePairs.add(new BasicNameValuePair("DATA_MSISDNx", s_DATA_MSISDNy));
                nameValuePairs.add(new BasicNameValuePair("JUMLAH_DATAx", s_JUMLAH_DATAy));
                nameValuePairs.add(new BasicNameValuePair("ID_SCCx", s_ID_SCCy));
                nameValuePairs.add(new BasicNameValuePair("KETERANGANx", s_KETERANGANy));
                nameValuePairs.add(new BasicNameValuePair("LAT_OUTx", s_LATy));
                nameValuePairs.add(new BasicNameValuePair("LONG_OUTx", s_LONGy));
                nameValuePairs.add(new BasicNameValuePair("JENIS_TROUTx", s_jns_Try));
                nameValuePairs.add(new BasicNameValuePair("JUM_ASx", s_jum_ASy));
                nameValuePairs.add(new BasicNameValuePair("JUM_SIMPATIx", s_jum_simPATIy));
                nameValuePairs.add(new BasicNameValuePair("JUM_LOOPx", s_jum_loopy));


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
                Toast.makeText(getApplicationContext(), "Data Outlet sukses tersimpan", Toast.LENGTH_LONG).show();
                finish();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(s_NAMA_OUTLET, s_DATA_MSISDN, s_JUMLAH_DATA, s_ID_SCC, s_KETERANGAN, s_LAT, s_LONG,
                s_jns_Tr, s_jum_AS, s_jum_simPATI, s_jum_loop);
    }


}
//memecah karakter
/*
String[] pecah1 = kalimatPakaiSeparator.split(";");
System.out.println("Hasil pecahan: ");
for(int counter = 0; counter < pecah1.length; counter++){
System.out.println(" " + pecah1[counter]);
}
 */