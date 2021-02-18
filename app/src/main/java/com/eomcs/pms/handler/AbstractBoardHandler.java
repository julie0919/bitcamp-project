package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Board;

// 이 클래스를 상속 받는 서브 클래스는
// 반드시 Command 규칙을 따르도록 강제한다.
public abstract class AbstractBoardHandler implements Command {

  protected List<Board> boardList;

  public AbstractBoardHandler(List<Board> boardList) {
    this.boardList = boardList;
  }

  protected Board findByNo(int boardNo) {
    Board[] list = boardList.toArray(new Board[0]);
    for (Board b : list) {
      if (b.getNo() == boardNo) {
        return b;
      }
    }
    return null;
  }

}






