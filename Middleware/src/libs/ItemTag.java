/**
 * Autogenerated by Thrift Compiler (0.9.0)
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

public class ItemTag implements org.apache.thrift.TBase<ItemTag, ItemTag._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ItemTag");

  private static final org.apache.thrift.protocol.TField TAG_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("tagID", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField ITEMS_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("itemsID", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ItemTagStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ItemTagTupleSchemeFactory());
  }

  public String tagID; // required
  public List<String> itemsID; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TAG_ID((short)1, "tagID"),
    ITEMS_ID((short)2, "itemsID");

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
        case 2: // ITEMS_ID
          return ITEMS_ID;
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
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TAG_ID, new org.apache.thrift.meta_data.FieldMetaData("tagID", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ITEMS_ID, new org.apache.thrift.meta_data.FieldMetaData("itemsID", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ItemTag.class, metaDataMap);
  }

  public ItemTag() {
  }

  public ItemTag(
    String tagID,
    List<String> itemsID)
  {
    this();
    this.tagID = tagID;
    this.itemsID = itemsID;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ItemTag(ItemTag other) {
    if (other.isSetTagID()) {
      this.tagID = other.tagID;
    }
    if (other.isSetItemsID()) {
      List<String> __this__itemsID = new ArrayList<String>();
      for (String other_element : other.itemsID) {
        __this__itemsID.add(other_element);
      }
      this.itemsID = __this__itemsID;
    }
  }

  public ItemTag deepCopy() {
    return new ItemTag(this);
  }

  @Override
  public void clear() {
    this.tagID = null;
    this.itemsID = null;
  }

  public String getTagID() {
    return this.tagID;
  }

  public ItemTag setTagID(String tagID) {
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

  public int getItemsIDSize() {
    return (this.itemsID == null) ? 0 : this.itemsID.size();
  }

  public java.util.Iterator<String> getItemsIDIterator() {
    return (this.itemsID == null) ? null : this.itemsID.iterator();
  }

  public void addToItemsID(String elem) {
    if (this.itemsID == null) {
      this.itemsID = new ArrayList<String>();
    }
    this.itemsID.add(elem);
  }

  public List<String> getItemsID() {
    return this.itemsID;
  }

  public ItemTag setItemsID(List<String> itemsID) {
    this.itemsID = itemsID;
    return this;
  }

  public void unsetItemsID() {
    this.itemsID = null;
  }

  /** Returns true if field itemsID is set (has been assigned a value) and false otherwise */
  public boolean isSetItemsID() {
    return this.itemsID != null;
  }

  public void setItemsIDIsSet(boolean value) {
    if (!value) {
      this.itemsID = null;
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

    case ITEMS_ID:
      if (value == null) {
        unsetItemsID();
      } else {
        setItemsID((List<String>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TAG_ID:
      return getTagID();

    case ITEMS_ID:
      return getItemsID();

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
    case ITEMS_ID:
      return isSetItemsID();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ItemTag)
      return this.equals((ItemTag)that);
    return false;
  }

  public boolean equals(ItemTag that) {
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

    boolean this_present_itemsID = true && this.isSetItemsID();
    boolean that_present_itemsID = true && that.isSetItemsID();
    if (this_present_itemsID || that_present_itemsID) {
      if (!(this_present_itemsID && that_present_itemsID))
        return false;
      if (!this.itemsID.equals(that.itemsID))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(ItemTag other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ItemTag typedOther = (ItemTag)other;

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
    lastComparison = Boolean.valueOf(isSetItemsID()).compareTo(typedOther.isSetItemsID());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetItemsID()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.itemsID, typedOther.itemsID);
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
    StringBuilder sb = new StringBuilder("ItemTag(");
    boolean first = true;

    sb.append("tagID:");
    if (this.tagID == null) {
      sb.append("null");
    } else {
      sb.append(this.tagID);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("itemsID:");
    if (this.itemsID == null) {
      sb.append("null");
    } else {
      sb.append(this.itemsID);
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ItemTagStandardSchemeFactory implements SchemeFactory {
    public ItemTagStandardScheme getScheme() {
      return new ItemTagStandardScheme();
    }
  }

  private static class ItemTagStandardScheme extends StandardScheme<ItemTag> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ItemTag struct) throws org.apache.thrift.TException {
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
          case 2: // ITEMS_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.itemsID = new ArrayList<String>(_list8.size);
                for (int _i9 = 0; _i9 < _list8.size; ++_i9)
                {
                  String _elem10; // required
                  _elem10 = iprot.readString();
                  struct.itemsID.add(_elem10);
                }
                iprot.readListEnd();
              }
              struct.setItemsIDIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ItemTag struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.tagID != null) {
        oprot.writeFieldBegin(TAG_ID_FIELD_DESC);
        oprot.writeString(struct.tagID);
        oprot.writeFieldEnd();
      }
      if (struct.itemsID != null) {
        oprot.writeFieldBegin(ITEMS_ID_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.itemsID.size()));
          for (String _iter11 : struct.itemsID)
          {
            oprot.writeString(_iter11);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ItemTagTupleSchemeFactory implements SchemeFactory {
    public ItemTagTupleScheme getScheme() {
      return new ItemTagTupleScheme();
    }
  }

  private static class ItemTagTupleScheme extends TupleScheme<ItemTag> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ItemTag struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTagID()) {
        optionals.set(0);
      }
      if (struct.isSetItemsID()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetTagID()) {
        oprot.writeString(struct.tagID);
      }
      if (struct.isSetItemsID()) {
        {
          oprot.writeI32(struct.itemsID.size());
          for (String _iter12 : struct.itemsID)
          {
            oprot.writeString(_iter12);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ItemTag struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.tagID = iprot.readString();
        struct.setTagIDIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.itemsID = new ArrayList<String>(_list13.size);
          for (int _i14 = 0; _i14 < _list13.size; ++_i14)
          {
            String _elem15; // required
            _elem15 = iprot.readString();
            struct.itemsID.add(_elem15);
          }
        }
        struct.setItemsIDIsSet(true);
      }
    }
  }

}

