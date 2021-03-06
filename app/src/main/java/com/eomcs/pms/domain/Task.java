package com.eomcs.pms.domain;

import java.sql.Date;
import com.eomcs.util.CsvObject;

public class Task implements CsvObject {
  private int no;
  private String content;
  private Date deadline;
  private String owner;
  private int status;

  public Task() {}

  public Task (String csv) {
    String[] fields = csv.split(",");

    this.setNo(Integer.parseInt(fields[0]));
    this.setContent(fields[1]);
    this.setDeadline(Date.valueOf(fields[2]));
    this.setStatus(Integer.parseInt(fields[3]));
    this.setOwner(fields[4]);
  }

  @Override
  public String toString() {
    return "Task [no=" + no + ", content=" + content + ", deadline=" + deadline + ", owner=" + owner
        + ", status=" + status + "]";
  }

  @Override
  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s\n",
        this.getNo(),this.getContent(),this.getDeadline(),this.getStatus(),this.getOwner());
  }

  public static Task valueOfCsv(String csv) {
    String[] fields = csv.split(",");
    Task t = new Task();

    t.setNo(Integer.parseInt(fields[0]));
    t.setContent(fields[1]);
    t.setDeadline(Date.valueOf(fields[3]));
    t.setStatus(Integer.parseInt(fields[4]));
    t.setOwner(fields[5]);
    return t;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((content == null) ? 0 : content.hashCode());
    result = prime * result + ((deadline == null) ? 0 : deadline.hashCode());
    result = prime * result + no;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Task other = (Task) obj;
    if (content == null) {
      if (other.content != null)
        return false;
    } else if (!content.equals(other.content))
      return false;
    if (deadline == null) {
      if (other.deadline != null)
        return false;
    } else if (!deadline.equals(other.deadline))
      return false;
    if (no != other.no)
      return false;
    return true;
  }

  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public Date getDeadline() {
    return deadline;
  }
  public void setDeadline(Date deadline) {
    this.deadline = deadline;
  }
  public String getOwner() {
    return owner;
  }
  public void setOwner(String owner) {
    this.owner = owner;
  }
  public int getStatus() {
    return status;
  }
  public void setStatus(int status) {
    this.status = status;
  }


}
