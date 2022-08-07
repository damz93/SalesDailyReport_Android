package dypta_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.dypta_2.R;

public class Act_penjualan_kartu extends Activity implements View.OnClickListener {
	Button b_next2, qrcode_as, qrcode_simpati, qrcode_loop, save_as, save_simpati, save_loop;
	TextView msisdn_as, msisdn_simpati, msisdn_loop;
	EditText e_circle40k, e_circle70k, e_member, e_cug, e_maxloop, e_nsp, e_other, e_suploop, e_loophol, e_pdk2gb, e_pdk4gb;
    AutoCompleteTextView act_msisdn_sim, act_msisdn_a, act_msisdn_loop;
	TextView e_simp,e_as,e_loop;
	String i_tugz, longz, latz, tglz, idz, clusterz="g";
    String aaz="cluster";
    ProgressDialog pDialog;
    private static String upd_msisdn = "http://own-youth.com/dd_json/upd_msisdn.php";
    Integer jml_msd_as,jml_msd_simp, jml_msd_loop;
    String db_datamsisdn_as,db_datamsisdn_simp,db_datamsisdn_loop,status_upd;
    JSONArray contacts = null;
    private static String url = "http://own-youth.com/dd_json/semua_msisdn.php";
    final List< String > listZ = new ArrayList < String > ();
	Act_set_get x2 = new Act_set_get();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_penjualan_kartu);
		//dari form pilih kegiatan
		Bundle b = getIntent().getExtras();
		longz = b.getString("long");
		latz = b.getString("lat");
		tglz = b.getString("tgl_kerja");
		i_tugz = b.getString("id_tugas");
		idz = b.getString("id_tugas");
        clusterz = x2.getcluster().toString().trim();
		//dari tampilan penjualan kartu
		e_simp = (TextView) findViewById(R.id.ed_simpati);
		e_as = (TextView) findViewById(R.id.ed_as);
		e_loop = (TextView) findViewById(R.id.ed_loop);
		e_circle40k = (EditText) findViewById(R.id.ed_circle40K);
		e_circle70k = (EditText) findViewById(R.id.ed_circle70K);
		e_member = (EditText) findViewById(R.id.ed_member);
		e_cug = (EditText) findViewById(R.id.ed_cug);
		e_maxloop = (EditText) findViewById(R.id.ed_maxiloop);
		e_nsp = (EditText) findViewById(R.id.ed_nsp);
		e_other = (EditText) findViewById(R.id.ed_other);
		e_suploop = (EditText)findViewById(R.id.ed_suploop);
		e_loophol = (EditText)findViewById(R.id.ed_loophol);
		e_pdk2gb = (EditText)findViewById(R.id.ed_pdk2gb);
		e_pdk4gb = (EditText)findViewById(R.id.ed_pdk4gb);


        //msisdn_as, msisdn_simpati, msisdn_loop;
        msisdn_as = (TextView)findViewById(R.id.tx_msisdnas);
        msisdn_simpati = (TextView)findViewById(R.id.tx_msisdnsimpati);
        msisdn_loop = (TextView)findViewById(R.id.tx_msisdnloop);


		b_next2 = (Button) findViewById(R.id.bt_next2);
        b_next2.setOnClickListener(this);

		qrcode_as = (Button)findViewById(R.id.bt_qrcode_as);
        qrcode_as.setOnClickListener(this);

		qrcode_simpati = (Button)findViewById(R.id.bt_qrcode_simpati);
        qrcode_simpati.setOnClickListener(this);

		qrcode_loop = (Button)findViewById(R.id.bt_qrcode_loop);
        qrcode_loop.setOnClickListener(this);

        save_as = (Button)findViewById(R.id.bt_saveas);
        save_as.setOnClickListener(this);

		save_simpati = (Button)findViewById(R.id.bt_savesimpati);
        save_simpati.setOnClickListener(this);

		save_loop = (Button)findViewById(R.id.bt_saveloop);
        save_loop.setOnClickListener(this);

        act_msisdn_sim = (AutoCompleteTextView)findViewById(R.id.act_msisdn_simp);
        act_msisdn_loop = (AutoCompleteTextView)findViewById(R.id.act_msisdn_loop);
        act_msisdn_a = (AutoCompleteTextView)findViewById(R.id.act_msisdn_as);

		e_simp.setText(x2.get_pr_simpati());
		e_loop.setText(x2.get_pr_loop());
		e_as.setText(x2.get_pr_as());

		e_circle40k.setText(x2.get_pr_circle40k());
		e_circle70k.setText(x2.get_pr_circle70k());
		e_member.setText(x2.get_pr_member());
		e_cug.setText(x2.get_pr_cug());
		e_maxloop.setText(x2.get_pr_maxloop());
		e_nsp.setText(x2.get_pr_nsp());
		e_other.setText(x2.get_pr_other());

        e_suploop.setText(x2.get_pr_suploop());
        e_loophol.setText(x2.get_pr_loophol());
        e_pdk2gb.setText(x2.get_pr_pdk2gb());
        e_pdk4gb.setText(x2.get_pr_pdk4gb());
     //   Toast.makeText(Act_penjualan_kartu.this,"Isi cluster anda: "+clusterz,Toast.LENGTH_LONG).show();



        //Toas(url + "?cluster=" + aaz);
     //   Toast.makeText(Act_penjualan_kartu.this,"Isi URLnya: "+url+"?cluster="+aaz,Toast.LENGTH_LONG).show();
        try {
            new buka_MSISDN().execute();
        }catch (Exception e){
            Toast.makeText(Act_penjualan_kartu.this,"Error "+e,Toast.LENGTH_LONG).show();
        }

	}

	public void onClick(View v) {
		if (v == b_next2) {
			if ((e_simp.length() > 0) && (e_as.length() > 0) && (e_loop.length() > 0) && (e_circle40k.length() > 0) && (e_circle70k.length() > 0)
                    && (e_member.length() > 0) && (e_cug.length() > 0) && (e_maxloop.length() > 0) && (e_nsp.length() > 0)
                    && (e_other.length() > 0)&& (e_suploop.length() > 0) && (e_loophol.length() > 0) && (e_pdk2gb.length() > 0)&& (e_pdk4gb.length() > 0) ) {
				Intent in = new Intent(getApplicationContext(), Act_penjualan_mkios.class);
                in.putExtra("k_simpatiy", e_simp.getText().toString().trim());
                in.putExtra("k_asy", e_as.getText().toString().trim());
                in.putExtra("k_loopy", e_loop.getText().toString().trim());
                in.putExtra("k_circle40ky", e_circle40k.getText().toString().trim());
                in.putExtra("k_circle70ky", e_circle70k.getText().toString().trim());
                in.putExtra("k_membery", e_member.getText().toString().trim());
                in.putExtra("k_cugy", e_cug.getText().toString().trim());
                in.putExtra("k_maxloopy", e_maxloop.getText().toString().trim());

                in.putExtra("k_suploopy", e_suploop.getText().toString().trim());
                in.putExtra("k_loopholy", e_loophol.getText().toString().trim());
                in.putExtra("k_pdk2gby", e_pdk2gb.getText().toString().trim());
                in.putExtra("k_pdk4gby", e_pdk4gb.getText().toString().trim());

                in.putExtra("k_nspy", e_nsp.getText().toString().trim());
                in.putExtra("k_othery", e_other.getText().toString().trim());
                in.putExtra("longy", longz); in .putExtra("laty", latz);
                in.putExtra("tgl_kerjay", tglz); in .putExtra("id_tugasy", idz);
                /* Toast.makeText(Act_penjualan_kartu.this, "Simpati: "+e_simp.getText().toString()+
                 		" As: "+e_as.getText().toString()+" Loop: "+e_loop.getText().toString()+
                 		" Long: "+longz+" Lat: "+latz+" Tgl Kerja: "+tglz+" Id Tugas: "+idz
                 		, Toast.LENGTH_LONG).show();
                 		*/

                x2.set_pr_suploop(e_suploop.getText().toString().trim());
                x2.set_pr_pdk2gb(e_pdk2gb.getText().toString().trim());
                x2.set_pr_pdk4gb(e_pdk4gb.getText().toString().trim());
                x2.set_pr_loophol(e_loophol.getText().toString().trim());

				startActivity( in );
			} else {
				//Toast.makeText(Act_penjualan_kartu.this, "Mohon dilengkapi field yang disediakan", Toast.LENGTH_LONG).show();
				try {
					AlertDialog dyam_dialog = new AlertDialog.Builder(Act_penjualan_kartu.this).create();
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
					Toast.makeText(Act_penjualan_kartu.this, "Ini errornya: " + e, Toast.LENGTH_LONG).show();
				}
			}
		}
        else if (v == qrcode_as){
            if(e_as.getText().toString().equals("0")){
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
            }
            else{
                AlertDialog dyam_dialog = new AlertDialog.Builder(Act_penjualan_kartu.this).create();
                dyam_dialog.setTitle("Peringatan");
                dyam_dialog.setIcon(R.drawable.warning);
                dyam_dialog.setMessage("Save terlebih dahulu..");
                dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
        }
        else if(v == qrcode_loop){
            if(e_loop.getText().toString().equals("0")){
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 1);
            }
            else{
                AlertDialog dyam_dialog = new AlertDialog.Builder(Act_penjualan_kartu.this).create();
                dyam_dialog.setTitle("Peringatan");
                dyam_dialog.setIcon(R.drawable.warning);
                dyam_dialog.setMessage("Save terlebih dahulu..");
                dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
        }

        else if(v == qrcode_simpati){
            if(e_simp.getText().toString().equals("0")){
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 2);
            }
            else{
                AlertDialog dyam_dialog = new AlertDialog.Builder(Act_penjualan_kartu.this).create();
                dyam_dialog.setTitle("Peringatan");
                dyam_dialog.setIcon(R.drawable.warning);
                dyam_dialog.setMessage("Save terlebih dahulu..");
                dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
        }
        else if(v == save_as){
            status_upd = "Done";
            if (e_as.length() > 10 ){
               upd_msisdn(e_as.getText().toString(),status_upd, longz, latz);
            }
            else{
                AlertDialog dyam_dialog = new AlertDialog.Builder(Act_penjualan_kartu.this).create();
                dyam_dialog.setTitle("Peringatan");
                dyam_dialog.setIcon(R.drawable.warning);
                dyam_dialog.setMessage("Input MSISDN As terlebih dahulu");
                dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }

        }
	}

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");

                Integer l_contents = contents.length();
                Integer l_cont2 = l_contents - 12;

                String nomor_scan = contents.substring(l_cont2, l_contents);
                String a = msisdn_as.getText().toString();
                jml_msd_as = Integer.parseInt(e_as.getText().toString());

                if (a.contains(nomor_scan)) {
                    AlertDialog dyam_dialog = new AlertDialog.Builder(Act_penjualan_kartu.this).create();
                    dyam_dialog.setTitle("iPeringatan");
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
                        msisdn_as.setText(a + "|" + nomor_scan);
                        db_datamsisdn_as = (a + "|" + nomor_scan).toString();
                        jml_msd_as++;
                    } else {
                        msisdn_as.setText(nomor_scan);
                        db_datamsisdn_as = (nomor_scan).toString();
                        jml_msd_as = 1;
                    }
                    e_as.setText(jml_msd_as.toString());
                }

            } else if (resultCode == RESULT_CANCELED) {
                Toast toast = Toast.makeText(this, "Scan was Cancelled!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            }
        }
    }



	public void onBackPressed() {
		x2.set_pr_as(e_as.getText().toString().trim());
		x2.set_pr_loop(e_loop.getText().toString().trim());
		x2.set_pr_simpati(e_simp.getText().toString().trim());
		x2.set_pr_circle40k(e_circle40k.getText().toString().trim());
		x2.set_pr_circle70k(e_circle70k.getText().toString().trim());
		x2.set_pr_member(e_member.getText().toString().trim());
		x2.set_pr_cug(e_cug.getText().toString().trim());
		x2.set_pr_maxloop(e_maxloop.getText().toString().trim());
		x2.set_pr_nsp(e_nsp.getText().toString().trim());
		x2.set_pr_other(e_other.getText().toString().trim());
        x2.set_pr_suploop(e_suploop.getText().toString().trim());
        x2.set_pr_pdk2gb(e_pdk2gb.getText().toString().trim());
        x2.set_pr_pdk4gb(e_pdk4gb.getText().toString().trim());
        x2.set_pr_loophol(e_loophol.getText().toString().trim());
		super.onBackPressed();
	}

    public class buka_MSISDN extends AsyncTask< String, String, String > {
        String[] str2 ;
        int countz = 0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @
                Override
        protected String doInBackground(String...arg0) {
            for (int ai = 0; ai < clusterz.length(); ai++) {
                if (Character.isWhitespace(clusterz.charAt(ai)))
                    countz++;
            }
            if (countz > 0) {
                aaz = clusterz.replace(' ', '+');
                // clus = aaz;
            } else {
                aaz = clusterz;
            }
            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.ambilURL(url + "?cluster=" + aaz);
            JSONObject json1 = null;
            try {
                contacts = json.getJSONArray("DATA_MSISDN");
                str2 = new String[contacts.length()];
                for (int aai = 0; aai < contacts.length(); aai++) {
                    JSONObject c = contacts.getJSONObject(aai);
                    json1 = contacts.getJSONObject(aai);
                    str2[aai] = c.getString("MSISDN");
                }
            } catch (JSONException e) {
            }


            return null;
        }@
                Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            for (int i = 0; i < str2.length; i++) {
                listZ.add(str2[i]);
            }
            Collections.sort(listZ);

            ArrayAdapter< String > dataAdapterz = new ArrayAdapter < String >
                    (getApplicationContext(), android.R.layout.simple_spinner_item, listZ);

            dataAdapterz.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            act_msisdn_sim.setThreshold(1);
            act_msisdn_sim.setAdapter(dataAdapterz);
            act_msisdn_loop.setThreshold(1);
            act_msisdn_loop.setAdapter(dataAdapterz);
            act_msisdn_a.setThreshold(1);
            act_msisdn_a.setAdapter(dataAdapterz);

            /*
             super.onPostExecute(result);

            for (int i = 0; i < str1.length; i++) {
                list2.add(str1[i]);
            }
            Collections.sort(list);
            ArrayAdapter < String > dataAdapter2 = new ArrayAdapter < String >
                    (getApplicationContext(), android.R.layout.simple_spinner_item, list2);

            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ed_jenis_pro.setThreshold(1);
            ed_jenis_pro.setAdapter(dataAdapter2);
             */


        }

    }

    private void upd_msisdn(String msisdn, String status, String longzz, String latzz) {

        class SendPostReqAsyncTask extends AsyncTask < String, Void, String > {

            @
                    Override
            protected String doInBackground(String...params) {
                String msisdnx = params[0];
                String statusx = params[1];
                String longx = params[2];
                String latx = params[3];


                List <NameValuePair> nameValuePairs = new ArrayList < NameValuePair > ();
                nameValuePairs.add(new BasicNameValuePair("msisdn", msisdnx));
                nameValuePairs.add(new BasicNameValuePair("status", statusx));
                nameValuePairs.add(new BasicNameValuePair("longg", longx));
                nameValuePairs.add(new BasicNameValuePair("lat", latx));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    upd_msisdn = upd_msisdn + "?msisdn=" + msisdnx;
                    HttpPost httpPost = new HttpPost(upd_msisdn);
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
        sendPostReqAsyncTask.execute(e_as.getText().toString(), status_upd, longz, latz);
    }


}