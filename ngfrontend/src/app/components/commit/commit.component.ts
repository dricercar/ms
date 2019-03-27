import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Product } from 'src/app/entity/product';
import { CommitService } from 'src/app/services/commit.service';

@Component({
  selector: 'app-commit',
  templateUrl: './commit.component.html',
  styleUrls: ['./commit.component.css']
})
export class CommitComponent implements OnInit {

  constructor(private commitService: CommitService) { }

  @ViewChild('file')
  inputFile: ElementRef;

  model = new Product();

  ngOnInit() {
  }

  commit(){
    console.log(this.inputFile.nativeElement.files[0]);
    let formData = new FormData();
    formData.append("name", this.model.name);
    formData.append("brand", this.model.brand);
    formData.append("price", ''+this.model.price);
    formData.append("img", this.inputFile.nativeElement.files[0]);
    formData.append("type", this.model.type);
    formData.append("os", this.model.os);
    formData.append("cpu", this.model.cpu);
    formData.append("size", this.model.size);
    console.log(formData);
    this.commitService.addProduct(formData).subscribe(result => {
      console.log("aa");
      console.log(result);
    });
  }
}
