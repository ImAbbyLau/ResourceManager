package com.yhj.APDP.cops.objects.pr;

import java.io.*;
import java.util.*;
import com.yhj.APDP.cops.CopsException;
import com.yhj.APDP.cops.objects.*;
import com.yhj.APDP.cops.utils.pr.*;

public class NamedDecData extends CODecData {

  public static final int UNINITIALIZED = 0;
  public static final int INSTALL = 1;
  public static final int REMOVE  = 2;

  protected int type = UNINITIALIZED;
  protected short length; // more convenient for tracking the total length

  // Compulsory
  // a list of Bindings
  protected Vector installs = null;
  // a list of Remove Decisions. It is either PRID or PPRID
  protected Vector removes = null;

  /**
   * Construct an empty decision data object.
   */
  public NamedDecData() {
    super(NAMED_DEC_DATA);
    this.length = coh.clen;
    setLength(length);
  }

  /**
   * Construct this install decision object with the given PRID and EPD bindings.
   */
  public NamedDecData(Binding[] id) {
    super(NAMED_DEC_DATA);
    length = coh.clen;
    if (id != null) {
      this.type = INSTALL;
      this.installs = new Vector(id.length);
      for (int i = 0 ; i < id.length; i++) {
        this.installs.addElement(id[i]);
        this.length += id[i].length();
      }
    }
    setLength(length);
  }

  /**
   * Construct this removes decision object with the given PRID and PPRID
   */
  public NamedDecData(COPRPRID[] prids, COPRPPRID[] pprids) {
    super(NAMED_DEC_DATA);
    length = coh.clen;
    this.type = REMOVE;
    if (prids != null) {
      for (int i = 0; i < prids.length; i++) {
        this.removes.addElement(prids[i]);
        length += prids[i].totalLength();
      }
    }
    if (pprids != null) {
      for (int i = 0; i < pprids.length; i++) {
        this.removes.addElement(pprids[i]);
        length += pprids[i].totalLength();
      }
    }
    setLength(length);
  }

  /**
   * Parsing from an array of bytes.
   */
  public NamedDecData(COHeader coh, byte[] data) throws CopsException {
    super(coh);
    parseData(data);
  }

  /**
   * Parse the given data.
   * This object is an install decision if it starts with PRID and EPD.
   * Other combinations of PRIDs and PPRIDs will lead to remove decision.
   */
  protected void parseData(byte[] data) throws CopsException {
    CopsprProtObjParser parser = new CopsprProtObjParser(data);
    COPRObject obj = parser.nextObject();
    if (obj == null) return;
    length = coh.clen;
    length += obj.totalLength();
    // determine the type.
    if (obj.getSNum() == COPRObject.PPRID) {
      // removes decision. since starts with PPRID
      this.type = REMOVE;
      this.removes = new Vector();
      this.removes.addElement(obj);
      getRemoves(parser);
      return;
    } else if (obj.getSNum() != COPRObject.PRID) {
      throw new CopsException("Invalid NamedDecData obj. Does not start with PRID or PPRID");
    }
    COPRPRID prid = (COPRPRID) obj;

    obj = parser.nextObject();
    if (obj == null) {
      // a remove decision with just one prid
      this.removes = new Vector();
      this.removes.addElement(prid);
      setLength(length);
      return;
    }
    length += obj.totalLength();

    switch (obj.getSNum()) {
    case COPRObject.EPD:
      // installs data
      this.type = INSTALL;
      this.installs = new Vector();
      this.installs.addElement(new Binding(prid, (COPREPD) obj));
      while ((obj = parser.nextObject()) != null) {
        if (obj.getSNum() != COPRObject.PRID) {
          String str = "Invalid NamedDecData obj. Install starts with: "+obj.getSNum();
          throw new CopsException(str);
        }
        prid = (COPRPRID) obj;
        obj = parser.nextObject();
        if (obj.getSNum() != COPRObject.EPD) {
          String str = "Invalid NamedDecData obj. Install expect EPD, get: "+obj.getSNum();
          throw new CopsException(str);
        }
        this.installs.addElement(new Binding(prid, (COPREPD) obj));
        length += prid.totalLength() + obj.totalLength();
      }
      setLength(length);
      break;
    case COPRObject.PRID:
      // falls through
    case COPRObject.PPRID:
      // removes data
      this.type = REMOVE;
      this.removes = new Vector();
      this.removes.addElement(prid);
      this.removes.addElement(obj);
      getRemoves(parser);
      break;
    default:
      throw new CopsException("Invalid NamedDecData obj. Get: "+obj.getSNum());
    }
  }

  /**
   * This method will parse all the remove decisions from a given parser.
   * The parser must contain either a PRID or a PPRID
   */
  protected void getRemoves(CopsprProtObjParser parser) throws CopsException {
    if (this.removes == null) {
      this.removes = new Vector();
      this.type = REMOVE;
    }
    COPRObject obj = null;
    while ((obj = parser.nextObject()) != null) {
      switch (obj.getSNum()) {
      case COPRObject.PRID:
        // falls through
      case COPRObject.PPRID:
        this.removes.addElement(obj);
        length += obj.totalLength();
      default:
        String s = "Invalid NamedDecData object. Removes expect PRID or PPRID";
        s += " get: " + obj.getSNum();
        throw new CopsException(s);
      }
    }
    setLength(length);
  }

  public Enumeration getInstallDecisions() {
    return installs.elements();
  }

  public Enumeration getRemoveDecisions() {
    return removes.elements();
  }

  public short length() {
    return length;
  }

  public int getType() {
    return type;
  }

  /**
   * Add an install binding to this object.
   * The binding is not added if this object has been created with REMOVE type.
   */
  public void addInstall(COPRPRID prid, COPREPD epd) {
    if (this.type == REMOVE) return; // ignore if set to remove
    if (this.installs == null) installs = new Vector();
    this.type = INSTALL;
    this.installs.addElement(new Binding(prid, epd));
    length += prid.totalLength() + epd.totalLength();
    setLength(length);
  }

  /**
   * Add an remove decision to this object.
   * The binding is not added if this object has been created with INSTALL type.
   */
  public void addRemove(COPRPRID prid) {
    if (this.type == INSTALL) return; // ignore if already set to install
    if (this.removes == null) removes = new Vector();
    this.type = REMOVE;
    this.removes.addElement(prid);
    length += prid.totalLength();
    setLength(length);
  }

  /**
   * Add an remove decision to this object.
   * The binding is not added if this object has been created with INSTALL type.
   */
  public void addRemove(COPRPPRID pprid) {
    if (this.type == INSTALL) return; // ignore if already set to install
    if (this.removes == null) removes = new Vector();
    this.type = REMOVE;
    this.removes.addElement(pprid);
    length += pprid.totalLength();
    setLength(length);
  }

  /**
   * Write this object to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    super.writeTo(os);
    switch (type) {
    case INSTALL:
      writeInstallsTo(os); break;
    case REMOVE:
      writeRemovesTo(os); break;
    default:
      break;
    }
  }

  /**
   * Write the installs decision to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeInstallsTo(OutputStream os) throws IOException {
    if (installs == null) return;
    for (int i = 0; i < installs.size(); i++) {
      Binding binding = (Binding) installs.elementAt(i);
      binding.prid.writeTo(os);
      binding.epd.writeTo(os);
    }
  }

  /**
   * Write the removes decision to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeRemovesTo(OutputStream os) throws IOException {
    if (removes == null) return;
    for (int i = 0; i < installs.size(); i++) {
      ((COPRObject) removes.elementAt(i)).writeTo(os);
    }
  }

  public String toString() {
    StringBuffer result = new StringBuffer("NamedDecData: ");
    result.append(super.toString());
    if (installs != null) {
      for (int i = 0; i < installs.size(); i++) {
        result.append(";installs[").append(i).append("]: ").append(installs.elementAt(i));
      }
    }
    if (removes != null) {
      for (int i = 0; i < removes.size(); i++) {
        result.append(";removes[").append(i).append("]: ").append(removes.elementAt(i));
      }
    }

    return result.toString();
  }
}