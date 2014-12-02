package de.tfh.gamecore.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Kann über den Zustand der Speicherung abgefragt werden
 *
 * @author W.Glanzer, 28.11.2014
 */
public class ProgressObject
{

  private final Set<IProgressListener> listeners = new HashSet<>();

  /**
   * Setzt den neuen Progress
   *
   * @param pProgress Progress der gesetzt werden soll
   */
  public synchronized void setProgress(double pProgress)
  {
    _fireProgressChanged(pProgress);
  }

  /**
   * Setzt, dass der Progress fertig ist
   */
  public synchronized void setFinished()
  {
    _fireProgressFinished();
    listeners.clear();
  }

  /**
   * Feuert, dass sich der Progress geändert hat
   *
   * @param pNewProgress Neuer Progress
   */
  private void _fireProgressChanged(double pNewProgress)
  {
    synchronized(listeners)
    {
      for(IProgressListener currListener : listeners)
        currListener.progressChanged(pNewProgress);
    }
  }

  /**
   * Feuert, dass der Progress fertig ist
   */
  private void _fireProgressFinished()
  {
    synchronized(listeners)
    {
      listeners.forEach(ProgressObject.IProgressListener::finished);
    }
  }

  /**
   * Fügt einen ProgressListener hinzu
   *
   * @param pListener ProgressListener, der hinzugefügt werden soll
   */
  public void addProgressListener(IProgressListener pListener)
  {
    synchronized(listeners)
    {
      listeners.add(pListener);
    }
  }

  /**
   * Entfernt einen ProgressListener hinzu
   *
   * @param pListener ProgressListener, der entfernt werden soll
   */
  public void removeProgressListener(IProgressListener pListener)
  {
    synchronized(listeners)
    {
      listeners.remove(pListener);
    }
  }

  /**
   * ProgressListener
   */
  public static interface IProgressListener
  {
    void progressChanged(double pNew);

    void finished();
  }
}
