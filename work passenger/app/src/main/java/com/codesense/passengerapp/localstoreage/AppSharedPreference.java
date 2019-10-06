package com.codesense.passengerapp.localstoreage;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.codesense.passengerapp.R;

import javax.inject.Inject;

public class AppSharedPreference {

    private static final String ACCESS_TOKEN_KEY = "AccessTokenKey";
    private static final String USER_ID_KEY = "UserIDKey";
    private static final String USER_TYPE = "userType";
    private static final String MOBILE_NUMBER_KEY = "MobileNumberKey";
    private static final String OWNER_TYPE_ID_KEY = "OwnerTypeIdKey";
    private static final String OWNER_TYPE_KEY = "OwnerTypeKey";
    private static final String OTP_VERIFY_KEY = "OtpVerifyKey";
    private static final String USER_TYPE_KEY = "UserTypeKey";
    private static final String PERMISSION_KEY = "PermissionKey";
    private static final String NETWORK_STATUS_KEY = "NetworkStatusKey";
    private static final String LAST_LOCATION_LAT = "LastLocationLat";
    private static final String LAST_LOCATION_LONG = "LastLocationLong";
    private static final String USER_STATUS = "UserStatus";
    private static final String COUNTRY_DIAL_CODE_KEY = "CountryDialCodeKey";
    private static final String EMAIL_ID_KEY = "EmailIdKey";
    private static final String PROFILE_PICTURE_KEY = "ProfilePictureKey";
    private static final String SPEED_KEY = "SpeedKey";
    private static final String DEFAULT_VALUE = null;
    private SharedPreferences sharedPreferences;

    @Inject
    public AppSharedPreference(Context context) {
        this.sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public void saveAccessToken(String accessToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN_KEY, accessToken);
        editor.apply();
    }

    public String getAccessTokenKey() {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, DEFAULT_VALUE);
    }

    public void saveUserID(String userID) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID_KEY, userID);
        editor.apply();
    }

    public void saveUserType(String userType) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_TYPE, userType);
        editor.apply();
    }

    public String getUserID() {
        return sharedPreferences.getString(USER_ID_KEY, DEFAULT_VALUE);
    }

    public String getUserType() {
        return sharedPreferences.getString(USER_TYPE, DEFAULT_VALUE);
    }

    public void saveMobileNumber(String mobileNumber) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MOBILE_NUMBER_KEY, mobileNumber);
        editor.apply();
    }

    public String getMobileNumberKey() {
        return sharedPreferences.getString(MOBILE_NUMBER_KEY, null);
    }

    public void saveOwnerTypeId(int ownerTypeId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(OWNER_TYPE_ID_KEY, ownerTypeId);
        editor.apply();
    }

    public void saveOtpVerify(int otpVerify) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(OTP_VERIFY_KEY, otpVerify);
        editor.apply();
    }

    public int getOtpVerify() {
        return sharedPreferences.getInt(OTP_VERIFY_KEY, 0);
    }

    public int getOwnerTypeId() {
        return sharedPreferences.getInt(OWNER_TYPE_ID_KEY, -1);
    }

    public void saveOwnerType(String ownerType) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(OWNER_TYPE_KEY, ownerType);
        editor.apply();
    }

    public String getOwnerType() {
        return sharedPreferences.getString(OWNER_TYPE_KEY, null);
    }

    public void savePermission(String permission, boolean isFirstTime) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(permission, isFirstTime);
        editor.apply();
    }

    public boolean isUserIdAvailable() {
        return !TextUtils.isEmpty(getUserID());
    }

    public boolean isFirstTime(String permission) {
        return sharedPreferences.getBoolean(permission, true);
    }

    public void setNetworkStatus(String networkStatus) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NETWORK_STATUS_KEY, networkStatus);
        editor.apply();
    }

    public void setLastLocationLatitude(String latitude) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_LOCATION_LAT, latitude);
        editor.apply();
    }

    public void setLastLocationLong(String lng) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_LOCATION_LONG, lng);
        editor.apply();
    }

    public void setUserStatusOnline(boolean isOnline) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(USER_STATUS, isOnline);
        editor.apply();
    }

    public void setCountryDialCode(String countryDialCode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COUNTRY_DIAL_CODE_KEY, countryDialCode);
        editor.apply();
    }

    public void setEmailId(String emailId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL_ID_KEY, emailId);
        editor.apply();
    }

    public void setProfilePicture(String profilePicture) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PROFILE_PICTURE_KEY, profilePicture);
        editor.apply();
    }

    public void setSpeed(float speed) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(SPEED_KEY, speed);
        editor.apply();
    }

    public float getSpeed() {
        return sharedPreferences.getFloat(SPEED_KEY, 0f);
    }

    public String getCountryDialCode() {
        return sharedPreferences.getString(COUNTRY_DIAL_CODE_KEY, null);
    }

    public String getEmailId() {
        return sharedPreferences.getString(EMAIL_ID_KEY, null);
    }

    public String getProfilePicture() {
        return sharedPreferences.getString(PROFILE_PICTURE_KEY, null);
    }

    public boolean isUserStatusOnline() {
        return sharedPreferences.getBoolean(USER_STATUS, false);
    }

    public String getLastLocationLatitude() {
        return sharedPreferences.getString(LAST_LOCATION_LAT, null);
    }

    public String getLastLocationLng() {
        return sharedPreferences.getString(LAST_LOCATION_LONG, null);
    }

    public String getNetworkStatus() {
        return sharedPreferences.getString(NETWORK_STATUS_KEY, null);
    }

    public boolean isInterNetStatusUpdated() {
        return !TextUtils.isEmpty(getNetworkStatus());
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

}
