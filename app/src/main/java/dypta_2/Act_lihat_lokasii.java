package dypta_2;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import app.dypta_2.R;

public class Act_lihat_lokasii extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Act_set_get a = new Act_set_get();
    String longnyaa,latnyaa,longmuu,latmuu,nm_lks;
    Double latnya,longnya,latmu,longmu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_lihat_lokasii);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        longnyaa = a.get_longz();
        latnyaa = a.get_latz();
        longmuu = a.get_longmu();
        latmuu = a.get_latmu();

        latnya = Double.parseDouble(latnyaa);
        longnya = Double.parseDouble(longnyaa);

        latmu = Double.parseDouble(latmuu);
        longmu = Double.parseDouble(longmuu);
        nm_lks = a.getnm_lokasi();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float cameraZoom = 12;
        LatLng letak_tujuan,letakmu;

        letak_tujuan = new LatLng(latnya, longnya);
        letakmu = new LatLng(latmu,longmu);

        mMap.addMarker(new MarkerOptions()
                .position(letak_tujuan)
                .title("Lokasi Tujuan("+nm_lks+")")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        mMap.addMarker(new MarkerOptions()
                .position(letakmu)
                .title("Lokasi anda sekarang"));
                //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(letak_tujuan, cameraZoom));
    }
}