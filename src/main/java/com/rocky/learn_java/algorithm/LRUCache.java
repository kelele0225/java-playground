package com.rocky.learn_java.algorithm;

import java.util.HashMap;
import java.util.Map;

class LRUCache {

    //dual direction linked list node;
    private class CacheNode{
        Integer key;
        Integer value;
        CacheNode next;
        CacheNode prev;
    }
    
    int capacity = 0;
    int length = 0;
    Map<Integer, CacheNode> cache = new HashMap<>();
    
    CacheNode head = new CacheNode();
    CacheNode tail = new CacheNode();
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.prev=null;
        head.next=tail;
        tail.prev=head;
        tail.next=null;
        
    }
    
    public int get(int key) {
        CacheNode node = cache.get(key);
        if(node!=null){
            moveToHead(node);
            return node.value;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        CacheNode node = cache.get(key);
        if(node!=null){
            node.value=value;
            moveToHead(node);
        }else{
            node = new CacheNode();
            node.key = key;
            node.value = value;
            node.next = head.next;
            node.prev = head;
            head.next=node;
            node.next.prev=node;
            cache.put(key,node);
            if(length==capacity){
                deleteTail();
            }else{
                length ++;
            }
        }
    }
    
    private void moveToHead(CacheNode node){
        node.prev.next=node.next;
        node.next.prev=node.prev;
        node.next=head.next;
        node.prev=head;
        head.next.prev=node;
        head.next=node;
    }
    
    private void deleteTail(){
        CacheNode node = tail.prev;
        node.prev.next=tail;
        tail.prev=node.prev;
        cache.remove(node.key);
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
