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

public class Act_penjualan_voucher extends Activity implements View.OnClickListener {
	Button b_next4;
	String longv, latv, tglv, k_simpativ, k_asv, k_loopv, k_circle40kv, k_circle70kv, k_memberv, k_cugv, k_maxloopv, k_nspv, k_otherv,
			k_pr_suploopv, k_pr_loopholv, k_pr_pdk2gbv,k_pr_pdk4gbv,
			id_tugasv, e_5kv, e_10kv, e_20kv, e_25kv, e_50kv, e_100kv;
	EditText ed_10kv, ed_25kv, ed_50kv, ed_100kv;
	Act_set_get x4 = new Act_set_get();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_penjualan_voucher);
		//dari form pilih kegiatan
		Bundle b = getIntent().getExtras();
		longv = b.getString("longw");
		latv = b.getString("latw");
		tglv = b.getString("tgl_kerjaw");
		id_tugasv = b.getString("id_tugasw");

		//dari form penjualan kartu
		k_simpativ = b.getString("k_simpatiw");
		k_asv = b.getString("k_asw");
		k_loopv = b.getString("k_loopw");
		k_circle40kv = b.getString("k_circle40kw");
		k_circle70kv = b.getString("k_circle70kw");
		k_memberv = b.getString("k_memberw");
		k_cugv = b.getString("k_cugw");
		k_maxloopv = b.getString("k_maxloopw");
		k_nspv = b.getString("k_nspw");
		k_otherv = b.getString("k_otherw");
		k_pr_suploopv = b.getString("k_pr_suploopx");
		k_pr_loopholv = b.getString("k_pr_loopholx");
		k_pr_pdk2gbv = b.getString("k_pr_pdk2gbx");
		k_pr_pdk4gbv = b.getString("k_pr_pdk4gbx");


		//dari form penjualan mkios
		e_5kv = b.getString("e_5kw");
		e_10kv = b.getString("e_10kw");
		e_20kv = b.getString("e_20kw");
		e_25kv = b.getString("e_25kw");
		e_50kv = b.getString("e_50kw");
		e_100kv = b.getString("e_100kw");

		//dari tampilan penjualan voucher
		ed_10kv = (EditText) findViewById(R.id.edv_10k);
		ed_25kv = (EditText) findViewById(R.id.edv_25k);
		ed_50kv = (EditText) findViewById(R.id.edv_50k);
		ed_100kv = (EditText) findViewById(R.id.edv_100k);
		b_next4 = (Button) findViewById(R.id.bt_next4);
		b_next4.setOnClickListener(this);

		ed_10kv.setText(x4.get_voucher_10k());
		ed_25kv.setText(x4.get_voucher_25kk());
		ed_50kv.setText(x4.get_voucher_50k());
		ed_100kv.setText(x4.get_voucher_100k());

	}

	public void onClick(View v) {
		if (v == b_next4) {
			if (((((ed_10kv.length() > 0) && (ed_25kv.length() > 0) && (ed_50kv.length() > 0) && (ed_100kv.length() > 0))))) {
				Intent in = new Intent(getApplicationContext(), Act_penjualan_paket.class);

				in .putExtra("e_v10ku", ed_10kv.getText().toString()); in .putExtra("e_v25ku", ed_25kv.getText().toString()); in .putExtra("e_v50ku", ed_50kv.getText().toString()); in .putExtra("e_v100ku", ed_100kv.getText().toString());

				in .putExtra("e_5ku", e_5kv); in .putExtra("e_10ku", e_10kv); in .putExtra("e_20ku", e_20kv); in .putExtra("e_25ku", e_25kv); in .putExtra("e_50ku", e_50kv); in .putExtra("e_100ku", e_100kv);

				in .putExtra("k_simpatiu", k_simpativ); in .putExtra("k_asu", k_asv); in .putExtra("k_loopu", k_loopv);
				in .putExtra("k_circle40ku", k_circle40kv); in .putExtra("k_circle70ku", k_circle70kv);in .putExtra("k_memberu", k_memberv);
				in .putExtra("k_maxloopu", k_maxloopv);
				in .putExtra("k_pr_suploopu", k_pr_suploopv);
				in .putExtra("k_pr_loopholu", k_pr_loopholv);
				in .putExtra("k_pr_pdk2gbu", k_pr_pdk2gbv);
				in .putExtra("k_pr_pdk4gbu", k_pr_pdk4gbv);


				in .putExtra("k_nspu", k_nspv); in .putExtra("k_otheru", k_otherv);
				in .putExtra("k_cugu", k_cugv); in .putExtra("longu", longv); in .putExtra("latu", latv);in .putExtra("tgl_kerjau", tglv);
				in .putExtra("id_tugasu", id_tugasv);
                /* Toast.makeText(Act_penjualan_kartu.this, "Simpati: "+e_simp.getText().toString()+
                 		" As: "+e_as.getText().toString()+" Loop: "+e_loop.getText().toString()+
                 		" Long: "+longz+" Lat: "+latz+" Tgl Kerja: "+tglz+" Id Tugas: "+idz
                 		, Toast.LENGTH_LONG).show();
                 		*/

				startActivity( in );
				//Toast.makeText(Act_penjualan_voucher.this,"Isi tanggal: "+tglv,Toast.LENGTH_LONG).show();
			} else {
				//Toast.makeText(Act_penjualan_kartu.this, "Mohon dilengkapi field yang disediakan", Toast.LENGTH_LONG).show();
				try {
					AlertDialog dyam_dialog = new AlertDialog.Builder(Act_penjualan_voucher.this).create();
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
					Toast.makeText(Act_penjualan_voucher.this, "Ini errornya: " + e, Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	public void onBackPressed() {
		x4.set_voucher_10k(ed_10kv.getText().toString().trim());
		x4.set_voucher_25k(ed_25kv.getText().toString().trim());
		x4.set_voucher_50k(ed_50kv.getText().toString().trim());
		x4.set_voucher_100k(ed_100kv.getText().toString().trim());
		super.onBackPressed();
		//   Toast.makeText(Act_penjualan_kartu.this, "ini listing kembali", Toast.LENGTH_LONG).show();
	}
}