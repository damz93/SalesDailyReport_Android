package dypta_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import app.dypta_2.R;

public class Act_update_lokasi extends Activity implements AdapterView.OnItemSelectedListener {
    private static String dyam_url_upd_tgs = "http://own-youth.com/dd_json/upd_lokasi.php";
    private static String dyam_url_simp_upd_tgs = "http://own-youth.com/dd_json/simp_upd_lokasi.php";
    public static String UPLOAD_URL = "http://own-youth.com/dd_json/upl_gmbr_lokasi.php";
    public static String UPLOAD_URL_produk = "http://own-youth.com/dd_json/upl_produk_lokasi.php";
    public static String UPLOAD_URL_promo = "http://own-youth.com/dd_json/upl_promo_lokasi.php";
    public static String UPLOAD_URL_price = "http://own-youth.com/dd_json/upl_price_lokasi.php";
    public static String UPLOAD_URL_place = "http://own-youth.com/dd_json/upl_place_lokasi.php";
    public static String UPLOAD_URL_people = "http://own-youth.com/dd_json/upl_people_lokasi.php";
    public static String UPLOAD_URL_program = "http://own-youth.com/dd_json/upl_program_lokasi.php";

    public static final int MEDIA_TYPE_IMAGE = 4;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static String IMAGE_DIRECTORY_NAME = "gambar";
    public static final String UPLOAD_KEY = "image";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_IMAGE_REQUEST = 3;
    private static final String TAG = Act_update_lokasi.class.getSimpleName();
    String apa_produk, apa_lokasi, apa_promo, apa_price, apa_place, apa_people, apa_program;
    Bitmap rotateBMP, rotateBMP_produk, rotateBMP_promo, rotateBMP_price, rotateBMP_place, rotateBMP_people, rotateBMP_program;
    Bitmap gambarzz;
    Act_set_get xd = new Act_set_get();
    Button btn_updlok, bt_mulai_ed;
    ImageView img_cam1, img_cam2, img_produk1, img_produk2, img_promo1, img_promo2, img_people1, img_people2, img_place1, img_place2,
            img_program1, img_program2, img_price1, img_price2;
    String cek1, aa1 = "0";
    String item_lokasi[] = {
            "Perkotaan",
            "Pedesaan",
            "Daerah Terpencil",
            "Daerah Tertinggal/Sulit",
            "Daerah Perbatasan"
    };
    String item_jenjang[] = {
            "TAMAN KANAK-KANAK",
            "SD/MI",
            "SLTP/MTS",
            "SLTA/SMK/MA/MAK",
            "PERGURUAN TINGGI",
            "PENDIDIKAN KEAGAMAAN"
    };
    String item_level[] = {
            "Negeri",
            "Swasta"
    };
    String item_kategori[] = {
            "Favorit",
            "Non Favorit"
    };
    String item_poi[] = {
            "POI",
            "NON POI"
    };
    String item_value[] = {
            "Circle",
            "PDK",
            "PDK dan Circle",
            "Non Value"
    };
    ProgressDialog pDialog;
    String pilih1, pilih2, pilih3, pilih4, pilih5, pilih6;
    String id_tgas, id_lks, nm_loks, longg, latt, stt_updsek, jn_kgn;
    String selectedImagePath;
    Spinner spn_lokasi, spn_jenjang, spn_level, spn_kategori, spn_poii, spn_valuee;
    JSONArray contacts = null;
    TextView nm_lk;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    GPSTracker gps;
    EditText npsn1, nm_skl1, almt1, kcmt1, notel1, webs1, jml_gr1, jml_ssw1, jmldt_amb1, nmdt_amb11, nmdt_amb12, nm_kepsek1, no_keps1,
            nm_wksek1, no_wksek1, nmpic_telk1, no_telpic1, nm_picad1, no_telpicad1, in2g_1, sor2g_1, in3g_1, sor3g_1,
            e_produk_telk, e_produk_xl, e_produk_indosat, e_produk_three, e_produk_other, e_priceeup, e_pricetp, e_placement,
            e_jutrs_mkios, e_jutrs_all, e_people, e_progrm, e_promo;
    String sp1, sp2, sp3, sp4, sp5, sp6, snpsn1, snm_skl1, salmt1, skcmt1, snotel1, swebs1, sjml_gr1, sjml_ssw1, sjmldt_amb1, snmdt_amb11, snmdt_amb12, snm_kepsek1, sno_keps1,
            snm_wksek1, sno_wksek1, snmpic_telk1, sno_telpic1, snm_picad1, sno_telpicad1, sin2g_1, ssor2g_1, sin3g_1, ssor3g_1,
            produk_telk, produk_indst, produk_thre, produk_xl, produk_othr, priceeup_1, pricetp_1, placement_1, jtr_mkios1, jtr_all1, people_1, program_1, promo_1;
    String npsn93, nm_skl93, almt93, kcmt93, notel93, webs93, jml_gr93, jml_ssw93, jmldt_amb93, nmdt_amb193, nmdt_amb293, nm_kepsek93, no_keps93,
            nm_wksek93, no_wksek93, nmpic_telk93, no_telpic93, nm_picad93, no_telpicad93, in2g_93, sor2g_93, in3g_93, sor3g_93,
            produk_telk93, produk_idst93, produk_xl93, produk_three93, produk_lain93,
            priceeup93, pricetp93, place93, jtr_mkios93, jtr_all93, people93, program93, promo93;
    String s_bt;

    @
            Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.lay_update_lokasi);

        nm_lk = (TextView) findViewById(R.id.tx_lokasiu);
        npsn1 = (EditText) findViewById(R.id.ed_npsnu);
        nm_skl1 = (EditText) findViewById(R.id.ed_nmlksu);
        almt1 = (EditText) findViewById(R.id.ed_alamu);
        kcmt1 = (EditText) findViewById(R.id.ed_kecamu);
        notel1 = (EditText) findViewById(R.id.ed_notelu);
        webs1 = (EditText) findViewById(R.id.ed_alwebu);
        jml_gr1 = (EditText) findViewById(R.id.ed_jumgukayu);
        jml_ssw1 = (EditText) findViewById(R.id.ed_jumsiswu);
        jmldt_amb1 = (EditText) findViewById(R.id.ed_dutambu);
        nmdt_amb11 = (EditText) findViewById(R.id.ed_nmdutambu1);
        nmdt_amb12 = (EditText) findViewById(R.id.ed_nmdutambu2);
        nm_kepsek1 = (EditText) findViewById(R.id.ed_nmkepseku);
        no_keps1 = (EditText) findViewById(R.id.ed_nokepseku);
        nm_wksek1 = (EditText) findViewById(R.id.ed_nmwakaseku);
        no_wksek1 = (EditText) findViewById(R.id.ed_nowakepseku);
        nmpic_telk1 = (EditText) findViewById(R.id.ed_nmpicu);
        no_telpic1 = (EditText) findViewById(R.id.ed_notepicu);
        nm_picad1 = (EditText) findViewById(R.id.ed_nmpiadu);
        no_telpicad1 = (EditText) findViewById(R.id.ed_notepicdu);
        in2g_1 = (EditText) findViewById(R.id.ed_jumin2gu);
        sor2g_1 = (EditText) findViewById(R.id.ed_jumsr2gu);
        in3g_1 = (EditText) findViewById(R.id.ed_jumin3gu);
        sor3g_1 = (EditText) findViewById(R.id.ed_jumsr3gu);

        e_produk_telk = (EditText) findViewById(R.id.ed_produk_telk);
        e_produk_xl = (EditText) findViewById(R.id.ed_produk_xl);
        e_produk_indosat = (EditText) findViewById(R.id.ed_produk_indosat);
        e_produk_three = (EditText) findViewById(R.id.ed_produk_three);
        e_produk_other = (EditText) findViewById(R.id.ed_produk_other);

        e_people = (EditText) findViewById(R.id.ed_people);
        e_placement = (EditText) findViewById(R.id.ed_place);
        e_jutrs_mkios = (EditText) findViewById(R.id.ed_jtr_mkios);
        e_jutrs_all = (EditText) findViewById(R.id.ed_jtr_all);

        e_priceeup = (EditText) findViewById(R.id.ed_price_eup);
        e_pricetp = (EditText) findViewById(R.id.ed_price_tp);
        e_progrm = (EditText) findViewById(R.id.ed_program);
        e_promo = (EditText) findViewById(R.id.ed_promo);

        Bundle b = getIntent().getExtras();
        id_tgas = b.getString("id_tugas");
        nm_loks = b.getString("nama_lokasi");
        id_lks = b.getString("id_lokasi");
        jn_kgn = b.getString("jns_kgt");
        nm_lk.setText(nm_loks);

        spn_lokasi = (Spinner) findViewById(R.id.spn_loksu);
        spn_jenjang = (Spinner) findViewById(R.id.spn_jenju);
        spn_level = (Spinner) findViewById(R.id.spn_levelu);
        spn_kategori = (Spinner) findViewById(R.id.spn_kategoru);
        spn_poii = (Spinner) findViewById(R.id.spn_poi);
        spn_valuee = (Spinner) findViewById(R.id.spn_value);
        ArrayAdapter < String > adapter_lokasi = new ArrayAdapter < String > (this, android.R.layout.simple_spinner_dropdown_item, item_lokasi);
        adapter_lokasi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter < String > adapter_jenjang = new ArrayAdapter < String > (this, android.R.layout.simple_spinner_dropdown_item, item_jenjang);
        adapter_jenjang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter < String > adapter_level = new ArrayAdapter < String > (this, android.R.layout.simple_spinner_dropdown_item, item_level);
        adapter_level.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter < String > adapter_kategori = new ArrayAdapter < String > (this, android.R.layout.simple_spinner_dropdown_item, item_kategori);
        adapter_kategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter < String > adapter_ktg_poi = new ArrayAdapter < String > (this, android.R.layout.simple_spinner_dropdown_item, item_poi);
        adapter_kategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter < String > adapter_value = new ArrayAdapter < String > (this, android.R.layout.simple_spinner_dropdown_item, item_value);
        adapter_kategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn_lokasi.setAdapter(adapter_lokasi);
        spn_jenjang.setAdapter(adapter_jenjang);
        spn_level.setAdapter(adapter_level);
        spn_kategori.setAdapter(adapter_kategori);
        spn_poii.setAdapter(adapter_ktg_poi);
        spn_valuee.setAdapter(adapter_value);

        spn_lokasi.setOnItemSelectedListener(this);
        spn_jenjang.setOnItemSelectedListener(this);
        spn_level.setOnItemSelectedListener(this);
        spn_kategori.setOnItemSelectedListener(this);
        spn_valuee.setOnItemSelectedListener(this);
        spn_poii.setOnItemSelectedListener(this);

        new AmbilData().execute();
        img_cam1 = (ImageView) findViewById(R.id.im_camup);
        img_cam2 = (ImageView) findViewById(R.id.im_camup2);
        img_cam1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cek1 = "camera";
                apa_lokasi = "ya";
                dispatchTakePictureIntent();
            }
        });

        img_produk1 = (ImageView) findViewById(R.id.im_produk1);
        img_produk2 = (ImageView) findViewById(R.id.im_produk2);
        img_produk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cek1 = "camera";
                apa_produk = "ya";
                dispatchTakePictureIntent();
            }
        });

        img_promo1 = (ImageView) findViewById(R.id.im_promo1);
        img_promo2 = (ImageView) findViewById(R.id.im_promo2);
        img_promo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cek1 = "camera";
                apa_promo = "ya";
                dispatchTakePictureIntent();
            }
        });

        img_price1 = (ImageView) findViewById(R.id.im_price1);
        img_price2 = (ImageView) findViewById(R.id.im_price2);
        img_price1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cek1 = "camera";
                apa_price = "ya";
                dispatchTakePictureIntent();
            }
        });
        img_place1 = (ImageView) findViewById(R.id.im_place1);
        img_place2 = (ImageView) findViewById(R.id.im_place2);
        img_place1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cek1 = "camera";
                apa_place = "ya";
                dispatchTakePictureIntent();
            }
        });

        img_people1 = (ImageView) findViewById(R.id.im_people1);
        img_people2 = (ImageView) findViewById(R.id.im_people2);
        img_people1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cek1 = "camera";
                apa_people = "ya";
                dispatchTakePictureIntent();
            }
        });

        img_program1 = (ImageView) findViewById(R.id.im_program1);
        img_program2 = (ImageView) findViewById(R.id.im_program2);
        img_program1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cek1 = "camera";
                apa_program = "ya";
                dispatchTakePictureIntent();
            }
        });
        disable_edt();
        bt_mulai_ed = (Button) findViewById(R.id.bt_mulai_edit);
        //s_bt = bt_mulai_ed.getText().toString();
        bt_mulai_ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bt_mulai_ed.getText().toString().equals("Mulai Edit")) {
                    enable_edt();
                    bt_mulai_ed.setText("Selesai Edit");
                } else {
                    disable_edt();
                    bt_mulai_ed.setText("Mulai Edit");
                }
                //Toast.makeText(Act_update_lokasi.this,"Tes",Toast.LENGTH_LONG).show();
            }
        });
        btn_updlok = (Button) findViewById(R.id.btn_simp_updz);
        btn_updlok.setOnClickListener(new View.OnClickListener() {
            @
                    Override
            public void onClick(View v) {
                gps = new GPSTracker(Act_update_lokasi.this);
                snpsn1 = npsn1.getText().toString();
                snm_skl1 = nm_skl1.getText().toString();
                salmt1 = almt1.getText().toString();
                skcmt1 = kcmt1.getText().toString();
                snotel1 = notel1.getText().toString();
                swebs1 = webs1.getText().toString();
                sjml_gr1 = jml_gr1.getText().toString();
                sjml_ssw1 = jml_ssw1.getText().toString();
                sjmldt_amb1 = jmldt_amb1.getText().toString();
                snmdt_amb11 = nmdt_amb11.getText().toString();
                snmdt_amb12 = nmdt_amb12.getText().toString();
                snm_kepsek1 = nm_kepsek1.getText().toString();
                sno_keps1 = no_keps1.getText().toString();
                snm_wksek1 = nm_wksek1.getText().toString();
                sno_wksek1 = no_wksek1.getText().toString();
                snmpic_telk1 = nmpic_telk1.getText().toString();
                sno_telpic1 = no_telpic1.getText().toString();
                snm_picad1 = nm_picad1.getText().toString();
                sno_telpicad1 = no_telpicad1.getText().toString();
                sin2g_1 = in2g_1.getText().toString();
                ssor2g_1 = sor2g_1.getText().toString();
                sin3g_1 = in3g_1.getText().toString();
                ssor3g_1 = sor3g_1.getText().toString();

                produk_telk = e_produk_telk.getText().toString();
                produk_indst = e_produk_indosat.getText().toString();
                produk_thre = e_produk_three.getText().toString();
                produk_xl = e_produk_xl.getText().toString();
                produk_othr = e_produk_other.getText().toString();

                people_1 = e_people.getText().toString();
                promo_1 = e_people.getText().toString();
                placement_1 = e_placement.getText().toString();
                jtr_mkios1 = e_jutrs_mkios.getText().toString();
                jtr_all1 = e_jutrs_all.getText().toString();

                program_1 = e_progrm.getText().toString();
                priceeup_1 = e_priceeup.getText().toString();
                pricetp_1 = e_pricetp.getText().toString();


                sp1 = pilih1;
                sp2 = pilih2;
                sp3 = pilih3;
                sp4 = pilih4;
                sp5 = pilih5;
                sp6 = pilih6;


                if (gps.canGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    latt = (String.valueOf(latitude));
                    longg = (String.valueOf(longitude));

                    if ((latitude == 0.0) || (longitude == 0.0)) {
                        Toast.makeText(getApplicationContext(), "Lokasi sementera direload, mohon dicoba lagi!!!", Toast.LENGTH_LONG).show();
                    } else {
                        cd = new ConnectionDetector(getApplicationContext());
                        isInternetPresent = cd.isConnectingToInternet();
                        try {
                            if (isInternetPresent) {
                                stt_updsek = "1";
                                DialogInterface.OnClickListener dd_dialog = new DialogInterface.OnClickListener() {
                                    @
                                            Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:

                                                break;
                                            case DialogInterface.BUTTON_NEGATIVE:
                                                if ((null != img_cam2.getDrawable()) && (null != img_produk2.getDrawable()) && (null != img_promo2.getDrawable()) && (null != img_people2.getDrawable()) && (null != img_place2.getDrawable()) && (null != img_program2.getDrawable()) && (null != img_price2.getDrawable())) {
                                                    try {
                                                        if (cek1.equals("galery")) {
                                                            sendphoto(rotateBMP);
                                                            sendphoto_produk(rotateBMP_produk);
                                                            sendphoto_promo(rotateBMP_promo);
                                                            sendphoto_price(rotateBMP_price);
                                                            sendphoto_place(rotateBMP_place);
                                                            sendphoto_people(rotateBMP_people);
                                                            sendphoto_program(rotateBMP_program);
                                                        } else {
                                                            sendphoto(rotateBMP);
                                                            sendphoto_produk(rotateBMP_produk);
                                                            sendphoto_promo(rotateBMP_promo);
                                                            sendphoto_price(rotateBMP_price);
                                                            sendphoto_place(rotateBMP_place);
                                                            sendphoto_people(rotateBMP_people);
                                                            sendphoto_program(rotateBMP_program);

                                                        }
                                                        //tambahkan ini ded
                                                        //produk_1, people_1, promo_1, placement_1, program_1,    priceeup_1, pricetp_1
                                                        simp_db(id_tgas, id_lks, sp1, sp2, sp3, sp4, sp5, sp6, snpsn1, snm_skl1, salmt1, skcmt1, snotel1, swebs1, sjml_gr1, sjml_ssw1, sjmldt_amb1, snmdt_amb11, snmdt_amb12, snm_kepsek1, sno_keps1,
                                                                snm_wksek1, sno_wksek1, snmpic_telk1, sno_telpic1, snm_picad1, sno_telpicad1, sin2g_1,
                                                                ssor2g_1, sin3g_1, ssor3g_1, latt, longg, produk_telk, people_1, promo_1, placement_1,
                                                                program_1, priceeup_1, pricetp_1, produk_indst, produk_xl, produk_thre, produk_othr,
                                                                jtr_mkios1, jtr_all1);
                                                        upd_db(id_tgas, stt_updsek);
                                                    } catch (Exception e) {
                                                        Toast.makeText(Act_update_lokasi.this, "Errornya" + e.toString().trim(), Toast.LENGTH_LONG).show();
                                                    }
                                                } else {
                                                    AlertDialog dyam_dialog = new AlertDialog.Builder(Act_update_lokasi.this).create();
                                                    dyam_dialog.setTitle("Peringatan");
                                                    dyam_dialog.setIcon(R.drawable.warning);
                                                    dyam_dialog.setMessage("Ambil Gambar setiap item terlebih dahulu");
                                                    dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });
                                                    dyam_dialog.show();
                                                }
                                                break;

                                        }
                                    }
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(Act_update_lokasi.this);
                                builder.setMessage("Yakin ingin menyimpan?\n*Posisi anda sekarang akan menjadi acuan letak lokasi " + nm_loks + "*").setPositiveButton("Tidak", dd_dialog).setNegativeButton("Ya", dd_dialog).show();
                            } else {
                                pesanUnkn();
                            }
                        } catch (Exception e) {
                            Toast.makeText(Act_update_lokasi.this, "Error: " + e, Toast.LENGTH_LONG).show();
                        }
                    }

                    //Toast.makeText(Act_update_lokasi.this,"Isi pilih 1: "+pilih1+"\nIsi Pilih 2: "+pilih2+"\nIsi Pilih 3: "+pilih3+"\nIsi Pilih 4: "+pilih4,Toast.LENGTH_LONG).show();
                } else {
                    gps.showSettingsAlert();
                }
            }
        });
    }

    @
            Override
    public void onItemSelected(AdapterView < ? > parent, View view, int position, long id) {
        pilih1 = spn_lokasi.getSelectedItem().toString();
        pilih2 = spn_jenjang.getSelectedItem().toString();
        pilih3 = spn_level.getSelectedItem().toString();
        pilih4 = spn_kategori.getSelectedItem().toString();
        pilih5 = spn_poii.getSelectedItem().toString();
        pilih6 = spn_valuee.getSelectedItem().toString();

    }

    @
            Override
    public void onNothingSelected(AdapterView < ? > parent) {

    }

    public class AmbilData extends AsyncTask < String, String, String > {
        //  ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        @
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Act_update_lokasi.this);
            pDialog.setMessage("Loading Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String...params) {
            String url = "http://own-youth.com/dd_json/lokasi2.php";
            JSONParser jParser = new JSONParser();
            int i;
            JSONObject json = jParser.ambilURL(url + "?npsn=" + id_lks);
            try {
                contacts = json.getJSONArray("lokasi");
                for (i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    //   HashMap<String, String> map = new HashMap<String, String>();
                    npsn93 = c.getString("npsn").trim();
                    nm_skl93 = c.getString("nama_lokasi").trim();
                    almt93 = c.getString("alamat").trim();
                    kcmt93 = c.getString("kec_name").trim();
                    notel93 = c.getString("telepon").trim();
                    webs93 = c.getString("alm_website").trim();
                    jml_gr93 = c.getString("jml_karyawan").trim();
                    jml_ssw93 = c.getString("jml_siswa").trim();
                    jmldt_amb93 = c.getString("jml_duta").trim();
                    nmdt_amb193 = c.getString("nm_duta1").trim();
                    nmdt_amb293 = c.getString("nm_duta2").trim();
                    nm_kepsek93 = c.getString("nm_kepala").trim();
                    no_keps93 = c.getString("tlp_kepala").trim();
                    nm_wksek93 = c.getString("nm_waka").trim();
                    no_wksek93 = c.getString("tlp_waka").trim();
                    nmpic_telk93 = c.getString("nm_pic_telk").trim();
                    no_telpic93 = c.getString("tlp_pic_telk").trim();
                    nm_picad93 = c.getString("nm_pic_ad").trim();
                    no_telpicad93 = c.getString("no_pic_ad").trim();
                    in2g_93 = c.getString("inner_2g").trim();
                    sor2g_93 = c.getString("sorround_2g").trim();
                    in3g_93 = c.getString("inner_3g").trim();
                    sor3g_93 = c.getString("sorround_3g").trim();
                    produk_telk93 = c.getString("produk_telk").trim();

                    produk_idst93 = c.getString("produk_indst").trim();
                    produk_xl93 = c.getString("produk_xl").trim();
                    produk_three93 = c.getString("produk_three").trim();
                    produk_lain93 = c.getString("produk_other").trim();

                    priceeup93 = c.getString("price_eup").trim();
                    pricetp93 = c.getString("price_tp").trim();
                    place93 = c.getString("placement").trim();
                    jtr_mkios93 = c.getString("juml_trs_mkios").trim();
                    jtr_all93 = c.getString("juml_trs_all").trim();

                    people93 = c.getString("people").trim();
                    program93 = c.getString("program").trim();
                    promo93 = c.getString("promotion").trim();

                    //map.put("npsn2", npsn);
                    //    contactList.add(map);
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
            npsn1.setText(npsn93);
            nm_skl1.setText(nm_skl93);
            almt1.setText(almt93);
            kcmt1.setText(kcmt93);
            notel1.setText(notel93);
            webs1.setText(webs93);
            jml_gr1.setText(jml_gr93);
            jml_ssw1.setText(jml_ssw93);
            jmldt_amb1.setText(jmldt_amb93);
            nmdt_amb11.setText(nmdt_amb193);
            nmdt_amb12.setText(nmdt_amb293);
            nm_kepsek1.setText(nm_kepsek93);
            no_keps1.setText(no_keps93);
            nm_wksek1.setText(nm_wksek93);
            no_wksek1.setText(no_wksek93);
            nmpic_telk1.setText(nmpic_telk93);
            no_telpic1.setText(no_telpic93);
            nm_picad1.setText(nm_picad93);
            no_telpicad1.setText(no_telpicad93);
            in2g_1.setText(in2g_93);
            sor2g_1.setText(sor2g_93);
            in3g_1.setText(in3g_93);
            sor3g_1.setText(sor3g_93);
            e_produk_telk.setText(produk_telk93);
            e_produk_indosat.setText(produk_idst93);
            e_produk_xl.setText(produk_xl93);
            e_produk_three.setText(produk_three93);
            e_produk_other.setText(produk_lain93);
            e_priceeup.setText(priceeup93);
            e_pricetp.setText(pricetp93);
            e_placement.setText(place93);
            e_jutrs_mkios.setText(jtr_mkios93);
            e_jutrs_all.setText(jtr_all93);
            e_people.setText(people93);
            e_progrm.setText(program93);
            e_promo.setText(promo93);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {}
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    String mCurrentPhotoPath;
    File photoFile = null;

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String storageDir = Environment.getExternalStorageDirectory() + "/pic_lokasi";
        File dir = new File(storageDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File image = new File(storageDir + "/" + imageFileName + ".jpg");
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.i(TAG, "photo path = " + mCurrentPhotoPath);
        return image;
    }

    private File createImageFileproduk() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String storageDir = Environment.getExternalStorageDirectory() + "/pic_produk";
        File dir = new File(storageDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File image = new File(storageDir + "/" + imageFileName + ".jpg");
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.i(TAG, "photo path = " + mCurrentPhotoPath);
        return image;
    }

    private File createImageFilepromo() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String storageDir = Environment.getExternalStorageDirectory() + "/pic_promotion";
        File dir = new File(storageDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File image = new File(storageDir + "/" + imageFileName + ".jpg");
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.i(TAG, "photo path = " + mCurrentPhotoPath);
        return image;
    }

    private File createImageFileprice() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String storageDir = Environment.getExternalStorageDirectory() + "/pic_price";
        File dir = new File(storageDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File image = new File(storageDir + "/" + imageFileName + ".jpg");
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.i(TAG, "photo path = " + mCurrentPhotoPath);
        return image;
    }


    private void simp_db(String id_tgasz, String id_lksz, String sp1z, String sp2z, String sp3z, String sp4z, String sp5z, String sp6z,
                         String snpsn1z, String snm_skl1z, String salmt1z, String skcmt1z, String snotel1z, String swebs1z, String sjml_gr1z,
                         String sjml_ssw1z, String sjmldt_amb1z, String snmdt_amb11z, String snmdt_amb12z, String snm_kepsek1z,
                         String sno_keps1z, String snm_wksek1z, String sno_wksek1z, String snmpic_telk1z, String sno_telpic1z,
                         String snm_picad1z, String sno_telpicad1z, String sin2g_1z, String ssor2g_1z, String sin3g_1z, String ssor3g_1z,
                         String lattz, String longg, String produkz, String peoplez, String promoz, String placementz, String programz, String priceeupz,
                         String pricetpz, String produk_indstz, String produk_xlz, String produk_threz, String produk_othrz,
                         String jtr_mkiosz, String jtr_allz) {
  /*
      jtr_mkios1, jtr_all1
         */

        class SendPostReqAsyncTask extends AsyncTask < String, Void, String > {
            @
                    Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(Act_update_lokasi.this);
                pDialog.setMessage("Proses Penyimpanan");
                pDialog.setIndeterminate(false);
                pDialog.show();
            }
            @
                    Override
            protected String doInBackground(String...params) {
                String id_tgasx = params[0];
                String id_lksx = params[1];
                String sp1x = params[2];
                String sp2x = params[3];
                String sp3x = params[4];
                String sp4x = params[5];
                String sp5x = params[6];
                String sp6x = params[7];
                String snpsn1x = params[8];
                String snm_skl1x = params[9];
                String salmt1x = params[10];
                String skcmt1x = params[11];
                String snotel1x = params[12];
                String swebs1x = params[13];
                String sjml_gr1x = params[14];
                String sjml_ssw1x = params[15];
                String sjmldt_amb1x = params[16];
                String snmdt_amb11x = params[17];
                String snmdt_amb12x = params[18];
                String snm_kepsek1x = params[19];
                String sno_keps1x = params[20];
                String snm_wksek1x = params[21];
                String sno_wksek1x = params[22];
                String snmpic_telk1x = params[23];
                String sno_telpic1x = params[24];
                String snm_picad1x = params[25];
                String sno_telpicad1x = params[26];
                String sin2g_1x = params[27];
                String ssor2g_1x = params[28];
                String sin3g_1x = params[29];
                String ssor3g_1x = params[30];
                String lattx = params[31];
                String longgx = params[32];
                String produkx = params[33];
                String peoplex = params[34];
                String promox = params[35];
                String placementx = params[36];
                String programx = params[37];
                String priceeupx = params[38];
                String pricetpx = params[39];
                String produk_indstx = params[40];
                String produk_xlx = params[41];
                String produk_threx = params[42];
                String produk_othrx = params[43];
                String jtr_mkiosx = params[44];
                String jtr_allx = params[45];
                //jtr_mkios1, jtr_all1
                List < NameValuePair > nameValuePairs = new ArrayList < NameValuePair > ();
                Act_set_get stgg = new Act_set_get();
                nameValuePairs.add(new BasicNameValuePair("id", id_lksx));
                nameValuePairs.add(new BasicNameValuePair("nm_scc", stgg.getnama()));
                nameValuePairs.add(new BasicNameValuePair("npsn", id_lksx));
                nameValuePairs.add(new BasicNameValuePair("lokasi", sp1x));
                nameValuePairs.add(new BasicNameValuePair("jenjang", sp2x));
                nameValuePairs.add(new BasicNameValuePair("level", sp3x));
                nameValuePairs.add(new BasicNameValuePair("kategori", sp4x));
                nameValuePairs.add(new BasicNameValuePair("kategori_poi", sp5x));
                nameValuePairs.add(new BasicNameValuePair("value_produk", sp6x));

                nameValuePairs.add(new BasicNameValuePair("npsn", snpsn1x));
                nameValuePairs.add(new BasicNameValuePair("nama_lokasi", snm_skl1x));
                nameValuePairs.add(new BasicNameValuePair("alamat", salmt1x));
                nameValuePairs.add(new BasicNameValuePair("kec_name", skcmt1x));
                nameValuePairs.add(new BasicNameValuePair("telepon", snotel1x));
                nameValuePairs.add(new BasicNameValuePair("alm_website", swebs1x));
                nameValuePairs.add(new BasicNameValuePair("jml_karyawan", sjml_gr1x));
                nameValuePairs.add(new BasicNameValuePair("jml_siswa", sjml_ssw1x));
                nameValuePairs.add(new BasicNameValuePair("jml_duta", sjmldt_amb1x));
                nameValuePairs.add(new BasicNameValuePair("nm_duta1", snmdt_amb11x));
                nameValuePairs.add(new BasicNameValuePair("nm_duta2", snmdt_amb12x));
                nameValuePairs.add(new BasicNameValuePair("nm_kepala", snm_kepsek1x));
                nameValuePairs.add(new BasicNameValuePair("tlp_kepala", sno_keps1x));
                nameValuePairs.add(new BasicNameValuePair("nm_waka", snm_wksek1x));
                nameValuePairs.add(new BasicNameValuePair("tlp_waka", sno_wksek1x));
                nameValuePairs.add(new BasicNameValuePair("nm_pic_telk", snmpic_telk1x));
                nameValuePairs.add(new BasicNameValuePair("tlp_pic_telk", sno_telpic1x));
                nameValuePairs.add(new BasicNameValuePair("nm_pic_ad", snm_picad1x));
                nameValuePairs.add(new BasicNameValuePair("np_pic_ad", sno_telpicad1x));
                nameValuePairs.add(new BasicNameValuePair("inner_2g", sin2g_1x));
                nameValuePairs.add(new BasicNameValuePair("sorround_2g", ssor2g_1x));
                nameValuePairs.add(new BasicNameValuePair("inner_3g", sin3g_1x));
                nameValuePairs.add(new BasicNameValuePair("sorround_3g", ssor3g_1x));
                nameValuePairs.add(new BasicNameValuePair("lat", lattx));
                nameValuePairs.add(new BasicNameValuePair("longg", longgx));

                nameValuePairs.add(new BasicNameValuePair("produkk", produkx));
                nameValuePairs.add(new BasicNameValuePair("peoplee", peoplex));
                nameValuePairs.add(new BasicNameValuePair("promoo", promox));
                nameValuePairs.add(new BasicNameValuePair("placementt", placementx));
                nameValuePairs.add(new BasicNameValuePair("programm", programx));
                nameValuePairs.add(new BasicNameValuePair("priceeupp", priceeupx));
                nameValuePairs.add(new BasicNameValuePair("pricetpp", pricetpx));

                nameValuePairs.add(new BasicNameValuePair("produk_indstt", produk_indstx));
                nameValuePairs.add(new BasicNameValuePair("produk_xll", produk_xlx));
                nameValuePairs.add(new BasicNameValuePair("produk_three", produk_threx));
                nameValuePairs.add(new BasicNameValuePair("produk_othrr", produk_othrx));
                nameValuePairs.add(new BasicNameValuePair("juml_trs_mkios", jtr_mkiosx));
                nameValuePairs.add(new BasicNameValuePair("juml_trs_all", jtr_allx));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(dyam_url_simp_upd_tgs + "?npsn=" + id_lks);
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
                Toast.makeText(getApplicationContext(), "Data update Lokasi tersimpan", Toast.LENGTH_LONG).show();
                xd.setstatus_updatee(stt_updsek);
                xd.set_pesan_survey("Update telah dilakukan di lokasi\n*");
                new AmbilData2().execute();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(id_tgas, id_lks, sp1, sp2, sp3, sp4, sp5, sp6, snpsn1, snm_skl1, salmt1, skcmt1, snotel1, swebs1, sjml_gr1, sjml_ssw1, sjmldt_amb1, snmdt_amb11, snmdt_amb12, snm_kepsek1, sno_keps1,
                snm_wksek1, sno_wksek1, snmpic_telk1, sno_telpic1, snm_picad1, sno_telpicad1, sin2g_1, ssor2g_1, sin3g_1, ssor3g_1, latt, longg,
                produk_telk, people_1, promo_1, placement_1, program_1, priceeup_1, pricetp_1, produk_indst, produk_xl, produk_thre, produk_othr,
                jtr_mkios1, jtr_all1);
        //tambahkan ini ded
        //produkx, peoplex, promox,placementx, programx, priceeupx, pricetpx



    }

    private void upd_db(String id_tgz, String updz) {

        class SendPostReqAsyncTask extends AsyncTask < String, Void, String > {

            @
                    Override
            protected String doInBackground(String...params) {
                String id_tgx = params[0];
                String stt_updx = params[1];

                List < NameValuePair > nameValuePairs = new ArrayList < NameValuePair > ();
                nameValuePairs.add(new BasicNameValuePair("id_tugas", id_tgx));
                nameValuePairs.add(new BasicNameValuePair("updatee", stt_updx));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    dyam_url_upd_tgs = dyam_url_upd_tgs + "?ID=" + id_tgas;
                    HttpPost httpPost = new HttpPost(dyam_url_upd_tgs);
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
        sendPostReqAsyncTask.execute(id_tgas, stt_updsek);

    }




    public void pesanUnkn() {
        AlertDialog dd_dialog = new AlertDialog.Builder(Act_update_lokasi.this).create();
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


    private void sendphoto(Bitmap bitmap) throws Exception {
        try {
            //   upd_gmbr(snpsn1);
            new Upload().execute(bitmap);
        } catch (Exception e) {}
    }
    private void sendphoto_produk(Bitmap bitmap) throws Exception {
        try {
            new Upload_produk().execute(bitmap);
        } catch (Exception e) {}
    }
    private void sendphoto_promo(Bitmap bitmap) throws Exception {
        try {
            new Upload_promo().execute(bitmap);
        } catch (Exception e) {}
    }

    private void sendphoto_price(Bitmap bitmap) throws Exception {
        try {
            new Upload_price().execute(bitmap);
        } catch (Exception e) {}
    }

    private void sendphoto_place(Bitmap bitmap) throws Exception {
        try {
            new Upload_place().execute(bitmap);
        } catch (Exception e) {}
    }
    private void sendphoto_people(Bitmap bitmap) throws Exception {
        try {
            new Upload_people().execute(bitmap);
        } catch (Exception e) {}
    }
    private void sendphoto_program(Bitmap bitmap) throws Exception {
        try {
            new Upload_program().execute(bitmap);
        } catch (Exception e) {}
    }

    private class Upload_people extends AsyncTask < Bitmap, Void, Void > {
        ProgressDialog loading;
        RequestHandler rh = new RequestHandler();

        @
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            ///    loading = ProgressDialog.show(Act_update_lokasi.this, "Sedang Mengupload Gambar","Mohon Tunggu...",true,true);
        }

        @
                Override
        protected Void doInBackground(Bitmap...params) {
            List < NameValuePair > paramsz = new ArrayList < NameValuePair > ();
            //String b1;
            if (params[0] == null)
                return null;
            setProgress(0);
            Bitmap bitmap = params[0];
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // UPLOAD_URL = UPLOAD_URL+"?NPSN="+id_lks;
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            InputStream in = new ByteArrayInputStream(stream.toByteArray());
            //DefaultHttpClient httpClient = new DefaultHttpClient();
            try {
                //skripku
                String uploadImage = getStringImage(bitmap);
                HashMap < String, String > data = new HashMap < String, String > ();
                data.put(UPLOAD_KEY, uploadImage);
                data.put("npsn", id_lks.toString());
                //UPLOAD_URL = UPLOAD_URL+"?NPSN="+id_lks;
                String result = rh.sendPostRequest(UPLOAD_URL_people, data);
            } finally {

            }

            if ( in != null) {
                try { in .close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @
                Override
        protected void onProgressUpdate(Void...values) {
            super.onProgressUpdate(values);
        }

        @
                Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }


    private class Upload_program extends AsyncTask < Bitmap, Void, Void > {
        ProgressDialog loading;
        RequestHandler rh = new RequestHandler();

        @
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            ///    loading = ProgressDialog.show(Act_update_lokasi.this, "Sedang Mengupload Gambar","Mohon Tunggu...",true,true);
        }

        @
                Override
        protected Void doInBackground(Bitmap...params) {
            List < NameValuePair > paramsz = new ArrayList < NameValuePair > ();
            //String b1;
            if (params[0] == null)
                return null;
            setProgress(0);
            Bitmap bitmap = params[0];
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // UPLOAD_URL = UPLOAD_URL+"?NPSN="+id_lks;
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            InputStream in = new ByteArrayInputStream(stream.toByteArray());
            //DefaultHttpClient httpClient = new DefaultHttpClient();
            try {
                //skripku
                String uploadImage = getStringImage(bitmap);
                HashMap < String, String > data = new HashMap < String, String > ();
                data.put(UPLOAD_KEY, uploadImage);
                data.put("npsn", id_lks.toString());
                //UPLOAD_URL = UPLOAD_URL+"?NPSN="+id_lks;
                String result = rh.sendPostRequest(UPLOAD_URL_program, data);
            } finally {

            }

            if ( in != null) {
                try { in .close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @
                Override
        protected void onProgressUpdate(Void...values) {
            super.onProgressUpdate(values);
        }

        @
                Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    private class Upload_place extends AsyncTask < Bitmap, Void, Void > {
        ProgressDialog loading;
        RequestHandler rh = new RequestHandler();

        @
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            ///    loading = ProgressDialog.show(Act_update_lokasi.this, "Sedang Mengupload Gambar","Mohon Tunggu...",true,true);
        }

        @
                Override
        protected Void doInBackground(Bitmap...params) {
            List < NameValuePair > paramsz = new ArrayList < NameValuePair > ();
            //String b1;
            if (params[0] == null)
                return null;
            setProgress(0);
            Bitmap bitmap = params[0];
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // UPLOAD_URL = UPLOAD_URL+"?NPSN="+id_lks;
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            InputStream in = new ByteArrayInputStream(stream.toByteArray());
            //DefaultHttpClient httpClient = new DefaultHttpClient();
            try {
                //skripku
                String uploadImage = getStringImage(bitmap);
                HashMap < String, String > data = new HashMap < String, String > ();
                data.put(UPLOAD_KEY, uploadImage);
                data.put("npsn", id_lks.toString());
                //UPLOAD_URL = UPLOAD_URL+"?NPSN="+id_lks;
                String result = rh.sendPostRequest(UPLOAD_URL_place, data);
            } finally {

            }

            if ( in != null) {
                try { in .close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @
                Override
        protected void onProgressUpdate(Void...values) {
            super.onProgressUpdate(values);
        }

        @
                Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    private class Upload_price extends AsyncTask < Bitmap, Void, Void > {
        ProgressDialog loading;
        RequestHandler rh = new RequestHandler();

        @
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            ///    loading = ProgressDialog.show(Act_update_lokasi.this, "Sedang Mengupload Gambar","Mohon Tunggu...",true,true);
        }

        @
                Override
        protected Void doInBackground(Bitmap...params) {
            List < NameValuePair > paramsz = new ArrayList < NameValuePair > ();
            //String b1;
            if (params[0] == null)
                return null;
            setProgress(0);
            Bitmap bitmap = params[0];
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // UPLOAD_URL = UPLOAD_URL+"?NPSN="+id_lks;
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            InputStream in = new ByteArrayInputStream(stream.toByteArray());
            //DefaultHttpClient httpClient = new DefaultHttpClient();
            try {
                //skripku
                String uploadImage = getStringImage(bitmap);
                HashMap < String, String > data = new HashMap < String, String > ();
                data.put(UPLOAD_KEY, uploadImage);
                data.put("npsn", id_lks.toString());
                //UPLOAD_URL = UPLOAD_URL+"?NPSN="+id_lks;
                String result = rh.sendPostRequest(UPLOAD_URL_price, data);
            } finally {

            }

            if ( in != null) {
                try { in .close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @
                Override
        protected void onProgressUpdate(Void...values) {
            super.onProgressUpdate(values);
        }

        @
                Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Toast.makeText(Act_update_lokasi.this, "Successfully Uploaded\nisi ID Lokasi:"+ id_lks, Toast.LENGTH_LONG).show();
            // loading.dismiss();
        }
    }

    private class Upload_promo extends AsyncTask < Bitmap, Void, Void > {
        ProgressDialog loading;
        RequestHandler rh = new RequestHandler();

        @
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            ///    loading = ProgressDialog.show(Act_update_lokasi.this, "Sedang Mengupload Gambar","Mohon Tunggu...",true,true);
        }

        @
                Override
        protected Void doInBackground(Bitmap...params) {
            List < NameValuePair > paramsz = new ArrayList < NameValuePair > ();
            //String b1;
            if (params[0] == null)
                return null;
            setProgress(0);
            Bitmap bitmap = params[0];
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // UPLOAD_URL = UPLOAD_URL+"?NPSN="+id_lks;
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            InputStream in = new ByteArrayInputStream(stream.toByteArray());
            //DefaultHttpClient httpClient = new DefaultHttpClient();
            try {
                //skripku
                String uploadImage = getStringImage(bitmap);
                HashMap < String, String > data = new HashMap < String, String > ();
                data.put(UPLOAD_KEY, uploadImage);
                data.put("npsn", id_lks.toString());
                //UPLOAD_URL = UPLOAD_URL+"?NPSN="+id_lks;
                String result = rh.sendPostRequest(UPLOAD_URL_promo, data);
            } finally {

            }

            if ( in != null) {
                try { in .close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @
                Override
        protected void onProgressUpdate(Void...values) {
            super.onProgressUpdate(values);
        }

        @
                Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Toast.makeText(Act_update_lokasi.this, "Successfully Uploaded\nisi ID Lokasi:"+ id_lks, Toast.LENGTH_LONG).show();
            // loading.dismiss();
        }
    }
    private class Upload_produk extends AsyncTask < Bitmap, Void, Void > {
        ProgressDialog loading;
        RequestHandler rh = new RequestHandler();

        @
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            ///    loading = ProgressDialog.show(Act_update_lokasi.this, "Sedang Mengupload Gambar","Mohon Tunggu...",true,true);
        }

        @
                Override
        protected Void doInBackground(Bitmap...params) {
            List < NameValuePair > paramsz = new ArrayList < NameValuePair > ();
            //String b1;
            if (params[0] == null)
                return null;
            setProgress(0);
            Bitmap bitmap = params[0];
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // UPLOAD_URL = UPLOAD_URL+"?NPSN="+id_lks;
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            InputStream in = new ByteArrayInputStream(stream.toByteArray());
            //DefaultHttpClient httpClient = new DefaultHttpClient();
            try {
                //skripku
                String uploadImage = getStringImage(bitmap);
                HashMap < String, String > data = new HashMap < String, String > ();
                data.put(UPLOAD_KEY, uploadImage);
                data.put("npsn", id_lks.toString());
                //UPLOAD_URL = UPLOAD_URL+"?NPSN="+id_lks;
                String result = rh.sendPostRequest(UPLOAD_URL_produk, data);
            } finally {

            }

            if ( in != null) {
                try { in .close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @
                Override
        protected void onProgressUpdate(Void...values) {
            super.onProgressUpdate(values);
        }

        @
                Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Toast.makeText(Act_update_lokasi.this, "Successfully Uploaded\nisi ID Lokasi:"+ id_lks, Toast.LENGTH_LONG).show();
            // loading.dismiss();
        }
    }

    private class Upload extends AsyncTask < Bitmap, Void, Void > {
        //  ProgressDialog loading;
        RequestHandler rh = new RequestHandler();

        @
                Override
        protected void onPreExecute() {
            super.onPreExecute();
            ///    loading = ProgressDialog.show(Act_update_lokasi.this, "Sedang Mengupload Gambar","Mohon Tunggu...",true,true);
        }

        @
                Override
        protected Void doInBackground(Bitmap...params) {
            List < NameValuePair > paramsz = new ArrayList < NameValuePair > ();
            //String b1;
            if (params[0] == null)
                return null;
            setProgress(0);
            Bitmap bitmap = params[0];
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // UPLOAD_URL = UPLOAD_URL+"?NPSN="+id_lks;
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            InputStream in = new ByteArrayInputStream(stream.toByteArray());
            //DefaultHttpClient httpClient = new DefaultHttpClient();
            try {
                //skripku
                String uploadImage = getStringImage(bitmap);
                HashMap < String, String > data = new HashMap < String, String > ();
                data.put(UPLOAD_KEY, uploadImage);
                data.put("npsn", id_lks.toString());
                //UPLOAD_URL = UPLOAD_URL+"?NPSN="+id_lks;
                String result = rh.sendPostRequest(UPLOAD_URL, data);
            } finally {

            }

            if ( in != null) {
                try { in .close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @
                Override
        protected void onProgressUpdate(Void...values) {
            super.onProgressUpdate(values);
        }

        @
                Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Toast.makeText(Act_update_lokasi.this, "Successfully Uploaded\nisi ID Lokasi:"+ id_lks, Toast.LENGTH_LONG).show();
            // loading.dismiss();
        }
    }

    private void upd_gmbr(final String id_lokasi) {

        class SendPostReqAsyncTask extends AsyncTask < String, Void, String > {

            @
                    Override
            protected String doInBackground(String...params) {
                String id_lokasix = params[0];

                List < NameValuePair > nameValuePairs = new ArrayList < NameValuePair > ();
                nameValuePairs.add(new BasicNameValuePair("npsn", id_lokasix));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    UPLOAD_URL = UPLOAD_URL + "?NPSN=" + id_lokasi;
                    HttpPost httpPost = new HttpPost(UPLOAD_URL);
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
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(id_lokasi);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                if (apa_lokasi == "ya") {
                    setpic();
                    apa_lokasi = "tidak";
                }
                if (apa_produk == "ya") {
                    setpic_produk();
                    apa_produk = "tidak";
                }
                if (apa_promo == "ya") {
                    setpic_promo();
                    apa_promo = "tidak";
                }
                if (apa_price == "ya") {
                    setpic_price();
                    apa_price = "tidak";
                }
                if (apa_place == "ya") {
                    setpic_place();
                    apa_place = "tidak";
                }
                if (apa_people == "ya") {
                    setpic_people();
                    apa_people = "tidak";
                }
                if (apa_program == "ya") {
                    setpic_program();
                    apa_program = "tidak";
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            selectedImagePath = getImagePath(selectedImageUri); //getPath(selectedImageUri);
            img_produk2.setImageURI(selectedImageUri);
            if (selectedImagePath == null) {
                Log.d("Selected", "Null");
            } else {
                Log.d("Selected", selectedImagePath);
            }
            cek1 = "galery";
            try {
                setpic();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //@SuppressLint("ShowToast")
    private void setpic() throws IOException {
        int targetH = img_cam2.getHeight() * 2;
        int targetW = img_cam2.getWidth() * 2;
        String url_foto;
        if (cek1.equals("galery")) {
            url_foto = selectedImagePath;
            if (url_foto == null) {
                Toast.makeText(getApplicationContext(), "URL FOTONYA NULL", Toast.LENGTH_LONG);
                url_foto = "/storage/emulated/0/DCIM/Facebook/FB_IMG_1442847270929.jpg";
            } else {
                Toast.makeText(getApplicationContext(), "URL : " + url_foto, Toast.LENGTH_LONG);
                Log.d("url_foto", url_foto);
            }
        } else {
            url_foto = mCurrentPhotoPath;
        }
        url_foto = mCurrentPhotoPath;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(url_foto, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor << 1;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(url_foto, bmOptions);
        Matrix mtx = new Matrix();
        ExifInterface ei = new ExifInterface(url_foto);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                mtx.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                mtx.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                mtx.postRotate(270);
                break;
        }
        rotateBMP = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mtx, true);
        img_cam2.setImageBitmap(rotateBMP);

    }

    private void setpic_program() throws IOException {
        int targetH = img_program2.getHeight() * 2;
        int targetW = img_program2.getWidth() * 2;
        String url_foto;
        if (cek1.equals("galery")) {
            url_foto = selectedImagePath;
            if (url_foto == null) {
                Toast.makeText(getApplicationContext(), "URL FOTONYA NULL", Toast.LENGTH_LONG);
                url_foto = "/storage/emulated/0/DCIM/Facebook/FB_IMG_1442847270929.jpg";
            } else {
                Toast.makeText(getApplicationContext(), "URL : " + url_foto, Toast.LENGTH_LONG);
                Log.d("url_foto", url_foto);
            }
        } else {
            url_foto = mCurrentPhotoPath;
        }
        url_foto = mCurrentPhotoPath;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(url_foto, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor << 1;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(url_foto, bmOptions);
        Matrix mtx = new Matrix();
        ExifInterface ei = new ExifInterface(url_foto);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                mtx.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                mtx.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                mtx.postRotate(270);
                break;
        }
        rotateBMP_program = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mtx, true);
        img_program2.setImageBitmap(rotateBMP_program);

    }

    private void setpic_people() throws IOException {
        int targetH = img_people2.getHeight() * 2;
        int targetW = img_people2.getWidth() * 2;
        String url_foto;
        if (cek1.equals("galery")) {
            url_foto = selectedImagePath;
            if (url_foto == null) {
                Toast.makeText(getApplicationContext(), "URL FOTONYA NULL", Toast.LENGTH_LONG);
                url_foto = "/storage/emulated/0/DCIM/Facebook/FB_IMG_1442847270929.jpg";
            } else {
                Toast.makeText(getApplicationContext(), "URL : " + url_foto, Toast.LENGTH_LONG);
                Log.d("url_foto", url_foto);
            }
        } else {
            url_foto = mCurrentPhotoPath;
        }
        url_foto = mCurrentPhotoPath;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(url_foto, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor << 1;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(url_foto, bmOptions);
        Matrix mtx = new Matrix();
        ExifInterface ei = new ExifInterface(url_foto);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                mtx.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                mtx.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                mtx.postRotate(270);
                break;
        }
        rotateBMP_people = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mtx, true);
        img_people2.setImageBitmap(rotateBMP_people);

    }

    private void setpic_place() throws IOException {
        int targetH = img_place2.getHeight() * 2;
        int targetW = img_place2.getWidth() * 2;
        String url_foto;
        if (cek1.equals("galery")) {
            url_foto = selectedImagePath;
            if (url_foto == null) {
                Toast.makeText(getApplicationContext(), "URL FOTONYA NULL", Toast.LENGTH_LONG);
                url_foto = "/storage/emulated/0/DCIM/Facebook/FB_IMG_1442847270929.jpg";
            } else {
                Toast.makeText(getApplicationContext(), "URL : " + url_foto, Toast.LENGTH_LONG);
                Log.d("url_foto", url_foto);
            }
        } else {
            url_foto = mCurrentPhotoPath;
        }
        url_foto = mCurrentPhotoPath;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(url_foto, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor << 1;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(url_foto, bmOptions);
        Matrix mtx = new Matrix();
        ExifInterface ei = new ExifInterface(url_foto);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                mtx.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                mtx.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                mtx.postRotate(270);
                break;
        }
        rotateBMP_place = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mtx, true);
        img_place2.setImageBitmap(rotateBMP_place);

    }

    private void setpic_price() throws IOException {
        int targetH = img_price2.getHeight() * 2;
        int targetW = img_price2.getWidth() * 2;
        String url_foto;
        if (cek1.equals("galery")) {
            url_foto = selectedImagePath;
            if (url_foto == null) {
                Toast.makeText(getApplicationContext(), "URL FOTONYA NULL", Toast.LENGTH_LONG);
                url_foto = "/storage/emulated/0/DCIM/Facebook/FB_IMG_1442847270929.jpg";
            } else {
                Toast.makeText(getApplicationContext(), "URL : " + url_foto, Toast.LENGTH_LONG);
                Log.d("url_foto", url_foto);
            }
        } else {
            url_foto = mCurrentPhotoPath;
        }
        url_foto = mCurrentPhotoPath;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(url_foto, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor << 1;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(url_foto, bmOptions);
        Matrix mtx = new Matrix();
        ExifInterface ei = new ExifInterface(url_foto);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                mtx.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                mtx.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                mtx.postRotate(270);
                break;
        }
        rotateBMP_price = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mtx, true);
        img_price2.setImageBitmap(rotateBMP_price);

    }
    private void setpic_produk() throws IOException {
        int targetH = img_produk2.getHeight() * 2;
        int targetW = img_produk2.getWidth() * 2;
        String url_foto;
        if (cek1.equals("galery")) {
            url_foto = selectedImagePath;
            if (url_foto == null) {
                Toast.makeText(getApplicationContext(), "URL FOTONYA NULL", Toast.LENGTH_LONG);
                url_foto = "/storage/emulated/0/DCIM/Facebook/FB_IMG_1442847270929.jpg";
            } else {
                Toast.makeText(getApplicationContext(), "URL : " + url_foto, Toast.LENGTH_LONG);
                Log.d("url_foto", url_foto);
            }
        } else {
            url_foto = mCurrentPhotoPath;
        }
        url_foto = mCurrentPhotoPath;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(url_foto, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor << 1;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(url_foto, bmOptions);
        Matrix mtx = new Matrix();
        ExifInterface ei = new ExifInterface(url_foto);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                mtx.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                mtx.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                mtx.postRotate(270);
                break;
        }
        rotateBMP_produk = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mtx, true);
        img_produk2.setImageBitmap(rotateBMP_produk);

    }
    private void setpic_promo() throws IOException {
        int targetH = img_promo2.getHeight() * 2;
        int targetW = img_promo2.getWidth() * 2;
        String url_foto;
        if (cek1.equals("galery")) {
            url_foto = selectedImagePath;
            if (url_foto == null) {
                Toast.makeText(getApplicationContext(), "URL FOTONYA NULL", Toast.LENGTH_LONG);
                url_foto = "/storage/emulated/0/DCIM/Facebook/FB_IMG_1442847270929.jpg";
            } else {
                Toast.makeText(getApplicationContext(), "URL : " + url_foto, Toast.LENGTH_LONG);
                Log.d("url_foto", url_foto);
            }
        } else {
            url_foto = mCurrentPhotoPath;
        }
        url_foto = mCurrentPhotoPath;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(url_foto, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor << 1;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(url_foto, bmOptions);
        Matrix mtx = new Matrix();
        ExifInterface ei = new ExifInterface(url_foto);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                mtx.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                mtx.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                mtx.postRotate(270);
                break;
        }
        rotateBMP_promo = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mtx, true);
        img_promo2.setImageBitmap(rotateBMP_promo);

    }

    private String getImagePath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                uri, null, android.provider.MediaStore.Images.Media._ID + " = ? ", new String[] {
                        document_id
                }, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;

    }


    public class AmbilData2 extends AsyncTask < String, String, String > {
        JSONArray contacts = null;
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
            String aa = xd.getusnme();
            JSONObject json = jParser.ambilURL(url + "?ID=" + id_tgas);
            //JSONObject json = jParser.ambilURL(url);
            try {
                contacts = json.getJSONArray("tugas");
                for (i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    HashMap < String, String > map = new HashMap < String, String > ();
                    xd.set_pesan_jual("Tugas Activity telah dilakukan, di lokasi\n*");
                    xd.set_pesan_survey("Tidak ada perintah survey di lokasi\n*");
                    xd.set_pesan_update("Tidak ada perintah update di lokasi\n*");
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
            in .putExtra("id_tugas", id_tgas);
            in .putExtra("id_lokasi", id_lks);
            in .putExtra("nama_lokasi", nm_loks);
            in .putExtra("UPDATEE", stt_updsek);
            in .putExtra("jenis_kegiatan", jn_kgn);
            finish();
            startActivity( in );
        }
    }

    public void disable_edt() {
        npsn1.setEnabled(false);
        nm_skl1.setEnabled(false);
        almt1.setEnabled(false);
        kcmt1.setEnabled(false);
        webs1.setEnabled(false);
        jml_gr1.setEnabled(false);
        jml_ssw1.setEnabled(false);
        jmldt_amb1.setEnabled(false);
        nmdt_amb11.setEnabled(false);
        nmdt_amb12.setEnabled(false);
        nm_kepsek1.setEnabled(false);
        no_keps1.setEnabled(false);
        nm_wksek1.setEnabled(false);
        no_wksek1.setEnabled(false);
        nmpic_telk1.setEnabled(false);
        no_telpic1.setEnabled(false);
        no_telpicad1.setEnabled(false);
        in2g_1.setEnabled(false);
        sor2g_1.setEnabled(false);
        in3g_1.setEnabled(false);
        sor3g_1.setEnabled(false);
        e_produk_telk.setEnabled(false);
        e_produk_xl.setEnabled(false);
        e_produk_indosat.setEnabled(false);
        e_produk_three.setEnabled(false);
        e_produk_other.setEnabled(false);
        e_priceeup.setEnabled(false);
        e_pricetp.setEnabled(false);
        e_placement.setEnabled(false);
        e_jutrs_mkios.setEnabled(false);
        e_jutrs_all.setEnabled(false);
        e_people.setEnabled(false);
        e_progrm.setEnabled(false);
        e_promo.setEnabled(false);
        spn_lokasi.setEnabled(false);
        spn_jenjang.setEnabled(false);
        spn_level.setEnabled(false);
        spn_kategori.setEnabled(false);
        spn_poii.setEnabled(false);
        spn_valuee.setEnabled(false);
    }
    public void enable_edt() {
        npsn1.setEnabled(true);
        nm_skl1.setEnabled(true);
        almt1.setEnabled(true);
        kcmt1.setEnabled(true);
        webs1.setEnabled(true);
        jml_gr1.setEnabled(true);
        jml_ssw1.setEnabled(true);
        jmldt_amb1.setEnabled(true);
        nmdt_amb11.setEnabled(true);
        nmdt_amb12.setEnabled(true);
        nm_kepsek1.setEnabled(true);
        no_keps1.setEnabled(true);
        nm_wksek1.setEnabled(true);
        no_wksek1.setEnabled(true);
        nmpic_telk1.setEnabled(true);
        no_telpic1.setEnabled(true);
        no_telpicad1.setEnabled(true);
        in2g_1.setEnabled(true);
        sor2g_1.setEnabled(true);
        in3g_1.setEnabled(true);
        sor3g_1.setEnabled(true);
        e_produk_telk.setEnabled(true);
        e_produk_xl.setEnabled(true);
        e_produk_indosat.setEnabled(true);
        e_produk_three.setEnabled(true);
        e_produk_other.setEnabled(true);
        e_priceeup.setEnabled(true);
        e_pricetp.setEnabled(true);
        e_placement.setEnabled(true);
        e_jutrs_mkios.setEnabled(true);
        e_jutrs_all.setEnabled(true);
        e_people.setEnabled(true);
        e_progrm.setEnabled(true);
        e_promo.setEnabled(true);
        spn_lokasi.setEnabled(true);
        spn_jenjang.setEnabled(true);
        spn_level.setEnabled(true);
        spn_kategori.setEnabled(true);
        spn_poii.setEnabled(true);
        spn_valuee.setEnabled(true);
    }
}