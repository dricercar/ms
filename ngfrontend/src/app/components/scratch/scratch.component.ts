import { Component, OnInit } from '@angular/core';
import { ScratchService } from 'src/app/services/scratch.service';
import { of } from 'rxjs';
import { Productsource } from 'src/app/entity/productsource';
import { Product } from 'src/app/entity/product';

@Component({
  selector: 'app-scratch',
  templateUrl: './scratch.component.html',
  styleUrls: ['./scratch.component.css']
})
export class ScratchComponent implements OnInit {
  URL:string;
  constructor(public scratchService: ScratchService) { }
  types: string[];
  type: string;
  productSource: Productsource[]=[];
  timer;
  ngOnInit() {
    this.scratchService.scratchType().subscribe( x => {
      this.types = x;
      this.type = this.types[0];
    });
    
  }
  ngOnDestroy(): void {
    //Called once, before the instance is destroyed.
    //Add 'implements OnDestroy' to the class.
    if(this.timer){
      clearInterval(this.timer);
      this.stop();
    }
  }

  comfirm(){
    alert(this.URL + ' ' + this.type);
    let formData = new FormData();
    if(this.URL && this.URL.length>0)
      formData.append("url", this.URL);
    formData.append("type", this.type);
    this.scratchService.scratch(formData).subscribe( payload =>{
      if(payload.code == "200"){
        alert("开始爬取数据。")
      }else{
        alert("错误： " + payload.msg);
      }
      //每隔5秒获取爬取到的数据
      this.timer = setInterval(() =>{
        this.getScratched();
      }, 5000);
    });

  }
  stop(){
    this.scratchService.stopScratch().subscribe( x => {
      console.log(x);
      //取消定时器
      if(this.timer){
        clearInterval(this.timer);
      }
    })
  }
  getScratched(){
    this.scratchService.getScratched().subscribe(payload =>{
      for(let i = 0; i < payload.data.length; i++){
        console.log(payload.data[i]);
        this.productSource.push(payload.data[i]);
      }
    })
  }
}
