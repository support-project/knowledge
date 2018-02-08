package org.support.project.knowledge.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SizeLimitMap<K, V> implements Map<K, V>{
    private LinkedHashMap<K, V> _map;
    private int _limit;
    
    public SizeLimitMap(int limit) {
        super();
        _map = new LinkedHashMap<>();
        _limit = limit;
    }

    /**
     * @param key
     * @param value
     * @return
     * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
     */
    public V put(K key, V value) {
        synchronized (_map) {
            while (_map.size() >= _limit) {
                K k = new ArrayList<K>(_map.keySet()).get(_map.size() - 1);
                _map.remove(k);
            }
            return _map.put(key, value);
        }
    }
    
    
    /**
     * @param o
     * @return
     * @see java.util.AbstractMap#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        return _map.equals(o);
    }

    /**
     * @param value
     * @return
     * @see java.util.LinkedHashMap#containsValue(java.lang.Object)
     */
    public boolean containsValue(Object value) {
        return _map.containsValue(value);
    }

    /**
     * @param key
     * @return
     * @see java.util.LinkedHashMap#get(java.lang.Object)
     */
    public V get(Object key) {
        return _map.get(key);
    }

    /**
     * @return
     * @see java.util.AbstractMap#hashCode()
     */
    public int hashCode() {
        return _map.hashCode();
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     * @see java.util.LinkedHashMap#getOrDefault(java.lang.Object, java.lang.Object)
     */
    public V getOrDefault(Object key, V defaultValue) {
        return _map.getOrDefault(key, defaultValue);
    }

    /**
     * 
     * @see java.util.LinkedHashMap#clear()
     */
    public void clear() {
        _map.clear();
    }

    /**
     * @return
     * @see java.util.AbstractMap#toString()
     */
    public String toString() {
        return _map.toString();
    }

    /**
     * @return
     * @see java.util.LinkedHashMap#keySet()
     */
    public Set<K> keySet() {
        return _map.keySet();
    }

    /**
     * @return
     * @see java.util.HashMap#size()
     */
    public int size() {
        return _map.size();
    }

    /**
     * @return
     * @see java.util.HashMap#isEmpty()
     */
    public boolean isEmpty() {
        return _map.isEmpty();
    }

    /**
     * @return
     * @see java.util.LinkedHashMap#values()
     */
    public Collection<V> values() {
        return _map.values();
    }

    /**
     * @param key
     * @return
     * @see java.util.HashMap#containsKey(java.lang.Object)
     */
    public boolean containsKey(Object key) {
        return _map.containsKey(key);
    }


    /**
     * @return
     * @see java.util.LinkedHashMap#entrySet()
     */
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        return _map.entrySet();
    }

    /**
     * @param action
     * @see java.util.LinkedHashMap#forEach(java.util.function.BiConsumer)
     */
    public void forEach(BiConsumer<? super K, ? super V> action) {
        _map.forEach(action);
    }

    /**
     * @param function
     * @see java.util.LinkedHashMap#replaceAll(java.util.function.BiFunction)
     */
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        _map.replaceAll(function);
    }

    /**
     * @param m
     * @see java.util.HashMap#putAll(java.util.Map)
     */
    public void putAll(Map<? extends K, ? extends V> m) {
        _map.putAll(m);
    }

    /**
     * @param key
     * @return
     * @see java.util.HashMap#remove(java.lang.Object)
     */
    public V remove(Object key) {
        return _map.remove(key);
    }

    /**
     * @param key
     * @param value
     * @return
     * @see java.util.HashMap#putIfAbsent(java.lang.Object, java.lang.Object)
     */
    public V putIfAbsent(K key, V value) {
        return _map.putIfAbsent(key, value);
    }

    /**
     * @param key
     * @param value
     * @return
     * @see java.util.HashMap#remove(java.lang.Object, java.lang.Object)
     */
    public boolean remove(Object key, Object value) {
        return _map.remove(key, value);
    }

    /**
     * @param key
     * @param oldValue
     * @param newValue
     * @return
     * @see java.util.HashMap#replace(java.lang.Object, java.lang.Object, java.lang.Object)
     */
    public boolean replace(K key, V oldValue, V newValue) {
        return _map.replace(key, oldValue, newValue);
    }

    /**
     * @param key
     * @param value
     * @return
     * @see java.util.HashMap#replace(java.lang.Object, java.lang.Object)
     */
    public V replace(K key, V value) {
        return _map.replace(key, value);
    }

    /**
     * @param key
     * @param mappingFunction
     * @return
     * @see java.util.HashMap#computeIfAbsent(java.lang.Object, java.util.function.Function)
     */
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return _map.computeIfAbsent(key, mappingFunction);
    }

    /**
     * @param key
     * @param remappingFunction
     * @return
     * @see java.util.HashMap#computeIfPresent(java.lang.Object, java.util.function.BiFunction)
     */
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return _map.computeIfPresent(key, remappingFunction);
    }

    /**
     * @param key
     * @param remappingFunction
     * @return
     * @see java.util.HashMap#compute(java.lang.Object, java.util.function.BiFunction)
     */
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return _map.compute(key, remappingFunction);
    }

    /**
     * @param key
     * @param value
     * @param remappingFunction
     * @return
     * @see java.util.HashMap#merge(java.lang.Object, java.lang.Object, java.util.function.BiFunction)
     */
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return _map.merge(key, value, remappingFunction);
    }

    /**
     * @return
     * @see java.util.HashMap#clone()
     */
    public Object clone() {
        return _map.clone();
    }
    
    
    
}
