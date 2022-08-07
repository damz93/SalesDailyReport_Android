package dypta_2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.dypta_2.R;

public class Act_pengaturan extends Activity implements View.OnClickListener {
	Button b_kmb, b_prbr;
	EditText e_ps_skg, e_ps_baru, e_u_ps;
	TextView tx_uname;
	JSONP2 jParser = new JSONP2();
	ProgressDialog dd_dialog;
	private TextView txtStatus;
	private String url1 = "http://own-youth.com/dd_json/sel_user.php";
	private String url2 = "http://own-youth.com/dd_json/upd_user.php";
	AlertDialog dyam_dialog;
	//Intent i_next4;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.lay_pengaturan);

		b_kmb = (Button) findViewById(R.id.bt_kembali2);
		b_prbr = (Button) findViewById(R.id.bt_perbarui);

		e_ps_skg = (EditText) findViewById(R.id.edt_pssekarang);
		e_ps_baru = (EditText) findViewById(R.id.edt_psbaru);
		e_u_ps = (EditText) findViewById(R.id.edt_psulang);

		txtStatus = (TextView) findViewById(R.id.txt_alert2);

		b_kmb.setOnClickListener(this);
		b_prbr.setOnClickListener(this);

		Act_set_get a = new Act_set_get();
		tx_uname = (TextView) findViewById(R.id.tx_user2);
		tx_uname.setText(a.getnama());
	}@
			Override
	public void onClick(View v) {
		if (v == b_kmb) {
			onBackPressed();
		} else if (v == b_prbr) {
			if (((e_u_ps.length() > 0) && (e_ps_skg.length() > 0) && (e_ps_baru.length() > 0))) {
				if (!(e_u_ps.getText().toString().equals(e_ps_baru.getText().toString()))) {
					dyam_dialog = new AlertDialog.Builder(Act_pengaturan.this).create();
					dyam_dialog.setTitle("Peringatan");
					dyam_dialog.setIcon(R.drawable.warning);
					dyam_dialog.setMessage("Field password baru dan field Ulangi password harus sesuai");
					dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					dyam_dialog.show();
				} else {
					readWebpage(v);
				}
			} else {
				dyam_dialog = new AlertDialog.Builder(Act_pengaturan.this).create();
				dyam_dialog.setTitle("Peringatan");
				dyam_dialog.setIcon(R.drawable.warning);
				dyam_dialog.setMessage("Mohon diisi field yang sudah disediakan");
				dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				dyam_dialog.show();
				e_u_ps.requestFocus();
			}
		}
	}
	void kosong() {
		e_ps_baru.setText("");
		e_ps_skg.setText("");
		e_u_ps.setText("");
		e_ps_skg.requestFocus();
	}

	public String getRequest(String Url) {
		String sret;
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(Url);
		try {
			HttpResponse response = client.execute(request);
			sret = request(response);
		} catch (Exception ex) {
			sret = "Failed Connect to server!";
		}
		return sret;
	}

	public static String request(HttpResponse response) {
		String result = "";
		try {
			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader( in ));
			StringBuilder str = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				str.append(line + "\n");
			} in .close();
			result = str.toString();
		} catch (Exception ex) {
			result = "Error";
		}
		return result;
	}

	private class CallWebPageTask extends AsyncTask < String, Void, String > {
		private ProgressDialog dialog;
		protected Context applicationContext;@
				Override
		protected void onPreExecute() {
			this.dialog = ProgressDialog.show(applicationContext, "Proses Mengupdate", "Please Wait...", true);
		}

		@
				Override
		protected String doInBackground(String...urls) {
			String response = "";
			response = getRequest(urls[0]);
			return response;

		}

		@
				Override
		protected void onPostExecute(String result) {
			this.dialog.cancel();
			txtStatus.setText(result);
			String u = txtStatus.getText().toString();
			if (u.substring(27, 29).trim().equals("ok")) {
				new input().execute();
			} else {
				AlertDialog dyam_dialog = new AlertDialog.Builder(Act_pengaturan.this).create();
				dyam_dialog.setTitle("Peringatan");
				dyam_dialog.setIcon(R.drawable.warning);
				dyam_dialog.setMessage("Password yang anda masukkan tidak benar...");
				dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				dyam_dialog.show();
				e_ps_skg.requestFocus();
			}
		}
	}

	public void readWebpage(View view) {
		CallWebPageTask task = new CallWebPageTask();
		task.applicationContext = Act_pengaturan.this;
		String url = url1 + "?usname=" + tx_uname.getText().toString() + "&psword=" + e_ps_skg.getText().toString();
		task.execute(new String[] {
				url
		});;
	}

	public class input extends AsyncTask < String, String, String > {
		String success;@
				Override
		protected void onPreExecute() {
			super.onPreExecute();
			dd_dialog = new ProgressDialog(Act_pengaturan.this);
			dd_dialog.setMessage("Proses Update");
			dd_dialog.setIndeterminate(false);
			dd_dialog.show();
		}

		@
				Override
		protected String doInBackground(String...arg0) {
			try {
				List < NameValuePair > params = new ArrayList < NameValuePair > ();
				params.add((NameValuePair) new BasicNameValuePair("usern", tx_uname.getText().toString()));
				params.add((NameValuePair) new BasicNameValuePair("pass", e_ps_baru.getText().toString()));
				url2 = url2 + "?USNAME=" + tx_uname.getText().toString();
				JSONObject json = jParser.makeHttpRequest(url2, "POST", params);
				success = json.getString("success");
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Errornya: " + e, Toast.LENGTH_LONG).show();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			dd_dialog.dismiss();
			if (success.equals("1")) {
				Toast.makeText(getApplicationContext(), "Update password berhasil", Toast.LENGTH_LONG).show();
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "Password Gagal diupdate", Toast.LENGTH_LONG).show();

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
						kosong();
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
}