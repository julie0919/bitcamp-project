package com.eomcs.pms.handler;

import java.util.Iterator;
import java.util.List;
import com.eomcs.pms.domain.Board;

public class BoardListHandler extends AbstractBoardHandler{

  public BoardListHandler(List<Board> boardList) {
    super(boardList);
  }
  @Override
  public void service() {
    System.out.println("[게시글 목록]");

    // Iterator 사용하여 데이터 조회하기

    Iterator<Board> iterator = boardList.iterator();
    while(iterator.hasNext()) {
      Board b = iterator.next();
      // 번호, 제목, 등록일, 작성자, 조회수, 좋아요
      System.out.printf("%d, %s, %s, %s, %d, %d\n", 
          b.getNo(), 
          b.getTitle(), 
          b.getRegisteredDate(), 
          b.getWriter(), 
          b.getViewCount(),
          b.getLike());
    }
  }
}






