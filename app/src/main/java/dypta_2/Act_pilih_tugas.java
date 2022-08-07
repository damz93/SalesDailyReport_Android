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
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.dypta_2.R;

public class Act_pilih_tugas extends Activity implements View.OnClickListener {
    String i_tugas, i_lokasi, n_lokasi, j_kegiatan, nm_cluster, longdd, latdd, surveyd, updated, statused;
    Button b_jual, b_update, b_survey, b_kantin, b_clout;
    ProgressDialog pDialog;
    AlertDialog dyam_dialog;
    private static String dyam_url_upd_jamsel = "http://own-youth.com/dd_json/upd_jamSelesai.php";
    private static String dyam_url_upd_jammul = "http://own-youth.com/dd_json/upd_jamMulai.php";
    Intent pind;
    String d_surv, d_updt, d_juala;
    JSONArray contacts = null;
    ConnectionDetector cd2;
    JSONP2 jParser = new JSONP2();
    Boolean isInternetPresent2 = false;
    Act_set_get xxd = new Act_set_get();

    //@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @
            Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.lay_pilih_tugas);

        b_jual = (Button) findViewById(R.id.bt_penjualan);
        b_update = (Button) findViewById(R.id.bt_update);
        b_survey = (Button) findViewById(R.id.bt_survey);
        b_kantin = (Button) findViewById(R.id.bt_kantin);
        b_clout = (Button) findViewById(R.id.bt_cloutt);

        b_jual.setOnClickListener(this);
        b_update.setOnClickListener(this);
        b_survey.setOnClickListener(this);
        b_kantin.setOnClickListener(this);
        b_clout.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        i_tugas = b.getString("id_tugas");
        i_lokasi = b.getString("id_lokasi");
        n_lokasi = b.getString("nama_lokasi");
        j_kegiatan = b.getString("jenis_kegiatan");
        nm_cluster = b.getString("cluster");
        surveyd = b.getString("SURVEY");
        updated = b.getString("UPDATEE");
        longdd = b.getString("long");
        latdd = b.getString("lat");
        statused = b.getString("status");
        if (j_kegiatan.equals("kampus") || (j_kegiatan.equals("sekolah"))) {
            new AmbilData().execute();
        }
        else{
            d_updt = "0";
            d_surv = "0";
            d_juala = statused;
        }
    }

    public void onClick(View v) {
        //    updated = xxd.getstatus_updatee();
        //    surveyd = xxd.getstatus_survey();
        try {
            if (v == b_update) {
                if (d_updt.equals("0")) {
                    dyam_dialog = new AlertDialog.Builder(Act_pilih_tugas.this).create();
                    dyam_dialog.setTitle("Peringatan");
                    dyam_dialog.setIcon(R.drawable.warning);
                    String pesanup;
                    pesanup = xxd.get_pesan_update();
                    dyam_dialog.setMessage(pesanup + n_lokasi + "*");
                    dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dyam_dialog.show();
                } else {
                    pind = new Intent(getApplicationContext(), Act_update_lokasi.class);
                    pind.putExtra("id_tugas", i_tugas);
                    pind.putExtra("id_lokasi", i_lokasi);
                    pind.putExtra("nama_lokasi", n_lokasi);
                    pind.putExtra("jns_kgt", j_kegiatan);
                    startActivity(pind);
                }
            } else if (v == b_survey) {
                if (d_surv.equals("0")) {
                    dyam_dialog = new AlertDialog.Builder(Act_pilih_tugas.this).create();
                    dyam_dialog.setTitle("Peringatan");
                    dyam_dialog.setIcon(R.drawable.warning);
                    String pesansur;
                    pesansur = xxd.get_pesan_survey();
                    dyam_dialog.setMessage(pesansur + n_lokasi + "*");
                    dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dyam_dialog.show();
                } else {
                    pind = new Intent(getApplicationContext(), Act_survey1.class);
                    pind.putExtra("id_tugas", i_tugas);
                    pind.putExtra("id_lokasi",i_lokasi);
                    pind.putExtra("nama_lokasi", n_lokasi);
                    pind.putExtra("jns_kgt", j_kegiatan);
                    finish();
                    startActivity(pind);
                }
            } else if (v == b_jual) {
                if (d_juala.equals("NOT")) {
                    /*Intent in = new Intent(getApplicationContext(), Act_pilih_kegiatan.class);
                    in .putExtra("id_tugas", i_tugas);
                    in .putExtra("nama_lokasi", n_lokasi);
                    in .putExtra("jenis_kegiatan", j_kegiatan);
                    in .putExtra("cluster", nm_cluster);
                    in .putExtra("long", longdd);
                    in .putExtra("lat", latdd);
                    startActivity( in );*/
                    new inputjam().execute();

                } else {
                    dyam_dialog = new AlertDialog.Builder(Act_pilih_tugas.this).create();
                    dyam_dialog.setTitle("Peringatan");
                    dyam_dialog.setIcon(R.drawable.warning);
                    String pesanjual;
                    pesanjual = xxd.get_pesan_jual();
                    dyam_dialog.setMessage(pesanjual + n_lokasi + "*");
                    dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dyam_dialog.show();
                }
            }
            else if (v == b_kantin){
                Intent in = new Intent(getApplicationContext(), Act_kantin.class);
                in .putExtra("id_tugas", i_tugas);
                in.putExtra("id_lokasi",i_lokasi);
                startActivity(in);
              //  Toast.makeText(Act_pilih_tugas.this,"tes",Toast.LENGTH_LONG).show();
            }
            else if (v == b_clout){
                if (d_updt.equals("0") && (d_surv.equals("0"))&&(d_juala.equals("DONE"))) {
                    cd2 = new ConnectionDetector(getApplicationContext());
                    isInternetPresent2 = cd2.isConnectingToInternet();
                    try {
                        if (isInternetPresent2) {
                            new input().execute();
                        } else {
                            pesanUnkn();
                        }
                    } catch (Exception e) {
                        Toast.makeText(Act_pilih_tugas.this, "Errornya: " + e, Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    try {
                        AlertDialog dyam_dialog = new AlertDialog.Builder(Act_pilih_tugas.this).create();
                        dyam_dialog.setTitle("Peringatan");
                        dyam_dialog.setIcon(R.drawable.warning);
                        dyam_dialog.setMessage("Anda belum menyelesaikan salah satu tugas\n(1)Aktivity,(2)Survey atau (3)Update Lokasi");
                        dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dyam_dialog.show();
                    } catch (Exception e) {
                        Toast.makeText(Act_pilih_tugas.this, "Ini errornya: " + e, Toast.LENGTH_LONG).show();
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(Act_pilih_tugas.this, "Errornya: " + e, Toast.LENGTH_LONG).show();
        }
    }

    public class AmbilData extends AsyncTask < String, String, String > {
        String d_sur, d_upd, d_jual;
        //ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

        @
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Act_pilih_tugas.this);
            pDialog.setMessage("Loading Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @
                Override
        protected String doInBackground(String...params) {
            String url = "http://own-youth.com/dd_json/surv_n_upd.php";
            JSONParser jParser = new JSONParser();
            int i;
            JSONObject json = jParser.ambilURL(url + "?NPSN=" + i_lokasi);
            try {
                contacts = json.getJSONArray("lokasi");
                for (i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    HashMap < String, String > map = new HashMap < String, String > ();
                    d_sur = c.getString("SURVEY").trim();
                    d_upd = c.getString("UPDATEE").trim();
                    d_jual = c.getString("STATUS").trim();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }@
                Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            d_surv = d_sur;
            d_updt = d_upd;
            d_juala = d_jual;
     //       Toast.makeText(Act_pilih_tugas.this,"Isi survey: "+d_surv+"\nIsi update: "+d_upd,Toast.LENGTH_LONG).show();
        }
    }
    public class input extends AsyncTask<String, String, String> {
        String success;

        @
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Act_pilih_tugas.this);
            pDialog.setMessage("Sedang Proses, Mohon ditunggu");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }

        @
                Override
        protected String doInBackground(String... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add((NameValuePair) new BasicNameValuePair("i_tugasu", i_tugas));
            dyam_url_upd_jamsel = dyam_url_upd_jamsel + "?ID=" + i_tugas;
            JSONObject json = jParser.makeHttpRequest(dyam_url_upd_jamsel, "POST", params);
            try {
                success = json.getString("success");

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Errornya: " + e, Toast.LENGTH_LONG).show();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if (success.equals("1")) {
                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Mohon diulangi.", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void pesanUnkn() {
        AlertDialog dd_dialog = new AlertDialog.Builder(Act_pilih_tugas.this).create();
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

    public class inputjam extends AsyncTask<String, String, String> {
        String success;

        @
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(Act_pilih_tugas.this);
            pDialog.setMessage("Sedang Proses, Mohon ditunggu");
            pDialog.setIndeterminate(false);
            pDialog.show();*/
        }

        @
                Override
        protected String doInBackground(String... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add((NameValuePair) new BasicNameValuePair("i_tugasu", i_tugas));
            dyam_url_upd_jammul = dyam_url_upd_jammul + "?ID=" + i_tugas;
            JSONObject json = jParser.makeHttpRequest(dyam_url_upd_jammul, "POST", params);
            try {
                success = json.getString("success");

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Errornya: " + e, Toast.LENGTH_LONG).show();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
          //  pDialog.dismiss();
            if (success.equals("1")) {
            //    Toast.makeText(getApplicationContext(), "(y)", Toast.LENGTH_LONG).show();
                Intent in = new Intent(getApplicationContext(), Act_aktivitas_sales.class);
                in .putExtra("id_tugas", i_tugas);
                in .putExtra("id_lokasi", i_lokasi);
                in .putExtra("nama_lokasi", n_lokasi);
                in .putExtra("jenis_kegiatan", j_kegiatan);
                in .putExtra("cluster", nm_cluster);
                in .putExtra("long", longdd);
                in .putExtra("lat", latdd);
                finish();
                startActivity(in);
            } else {
                Toast.makeText(getApplicationContext(), "Mohon diulangi.", Toast.LENGTH_LONG).show();
            }
        }
    }



}