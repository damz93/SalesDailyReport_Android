package dypta_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import app.dypta_2.R;

public class Act_utama2 extends Activity implements View.OnClickListener {
    TextView tx_slmt;
    Button bt_logout, bt_tugas, bt_outlet, bt_pengaturan, bt_request, bt_sales_act;
    Act_set_get stg = new Act_set_get();
    ConnectionDetector cd2;
    JSONP2 jParser = new JSONP2();
    Boolean isInternetPresent2 = false;
    //1
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.lay_utama);

        tx_slmt = (TextView) findViewById(R.id.txt_selamat);
        tx_slmt.setText("User Aktif: " + stg.getnama());
        bt_logout = (Button) findViewById(R.id.bt_logoutdd);
        bt_tugas = (Button) findViewById(R.id.btn_lihat_tugas);
        bt_outlet = (Button) findViewById(R.id.btn_d_outlet);
        bt_pengaturan = (Button) findViewById(R.id.btn_pengaturan);
        bt_request = (Button) findViewById(R.id.btn_req_member);
        bt_logout.setOnClickListener(this);
        bt_tugas.setOnClickListener(this);
        bt_outlet.setOnClickListener(this);
        bt_pengaturan.setOnClickListener(this);
        bt_request.setOnClickListener(this);
    }
    @
            Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == bt_logout) {
            DialogInterface.OnClickListener dd_dialog = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked

                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            finish();
                            Intent a = new Intent(Act_utama2.this, Act_login.class);
                            startActivity(a);
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Yakin Ingin Logout?").setPositiveButton("Tidak", dd_dialog).setNegativeButton("Ya", dd_dialog).show();

        } else if (v == bt_tugas) {
            cd2 = new ConnectionDetector(getApplicationContext());
            isInternetPresent2 = cd2.isConnectingToInternet();
            try {
                if (isInternetPresent2) {
                    try {
                        Intent b = new Intent(this, Act_tugas.class);
                        startActivity(b);
                    } catch (Exception e) {
                        Toast.makeText(Act_utama2.this, "error: " + e, Toast.LENGTH_LONG).show();
                    }
                } else {
                    pesanUnkn();
                }
            } catch (Exception e) {
                Toast.makeText(Act_utama2.this, "Errornya: " + e, Toast.LENGTH_LONG).show();
            }

        } else if (v == bt_pengaturan) {
            Intent c = new Intent(this, Act_pengaturan.class);
            startActivity(c);
        } else if (v == bt_outlet) {
            Intent c = new Intent(this, Act_outlet.class);
            startActivity(c);
        } else if (v == bt_request) {
            Intent intent = new Intent(this, Act_requestmember.class);
            startActivity(intent);
        } else if (v == bt_sales_act) {
            Intent intent = new Intent(this, Act_sales_activity.class);
            startActivity(intent);
        }
    }
    public void onBackPressed() {
        DialogInterface.OnClickListener dd_dialog = new DialogInterface.OnClickListener() {
            @
                    Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        };
    }
    private void pesanUnkn() {
        AlertDialog dd_dialog = new AlertDialog.Builder(Act_utama2.this).create();
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