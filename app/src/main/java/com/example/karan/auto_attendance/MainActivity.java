package com.example.karan.auto_attendance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.mobile.auth.core.DefaultSignInResultHandler;
import com.amazonaws.mobile.auth.core.signin.ui.buttons.SignInButton;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.auth.core.signin.ui.buttons.SignInButton;
import com.amazonaws.mobile.auth.ui.SignInActivity;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.auth.ui.AuthUIConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.IdentityProvider;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DynamoDBMapper dynamoDBMapper;
    private IdentityManager identityManager;
    private Button logout;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layout;
    private List<Week> weeksList;

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static PinpointManager pinpointManager;

    public static AuthUIConfiguration sAuthUIConfiguration =
            new AuthUIConfiguration.Builder()
                    .userPools(true)
                    .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (pinpointManager == null) {
            PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                    getApplicationContext(),
                    AWSMobileClient.getInstance().getCredentialsProvider(),
                    AWSMobileClient.getInstance().getConfiguration());

            pinpointManager = new PinpointManager(pinpointConfig);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String deviceToken =
                                InstanceID.getInstance(MainActivity.this).getToken(
                                        "271542907893",
                                        GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                        Log.e("NotError", deviceToken);
                        pinpointManager.getNotificationClient()
                                .registerGCMDeviceToken(deviceToken);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        rv = (RecyclerView)findViewById(R.id.container);
        rv.setHasFixedSize(true);

        LinearLayoutManager linear = new LinearLayoutManager(this);
        rv.setLayoutManager(linear);
        weeksList = new ArrayList<Week>();
        weeksList.add(new Week("WEEK 1", "January 4th"));
        weeksList.add(new Week("WEEK 2", "January 11th"));
        weeksList.add(new Week("WEEK 3", "January 18th"));
        weeksList.add(new Week("WEEK 4", "January 25th"));
        weeksList.add(new Week("WEEK 5", "February 1st"));
        weeksList.add(new Week("WEEK 6", "February 8th"));
        weeksList.add(new Week("WEEK 7", "February 15th"));
        weeksList.add(new Week("WEEK 8", "February 22nd"));
        weeksList.add(new Week("WEEK 9", "March 1st"));
        weeksList.add(new Week("WEEK 10", "March 8th"));
        weeksList.add(new Week("WEEK 11", "March 15th"));
        weeksList.add(new Week("WEEK 12", "March 22th"));

        adapter = new WeeksAdapter(this, weeksList);
        rv.setAdapter(adapter);

        logout = (Button)findViewById(R.id.button2);

        identityManager = IdentityManager.getDefaultIdentityManager();

        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();

        readItem();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // unregister notification receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(notificationReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register notification receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(notificationReceiver,
                new IntentFilter(PushService.ACTION_PUSH_NOTIFICATION));
    }

    private final BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG_TAG, "Received notification from local broadcast. Display it in a dialog.");

            Bundle data = intent.getBundleExtra(PushService.INTENT_SNS_NOTIFICATION_DATA);
            String message = PushService.getMessage(data);

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Push notification")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    };

    public void readItem() {
        new Thread(new Runnable() {
            @Override
            public void run() {
               AttendanceDO newsItem = dynamoDBMapper.load(
                       AttendanceDO.class, "100290458");
                Log.d("Item: ", newsItem.toString());
            }
        }).start();
    }

    public void onLogout(View view) {
        identityManager.signOut();
        identityManager.setUpToAuthenticate(this, new DefaultSignInResultHandler() {
            @Override
            public void onSuccess(Activity callingActivity, IdentityProvider provider) {
                Log.e("Success", "User signed in");
                callingActivity.finish();
            }

            @Override
            public boolean onCancel(Activity callingActivity) {
                return false;
            }
        });
        SignInActivity.startSignInActivity(this, sAuthUIConfiguration);
    }
}
