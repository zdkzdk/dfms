package cn.dc.fms.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_feiyong")
public class TFeiyong
{
	@Id
	@Column(name="id")
	private int id;
	private String mingcheng;
	private String shijian;
	private String feiyong;
	private String leixing;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMingcheng() {
		return mingcheng;
	}
	public void setMingcheng(String mingcheng) {
		this.mingcheng = mingcheng;
	}
	public String getShijian() {
		return shijian;
	}
	public void setShijian(String shijian) {
		this.shijian = shijian;
	}
	public String getFeiyong() {
		return feiyong;
	}
	public void setFeiyong(String feiyong) {
		this.feiyong = feiyong;
	}
	public String getLeixing() {
		return leixing;
	}
	public void setLeixing(String leixing) {
		this.leixing = leixing;
	}
}
