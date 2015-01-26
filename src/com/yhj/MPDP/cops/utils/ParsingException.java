package com.yhj.MPDP.cops.utils;

import com.yhj.MPDP.cops.CopsException;
import com.yhj.MPDP.cops.objects.pr.*;

/**
 * This exception indicates that there is an error during parsing COPS
 * objects or messages.
 * It may contain an Object so that the catcher may examine the object.
 */
public class ParsingException extends CopsException {

  Object object = null;

  public ParsingException() {}
  public ParsingException(String s) {
    super(s);
  }
  public ParsingException(String s, COPRObject obj) {
    super(s);
    this.object = obj;
  }

  /**
   * Get the object contained in the exception.
   */
  public Object getObject() {
    return object;
  }
}