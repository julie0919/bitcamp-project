package com.eomcs.util;

public class List {

  private Node first;
  private Node last;
  protected int size = 0;  

  public void add(Object obj) {

    Node node = new Node(obj);

    if (last == null) { // 연결 리스트의 첫 항목이라면,
      last = node;
      first = node;
    } else { // 연결리스트에 이미 항목이 있다면, 
      last.next = node; // 현재 마지막 상자의 다음 상자가 새 상자를 가리키게 한다.
      node.prev = last; // 새 상자에서 이전 상자로서 현재 마지막 상자를 가리키게 한다. 
      last = node; // 새 상자가 마지막 상자가 되게 한다.
    }

    this.size++;
  }

  public Object[] toArray() {
    // 현재까지 저장된 게시글 목록을 리턴하기 위해 새 배열을 준비한다.
    Object[] arr = new Object[this.size];

    Node cursor = this.first;
    int i = 0;
    while (cursor != null) {
      arr[i++] = cursor.obj;
      cursor = cursor.next;
    }
    return arr;
  }

  public Object get(int index) {
    if (index < 0 || index >= this.size) {
      return null;
    }

    int count = 0;
    Node cursor = first;
    while (cursor != null) {
      if (index == count++) {
        return cursor.obj;
      }
      cursor = cursor.next;
    }
    return null;
  }

  public boolean delete(Object obj) {
    Object deleted = null;
    Node cursor = first;
    while (cursor != null) {
      if (cursor.obj.equals(obj)) {
        deleted = cursor.obj; // 삭제될 객체를 보관한다.
        this.size--;
        if (first == last) {
          first = last = null;
          return true;
        }
        if (cursor == first) {
          first = cursor.next;
          cursor.prev = null;
        } else {
          cursor.prev.next = cursor.next;
          if (cursor.next != null) {
            cursor.next.prev = cursor.prev;
          }
        }
        if (cursor == last) {
          last = cursor.prev;
        }
        return true;
      }
      cursor = cursor.next;
    }
    return false;
  }


  public Object delete(int index) {
    if (index < 0 || index >= this.size) {
      return null;
    }
    Object deleted = null;
    int count = 0;
    Node cursor = first;
    while (cursor != null) {
      if (index == count++) {
        deleted = cursor.obj; // 삭제될 항목을 보관해 둔다.
        this.size--;
        if (first == last) {
          first = last = null;
          break;
        }
        if (cursor == first) {
          first = cursor.next;
          cursor.prev = null;
        } else {
          cursor.prev.next = cursor.next;
          if (cursor.next != null) {
            cursor.next.prev = cursor.prev;
          }
        }
        if (cursor == last) {
          last = cursor.prev;
        }
        break;
      }
      cursor = cursor.next;
    }
    return deleted;
  }


  public int indexOf(Object obj) {
    Object[] list= this.toArray();
    for (int i = 0; i < list.length; i++) {
      if (list[i].equals(obj)) {
        return i;
      }
    }
    return -1;
  }

  public int size() {
    return this.size;
  }


  private static class Node {
    // 다형적 변수
    // - 해당 클래스의 객체(인스턴스의 주소) 뿐만 아니라 
    //   그 하위 클래스의 객체(인스턴스의 주소)까지 지정할 수 있다.
    Object obj;
    Node next;
    Node prev;

    Node(Object obj) {
      this.obj = obj;
    }
  }


  public Iterator iterator() throws CloneNotSupportedException {

    Iterator iterator = new Iterator() {
      int cursor = 0;

      @Override
      public boolean hasNext() {
        // 로컬 클래스에서 바깥 클래스의 인스턴스 주소를 사용할 때는
        // => 바깥클래스명.this
        // => 예) List.this
        return cursor < List.this.size();
      }
      @Override
      public Object next() {
        return List.this.get(cursor++);
      }
    };
    return iterator;
  }


}
