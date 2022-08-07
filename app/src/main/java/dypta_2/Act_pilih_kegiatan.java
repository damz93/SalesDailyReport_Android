package dypta_2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import app.dypta_2.R;

@
		SuppressLint({
		"SimpleDateFormat", "ShowToast"
})
public class Act_pilih_kegiatan extends Activity implements OnItemSelectedListener {
	private static final String TAG = Act_pilih_kegiatan.class.getSimpleName();
	GPSTracker gps;
	TextView d_tgl_kegiatan, t_ktgr_kegiatan, t_nama_lokasi, t_nm_cluster;
	Spinner s_jenis_kegiatan;
	//String item[]={"Reguler", "Event"};
	String i_tugas, n_lokasi, j_kegiatan, nm_cluster, str_tgl, str_tgl2, tglz2, longdd, latdd;
	String selectedImagePath;
	String longmu, latmu, tgl_kerjas, longnya, latnya;
	public static String UPLOAD_URL = "http://own-youth.com/dd_json/upl2.php";
	public static final String UPLOAD_KEY = "image";
	public static String IMAGE_DIRECTORY_NAME = "gambar";
	Bitmap rotateBMP;
	Calendar tgl = Calendar.getInstance();
	Button b_next;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	//String mCurrentPhotoPath;
	//private Bitmap bitmap;
	//private Uri filePath;
	Uri imageUri2;
	private static final int PICK_IMAGE_REQUEST = 3;
	//private static final int PICK_CAMERA_REQUEST = 4;
	public static final int MEDIA_TYPE_IMAGE = 4;
	public static final int MEDIA_TYPE_VIDEO = 2;
	//private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	Act_set_get x1 = new Act_set_get();
	Intent i_next;
	String picturePath;
	ImageView img_cam, img_cams, img_lok;
	Bitmap photo;
	Uri selectedImage;

	String cek, a1 = "0";
	Uri imageUri;
	static TextView imageDetails;
	// public  static ImageView showImg  = null;
	Act_pilih_kegiatan CameraActivity = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_pilih_kegiatan);

		t_ktgr_kegiatan = (TextView) findViewById(R.id.tx_ktg_kegiatan);
		t_nama_lokasi = (TextView) findViewById(R.id.tx_lks_kegiatan);
		t_nm_cluster = (TextView) findViewById(R.id.tx_nama_cluster);
		d_tgl_kegiatan = (TextView) findViewById(R.id.dt_tgl_kegiatan);
		s_jenis_kegiatan = (Spinner) findViewById(R.id.sp_jenis_kegiatan);
		img_cam = (ImageView) findViewById(R.id.im_kamera);
		img_cams = (ImageView) findViewById(R.id.im_kameraS);
		img_lok = (ImageView) findViewById(R.id.im_lokasi);

		s_jenis_kegiatan.setOnItemSelectedListener(this);
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
		str_tgl = sdf1.format(tgl.getTime());
		str_tgl2 = sdf2.format(tgl.getTime());

		Bundle b = getIntent().getExtras();
		i_tugas = b.getString("id_tugas");
		n_lokasi = b.getString("nama_lokasi");
		x1.setnm_lokasi(n_lokasi);
		j_kegiatan = b.getString("jenis_kegiatan");
		nm_cluster = b.getString("cluster");
		longdd = b.getString("long");
		latdd = b.getString("lat");
		t_ktgr_kegiatan.setText(j_kegiatan);
		t_nama_lokasi.setText(n_lokasi);

		//t_nm_cluster.setText(nm_cluster+"\nLong: "+longdd+"\nLat: "+latdd);
		t_nm_cluster.setText(nm_cluster);

		x1.set_pr_loop(a1);
		x1.set_pr_as(a1);
		x1.set_pr_simpati(a1);
		x1.set_pr_circle40k(a1);
		x1.set_pr_circle70k(a1);
		x1.set_pr_member(a1);
		x1.set_pr_cug(a1);
		x1.set_pr_maxloop(a1);
		x1.set_pr_nsp(a1);
		x1.set_pr_other(a1);
		x1.set_pr_suploop(a1);
		x1.set_pr_loophol(a1);
		x1.set_pr_pdk2gb(a1);
		x1.set_pr_pdk4gb(a1);

		x1.set_mkios_5k(a1);
		x1.set_mkios_10k(a1);
		x1.set_mkios_20k(a1);
		x1.set_mkios_25k(a1);
		x1.set_mkios_50k(a1);
		x1.set_mkios_100k(a1);

		x1.set_voucher_10k(a1);
		x1.set_voucher_25k(a1);
		x1.set_voucher_50k(a1);
		x1.set_voucher_100k(a1);

		x1.set_pk_5k(a1);
		x1.set_pk_10k(a1);
		x1.set_pk_20k(a1);
		x1.set_pk_25k(a1);
		x1.set_pk_50k(a1);
		x1.set_pk_100k(a1);

		x1.set_th_xl(a1);
		x1.set_th_3(a1);
		x1.set_th_isat(a1);
		x1.set_th_smart(a1);
		x1.set_th_other(a1);


		//ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,item );
		//    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//   s_jenis_kegiatan.setAdapter(adapter);
		d_tgl_kegiatan.setText(str_tgl);
		b_next = (Button) findViewById(R.id.bt_next);
		//Untuk button lokasi
		img_lok.setOnClickListener(new View.OnClickListener() {@
				Override
		public void onClick(View v) {
			gps = new GPSTracker(Act_pilih_kegiatan.this);

			// check if GPS enabled
			if (gps.canGetLocation()) {

				double latitude = gps.getLatitude();
				double longitude = gps.getLongitude();
				longmu = Double.toString(longitude);
				latmu = Double.toString(latitude);
				if ((latitude == 0.0) || (longitude == 0.0)) {
					Toast.makeText(getApplicationContext(), "Lokasi sementera direload, mohon dicoba lagi!!!", Toast.LENGTH_LONG).show();
				} else {
					longnya = longdd;
					latnya = latdd;
					x1.set_longz(longnya);
					x1.set_latz(latnya);
					x1.set_longmu(String.valueOf(longitude));
					x1.set_latmu(String.valueOf(latitude));
					//Toast.makeText(Act_pilih_kegiatan.this,"Isi latmu: "+latmu+" ||Longmu: "+longmu+
					//		"\nLatnya: "+latnya+" Longnya: "+longnya,Toast.LENGTH_LONG).show();
					Intent a = new Intent(Act_pilih_kegiatan.this, Act_lihat_lokasii.class);
					startActivity(a);
				}
			} else {
				gps.showSettingsAlert();
			}
		}
		});
		//Untuk Button Kamera
		img_cam.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cek = "camera";
				//Listing 1
				dispatchTakePictureIntent();
                    /*					//final CharSequence[] dialogitem = {"Dari Galery","Buka Kamera"};

                    			    	AlertDialog.Builder builder = new AlertDialog.Builder(Act_pilih_kegiatan.this);
                    			        builder.setTitle("Pilih Gambar");
                    			        builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    						public void onClick(DialogInterface dialog, int item) {
                    							switch(item){
                    								/*case 0 :{
                    									showFileChooser();
                    									cek="galery";
                    									break;
                    								}
                    								case 0 :{
                    									cek="camera";
                    									//Listing 1
                    									dispatchTakePictureIntent();
                    							//		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    						//			getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    						//		startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    									/*Listing 2
                    									Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    							        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    							        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    							        // start the image capture Intent
                    							        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    							        cek="camera";


                    								}

                    							}
                    						}
                    					});
                    			        builder.create().show();      */
			}
		});
		//Untuk Button Next
		b_next.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Toast.makeText(Act_pilih_kegiatan.this,"Isi tanggal: "+str_tgl2,Toast.LENGTH_LONG).show();
				//untuk gps
				gps = new GPSTracker(Act_pilih_kegiatan.this);

				// check if GPS enabled
				cd = new ConnectionDetector(getApplicationContext());
				isInternetPresent = cd.isConnectingToInternet();
				try {
					if (isInternetPresent) {
						if (gps.canGetLocation()) {
							double latitude = gps.getLatitude();
							double longitude = gps.getLongitude();
							// \n is for new line
							if ((latitude == 0.0) || (longitude == 0.0)) {
								Toast.makeText(getApplicationContext(), "Lokasi sementera direload, mohon dicoba lagi!!!", Toast.LENGTH_LONG).show();
							} else {
								longmu = Double.toString(longitude);
								latmu = Double.toString(latitude);

								if (null != img_cams.getDrawable()) {
									try {
										if (cek.equals("galery")) {
											sendphoto(rotateBMP);
										} else {
											sendphoto(rotateBMP);
											//Toast.makeText(Act_pilih_kegiatan.this,"Isi id tugas adalah: "+i_tugas,Toast.LENGTH_LONG).show();
										}
									} catch (Exception e) {
										Toast.makeText(Act_pilih_kegiatan.this, "Errornya" + e.toString().trim(), Toast.LENGTH_LONG).show();
									}
								} else {
									AlertDialog dyam_dialog = new AlertDialog.Builder(Act_pilih_kegiatan.this).create();
									dyam_dialog.setTitle("Peringatan");
									dyam_dialog.setIcon(R.drawable.warning);
									dyam_dialog.setMessage("Pilih Gambar terlebih dahulu");
									dyam_dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {

										public void onClick(DialogInterface dialog, int which) {
											dialog.dismiss();
										}
									});
									dyam_dialog.show();
								}
							}
						} else {
							// can't get location
							// GPS or Network is not enabled
							// Ask user to enable GPS/network in settings
							gps.showSettingsAlert();
						}
					} else {
						pesanUnkn();
					}
				} catch (Exception e) {
					Toast.makeText(Act_pilih_kegiatan.this, "Errornya: " + e, Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	/*
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    */
	@
			Override
	public void onItemSelected(AdapterView <? > arg0, View arg1, int arg2, long arg3) {
		//t_jenis_kegiatan.setText(item[arg2]);
	}

	@
			Override
	public void onNothingSelected(AdapterView <? > arg0) {

	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			try {
				setpic();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
			Uri selectedImageUri = data.getData();
			selectedImagePath = getImagePath(selectedImageUri); //getPath(selectedImageUri);
			img_cams.setImageURI(selectedImageUri);
			if (selectedImagePath == null) {
				Log.d("Selected", "Null");
			} else {
				Log.d("Selected", selectedImagePath);
			}
			cek = "galery";
			try {
				setpic();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void sendphoto(Bitmap bitmap) throws Exception {
		try {
			new Upload().execute(bitmap);
		} catch (Exception e) {}
	}

	private class Upload extends AsyncTask < Bitmap, Void, Void > {
		ProgressDialog loading;
		RequestHandler rh = new RequestHandler();

		@
				Override
		protected void onPreExecute() {
			super.onPreExecute();
			loading = ProgressDialog.show(Act_pilih_kegiatan.this, "Sedang Mengupload Gambar", "Mohon Tunggu...", true, true);
		}

		@
				Override
		protected Void doInBackground(Bitmap...params) {
			if (params[0] == null)
				return null;
			setProgress(0);
			Bitmap bitmap = params[0];
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			//UPLOAD_URL = UPLOAD_URL+"?ID="+i_tugas;
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
			InputStream in = new ByteArrayInputStream(stream.toByteArray());
			//DefaultHttpClient httpClient = new DefaultHttpClient();
			try {
				//skripku
				String uploadImage = getStringImage(bitmap);
				HashMap < String, String > data = new HashMap < String, String > ();
				data.put(UPLOAD_KEY, uploadImage);
				data.put("i_tugas", i_tugas.toString());
				//UPLOAD_URL = UPLOAD_URL+"?ID="+i_tugas;
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
			loading.dismiss();
			//        if (result.equals("Successfully Uploaded"))
			Toast.makeText(Act_pilih_kegiatan.this, "Successfully Uploaded", Toast.LENGTH_LONG).show();
			Intent in = new Intent(getApplicationContext(), Act_penjualan_kartu.class);
			//tgl_kerjas = str_tgl;
			tglz2 = str_tgl2; in .putExtra("id_tugas", i_tugas); in .putExtra("long", longmu); in .putExtra("lat", latmu);
			in .putExtra("tgl_kerja", tglz2);
			startActivity( in );

		}
	}




	@
			Override
	public void onPause() {
		super.onPause();
	}

	@
			Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@
			Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.i(TAG, "onSaveInstanceState");
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
	//Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	//			getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
	//		startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
	private File createImageFile() throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		String storageDir = Environment.getExternalStorageDirectory() + "/pic_penjualan";
		File dir = new File(storageDir);
		if (!dir.exists()) {
			dir.mkdir();
		}
		File image = new File(storageDir + "/" + imageFileName + ".jpg");
		mCurrentPhotoPath = image.getAbsolutePath();
		Log.i(TAG, "photo path = " + mCurrentPhotoPath);
		return image;
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

	@
			SuppressLint("ShowToast")
	private void setpic() throws IOException {
		int targetH = img_cams.getHeight() * 2;
		int targetW = img_cams.getWidth() * 2;
		String url_foto;
		if (cek.equals("galery")) {
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
		img_cams.setImageBitmap(rotateBMP);

	}
	public void onBackPressed() {
		DialogInterface.OnClickListener dd_dialog = new DialogInterface.OnClickListener() {@
				Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					//No button clicked
					dd_kembali();
					break;
			}
		}
		};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Yakin Ingin Kembali?\n(Inputan akan kembali ke default)").setPositiveButton("Tidak", dd_dialog).setNegativeButton("Ya", dd_dialog).show();
	}
	private void pesanUnkn() {
		AlertDialog dd_dialog = new AlertDialog.Builder(Act_pilih_kegiatan.this).create();
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
	public void dd_kembali() {
		super.onBackPressed();
	}

}