package com.sm19003564.omnimarkerza;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

// classes needed to initialize map
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Polygon;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

// classes needed to add the location component
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;

// classes needed to add a marker
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mapbox.mapboxsdk.plugins.places.picker.PlacePicker;
import com.mapbox.mapboxsdk.plugins.places.picker.model.PlacePickerOptions;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import static com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newLatLngBounds;
import static com.mapbox.mapboxsdk.style.expressions.Expression.within;
import static com.mapbox.mapboxsdk.style.layers.Property.NONE;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;
import static com.mapbox.turf.TurfConstants.UNIT_KILOMETERS;
import static com.mapbox.turf.TurfConstants.UNIT_MILES;

// classes to calculate a route
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;

// classes needed to launch navigation UI
import android.view.View;
import android.widget.Button;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.turf.TurfMeta;
import com.mapbox.turf.TurfTransformation;


public class MapboxActivity extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener, AdapterView.OnItemSelectedListener {
    // variables for adding location layer
    private MapView mapView;
    private MapboxMap mapboxMap;
    // variables for adding location layer
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;
    // variables for calculating and drawing a route
    private DirectionsRoute currentRoute;
    private static final String TAG = "MapboxActivity";
    private NavigationMapRoute navigationMapRoute;
    // variables needed to initialize navigation
    private Button button;

    //Settings
    Settings settings;
    private String measure;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference settingsRef = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());

    //Search
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private CarmenFeature hospital;
    private CarmenFeature college;
    private String geojsonSourceLayerId = "geojsonSourceLayerId";
    private String symbolIconId = "symbolIconId";

    //Places picker
    private static final int REQUEST_CODE = 5678;
    private TextView selectedLocationTextView;

    //Point of Interest Turf Circle
    private static final String TURF_CALCULATION_FILL_LAYER_GEOJSON_SOURCE_ID
            = "TURF_CALCULATION_FILL_LAYER_GEOJSON_SOURCE_ID";
    private static final String TURF_CALCULATION_FILL_LAYER_ID = "TURF_CALCULATION_FILL_LAYER_ID";
    private static final int RADIUS_SEEKBAR_DIFFERENCE = 1;
    private static final int RADIUS_SEEKBAR_MAX = 10;
    private static final Point DOWNTON_MUNICH_START_LOCATION =
            Point.fromLngLat(31.1328906235521, -29.643437120518342);
    private Point lastClickPoint;
    private String circleUnit = UNIT_KILOMETERS;
    private int circleRadius = RADIUS_SEEKBAR_MAX / 2;
    private TextView circleRadiusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_mapbox);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        selectedLocationTextView = findViewById(R.id.selected_location_info_textview);

        // Show the button and set the OnClickListener()
        Button goToPickerActivityButton = findViewById(R.id.go_to_picker_button);
        goToPickerActivityButton.setVisibility(View.VISIBLE);
        goToPickerActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPickerActivity();
            }
        });
        settingsRef.child("SettingsData").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataInfo: snapshot.getChildren()){
                    settings = dataInfo.getValue(Settings.class);
                }
                if (settings.isMetric()) {
                    measure = "metric";
                } else {
                    if (settings.isImperial()){
                        measure = "imperial";
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MapboxActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void goToPickerActivity() {
        startActivityForResult(
                new PlacePicker.IntentBuilder()
                        .accessToken(getString(R.string.access_token))
                        .placeOptions(PlacePickerOptions.builder()
                                .statingCameraPosition(new CameraPosition.Builder()
                                        .target(new LatLng(locationComponent.getLastKnownLocation().getLatitude(), locationComponent.getLastKnownLocation().getLongitude())).zoom(16).build())
                                .build())
                        .build(this), REQUEST_CODE);
    }

    /**
     * This fires after a location is selected in the Places Plugin's PlacePickerActivity.
     * @param requestCode code that is a part of the return to this activity
     * @param resultCode code that is a part of the return to this activity
     * @param data the data that is a part of the return to this activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {

        } else if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Retrieve the information from the selected location's CarmenFeature
            CarmenFeature carmenFeature = PlacePicker.getPlace(data);

            // Set the TextView text to the entire CarmenFeature. The CarmenFeature
            // also be parsed through to grab and display certain information such as
            // its placeName, text, or coordinates.
            if (carmenFeature != null) {
                //selectedLocationTextView.setText(String.format(getString(R.string.selected_place_info), carmenFeature.toJson()));

                pickerRouter(new LatLng(carmenFeature.center().latitude(), carmenFeature.center().longitude()));
            }
        }

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {

            // Retrieve selected location's CarmenFeature
            CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);

            // Create a new FeatureCollection and add a new Feature to it using selectedCarmenFeature above.
            // Then retrieve and update the source designated for showing a selected location's symbol layer icon

            if (mapboxMap != null) {
                Style style = mapboxMap.getStyle();
                if (style != null) {
                    GeoJsonSource source = style.getSourceAs(geojsonSourceLayerId);
                    if (source != null) {
                        source.setGeoJson(FeatureCollection.fromFeatures(
                                new Feature[] {Feature.fromJson(selectedCarmenFeature.toJson())}));
                    }

                    // Move map camera to the selected location
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(((Point) selectedCarmenFeature.geometry()).latitude(),
                                            ((Point) selectedCarmenFeature.geometry()).longitude()))
                                    .zoom(14)
                                    .build()), 4000);
                }
            }
        }
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(new Style.Builder().fromUri(Style.MAPBOX_STREETS)
                .withSource(new GeoJsonSource(TURF_CALCULATION_FILL_LAYER_GEOJSON_SOURCE_ID)), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);

                addDestinationIconSymbolLayer(style);

                initSearchFab();

                addUserLocations();

                // Add the symbol layer icon to map for future use
                style.addImage(symbolIconId, BitmapFactory.decodeResource(
                        MapboxActivity.this.getResources(), R.drawable.blue_picker3));

                // Create an empty GeoJSON source using the empty feature collection
                setUpSource(style);

                // Set up a new symbol layer for displaying the searched location's feature coordinates
                setupLayer(style);

                mapboxMap.addOnMapClickListener(MapboxActivity.this);
                button = findViewById(R.id.startButton);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean simulateRoute = true;
                        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                .directionsRoute(currentRoute)
                                .shouldSimulateRoute(simulateRoute)
                                .build();
                        // Call this method with Context from within an Activity
                        NavigationLauncher.startNavigation(MapboxActivity.this, options);
                    }
                });

                //POI Turf


                hideLayers();

                initPolygonCircleFillLayer();

                // Set up the seekbar so that the circle's radius can be adjusted
                final SeekBar circleRadiusSeekbar = findViewById(R.id.circle_radius_seekbar);
                circleRadiusSeekbar.setMax(RADIUS_SEEKBAR_MAX);
                circleRadiusSeekbar.incrementProgressBy(RADIUS_SEEKBAR_DIFFERENCE / 10);
                circleRadiusSeekbar.setProgress(RADIUS_SEEKBAR_MAX / 2);

                circleRadiusTextView = findViewById(R.id.circle_radius_textview);
                circleRadiusTextView.setText(String.format(getString(
                        R.string.polygon_circle_transformation_circle_radius),
                        String.format(".%s", String.valueOf(RADIUS_SEEKBAR_MAX / 2))));

                // Draw the initial circle around the starting location
                //drawPolygonCircle(DOWNTOWN_MUNICH_START_LOCATION);

                circleRadiusSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        makeNewRadiusAdjustments(seekBar.getProgress());
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
// Not needed in this example.
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        makeNewRadiusAdjustments(seekBar.getProgress());
                    }
                });

                mapboxMap.addOnMapClickListener(MapboxActivity.this);

                initDistanceUnitSpinner();

                Toast.makeText(MapboxActivity.this,
                        getString(R.string.polygon_circle_transformation_click_map_instruction),
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

    /**
     * Remove other types of label layers from the style in order to highlight the POI label layer.
     */
    private void hideLayers() {
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                SymbolLayer roadLabelLayer = style.getLayerAs("road-label");
                if (roadLabelLayer != null) {
                    roadLabelLayer.setProperties(visibility(NONE));
                }
                SymbolLayer transitLabelLayer = style.getLayerAs("transit-label");
                if (transitLabelLayer != null) {
                    transitLabelLayer.setProperties(visibility(NONE));
                }
                SymbolLayer roadNumberShieldLayer = style.getLayerAs("road-number-shield");
                if (roadNumberShieldLayer != null) {
                    roadNumberShieldLayer.setProperties(visibility(NONE));
                }
            }
        });
    }

    private void makeNewRadiusAdjustments(int progress) {
        String amount;
        if (progress == 0) {
            amount = "0";
        } else if (progress == RADIUS_SEEKBAR_MAX) {
            amount = "1";
        } else {
            amount = String.format(".%s", String.valueOf(progress));
        }
        circleRadiusTextView.setText(String.format(getString(
                R.string.polygon_circle_transformation_circle_radius), amount));

        circleRadius = progress;
        Point lcp = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(), locationComponent.getLastKnownLocation().getLatitude());
        drawPolygonCircle(lastClickPoint != null ? lastClickPoint : lcp);
    }

    private void initDistanceUnitSpinner() {
        Spinner spinner = findViewById(R.id.circle_units_spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.within_poi_filter_circle_distance_units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parentAdapterView, View view, int position, long id) {
        String selectedUnitInSpinnerMenu = String.valueOf(parentAdapterView.getItemAtPosition(position));
        if ("Miles".equals(selectedUnitInSpinnerMenu)) {
            circleUnit = UNIT_MILES;
        } else {
            circleUnit = UNIT_KILOMETERS;
        }
        Point lcp = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(), locationComponent.getLastKnownLocation().getLatitude());
        drawPolygonCircle(lastClickPoint != null ? lastClickPoint : lcp);
    }

    private void initSearchFab() {
        findViewById(R.id.fab_location_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken(Mapbox.getAccessToken() != null ? Mapbox.getAccessToken() : getString(R.string.access_token))
                        .placeOptions(PlaceOptions.builder()
                                .backgroundColor(Color.parseColor("#EEEEEE"))
                                .limit(10)
                                .addInjectedFeature(hospital)
                                .addInjectedFeature(college)
                                .build(PlaceOptions.MODE_CARDS))
                        .build(MapboxActivity.this);
                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
            }
        });
    }

    private void addUserLocations() {
        hospital = CarmenFeature.builder().text("Core Medical Centre")
                .geometry(Point.fromLngLat(31.030243117108757, -29.794697509199565))
                .placeName("20 Gainsborough Dr, Athlone, Durban North, 4051")
                .id("core-med-cen")
                .properties(new JsonObject())
                .build();

        college = CarmenFeature.builder().text("The IIE's Varsity College - Durban North")
                .placeName("12 Radar Dr, Durban North, Durban, 4051")
                .geometry(Point.fromLngLat(31.035638157986508, -29.79661356385285))
                .id("iie-vc-dbn")
                .properties(new JsonObject())
                .build();
    }

    private void setUpSource(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(geojsonSourceLayerId));
    }

    private void setupLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addLayer(new SymbolLayer("SYMBOL_LAYER_ID", geojsonSourceLayerId).withProperties(
                iconImage(symbolIconId),
                iconOffset(new Float[] {0f, -8f})
        ));
    }

    private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("destination-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);
    }

    @SuppressWarnings( {"MissingPermission"})
    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        Point destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());

        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            source.setGeoJson(Feature.fromGeometry(destinationPoint));
        }

        getRoute(originPoint, destinationPoint);
        button.setEnabled(true);
        button.setBackgroundResource(R.color.mapboxBlue);

        //POI Turf
        mapboxMap.easeCamera(CameraUpdateFactory.newLatLng(point));
        lastClickPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        drawPolygonCircle(lastClickPoint);
        return true;
    }

    public void pickerRouter(@NonNull LatLng point) {

        Point destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());

        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            source.setGeoJson(Feature.fromGeometry(destinationPoint));
        }

        getRoute(originPoint, destinationPoint);
        button.setEnabled(true);
        button.setBackgroundResource(R.color.mapboxBlue);

        //POI Turf
        mapboxMap.easeCamera(CameraUpdateFactory.newLatLng(point));
        lastClickPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        drawPolygonCircle(lastClickPoint);
    }

    /**
     * Update the {@link FillLayer} based on the GeoJSON retrieved via
     * {@link this#getTurfPolygon(Point, double, String)}.
     *
     * @param circleCenter the center coordinate to be used in the Turf calculation.
     */
    private void drawPolygonCircle(Point circleCenter) {
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
// Use Turf to calculate the Polygon's coordinates
                Polygon polygonArea = getTurfPolygon(circleCenter, circleRadius, circleUnit);

                List<Point> pointList = TurfMeta.coordAll(polygonArea, false);

// Update the source's GeoJSON to draw a new circle
                GeoJsonSource polygonCircleSource = style.getSourceAs(TURF_CALCULATION_FILL_LAYER_GEOJSON_SOURCE_ID);
                if (polygonCircleSource != null) {
                    polygonCircleSource.setGeoJson(Polygon.fromOuterInner(
                            LineString.fromLngLats(pointList)));
                }

// Show new places of interest (POIs)
                filterPlacesOfInterest(polygonArea);

// Adjust camera bounds to include entire circle
                List<LatLng> latLngList = new ArrayList<>(pointList.size());
                for (Point singlePoint : pointList) {
                    latLngList.add(new LatLng((singlePoint.latitude()), singlePoint.longitude()));
                }
                mapboxMap.easeCamera(newLatLngBounds(
                        new LatLngBounds.Builder()
                                .includes(latLngList)
                                .build(), 75), 1000);
            }
        });
    }

    /**
     * Show only POI labels inside geometry using within expression
     *
     * @param polygonArea the polygon area (a circle in this example) to show POIs in.
     */
    private void filterPlacesOfInterest(Polygon polygonArea) {
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                SymbolLayer poiLabelLayer = style.getLayerAs("poi-label");
                if (poiLabelLayer != null) {
                    poiLabelLayer.setFilter(within(polygonArea));
                }
            }
        });
    }

    /**
     * Use the Turf library {@link TurfTransformation#circle(Point, double, int, String)} method to
     * retrieve a {@link Polygon} .
     *
     * @param centerPoint a {@link Point} which the circle will center around
     * @param radius      the radius of the circle
     * @param units       one of the units found inside {@link com.mapbox.turf.TurfConstants}
     * @return a {@link Polygon} which represents the newly created circle
     */
    private Polygon getTurfPolygon(@NonNull Point centerPoint, double radius,
                                   @NonNull String units) {
        return TurfTransformation.circle(centerPoint, radius / 10, 360, units);
    }

    /**
     * Add a {@link FillLayer} to display a {@link Polygon} in a the shape of a circle.
     */
    private void initPolygonCircleFillLayer() {
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
// Create and style a FillLayer based on information that will come from the Turf calculation
                FillLayer fillLayer = new FillLayer(TURF_CALCULATION_FILL_LAYER_ID,
                        TURF_CALCULATION_FILL_LAYER_GEOJSON_SOURCE_ID);
                fillLayer.setProperties(
                        fillColor(Color.parseColor("#f5425d")),
                        fillOpacity(.5f));
                style.addLayerBelow(fillLayer, "poi-label");
            }
        });
    }

    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .voiceUnits(measure)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        // You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }


                        currentRoute = response.body().routes().get(0);


                        // Draw the route on the map
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // Activate the MapboxMap LocationComponent to show user location
            // Adding in LocationComponentOptions is also an optional parameter
            locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(this, loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationComponent(mapboxMap.getStyle());
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}