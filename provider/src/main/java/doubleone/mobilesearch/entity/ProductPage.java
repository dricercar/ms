package doubleone.mobilesearch.entity;

import java.util.List;

public class ProductPage {
    private Integer totalPage;
    private Integer currentPage;
    private List<Product> products;

    public ProductPage(Integer currentPage, List<Product> products) {
        this(null, currentPage, products);
    }

    public ProductPage(Integer totalPage, Integer currentPage, List<Product> products) {
        this.setTotalPage(totalPage);
        this.setCurrentPage(currentPage);
        this.setProducts(products);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public List<Product> getProducts(){
        return this.products;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}