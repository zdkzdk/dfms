package cn.dc.fms.orm;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "t_bumen")
public class TBumen implements Serializable
{
	@Id
	@Column(name="id")
	private int id;
	@Column(name="mingcheng")
	private String mingcheng;
	@Column(name="renshu")
	private String renshu;
	@Column(name="xishu")
	private String xishu;
	@Column(name="del")
	private String del;

	//πÿ¡™ Ù–‘
	@OneToMany(mappedBy = "bumen")
	private Set<TZhigong> zhigongs;






	public Set<TZhigong> getZhigongs() {
		return zhigongs;
	}
	public void setZhigongs(Set<TZhigong> zhigongs) {
		this.zhigongs = zhigongs;
	}
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
	public String getRenshu() {
		return renshu;
	}
	public void setRenshu(String renshu) {
		this.renshu = renshu;
	}
	public String getXishu() {
		return xishu;
	}
	public void setXishu(String xishu) {
		this.xishu = xishu;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
}
