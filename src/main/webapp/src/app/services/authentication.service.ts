import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { AlertService } from './alert.service';
import 'rxjs/add/operator/map';
import { AppLoginModel } from 'app/models';

@Injectable()
export class AuthenticationService {
    public token: string;
    public apiUrl: string;
    constructor(private http: Http, private alertService: AlertService) {
        // set token if saved in local storage
        const authToken = JSON.parse(localStorage.getItem('authToken'));
        this.token = authToken && authToken.token;
        this.apiUrl = '/api/auth';
    }

    login(appLoginModel: AppLoginModel): Observable<Response> {
        return this.http.post(this.apiUrl + '/login', appLoginModel)
            .map((response: Response) => {
                return response;
            });
    }

    register(appLoginModel: AppLoginModel): Observable<Response> {
        return this.http.post(this.apiUrl + '/register', appLoginModel)
            .map((response: Response) => {
                return response;
            });
    }

    logout(emailAddress: string): void {
        // clear token remove user from local storage to log user out
        this.http.post(this.apiUrl + '/logout', null).map((resp: Response) => {
           this.token = null;
        });
        localStorage.removeItem('authToken');
    }
}