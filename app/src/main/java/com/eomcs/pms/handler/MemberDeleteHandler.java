package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberDeleteHandler extends AbstractMemberHandler{

  public MemberDeleteHandler(List<Member> memberList) {
    super(memberList);
  }

  @Override
  public void service() {
    System.out.println("[회원 삭제]");

    int no = Prompt.inputInt("번호? ");

    Member member = findByNo(no);
    if (member == null) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      memberList.remove(member);
      System.out.println("회원을 삭제하였습니다.");

    } else {
      System.out.println("회원 삭제를 취소하였습니다.");
    }

  }
}






