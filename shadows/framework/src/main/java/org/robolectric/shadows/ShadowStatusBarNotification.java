package org.robolectric.shadows;

import static org.robolectric.shadow.api.Shadow.directlyOn;

import android.os.Build;
import android.service.notification.StatusBarNotification;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

/** A shadow implementation of {@link android.service.notification.StatusBarNotification}. */
@Implements(StatusBarNotification.class)
public class ShadowStatusBarNotification {
  @RealObject StatusBarNotification realNotification;
  private String key;

  /** Returns the key previously set in {@link #setKey}, otherwise calls getKey on real object. */
  @Implementation(minSdk = Build.VERSION_CODES.KITKAT_WATCH)
  public String getKey() {
    if (key != null) {
      return key;
    }
    return directlyOn(realNotification, StatusBarNotification.class).getKey();
  }

  /** Sets the value that will be returned by {@link #getKey()}. */
  public void setKey(String key) {
    this.key = key;
  }
}
