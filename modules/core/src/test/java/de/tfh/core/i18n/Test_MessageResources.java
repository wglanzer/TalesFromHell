package de.tfh.core.i18n;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Locale;
import java.util.Map;

/**
 * @author W.Glanzer, 14.11.2014
 */
public class Test_MessageResources
{

  @Test
  public void test_errorResources()
  {
    MessageResources res = new MessageResources(Locale.getDefault());
    Assert.assertNotNull(res);

    String def = res.getDefaultMessage();
    Assert.assertNotNull(def);

    Map<Integer, String> all = res.getAllMessages();
    Assert.assertTrue(all.size() > 0);
  }

}
