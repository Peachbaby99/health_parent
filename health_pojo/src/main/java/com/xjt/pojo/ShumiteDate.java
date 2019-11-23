package com.xjt.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/16 下午 12:18
 */
public class ShumiteDate implements Serializable {
 private   Integer number;
 private Object date;

 @Override
 public String toString() {
  return "ShumiteDate{" +
          "number=" + number +
          ", date=" + date +
          '}';
 }

 public Integer getNumber() {
  return number;
 }

 public void setNumber(Integer number) {
  this.number = number;
 }

 public Object getDate() {
  return date;
 }

 public void setDate(Object date) {
  this.date = date;
 }
}
