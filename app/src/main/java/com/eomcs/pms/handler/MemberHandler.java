package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberHandler {

  static final int LENGTH = 100;
  Member[] members = new Member[LENGTH];  // 레퍼런스 배열 준비  
  int size = 0;

  public void add() {
    System.out.println("[회원 등록]");

    Member m = new Member();

    m.no = Prompt.inputInt("번호? ");
    m.name = Prompt.inputString("이름? ");
    m.email = Prompt.inputString("이메일? ");
    m.password = Prompt.inputString("암호? ");
    m.photo = Prompt.inputString("사진? ");
    m.tel = Prompt.inputString("전화? ");
    m.registeredDate = new java.sql.Date(System.currentTimeMillis());

    this.members[this.size++] = m;


  }

  public void list() {
    System.out.println("[회원 목록]");

    for (int i = 0; i < this.size; i++) {
      Member m = this.members[i];
      // 번호, 이름, 이메일, 전화, 가입일
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          m.no, m.name, m.email, m.tel, m.registeredDate);
    }
  }

  public boolean exist(String name) {
    for (int i = 0; i < this.size; i++) {
      if (name.equals(this.members[i].name)) {
        return true;
      }
    }
    return false;
  }


  public void detail() {
    System.out.println("[멤버 상세보기]");

    int no = Prompt.inputInt("번호? ");

    Member member = findByNo(no);
    if (member == null) {
      System.out.println("해당 번호의 멤버가 없습니다.");
      return;
    }
    System.out.printf("이름: %s\n", member.name);
    System.out.printf("이메일: %s\n", member.email);
    System.out.printf("암호: %s\n", member.password);
    System.out.printf("사진: %s\n", member.photo);
    System.out.printf("전화: %s\n", member.tel);
    System.out.printf("등록일: %s\n", member.registeredDate);
  }

  public void update() {
    System.out.println("[멤버 변경]");

    int no = Prompt.inputInt("번호? ");


    Member member = findByNo(no);
    if (member == null) {
      System.out.println("해당 번호의 멤버가 없습니다.");
      return;
    }

    String name = Prompt.inputString(String.format("제목(%s)?", member.name));
    String email = Prompt.inputString(String.format("내용(%s)?", member.email));
    String password = Prompt.inputString(String.format("비밀번호(%s)?", member.password));
    String photo = Prompt.inputString(String.format("사진(%s)?", member.photo));
    String tel = Prompt.inputString(String.format("전화(%s)?", member.tel));

    String input = Prompt.inputString(String.format("정말 변경하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      member.name = name;
      member.email = email;
      member.password = password;
      member.photo = photo;
      member.tel = tel;

      System.out.println("멤버정보를 변경하였습니다.");
    } else {
      System.out.println("멤버 변경을 취소하였습니다.");
    }
  }

  public void delete() {
    System.out.println("[멤버 삭제]");

    int no = Prompt.inputInt("번호? ");

    int i = indexOf(no);
    if (i == -1) {
      System.out.println("해당 번호의 멤버가 없습니다.");
      return;
    }
    String input = Prompt.inputString(String.format("정말 삭제하시겠습니까?(y/N)"));

    if (input.equalsIgnoreCase("Y")) {
      for (int x = i + 1; x < this.size; x++) {
        this.members[x-1] = this.members[x];
      }
      members[--this.size] = null; // 앞으로 당긴 후 맨 뒤의 항목은 null로 설정한다. (가비지관리를 효율적으로 하기 위해서)
      System.out.println("멤버를 삭제하였습니다.");
    } else {
      System.out.println("멤버 삭제를 취소하였습니다.");
    }
  }

  int indexOf(int memberNo) {
    for (int i = 0; i < this.size; i++) {
      Member member = this.members[i];
      if (member.no == memberNo) { 
        return i;
      }
    }
    return -1;
  }

  Member findByNo(int memberNo) {
    int i = indexOf(memberNo);
    if (i == -1)
      return null;
    else
      return members[i];
  }




}






