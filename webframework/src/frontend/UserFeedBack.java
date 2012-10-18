/**
 * Autogenerated by Thrift Compiler (0.9.0-dev)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package frontend;

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

public class UserFeedBack implements org.apache.thrift.TBase<UserFeedBack, UserFeedBack._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UserFeedBack");

  private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("userID", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField ITEMS_LIKE_FIELD_DESC = new org.apache.thrift.protocol.TField("itemsLike", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField ITEMS_DISLIKE_FIELD_DESC = new org.apache.thrift.protocol.TField("itemsDislike", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField FAVOURITE_ITEMS_FIELD_DESC = new org.apache.thrift.protocol.TField("favouriteItems", org.apache.thrift.protocol.TType.LIST, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new UserFeedBackStandardSchemeFactory());
    schemes.put(TupleScheme.class, new UserFeedBackTupleSchemeFactory());
  }

  public String userID; // required
  public List<String> itemsLike; // required
  public List<String> itemsDislike; // required
  public List<String> favouriteItems; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    USER_ID((short)1, "userID"),
    ITEMS_LIKE((short)2, "itemsLike"),
    ITEMS_DISLIKE((short)3, "itemsDislike"),
    FAVOURITE_ITEMS((short)4, "favouriteItems");

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
        case 1: // USER_ID
          return USER_ID;
        case 2: // ITEMS_LIKE
          return ITEMS_LIKE;
        case 3: // ITEMS_DISLIKE
          return ITEMS_DISLIKE;
        case 4: // FAVOURITE_ITEMS
          return FAVOURITE_ITEMS;
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
    tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("userID", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ITEMS_LIKE, new org.apache.thrift.meta_data.FieldMetaData("itemsLike", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.ITEMS_DISLIKE, new org.apache.thrift.meta_data.FieldMetaData("itemsDislike", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.FAVOURITE_ITEMS, new org.apache.thrift.meta_data.FieldMetaData("favouriteItems", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UserFeedBack.class, metaDataMap);
  }

  public UserFeedBack() {
  }

  public UserFeedBack(
    String userID,
    List<String> itemsLike,
    List<String> itemsDislike,
    List<String> favouriteItems)
  {
    this();
    this.userID = userID;
    this.itemsLike = itemsLike;
    this.itemsDislike = itemsDislike;
    this.favouriteItems = favouriteItems;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public UserFeedBack(UserFeedBack other) {
    if (other.isSetUserID()) {
      this.userID = other.userID;
    }
    if (other.isSetItemsLike()) {
      List<String> __this__itemsLike = new ArrayList<String>();
      for (String other_element : other.itemsLike) {
        __this__itemsLike.add(other_element);
      }
      this.itemsLike = __this__itemsLike;
    }
    if (other.isSetItemsDislike()) {
      List<String> __this__itemsDislike = new ArrayList<String>();
      for (String other_element : other.itemsDislike) {
        __this__itemsDislike.add(other_element);
      }
      this.itemsDislike = __this__itemsDislike;
    }
    if (other.isSetFavouriteItems()) {
      List<String> __this__favouriteItems = new ArrayList<String>();
      for (String other_element : other.favouriteItems) {
        __this__favouriteItems.add(other_element);
      }
      this.favouriteItems = __this__favouriteItems;
    }
  }

  public UserFeedBack deepCopy() {
    return new UserFeedBack(this);
  }

  @Override
  public void clear() {
    this.userID = null;
    this.itemsLike = null;
    this.itemsDislike = null;
    this.favouriteItems = null;
  }

  public String getUserID() {
    return this.userID;
  }

  public UserFeedBack setUserID(String userID) {
    this.userID = userID;
    return this;
  }

  public void unsetUserID() {
    this.userID = null;
  }

  /** Returns true if field userID is set (has been assigned a value) and false otherwise */
  public boolean isSetUserID() {
    return this.userID != null;
  }

  public void setUserIDIsSet(boolean value) {
    if (!value) {
      this.userID = null;
    }
  }

  public int getItemsLikeSize() {
    return (this.itemsLike == null) ? 0 : this.itemsLike.size();
  }

  public java.util.Iterator<String> getItemsLikeIterator() {
    return (this.itemsLike == null) ? null : this.itemsLike.iterator();
  }

  public void addToItemsLike(String elem) {
    if (this.itemsLike == null) {
      this.itemsLike = new ArrayList<String>();
    }
    this.itemsLike.add(elem);
  }

  public List<String> getItemsLike() {
    return this.itemsLike;
  }

  public UserFeedBack setItemsLike(List<String> itemsLike) {
    this.itemsLike = itemsLike;
    return this;
  }

  public void unsetItemsLike() {
    this.itemsLike = null;
  }

  /** Returns true if field itemsLike is set (has been assigned a value) and false otherwise */
  public boolean isSetItemsLike() {
    return this.itemsLike != null;
  }

  public void setItemsLikeIsSet(boolean value) {
    if (!value) {
      this.itemsLike = null;
    }
  }

  public int getItemsDislikeSize() {
    return (this.itemsDislike == null) ? 0 : this.itemsDislike.size();
  }

  public java.util.Iterator<String> getItemsDislikeIterator() {
    return (this.itemsDislike == null) ? null : this.itemsDislike.iterator();
  }

  public void addToItemsDislike(String elem) {
    if (this.itemsDislike == null) {
      this.itemsDislike = new ArrayList<String>();
    }
    this.itemsDislike.add(elem);
  }

  public List<String> getItemsDislike() {
    return this.itemsDislike;
  }

  public UserFeedBack setItemsDislike(List<String> itemsDislike) {
    this.itemsDislike = itemsDislike;
    return this;
  }

  public void unsetItemsDislike() {
    this.itemsDislike = null;
  }

  /** Returns true if field itemsDislike is set (has been assigned a value) and false otherwise */
  public boolean isSetItemsDislike() {
    return this.itemsDislike != null;
  }

  public void setItemsDislikeIsSet(boolean value) {
    if (!value) {
      this.itemsDislike = null;
    }
  }

  public int getFavouriteItemsSize() {
    return (this.favouriteItems == null) ? 0 : this.favouriteItems.size();
  }

  public java.util.Iterator<String> getFavouriteItemsIterator() {
    return (this.favouriteItems == null) ? null : this.favouriteItems.iterator();
  }

  public void addToFavouriteItems(String elem) {
    if (this.favouriteItems == null) {
      this.favouriteItems = new ArrayList<String>();
    }
    this.favouriteItems.add(elem);
  }

  public List<String> getFavouriteItems() {
    return this.favouriteItems;
  }

  public UserFeedBack setFavouriteItems(List<String> favouriteItems) {
    this.favouriteItems = favouriteItems;
    return this;
  }

  public void unsetFavouriteItems() {
    this.favouriteItems = null;
  }

  /** Returns true if field favouriteItems is set (has been assigned a value) and false otherwise */
  public boolean isSetFavouriteItems() {
    return this.favouriteItems != null;
  }

  public void setFavouriteItemsIsSet(boolean value) {
    if (!value) {
      this.favouriteItems = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case USER_ID:
      if (value == null) {
        unsetUserID();
      } else {
        setUserID((String)value);
      }
      break;

    case ITEMS_LIKE:
      if (value == null) {
        unsetItemsLike();
      } else {
        setItemsLike((List<String>)value);
      }
      break;

    case ITEMS_DISLIKE:
      if (value == null) {
        unsetItemsDislike();
      } else {
        setItemsDislike((List<String>)value);
      }
      break;

    case FAVOURITE_ITEMS:
      if (value == null) {
        unsetFavouriteItems();
      } else {
        setFavouriteItems((List<String>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case USER_ID:
      return getUserID();

    case ITEMS_LIKE:
      return getItemsLike();

    case ITEMS_DISLIKE:
      return getItemsDislike();

    case FAVOURITE_ITEMS:
      return getFavouriteItems();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case USER_ID:
      return isSetUserID();
    case ITEMS_LIKE:
      return isSetItemsLike();
    case ITEMS_DISLIKE:
      return isSetItemsDislike();
    case FAVOURITE_ITEMS:
      return isSetFavouriteItems();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof UserFeedBack)
      return this.equals((UserFeedBack)that);
    return false;
  }

  public boolean equals(UserFeedBack that) {
    if (that == null)
      return false;

    boolean this_present_userID = true && this.isSetUserID();
    boolean that_present_userID = true && that.isSetUserID();
    if (this_present_userID || that_present_userID) {
      if (!(this_present_userID && that_present_userID))
        return false;
      if (!this.userID.equals(that.userID))
        return false;
    }

    boolean this_present_itemsLike = true && this.isSetItemsLike();
    boolean that_present_itemsLike = true && that.isSetItemsLike();
    if (this_present_itemsLike || that_present_itemsLike) {
      if (!(this_present_itemsLike && that_present_itemsLike))
        return false;
      if (!this.itemsLike.equals(that.itemsLike))
        return false;
    }

    boolean this_present_itemsDislike = true && this.isSetItemsDislike();
    boolean that_present_itemsDislike = true && that.isSetItemsDislike();
    if (this_present_itemsDislike || that_present_itemsDislike) {
      if (!(this_present_itemsDislike && that_present_itemsDislike))
        return false;
      if (!this.itemsDislike.equals(that.itemsDislike))
        return false;
    }

    boolean this_present_favouriteItems = true && this.isSetFavouriteItems();
    boolean that_present_favouriteItems = true && that.isSetFavouriteItems();
    if (this_present_favouriteItems || that_present_favouriteItems) {
      if (!(this_present_favouriteItems && that_present_favouriteItems))
        return false;
      if (!this.favouriteItems.equals(that.favouriteItems))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(UserFeedBack other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    UserFeedBack typedOther = (UserFeedBack)other;

    lastComparison = Boolean.valueOf(isSetUserID()).compareTo(typedOther.isSetUserID());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserID()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userID, typedOther.userID);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetItemsLike()).compareTo(typedOther.isSetItemsLike());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetItemsLike()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.itemsLike, typedOther.itemsLike);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetItemsDislike()).compareTo(typedOther.isSetItemsDislike());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetItemsDislike()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.itemsDislike, typedOther.itemsDislike);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFavouriteItems()).compareTo(typedOther.isSetFavouriteItems());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFavouriteItems()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.favouriteItems, typedOther.favouriteItems);
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
    StringBuilder sb = new StringBuilder("UserFeedBack(");
    boolean first = true;

    sb.append("userID:");
    if (this.userID == null) {
      sb.append("null");
    } else {
      sb.append(this.userID);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("itemsLike:");
    if (this.itemsLike == null) {
      sb.append("null");
    } else {
      sb.append(this.itemsLike);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("itemsDislike:");
    if (this.itemsDislike == null) {
      sb.append("null");
    } else {
      sb.append(this.itemsDislike);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("favouriteItems:");
    if (this.favouriteItems == null) {
      sb.append("null");
    } else {
      sb.append(this.favouriteItems);
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

  private static class UserFeedBackStandardSchemeFactory implements SchemeFactory {
    public UserFeedBackStandardScheme getScheme() {
      return new UserFeedBackStandardScheme();
    }
  }

  private static class UserFeedBackStandardScheme extends StandardScheme<UserFeedBack> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, UserFeedBack struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.userID = iprot.readString();
              struct.setUserIDIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ITEMS_LIKE
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list16 = iprot.readListBegin();
                struct.itemsLike = new ArrayList<String>(_list16.size);
                for (int _i17 = 0; _i17 < _list16.size; ++_i17)
                {
                  String _elem18; // required
                  _elem18 = iprot.readString();
                  struct.itemsLike.add(_elem18);
                }
                iprot.readListEnd();
              }
              struct.setItemsLikeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ITEMS_DISLIKE
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list19 = iprot.readListBegin();
                struct.itemsDislike = new ArrayList<String>(_list19.size);
                for (int _i20 = 0; _i20 < _list19.size; ++_i20)
                {
                  String _elem21; // required
                  _elem21 = iprot.readString();
                  struct.itemsDislike.add(_elem21);
                }
                iprot.readListEnd();
              }
              struct.setItemsDislikeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // FAVOURITE_ITEMS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list22 = iprot.readListBegin();
                struct.favouriteItems = new ArrayList<String>(_list22.size);
                for (int _i23 = 0; _i23 < _list22.size; ++_i23)
                {
                  String _elem24; // required
                  _elem24 = iprot.readString();
                  struct.favouriteItems.add(_elem24);
                }
                iprot.readListEnd();
              }
              struct.setFavouriteItemsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, UserFeedBack struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.userID != null) {
        oprot.writeFieldBegin(USER_ID_FIELD_DESC);
        oprot.writeString(struct.userID);
        oprot.writeFieldEnd();
      }
      if (struct.itemsLike != null) {
        oprot.writeFieldBegin(ITEMS_LIKE_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.itemsLike.size()));
          for (String _iter25 : struct.itemsLike)
          {
            oprot.writeString(_iter25);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.itemsDislike != null) {
        oprot.writeFieldBegin(ITEMS_DISLIKE_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.itemsDislike.size()));
          for (String _iter26 : struct.itemsDislike)
          {
            oprot.writeString(_iter26);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.favouriteItems != null) {
        oprot.writeFieldBegin(FAVOURITE_ITEMS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.favouriteItems.size()));
          for (String _iter27 : struct.favouriteItems)
          {
            oprot.writeString(_iter27);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class UserFeedBackTupleSchemeFactory implements SchemeFactory {
    public UserFeedBackTupleScheme getScheme() {
      return new UserFeedBackTupleScheme();
    }
  }

  private static class UserFeedBackTupleScheme extends TupleScheme<UserFeedBack> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, UserFeedBack struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetUserID()) {
        optionals.set(0);
      }
      if (struct.isSetItemsLike()) {
        optionals.set(1);
      }
      if (struct.isSetItemsDislike()) {
        optionals.set(2);
      }
      if (struct.isSetFavouriteItems()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetUserID()) {
        oprot.writeString(struct.userID);
      }
      if (struct.isSetItemsLike()) {
        {
          oprot.writeI32(struct.itemsLike.size());
          for (String _iter28 : struct.itemsLike)
          {
            oprot.writeString(_iter28);
          }
        }
      }
      if (struct.isSetItemsDislike()) {
        {
          oprot.writeI32(struct.itemsDislike.size());
          for (String _iter29 : struct.itemsDislike)
          {
            oprot.writeString(_iter29);
          }
        }
      }
      if (struct.isSetFavouriteItems()) {
        {
          oprot.writeI32(struct.favouriteItems.size());
          for (String _iter30 : struct.favouriteItems)
          {
            oprot.writeString(_iter30);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, UserFeedBack struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.userID = iprot.readString();
        struct.setUserIDIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list31 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.itemsLike = new ArrayList<String>(_list31.size);
          for (int _i32 = 0; _i32 < _list31.size; ++_i32)
          {
            String _elem33; // required
            _elem33 = iprot.readString();
            struct.itemsLike.add(_elem33);
          }
        }
        struct.setItemsLikeIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list34 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.itemsDislike = new ArrayList<String>(_list34.size);
          for (int _i35 = 0; _i35 < _list34.size; ++_i35)
          {
            String _elem36; // required
            _elem36 = iprot.readString();
            struct.itemsDislike.add(_elem36);
          }
        }
        struct.setItemsDislikeIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list37 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.favouriteItems = new ArrayList<String>(_list37.size);
          for (int _i38 = 0; _i38 < _list37.size; ++_i38)
          {
            String _elem39; // required
            _elem39 = iprot.readString();
            struct.favouriteItems.add(_elem39);
          }
        }
        struct.setFavouriteItemsIsSet(true);
      }
    }
  }

}

