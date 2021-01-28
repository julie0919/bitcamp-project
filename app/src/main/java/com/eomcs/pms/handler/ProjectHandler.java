package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Project;
import com.eomcs.util.Prompt;

public class ProjectHandler {

  static final int LENGTH = 100;

  // 의존 객체 (dependency) 를 담을 인스턴스 필드
  // 메서드가 작업할 때 사용할 객체를 담는다.
  public MemberHandler memberList;

  Project[] projects = new Project[LENGTH];
  int size = 0;

  // 생성자 정의
  // - ProjectHanler가 의존하는 객체를 반드시 주입하도록 강요한다.
  // - 다른 패키지에서 생성자를 호출할 수 있도록 공개한다.
  public ProjectHandler(MemberHandler memberHandler) {
    this.memberList = memberHandler;
  }

  public void add() {
    System.out.println("[프로젝트 등록]");

    Project p = new Project();
    p.no = Prompt.inputInt("번호? ");
    p.title = Prompt.inputString("프로젝트명? ");
    p.content = Prompt.inputString("내용? ");
    p.startDate = Prompt.inputDate("시작일? ");
    p.endDate = Prompt.inputDate("종료일? ");

    while (true) {
      String name = Prompt.inputString("만든이?(취소: 빈 문자열) ");
      if (name.length() == 0) {
        System.out.println("프로젝트 등록을 취소합니다.");
        return;
      } 
      if (this.memberList.exist(name)) {
        p.owner = name;
        break;
      }
      System.out.println("등록된 회원이 아닙니다.");
    }

    p.members = "";
    while (true) {
      String name = Prompt.inputString("팀원?(완료: 빈 문자열) ");
      if (name.length() == 0) {
        break;
      } else if (this.memberList.exist(name)) {
        if (!p.members.isEmpty()) {
          p.members += ",";
        }
        p.members += name;
      } else {
        System.out.println("등록된 회원이 아닙니다.");
      }
    }

    projects[size++] = p;
  }

  public void list() {
    System.out.println("[프로젝트 목록]");

    for (int i = 0; i < size; i++) {
      Project p = projects[i];
      System.out.printf("%d, %s, %s, %s, %s, [%s]\n",
          p.no, p.title, p.startDate, p.endDate, p.owner, p.members);
    }
  }

  public void detail() {
    System.out.println("[프로젝트 상세보기]");

    int no = Prompt.inputInt("번호? ");

    Project project = findByNo(no);
    if (project == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }
    System.out.printf("프로젝트명: %s\n", project.title);
    System.out.printf("내용: %s\n", project.content);
    System.out.printf("시작일: %s\n", project.startDate);
    System.out.printf("종료일: %s\n", project.endDate);
    System.out.printf("만든이: %d\n", project.owner);
    System.out.printf("팀원: %d\n", project.members);

  }

  public void update() {
    System.out.println("[프로젝트 변경]");

    int no = Prompt.inputInt("번호? ");


    Project project = findByNo(no);
    if (project == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }

    String title = Prompt.inputString(String.format("프로젝트명(%s)?", project.title));
    String content = Prompt.inputString(String.format("내용(%s)?", project.content));
    String owner = Prompt.inputString(String.format("만든이(%s)?", project.owner));
    String members = Prompt.inputString(String.format("팀원(%s)?", project.members));


    String input = Prompt.inputString(String.format("정말 변경하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      project.title = title;
      project.content = content;
      project.owner = owner;
      project.members = members;

      System.out.println("프로젝트 정보를 변경하였습니다.");
    } else {
      System.out.println("프로젝트 변경을 취소하였습니다.");
    }
  }

  public void delete() {
    System.out.println("[프로젝트 삭제]");

    int no = Prompt.inputInt("번호? ");

    int i = indexOf(no);
    if (i == -1) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }
    String input = Prompt.inputString(String.format("정말 삭제하시겠습니까?(y/N)"));

    if (input.equalsIgnoreCase("Y")) {
      for (int x = i + 1; x < this.size; x++) {
        this.projects[x-1] = this.projects[x];
      }
      projects[--this.size] = null; // 앞으로 당긴 후 맨 뒤의 항목은 null로 설정한다. (가비지관리를 효율적으로 하기 위해서)
      System.out.println("프로젝트를 삭제하였습니다.");
    } else {
      System.out.println("프로젝트 삭제를 취소하였습니다.");
    }
  }

  int indexOf(int projectNo) {
    for (int i = 0; i < this.size; i++) {
      Project project = this.projects[i];
      if (project.no == projectNo) {
        return i;
      }
    }
    return -1;
  }

  Project findByNo(int projectNo) {
    int i = indexOf(projectNo);
    if (i == -1)
      return null;
    else
      return projects[i];
  }


}








