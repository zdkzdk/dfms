package cn.dc.fms.orm;

import javax.persistence.*;

@Entity
@Table(name = "t_zhigong")
public class TZhigong
{
	@Id
	@Column(name="id")
	private int id;
	@Column(name="bumen_id")
	private int bumen_id;
	private String bianhao;
	private String loginpw;
	private String xingming;
	private String xingbie;
	private String ruzhi;
	private String del;
	//πÿ¡™ Ù–‘
	private String bmmc;
	private double xishu;
	@ManyToOne
	@JoinColumn(name="bumen_id",insertable = false,updatable = false)
	private TBumen bumen;



	@OneToOne(mappedBy = "zgxx",cascade = CascadeType.ALL)
	private TGongzi gongzi;





	public TGongzi getGongzi() {
		return gongzi;
	}

	public void setGongzi(TGongzi gongzi) {
		this.gongzi = gongzi;
	}



	public TBumen getBumen() {
		return bumen;
	}

	public void setBumen(TBumen bumen) {
		this.bumen = bumen;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getBumen_id() {
		return bumen_id;
	}
	public void setBumen_id(int bumen_id) {
		this.bumen_id = bumen_id;
	}
	public String getBianhao() {
		return bianhao;
	}
	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
	}
	public String getLoginpw() {
		return loginpw;
	}
	public void setLoginpw(String loginpw) {
		this.loginpw = loginpw;
	}
	public String getXingming() {
		return xingming;
	}
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	public String getXingbie() {
		return xingbie;
	}
	public void setXingbie(String xingbie) {
		this.xingbie = xingbie;
	}
	public String getRuzhi() {
		return ruzhi;
	}
	public void setRuzhi(String ruzhi) {
		this.ruzhi = ruzhi;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getBmmc() {
		return bmmc;
	}
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
	public double getXishu() {
		return xishu;
	}
	public void setXishu(double xishu) {
		this.xishu = xishu;
	}
}
