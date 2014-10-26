package de.tfh.core.i18n;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.utils.ExceptionUtil;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Locale;
import java.util.Map;

/**
 * Tested, ob die Resourcen geladen werden können (für Exceptions)
 *
 * @author W.Glanzer, 26.10.2014
 */
public class Test_ErrorResources
{

  @Test
  public void test_errorResources()
  {
    ExceptionResources res = new ExceptionResources(Locale.getDefault());
    Assert.assertNotNull(res);

    String def = res.getDefaultMessage();
    Assert.assertNotNull(def);

    Map<Integer, String> all = res.getAllMessages();
    Assert.assertTrue(all.size() > 0);

    TFHException ex = new TFHException(ExceptionResources.DEFAULT_NO_MESSAGE);
    String exp = ExceptionUtil.parseErrorMessage(res.getDefaultMessage(), ExceptionResources.DEFAULT_NO_MESSAGE);
    Assert.assertEquals(exp, ex.getMessage());
  }

}
