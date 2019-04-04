import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api'
import { Product } from '../entity/product'
@Injectable({
  providedIn: 'root'
})
export class InMemoryDataServiceService implements InMemoryDbService{
  createDb(){
    const products = [
      new Product(1,"华为nova 4e", "华为"),
      new Product(2,"苹果iPhone XS Max", "苹果", 123),
      new Product(3,"三星Galaxy S10", "三星", 2999),
      new Product(4,"华为Mate20", "华为"),
      new Product(5,"OPPO R17 Pro", "OPPO"),
      new Product(6,"vivo X27", "vivo"),
      new Product(7,"一加6T", "一加"),
      new Product(8,"华为P30", "华为"),
      new Product(9,"小米9", "小米"),
      new Product(10,"荣耀v20", "荣耀"),
      new Product(11,"魅族Note9", "魅族"),
      new Product(12,"OPPO Find X", "OPPO"),
      new Product(13,"苹果iPhone X", "苹果"),
      new Product(14,"荣耀8X", "荣耀"),
      new Product(15,"OPPO K1", "OPPO"),
      new Product(16,"vivo IQOO", "vivo"),
      new Product(17,"小米9 SE", "小米"),
      new Product(18,"OPPO A5", "OPPO"),
      new Product(19,"苹果iPhone8", "苹果"),
      new Product(20,"小米红米7", "小米"),
      new Product(21,"vivo Y93", "vivo"),
      new Product(22,"荣耀10", "荣耀"),
      new Product(23,"OPPO R15x", "OPPO"),
      new Product(24,"vivo X21", "vivo"),
      new Product(25,"华为Mate 20 Pro", "华为"),
      new Product(26,"三星Galaxy Fold", "三星"),
      new Product(27,"一加7", "一加"),
      new Product(28,"努比亚红魔", "努比亚"),
      new Product(29,"vivo X9", "vivo")
    ];
    return products; 
  }

  constructor() { }
}
