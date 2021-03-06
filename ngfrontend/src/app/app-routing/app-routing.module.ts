import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommitComponent } from '../components/commit/commit.component';
import { ScratchComponent } from '../components/scratch/scratch.component';
import { PageNotFoundComponent } from '../components/page-not-found/page-not-found.component';
import { SearchComponent } from '../components/search/search.component';
import { SearchPageComponent } from '../components/search-page/search-page.component';
import { ProductDetailComponent } from '../components/product-detail/product-detail.component';
const appRoutes:Routes = [
  {
    path: "commit", component: CommitComponent
  },
  {
    path: "scratch", component: ScratchComponent
  },
  {
    path: "search", component: SearchComponent
  },
  {
    path: "search/:query", component: SearchPageComponent
  },
  {
    path: "detail/:id", component:ProductDetailComponent
  },
  {
    path: "", component: SearchComponent
  },
  {
    path: "**", component: PageNotFoundComponent
  }
]
@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(
      appRoutes
    ),
  ]
})
export class AppRoutingModule { }
