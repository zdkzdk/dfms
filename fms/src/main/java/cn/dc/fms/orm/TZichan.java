package cn.dc.fms.orm;

import javax.persistence.*;

@Entity
@Table(name = "t_zichan")
public class TZichan {
	@Id
	@Column(name="id")
	@GeneratedValue
	private int id;
	private String bianhao;
	private String mingcheng;
	private String shijian;
	private String jiazhi;
	private String type;
	private String fangshi;
	
	private String strType;
	private String strFangshi;
	private String lbmc;

	@Column(name = "catelog_id")
	private String catelog_id;

	/*
	πÿ¡™ Ù–‘
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "catelog_id",insertable = false,updatable = false)
	private TCatelog catelog;



	public String getCatelog_id() {
		return catelog_id;
	}

	public void setCatelog_id(String catelog_id) {
		this.catelog_id = catelog_id;
	}
	public String getFangshi() {
		return fangshi;
	}

	public void setFangshi(String fangshi) {
		this.fangshi = fangshi;
	}

	public TCatelog getCatelog() {
		return catelog;
	}

	public void setCatelog(TCatelog catelog) {
		this.catelog = catelog;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBianhao() {
		return bianhao;
	}

	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
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

	public String getJiazhi() {
		return jiazhi;
	}

	public void setJiazhi(String jiazhi) {
		this.jiazhi = jiazhi;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrFangshi() {
		return strFangshi;
	}

	public void setStrFangshi(String strFangshi) {
		this.strFangshi = strFangshi;
	}

	public String getLbmc() {
		return lbmc;
	}

	public void setLbmc(String lbmc) {
		this.lbmc = lbmc;
	}
	
}
