package doubleone.mobilesearch.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

public class Product {

	private Long id;
	// @JSONField(name = "名称")
	private String name;
	// @JSONField(name = "品牌")
	private String brand;
	// @JSONField(name = "价格")
	private String price;
	// @JSONField(name = "imgUrl")
	private String imgUrl;

	private String type;
	private String os;
	private String cpu;
	private String size;
	private String path;
	public Product(){
	}
	public Product(String name, String brand, String price, String imgUrl, String type, String os, String cpu, String size){
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.imgUrl = imgUrl;
		this.type = type;
		this.os = os;
		this.cpu = cpu; 
		this.size = size;
	}
	public String getBrand() {
		return brand;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return JSON.toJSONString(this);
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}