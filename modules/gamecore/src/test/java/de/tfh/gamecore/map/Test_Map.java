package de.tfh.gamecore.map;

import de.tfh.core.exceptions.TFHException;
import de.tfh.datamodels.StaticDataModelRegistrator;
import de.tfh.datamodels.TFHDataModelException;
import de.tfh.datamodels.models.ChunkDataModel;
import de.tfh.datamodels.models.MapDescriptionDataModel;
import de.tfh.datamodels.registry.DefaultDataModelRegistry;
import de.tfh.datamodels.registry.IDataModelRegistry;
import de.tfh.datamodels.utils.DataModelIOUtil;
import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Tested die Grundfunktionen der Map, wie das korrekte
 * Speichern und Laden
 *
 * @author W.Glanzer, 22.11.2014
 */
public class Test_Map
{

  private File file;

  @Test
  public void test_load() throws IOException, TFHDataModelException
  {
    StaticDataModelRegistrator.registerAll(false);
    file = File.createTempFile("test", ".zip");

    IDataModelRegistry reg = DefaultDataModelRegistry.getDefault();

    ChunkDataModel model = (ChunkDataModel) reg.newInstance(ChunkDataModel.class);
    model.x = 0;
    model.y = 0;
    model.tiles = new Long[]{1L, 2L, 4L, 8L};

    ChunkDataModel model2 = (ChunkDataModel) reg.newInstance(ChunkDataModel.class);
    model2.x = 1;
    model2.y = 0;
    model2.tiles = new Long[]{32L, 64L, 128L, 1L};

    ChunkDataModel model3 = (ChunkDataModel) reg.newInstance(ChunkDataModel.class);
    model3.x = 1;
    model3.y = 1;
    model3.tiles = new Long[]{255L, 155L, 65L};

    MapDescriptionDataModel mapDesc = (MapDescriptionDataModel) reg.newInstance(MapDescriptionDataModel.class);
    mapDesc.chunksX = 2;
    mapDesc.chunksY = 2;
    mapDesc.tilesPerChunkY = 2;
    mapDesc.tilesPerChunkX = 2;
    mapDesc.tileWidth = 32;
    mapDesc.tileHeight = 32;

    ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(file));

    outputStream.putNextEntry(new ZipEntry("chunks/00.chunk"));
    DataModelIOUtil.writeDataModelXML(model, outputStream);
    outputStream.closeEntry();

    outputStream.putNextEntry(new ZipEntry("chunks/10.chunk"));
    DataModelIOUtil.writeDataModelXML(model2, outputStream);
    outputStream.closeEntry();

    outputStream.putNextEntry(new ZipEntry("chunks/11.chunk"));
    DataModelIOUtil.writeDataModelXML(model3, outputStream);
    outputStream.closeEntry();

    outputStream.putNextEntry(new ZipEntry("map.xml"));
    DataModelIOUtil.writeDataModelXML(mapDesc, outputStream);
    outputStream.closeEntry();

    URL urlTiles = Test_Map.class.getResource("static/tiles.png");
    File file1 = new File(urlTiles.getPath());
    outputStream.putNextEntry(new ZipEntry("tiles.png"));
    IOUtils.copy(new FileInputStream(file1), outputStream);
    outputStream.closeEntry();

    outputStream.flush();
    outputStream.close();
  }

  @Test
  public void test_LoadMap() throws TFHException
  {
    Map map = new Map(file);
    Assert.assertTrue(map.getChunkCountX() == 2);
    Assert.assertTrue(map.getChunkCountY() == 2);
    Assert.assertTrue(map.getTileHeight() == 32);
    Assert.assertTrue(map.getTileWidth() == 32);
    Assert.assertTrue(map.getTilesPerChunkX() == 2);
    Assert.assertTrue(map.getTilesPerChunkY() == 2);

    Chunk c00 = map.getChunk(0, 0);
    Assert.assertNotNull(c00);
    Assert.assertTrue(c00.getLayers(true).size() == 4);
    Assert.assertTrue(c00.getTilesOn(0, 0).length == 4);
    Assert.assertTrue(c00.getTilesOn(0, 0)[0].getGraphicID() == 1L);
    Assert.assertTrue(c00.getTilesOn(1, 0)[0].getGraphicID() == 2L);
    Assert.assertTrue(c00.getTilesOn(0, 1)[0].getGraphicID() == 4L);
    Assert.assertTrue(c00.getTilesOn(1, 1)[0].getGraphicID() == 8L);

    Chunk c11 = map.getChunk(1, 1);
    Assert.assertNotNull(c11);
    Assert.assertTrue(c11.getTilesOn(1, 1)[0] == null);
    Assert.assertTrue(c11.getTilesOn(1, 1)[1] == null);
    Assert.assertTrue(c11.getTilesOn(1, 1)[2] == null);
    Assert.assertTrue(c11.getTilesOn(1, 1)[3] == null);
  }

}
