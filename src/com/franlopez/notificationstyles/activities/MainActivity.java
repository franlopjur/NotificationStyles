package com.franlopez.notificationstyles.activities;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.franlopez.notificationstyles.R;
import com.franlopez.notificationstyles.commons.NotificationsConstants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    private Button mBtnNormal;
    private Button mBtnNormalWithActions;
    private Button mBtnBigText;
    private Button mBtnBigPicture;
    private Button mBtnInbox;
    private Button mBtnCustomView;

    private static NotificationManager mNotificationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
   
        getViews();
        init();
    }

    private void init() {

    	mBtnNormal.setOnClickListener(this);
    	mBtnNormalWithActions.setOnClickListener(this);
    	mBtnBigPicture.setOnClickListener(this);
    	mBtnBigText.setOnClickListener(this);
    	mBtnInbox.setOnClickListener(this);
    	mBtnCustomView.setOnClickListener(this);
	}

	private void getViews() {
		
    	mBtnNormal = (Button) findViewById(R.id.btn__main_normal);
    	mBtnNormalWithActions = (Button) findViewById(R.id.btn__main_normal_with_actions);
    	mBtnBigText = (Button) findViewById(R.id.btn__main_big_text);
    	mBtnBigPicture = (Button) findViewById(R.id.btn__main_big_picture);
    	mBtnInbox = (Button) findViewById(R.id.btn__main_inbox);
    	mBtnCustomView = (Button) findViewById(R.id.btn__main_custom_view);
	}

	public void setNormalStyle(View view) {
        new CreateNotification(NotificationsConstants.NORMAL).execute();
    }
    
    public void setNormalWithActionsStyle(View view) {
        new CreateNotification(NotificationsConstants.NORMAL_WITH_ACTIONS).execute();
    }

    public void setBigTextStyle(View view) {
        new CreateNotification(NotificationsConstants.BIG_TEXT_STYLE).execute();
    }

    public void setBigPictureStyle(View view) {
        new CreateNotification(NotificationsConstants.BIG_PICTURE_STYLE).execute();
    }

    public void setInboxStyle(View view) {
        new CreateNotification(NotificationsConstants.INBOX_STYLE).execute();
    }

    public void setCustomView(View view) {
        new CreateNotification(NotificationsConstants.CUSTOM_VIEW).execute();
    }

    /**
     * Notification AsyncTask to create and return the
     * requested notification.
     */
    public class CreateNotification extends AsyncTask<Void, Void, Void> {

        int style = NotificationsConstants.NORMAL;

        /**
         * Main constructor for AsyncTask that accepts the parameters below.
         */
        public CreateNotification(int style) {
            this.style = style;
        }

        /**
         * Creates the notification object.
         */
        @Override
        protected Void doInBackground(Void... params) {
            Notification noti = new Notification();

            switch (style)
            {
                case NotificationsConstants.NORMAL:
                    noti = setNormalNotification();
                    break;
                    
                case NotificationsConstants.NORMAL_WITH_ACTIONS:
                    noti = setNormalWithActionsNotification();
                    break;
                    
                case NotificationsConstants.BIG_TEXT_STYLE:
                    noti = setBigTextStyleNotification();
                    break;

                case NotificationsConstants.BIG_PICTURE_STYLE:
                    noti = setBigPictureStyleNotification();
                    break;

                case NotificationsConstants.INBOX_STYLE:
                    noti = setInboxStyleNotification();
                    break;

                case NotificationsConstants.CUSTOM_VIEW:
                    noti = setCustomViewNotification();
                    break;

            }

            noti.defaults |= Notification.DEFAULT_LIGHTS;
            noti.defaults |= Notification.DEFAULT_VIBRATE;
            noti.defaults |= Notification.DEFAULT_SOUND;

            noti.flags |= Notification.FLAG_ONLY_ALERT_ONCE;

            mNotificationManager.notify(0, noti);

            return null;

        }
    }

    /**
     * Normal Notification
     *
     * @return Notification
     */
    private Notification setNormalNotification() {
        Bitmap remote_picture = null;

        try {
            remote_picture = BitmapFactory.decodeStream((InputStream) new URL(NotificationsConstants.URL_IMAGE).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent resultIntent = new Intent(this, MainActivity.class);

        PendingIntent resultPendingIntent =
            PendingIntent.getActivity(
            this,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );

        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .setLargeIcon(remote_picture)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(getResources().getString(R.string.notification_normal_title_normal))
                .setContentText(getResources().getString(R.string.notification_normal_content_normal)).build();
    }
    
    /**
     * Normal with Actions Notification
     *
     * @return Notification
     */
    private Notification setNormalWithActionsNotification() {
        Bitmap remote_picture = null;

        try {
            remote_picture = BitmapFactory.decodeStream((InputStream) new URL(NotificationsConstants.URL_IMAGE).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent resultIntent = new Intent(this, MainActivity.class);
        
        Intent resultIntentAction1 = new Intent(this, ActionsActivity.class);
        resultIntentAction1.setAction("Action 1");
        
        Intent resultIntentAction2 = new Intent(this, ActionsActivity.class);
        resultIntentAction2.setAction("Action 2");

        PendingIntent resultPendingIntent =
            PendingIntent.getActivity(
            this,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );
        
        PendingIntent resultPendingIntentAction1 =
                PendingIntent.getActivity(
                this,
                0,
                resultIntentAction1,
                PendingIntent.FLAG_UPDATE_CURRENT
            );
        
        PendingIntent resultPendingIntentAction2 =
                PendingIntent.getActivity(
                this,
                0,
                resultIntentAction2,
                PendingIntent.FLAG_UPDATE_CURRENT
            );

        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .setLargeIcon(remote_picture)
                .setContentIntent(resultPendingIntent)
                .addAction(R.drawable.ic_launcher, getResources().getString(R.string.notification_normal_with_actions_action_one), resultPendingIntentAction1)
                .addAction(R.drawable.ic_launcher, getResources().getString(R.string.notification_normal_with_actions_action_two), resultPendingIntentAction2)
                .setContentTitle(getResources().getString(R.string.notification_normal_with_actions_title_normal))
                .setContentText(getResources().getString(R.string.notification_normal_with_actions_content_normal)).build();
    }

    /**
     * Big Text Style Notification
     *
     * @return Notification
     */
    private Notification setBigTextStyleNotification() {
        Bitmap remote_picture = null;

        // - Creamos el estilo de la notificación, en este caso es de tipo BigTextStyle
        NotificationCompat.BigTextStyle notiStyle = new NotificationCompat.BigTextStyle();
        notiStyle.setBigContentTitle(getResources().getString(R.string.notification_big_text_title_expanded));
        notiStyle.setSummaryText(getResources().getString(R.string.notification_big_text_summary_expanded));

        try {
            remote_picture = BitmapFactory.decodeStream((InputStream) new URL(NotificationsConstants.URL_IMAGE).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Añadimos el texto de mayor tamaño al estilo
        CharSequence bigText = getResources().getString(R.string.notification_big_text_big_text);
        notiStyle.bigText(bigText);

        Intent resultIntent = new Intent(this, MainActivity.class);

        PendingIntent resultPendingIntent =
            PendingIntent.getActivity(
            this,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );

        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .setLargeIcon(remote_picture)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(getResources().getString(R.string.notification_big_text_title_normal))
                .setContentText(getResources().getString(R.string.notification_big_text_content_normal))
                .setStyle(notiStyle).build();
    }

    /**
     * Big Picture Style Notification
     *
     * @return Notification
     */
    private Notification setBigPictureStyleNotification() {
        Bitmap picture = null;

        // - Creamos el estilo de la notificación, en este caso es de tipo Big Picture
        NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
        notiStyle.setBigContentTitle(getResources().getString(R.string.notification_big_picture_title_expanded));
        notiStyle.setSummaryText(getResources().getString(R.string.notification_big_picture_summary_expanded));

        try {
            picture = BitmapFactory.decodeStream((InputStream) new URL(NotificationsConstants.URL_IMAGE).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Añadimos la imagen al estilo
        notiStyle.bigPicture(picture);

        Intent resultIntent = new Intent(this, MainActivity.class);

        PendingIntent resultPendingIntent =
            PendingIntent.getActivity(
            this,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );
        
        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .setLargeIcon(picture)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(getResources().getString(R.string.notification_big_picture_title_normal))
                .setContentText(getResources().getString(R.string.notification_big_picture_content_normal))
                .setStyle(notiStyle).build();
    }

    /**
     * Inbox Style Notification
     *
     * @return Notification
     */
    private Notification setInboxStyleNotification() {
        Bitmap remote_picture = null;

        // - Creamos el estilo de la notificación, en este caso es de tipo InboxStyle
        NotificationCompat.InboxStyle notiStyle = new NotificationCompat.InboxStyle();
        notiStyle.setBigContentTitle(getResources().getString(R.string.notification_inbox_title_normal));

        // Añadimos múltiples líneas
        for (int i = 0; i < 8; i++) {
            notiStyle.addLine(getResources().getString(R.string.notification_inbox_line) + i);
        }
        notiStyle.setSummaryText(getResources().getString(R.string.notification_inbox_content_normal));

        try {
            remote_picture = BitmapFactory.decodeStream((InputStream) new URL(NotificationsConstants.URL_IMAGE).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent resultIntent = new Intent(this, MainActivity.class);

        PendingIntent resultPendingIntent =
            PendingIntent.getActivity(
            this,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );
        
        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .setLargeIcon(remote_picture)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(getResources().getString(R.string.notification_inbox_title_expanded))
                .setContentText(getResources().getString(R.string.notification_inbox_summary_expanded))
                .setStyle(notiStyle).build();
    }

    /**
     * Custom View Notification
     *
     * @return Notification
     */
    @SuppressLint("NewApi")
	private Notification setCustomViewNotification() {

    	Intent resultIntent = new Intent(this, MainActivity.class);
    	
        PendingIntent resultPendingIntent =
            PendingIntent.getActivity(
            this,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );
        
        // Creamos el remote view y seteamos el bigContentView
        RemoteViews expandedView = new RemoteViews(this.getPackageName(), R.layout.notification_custom_view);
        expandedView.setTextViewText(R.id.text_view, getResources().getString(R.string.notification_custom_view_inside));

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(getResources().getString(R.string.notification_custom_view_title_normal)).build();
        
    	if(android.os.Build.VERSION.SDK_INT >= 16) 
	        notification.bigContentView = expandedView;
    	else
    		Toast.makeText(MainActivity.this, getResources().getString(R.string.notification_custom_view_toast), Toast.LENGTH_SHORT).show();
    	
        return notification;
    }

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
			case R.id.btn__main_normal:
				setNormalStyle(view);
				break;
			case R.id.btn__main_normal_with_actions:
				setNormalWithActionsStyle(view);
				break;
			case R.id.btn__main_big_text:
				setBigTextStyle(view);
				break;
			case R.id.btn__main_big_picture:
				setBigPictureStyle(view);
				break;
			case R.id.btn__main_inbox:
				setInboxStyle(view);
				break;
			case R.id.btn__main_custom_view:
				setCustomView(view);
				break;
		default:
			break;
		}
	}

}
