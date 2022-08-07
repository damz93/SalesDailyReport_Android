package dypta_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import app.dypta_2.R;

public class Act_survey1 extends Activity implements AdapterView.OnItemSelectedListener {
    Act_set_get xd = new Act_set_get();
    String item[] = {
            "Telkomsel",
            "Indosat",
            "XL-Axiata",
            "Three",
            "Smartfren",
            "Yang lain"
    };
    String item2[] = {
            "2G,3G,4G",
            "2G,3G",
            "4G/LTE",
            "3G/HSDPA",
            "2G/GPRS",
            "WIFI ID"
    };
    String item3[] = {
            "Good",
            "Foor"
    };
    String item4[] = {
            "Tidak",
            "Iya"
    };
    Spinner sp_lap_basket, sp_mading, sp_kantin, sp_wall_climb, sp_wall_paint, sp_knd_nwork, sp_knd_sinyal, sp_reselr;
    EditText e_lain1, e_lain2, e_lain3, e_lain4, e_lain5, e_lain6;
    String tgldd;
    String st_lain1, st_lain2, st_lain3, st_lain4, st_lain5, st_lain6;
    EditText e_npsn, e_pgn_telk, e_pgn_inds, e_pgn_xl, e_pgn_3, e_pgn_smrt, e_pgn_lain, e_sisa_stok, e_prg_telk, e_prg_lain;
    String id_tgas, id_lks, nm_loks, js_kgt, pilih1, pilih2, pilih3, pilih4, pilih5, pilih6, pilih7, pilih8;
    String es_nm_lks, es_id_tgs, es_npsn, es_pgn_telk, es_pgn_inds, es_pgn_xl, es_pgn_3, es_pgn_smrt, es_pgn_lain,
            latq, longq, es_sisa_stok, es_prg_telk, es_prg_lain, es_lain1, es_lain2, es_lain3, es_lain4, es_lain5, es_lain6, es_lain7, es_lain8;
    Button bt_simpsurv;
    TextView tx_lokasi;
    Calendar tgl = Calendar.getInstance();
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    JSONP2 jParser = new JSONP2();
    ProgressDialog pDialog;
    String stt_surv;
    GPSTracker gps;
    private static String dyam_url_surv = "http://own-youth.com/dd_json/simp_survey.php";
    private static String dyam_url_up_surv = "http://own-youth.com/dd_json/upd_survey.php";
    @
            Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.lay_survey1);

        tx_lokasi = (TextView) findViewById(R.id.tx_lokasiz);
        Bundle b = getIntent().getExtras();
        id_tgas = b.getString("id_tugas");
        id_lks = b.getString("id_lokasi");
        nm_loks = b.getString("nama_lokasi");
        js_kgt = b.getString("jns_kgt");

        tx_lokasi.setText(nm_loks);

        sp_lap_basket = (Spinner) findViewById(R.id.spn_lapbas);
        sp_mading = (Spinner) findViewById(R.id.spn_mading);
        sp_kantin = (Spinner) findViewById(R.id.spn_kantin);
        sp_wall_climb = (Spinner) findViewById(R.id.spn_wclimb);
        sp_wall_paint = (Spinner) findViewById(R.id.spn_wpaint);
        sp_knd_nwork = (Spinner) findViewById(R.id.spn_knds_nwork);
        sp_knd_sinyal = (Spinner) findViewById(R.id.spn_knds_sinyal);
        sp_reselr = (Spinner) findViewById(R.id.spn_reseller);

        e_npsn = (EditText) findViewById(R.id.ed_npsn);
        e_npsn.setText(id_lks);
        e_npsn.setEnabled(false);
        e_pgn_telk = (EditText) findViewById(R.id.ed_pgn_telkomsel);
        e_pgn_telk.requestFocus();
        e_pgn_inds = (EditText) findViewById(R.id.ed_pgn_indosat);
        e_pgn_xl = (EditText) findViewById(R.id.ed_pgn_xl);
        e_pgn_3 = (EditText) findViewById(R.id.ed_pgn_3);
        e_pgn_smrt = (EditText) findViewById(R.id.ed_pgn_smartfren);
        e_pgn_lain = (EditText) findViewById(R.id.ed_pgn_lain);

        e_sisa_stok = (EditText) findViewById(R.id.ed_jml_sch);
        e_prg_telk = (EditText) findViewById(R.id.ed_prg_telk);
        e_prg_lain = (EditText) findViewById(R.id.ed_prg_lain);

        e_lain1 = (EditText) findViewById(R.id.ed_lain1);
        e_lain2 = (EditText) findViewById(R.id.ed_lain2);
        e_lain3 = (EditText) findViewById(R.id.ed_lain3);
        e_lain4 = (EditText) findViewById(R.id.ed_lain4);
        e_lain5 = (EditText) findViewById(R.id.ed_lain5);
        e_lain6 = (EditText) findViewById(R.id.ed_lain6);

        ArrayAdapter < String > adapter = new ArrayAdapter < String > (this, android.R.layout.simple_spinner_dropdown_item, item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter < String > adapter2 = new ArrayAdapter < String > (this, android.R.layout.simple_spinner_dropdown_item, item2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter < String > adapter3 = new ArrayAdapter < String > (this, android.R.layout.simple_spinner_dropdown_item, item3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter < String > adapter4 = new ArrayAdapter < String > (this, android.R.layout.simple_spinner_dropdown_item, item4);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_lap_basket.setAdapter(adapter);
        sp_mading.setAdapter(adapter);
        sp_kantin.setAdapter(adapter);
        sp_wall_climb.setAdapter(adapter);
        sp_wall_paint.setAdapter(adapter);
        sp_knd_nwork.setAdapter(adapter2);
        sp_knd_sinyal.setAdapter(adapter3);
        sp_reselr.setAdapter(adapter4);

        sp_lap_basket.setOnItemSelectedListener(this);
        sp_mading.setOnItemSelectedListener(this);
        sp_kantin.setOnItemSelectedListener(this);
        sp_wall_climb.setOnItemSelectedListener(this);
        sp_wall_paint.setOnItemSelectedListener(this);
        sp_knd_nwork.setOnItemSelectedListener(this);
        sp_knd_sinyal.setOnItemSelectedListener(this);
        sp_reselr.setOnItemSelectedListener(this);

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        tgldd = sdf2.format(tgl.getTime());

        bt_simpsurv = (Button) findViewById(R.id.btn_simp_survey);
        bt_simpsurv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //List field yang akan dimasukkan di database
                es_nm_lks = nm_loks;
                es_id_tgs = id_tgas;

                es_lain1 = e_lain1.getText().toString();
                es_lain2 = e_lain2.getText().toString();
                es_lain3 = e_lain3.getText().toString();
                es_lain4 = e_lain4.getText().toString();
                es_lain5 = e_lain5.getText().toString();
                es_lain6 = e_lain6.getText().toString();
                es_npsn = e_npsn.getText().toString();
                es_pgn_telk = e_pgn_telk.getText().toString();
                es_pgn_inds = e_pgn_inds.getText().toString();
                es_pgn_xl = e_pgn_xl.getText().toString();
                es_pgn_3 = e_pgn_3.getText().toString();
                es_pgn_smrt = e_pgn_smrt.getText().toString();
                es_pgn_lain = e_pgn_lain.getText().toString();
                es_sisa_stok = e_sisa_stok.getText().toString();
                es_prg_telk = e_prg_telk.getText().toString();
                es_prg_lain = e_prg_lain.getText().toString();
                gps = new GPSTracker(Act_survey1.this);
                //  if (es_npsn.length() > 0) {
                if ((es_npsn.length() > 0) && (es_pgn_telk.length() > 0) && (es_pgn_inds.length() > 0) && (es_pgn_xl.length() > 0) && (es_pgn_3.length() > 0) && (es_pgn_smrt.length() > 0) && (es_pgn_lain.length() > 0) && (es_sisa_stok.length() > 0) && (es_prg_telk.length() > 0) && (es_prg_lain.length() > 0) && (es_lain1.length() > 0) && (es_lain2.length() > 0) && (es_lain3.length() > 0) && (es_lain4.length() > 0) && (es_lain5.length() > 0) && (es_lain6.length() > 0)) {
                    //new input2().execute();
                    if (gps.canGetLocation()) {
                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();
                        latq = (String.valueOf(latitude));
                        longq = (String.valueOf(longitude));
                        if ((latitude == 0.0) || (longitude == 0.0)) {
                            Toast.makeText(getApplicationContext(), "Lokasi sementera direload, mohon dicoba lagi!!!", Toast.LENGTH_LONG).show();
                        } else {
                            cd = new ConnectionDetector(getApplicationContext());
                            isInternetPresent = cd.isConnectingToInternet();
                            try {
                                if (isInternetPresent) {
                                    stt_surv = "0";
                                    DialogInterface.OnClickListener dd_dialog = new DialogInterface.OnClickListener() {

                                        @
                                                Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    //Yes button clicked

                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    simp_db(es_id_tgs, es_nm_lks, es_npsn, es_pgn_telk, es_pgn_inds, es_pgn_xl, es_pgn_3, es_pgn_smrt,
                                                            es_pgn_lain, es_sisa_stok, es_prg_telk, es_prg_lain, es_lain1, es_lain2, es_lain3, es_lain4,
                                                            es_lain5, es_lain6, es_lain7, es_lain8, tgldd, latq, longq);
                                                    upd_db(es_id_tgs, stt_surv);
                                                    break;
                                            }
                                        }
                                    };
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Act_survey1.this);
                                    builder.setMessage("Yakin ingin menyimpan?").setPositiveButton("Tidak", dd_dialog).setNegativeButton("Ya", dd_dialog).show();
                                } else {
                                    pesanUnkn();
                                }
                            } catch (Exception e) {
                                Toast.makeText(Act_survey1.this, "Error: " + e, Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        gps.showSettingsAlert();
                    }
                } else {
                    try {
                        AlertDialog dyam_dialog = new AlertDialog.Builder(Act_survey1.this).create();
                        dyam_dialog.setTitle("Peringatan");
                        dyam_dialog.setIcon(R.drawable.warning);
                        dyam_dialog.setMessage("Mohon dilengkapi field yang disediakan");
                        dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dyam_dialog.show();
                    } catch (Exception e) {
                        Toast.makeText(Act_survey1.this, "Ini errornya: " + e, Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }

    public void onItemSelected(AdapterView < ? > parent, View arg1, int arg2, long arg3) {
        // String isi1,isi2,isi3,isi4,isi5,isi6,isi7,isi8;
        pilih1 = sp_lap_basket.getSelectedItem().toString();
        pilih2 = sp_mading.getSelectedItem().toString();
        pilih3 = sp_kantin.getSelectedItem().toString();
        pilih4 = sp_wall_climb.getSelectedItem().toString();
        pilih5 = sp_wall_paint.getSelectedItem().toString();
        pilih6 = sp_reselr.getSelectedItem().toString();
        pilih7 = sp_knd_nwork.getSelectedItem().toString();
        pilih8 = sp_knd_sinyal.getSelectedItem().toString();
        parent.getItemAtPosition(arg2);
        switch (parent.getId()) {
            case R.id.spn_lapbas:
                if (pilih1.equals("Yang lain")) {
                    e_lain1.setFocusableInTouchMode(true);
                    e_lain1.requestFocus();
                } else {
                    e_lain1.setFocusable(false);
                }
                e_lain1.setText(pilih1);
                break;
            case R.id.spn_mading:
                if (pilih2.equals("Yang lain")) {
                    e_lain2.setFocusableInTouchMode(true);
                    e_lain2.requestFocus();
                } else {
                    e_lain2.setFocusable(false);
                }
                e_lain2.setText(pilih2);
                break;
            case R.id.spn_kantin:
                if (pilih3.equals("Yang lain")) {
                    e_lain3.setFocusableInTouchMode(true);
                    e_lain3.requestFocus();
                } else {
                    e_lain3.setFocusable(false);
                }
                e_lain3.setText(pilih3);
                break;
            case R.id.spn_wclimb:
                if (pilih4.equals("Yang lain")) {
                    e_lain4.setFocusableInTouchMode(true);
                    e_lain4.requestFocus();
                } else {
                    e_lain4.setFocusable(false);
                }
                e_lain4.setText(pilih4);
                break;
            case R.id.spn_wpaint:
                if (pilih5.equals("Yang lain")) {
                    e_lain5.setFocusableInTouchMode(true);
                    e_lain5.requestFocus();
                } else {
                    e_lain5.setFocusable(false);
                }
                e_lain5.setText(pilih5);
                break;
            case R.id.spn_reseller:
                if (pilih6.equals("Iya")) {
                    e_lain6.setFocusableInTouchMode(true);
                    e_lain6.requestFocus();
                } else {
                    e_lain6.setFocusable(false);

                }
                e_lain6.setText("0");
                break;
            case R.id.spn_knds_nwork:
                es_lain7 = pilih7;
                break;
            case R.id.spn_knds_sinyal:
                es_lain8 = pilih8;
                break;
        }
    }

    public void onNothingSelected(AdapterView < ? > arg0) {

    }

    public class input2 extends AsyncTask < String, String, String > {
        String success,
                success2;@
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Act_survey1.this);
            pDialog.setMessage("Proses Penyimpanan");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }

        @
                Override
        protected String doInBackground(String...arg0) {
            List < NameValuePair > params = new ArrayList < NameValuePair > ();
            List < NameValuePair > params2 = new ArrayList < NameValuePair > ();
            stt_surv = "1";
            params.add((NameValuePair) new BasicNameValuePair("id_tugas", es_id_tgs));
            params.add((NameValuePair) new BasicNameValuePair("nama_lokasi", es_nm_lks));
            params.add((NameValuePair) new BasicNameValuePair("npsn", es_npsn));
            params.add((NameValuePair) new BasicNameValuePair("pgn_telkomsel", es_pgn_telk));
            params.add((NameValuePair) new BasicNameValuePair("pgn_indosat", es_pgn_inds));
            params.add((NameValuePair) new BasicNameValuePair("pgn_xl", es_pgn_xl));
            params.add((NameValuePair) new BasicNameValuePair("pgn_3", es_pgn_3));
            params.add((NameValuePair) new BasicNameValuePair("pgn_smrt", es_pgn_smrt));
            params.add((NameValuePair) new BasicNameValuePair("pgn_lain", es_pgn_lain));
            params.add((NameValuePair) new BasicNameValuePair("lap_basket", es_lain1));
            params.add((NameValuePair) new BasicNameValuePair("mading", es_lain2));
            params.add((NameValuePair) new BasicNameValuePair("kantin", es_lain3));
            params.add((NameValuePair) new BasicNameValuePair("wall_climbing", es_lain4));
            params.add((NameValuePair) new BasicNameValuePair("wall_painting", es_lain5));
            params.add((NameValuePair) new BasicNameValuePair("knd_network", es_lain7));
            params.add((NameValuePair) new BasicNameValuePair("knd_sinyal", es_lain8));
            params.add((NameValuePair) new BasicNameValuePair("reseller_telk", es_lain6));
            params.add((NameValuePair) new BasicNameValuePair("jml_sc_kantin", es_sisa_stok));
            params.add((NameValuePair) new BasicNameValuePair("pr_telkomsel", es_prg_telk));
            params.add((NameValuePair) new BasicNameValuePair("pr_lain", es_prg_lain));

            params2.add((NameValuePair) new BasicNameValuePair("id_tugas", es_id_tgs));
            params2.add((NameValuePair) new BasicNameValuePair("survey", stt_surv));

    /*JSONObject json = jParser.makeHttpRequest(dyam_url_surv, "POST", params);
    try {
        success = json.getString("success");
    } catch (Exception e) {
        Toast.makeText(getApplicationContext(), "Error surveynya: "+e,Toast.LENGTH_LONG).show();
    }
    return null;*/
            dyam_url_up_surv = dyam_url_up_surv + "?ID=" + es_id_tgs;
            JSONObject json2 = jParser.makeHttpRequest(dyam_url_up_surv, "POST", params2);
            try {
                success2 = json2.getString("success");
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error updatenya: " + e, Toast.LENGTH_LONG).show();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if (success.equals("1")) {
                Toast.makeText(getApplicationContext(), "Simpan Sukses", Toast.LENGTH_LONG).show();
                new AmbilData().execute();
                /*Intent in = new Intent(getApplicationContext(), Act_pilih_tugas.class);
                startActivity( in );*/
            } else {
                Toast.makeText(getApplicationContext(), "Data Gagal Tersimpan", Toast.LENGTH_LONG).show();
            }
        }
    }
    //proses simpan ke tabel t_survey
  /*  private void simp_db(String es_id_tgsz, String es_nm_lksz, String es_npsnz, String es_pgn_telkz, String es_pgn_indsz,
            String es_pgn_xlz,String es_pgn_3z,String es_pgn_smrtz,String es_pgn_lainz,String es_lain1z,String es_lain2z,String es_lain3z,
                         String es_lain4z,String es_lain5z,String es_lain7z,String es_lain8z,String es_lain6z,String es_sisa_stokz,String es_prg_telkz,String es_prg_lainz) {*/
    //private void simp_db(String es_id_tgsz, String es_nm_lksz, String es_npsnz){

    private void simp_db(String es_id_tgsz, String es_nm_lksz, String es_npsnz, String es_pgn_telkz, String es_pgn_indsz, String es_pgn_xlz, String es_pgn_3z,
                         String es_pgn_smrtz, String es_pgn_lainz, String es_sisa_stokz, String es_prg_telkz, String es_prg_lainz, String es_lain1z,
                         String es_lain2z, String es_lain3z, String es_lain4z, String es_lain5z, String es_lain6z, String es_lain7z, String es_lain8z, String tglddz,
                         String latqz, String longqz) {
        class SendPostReqAsyncTask extends AsyncTask < String, Void, String > {
            @
                    Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(Act_survey1.this);
                pDialog.setMessage("Proses Penyimpanan");
                pDialog.setIndeterminate(false);
                pDialog.show();
            }
            @
                    Override
            protected String doInBackground(String...params) {
                String es_id_tgsx = params[0];
                String es_nm_lksx = params[1];
                String es_npsnx = params[2];
                String es_pgn_telkx = params[3];
                String es_pgn_indsx = params[4];
                String es_pgn_xlx = params[5];
                String es_pgn_3x = params[6];
                String es_pgn_smrtx = params[7];
                String es_pgn_lainx = params[8];
                String es_sisa_stokx = params[9];
                String es_prg_telkx = params[10];
                String es_prg_lainx = params[11];
                String es_lain1x = params[12];
                String es_lain2x = params[13];
                String es_lain3x = params[14];
                String es_lain4x = params[15];
                String es_lain5x = params[16];
                String es_lain6x = params[17];
                String es_lain7x = params[18];
                String es_lain8x = params[19];
                String tglddx = params[20];
                String latqx = params[21];
                String longqx = params[22];
                List < NameValuePair > nameValuePairs = new ArrayList < NameValuePair > ();
                nameValuePairs.add(new BasicNameValuePair("id_tugas", es_id_tgsx));
                nameValuePairs.add(new BasicNameValuePair("nama_lokasi", es_nm_lksx));
                nameValuePairs.add(new BasicNameValuePair("npsn", es_npsnx));
                nameValuePairs.add(new BasicNameValuePair("pgn_telkomsel", es_pgn_telkx));
                nameValuePairs.add(new BasicNameValuePair("pgn_indosat", es_pgn_indsx));
                nameValuePairs.add(new BasicNameValuePair("pgn_xl", es_pgn_xlx));
                nameValuePairs.add(new BasicNameValuePair("pgn_3", es_pgn_3x));
                nameValuePairs.add(new BasicNameValuePair("pgn_smrt", es_pgn_smrtx));
                nameValuePairs.add(new BasicNameValuePair("pgn_lain", es_pgn_lainx));
                nameValuePairs.add(new BasicNameValuePair("jml_sc_kantin", es_sisa_stokx));
                nameValuePairs.add(new BasicNameValuePair("pr_telkomsel", es_prg_telkx));
                nameValuePairs.add(new BasicNameValuePair("pr_lain", es_prg_lainx));
                nameValuePairs.add(new BasicNameValuePair("lap_basket", es_lain1x));
                nameValuePairs.add(new BasicNameValuePair("mading", es_lain2x));
                nameValuePairs.add(new BasicNameValuePair("kantin", es_lain3x));
                nameValuePairs.add(new BasicNameValuePair("wall_climbing", es_lain4x));
                nameValuePairs.add(new BasicNameValuePair("wall_painting", es_lain5x));
                nameValuePairs.add(new BasicNameValuePair("reseller_telk", es_lain6x));
                nameValuePairs.add(new BasicNameValuePair("knd_network", es_lain7x));
                nameValuePairs.add(new BasicNameValuePair("knd_sinyal", es_lain8x));
                nameValuePairs.add(new BasicNameValuePair("tgl_survey", tglddx));
                nameValuePairs.add(new BasicNameValuePair("lat_surv", latqx));
                nameValuePairs.add(new BasicNameValuePair("long_surv", longqx));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(dyam_url_surv);
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
                Toast.makeText(getApplicationContext(), "Data survey sukses tersimpan", Toast.LENGTH_LONG).show();
                xd.setstatus_survey(stt_surv);
                xd.set_pesan_survey("Survey telah dilakukan di lokasi\n*");
                new AmbilData().execute();

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(es_id_tgs, es_nm_lks, es_npsn, es_pgn_telk, es_pgn_inds, es_pgn_xl, es_pgn_3, es_pgn_smrt, es_pgn_lain,
                es_sisa_stok, es_prg_telk, es_prg_lain, es_lain1, es_lain2, es_lain3, es_lain4, es_lain5, es_lain6, es_lain7, es_lain8, tgldd,
                latq, longq);
    }
    //proses update di table t_tugas field survey dari ya menjadi tidak
    private void upd_db(String es_id_tgsz, String surv) {
        class SendPostReqAsyncTask extends AsyncTask < String, Void, String > {
            @
                    Override
            protected String doInBackground(String...params) {
                String es_id_tgsx = params[0];
                String stt_survx = params[1];
                List < NameValuePair > nameValuePairs = new ArrayList < NameValuePair > ();
                nameValuePairs.add(new BasicNameValuePair("id_tugas", es_id_tgsx));
                nameValuePairs.add(new BasicNameValuePair("survey", stt_survx));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    dyam_url_up_surv = dyam_url_up_surv + "?ID=" + es_id_tgs;
                    HttpPost httpPost = new HttpPost(dyam_url_up_surv);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                } catch (ClientProtocolException e) {} catch (IOException e) {}
                return "success";
            }

            @
                    Override
            protected void onPostExecute(String result) {
                pDialog.dismiss();
                super.onPostExecute(result);
                //Toast.makeText(getApplicationContext(), "Simpan Sukses", Toast.LENGTH_LONG).show();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(es_id_tgs, stt_surv);
    }

    public class AmbilData extends AsyncTask < String, String, String > {
        JSONArray contacts = null;
        ArrayList <HashMap< String, String >> contactList = new ArrayList < HashMap < String, String >> ();@
                Override

        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(Act_aktivitas_sales.this);
            pDialog.setMessage("Class AmbilData");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/
        }

        @
                Override
        protected String doInBackground(String...arg0) {
            String url = "http://own-youth.com/dd_json/tugas.php";
            JSONParser jParser = new JSONParser();
            int i;
            String aa = xd.getusnme();
            JSONObject json = jParser.ambilURL(url + "?ID=" + id_tgas);
            //JSONObject json = jParser.ambilURL(url);
            try {
                contacts = json.getJSONArray("tugas");
                for (i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    HashMap < String, String > map = new HashMap < String, String > ();
                    xd.set_pesan_jual("Tugas Activity telah dilakukan, di lokasi\n*");
                    xd.set_pesan_survey("Tidak ada perintah survey di lokasi\n*");
                    xd.set_pesan_update("Tidak ada perintah update di lokasi\n*");
                }
            } catch (JSONException e) {

            }

            return null;
        }@
                Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            Intent in = new Intent(getApplicationContext(), Act_pilih_tugas.class);
            in .putExtra("id_tugas", id_tgas);
            in .putExtra("id_lokasi", id_lks);
            in .putExtra("nama_lokasi", nm_loks);
            in .putExtra("SURVEY", stt_surv);
            in .putExtra("jenis_kegiatan", js_kgt);
            finish();
            startActivity( in );
        }
    }


    public void pesanUnkn() {
        AlertDialog dd_dialog = new AlertDialog.Builder(Act_survey1.this).create();
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
}