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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.dypta_2.R;

public class Act_kantin extends Activity {
    TextView tx_user;
    String i_tugas, i_lokasi, s_jml_perdana, s_jml_jual;
    JSONArray contacts = null;
    Act_set_get xyz = new Act_set_get();
    TextView t_id_kantin, t_nm_kantin, t_nm_pemilik, t_nmhp, t_no_mkios;
    EditText e_jumperd_telkoms, e_jumperd_indosat, e_jumper_xl, e_jumper_three, e_jumper_other;
    EditText e_jumpenjpul_telk, e_jumpenjpul_indosat, e_jumpenjpul_xl, e_jumpenjpul_three, e_jumpenjpul_other;
    EditText e_jumppenjpdata_telk, e_jumppenjpdata_indosat, e_jumppenjpdata_xl, e_jumppenjpdata_three, e_jumppenjpdata_other;
    String s_id_kantin, s_nm_kantin, s_nm_pemilik, s_nohp, s_nomkios;
    String s_jumper_telk, s_jumper_idst, s_jumper_xl, s_jumper_3, s_jumper_other, s_jumpenpul_telk, s_jumpenpul_idst, s_jumpenpul_xl,
            s_jumpenpul_3, s_jumpenpul_other, s_jumpndata_telk, s_jumpndata_idst, s_jumpndata_xl, s_jumpndata_three, s_jumpndata_other;
    ConnectionDetector cd2;
    JSONP2 jParser = new JSONP2();
    Boolean isInternetPresent2 = false;
    Button bt_krim_kn;
    private static String dyam_url_sim_kantin = "http://own-youth.com/dd_json/upd_Kantin.php";
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.lay_kantin);
        tx_user = (TextView) findViewById(R.id.txt_us_kan);
        t_id_kantin = (TextView) findViewById(R.id.tx_id_kantin);
        t_nm_kantin = (TextView) findViewById(R.id.tx_nama_kantin);
        t_nm_pemilik = (TextView) findViewById(R.id.tx_nama_pemilik);
        t_nmhp = (TextView) findViewById(R.id.tx_no_hanphone);
        t_no_mkios = (TextView) findViewById(R.id.tx_no_mkios);

        e_jumperd_telkoms = (EditText) findViewById(R.id.ed_jumperd_telkomsel);
        e_jumperd_indosat = (EditText) findViewById(R.id.ed_jumperd_indosat);
        e_jumper_xl = (EditText) findViewById(R.id.ed_jumperd_XL);
        e_jumper_three = (EditText) findViewById(R.id.ed_jumperd_three);
        e_jumper_other = (EditText) findViewById(R.id.ed_jumperd_other);

        e_jumpenjpul_telk = (EditText) findViewById(R.id.ed_jumpenj_telkomsel);
        e_jumpenjpul_indosat = (EditText) findViewById(R.id.ed_jumpenj_indosat);
        e_jumpenjpul_xl = (EditText) findViewById(R.id.ed_jumpenj_xl);
        e_jumpenjpul_three = (EditText) findViewById(R.id.ed_jumpenj_three);
        e_jumpenjpul_other = (EditText) findViewById(R.id.ed_jumpenj_other);

        e_jumppenjpdata_telk = (EditText) findViewById(R.id.ed_jumpenjdata_telkomsel);
        e_jumppenjpdata_indosat = (EditText) findViewById(R.id.ed_jumpenjdata_indosat);
        e_jumppenjpdata_xl = (EditText) findViewById(R.id.ed_jumpenjdata_xl);
        e_jumppenjpdata_three = (EditText) findViewById(R.id.ed_jumpenjdata_three);
        e_jumppenjpdata_other = (EditText) findViewById(R.id.ed_jumpenjdata_other);

        tx_user.setText("User Aktif : " + xyz.getnama());
        Bundle b = getIntent().getExtras();
        i_tugas = b.getString("id_tugas");
        i_lokasi = b.getString("id_lokasi");
        new AmbilData().execute();
        bt_krim_kn = (Button) findViewById(R.id.bt_kr_kantin);
        bt_krim_kn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_jumper_telk = e_jumperd_telkoms.getText().toString();
                s_jumper_idst = e_jumperd_indosat.getText().toString();
                s_jumper_xl = e_jumper_xl.getText().toString();
                s_jumper_3 = e_jumper_three.getText().toString();
                s_jumper_other = e_jumper_other.getText().toString();
                s_jumpenpul_telk = e_jumpenjpul_telk.getText().toString();
                s_jumpenpul_idst = e_jumpenjpul_indosat.getText().toString();
                s_jumpenpul_xl = e_jumpenjpul_xl.getText().toString();
                s_jumpenpul_3 = e_jumpenjpul_three.getText().toString();
                s_jumpenpul_other = e_jumpenjpul_other.getText().toString();
                s_jumpndata_telk = e_jumppenjpdata_telk.getText().toString();
                s_jumpndata_idst = e_jumppenjpdata_indosat.getText().toString();
                s_jumpndata_xl = e_jumppenjpdata_xl.getText().toString();
                s_jumpndata_three = e_jumppenjpdata_three.getText().toString();
                s_jumpndata_other = e_jumppenjpdata_other.getText().toString();

                s_id_kantin = t_id_kantin.getText().toString();
                s_nm_kantin = t_nm_kantin.getText().toString();

                cd2 = new ConnectionDetector(getApplicationContext());
                isInternetPresent2 = cd2.isConnectingToInternet();
                try {
                    if (isInternetPresent2) {
                        DialogInterface.OnClickListener dd_dialog = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        new Act_kantin.input().execute();
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(Act_kantin.this);
                        builder.setMessage("Yakin ingin menyimpan?").setPositiveButton("Tidak", dd_dialog).setNegativeButton("Ya", dd_dialog).show();

                    } else {
                        pesanUnkn();
                    }
                } catch (Exception e) {
                    Toast.makeText(Act_kantin.this, "Errornya: " + e, Toast.LENGTH_LONG).show();
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
            pDialog = new ProgressDialog(Act_kantin.this);
            pDialog.setMessage("Proses Input");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }

        @
                Override
        protected String doInBackground(String...arg0) {
            List < NameValuePair > params = new ArrayList < NameValuePair > ();

            params.add((NameValuePair) new BasicNameValuePair("i_tugasd", i_tugas));

            params.add((NameValuePair) new BasicNameValuePair("s_jumper_telkd", s_jumper_telk));
            params.add((NameValuePair) new BasicNameValuePair("s_jumper_idstd", s_jumper_idst));
            params.add((NameValuePair) new BasicNameValuePair("s_jumper_xld", s_jumper_xl));
            params.add((NameValuePair) new BasicNameValuePair("s_jumper_3d", s_jumper_3));
            params.add((NameValuePair) new BasicNameValuePair("s_jumper_otherd", s_jumper_other));
            params.add((NameValuePair) new BasicNameValuePair("s_jumpenpul_telkd", s_jumpenpul_telk));
            params.add((NameValuePair) new BasicNameValuePair("s_jumpenpul_idstd", s_jumpenpul_idst));
            params.add((NameValuePair) new BasicNameValuePair("s_jumpenpul_xld", s_jumpenpul_xl));
            params.add((NameValuePair) new BasicNameValuePair("s_jumpenpul_3d", s_jumpenpul_3));
            params.add((NameValuePair) new BasicNameValuePair("s_jumpenpul_otherd", s_jumpenpul_other));
            params.add((NameValuePair) new BasicNameValuePair("s_jumpndata_telkd", s_jumpndata_telk));
            params.add((NameValuePair) new BasicNameValuePair("s_jumpndata_idstd", s_jumpndata_idst));
            params.add((NameValuePair) new BasicNameValuePair("s_jumpndata_xld", s_jumpndata_xl));
            params.add((NameValuePair) new BasicNameValuePair("s_jumpndata_threed", s_jumpndata_three));
            params.add((NameValuePair) new BasicNameValuePair("s_jumpndata_otherd", s_jumpndata_other));

            params.add((NameValuePair) new BasicNameValuePair("s_id_kantind", s_id_kantin));
            params.add((NameValuePair) new BasicNameValuePair("s_nm_kantind", s_nm_kantin));



            dyam_url_sim_kantin = dyam_url_sim_kantin + "?ID=" + i_tugas;
            JSONObject json = jParser.makeHttpRequest(dyam_url_sim_kantin, "POST", params);
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
                Toast.makeText(getApplicationContext(), "Simpan Sukses", Toast.LENGTH_LONG).show();
                Intent in = new Intent(getApplicationContext(), Act_pilih_tugas.class);
                startActivity( in );
            } else {
                Toast.makeText(getApplicationContext(), "Data Gagal Tersimpan", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void pesanUnkn() {
        AlertDialog dd_dialog = new AlertDialog.Builder(Act_kantin.this).create();
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
    public class AmbilData extends AsyncTask < String, String, String > {
        //  ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        @
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Act_kantin.this);
            pDialog.setMessage("Loading Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String...params) {
            String url = "http://own-youth.com/dd_json/lihat_kantin.php";
            JSONParser jParser = new JSONParser();
            int i;
            JSONObject json = jParser.ambilURL(url + "?npsn=" + i_lokasi);
            try {
                contacts = json.getJSONArray("kantin");
                for (i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    //   HashMap<String, String> map = new HashMap<String, String>();
                    s_id_kantin = c.getString("id_kantin").trim();
                    s_nm_kantin = c.getString("nm_kantin").trim();
                    s_nm_pemilik = c.getString("nm_pemilik").trim();
                    s_nohp = c.getString("nohp").trim();
                    s_nomkios = c.getString("nmkios").trim();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        @
                Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            pDialog.dismiss();
            // Toast.makeText(Act_update_lokasi.this,"Isi id npsn: "+id_lks,Toast.LENGTH_LONG).show();
            t_id_kantin.setText(s_id_kantin);
            t_nm_kantin.setText(s_nm_kantin);
            t_nm_pemilik.setText(s_nm_pemilik);
            t_nmhp.setText(s_nohp);
            t_no_mkios.setText(s_nomkios);
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
}