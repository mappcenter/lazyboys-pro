/**
 * Autogenerated by Thrift Compiler (0.9.0-dev)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package libs;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tag implements org.apache.thrift.TBase<Tag, Tag._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Tag");

  private static final org.apache.thrift.protocol.TField TAG_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("tagID", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TAG_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("tagName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField VIEW_COUNTS_FIELD_DESC = new org.apache.thrift.protocol.TField("viewCounts", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField DATE_ADD_FIELD_DESC = new org.apache.thrift.protocol.TField("dateAdd", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField DATE_UPDATE_FIELD_DESC = new org.apache.thrift.protocol.TField("dateUpdate", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TagStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TagTupleSchemeFactory());
  }

  public String tagID; // required
  public String tagName; // required
  public long viewCounts; // required
  public String dateAdd; // required
  public String dateUpdate; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TAG_ID((short)1, "tagID"),
    TAG_NAME((short)2, "tagName"),
    VIEW_COUNTS((short)3, "viewCounts"),
    DATE_ADD((short)4, "dateAdd"),
    DATE_UPDATE((short)5, "dateUpdate");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TAG_ID
          return TAG_ID;
        case 2: // TAG_NAME
          return TAG_NAME;
        case 3: // VIEW_COUNTS
          return VIEW_COUNTS;
        case 4: // DATE_ADD
          return DATE_ADD;
        case 5: // DATE_UPDATE
          return DATE_UPDATE;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __VIEWCOUNTS_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TAG_ID, new org.apache.thrift.meta_data.FieldMetaData("tagID", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TAG_NAME, new org.apache.thrift.meta_data.FieldMetaData("tagName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.VIEW_COUNTS, new org.apache.thrift.meta_data.FieldMetaData("viewCounts", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.DATE_ADD, new org.apache.thrift.meta_data.FieldMetaData("dateAdd", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DATE_UPDATE, new org.apache.thrift.meta_data.FieldMetaData("dateUpdate", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Tag.class, metaDataMap);
  }

  public Tag() {
  }

  public Tag(
    String tagID,
    String tagName,
    long viewCounts,
    String dateAdd,
    String dateUpdate)
  {
    this();
    this.tagID = tagID;
    this.tagName = tagName;
    this.viewCounts = viewCounts;
    setViewCountsIsSet(true);
    this.dateAdd = dateAdd;
    this.dateUpdate = dateUpdate;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Tag(Tag other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetTagID()) {
      this.tagID = other.tagID;
    }
    if (other.isSetTagName()) {
      this.tagName = other.tagName;
    }
    this.viewCounts = other.viewCounts;
    if (other.isSetDateAdd()) {
      this.dateAdd = other.dateAdd;
    }
    if (other.isSetDateUpdate()) {
      this.dateUpdate = other.dateUpdate;
    }
  }

  public Tag deepCopy() {
    return new Tag(this);
  }

  @Override
  public void clear() {
    this.tagID = null;
    this.tagName = null;
    setViewCountsIsSet(false);
    this.viewCounts = 0;
    this.dateAdd = null;
    this.dateUpdate = null;
  }

  public String getTagID() {
    return this.tagID;
  }

  public Tag setTagID(String tagID) {
    this.tagID = tagID;
    return this;
  }

  public void unsetTagID() {
    this.tagID = null;
  }

  /** Returns true if field tagID is set (has been assigned a value) and false otherwise */
  public boolean isSetTagID() {
    return this.tagID != null;
  }

  public void setTagIDIsSet(boolean value) {
    if (!value) {
      this.tagID = null;
    }
  }

  public String getTagName() {
    return this.tagName;
  }

  public Tag setTagName(String tagName) {
    this.tagName = tagName;
    return this;
  }

  public void unsetTagName() {
    this.tagName = null;
  }

  /** Returns true if field tagName is set (has been assigned a value) and false otherwise */
  public boolean isSetTagName() {
    return this.tagName != null;
  }

  public void setTagNameIsSet(boolean value) {
    if (!value) {
      this.tagName = null;
    }
  }

  public long getViewCounts() {
    return this.viewCounts;
  }

  public Tag setViewCounts(long viewCounts) {
    this.viewCounts = viewCounts;
    setViewCountsIsSet(true);
    return this;
  }

  public void unsetViewCounts() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VIEWCOUNTS_ISSET_ID);
  }

  /** Returns true if field viewCounts is set (has been assigned a value) and false otherwise */
  public boolean isSetViewCounts() {
    return EncodingUtils.testBit(__isset_bitfield, __VIEWCOUNTS_ISSET_ID);
  }

  public void setViewCountsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VIEWCOUNTS_ISSET_ID, value);
  }

  public String getDateAdd() {
    return this.dateAdd;
  }

  public Tag setDateAdd(String dateAdd) {
    this.dateAdd = dateAdd;
    return this;
  }

  public void unsetDateAdd() {
    this.dateAdd = null;
  }

  /** Returns true if field dateAdd is set (has been assigned a value) and false otherwise */
  public boolean isSetDateAdd() {
    return this.dateAdd != null;
  }

  public void setDateAddIsSet(boolean value) {
    if (!value) {
      this.dateAdd = null;
    }
  }

  public String getDateUpdate() {
    return this.dateUpdate;
  }

  public Tag setDateUpdate(String dateUpdate) {
    this.dateUpdate = dateUpdate;
    return this;
  }

  public void unsetDateUpdate() {
    this.dateUpdate = null;
  }

  /** Returns true if field dateUpdate is set (has been assigned a value) and false otherwise */
  public boolean isSetDateUpdate() {
    return this.dateUpdate != null;
  }

  public void setDateUpdateIsSet(boolean value) {
    if (!value) {
      this.dateUpdate = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TAG_ID:
      if (value == null) {
        unsetTagID();
      } else {
        setTagID((String)value);
      }
      break;

    case TAG_NAME:
      if (value == null) {
        unsetTagName();
      } else {
        setTagName((String)value);
      }
      break;

    case VIEW_COUNTS:
      if (value == null) {
        unsetViewCounts();
      } else {
        setViewCounts((Long)value);
      }
      break;

    case DATE_ADD:
      if (value == null) {
        unsetDateAdd();
      } else {
        setDateAdd((String)value);
      }
      break;

    case DATE_UPDATE:
      if (value == null) {
        unsetDateUpdate();
      } else {
        setDateUpdate((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TAG_ID:
      return getTagID();

    case TAG_NAME:
      return getTagName();

    case VIEW_COUNTS:
      return Long.valueOf(getViewCounts());

    case DATE_ADD:
      return getDateAdd();

    case DATE_UPDATE:
      return getDateUpdate();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TAG_ID:
      return isSetTagID();
    case TAG_NAME:
      return isSetTagName();
    case VIEW_COUNTS:
      return isSetViewCounts();
    case DATE_ADD:
      return isSetDateAdd();
    case DATE_UPDATE:
      return isSetDateUpdate();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Tag)
      return this.equals((Tag)that);
    return false;
  }

  public boolean equals(Tag that) {
    if (that == null)
      return false;

    boolean this_present_tagID = true && this.isSetTagID();
    boolean that_present_tagID = true && that.isSetTagID();
    if (this_present_tagID || that_present_tagID) {
      if (!(this_present_tagID && that_present_tagID))
        return false;
      if (!this.tagID.equals(that.tagID))
        return false;
    }

    boolean this_present_tagName = true && this.isSetTagName();
    boolean that_present_tagName = true && that.isSetTagName();
    if (this_present_tagName || that_present_tagName) {
      if (!(this_present_tagName && that_present_tagName))
        return false;
      if (!this.tagName.equals(that.tagName))
        return false;
    }

    boolean this_present_viewCounts = true;
    boolean that_present_viewCounts = true;
    if (this_present_viewCounts || that_present_viewCounts) {
      if (!(this_present_viewCounts && that_present_viewCounts))
        return false;
      if (this.viewCounts != that.viewCounts)
        return false;
    }

    boolean this_present_dateAdd = true && this.isSetDateAdd();
    boolean that_present_dateAdd = true && that.isSetDateAdd();
    if (this_present_dateAdd || that_present_dateAdd) {
      if (!(this_present_dateAdd && that_present_dateAdd))
        return false;
      if (!this.dateAdd.equals(that.dateAdd))
        return false;
    }

    boolean this_present_dateUpdate = true && this.isSetDateUpdate();
    boolean that_present_dateUpdate = true && that.isSetDateUpdate();
    if (this_present_dateUpdate || that_present_dateUpdate) {
      if (!(this_present_dateUpdate && that_present_dateUpdate))
        return false;
      if (!this.dateUpdate.equals(that.dateUpdate))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(Tag other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Tag typedOther = (Tag)other;

    lastComparison = Boolean.valueOf(isSetTagID()).compareTo(typedOther.isSetTagID());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTagID()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tagID, typedOther.tagID);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTagName()).compareTo(typedOther.isSetTagName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTagName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tagName, typedOther.tagName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetViewCounts()).compareTo(typedOther.isSetViewCounts());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetViewCounts()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.viewCounts, typedOther.viewCounts);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDateAdd()).compareTo(typedOther.isSetDateAdd());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDateAdd()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dateAdd, typedOther.dateAdd);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDateUpdate()).compareTo(typedOther.isSetDateUpdate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDateUpdate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dateUpdate, typedOther.dateUpdate);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Tag(");
    boolean first = true;

    sb.append("tagID:");
    if (this.tagID == null) {
      sb.append("null");
    } else {
      sb.append(this.tagID);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("tagName:");
    if (this.tagName == null) {
      sb.append("null");
    } else {
      sb.append(this.tagName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("viewCounts:");
    sb.append(this.viewCounts);
    first = false;
    if (!first) sb.append(", ");
    sb.append("dateAdd:");
    if (this.dateAdd == null) {
      sb.append("null");
    } else {
      sb.append(this.dateAdd);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("dateUpdate:");
    if (this.dateUpdate == null) {
      sb.append("null");
    } else {
      sb.append(this.dateUpdate);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TagStandardSchemeFactory implements SchemeFactory {
    public TagStandardScheme getScheme() {
      return new TagStandardScheme();
    }
  }

  private static class TagStandardScheme extends StandardScheme<Tag> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Tag struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TAG_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.tagID = iprot.readString();
              struct.setTagIDIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TAG_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.tagName = iprot.readString();
              struct.setTagNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // VIEW_COUNTS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.viewCounts = iprot.readI64();
              struct.setViewCountsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // DATE_ADD
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.dateAdd = iprot.readString();
              struct.setDateAddIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // DATE_UPDATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.dateUpdate = iprot.readString();
              struct.setDateUpdateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Tag struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.tagID != null) {
        oprot.writeFieldBegin(TAG_ID_FIELD_DESC);
        oprot.writeString(struct.tagID);
        oprot.writeFieldEnd();
      }
      if (struct.tagName != null) {
        oprot.writeFieldBegin(TAG_NAME_FIELD_DESC);
        oprot.writeString(struct.tagName);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(VIEW_COUNTS_FIELD_DESC);
      oprot.writeI64(struct.viewCounts);
      oprot.writeFieldEnd();
      if (struct.dateAdd != null) {
        oprot.writeFieldBegin(DATE_ADD_FIELD_DESC);
        oprot.writeString(struct.dateAdd);
        oprot.writeFieldEnd();
      }
      if (struct.dateUpdate != null) {
        oprot.writeFieldBegin(DATE_UPDATE_FIELD_DESC);
        oprot.writeString(struct.dateUpdate);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TagTupleSchemeFactory implements SchemeFactory {
    public TagTupleScheme getScheme() {
      return new TagTupleScheme();
    }
  }

  private static class TagTupleScheme extends TupleScheme<Tag> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Tag struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTagID()) {
        optionals.set(0);
      }
      if (struct.isSetTagName()) {
        optionals.set(1);
      }
      if (struct.isSetViewCounts()) {
        optionals.set(2);
      }
      if (struct.isSetDateAdd()) {
        optionals.set(3);
      }
      if (struct.isSetDateUpdate()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetTagID()) {
        oprot.writeString(struct.tagID);
      }
      if (struct.isSetTagName()) {
        oprot.writeString(struct.tagName);
      }
      if (struct.isSetViewCounts()) {
        oprot.writeI64(struct.viewCounts);
      }
      if (struct.isSetDateAdd()) {
        oprot.writeString(struct.dateAdd);
      }
      if (struct.isSetDateUpdate()) {
        oprot.writeString(struct.dateUpdate);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Tag struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.tagID = iprot.readString();
        struct.setTagIDIsSet(true);
      }
      if (incoming.get(1)) {
        struct.tagName = iprot.readString();
        struct.setTagNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.viewCounts = iprot.readI64();
        struct.setViewCountsIsSet(true);
      }
      if (incoming.get(3)) {
        struct.dateAdd = iprot.readString();
        struct.setDateAddIsSet(true);
      }
      if (incoming.get(4)) {
        struct.dateUpdate = iprot.readString();
        struct.setDateUpdateIsSet(true);
      }
    }
  }

}

