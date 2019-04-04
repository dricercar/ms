import { Product } from './product';

export class ProductPage<T> {
    constructor(
        public totalPage: number,
        public currentPage: number,
        public products: T
    ){}
}
