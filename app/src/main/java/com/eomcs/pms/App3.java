package com.eomcs.pms;

import java.util.Scanner;

public class App3 {
  public static void main(String[] args) { 
    final int SIZE = 3;
    Scanner sc = new Scanner(System.in);

    int[] id = new int[SIZE];
    String[] content = new String[SIZE];
    String[] end = new String[SIZE];
    int[] progress = new int[SIZE];
    String[] result = new String[SIZE];
    String[] name = new String[SIZE];
    int count = 0;

    System.out.println("[작업]");
    System.out.print("프로젝트? ");
    String project = sc.nextLine();
    System.out.println();

    for (int i = 0; i < SIZE; i++) {
      System.out.print("번호? ");
      id[i] = sc.nextInt();
      sc.nextLine();
      System.out.print("내용? ");
      content[i] = sc.nextLine();
      System.out.print("마감일? ");
      end[i] = sc.nextLine();
      System.out.println("상태?");
      System.out.println("0: 신규\n1: 진행중\n2: 완료");
      System.out.print("> ");
      progress[i] = sc.nextInt();
      sc.nextLine();
      result[i] = (progress[i] == 0) ? "신규" : (progress[i] == 1) ? "진행중" : "완료";
      System.out.print("담당자? ");
      name[i] = sc.nextLine();
      count ++;

      System.out.println();
      System.out.print("계속 입력하시겠습니까?(y/N)");
      String response = sc.nextLine();
      if (response.length() == 0 || response.equalsIgnoreCase("n")) {
        break;
      }
    }
    System.out.println("--------------------------------------------");
    for (int i = 0; i < count; i++) {
      System.out.printf("[%s]\n%d, %s, %s, %s, %s\n", project, id[i], content[i], end[i], result[i], name[i]);
    }
    sc.close();
  }
}
