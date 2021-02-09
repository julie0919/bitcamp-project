package com.eomcs.pms;

import com.eomcs.pms.handler.BoardHandler;
import com.eomcs.pms.handler.MemberHandler;
import com.eomcs.pms.handler.ProjectHandler;
import com.eomcs.pms.handler.TaskHandler;
import com.eomcs.util.Iterator;
import com.eomcs.util.Prompt;
import com.eomcs.util.Queue;
import com.eomcs.util.Stack;

public class App {

  static Stack commandStack = new Stack(); 
  static Queue commandQueue = new Queue(); 

  public static void main(String[] args) throws CloneNotSupportedException {

    BoardHandler BoardHandler = new BoardHandler();
    MemberHandler MemberHandler = new MemberHandler();
    ProjectHandler ProjectHandler = new ProjectHandler(MemberHandler);
    TaskHandler TaskHandler = new TaskHandler(MemberHandler);

    loop:
      while (true) {
        String command = com.eomcs.util.Prompt.inputString( "명령> ");

        if (command.length() == 0) // 사용자가 빈 문자열을 입력하면 다시 입력하도록 요구한다.
          continue;
        // 사용자가 입력한 명령을 보관해둔다.
        commandStack.push(command);
        commandQueue.offer(command);

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
          case "history":
            printCommandHistory(commandStack.iterator());
          case "history2":
            printCommandHistory(commandQueue.iterator());
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
  static void printCommandHistory(Iterator iterator) {
    int count = 0;
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
      if ((++count % 5) == 0) {
        String input = Prompt.inputString(": ");
        if (input.equalsIgnoreCase("q")) {
          break;
        }
      }
    }
  }

}
