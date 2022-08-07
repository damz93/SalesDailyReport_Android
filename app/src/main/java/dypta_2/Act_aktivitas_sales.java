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
import android.widget.CheckBox;
import android.widget.TextView;
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

public class Act_aktivitas_sales extends Activity {
    CheckBox c_sales, c_recharge, c_rechdata, c_fitur;
    TextView tx_user;
    Button b_kirim;
    String c_sl, c_rc, c_rd, c_ft;
    String longa, lata, n_lokasi,i_lokasi, i_tugas, statusu2, j_kegiatan, nm_cluster;
    Act_set_get xyz = new Act_set_get();
    ConnectionDetector cd2;
    Boolean isInternetPresent2 = false;
    JSONP2 jParser = new JSONP2();
    ProgressDialog pDialog;
    private static String dyam_url_upd_sales = "http://own-youth.com/dd_json/upd_salesNEW.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.lay_aktivitas_sales);
        c_sales = (CheckBox) findViewById(R.id.ck_sales);
        c_recharge = (CheckBox) findViewById(R.id.ck_recharge);
        c_rechdata = (CheckBox) findViewById(R.id.ck_rechdata);
        c_fitur = (CheckBox) findViewById(R.id.ck_fitur);
        tx_user = (TextView) findViewById(R.id.txt_us_sal);
        tx_user.setText("User Aktif : " + xyz.getnama());

        Bundle b = getIntent().getExtras();
        i_tugas = b.getString("id_tugas");
        i_lokasi = b.getString("id_lokasi");
        n_lokasi = b.getString("nama_lokasi");
        xyz.setnm_lokasi(n_lokasi);
        j_kegiatan = b.getString("jenis_kegiatan");
        nm_cluster = b.getString("cluster");
        longa = b.getString("long");
        lata = b.getString("lat");


        b_kirim = (Button) findViewById(R.id.bt_kr_sales);
        b_kirim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (c_sales.isChecked()) {
                    c_sl = "Ya";
                } else {
                    c_sl = "Tidak";
                }
                if (c_recharge.isChecked()) {
                    c_rc = "Ya";
                } else {
                    c_rc = "Tidak";
                }
                if (c_rechdata.isChecked()) {
                    c_rd = "Ya";
                } else {
                    c_rd = "Tidak";
                }
                if (c_fitur.isChecked()) {
                    c_ft = "Ya";
                } else {
                    c_ft = "Tidak";
                }

                cd2 = new ConnectionDetector(getApplicationContext());
                isInternetPresent2 = cd2.isConnectingToInternet();
                try {
                    if (isInternetPresent2) {
                        DialogInterface.OnClickListener dd_dialog = new DialogInterface.OnClickListener() {

                            @
                                    Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        new input().execute();
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(Act_aktivitas_sales.this);
                        builder.setMessage("Yakin ingin menyimpan?").setPositiveButton("Tidak", dd_dialog).setNegativeButton("Ya", dd_dialog).show();

                    } else {
                        pesanUnkn();
                    }
                } catch (Exception e) {
                    Toast.makeText(Act_aktivitas_sales.this, "Errornya: " + e, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public class input extends AsyncTask < String, String, String > {
        String success;

        @
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Act_aktivitas_sales.this);
            pDialog.setMessage("Proses Input");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }

        @
                Override
        protected String doInBackground(String...arg0) {
            List < NameValuePair > params = new ArrayList < NameValuePair > ();
            statusu2 = "DONE";
            params.add((NameValuePair) new BasicNameValuePair("i_tugasu", i_tugas));
   /*      params.add((NameValuePair) new BasicNameValuePair("j_kegiatanu", j_kegiatan));
         params.add((NameValuePair) new BasicNameValuePair("nm_clusteru", nm_cluster));*/
            params.add((NameValuePair) new BasicNameValuePair("statusu2u", statusu2));

            params.add((NameValuePair) new BasicNameValuePair("longau", longa));
            params.add((NameValuePair) new BasicNameValuePair("latau", lata));
            params.add((NameValuePair) new BasicNameValuePair("c_slu", c_sl));
            params.add((NameValuePair) new BasicNameValuePair("c_rcu", c_rc));
            params.add((NameValuePair) new BasicNameValuePair("c_rdu", c_rd));
            params.add((NameValuePair) new BasicNameValuePair("c_ftu", c_ft));

            dyam_url_upd_sales = dyam_url_upd_sales + "?ID=" + i_tugas;
            JSONObject json = jParser.makeHttpRequest(dyam_url_upd_sales, "POST", params);
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
                xyz.setstatus_jual(statusu2);
                xyz.set_pesan_jual("Penjualan telah dilakukan di lokasi\n*");
                Toast.makeText(getApplicationContext(), "Simpan Sukses", Toast.LENGTH_LONG).show();

               // Intent in = new Intent(getApplicationContext(), Act_utama2.class);
               // startActivity( in );
                new AmbilData().execute();
            } else {
                Toast.makeText(getApplicationContext(), "Data Gagal Tersimpan", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class AmbilData extends AsyncTask < String, String, String > {
        JSONArray contacts = null;
        String id, id_lokasix,nm_lokasi,kategori_kegiatan, cluster, longd,latd,sttus  ;
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
            String aa = xyz.getusnme();
            JSONObject json = jParser.ambilURL(url + "?ID=" + i_tugas);
            //JSONObject json = jParser.ambilURL(url);
            try {
                contacts = json.getJSONArray("tugas");
                for (i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    HashMap < String, String > map = new HashMap < String, String > ();
                    sttus = c.getString("STATUS").trim();
                    xyz.set_pesan_jual("Tugas Activity telah dilakukan, di lokasi\n*");
                    xyz.set_pesan_survey("Tidak ada perintah survey di lokasi\n*");
                    xyz.set_pesan_update("Tidak ada perintah update di lokasi\n*");
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
            in .putExtra("id_tugas", i_tugas);
            in .putExtra("id_lokasi", i_lokasi);
            in .putExtra("nama_lokasi", n_lokasi);
            in .putExtra("jenis_kegiatan", j_kegiatan);
            in .putExtra("cluster", nm_cluster);
            in .putExtra("long", longa);
            in .putExtra("lat", lata);
            in .putExtra("status", sttus);
            finish();
            startActivity( in );
        }
    }


    private void pesanUnkn() {
        AlertDialog dd_dialog = new AlertDialog.Builder(Act_aktivitas_sales.this).create();
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
}