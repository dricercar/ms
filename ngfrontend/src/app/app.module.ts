import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing/app-routing.module';
import { SearchComponent } from './components/search/search.component';
import { CommitComponent } from './components/commit/commit.component';
import { HttpClientModule } from '@angular/common/http';
import { ScratchComponent } from './components/scratch/scratch.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { RouterModule } from '@angular/router';
import { SearchPageComponent } from './components/search-page/search-page.component';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    SearchComponent,
    CommitComponent,
    ScratchComponent,
    PageNotFoundComponent,
    SearchPageComponent,
    ProductDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    RouterModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
