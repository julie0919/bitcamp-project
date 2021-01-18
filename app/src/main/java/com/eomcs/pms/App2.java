package com.eomcs.pms;

import java.util.Scanner;
public class App2 {
  public static void main(String[] args) {
    final int SIZE = 3;

    Scanner sc = new Scanner(System.in);
    int[] id = new int[SIZE];
    String[] name = new String[SIZE];
    String[] content = new String[SIZE];
    String[] start = new String[SIZE];
    String[] end = new String[SIZE];
    String[] writer = new String[SIZE];
    String[] team = new String[SIZE];
    int count = 0;

    System.out.println("[프로젝트]");
    for (int i =0; i < SIZE; i++) {
      System.out.print("번호? ");
      id[i] = sc.nextInt();
      sc.nextLine();
      System.out.print("프로젝트명? ");
      name[i] = sc.nextLine();
      System.out.print("내용? ");
      content[i] = sc.nextLine();
      System.out.print("시작일? ");
      start[i] = sc.nextLine();
      System.out.print("종료일? ");
      end[i] = sc.nextLine();
      System.out.print("만든이? ");
      writer[i] = sc.nextLine();
      System.out.print("팀원? ");
      team[i] = sc.nextLine();
      count++;

      System.out.println();
      System.out.print("계속 입력하시겠습니까?(y/N)");
      String result = sc.nextLine();
      if (result.length() == 0 || result.equalsIgnoreCase("n")) {
        break;
      }
      System.out.println();
    }

    System.out.println("--------------------------------------------");
    for (int i = 0; i < count; i++) {
      System.out.printf("%d, %s, %s, %s, %s\n", id[i], name[i], start[i], end[i], writer[i]);
    }
    sc.close();

  }
}
