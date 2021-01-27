package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.Prompt;

public class BoardHandler {

  //공통으로 사용하는 값은 스태틱 필드로 선언한다.
  static final int LENGTH = 100;

  // 개별적으로 관리해야하는 값은 인스턴스 필드로 선언한다.
  Board[] boards = new Board[LENGTH];   
  int size = 0;

  public void add() {
    System.out.println("[게시글 등록]");

    Board b = new Board();

    b.no = Prompt.inputInt("번호? ");
    b.title = Prompt.inputString("제목? ");
    b.content = Prompt.inputString("내용? ");
    b.writer = Prompt.inputString("작성자? ");
    b.registeredDate = new Date(System.currentTimeMillis());

    this.boards[this.size++] = b;

    System.out.println("게시글을 등록하였습니다.");
  }

  public void list() {
    System.out.println("[게시글 목록]");

    for (int i = 0; i < this.size; i++) {
      Board b = this.boards[i];

      if (b == null)
        continue; // 아래로 가지않고 다시 for문으로 돌아가 시작하고 i < this.size가 거짓이 될때까지 반복

      // 번호, 제목, 등록일, 작성자, 조회수, 좋아요
      System.out.printf("%d, %s, %s, %s, %d, %d\n", 
          b.no, 
          b.title, 
          b.registeredDate, 
          b.writer, 
          b.viewCount,
          b.like);
    }
  }

  public void detail() {
    System.out.println("[게시글 상세보기]");

    int no = Prompt.inputInt("번호? ");

    Board board = findByNo(no);
    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }
    board.viewCount++;
    System.out.printf("제목: %s\n", board.title);
    System.out.printf("내용: %s\n", board.content);
    System.out.printf("작성자: %s\n", board.writer);
    System.out.printf("등록일: %s\n", board.registeredDate);
    System.out.printf("조회수: %d\n", board.viewCount);
  }

  public void update() {
    System.out.println("[게시글 변경]");

    int no = Prompt.inputInt("번호? ");


    Board board = findByNo(no);
    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }
    // 아래 문장을 길게 쓰면 이거다 String promptCaption = "제목(" + board.title + ")? ";
    //String str = Prompt.inputString(promptCaption);
    // String promptCaption = String.format("제목(%s)?", board.title);

    String title = Prompt.inputString(String.format("제목(%s)?", board.title));
    String content = Prompt.inputString(String.format("내용(%s)?", board.content));

    String input = Prompt.inputString(String.format("정말 변경하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      board.title = title;
      board.content = content;
      System.out.println("게시글을 변경하였습니다.");
    } else {
      System.out.println("게시글 변경을 취소하였습니다.");
    }
  }

  public void delete() {
    System.out.println("[게시글 삭제]");

    int no = Prompt.inputInt("번호? ");

    int i = indexOf(no);
    if (i == -1) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }
    String input = Prompt.inputString(String.format("정말 삭제하시겠습니까?(y/N)"));

    if (input.equalsIgnoreCase("Y")) {
      for (int x = i + 1; x < this.size; x++) {
        this.boards[x-1] = this.boards[x];
      }
      boards[--this.size] = null; // 앞으로 당긴 후 맨 뒤의 항목은 null로 설정한다. (가비지관리를 효율적으로 하기 위해서)
      System.out.println("게시글을 삭제하였습니다.");
    } else {
      System.out.println("게시글 삭제를 취소하였습니다.");
    }
  }

  // 게시글 번호에 해당하는 인스턴스를 배열에서 찾아 그 인덱스를 리턴한다.
  int indexOf(int boardNo) {
    for (int i = 0; i < this.size; i++) {
      Board board = this.boards[i];
      if (board.no == boardNo) { // board != null && 이 조건은 삭제 후 앞으로 땅겨서 null을 없앴으니까 불필요
        return i;
      }
    }
    return -1;
  }

  // 게시글 번호에 해당하는 인스턴스를 찾아 리턴한다.
  Board findByNo(int boardNo) {
    int i = indexOf(boardNo);
    if (i == -1)
      return null;
    else
      return boards[i];
  }
}






