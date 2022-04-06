package com.example.qrgameteam15;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
/**
 * This class helps in plotting the latitude longitude on the map.
 */
public class GameMap extends AppCompatActivity {
    private MapView map;
    private FirebaseFirestore db;
    private ArrayList<Player> allPlayers;
    private ArrayList<OverlayItem> items;
    private GlobalAllPlayers globalAllPlayers = new GlobalAllPlayers();
    private SingletonPlayer singletonPlayer = new SingletonPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_map);
        Toast.makeText(GameMap.this, "lat " + singletonPlayer.lat + " lon " + singletonPlayer.lon, Toast.LENGTH_SHORT).show();

        items = new ArrayList<>();
        // fetch all players --------------------
        // TODO: snapshot listener might cause issues when players get updated real time. whil
        // TODO: tryna cmput coordinates.
        allPlayers = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Players");
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK); //render
        map.setBuiltInZoomControls(true); //zoomable
        // change start point

        GeoPoint startPoint = new GeoPoint(53.52289, -113.52503);
        // if user saved his current location
        if (singletonPlayer.lat  != -1 && singletonPlayer.lon != -1) {
            startPoint = new GeoPoint(singletonPlayer.lat, singletonPlayer.lon);
        }
        IMapController mapController = map.getController();
        mapController.setCenter(startPoint);
        mapController.setZoom(18.0);
        String go = "53.607426, -113.529866";

        //items = new ArrayList<>();
        OverlayItem home = new OverlayItem("Em's test office", "my test office", new GeoPoint(53.600044, -113.530837));
        Drawable m = home.getMarker(0);

        items.add(home);
        populateMap();

        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(getApplicationContext(),
                items, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        });

        mOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(mOverlay);
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    /**
     * given an array of All players, get all location of the qrcode and put it on the OSM.
     */
    public void populateMap() {
        ArrayList<QRCode> qrCodes = new ArrayList<>();
        //assert(globalAllPlayers.allPlayers.size() > 0);
        for (int i = 0; i < globalAllPlayers.allPlayers.size(); i++) {
            qrCodes = globalAllPlayers.allPlayers.get(i).qrCodes;
            for (int j = 0; j < qrCodes.size(); j++) {
                QRCode thisCode = qrCodes.get(j);
                if (thisCode.getHasLocation() == true) {
                    String geolocation = qrCodes.get(j).getLocation();
                    String latStr = geolocation.split(" ")[0];
                    String lonStr = geolocation.split(" ")[1];
                    double latDouble = Double.parseDouble(latStr);
                    double lonDouble = Double.parseDouble(lonStr);
                    Log.i("latlong", latStr + " " + lonStr);
                    items.add(new OverlayItem("Date Scanned: " + thisCode.getDateStr(), "Score: " + thisCode.getScore(), new GeoPoint(latDouble, lonDouble)));
                }
            }
        }
    }
}