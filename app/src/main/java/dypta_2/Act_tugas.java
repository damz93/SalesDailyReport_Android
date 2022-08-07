package dypta_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.dypta_2.R;

public class Act_tugas extends Activity {
	ListView lv;
	Button bt_ref;
	ProgressDialog pDialog;
	TextView j_tug;
	JSONArray contacts = null;
	Act_set_get a = new Act_set_get();
	String kategori_loks;

	String id_tugas, id_lokasi, nama_lokasi, jns_kegiatan, j_tugas, cluster, longg, latt, surveyy, updatee = "", stt_juall, tgt;

	@
			Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_list);
		lv = (ListView) findViewById(R.id.list);
		bt_ref = (Button) findViewById(R.id.bt_ref);
		bt_ref.setOnClickListener(new View.OnClickListener() {@
				Override
		public void onClick(View v) {
			new AmbilData().execute();
		}
		});

		new AmbilData().execute();
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@
					Override
			public void onItemClick(AdapterView <? > parent, View view, int position, long id) {
			//	Intent in = new Intent(getApplicationContext(), Act_pilih_tugas.class);
				Intent in = new Intent(getApplicationContext(), Act_pilih_tugas.class);
				id_tugas = ((TextView) view.findViewById(R.id.id_tugas)).getText().toString();
				id_lokasi = ((TextView) view.findViewById(R.id.id_lokasiz)).getText().toString();
				nama_lokasi = ((TextView) view.findViewById(R.id.nama_lokasi)).getText().toString();
				jns_kegiatan = ((TextView) view.findViewById(R.id.jenis_kegiatan)).getText().toString();
				cluster = ((TextView) view.findViewById(R.id.tx_cluster)).getText().toString();
				longg = ((TextView) view.findViewById(R.id.tx_long)).getText().toString();
				latt = ((TextView) view.findViewById(R.id.tx_lat)).getText().toString();
				surveyy = ((TextView) view.findViewById(R.id.tx_srv)).getText().toString();
				updatee = ((TextView) view.findViewById(R.id.tx_upd)).getText().toString();
				stt_juall = ((TextView) view.findViewById(R.id.tx_status)).getText().toString();
				in .putExtra("id_tugas", id_tugas);
				in .putExtra("id_lokasi", id_lokasi);
				in .putExtra("nama_lokasi", nama_lokasi);
				in .putExtra("jenis_kegiatan", jns_kegiatan);
				in .putExtra("cluster", cluster);
				in .putExtra("long", longg);
				in .putExtra("lat", latt);
				in .putExtra("survey", surveyy);
				in .putExtra("apdate", updatee);
				in .putExtra("status", stt_juall);
				finish();
				startActivity( in );
			}
		});
	}

	public class AmbilData extends AsyncTask < String, String, String > {
		ArrayList < HashMap < String, String >> contactList = new ArrayList < HashMap < String, String >> ();@
				Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Act_tugas.this);
			pDialog.setMessage("Loading Data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@
				Override
		protected String doInBackground(String...arg0) {
			String url = "http://own-youth.com/dd_json/tugas.php";
			j_tug = (TextView) findViewById(R.id.txt_jumlah);
			JSONParser jParser = new JSONParser();
			int i;
			String aa = a.getusnme();
			JSONObject json = jParser.ambilURL(url + "?ID=" + aa);
			//JSONObject json = jParser.ambilURL(url);
			try {
				contacts = json.getJSONArray("tugas");
				for (i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);
					HashMap < String, String > map = new HashMap < String, String > ();
					String id = c.getString("ID").trim();
					String nm_lokasi = c.getString("NAMA_LOKASI").trim();
					String kategori_kegiatan = c.getString("KATEGORI_LOKASI").trim();
					String id_lokasix = c.getString("ID_LOKASI").trim();
					String cluster = c.getString("CLUSTER").trim();
					String longd = c.getString("LONGG").trim();
					String latd = c.getString("LAT").trim();
					String sttus = c.getString("STATUS").trim();
					String trget = c.getString("T_PERD_LOOP").trim();
					String updated = c.getString("UPDATEE").trim();
					String surveyy = c.getString("SURVEY").trim();
					//	a.setstatus_survey(surveyy);
					//	a.setstatus_updatee(updated);
					a.set_pesan_jual("Tugas Activity telah dilakukan, di lokasi\n*");
					a.set_pesan_survey("Tidak ada perintah survey di lokasi\n*");
					a.set_pesan_update("Tidak ada perintah update di lokasi\n*");
					map.put("ID", id);
					map.put("ID_LOKASI", id_lokasix);
					map.put("NAMA_LOKASI", nm_lokasi);
					map.put("KATEGORI_LOKASI", kategori_kegiatan);
					map.put("CLUSTER", cluster);
					map.put("LONG", longd);
					map.put("LAT", latd);
					map.put("STATUS", sttus);
					map.put("UPDATEE", updated);
					map.put("SURVEY", surveyy);
					contactList.add(map);
					tgt = trget;
				}
				if (tgt == null) {
					tgt = "0";
				}
				j_tugas = "Hallo, " + a.getnama() + ".\nTarget Kunjungan hari ini sebanyak: " + Integer.toString(i) + "\nTarget Penjualan LOOP " +
						"hari ini sebanyak: " + tgt;

			} catch (JSONException e) {

			}

			return null;
		}@
				Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			pDialog.dismiss();
			ListAdapter adapter2 = new SimpleAdapter(getApplicationContext(),
					contactList, R.layout.lay_tugas, new String[] {
					"ID", "ID_LOKASI", "NAMA_LOKASI", "KATEGORI_LOKASI", "CLUSTER", "LONG", "LAT", "UPDATEE", "SURVEY", "STATUS"
			},
					new int[] {
							R.id.id_tugas, R.id.id_lokasiz, R.id.nama_lokasi, R.id.jenis_kegiatan, R.id.tx_cluster, R.id.tx_long, R.id.tx_lat, R.id.tx_upd, R.id.tx_srv, R.id.tx_status
					});
			lv.setAdapter(adapter2);
			j_tug.setText((CharSequence) j_tugas);
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