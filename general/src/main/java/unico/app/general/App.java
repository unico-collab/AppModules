package unico.app.general;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;
import androidx.preference.PreferenceManager;
import es.dmoral.toasty.Toasty;

public class App {
    //Constants
    private static final int TYPE_WIFI = 0;
    private static final int TYPE_MOBILE = 1;
    private static final int TYPE_NONE = 3;


    public static final int PERMISSIONS_REQUEST_CODE = 4;

    private static int networkType = 3;

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    networkType = TYPE_WIFI;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    networkType = TYPE_MOBILE;
                }
            } else {
                networkType = TYPE_NONE;
            }
        }
        return false;
    }

    public static int getNetworkType() {
        return networkType;
    }

    public static void showSuccess(Context context, String message) {
        Toasty.success(context, message).show();
    }

    public static void showInfo(Context context, String message) {
        Toasty.info(context, message).show();
    }

    public static void showError(Context context, String message) {
        Toasty.error(context, message).show();
    }

    public static void showNormal(Context context, String message) {
        Toasty.normal(context, message).show();
    }

    public static void showWarning(Context context, String message) {
        Toasty.warning(context, message).show();
    }

    public static void copyToClipboard(Context context, String text) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(context.getString(R.string.app_name), text);
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(clip);
        }
    }

    public static boolean checkPermission(Context context, String permission) {
        int code = ActivityCompat.checkSelfPermission(context, permission);
        return code == PackageManager.PERMISSION_GRANTED;
    }

    public static String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static void requestPermissions(Context context, String[] permissions) {
        ActivityCompat.requestPermissions((AppCompatActivity) context, permissions, PERMISSIONS_REQUEST_CODE);
    }

    public static SharedPreferences getDefaultSharedPreferences(Context context){
       return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferences.Editor editor(Context context){
        return getDefaultSharedPreferences(context).edit();
    }
    public static boolean isFirstLaunch(Context context){
        return getDefaultSharedPreferences(context).getBoolean("first_launch", true);
    }

    public static void setFirstLaunch(Context context, boolean bool){
       getDefaultSharedPreferences(context).edit().putBoolean("first_launch", bool).apply();
    }

    public static String getAppUrl(String packageName){
        return "http://play.google.com/store/apps/details?id=" + packageName;
    }

    public static void shareApp(Context context, String chooserTitle, String message){
        try {
            ShareCompat.IntentBuilder.from((AppCompatActivity)context)
                    .setType("text/plain")
                    .setChooserTitle(chooserTitle)
                    .setText(message)
                    .startChooser();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void openGooglePlay(Context context, String packageName){
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        }
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    public static void emailIntent(Context context, String mailto, String subject, String text, String chooserTitle){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + mailto));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(emailIntent, chooserTitle));
    }

    public static String getClassName(){
        return "";
    }

}
