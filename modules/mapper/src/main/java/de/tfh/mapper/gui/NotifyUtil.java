package de.tfh.mapper.gui;

import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebNotificationPopup;

/**
 * Util fürs Notify im Mapper
 *
 * @author W.Glanzer, 14.12.2014
 */
public class NotifyUtil
{

  private static final int DISPLAY_TIME = 5000;

  /**
   * Zeigt eine Nachricht an
   *
   * @param pMessage Nachricht, die angezeigt werden soll
   */
  public static void info(String pMessage)
  {
    log(NotificationIcon.information, pMessage);
  }

  /**
   * Zeigt eine Error-Nachricht an
   *
   * @param pMessage Nachricht, die angezeigt werden soll
   */
  public static void error(String pMessage)
  {
    log(NotificationIcon.error, pMessage);
  }

  /**
   * Zeigt eine Error-Nachricht an
   *
   * @param pThrowable STacktrace, der angezeigt werden soll
   */
  public static void error(Throwable pThrowable)
  {
    log(NotificationIcon.error, pThrowable.getMessage());
  }

  /**
   * Zeigt eine Nachricht an
   *
   * @param pIcon    Icon das dazu passt
   * @param pMessage Nachricht, die angezeigt werden soll
   */
  public static void log(NotificationIcon pIcon, String pMessage)
  {
    final WebNotificationPopup notificationPopup = new WebNotificationPopup();
    notificationPopup.setIcon(pIcon);
    notificationPopup.setDisplayTime(DISPLAY_TIME);
    notificationPopup.setContent(pMessage);
    notificationPopup.setPreferredHeight(20);
    NotificationManager.showNotification(notificationPopup);
  }
}
