package de.tfh.datamodels.utils;

import de.tfh.datamodels.IDataModel;
import de.tfh.datamodels.TFHDataModelException;
import de.tfh.datamodels.registry.DefaultDataModelRegistry;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jetbrains.annotations.Nullable;

import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Schreibt ein Datenmodell als XML-Datei auf Platte
 *
 * @author W.Glanzer, 26.10.2014
 */
public abstract class DataModelIOUtil
{

  /**
   * Schreibt ein Datenmodell auf Platte
   *
   * @param pModel Datenmodell das geschrieben werden soll
   * @param pPath  Pfad der Datei, in der das Datenmodell geschrieben wird
   * @throws TFHDataModelException Wenn dabei ein Fehler aufgetreten ist
   */
  public static void writeDataModelXML(IDataModel pModel, String pPath) throws TFHDataModelException
  {
    try
    {
      Element ele = pModel.toXMLElement();
      Document doc = new Document(ele);

      XMLOutputter out = new XMLOutputter();
      out.setFormat(Format.getPrettyFormat());
      out.output(doc, new FileWriter(pPath));
    }
    catch(Exception e)
    {
      throw new TFHDataModelException(e, 7);
    }
  }

  /**
   * Schreibt ein Datenmodell auf einen Stream
   *
   * @param pModel  Datenmodell das geschrieben werden soll
   * @param pStream Stream, auf den geschrieben wird
   * @throws TFHDataModelException Wenn dabei ein Fehler aufgetreten ist
   */
  public static void writeDataModelXML(IDataModel pModel, OutputStream pStream) throws TFHDataModelException
  {
    try
    {
      Element ele = pModel.toXMLElement();
      Document doc = new Document(ele);

      XMLOutputter out = new XMLOutputter();
      out.setFormat(Format.getPrettyFormat());
      out.output(doc, pStream);
    }
    catch(Exception e)
    {
      throw new TFHDataModelException(e, 8);
    }
  }

  /**
   * Liest ein Datenmodell anhand eines InputStreams ein
   *
   * @param pStream  Stream, von dem das Datenmodell gelesen wird
   * @return Das IDataModel, oder <tt>null</tt> wenn nichts gefunden wird
   * @throws TFHDataModelException Wenn dabei ein Fehler aufgetreten ist
   */
  @Nullable
  public static IDataModel readDataModelFromXML(@Nullable InputStream pStream) throws TFHDataModelException
  {
    try
    {
      if(pStream == null)
        return null;

      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(pStream);

      if(doc != null)
      {
        Element root = doc.getRootElement();
        if(root != null)
        {
          IDataModel model = DefaultDataModelRegistry.getDefault().getDataModel(root.getName());
          if(model != null)
          {
            model.fromXMLElement(root);
            return model;
          }
        }
      }

      return null;
    }
    catch(Exception e)
    {
      throw new TFHDataModelException(e, 9);
    }
  }
}
