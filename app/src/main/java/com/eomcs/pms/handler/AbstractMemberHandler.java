package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Member;

public abstract class AbstractMemberHandler implements Command {

  protected List<Member> memberList;

  public AbstractMemberHandler (List<Member> memberList) {
    this.memberList = memberList;
  }

  //  public String inputMember(String promptTitle) {
  //    while (true) {
  //      String name = Prompt.inputString(promptTitle);
  //      if (name.length() == 0) {
  //        return null;
  //      } 
  //      if (findByName(name) != null)
  //        return name;
  //
  //      System.out.println("등록된 회원이 아닙니다.");
  //    }
  //  }
  //
  //  public String inputMembers(String promptTitle) {
  //    String members = "";
  //    while (true) {
  //      String name = inputMember(promptTitle);
  //      if (name == null) {
  //        return members;
  //      } else {
  //        if (!members.isEmpty()) {
  //          members += ",";
  //        }
  //        members += name;
  //      }
  //    }
  //  }

  protected Member findByNo(int memberNo) {
    Member[] list= memberList.toArray(new Member[memberList.size()]);
    for (Member m : list) {
      if (m.getNo() == memberNo) {
        return m;
      }
    }
    return null;
  }

  //  private Member findByName(String name) {
  //    Member[] list= memberList.toArray(new Member[memberList.size()]);
  //    for (Object obj : list) {
  //      Member m = (Member) obj;
  //      if (m.getName().equals(name)) {
  //        return m;
  //      }
  //    }
  //    return null;
  //  }
}






