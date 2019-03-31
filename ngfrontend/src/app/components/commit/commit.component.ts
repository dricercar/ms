import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Product } from 'src/app/entity/product';
import { CommitService } from 'src/app/services/commit.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-commit',
  templateUrl: './commit.component.html',
  styleUrls: ['./commit.component.css']
})
export class CommitComponent implements OnInit {

  constructor(private commitService: CommitService,
          private route: ActivatedRoute,
          private router: Router) { }

  @ViewChild('file')
  inputFile: ElementRef;

  model = new Product();

  ngOnInit() {
  }

  commit(){
    console.log(this.inputFile.nativeElement.files[0]);
    let formData = new FormData();
    if(this.model.name)
      formData.append("name", this.model.name);
    if(this.model.brand)
      formData.append("brand", this.model.brand);
    if(this.model.price)
      formData.append("price", ''+this.model.price);
    
    formData.append("img", this.inputFile.nativeElement.files[0]);
    if(this.model.type)
      formData.append("type", this.model.type);
    if(this.model.os)
      formData.append("os", this.model.os);
    if(this.model.cpu)
      formData.append("cpu", this.model.cpu);
    if(this.model.size)
      formData.append("size", this.model.size);
    console.log("size: " + this.model.size);
    this.commitService.addProduct(formData).subscribe(result => {
      if(result.code == '200'){
        alert("提交成功");
        this.model = new Product();
      }else
        alert(result.msg);
    });
  }
}
