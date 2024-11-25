import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';

const BASIC_URL = 'http://localhost:8080/'
export const AUTH_HEADER = 'authorization';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient,) { }

  registerCustomer(signupRequestDTO:any):Observable<any>{
    return this.http.post(BASIC_URL + "customer/signup" , signupRequestDTO);
  }

  registerVendor(signupRequestDTO:any):Observable<any>{
    return this.http.post(BASIC_URL + "vendor/signup" , signupRequestDTO);
  }

  login(username:string, password:string){
    return this.http.post(BASIC_URL + "authenticate" , { username, password},{observe: 'response'})
      .pipe(
        map((res: HttpResponse<any>) =>{
          console.log(res.body)
          const tokenLength = res.headers.get(AUTH_HEADER)?.length;
          const bearerToken = res.headers.get(AUTH_HEADER)?.substring(7, tokenLength);
          console.log(bearerToken);
          return res;
        })
      );
  }
}
