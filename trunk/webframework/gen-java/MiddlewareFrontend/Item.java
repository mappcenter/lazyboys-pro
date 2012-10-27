/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package MiddlewareFrontend;

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

public class Item implements org.apache.thrift.TBase<Item, Item._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Item");

  private static final org.apache.thrift.protocol.TField ITEM_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("itemID", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField CONTENT_FIELD_DESC = new org.apache.thrift.protocol.TField("content", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TAGS_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("tagsID", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField VIEW_COUNTS_FIELD_DESC = new org.apache.thrift.protocol.TField("viewCounts", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField LIKE_COUNTS_FIELD_DESC = new org.apache.thrift.protocol.TField("likeCounts", org.apache.thrift.protocol.TType.I64, (short)5);
  private static final org.apache.thrift.protocol.TField DISLIKE_COUNTS_FIELD_DESC = new org.apache.thrift.protocol.TField("dislikeCounts", org.apache.thrift.protocol.TType.I64, (short)6);
  private static final org.apache.thrift.protocol.TField DATE_ADD_FIELD_DESC = new org.apache.thrift.protocol.TField("dateAdd", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField DATE_UPDATE_FIELD_DESC = new org.apache.thrift.protocol.TField("dateUpdate", org.apache.thrift.protocol.TType.STRING, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ItemStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ItemTupleSchemeFactory());
  }

  public String itemID; // required
  public String content; // required
  public List<String> tagsID; // required
  public long viewCounts; // required
  public long likeCounts; // required
  public long dislikeCounts; // required
  public String dateAdd; // required
  public String dateUpdate; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ITEM_ID((short)1, "itemID"),
    CONTENT((short)2, "content"),
    TAGS_ID((short)3, "tagsID"),
    VIEW_COUNTS((short)4, "viewCounts"),
    LIKE_COUNTS((short)5, "likeCounts"),
    DISLIKE_COUNTS((short)6, "dislikeCounts"),
    DATE_ADD((short)7, "dateAdd"),
    DATE_UPDATE((short)8, "dateUpdate");

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
        case 1: // ITEM_ID
          return ITEM_ID;
        case 2: // CONTENT
          return CONTENT;
        case 3: // TAGS_ID
          return TAGS_ID;
        case 4: // VIEW_COUNTS
          return VIEW_COUNTS;
        case 5: // LIKE_COUNTS
          return LIKE_COUNTS;
        case 6: // DISLIKE_COUNTS
          return DISLIKE_COUNTS;
        case 7: // DATE_ADD
          return DATE_ADD;
        case 8: // DATE_UPDATE
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
  private static final int __LIKECOUNTS_ISSET_ID = 1;
  private static final int __DISLIKECOUNTS_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ITEM_ID, new org.apache.thrift.meta_data.FieldMetaData("itemID", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CONTENT, new org.apache.thrift.meta_data.FieldMetaData("content", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TAGS_ID, new org.apache.thrift.meta_data.FieldMetaData("tagsID", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.VIEW_COUNTS, new org.apache.thrift.meta_data.FieldMetaData("viewCounts", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.LIKE_COUNTS, new org.apache.thrift.meta_data.FieldMetaData("likeCounts", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.DISLIKE_COUNTS, new org.apache.thrift.meta_data.FieldMetaData("dislikeCounts", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.DATE_ADD, new org.apache.thrift.meta_data.FieldMetaData("dateAdd", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DATE_UPDATE, new org.apache.thrift.meta_data.FieldMetaData("dateUpdate", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Item.class, metaDataMap);
  }

  public Item() {
  }

  public Item(
    String itemID,
    String content,
    List<String> tagsID,
    long viewCounts,
    long likeCounts,
    long dislikeCounts,
    String dateAdd,
    String dateUpdate)
  {
    this();
    this.itemID = itemID;
    this.content = content;
    this.tagsID = tagsID;
    this.viewCounts = viewCounts;
    setViewCountsIsSet(true);
    this.likeCounts = likeCounts;
    setLikeCountsIsSet(true);
    this.dislikeCounts = dislikeCounts;
    setDislikeCountsIsSet(true);
    this.dateAdd = dateAdd;
    this.dateUpdate = dateUpdate;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Item(Item other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetItemID()) {
      this.itemID = other.itemID;
    }
    if (other.isSetContent()) {
      this.content = other.content;
    }
    if (other.isSetTagsID()) {
      List<String> __this__tagsID = new ArrayList<String>();
      for (String other_element : other.tagsID) {
        __this__tagsID.add(other_element);
      }
      this.tagsID = __this__tagsID;
    }
    this.viewCounts = other.viewCounts;
    this.likeCounts = other.likeCounts;
    this.dislikeCounts = other.dislikeCounts;
    if (other.isSetDateAdd()) {
      this.dateAdd = other.dateAdd;
    }
    if (other.isSetDateUpdate()) {
      this.dateUpdate = other.dateUpdate;
    }
  }

  public Item deepCopy() {
    return new Item(this);
  }

  @Override
  public void clear() {
    this.itemID = null;
    this.content = null;
    this.tagsID = null;
    setViewCountsIsSet(false);
    this.viewCounts = 0;
    setLikeCountsIsSet(false);
    this.likeCounts = 0;
    setDislikeCountsIsSet(false);
    this.dislikeCounts = 0;
    this.dateAdd = null;
    this.dateUpdate = null;
  }

  public String getItemID() {
    return this.itemID;
  }

  public Item setItemID(String itemID) {
    this.itemID = itemID;
    return this;
  }

  public void unsetItemID() {
    this.itemID = null;
  }

  /** Returns true if field itemID is set (has been assigned a value) and false otherwise */
  public boolean isSetItemID() {
    return this.itemID != null;
  }

  public void setItemIDIsSet(boolean value) {
    if (!value) {
      this.itemID = null;
    }
  }

  public String getContent() {
    return this.content;
  }

  public Item setContent(String content) {
    this.content = content;
    return this;
  }

  public void unsetContent() {
    this.content = null;
  }

  /** Returns true if field content is set (has been assigned a value) and false otherwise */
  public boolean isSetContent() {
    return this.content != null;
  }

  public void setContentIsSet(boolean value) {
    if (!value) {
      this.content = null;
    }
  }

  public int getTagsIDSize() {
    return (this.tagsID == null) ? 0 : this.tagsID.size();
  }

  public java.util.Iterator<String> getTagsIDIterator() {
    return (this.tagsID == null) ? null : this.tagsID.iterator();
  }

  public void addToTagsID(String elem) {
    if (this.tagsID == null) {
      this.tagsID = new ArrayList<String>();
    }
    this.tagsID.add(elem);
  }

  public List<String> getTagsID() {
    return this.tagsID;
  }

  public Item setTagsID(List<String> tagsID) {
    this.tagsID = tagsID;
    return this;
  }

  public void unsetTagsID() {
    this.tagsID = null;
  }

  /** Returns true if field tagsID is set (has been assigned a value) and false otherwise */
  public boolean isSetTagsID() {
    return this.tagsID != null;
  }

  public void setTagsIDIsSet(boolean value) {
    if (!value) {
      this.tagsID = null;
    }
  }

  public long getViewCounts() {
    return this.viewCounts;
  }

  public Item setViewCounts(long viewCounts) {
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

  public long getLikeCounts() {
    return this.likeCounts;
  }

  public Item setLikeCounts(long likeCounts) {
    this.likeCounts = likeCounts;
    setLikeCountsIsSet(true);
    return this;
  }

  public void unsetLikeCounts() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LIKECOUNTS_ISSET_ID);
  }

  /** Returns true if field likeCounts is set (has been assigned a value) and false otherwise */
  public boolean isSetLikeCounts() {
    return EncodingUtils.testBit(__isset_bitfield, __LIKECOUNTS_ISSET_ID);
  }

  public void setLikeCountsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LIKECOUNTS_ISSET_ID, value);
  }

  public long getDislikeCounts() {
    return this.dislikeCounts;
  }

  public Item setDislikeCounts(long dislikeCounts) {
    this.dislikeCounts = dislikeCounts;
    setDislikeCountsIsSet(true);
    return this;
  }

  public void unsetDislikeCounts() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DISLIKECOUNTS_ISSET_ID);
  }

  /** Returns true if field dislikeCounts is set (has been assigned a value) and false otherwise */
  public boolean isSetDislikeCounts() {
    return EncodingUtils.testBit(__isset_bitfield, __DISLIKECOUNTS_ISSET_ID);
  }

  public void setDislikeCountsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DISLIKECOUNTS_ISSET_ID, value);
  }

  public String getDateAdd() {
    return this.dateAdd;
  }

  public Item setDateAdd(String dateAdd) {
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

  public Item setDateUpdate(String dateUpdate) {
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
    case ITEM_ID:
      if (value == null) {
        unsetItemID();
      } else {
        setItemID((String)value);
      }
      break;

    case CONTENT:
      if (value == null) {
        unsetContent();
      } else {
        setContent((String)value);
      }
      break;

    case TAGS_ID:
      if (value == null) {
        unsetTagsID();
      } else {
        setTagsID((List<String>)value);
      }
      break;

    case VIEW_COUNTS:
      if (value == null) {
        unsetViewCounts();
      } else {
        setViewCounts((Long)value);
      }
      break;

    case LIKE_COUNTS:
      if (value == null) {
        unsetLikeCounts();
      } else {
        setLikeCounts((Long)value);
      }
      break;

    case DISLIKE_COUNTS:
      if (value == null) {
        unsetDislikeCounts();
      } else {
        setDislikeCounts((Long)value);
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
    case ITEM_ID:
      return getItemID();

    case CONTENT:
      return getContent();

    case TAGS_ID:
      return getTagsID();

    case VIEW_COUNTS:
      return Long.valueOf(getViewCounts());

    case LIKE_COUNTS:
      return Long.valueOf(getLikeCounts());

    case DISLIKE_COUNTS:
      return Long.valueOf(getDislikeCounts());

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
    case ITEM_ID:
      return isSetItemID();
    case CONTENT:
      return isSetContent();
    case TAGS_ID:
      return isSetTagsID();
    case VIEW_COUNTS:
      return isSetViewCounts();
    case LIKE_COUNTS:
      return isSetLikeCounts();
    case DISLIKE_COUNTS:
      return isSetDislikeCounts();
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
    if (that instanceof Item)
      return this.equals((Item)that);
    return false;
  }

  public boolean equals(Item that) {
    if (that == null)
      return false;

    boolean this_present_itemID = true && this.isSetItemID();
    boolean that_present_itemID = true && that.isSetItemID();
    if (this_present_itemID || that_present_itemID) {
      if (!(this_present_itemID && that_present_itemID))
        return false;
      if (!this.itemID.equals(that.itemID))
        return false;
    }

    boolean this_present_content = true && this.isSetContent();
    boolean that_present_content = true && that.isSetContent();
    if (this_present_content || that_present_content) {
      if (!(this_present_content && that_present_content))
        return false;
      if (!this.content.equals(that.content))
        return false;
    }

    boolean this_present_tagsID = true && this.isSetTagsID();
    boolean that_present_tagsID = true && that.isSetTagsID();
    if (this_present_tagsID || that_present_tagsID) {
      if (!(this_present_tagsID && that_present_tagsID))
        return false;
      if (!this.tagsID.equals(that.tagsID))
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

    boolean this_present_likeCounts = true;
    boolean that_present_likeCounts = true;
    if (this_present_likeCounts || that_present_likeCounts) {
      if (!(this_present_likeCounts && that_present_likeCounts))
        return false;
      if (this.likeCounts != that.likeCounts)
        return false;
    }

    boolean this_present_dislikeCounts = true;
    boolean that_present_dislikeCounts = true;
    if (this_present_dislikeCounts || that_present_dislikeCounts) {
      if (!(this_present_dislikeCounts && that_present_dislikeCounts))
        return false;
      if (this.dislikeCounts != that.dislikeCounts)
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

  public int compareTo(Item other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Item typedOther = (Item)other;

    lastComparison = Boolean.valueOf(isSetItemID()).compareTo(typedOther.isSetItemID());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetItemID()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.itemID, typedOther.itemID);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetContent()).compareTo(typedOther.isSetContent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.content, typedOther.content);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTagsID()).compareTo(typedOther.isSetTagsID());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTagsID()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tagsID, typedOther.tagsID);
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
    lastComparison = Boolean.valueOf(isSetLikeCounts()).compareTo(typedOther.isSetLikeCounts());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLikeCounts()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.likeCounts, typedOther.likeCounts);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDislikeCounts()).compareTo(typedOther.isSetDislikeCounts());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDislikeCounts()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dislikeCounts, typedOther.dislikeCounts);
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
    StringBuilder sb = new StringBuilder("Item(");
    boolean first = true;

    sb.append("itemID:");
    if (this.itemID == null) {
      sb.append("null");
    } else {
      sb.append(this.itemID);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("content:");
    if (this.content == null) {
      sb.append("null");
    } else {
      sb.append(this.content);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("tagsID:");
    if (this.tagsID == null) {
      sb.append("null");
    } else {
      sb.append(this.tagsID);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("viewCounts:");
    sb.append(this.viewCounts);
    first = false;
    if (!first) sb.append(", ");
    sb.append("likeCounts:");
    sb.append(this.likeCounts);
    first = false;
    if (!first) sb.append(", ");
    sb.append("dislikeCounts:");
    sb.append(this.dislikeCounts);
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

  private static class ItemStandardSchemeFactory implements SchemeFactory {
    public ItemStandardScheme getScheme() {
      return new ItemStandardScheme();
    }
  }

  private static class ItemStandardScheme extends StandardScheme<Item> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Item struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ITEM_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.itemID = iprot.readString();
              struct.setItemIDIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CONTENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.content = iprot.readString();
              struct.setContentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TAGS_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.tagsID = new ArrayList<String>(_list0.size);
                for (int _i1 = 0; _i1 < _list0.size; ++_i1)
                {
                  String _elem2; // required
                  _elem2 = iprot.readString();
                  struct.tagsID.add(_elem2);
                }
                iprot.readListEnd();
              }
              struct.setTagsIDIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // VIEW_COUNTS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.viewCounts = iprot.readI64();
              struct.setViewCountsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // LIKE_COUNTS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.likeCounts = iprot.readI64();
              struct.setLikeCountsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // DISLIKE_COUNTS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.dislikeCounts = iprot.readI64();
              struct.setDislikeCountsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // DATE_ADD
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.dateAdd = iprot.readString();
              struct.setDateAddIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // DATE_UPDATE
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Item struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.itemID != null) {
        oprot.writeFieldBegin(ITEM_ID_FIELD_DESC);
        oprot.writeString(struct.itemID);
        oprot.writeFieldEnd();
      }
      if (struct.content != null) {
        oprot.writeFieldBegin(CONTENT_FIELD_DESC);
        oprot.writeString(struct.content);
        oprot.writeFieldEnd();
      }
      if (struct.tagsID != null) {
        oprot.writeFieldBegin(TAGS_ID_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.tagsID.size()));
          for (String _iter3 : struct.tagsID)
          {
            oprot.writeString(_iter3);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(VIEW_COUNTS_FIELD_DESC);
      oprot.writeI64(struct.viewCounts);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(LIKE_COUNTS_FIELD_DESC);
      oprot.writeI64(struct.likeCounts);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(DISLIKE_COUNTS_FIELD_DESC);
      oprot.writeI64(struct.dislikeCounts);
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

  private static class ItemTupleSchemeFactory implements SchemeFactory {
    public ItemTupleScheme getScheme() {
      return new ItemTupleScheme();
    }
  }

  private static class ItemTupleScheme extends TupleScheme<Item> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Item struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetItemID()) {
        optionals.set(0);
      }
      if (struct.isSetContent()) {
        optionals.set(1);
      }
      if (struct.isSetTagsID()) {
        optionals.set(2);
      }
      if (struct.isSetViewCounts()) {
        optionals.set(3);
      }
      if (struct.isSetLikeCounts()) {
        optionals.set(4);
      }
      if (struct.isSetDislikeCounts()) {
        optionals.set(5);
      }
      if (struct.isSetDateAdd()) {
        optionals.set(6);
      }
      if (struct.isSetDateUpdate()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetItemID()) {
        oprot.writeString(struct.itemID);
      }
      if (struct.isSetContent()) {
        oprot.writeString(struct.content);
      }
      if (struct.isSetTagsID()) {
        {
          oprot.writeI32(struct.tagsID.size());
          for (String _iter4 : struct.tagsID)
          {
            oprot.writeString(_iter4);
          }
        }
      }
      if (struct.isSetViewCounts()) {
        oprot.writeI64(struct.viewCounts);
      }
      if (struct.isSetLikeCounts()) {
        oprot.writeI64(struct.likeCounts);
      }
      if (struct.isSetDislikeCounts()) {
        oprot.writeI64(struct.dislikeCounts);
      }
      if (struct.isSetDateAdd()) {
        oprot.writeString(struct.dateAdd);
      }
      if (struct.isSetDateUpdate()) {
        oprot.writeString(struct.dateUpdate);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Item struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.itemID = iprot.readString();
        struct.setItemIDIsSet(true);
      }
      if (incoming.get(1)) {
        struct.content = iprot.readString();
        struct.setContentIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.tagsID = new ArrayList<String>(_list5.size);
          for (int _i6 = 0; _i6 < _list5.size; ++_i6)
          {
            String _elem7; // required
            _elem7 = iprot.readString();
            struct.tagsID.add(_elem7);
          }
        }
        struct.setTagsIDIsSet(true);
      }
      if (incoming.get(3)) {
        struct.viewCounts = iprot.readI64();
        struct.setViewCountsIsSet(true);
      }
      if (incoming.get(4)) {
        struct.likeCounts = iprot.readI64();
        struct.setLikeCountsIsSet(true);
      }
      if (incoming.get(5)) {
        struct.dislikeCounts = iprot.readI64();
        struct.setDislikeCountsIsSet(true);
      }
      if (incoming.get(6)) {
        struct.dateAdd = iprot.readString();
        struct.setDateAddIsSet(true);
      }
      if (incoming.get(7)) {
        struct.dateUpdate = iprot.readString();
        struct.setDateUpdateIsSet(true);
      }
    }
  }

}

