import { Observer } from 'rxjs';

export class ResponseObserver implements Observer<any> {
    closed?: boolean;

    constructor(private handler: any) { }

    static handle(handler) {
        return new ResponseObserver(handler);
    }

    next(value: any) {
        this.handler(value);
    }

    error(err: any) { }

    complete() { }
}
