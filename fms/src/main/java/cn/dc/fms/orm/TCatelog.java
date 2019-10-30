package cn.dc.fms.orm;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_catelog")
public class TCatelog {
    @Id
    @Column(name="id")
    private int id;
    private String name;
    private String del;



    /*
     * πÿ¡™ Ù–‘*/
    @OneToMany(mappedBy = "catelog")
    private Set<TZichan> zichans;


    public Set<TZichan> getZichans() {
        return zichans;
    }

    public void setZichans(Set<TZichan> zichans) {
        this.zichans = zichans;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }
}
