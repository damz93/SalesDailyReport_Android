package dypta_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.dypta_2.R;

public class Act_trade_in extends Activity implements View.OnClickListener {
    Button b_send2;
    String longu2, latu2, tglu2, k_simpatiu2, k_asu2, k_loopu2, k_circle40ku2, k_circle70ku2, k_memberu2, k_cugu2, k_maxloopu2,
            k_pr_suploopu2, k_pr_loopholu2, k_pr_pdk2gbu2, k_pr_pdk4gbu2, k_nspu2, k_otheru2,
            id_tugasu2, e_5ku2, e_10ku2, e_20ku2, e_25ku2, e_50ku2, e_100ku2, e_v10ku2, e_v25ku2, e_v50ku2, e_v100ku2;
    String s_p5kv2, s_p10kv2, s_p20kv2, s_p25kv2, s_p50kv2, s_p100kv2;
    String s_xl, s_3, s_isat, s_smart, s_lain;
    EditText ed_xl, ed_isat, ed_th3, ed_smart, ed_lain;
    JSONP2 jParser = new JSONP2();
    ProgressDialog pDialog;
    String statusu2;
    ConnectionDetector cd2;
    Boolean isInternetPresent2 = false;
    Act_set_get x52 = new Act_set_get();

    private static String dyam_url_konz = "http://own-youth.com/dd_json/upd_jual.php";

    @
            Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_trade_in);

        Bundle b = getIntent().getExtras();
        //dari form pilih kegiatan
        longu2 = b.getString("longt");
        latu2 = b.getString("latt");
        tglu2 = b.getString("tglt");
        id_tugasu2 = b.getString("id_tugast");

        //dari form penjualan kartu
        k_simpatiu2 = b.getString("k_simpatit");
        k_asu2 = b.getString("k_ast");
        k_loopu2 = b.getString("k_loopt");
        k_circle40ku2 = b.getString("k_circle40kt");
        k_circle70ku2 = b.getString("k_circle70kt");
        k_memberu2 = b.getString("k_membert");
        k_cugu2 = b.getString("k_cugt");
        k_maxloopu2 = b.getString("k_maxloopt");
        k_nspu2 = b.getString("k_nspt");
        k_otheru2 = b.getString("k_othert");

  /*   k_pr_suploopu2 = b.getString("k_pr_suploopt");
        k_pr_loopholu2 = b.getString("k_pr_loopholt");
        k_pr_pdk2gbu2 = b.getString("k_pr_pdk2gbt");
        k_pr_pdk4gbu2 = b.getString("k_pr_pdk4gbt");
*/
        k_pr_suploopu2 = x52.get_pr_suploop();
        k_pr_loopholu2 = x52.get_pr_loophol();
        k_pr_pdk2gbu2 = x52.get_pr_pdk2gb();
        k_pr_pdk4gbu2 = x52.get_pr_pdk4gb();

        //dari form penjualan mkios
        e_5ku2 = b.getString("e_5kt");
        e_10ku2 = b.getString("e_10kt");
        e_20ku2 = b.getString("e_20kt");
        e_25ku2 = b.getString("e_25kt");
        e_50ku2 = b.getString("e_50kt");
        e_100ku2 = b.getString("e_100kt");

        //dari form penjualan voucher
        e_v10ku2 = b.getString("e_v10kt");
        e_v25ku2 = b.getString("e_v25kt");
        e_v50ku2 = b.getString("e_v50kt");
        e_v100ku2 = b.getString("e_v100kt");

        //dari tampilan penjualan paket
        s_p5kv2 = b.getString("s_p5kt");
        s_p10kv2 = b.getString("s_p10kt");
        s_p20kv2 = b.getString("s_p20kt");
        s_p25kv2 = b.getString("s_p25kt");
        s_p50kv2 = b.getString("s_p50kt");
        s_p100kv2 = b.getString("s_p100kt");
        //untuk trade in
        ed_xl = (EditText) findViewById(R.id.ed_th_xl);
        ed_th3 = (EditText) findViewById(R.id.ed_th3);
        ed_isat = (EditText) findViewById(R.id.ed_thisat);
        ed_smart = (EditText) findViewById(R.id.ed_thsmart);
        ed_lain = (EditText) findViewById(R.id.ed_thyalain);
        b_send2 = (Button) findViewById(R.id.bt_sendth);
        b_send2.setOnClickListener(this);

        ed_xl.setText(x52.get_th_xl());
        ed_th3.setText(x52.get_th_3());
        ed_isat.setText(x52.get_th_isat());
        ed_smart.setText(x52.get_th_smart());
        ed_lain.setText(x52.get_th_other());
    }

    @
            Override
    public void onClick(View v) {
        s_xl = ed_xl.getText().toString();
        s_3 = ed_th3.getText().toString();
        s_isat = ed_isat.getText().toString();
        s_smart = ed_smart.getText().toString();
        s_lain = ed_lain.getText().toString();
        if (v == b_send2) {
            if (((((s_xl.length() > 0) && (s_3.length() > 0) && (s_isat.length() > 0) && (s_smart.length() > 0) && (s_lain.length() > 0))))) {
                cd2 = new ConnectionDetector(getApplicationContext());
                isInternetPresent2 = cd2.isConnectingToInternet();
                try {
                    if (isInternetPresent2) {
      /*Toast.makeText(Act_trade_in.this,"Isi yg mau disimpan: \n"
              +x52.get_pr_suploop()+"\n"
              + x52.get_pr_loophol()+"\n"
              +x52.get_pr_pdk2gb()+"\n"
              +x52.get_pr_pdk4gb(),Toast.LENGTH_LONG).show();*/
                        new input().execute();
                    } else {
                        pesanUnkn();
                    }
                } catch (Exception e) {
                    Toast.makeText(Act_trade_in.this, "Errornya: " + e, Toast.LENGTH_LONG).show();
                }
            } else {
                try {
                    AlertDialog dyam_dialog = new AlertDialog.Builder(Act_trade_in.this).create();
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
                    Toast.makeText(Act_trade_in.this, "Ini errornya: " + e, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public class input extends AsyncTask < String, String, String > {
        String success;@
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Act_trade_in.this);
            pDialog.setMessage("Proses Input");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }

        @
                Override
        protected String doInBackground(String...arg0) {
            List < NameValuePair > params = new ArrayList < NameValuePair > ();
            statusu2 = "DONE";
            params.add((NameValuePair) new BasicNameValuePair("id", id_tugasu2));
            params.add((NameValuePair) new BasicNameValuePair("longg", longu2));
            params.add((NameValuePair) new BasicNameValuePair("lat", latu2));
            params.add((NameValuePair) new BasicNameValuePair("tgl", tglu2));
            params.add((NameValuePair) new BasicNameValuePair("status", statusu2));

            params.add((NameValuePair) new BasicNameValuePair("k_simpati", k_simpatiu2));
            params.add((NameValuePair) new BasicNameValuePair("k_as", k_asu2));
            params.add((NameValuePair) new BasicNameValuePair("k_loop", k_loopu2));
            params.add((NameValuePair) new BasicNameValuePair("k_circle40k", k_circle40ku2));
            params.add((NameValuePair) new BasicNameValuePair("k_circle70k", k_circle70ku2));
            params.add((NameValuePair) new BasicNameValuePair("k_member", k_memberu2));
            params.add((NameValuePair) new BasicNameValuePair("k_cug", k_cugu2));
            params.add((NameValuePair) new BasicNameValuePair("k_maxloop", k_maxloopu2));

            params.add((NameValuePair) new BasicNameValuePair("SUPER_LOOPx", x52.get_pr_suploop()));
            params.add((NameValuePair) new BasicNameValuePair("LOOP_HOLICx", x52.get_pr_loophol()));
            params.add((NameValuePair) new BasicNameValuePair("PDK_2GBx", x52.get_pr_pdk2gb()));
            params.add((NameValuePair) new BasicNameValuePair("PDK_4GBx", x52.get_pr_pdk4gb()));

            params.add((NameValuePair) new BasicNameValuePair("k_nsp", k_nspu2));
            params.add((NameValuePair) new BasicNameValuePair("k_other", k_otheru2));

            params.add((NameValuePair) new BasicNameValuePair("e_5k", e_5ku2));
            params.add((NameValuePair) new BasicNameValuePair("e_10k", e_10ku2));
            params.add((NameValuePair) new BasicNameValuePair("e_20k", e_20ku2));
            params.add((NameValuePair) new BasicNameValuePair("e_25k", e_25ku2));
            params.add((NameValuePair) new BasicNameValuePair("e_50k", e_50ku2));
            params.add((NameValuePair) new BasicNameValuePair("e_100k", e_100ku2));

            params.add((NameValuePair) new BasicNameValuePair("e_v10k", e_v10ku2));
            params.add((NameValuePair) new BasicNameValuePair("e_v25k", e_v25ku2));
            params.add((NameValuePair) new BasicNameValuePair("e_v50k", e_v50ku2));
            params.add((NameValuePair) new BasicNameValuePair("e_v100k", e_v100ku2));

            params.add((NameValuePair) new BasicNameValuePair("s_p5k", s_p5kv2));
            params.add((NameValuePair) new BasicNameValuePair("s_p10k", s_p10kv2));
            params.add((NameValuePair) new BasicNameValuePair("s_p20k", s_p20kv2));
            params.add((NameValuePair) new BasicNameValuePair("s_p25k", s_p25kv2));
            params.add((NameValuePair) new BasicNameValuePair("s_p50k", s_p50kv2));
            params.add((NameValuePair) new BasicNameValuePair("s_p100k", s_p100kv2));

            params.add((NameValuePair) new BasicNameValuePair("s_xld", s_xl));
            params.add((NameValuePair) new BasicNameValuePair("s_3d", s_3));
            params.add((NameValuePair) new BasicNameValuePair("s_isatd", s_isat));
            params.add((NameValuePair) new BasicNameValuePair("s_smartd", s_smart));
            params.add((NameValuePair) new BasicNameValuePair("s_laind", s_lain));

            dyam_url_konz = dyam_url_konz + "?ID=" + id_tugasu2;
            JSONObject json = jParser.makeHttpRequest(dyam_url_konz, "POST", params);
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
                x52.setstatus_jual(statusu2);
                x52.set_pesan_jual("Penjualan telah dilakukan di lokasi\n*");
                Toast.makeText(getApplicationContext(), "Simpan Sukses", Toast.LENGTH_LONG).show();
                //finish();
                Intent in = new Intent(getApplicationContext(), Act_utama2.class);
                startActivity( in );
            } else {
                Toast.makeText(getApplicationContext(), "Data Gagal Tersimpan", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onBackPressed() {
        x52.set_th_xl(ed_xl.getText().toString().trim());
        x52.set_th_3(ed_th3.getText().toString().trim());
        x52.set_th_isat(ed_isat.getText().toString().trim());
        x52.set_th_smart(ed_smart.getText().toString().trim());
        x52.set_th_other(ed_lain.getText().toString().trim());
        super.onBackPressed();
    }

    private void pesanUnkn() {
        AlertDialog dd_dialog = new AlertDialog.Builder(Act_trade_in.this).create();
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