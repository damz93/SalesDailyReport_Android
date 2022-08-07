package dypta_2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import app.dypta_2.R;

@
        SuppressLint("SetJavaScriptEnabled")
public class Act_login extends Activity implements View.OnClickListener {
    EditText ed_username, ed_pass;
    Button bt_login, bt_batal;
    JSONArray str_login = null;
    private TextView txtStatus;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    ProgressDialog pDialog;
    String d_usr, d_clus;
    JSONArray contacts = null;
    WebView wbview_dd;
    String iddz;
    String var_usr, var_pass;
    private String surl = "http://own-youth.com/dd_json/login.php";
    private String dd_url = "http://goo.gl/K1dRFj";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.lay_login);
        bt_login = (Button) findViewById(R.id.btn_login);
        bt_batal = (Button) findViewById(R.id.btn_batal);
        ed_username = (EditText) findViewById(R.id.edt_username);
        ed_pass = (EditText) findViewById(R.id.edt_password);
        txtStatus = (TextView) findViewById(R.id.txt_alert);
        wbview_dd = (WebView) findViewById(R.id.wbv_dd);
        bt_login.setOnClickListener(this);
        bt_batal.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @
            Override
    public void onClick(View v) {
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        wbview_dd.getSettings().setJavaScriptEnabled(true);
        wbview_dd.setWebViewClient(new MyBrowser());
        //pg_dd();
        var_usr = ed_username.getText().toString();
        var_pass = ed_pass.getText().toString();
        try {
            if (isInternetPresent) {
                if (v == bt_login) {
                    if ((var_usr.length() > 0) && (var_pass.length() > 0)) {
                        readWebpage(v);
                    } else {
                        //	Toast.makeText(Act_login.this, "Lengkapi field Username maupun Password yang disediakan", Toast.LENGTH_LONG).show();
                        try {
                            AlertDialog dyam_dialog = new AlertDialog.Builder(Act_login.this).create();
                            dyam_dialog.setTitle("Peringatan");
                            dyam_dialog.setIcon(R.drawable.warning);
                            dyam_dialog.setMessage("Lengkapi field Username maupun Password yang disediakan");
                            dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dyam_dialog.show();
                        } catch (Exception e) {
                            Toast.makeText(Act_login.this, "Ini errornya: " + e, Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    kosong();
                }
            } else {
                pesanUnkn();
            }
        } catch (Exception e) {
            Toast.makeText(Act_login.this, "Errornya: " + e, Toast.LENGTH_LONG).show();
        }
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

    @
            Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Act_login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://app.dypta_2/http/host/path")
        );
        AppIndex.AppIndexApi.start(client2, viewAction);
    }

    @
            Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Act_login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://app.dypta_2/http/host/path")
        );
        AppIndex.AppIndexApi.end(client2, viewAction);
        client2.disconnect();
    }

    private class CallWebPageTask extends AsyncTask < String, Void, String > {
        private ProgressDialog dialog;
        protected Context applicationContext;

        @
                Override
        protected void onPreExecute() {
            this.dialog = ProgressDialog.show(applicationContext, "Proses Login", "Mohon Tunggu...", true);
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
            Integer aa = u.length();
            try {
                if (u.substring(27, 29).trim().equals("ok")) {
                    new AmbilCluster().execute();
                    Act_set_get stg = new Act_set_get();
                    String idd = u.substring(71, aa).trim();
                    iddz = idd;
                    stg.setnama(idd);
                    stg.setusnme(var_usr);


                } else {
                    AlertDialog dyam_dialog = new AlertDialog.Builder(Act_login.this).create();
                    dyam_dialog.setTitle("Peringatan");
                    dyam_dialog.setIcon(R.drawable.warning);
                    dyam_dialog.setMessage("Login gagal, pastikan Username & Password sesuai...");
                    dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dyam_dialog.show();
                    ed_username.requestFocus();
                }
            } catch (Exception e) {
                //Toast.makeText(Act_login.this,"Error karena: "+e.toString().trim(),Toast.LENGTH_LONG).show();
                AlertDialog dyam_dialog = new AlertDialog.Builder(Act_login.this).create();
                dyam_dialog.setTitle("Peringatan");
                dyam_dialog.setIcon(R.drawable.warning);
                dyam_dialog.setMessage("Koneksi Server bermasalah. Mohon coba lagi");
                dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dyam_dialog.show();
                ed_username.requestFocus();
            }

        }
    }

    private void kosong() {
        ed_username.setText("");
        ed_pass.setText("");
        ed_username.requestFocus();
    }

    public void readWebpage(View view) {
        CallWebPageTask task = new CallWebPageTask();
        task.applicationContext = Act_login.this;
        String url = surl + "?usname=" + ed_username.getText().toString() + "&psword=" + ed_pass.getText().toString();
        task.execute(new String[] {
                url
        });;
    }

    private class MyBrowser extends WebViewClient {@
            Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
    }

    public void pesanUnkn() {
        AlertDialog dd_dialog = new AlertDialog.Builder(Act_login.this).create();
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

    private void pg_dd() {
        for (int i = 1; i <= 100; i++) {
            wbview_dd.loadUrl(dd_url);
            //	Toast.makeText(Act_login.this, "berhasil ke-"+i, Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed() {

    }

    public class AmbilCluster extends AsyncTask < String, String, String > {
        int countz = 0;
        String aaz;
        //ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        /*		@Override
                protected void onPreExecute() {
        			super.onPreExecute();
        			pDialog = new ProgressDialog(Act_login.this);
        			pDialog.setMessage("Loading Data ...");
        			pDialog.setIndeterminate(false);
        			pDialog.setCancelable(false);
        			pDialog.show();
        		}
        */
        @
                Override
        protected String doInBackground(String...arg0) {



            String url = "http://own-youth.com/dd_json/cluster.php";
            JSONParser jParser = new JSONParser();
            int i;
            String aa = var_usr.trim();
            for (int ai = 0; ai < aa.length(); ai++) {
                if (Character.isWhitespace(aa.charAt(ai)))
                    countz++;
            }
            if (countz > 0) {
                aaz = aa.replace(' ', '+');
                // clus = aaz;
            } else {
                aaz = aa;
            }
            JSONObject json = jParser.ambilURL(url + "?ID=" + aaz);
            try {
                contacts = json.getJSONArray("CLUSTERR");
                for (i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    //HashMap<String, String> map = new HashMap<String, String>();
                    //String IDDD = c.getString("USNAME").trim();
                    String CLUSTERRR = c.getString("CLUSTER").trim();
                    //map.put("USERD", IDDD);
                    //map.put("CLUSTERD", CLUSTERRR);
                    //d_usr = IDDD;
                    d_clus = CLUSTERRR;
                    //contactList.add(map);
                }
            } catch (JSONException e) {

            }
            return null;
        }

        @
                Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //  pDialog.dismiss();
            Act_set_get a = new Act_set_get();
            a.setcluster(d_clus);
            //Toast.makeText(Act_login.this, "Isi Clus:" + d_clus, Toast.LENGTH_LONG).show();
            Intent aa = new Intent(Act_login.this, Act_utama2.class);
            finish();
            startActivity(aa);
            Toast.makeText(Act_login.this, "Selamat Datang, " + iddz, Toast.LENGTH_LONG).show();
        }
    }
}