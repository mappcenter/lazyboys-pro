/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myLRUCaching;

/**
 *
 * @author root
 */
public class Pair<K extends Comparable, V> implements Comparable<Pair> {
   public Pair(K key1, V value1) {
      this.key = key1;
      this.value = value1;
   }
   public K key;
   public V value;
    @Override
   public boolean equals(Object obj) {
      if(obj instanceof Pair) {
         Pair p = (Pair)obj;
         return key.equals(p.key)&&value.equals(p.value);
      }
      return false;
   }
   @SuppressWarnings("unchecked")
    @Override
   public int compareTo(Pair p) {
      int v = key.compareTo(p.key);
      if(v==0) {
         if(p.value instanceof Comparable) {
            return ((Comparable)value).compareTo(p.value);
         }
      }
      return v;
   }
   @Override
   public int hashCode() {
      return key.hashCode()^value.hashCode();
   }
   @Override
   public String toString() {
      return key+": "+value;
   }
}
