package com.yhj.PDP.pib;

import java.util.*;

/**
 * This interface emulates the behaviour of PIB. More work is needed on this class.
 */
public interface PIB {
  public PRI getPRI(byte[] prid);
  public PRC getPRC(byte[] prcIndex);
  public PRC getPRC(byte[] prcIndex, int offset, int length);
  public void putPRC(byte[] prcIndex, PRC prc);
  public void putPRC(byte[] prcIndex, int offset, int length, PRC prc);
  public void putPRI(byte[] prid, PRI obj)
      throws InvalidPRIException, PRCNotFoundException;
  public void removePRI(byte[] prid) throws PRCNotFoundException;

  public Enumeration getPRCs();

  /**
   * Check if this PIB has the PRC identified by the given index.
   */
  public boolean hasPRC(byte[] prcIndex);
  public boolean hasPRC(byte[] prcIndex, int offset, int length);

  /**
   * Check if this PIB has the PRI identified by the given PRID.
   */
  public boolean hasPRI(byte[] prid);
  public boolean hasPRI(byte[] prid, int offset, int length);
}