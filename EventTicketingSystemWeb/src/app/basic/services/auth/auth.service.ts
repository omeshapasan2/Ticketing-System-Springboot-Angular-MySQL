import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';
import { UserStorageService } from '../storage/user-storage.service';

const BASIC_URL = 'http://localhost:8080/';
export const AUTH_HEADER = 'Authorization'; // Corrected capitalization

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private http: HttpClient,
    private userStorageService: UserStorageService
  ) {}

  registerCustomer(signupRequestDTO: any): Observable<any> {
    return this.http.post(BASIC_URL + 'customer/signup', signupRequestDTO);
  }

  registerVendor(signupRequestDTO: any): Observable<any> {
    return this.http.post(BASIC_URL + 'vendor/signup', signupRequestDTO);
  }

  login(username: string, password: string): Observable<any> {
    return this.http
      .post(BASIC_URL + 'authenticate', { username, password }, { observe: 'response' })
      .pipe(
        map((res: HttpResponse<any>) => {
          console.log(res.body);
          this.userStorageService.saveUser(res.body);

          // Corrected handling of token extraction
          const token = res.headers.get(AUTH_HEADER)?.replace('Bearer ', '');
          console.log(token);
          if (token) {
            this.userStorageService.saveToken(token);
          }

          return res;
        }),
        catchError((error: HttpErrorResponse) => {
          console.error('Error during login:', error);
          return throwError(() => error);
        })
      );
  }
}
