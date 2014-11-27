package de.tfh.mapper.gui.tablelayout;

/**
 * @author W.Glanzer, 27.11.2014
 */
public class TableLayoutHelper
{

  /**
   * Erstellt ein Tablelayout mit einer bestimmten Anzahl
   * an Columns und Rows. Alle Zellen erhalten die selbe, übergebene Eigenschaft
   *
   * @param pX     Anzahl an Columns
   * @param pY     Anzahl an Rows
   * @param pType  Typ der einzelnen Zellen
   * @return Neues Tablelayout
   */
  public static TableLayout createLayout(int pX, int pY, double pType)
  {
    double[] rows = new double[pY];
    double[] cols = new double[pX];

    for(int y = 0; y < pY; y++)
    {
      for(int x = 0; x < pX; x++)
        if(y == 0)
          cols[x] = pType;

      rows[y] = pType;
    }

    return new TableLayout(new double[][]{cols, rows});
  }
  
}
