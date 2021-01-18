package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

public class App {

  static Scanner sc = new Scanner(System.in);

  static final int MEMBER = 100;

  static int[] mno = new int[MEMBER];
  static String[] mname = new String[MEMBER];
  static String[] memail = new String[MEMBER];
  static String[] mpassword = new String[MEMBER];
  static String[] mphoto = new String[MEMBER];
  static String[] mtel = new String[MEMBER];
  static Date[] mregisteredDate = new Date[MEMBER];

  static int msize = 0;

  static final int PROJECT = 100;

  static int[] pno = new int[PROJECT];
  static String[] ptitle = new String[PROJECT];
  static String[] pcontent = new String[PROJECT];
  static Date[] pstartDate = new Date[PROJECT];
  static Date[] pendDate = new Date[PROJECT];
  static String[] powner = new String[PROJECT];
  static String[] pmembers = new String[PROJECT];

  static int psize = 0;

  static final int TASK = 100;

  static int[] tno = new int[TASK];
  static String[] tcontent = new String[TASK];
  static Date[] tdeadline = new Date[TASK];
  static String[] towner = new String[TASK];
  static int[] tstatus = new int[TASK];

  static int tsize = 0;


  static void addMember() {
    System.out.println("[회원 등록]");

    for (int i = 0; i < MEMBER; i++) {
      System.out.print("번호? ");
      mno[i] = Integer.parseInt(sc.nextLine());

      System.out.print("이름? ");
      mname[i] = sc.nextLine();

      System.out.print("이메일? ");
      memail[i] = sc.nextLine();

      System.out.print("암호? ");
      mpassword[i] = sc.nextLine();

      System.out.print("사진? ");
      mphoto[i] = sc.nextLine();

      System.out.print("전화? ");
      mtel[i] = sc.nextLine();

      mregisteredDate[i] = new java.sql.Date(System.currentTimeMillis());

      msize++;
      System.out.println();
      System.out.print("계속 입력하시겠습니까?(y/N) ");
      String mstr = sc.nextLine();
      if (!mstr.equalsIgnoreCase("y")) {
        break;
      }
      System.out.println();
    }
  }

  static void listMember() {
    System.out.println("--------------------------------");
    System.out.println("[회원 목록]");

    for (int i = 0; i < msize; i++) {
      System.out.printf("%d, %s, %s, %s, %s\n",
          mno[i], mname[i], memail[i], mtel[i], mregisteredDate[i]);
    }
  }

  static void addProject() {
    System.out.println("[프로젝트 등록]");

    for (int i = 0; i < PROJECT; i++) {
      System.out.print("번호? ");
      pno[i] = Integer.valueOf(sc.nextLine());

      System.out.print("프로젝트명? ");
      ptitle[i] = sc.nextLine();

      System.out.print("내용? ");
      pcontent[i] = sc.nextLine();

      System.out.print("시작일? ");
      pstartDate[i] = Date.valueOf(sc.nextLine());

      System.out.print("종료일? ");
      pendDate[i] = Date.valueOf(sc.nextLine());

      System.out.print("만든이? ");
      powner[i] = sc.nextLine();

      System.out.print("팀원? ");
      pmembers[i] = sc.nextLine();

      psize++;
      System.out.println();

      System.out.print("계속 입력하시겠습니까?(y/N) ");
      String pstr = sc.nextLine();
      if (!pstr.equalsIgnoreCase("y")) {
        break;
      }
      System.out.println();
    }
  }

  static void listProject() {
    System.out.println("--------------------------------");
    System.out.println("[프로젝트 목록]");

    for (int i = 0; i < psize; i++) {
      System.out.printf("%d, %s, %s, %s, %s\n", 
          pno[i], ptitle[i], pstartDate[i], pendDate[i], powner[i]);
    }
  }

  static void addTask() {
    System.out.println("[작업 등록]");
    for (int i = 0; i < TASK; i++) {
      System.out.print("번호? ");
      tno[i] = Integer.parseInt(sc.nextLine());

      System.out.print("내용? ");
      tcontent[i] = sc.nextLine();

      System.out.print("마감일? ");
      tdeadline[i] = Date.valueOf(sc.nextLine());

      System.out.println("상태?");
      System.out.println("0: 신규");
      System.out.println("1: 진행중");
      System.out.println("2: 완료");
      System.out.print("> ");
      tstatus[i] = Integer.valueOf(sc.nextLine());

      System.out.print("담당자? ");
      towner[i] = sc.nextLine();

      tsize++;
      System.out.println(); 

      System.out.print("계속 입력하시겠습니까?(y/N) ");
      String tstr = sc.nextLine();
      if (!tstr.equalsIgnoreCase("y")) {
        break;
      }
      System.out.println(); 
    }
  }

  static void listTask() {
    System.out.println("--------------------------------");
    System.out.println("[작업 목록]");

    for (int i = 0; i < tsize; i++) {
      String stateLabel = null;
      switch (tstatus[i]) {
        case 1:
          stateLabel = "진행중";
          break;
        case 2:
          stateLabel = "완료";
          break;
        default:
          stateLabel = "신규";
      }
      System.out.printf("%d, %s, %s, %s, %s\n",
          tno[i], tcontent[i], tdeadline[i], stateLabel, towner[i]);
    }
  }

  public static void main(String[] args) {

    while (true) {
      System.out.print("명령> ");
      String input = sc.nextLine();

      if (input.equals("/member/add")) {
        addMember();
      } else if (input.equals("/member/list")) {
        listMember();
      }else if (input.equals("/project/add")) {
        addProject();
      } else if (input.equals("/project/list")) {
        listProject();
      } else if (input.equals("/task/add")) {
        addTask();
      } else if (input.equals("/task/list")) {
        listTask();
      } else if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
        System.out.println("안녕!");
        break;
      } else {
        System.out.println("실행할 수 없는 명령입니다.");
      }
      System.out.println();
    }
    sc.close();
  }
}
