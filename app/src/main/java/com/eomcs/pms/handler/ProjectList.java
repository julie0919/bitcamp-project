package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Project;

public class ProjectList {
  Node first;
  Node last;
  int size = 0;  

  void add (Project p) {
    Node node = new Node(p);

    if (last == null) {
      last = node;
      first = node;
    } else { 
      last.next = node; 
      node.prev = last; 
      last = node; 
    }

    this.size++;
  }

  Project[] toArray() {
    Project[] arr = new Project[this.size];
    Node cursor = first;
    int i = 0;
    while (cursor != null) {
      arr[i++] = cursor.project;
      cursor = cursor.next;
    }
    return arr;
  }

  Project get(int projectNo) {
    Node cursor = first;
    while (cursor != null) {
      Project p = cursor.project;
      if (p.no == projectNo) {
        return p;
      }
      cursor = cursor.next;
    }
    return null;
  }

  void delete(int projectNo) {
    Project project = get(projectNo);
    if (project == null)
      return;

    Node cursor = first;
    while (cursor != null) {
      if (cursor.project == project) {
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
        this.size--;
        break;
      }
      cursor = cursor.next;
    }
  }

  static class Node {
    Project project;
    Node next;
    Node prev;

    Node(Project p) {
      this.project = p;
    }
  }
}
