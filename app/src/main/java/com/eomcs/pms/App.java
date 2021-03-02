package com.eomcs.pms;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Date;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.handler.BoardAddHandler;
import com.eomcs.pms.handler.BoardDeleteHandler;
import com.eomcs.pms.handler.BoardDetailHandler;
import com.eomcs.pms.handler.BoardListHandler;
import com.eomcs.pms.handler.BoardSearchHandler;
import com.eomcs.pms.handler.BoardUpdateHandler;
import com.eomcs.pms.handler.Command;
import com.eomcs.pms.handler.HelloHandler;
import com.eomcs.pms.handler.MemberAddHandler;
import com.eomcs.pms.handler.MemberDeleteHandler;
import com.eomcs.pms.handler.MemberDetailHandler;
import com.eomcs.pms.handler.MemberListHandler;
import com.eomcs.pms.handler.MemberUpdateHandler;
import com.eomcs.pms.handler.MemberValidatorHandler;
import com.eomcs.pms.handler.ProjectAddHandler;
import com.eomcs.pms.handler.ProjectDeleteHandler;
import com.eomcs.pms.handler.ProjectDetailHandler;
import com.eomcs.pms.handler.ProjectListHandler;
import com.eomcs.pms.handler.ProjectUpdateHandler;
import com.eomcs.pms.handler.TaskAddHandler;
import com.eomcs.pms.handler.TaskDeleteHandler;
import com.eomcs.pms.handler.TaskDetailHandler;
import com.eomcs.pms.handler.TaskListHandler;
import com.eomcs.pms.handler.TaskUpdateHandler;
import com.eomcs.util.Prompt;

//1) 게시글 데이터 로딩 및 저장

public class App {

  // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비
  static ArrayDeque<String> commandStack = new ArrayDeque<>();
  static LinkedList<String> commandQueue = new LinkedList<>();

  // VO 를 저장할 컬렉션 객체
  static ArrayList<Board> boardList = new ArrayList<>();
  static ArrayList<Member> memberList = new ArrayList<>();
  static LinkedList<Project> projectList = new LinkedList<>();
  static LinkedList<Task> taskList = new LinkedList<>();

  public static void main(String[] args) {


    // 파일에서 데이터를 읽어온다.(데이터 로딩)
    loadBoards();
    loadMembers();
    loadProjects();
    loadTasks();

    // 사용자 명령을 처리하는 객체를 맵에 보관한다.
    HashMap<String,Command> commandMap = new HashMap<>();

    commandMap.put("/board/add", new BoardAddHandler(boardList));
    commandMap.put("/board/list", new BoardListHandler(boardList));
    commandMap.put("/board/detail", new BoardDetailHandler(boardList));
    commandMap.put("/board/update", new BoardUpdateHandler(boardList));
    commandMap.put("/board/delete", new BoardDeleteHandler(boardList));

    commandMap.put("/member/add", new MemberAddHandler(memberList));
    commandMap.put("/member/list", new MemberListHandler(memberList));
    commandMap.put("/member/detail", new MemberDetailHandler(memberList));
    commandMap.put("/member/update", new MemberUpdateHandler(memberList));
    commandMap.put("/member/delete", new MemberDeleteHandler(memberList));
    MemberValidatorHandler memberValidatorHandler = new MemberValidatorHandler(memberList);

    commandMap.put("/project/add", new ProjectAddHandler(projectList, memberValidatorHandler));
    commandMap.put("/project/list", new ProjectListHandler(projectList));
    commandMap.put("/project/detail", new ProjectDetailHandler(projectList));
    commandMap.put("/project/update", new ProjectUpdateHandler(projectList, memberValidatorHandler));
    commandMap.put("/project/delete", new ProjectDeleteHandler(projectList));

    commandMap.put("/task/add", new TaskAddHandler(taskList, memberValidatorHandler));
    commandMap.put("/task/list", new TaskListHandler(taskList));
    commandMap.put("/task/detail", new TaskDetailHandler(taskList));
    commandMap.put("/task/update", new TaskUpdateHandler(taskList, memberValidatorHandler));
    commandMap.put("/task/delete", new TaskDeleteHandler(taskList));

    // 새 기능 추가
    commandMap.put("/board/search", new BoardSearchHandler(boardList));
    commandMap.put("/hello", new HelloHandler());

    loop:
      while (true) {
        String command = com.eomcs.util.Prompt.inputString("명령> ");

        if (command.length() == 0) // 사용자가 빈 문자열을 입력하면 다시 입력하도록 요구한다.
          continue;

        // 사용자가 입력한 명령을 보관해둔다.
        commandStack.push(command);
        commandQueue.offer(command);

        try {
          switch (command) {
            case "history":
              printCommandHistory(commandStack.iterator());
              break;
            case "history2": 
              printCommandHistory(commandQueue.iterator());
              break;
            case "quit":
            case "exit":
              System.out.println("안녕!");
              break loop;
            default:
              Command commandHandler = commandMap.get(command);

              if (commandHandler == null) {
                System.out.println("실행할 수 없는 명령입니다.");
              } else {
                commandHandler.service();
                // 이제 명령어와 그 명령어를 처리하는 핸들러를 추가할 때 마다
                // case 문을 추가할 필요가 없다.
              }
          }
        } catch (Exception e) {
          System.out.println("------------------------------------------");
          System.out.printf("명령어 실행 중 오류 발생: %s - %s\n", 
              e.getClass().getName(), e.getMessage());
          System.out.println("------------------------------------------");
        }
        System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력
      }

    // 게시글 데이터를 파일로 출력한다.
    saveBoards();
    saveMembers();
    saveProjects();
    saveTasks();

    Prompt.close();
  }

  static void printCommandHistory(Iterator<String> iterator) {
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

  static void loadBoards() {
    try (Scanner in = new Scanner(new FileReader("boards.csv"))) {
      while (true) {
        try {
          String record = in.nextLine();
          String[] fields = record.split(","); // 번호, 제목, 내용, 작성자, 등록일, 조회수
          Board b = new Board();
          b.setNo(Integer.parseInt(fields[0]));
          b.setTitle(fields[1]);
          b.setContent(fields[2]);
          b.setWriter(fields[3]);
          b.setRegisteredDate(Date.valueOf(fields[4]));
          b.setViewCount(Integer.parseInt(fields[5]));
          boardList.add(b);
        } catch (NoSuchElementException e) {
          break;
        }
      }
      System.out.println("게시글 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("게시글 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveBoards() {
    try (FileWriter out = new FileWriter("boards.csv")) {

      // boards.csv 파일 포맷
      // - 번호, 제목, 내용, 작성자, 등록일, 조회수(CRLF)

      for (Board b : boardList) {
        out.write(String.format("%d,%s,%s,%s,%s,%d\n", 
            b.getNo(),b.getTitle(),b.getContent(),b.getWriter(),b.getRegisteredDate(),b.getViewCount()));

      }
      System.out.println("게시글 데이터 저장!");

    } catch (Exception e) {
      System.out.println("게시글 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadMembers() {

    try (Scanner in = new Scanner(new FileInputStream("members.csv"))) {

      while (true) {

        try {
          String record = in.nextLine();
          String[] fields = record.split(",");
          Member m = new Member();
          m.setNo(Integer.parseInt(fields[0]));
          m.setName(fields[1]);
          m.setEmail(fields[2]);
          m.setPassword(fields[3]);
          m.setPhoto(fields[4]);
          m.setTel(fields[5]);
          m.setRegisteredDate(Date.valueOf(fields[6]));

          memberList.add(m);
        } catch (NoSuchElementException e) {
          break;
        }
      }
      System.out.println("회원 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("회원 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveMembers() {

    try (FileWriter out = new FileWriter("members.csv")) {

      // members.csv 파일 포맷
      // - 번호, 이름, 이메일, 비밀번호, 사진, 연락처, 등록일

      for (Member m : memberList) {
        out.write(String.format("%d,%s,%s,%s,%s,%s,%s\n",
            m.getNo(),m.getName(),m.getEmail(),m.getPassword(),m.getPhoto(),m.getTel(),m.getRegisteredDate()));
      }
      System.out.println("회원 데이터 저장!");

    } catch (Exception e) {
      System.out.println("회원 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadProjects() {

    try (Scanner in = new Scanner(new FileInputStream("projects.csv"))) {

      while (true) {
        try {
          String record = in.nextLine();
          String[] fields = record.split(",");
          Project p = new Project();

          p.setNo(Integer.parseInt(fields[0]));
          p.setTitle(fields[1]);
          p.setContent(fields[2]);
          p.setStartDate(Date.valueOf(fields[3]));
          p.setEndDate(Date.valueOf(fields[4]));
          p.setOwner(fields[5]);
          p.setMembers(fields[6].replace("|", ","));

          projectList.add(p);
        } catch (NoSuchElementException e) {
          break;
        }
      }  
      System.out.println("프로젝트 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("프로젝트 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveProjects() {

    try (FileWriter out = new FileWriter("projects.csv")) {
      for (Project p : projectList) {
        out.write(String.format("%d,%s,%s,%s,%s,%s,%s\n",
            p.getNo(),p.getTitle(),p.getContent(),p.getStartDate(),p.getEndDate(),p.getOwner(),p.getMembers().replace(",", "|")));
      }
      System.out.println("프로젝트 데이터 저장!");

    } catch (Exception e) {
      System.out.println("프로젝트 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadTasks() {

    try (Scanner in = new Scanner(new FileInputStream("tasks.csv"))) {
      while (true) {
        try {
          String record = in.nextLine();
          String[] fields = record.split(",");
          Task t = new Task();

          t.setNo(Integer.parseInt(fields[0]));
          t.setContent(fields[1]);
          t.setDeadline(Date.valueOf(fields[3]));
          t.setStatus(Integer.parseInt(fields[4]));
          t.setOwner(fields[5]);

          taskList.add(t);
        } catch (NoSuchElementException e) {
          break;
        }
      } 
      System.out.println("작업 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("작업 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveTasks() {

    try (FileWriter out = new FileWriter("tasks.csv")) {
      for (Task t : taskList) {
        out.write(String.format("%d,%s,%s,%s,%s\n",
            t.getNo(),t.getContent(),t.getDeadline(),t.getStatus(),t.getOwner()));
      }
      System.out.println("작업 데이터 저장!");

    } catch (Exception e) {
      System.out.println("작업 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }
}
