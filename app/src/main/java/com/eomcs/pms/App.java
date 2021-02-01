package com.eomcs.pms;

import com.eomcs.pms.handler.BoardHandler;
import com.eomcs.pms.handler.MemberHandler;
import com.eomcs.pms.handler.ProjectHandler;
import com.eomcs.pms.handler.TaskHandler;
import com.eomcs.util.Prompt;

public class App {

  public static void main(String[] args) {

    BoardHandler BoardHandler = new BoardHandler();
    MemberHandler MemberHandler = new MemberHandler();
    ProjectHandler ProjectHandler = new ProjectHandler(MemberHandler);
    TaskHandler TaskHandler = new TaskHandler(MemberHandler);


    loop:
      while (true) {
        String command = com.eomcs.util.Prompt.inputString("명령> ");

        switch (command) {
          case "/member/add":
            MemberHandler.add();
            break;
          case "/member/list":
            MemberHandler.list();
            break;
          case "/member/detail":
            MemberHandler.detail();
            break;  
          case "/member/update":
            MemberHandler.update();
            break; 
          case "/member/delete":
            MemberHandler.delete();
            break;
          case "/project/add":
            ProjectHandler.add();
            break;
          case "/project/list":
            ProjectHandler.list();
            break;
          case "/project/detail": 
            ProjectHandler.detail();
            break;  
          case "/project/update":
            ProjectHandler.update();
            break; 
          case "/project/delete":
            ProjectHandler.delete();
            break;
          case "/task/add":
            TaskHandler.add();
            break;
          case "/task/list":
            TaskHandler.list();
            break;
          case "/task/detail": 
            TaskHandler.detail();
            break;  
          case "/task/update":
            TaskHandler.update();
            break; 
          case "/task/delete":
            TaskHandler.delete();
            break;
          case "/board/add":
            BoardHandler.add();
            break;
          case "/board/list":
            BoardHandler.list();
            break;
          case "/board/detail":
            BoardHandler.detail();
            break;  
          case "/board/update":
            BoardHandler.update();
            break; 
          case "/board/delete":
            BoardHandler.delete();
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
