package com.eomcs.pms;

import com.eomcs.pms.handler.BoardHandler;
import com.eomcs.pms.handler.MemberHandler;
import com.eomcs.pms.handler.ProjectHandler;
import com.eomcs.pms.handler.TaskHandler;
import com.eomcs.util.Prompt;

public class App {

  public static void main(String[] args) {

    // 각 게시판 데이터를 저장할 메모리 준비
    BoardHandler boardList = new BoardHandler();


    // 각 회원 목록 데이터를 저장할 메모리 준비
    MemberHandler memberList = new MemberHandler();

    // 각 프로젝트 목록 데이터를 저장할 메모리 준비
    // - 생성자에서 MemberHandler 객체를 주입하라고 강요한다.
    // - ProjectHandler 객체를 만드려면 반드시 주입해야한다.
    ProjectHandler projectList = new ProjectHandler(memberList);

    // ProjectHandler 가 의존하는 객체 (dependency)를 주입한다.
    // add() 메서드를 호출할 때 마다 파라미터에 넘기는 대신에
    // 계속 사용할 수 있도록 인스턴스 필드에 담아놓는다.
    // projectList.memberList = memberList;

    // 각 작업 목록 데이터를 저장할 메모리 준비
    // - 생성자에서 MemberHandler 객체를 주입하라고 강요한다.
    // - TaskHandler 객체를 만드려면 반드시 주입해야한다.
    TaskHandler taskList = new TaskHandler(memberList);

    // TaskHandler 가 사용할 의존 객체 (dependency)를 주입한다.
    // taskList.memberList = memberList;

    loop:
      while (true) {
        String command = com.eomcs.util.Prompt.inputString("명령> ");

        switch (command) {
          case "/member/add":
            memberList.add();
            break;
          case "/member/list":
            memberList.list();
            break;
          case "/project/add":
            projectList.add();
            break;
          case "/project/list":
            projectList.list();
            break;
          case "/task/add":
            taskList.add();
            break;
          case "/task/list":
            taskList.list();
            break;
          case "/board/add":
            boardList.add();
            break;
          case "/board/list":
            boardList.list();
            break;
          case "/board/detail":
            boardList.detail();
            break;
          case "/board/update":
            boardList.update();
            break;
          case "/board/delete":
            boardList.delete();
            break;
          case "quit":
          case "exit":
            System.out.println("안녕!");
            break loop;
          default:
            System.out.println("실행할 수 없는 명령입니다.");
        }
        System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력
      }

    Prompt.close();
  }
}
