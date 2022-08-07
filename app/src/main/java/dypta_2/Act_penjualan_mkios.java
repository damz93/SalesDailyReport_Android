package dypta_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.dypta_2.R;

public class Act_penjualan_mkios extends Activity implements View.OnClickListener {
	Button b_next3;
	String longx, latx, tglx, k_simpatix, k_asx, k_loopx, id_tugasx, k_circle40kx, k_circle70kx, k_memberx, k_cugx, k_maxloopx, k_nspx, k_otherx,
			k_pr_suploopx, k_pr_loopholx, k_pr_pdk2gbx, k_pr_pdk4gbx;
	EditText e_5k, e_10k, e_20k, e_25k, e_50k, e_100k;
	Act_set_get x3 = new Act_set_get();
	//5,10,20,25,50,100
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_penjualan_mkios);
		//dari form pilih kegiatan
		Bundle b = getIntent().getExtras();
		longx = b.getString("longy");
		latx = b.getString("laty");
		tglx = b.getString("tgl_kerjay");
		id_tugasx = b.getString("id_tugasy");

		//dari form penjualan kartu
		k_simpatix = b.getString("k_simpatiy");
		k_asx = b.getString("k_asy");
		k_loopx = b.getString("k_loopy");
		k_circle40kx = b.getString("k_circle40ky");
		k_circle70kx = b.getString("k_circle70ky");
		k_memberx = b.getString("k_membery");
		k_cugx = b.getString("k_cugy");
		k_maxloopx = b.getString("k_maxloopy");
		k_nspx = b.getString("k_nspy");
		k_otherx = b.getString("k_othery");
		k_pr_suploopx = b.getString("k_suploopy");
		k_pr_loopholx = b.getString("k_loopholy");
		k_pr_pdk2gbx = b.getString("k_pdk2gby");
		k_pr_pdk4gbx = b.getString("k_pdk4gby");

		//dari tampilan penjualan mkios
		e_5k = (EditText) findViewById(R.id.ed_5k);
		e_10k = (EditText) findViewById(R.id.ed_10k);
		e_20k = (EditText) findViewById(R.id.ed_20k);
		e_25k = (EditText) findViewById(R.id.ed_25k);
		e_50k = (EditText) findViewById(R.id.ed_50k);
		e_100k = (EditText) findViewById(R.id.ed_100k);
		b_next3 = (Button) findViewById(R.id.bt_next3);
		b_next3.setOnClickListener(this);

		e_5k.setText(x3.get_mkios_5k());
		e_10k.setText(x3.get_mkios_10k());
		e_20k.setText(x3.get_mkios_20k());
		e_25k.setText(x3.get_mkios_25k());
		e_50k.setText(x3.get_mkios_50k());
		e_100k.setText(x3.get_mkios_100k());
	}

	public void onClick(View v) {
		if (v == b_next3) {
			if (((((e_5k.length() > 0) && (e_10k.length() > 0) && (e_20k.length() > 0) && (e_25k.length() > 0) && (e_50k.length() > 0) && (e_100k.length() > 0))))) {
				Intent in = new Intent(getApplicationContext(), Act_penjualan_voucher.class);

				in .putExtra("e_5kw", e_5k.getText().toString());
				in .putExtra("e_10kw", e_10k.getText().toString());
				in .putExtra("e_20kw", e_20k.getText().toString()); in .putExtra("e_25kw", e_25k.getText().toString()); in .putExtra("e_50kw", e_50k.getText().toString()); in .putExtra("e_100kw", e_100k.getText().toString());

				in .putExtra("k_simpatiw", k_simpatix);
				in .putExtra("k_asw", k_asx);
				in .putExtra("k_loopw", k_loopx);

				in .putExtra("k_circle40kw", k_circle40kx);
				in .putExtra("k_circle70kw", k_circle70kx);
				in .putExtra("k_memberw", k_memberx);
				in .putExtra("k_cugw", k_cugx);
				in .putExtra("k_maxloopw", k_maxloopx);
				in .putExtra("k_nspw", k_nspx);
				in .putExtra("k_otherw", k_otherx);

				in .putExtra("k_pr_suploopw", k_pr_suploopx);
				in .putExtra("k_pr_loopholw", k_pr_loopholx);
				in .putExtra("k_pr_pdk2gbw", k_pr_pdk2gbx);
				in .putExtra("k_pr_pdk4gbw", k_pr_pdk4gbx);

				in .putExtra("longw", longx);
				in .putExtra("latw", latx);
				in .putExtra("tgl_kerjaw", tglx);
				in .putExtra("id_tugasw", id_tugasx);
                /* Toast.makeText(Act_penjualan_kartu.this, "Simpati: "+e_simp.getText().toString()+
                 		" As: "+e_as.getText().toString()+" Loop: "+e_loop.getText().toString()+
                 		" Long: "+longz+" Lat: "+latz+" Tgl Kerja: "+tglz+" Id Tugas: "+idz
                 		, Toast.LENGTH_LONG).show();
                 		*/
				//Toast.makeText(Act_penjualan_mkios.this,"Anjing!",Toast.LENGTH_LONG).show();
				startActivity( in );
			} else {
				//Toast.makeText(Act_penjualan_kartu.this, "Mohon dilengkapi field yang disediakan", Toast.LENGTH_LONG).show();
				try {
					AlertDialog dyam_dialog = new AlertDialog.Builder(Act_penjualan_mkios.this).create();
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
					Toast.makeText(Act_penjualan_mkios.this, "Ini errornya: " + e, Toast.LENGTH_LONG).show();
				}
			}
		}
	}
	public void onBackPressed() {
		x3.set_mkios_5k(e_5k.getText().toString().trim());
		x3.set_mkios_10k(e_10k.getText().toString().trim());
		x3.set_mkios_20k(e_20k.getText().toString().trim());
		x3.set_mkios_25k(e_25k.getText().toString().trim());
		x3.set_mkios_50k(e_50k.getText().toString().trim());
		x3.set_mkios_100k(e_100k.getText().toString().trim());
		super.onBackPressed();
		//   Toast.makeText(Act_penjualan_kartu.this, "ini listing kembali", Toast.LENGTH_LONG).show();
	}
}