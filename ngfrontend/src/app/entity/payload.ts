export class Payload<T> {
    constructor(public code:string,
        public msg:string,
        public data:T){
            
        }
}
