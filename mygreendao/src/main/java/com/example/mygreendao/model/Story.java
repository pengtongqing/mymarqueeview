package com.example.mygreendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by user on 2016/9/26.
 */
@Entity
public class Story {
  @Id
  private String fid;
  private String fn;
  private String artist;
  private String spell;
  private int ds;

  @Generated(hash = 1470378243)
  public Story(String fid, String fn, String artist, String spell, int ds) {
    this.fid = fid;
    this.fn = fn;
    this.artist = artist;
    this.spell = spell;
    this.ds = ds;
  }

  @Generated(hash = 922655990)
  public Story() {
  }

  public String getFid() {
    return fid;
  }

  public void setFid(String fid) {
    this.fid = fid;
  }

  public String getFn() {
    return fn;
  }

  public void setFn(String fn) {
    this.fn = fn;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public String getSpell() {
    return spell;
  }

  public void setSpell(String spell) {
    this.spell = spell;
  }

  public int getDs() {
    return ds;
  }

  public void setDs(int ds) {
    this.ds = ds;
  }
}
