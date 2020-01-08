package org.robolectric.shadows;

import static com.google.common.truth.Truth.assertThat;
import static org.robolectric.Shadows.shadowOf;

import android.app.Notification;
import android.os.Build;
import android.os.Process;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Implementation;

/** Unit tests for {@link ShadowStatusBarNotification}. */
@RunWith(AndroidJUnit4.class)
public class ShadowStatusBarNotificationTest {
  private StatusBarNotification statusBarNotification;
  private ShadowStatusBarNotification shadowStatusBarNotification;

  private static StatusBarNotification createNotification() {
    Notification notification =
        new Notification.Builder(ApplicationProvider.getApplicationContext())
            .setContentTitle("TestTitle")
            .setContentText("Test Text")
            .build();
    return new StatusBarNotification(
        "TestPackage",
        "TestPackage.app_ops",
        1 /* id */,
        "notification_tag",
        UserHandle.myUserId() /* uid */,
        1 /* pid */,
        0 /* score */,
        notification,
        Process.myUserHandle(),
        System.currentTimeMillis());
  }

  @Before
  public void setUp() {
    statusBarNotification = createNotification();
    shadowStatusBarNotification = shadowOf(statusBarNotification);
  }

  @Implementation(minSdk = Build.VERSION_CODES.KITKAT_WATCH)
  @Test
  public void setKey() {
    assertThat(statusBarNotification.getKey()).isEqualTo("0|TestPackage|1|notification_tag|0");
    shadowStatusBarNotification.setKey("NewKey");
    assertThat(statusBarNotification.getKey()).isEqualTo("NewKey");
  }

  @Implementation(minSdk = Build.VERSION_CODES.KITKAT_WATCH)
  @Test
  public void getKey() {
    assertThat(shadowStatusBarNotification.getKey())
        .isEqualTo("0|TestPackage|1|notification_tag|0");
    assertThat(statusBarNotification.getKey()).isEqualTo("0|TestPackage|1|notification_tag|0");
  }
}
