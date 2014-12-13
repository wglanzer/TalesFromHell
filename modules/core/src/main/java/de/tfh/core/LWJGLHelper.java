package de.tfh.core;

/**
 * @author W.Glanzer, 13.12.2014
 */
public class LWJGLHelper
{

  /**
   * Gibt zurück, ob LWJGL verwendet werden kann
   *
   * @return <tt>true</tt>, wenn LWJGL verwendet werden kann, andernfalls <tt>false</tt>
   */
  public static boolean isLWJGLEnabled()
  {
    return System.getProperty("java.library.path") != null && System.getProperty("java.library.path").contains("\\natives");
  }

}
