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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.dypta_2.R;

public class Act_jam_mulai extends Activity {
    Button bt_mulai;
    String i_tugas, i_lokasi, n_lokasi, j_kegiatan, nm_cluster, longdd, latdd, surveyd, updated, statused;
    ConnectionDetector cd2;
    JSONP2 jParser = new JSONP2();
    Boolean isInternetPresent2 = false;
    private static String dyam_url_upd_jammul = "http://own-youth.com/dd_json/upd_jamMulai.php";
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.lay_jam_mulai);
        Bundle b = getIntent().getExtras();
        i_tugas = b.getString("id_tugas");
        i_lokasi = b.getString("id_lokasi");
        n_lokasi = b.getString("nama_lokasi");
        j_kegiatan = b.getString("jenis_kegiatan");
        nm_cluster = b.getString("cluster");
        longdd = b.getString("long");
        latdd = b.getString("lat");
        statused = b.getString("status");
        bt_mulai = (Button) findViewById(R.id.btn_mulai);
        bt_mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cd2 = new ConnectionDetector(getApplicationContext());
                isInternetPresent2 = cd2.isConnectingToInternet();
                try {
                    if (isInternetPresent2) {
                        new Act_jam_mulai.input().execute();
                    } else {
                        pesanUnkn();
                    }
                } catch (Exception e) {
                    Toast.makeText(Act_jam_mulai.this, "Errornya: " + e, Toast.LENGTH_LONG).show();
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
            pDialog = new ProgressDialog(Act_jam_mulai.this);
            pDialog.setMessage("Sedang Proses, Mohon ditunggu");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }

        @
                Override
        protected String doInBackground(String...arg0) {
            List < NameValuePair > params = new ArrayList < NameValuePair > ();
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
            pDialog.dismiss();
            if (success.equals("1")) {
                Toast.makeText(getApplicationContext(), "Selamat Bekerja", Toast.LENGTH_LONG).show();
                Intent in = new Intent(getApplicationContext(), Act_pilih_tugas.class); in .putExtra("id_tugas", i_tugas); in .putExtra("id_lokasi", i_lokasi); in .putExtra("nama_lokasi", n_lokasi); in .putExtra("jenis_kegiatan", j_kegiatan); in .putExtra("cluster", nm_cluster); in .putExtra("long", longdd); in .putExtra("lat", latdd); in .putExtra("status", statused);
                finish();
                startActivity( in );
            } else {
                Toast.makeText(getApplicationContext(), "Mohon diulangi.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void pesanUnkn() {
        AlertDialog dd_dialog = new AlertDialog.Builder(Act_jam_mulai.this).create();
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