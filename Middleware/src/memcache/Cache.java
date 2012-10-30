/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package memcache;

/**
 *
 * @author root
 */
public interface Cache<K extends Comparable, V> {
   V get(K obj);
   void put(K key, V obj);
   void put(K key, V obj, long validTime);
   void remove(K key);
   Pair[] getAll();
   int size();
}
