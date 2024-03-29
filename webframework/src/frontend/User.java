/**
 * Autogenerated by Thrift Compiler (0.9.0)
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

public class User implements org.apache.thrift.TBase<User, User._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("User");

  private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("userID", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField USER_TOKEN_FIELD_DESC = new org.apache.thrift.protocol.TField("userToken", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField USER_ROLE_FIELD_DESC = new org.apache.thrift.protocol.TField("userRole", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new UserStandardSchemeFactory());
    schemes.put(TupleScheme.class, new UserTupleSchemeFactory());
  }

  public String userID; // required
  public String userToken; // required
  public int userRole; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    USER_ID((short)1, "userID"),
    USER_TOKEN((short)2, "userToken"),
    USER_ROLE((short)3, "userRole");

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
        case 2: // USER_TOKEN
          return USER_TOKEN;
        case 3: // USER_ROLE
          return USER_ROLE;
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
  private static final int __USERROLE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("userID", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.USER_TOKEN, new org.apache.thrift.meta_data.FieldMetaData("userToken", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.USER_ROLE, new org.apache.thrift.meta_data.FieldMetaData("userRole", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(User.class, metaDataMap);
  }

  public User() {
  }

  public User(
    String userID,
    String userToken,
    int userRole)
  {
    this();
    this.userID = userID;
    this.userToken = userToken;
    this.userRole = userRole;
    setUserRoleIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public User(User other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetUserID()) {
      this.userID = other.userID;
    }
    if (other.isSetUserToken()) {
      this.userToken = other.userToken;
    }
    this.userRole = other.userRole;
  }

  public User deepCopy() {
    return new User(this);
  }

  @Override
  public void clear() {
    this.userID = null;
    this.userToken = null;
    setUserRoleIsSet(false);
    this.userRole = 0;
  }

  public String getUserID() {
    return this.userID;
  }

  public User setUserID(String userID) {
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

  public String getUserToken() {
    return this.userToken;
  }

  public User setUserToken(String userToken) {
    this.userToken = userToken;
    return this;
  }

  public void unsetUserToken() {
    this.userToken = null;
  }

  /** Returns true if field userToken is set (has been assigned a value) and false otherwise */
  public boolean isSetUserToken() {
    return this.userToken != null;
  }

  public void setUserTokenIsSet(boolean value) {
    if (!value) {
      this.userToken = null;
    }
  }

  public int getUserRole() {
    return this.userRole;
  }

  public User setUserRole(int userRole) {
    this.userRole = userRole;
    setUserRoleIsSet(true);
    return this;
  }

  public void unsetUserRole() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __USERROLE_ISSET_ID);
  }

  /** Returns true if field userRole is set (has been assigned a value) and false otherwise */
  public boolean isSetUserRole() {
    return EncodingUtils.testBit(__isset_bitfield, __USERROLE_ISSET_ID);
  }

  public void setUserRoleIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __USERROLE_ISSET_ID, value);
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

    case USER_TOKEN:
      if (value == null) {
        unsetUserToken();
      } else {
        setUserToken((String)value);
      }
      break;

    case USER_ROLE:
      if (value == null) {
        unsetUserRole();
      } else {
        setUserRole((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case USER_ID:
      return getUserID();

    case USER_TOKEN:
      return getUserToken();

    case USER_ROLE:
      return Integer.valueOf(getUserRole());

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
    case USER_TOKEN:
      return isSetUserToken();
    case USER_ROLE:
      return isSetUserRole();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof User)
      return this.equals((User)that);
    return false;
  }

  public boolean equals(User that) {
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

    boolean this_present_userToken = true && this.isSetUserToken();
    boolean that_present_userToken = true && that.isSetUserToken();
    if (this_present_userToken || that_present_userToken) {
      if (!(this_present_userToken && that_present_userToken))
        return false;
      if (!this.userToken.equals(that.userToken))
        return false;
    }

    boolean this_present_userRole = true;
    boolean that_present_userRole = true;
    if (this_present_userRole || that_present_userRole) {
      if (!(this_present_userRole && that_present_userRole))
        return false;
      if (this.userRole != that.userRole)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(User other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    User typedOther = (User)other;

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
    lastComparison = Boolean.valueOf(isSetUserToken()).compareTo(typedOther.isSetUserToken());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserToken()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userToken, typedOther.userToken);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUserRole()).compareTo(typedOther.isSetUserRole());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserRole()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userRole, typedOther.userRole);
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
    StringBuilder sb = new StringBuilder("User(");
    boolean first = true;

    sb.append("userID:");
    if (this.userID == null) {
      sb.append("null");
    } else {
      sb.append(this.userID);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("userToken:");
    if (this.userToken == null) {
      sb.append("null");
    } else {
      sb.append(this.userToken);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("userRole:");
    sb.append(this.userRole);
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

  private static class UserStandardSchemeFactory implements SchemeFactory {
    public UserStandardScheme getScheme() {
      return new UserStandardScheme();
    }
  }

  private static class UserStandardScheme extends StandardScheme<User> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, User struct) throws org.apache.thrift.TException {
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
          case 2: // USER_TOKEN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.userToken = iprot.readString();
              struct.setUserTokenIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // USER_ROLE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.userRole = iprot.readI32();
              struct.setUserRoleIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, User struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.userID != null) {
        oprot.writeFieldBegin(USER_ID_FIELD_DESC);
        oprot.writeString(struct.userID);
        oprot.writeFieldEnd();
      }
      if (struct.userToken != null) {
        oprot.writeFieldBegin(USER_TOKEN_FIELD_DESC);
        oprot.writeString(struct.userToken);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(USER_ROLE_FIELD_DESC);
      oprot.writeI32(struct.userRole);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class UserTupleSchemeFactory implements SchemeFactory {
    public UserTupleScheme getScheme() {
      return new UserTupleScheme();
    }
  }

  private static class UserTupleScheme extends TupleScheme<User> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, User struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetUserID()) {
        optionals.set(0);
      }
      if (struct.isSetUserToken()) {
        optionals.set(1);
      }
      if (struct.isSetUserRole()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetUserID()) {
        oprot.writeString(struct.userID);
      }
      if (struct.isSetUserToken()) {
        oprot.writeString(struct.userToken);
      }
      if (struct.isSetUserRole()) {
        oprot.writeI32(struct.userRole);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, User struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.userID = iprot.readString();
        struct.setUserIDIsSet(true);
      }
      if (incoming.get(1)) {
        struct.userToken = iprot.readString();
        struct.setUserTokenIsSet(true);
      }
      if (incoming.get(2)) {
        struct.userRole = iprot.readI32();
        struct.setUserRoleIsSet(true);
      }
    }
  }

}

