import { HttpInterceptor, HttpHandler, HttpEvent, HttpRequest, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { DialogBoxService, DialogType } from '../utils';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

    constructor(private dialogBox: DialogBoxService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req).pipe(map((event: HttpEvent<any>) => {
            if (event instanceof HttpResponse && event.body.code) {
                if (event.body.code === 200) {
                    let body = null;
                    if (event.body.body != null) {
                        body = event.body.body;
                    } else if (event.body.message != null) {
                        body = event.body.message;
                    }
                    return event.clone({
                        body: body,
                        headers: event.headers,
                        status: event.status,
                        statusText: event.statusText,
                        url: event.url
                    });
                } else {
                    throw new HttpErrorResponse({
                        headers: event.headers,
                        status: 500,
                        statusText: event.statusText,
                        url: event.url,
                        error: {
                            message: event.body.message
                        }
                    });
                }
            }
            return event;
        })).pipe(catchError(err => {
            const error = err.error.message || err.statusTest;
            this.dialogBox.showLoading = false;
            this.dialogBox.alert(DialogType.ERROR, error);
            return throwError(error);
        }));
    }
}
