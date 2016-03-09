package com.mapbox.mapboxsdk.maps;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;

import com.mapbox.mapboxsdk.R;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.constants.MapboxConstants;

public class MapboxMapOptions implements Parcelable {

    private static final float DIMENSION_SEVEN_DP = 7f;
    private static final float DIMENSION_TEN_DP = 10f;
    private static final float DIMENSION_SIXTEEN_DP = 16f;
    private static final float DIMENSION_SEVENTY_SIX_DP = 76f;

    private CameraPosition cameraPosition;

    private boolean debugActive;

    private boolean compassEnabled = true;
    private int compassGravity = Gravity.TOP | Gravity.END;
    private int compassMargins[];

    private boolean logoEnabled = true;
    private int logoGravity;
    private int logoMargins[];

    private boolean attributionEnabled = true;
    private int attributionGravity;
    private int attributionMargins[];

    private float minZoom = MapboxConstants.MINIMUM_ZOOM;
    private float maxZoom = MapboxConstants.MAXIMUM_ZOOM;

    private boolean rotateGesturesEnabled = true;
    private boolean scrollGesturesEnabled = true;
    private boolean tiltGesturesEnabled = true;
    private boolean zoomControlsEnabled = true;
    private boolean zoomGesturesEnabled = true;

    private boolean locationEnabled;

    private String style;
    private String accessToken;

    public MapboxMapOptions() {
    }

    private MapboxMapOptions(Parcel in) {
        cameraPosition = in.readParcelable(CameraPosition.class.getClassLoader());
        debugActive = in.readByte() != 0;
        compassEnabled = in.readByte() != 0;
        rotateGesturesEnabled = in.readByte() != 0;
        scrollGesturesEnabled = in.readByte() != 0;
        tiltGesturesEnabled = in.readByte() != 0;
        zoomControlsEnabled = in.readByte() != 0;
        zoomGesturesEnabled = in.readByte() != 0;
        style = in.readString();
        accessToken = in.readString();
    }

    public static MapboxMapOptions createFromAttributes(Context context, AttributeSet attrs) {
        MapboxMapOptions mapboxMapOptions = new MapboxMapOptions();
        float screenDensity = context.getResources().getDisplayMetrics().density;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MapView, 0, 0);
        try {
            mapboxMapOptions.debugActive(typedArray.getBoolean(R.styleable.MapView_debug_active, false));

            mapboxMapOptions.camera(new CameraPosition.Builder(typedArray).build());

            mapboxMapOptions.accessToken(typedArray.getString(R.styleable.MapView_access_token));
            mapboxMapOptions.styleUrl(typedArray.getString(R.styleable.MapView_style_url));

            mapboxMapOptions.zoomGesturesEnabled(typedArray.getBoolean(R.styleable.MapView_zoom_enabled, true));
            mapboxMapOptions.scrollGesturesEnabled(typedArray.getBoolean(R.styleable.MapView_scroll_enabled, true));
            mapboxMapOptions.rotateGesturesEnabled(typedArray.getBoolean(R.styleable.MapView_rotate_enabled, true));
            mapboxMapOptions.tiltGesturesEnabled(typedArray.getBoolean(R.styleable.MapView_tilt_enabled, true));
            mapboxMapOptions.zoomControlsEnabled(typedArray.getBoolean(R.styleable.MapView_zoom_controls_enabled, false));

            mapboxMapOptions.maxZoom(typedArray.getFloat(R.styleable.MapView_zoom_max, MapboxConstants.MAXIMUM_ZOOM));
            mapboxMapOptions.minZoom(typedArray.getFloat(R.styleable.MapView_zoom_min, MapboxConstants.MINIMUM_ZOOM));

            mapboxMapOptions.compassEnabled(typedArray.getBoolean(R.styleable.MapView_compass_enabled, true));
            mapboxMapOptions.compassGravity(typedArray.getInt(R.styleable.MapView_compass_gravity, Gravity.TOP | Gravity.END));
            mapboxMapOptions.compassMargins(new int[]{(int) (typedArray.getDimension(R.styleable.MapView_compass_margin_left, DIMENSION_TEN_DP) * screenDensity)
                    , ((int) typedArray.getDimension(R.styleable.MapView_compass_margin_top, DIMENSION_TEN_DP * screenDensity))
                    , ((int) typedArray.getDimension(R.styleable.MapView_compass_margin_right, DIMENSION_TEN_DP * screenDensity))
                    , ((int) typedArray.getDimension(R.styleable.MapView_compass_margin_bottom, DIMENSION_TEN_DP * screenDensity))});
//
//            // Logo
            uiSettings.setLogoEnabled(typedArray.getBoolean(R.styleable.MapView_logo_visibility, true));
            uiSettings.setLogoGravity(typedArray.getInt(R.styleable.MapView_logo_gravity, Gravity.BOTTOM | Gravity.START));
            uiSettings.setLogoMargins((int) (typedArray.getDimension(R.styleable.MapView_logo_margin_left, DIMENSION_SIXTEEN_DP) * mScreenDensity)
                    , (int) (typedArray.getDimension(R.styleable.MapView_logo_margin_top, DIMENSION_SIXTEEN_DP) * mScreenDensity)
                    , (int) (typedArray.getDimension(R.styleable.MapView_logo_margin_right, DIMENSION_SIXTEEN_DP) * mScreenDensity)
                    , (int) (typedArray.getDimension(R.styleable.MapView_logo_margin_bottom, DIMENSION_SIXTEEN_DP) * mScreenDensity));
//
//            // Attribution
//            uiSettings.setAttributionEnabled(typedArray.getBoolean(R.styleable.MapView_attribution_visibility, true));
//            uiSettings.setAttributionGravity(typedArray.getInt(R.styleable.MapView_attribution_gravity, Gravity.BOTTOM));
//            uiSettings.setAttributionMargins((int) (typedArray.getDimension(R.styleable.MapView_attribution_margin_left, DIMENSION_SEVENTY_SIX_DP) * mScreenDensity)
//                    , (int) (typedArray.getDimension(R.styleable.MapView_attribution_margin_top, DIMENSION_SEVEN_DP) * mScreenDensity)
//                    , (int) (typedArray.getDimension(R.styleable.MapView_attribution_margin_right, DIMENSION_SEVEN_DP) * mScreenDensity)
//                    , (int) (typedArray.getDimension(R.styleable.MapView_attribution_margin_bottom, DIMENSION_SEVEN_DP) * mScreenDensity));
//
//            // User location
//            mMapboxMap.setMyLocationEnabled(typedArray.getBoolean(R.styleable.MapView_my_location_enabled, false));
        } finally {
            typedArray.recycle();
        }


        return mapboxMapOptions;
    }

    public MapboxMapOptions camera(CameraPosition cameraPosition) {
        this.cameraPosition = cameraPosition;
        return this;
    }

    public MapboxMapOptions accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public MapboxMapOptions styleUrl(String styleUrl) {
        style = styleUrl;
        return this;
    }

    public MapboxMapOptions debugActive(boolean enabled) {
        debugActive = enabled;
        return this;
    }

    public MapboxMapOptions minZoom(float minZoom) {
        this.minZoom = minZoom;
        return this;
    }

    public MapboxMapOptions maxZoom(float maxZoom) {
        this.maxZoom = maxZoom;
        return this;
    }

    public MapboxMapOptions compassEnabled(boolean enabled) {
        compassEnabled = enabled;
        return this;
    }

    public MapboxMapOptions compassGravity(int gravity) {
        compassGravity = gravity;
        return this;
    }

    public MapboxMapOptions compassMargins(int[] margins) {
        compassMargins = margins;
        return this;
    }

    public MapboxMapOptions logoEnabled(boolean enabled){
        logoEnabled  = enabled;
        return this
    }

    public MapboxMapOptions logoGravity(int gravity){
        logoGravity = gravity;
        return this;
    }

    public MapboxMapOptions logoMargins(int[]margins){
        logoMargins = margins;
        return this;
    }

    public MapboxMapOptions rotateGesturesEnabled(boolean enabled) {
        rotateGesturesEnabled = enabled;
        return this;
    }

    public MapboxMapOptions scrollGesturesEnabled(boolean enabled) {
        scrollGesturesEnabled = enabled;
        return this;
    }

    public MapboxMapOptions tiltGesturesEnabled(boolean enabled) {
        tiltGesturesEnabled = enabled;
        return this;
    }

    public MapboxMapOptions zoomControlsEnabled(boolean enabled) {
        zoomControlsEnabled = enabled;
        return this;
    }

    public MapboxMapOptions zoomGesturesEnabled(boolean enabled) {
        zoomGesturesEnabled = enabled;
        return this;
    }

    public CameraPosition getCamera() {
        return cameraPosition;
    }

    public float getMinZoom() {
        return minZoom;
    }

    public float getMaxZoom() {
        return maxZoom;
    }

    public boolean getCompassEnabled() {
        return compassEnabled;
    }

    public int getCompassGravity() {
        return compassGravity;
    }

    public int[] getCompassMargins() {
        return compassMargins;
    }

    public boolean getLogoEnabled() {
        return logoEnabled;
    }

    public int getLogoGravity() {
        return logoGravity;
    }

    public int[] getLogoMargins() {
        return logoMargins;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getStyle() {
        return style;
    }

    public boolean getRotateGesturesEnabled() {
        return rotateGesturesEnabled;
    }

    public boolean getScrollGesturesEnabled() {
        return scrollGesturesEnabled;
    }

    public boolean getTitltGesturesEnabeld() {
        return tiltGesturesEnabled;
    }

    public boolean getZoomControlsEnabled() {
        return zoomControlsEnabled;
    }

    public boolean getZoomGesturesEnabled() {
        return zoomGesturesEnabled;
    }

    public static final Parcelable.Creator<MapboxMapOptions> CREATOR
            = new Parcelable.Creator<MapboxMapOptions>() {
        public MapboxMapOptions createFromParcel(Parcel in) {
            return new MapboxMapOptions(in);
        }

        public MapboxMapOptions[] newArray(int size) {
            return new MapboxMapOptions[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(cameraPosition, flags);
        dest.writeByte((byte) (debugActive ? 1 : 0));
        dest.writeByte((byte) (compassEnabled ? 1 : 0));
        dest.writeByte((byte) (rotateGesturesEnabled ? 1 : 0));
        dest.writeByte((byte) (scrollGesturesEnabled ? 1 : 0));
        dest.writeByte((byte) (tiltGesturesEnabled ? 1 : 0));
        dest.writeByte((byte) (zoomControlsEnabled ? 1 : 0));
        dest.writeByte((byte) (zoomGesturesEnabled ? 1 : 0));
        dest.writeString(style);
        dest.writeString(accessToken);
    }
}