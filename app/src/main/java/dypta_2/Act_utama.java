package dypta_2;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

import app.dypta_2.R;

public class Act_utama extends Activity {
	private final int SPLASH_DISPLAY_LENGTH = 1500;

	/** Called when the activity is first created. */
	@
			Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
        /* layout splashscreen dengan background gambar */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.lay_splash);
		new Handler().postDelayed(new Runnable() {@
				Override
		public void run() {
			Intent mainIntent = null;

			mainIntent = new Intent(Act_utama.this, Act_login.class);

			Act_utama.this.startActivity(mainIntent);
			Act_utama.this.finish();
		}
		}, SPLASH_DISPLAY_LENGTH);
	}

}