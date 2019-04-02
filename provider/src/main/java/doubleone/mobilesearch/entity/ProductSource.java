package doubleone.mobilesearch.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "t_productsource")
public class ProductSource{

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="url")
    private String url;


    @Column(name="create_time")
    private Long createTime;

    public ProductSource (){
    }
    public ProductSource(String name, String url){
        this(name, url, null);
    }
    public ProductSource(String name, String url, Long createTime){
        this.name = name;
        this.url = url;
        this.createTime = createTime;
    }
    @Override
    public String toString(){
        return "{\"name\":\"" + name + "\"," +
                "\"url:\"" + url + "\"}";
    }
    public Long getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Long createTime){
        this.createTime = createTime;
    }
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }
}