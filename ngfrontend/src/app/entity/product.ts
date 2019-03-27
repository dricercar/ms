export class Product {
    constructor(
        public name?:string,
        public brand?:string,
        public price?:number,
        public img?:FileList,
        public type?:string,
        public os?:string,
        public cpu?:string,
        public size?:string
    ){}
}
