package com.yhj.PEP.pib;

import java.util.*;
import com.yhj.PEP.cops.*;

/**
 * This class emulates the behaviour of PIB. More work is needed on this class.
 */
public class PIBImpl implements PIB {

  /**
   * A map of (PRCid, PRC). Where PRCid is a sequence of bytes.
   * Current implementation uses the class String to represent the key (PRCid).
   * This may not be optimal, but certainly easiest to implement.
   */
  protected Hashtable prcs;

  public PIBImpl() {
    this.prcs = new Hashtable();
  }

  public PRC getPRC(byte[] prcIndex) {
    return getPRC(prcIndex, 0, prcIndex.length);
  }

  public PRC getPRC(byte[] prcIndex, int offset, int length) {
    String key = new String(prcIndex, offset, length);
    return (PRC) this.prcs.get(key);
  }

  public void putPRC(byte[] prcIndex, PRC prc) {
    putPRC(prcIndex, 0, prcIndex.length, prc);
  }

  public void putPRC(byte[] prcIndex, int offset, int length, PRC prc) {
    String key = new String(prcIndex, offset, length);
    this.prcs.put(key, prc);
  }

  public PRI getPRI(byte[] prid) {
    if (prid == null) return null;
    PRC prc = getPRC(prid, 0, prid.length - 1);
    if (prc == null) return null;
    return prc.getPRI(prid[prid.length - 1]);
  }

  public void putPRI(byte[] prid, PRI obj)
      throws InvalidPRIException, PRCNotFoundException {
    PRC prc = getPRC(prid, 0, prid.length - 1);
    if (prc == null) throw new PRCNotFoundException();
    prc.putPRI(prid[prid.length - 1], obj);
  }

  public void removePRI(byte[] prid) throws PRCNotFoundException {
    PRC prc = getPRC(prid, 0, prid.length - 1);
    if (prc == null) throw new PRCNotFoundException();
    prc.removePRI(prid[prid.length-1]);
  }

  public Enumeration getPRCs() {
    return prcs.elements();
  }

  public boolean hasPRC(byte[] prcIndex) {
    return hasPRC(prcIndex, 0, prcIndex.length);
  }
  public boolean hasPRC(byte[] prcIndex, int offset, int length) {
    return prcs.containsKey(new String(prcIndex, offset, length));
  }

  public boolean hasPRI(byte[] prid) {
    return hasPRI(prid, 0, prid.length);
  }
  public boolean hasPRI(byte[] prid, int offset, int length) {
    PRC prc = (PRC) prcs.get(new String(prid, offset, length - 1));
    if (prc == null) return false;
    return prc.hasPRI(prid[length]);
  }
}