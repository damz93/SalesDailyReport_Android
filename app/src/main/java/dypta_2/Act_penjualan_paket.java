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

public class Act_penjualan_paket extends Activity implements View.OnClickListener {
	Button b_send;
	String longu, latu, tglu, k_simpatiu, k_asu, k_loopu, k_circle40ku, k_circle70ku, k_memberu, k_cugu, k_maxloopu, k_nspu, k_otheru,
			id_tugasu, e_5ku, e_10ku, e_20ku, e_25ku, e_50ku, e_100ku, e_v10ku, e_v25ku, e_v50ku, e_v100ku;
	String s_p5kv, s_p10kv, s_p20kv, s_p25kv, s_p50kv, s_p100kv;
	String k_pr_suploopu, k_pr_loopholu, k_pr_pdk2gbu,k_pr_pdk4gbu;
	EditText ed_p5kv, ed_p10kv, ed_p20kv, ed_p25kv, ed_p50kv, ed_p100kv;
	Act_set_get x5 = new Act_set_get();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_penjualan_paket);

		Bundle b = getIntent().getExtras();
		//dari form pilih kegiatan
		longu = b.getString("longu");
		latu = b.getString("latu");
		tglu = b.getString("tgl_kerjau");
		id_tugasu = b.getString("id_tugasu");

		//dari form penjualan kartu
		k_simpatiu = b.getString("k_simpatiu");
		k_asu = b.getString("k_asu");
		k_loopu = b.getString("k_loopu");
		k_circle40ku = b.getString("k_circle40ku");
		k_circle70ku = b.getString("k_circle70ku");
		k_memberu = b.getString("k_memberu");
		k_cugu = b.getString("k_cugu");
		k_maxloopu = b.getString("k_maxloopu");
		k_nspu = b.getString("k_nspu");
		k_otheru = b.getString("k_otheru");

		k_pr_suploopu = b.getString("k_pr_suploopu");
		k_pr_loopholu = b.getString("k_pr_loopholu");
		k_pr_pdk2gbu = b.getString("k_pr_pdk2gbu");
		k_pr_pdk4gbu = b.getString("k_pr_pdk4gbu");

		//dari form penjualan mkios
		e_5ku = b.getString("e_5ku");
		e_10ku = b.getString("e_10ku");
		e_20ku = b.getString("e_20ku");
		e_25ku = b.getString("e_25ku");
		e_50ku = b.getString("e_50ku");
		e_100ku = b.getString("e_100ku");

		//dari form penjualan voucher
		e_v10ku = b.getString("e_v10ku");
		e_v25ku = b.getString("e_v25ku");
		e_v50ku = b.getString("e_v50ku");
		e_v100ku = b.getString("e_v100ku");

		//dari tampilan penjualan paket
		ed_p5kv = (EditText) findViewById(R.id.ed_p5k);
		ed_p10kv = (EditText) findViewById(R.id.ed_p10k);
		ed_p20kv = (EditText) findViewById(R.id.ed_p20k);
		ed_p25kv = (EditText) findViewById(R.id.ed_p25k);
		ed_p50kv = (EditText) findViewById(R.id.ed_p50k);
		ed_p100kv = (EditText) findViewById(R.id.ed_p100k);
		b_send = (Button) findViewById(R.id.bt_send);
		b_send.setOnClickListener(this);

		ed_p5kv.setText(x5.get_pk_5k());
		ed_p10kv.setText(x5.get_pk_10k());
		ed_p20kv.setText(x5.get_pk_20k());
		ed_p25kv.setText(x5.get_pk_25k());
		ed_p50kv.setText(x5.get_pk_50k());
		ed_p100kv.setText(x5.get_pk_100k());
	}

	@
			Override
	public void onClick(View v) {
		s_p5kv = ed_p5kv.getText().toString();
		s_p10kv = ed_p10kv.getText().toString();
		s_p20kv = ed_p20kv.getText().toString();
		s_p25kv = ed_p25kv.getText().toString();
		s_p50kv = ed_p50kv.getText().toString();
		s_p100kv = ed_p100kv.getText().toString();
		if (v == b_send) {
			if (((((s_p5kv.length() > 0) && (s_p10kv.length() > 0) && (s_p20kv.length() > 0) && (s_p25kv.length() > 0) && (s_p50kv.length() > 0) && (ed_p100kv.length() > 0))))) {
				Intent in = new Intent(getApplicationContext(), Act_trade_in.class);

				in .putExtra("s_p5kt", s_p5kv); in .putExtra("s_p10kt", s_p10kv); in .putExtra("s_p20kt", s_p20kv); in .putExtra("s_p25kt", s_p25kv); in .putExtra("s_p50kt", s_p50kv); in .putExtra("s_p100kt", s_p100kv);

				in .putExtra("e_v10kt", e_v10ku); in .putExtra("e_v25kt", e_v25ku); in .putExtra("e_v50kt", e_v50ku); in .putExtra("e_v100kt", e_v100ku);

				in .putExtra("e_5kt", e_5ku); in .putExtra("e_10kt", e_10ku); in .putExtra("e_20kt", e_20ku); in .putExtra("e_25kt", e_25ku); in .putExtra("e_50kt", e_50ku); in .putExtra("e_100kt", e_100ku);

				in .putExtra("k_simpatit", k_simpatiu); in .putExtra("k_ast", k_asu); in .putExtra("k_loopt", k_loopu); in .putExtra("k_circle40kt", k_circle40ku); in .putExtra("k_circle70kt", k_circle70ku); in .putExtra("k_membert", k_memberu);
				in .putExtra("k_maxloopt", k_maxloopu);
				in .putExtra("k_pr_suploopt", k_pr_suploopu);
				in .putExtra("k_pr_loopholt", k_pr_loopholu);
				in .putExtra("k_pr_pdk2gbt", k_pr_pdk2gbu);
				in .putExtra("k_pr_pdk4gbt", k_pr_pdk4gbu);

				in .putExtra("k_nspt", k_nspu); in .putExtra("k_othert", k_otheru); in .putExtra("k_cugt", k_cugu); in .putExtra("longt", longu); in .putExtra("latt", latu); in .putExtra("tglt", tglu); in .putExtra("id_tugast", id_tugasu);
				startActivity( in );
			} else {
				try {
					AlertDialog dyam_dialog = new AlertDialog.Builder(Act_penjualan_paket.this).create();
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
					Toast.makeText(Act_penjualan_paket.this, "Ini errornya: " + e, Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	public void onBackPressed() {
		x5.set_pk_5k(ed_p5kv.getText().toString().trim());
		x5.set_pk_10k(ed_p10kv.getText().toString().trim());
		x5.set_pk_20k(ed_p20kv.getText().toString().trim());
		x5.set_pk_25k(ed_p25kv.getText().toString().trim());
		x5.set_pk_50k(ed_p50kv.getText().toString().trim());
		x5.set_pk_100k(ed_p100kv.getText().toString().trim());
		super.onBackPressed();
		//   Toast.makeText(Act_penjualan_kartu.this, "ini listing kembali", Toast.LENGTH_LONG).show();
	}
}