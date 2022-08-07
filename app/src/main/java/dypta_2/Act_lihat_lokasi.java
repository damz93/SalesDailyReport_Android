package dypta_2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import app.dypta_2.R;

@SuppressLint("SetJavaScriptEnabled")
public class Act_lihat_lokasi extends Activity implements View.OnClickListener{
	Button bt_kemblok;
	WebView WB;
	String longg,latt;
	Act_set_get xx1;
	protected void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.lay_lihat_lokasi);
	     xx1 = new Act_set_get();
	     bt_kemblok = (Button)findViewById(R.id.bt_kmblok);
	     bt_kemblok.setOnClickListener(this);
	     WB = (WebView)findViewById(R.id.wb_view);
	     WB.getSettings().setJavaScriptEnabled(true);
	     longg = xx1.get_longz();
	     latt = xx1.get_latz();
	    // latt = "-5.1336345";
	     //longg = "119.4872973";
	    // Toast.makeText(Act_lihat_lokasi.this, "Longnya: "+longg+" Latnya: "+latt, Toast.LENGTH_LONG).show();	    	    
	     WebSettings webSetting = WB.getSettings();	        	       	        	       
	     WB.setWebViewClient(new MyBrowser());
	     webSetting.setBuiltInZoomControls(true);
	     webSetting.setDisplayZoomControls(true);
	     webSetting.setLoadWithOverviewMode(true);    
	     webSetting.setUseWideViewPort(true);
	   //  WB.loadUrl("https://www.google.co.id/maps/@"+latt+","+longg+",15z?hl=id");
	     WB.loadUrl("https://www.google.co.id/maps/@"+latt+","+longg+",18z?hl=id");
	}

	@Override
	public void onClick(View v) {
		if (v==bt_kemblok){
			finish();
		}
	}
	
	private class MyBrowser extends WebViewClient {
	       @Override
	        public  boolean shouldOverrideUrlLoading(WebView view, String url ){
	            view.loadUrl(url);
	            return true;
	        }
	}
	
}
