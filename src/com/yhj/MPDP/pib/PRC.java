package com.yhj.MPDP.pib;

import java.util.*;

/**
 * This is an interface for a PRC.
 */
public interface PRC {
  public PRI getPRI(byte prid);
  public Class getTemplate();
  public byte[] getIndex();
  public void putPRI(byte prid, PRI obj) throws InvalidPRIException;
  public void removePRI(byte prid);

  public Enumeration getPRIs();
  public boolean hasPRI(byte prid);
  public int countPRIs();
}