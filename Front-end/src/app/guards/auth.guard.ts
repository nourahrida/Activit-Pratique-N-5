import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import {inject, Injectable} from "@angular/core";
import {AuthService} from "../services/auth.service";
@Injectable()
export class AuthGuard {
  constructor(private authService :AuthService ,
              private router:Router) {
  }
  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot):boolean {
   if( this.authService.isAuthenticated)
    return true;
   else{
     this.router.navigateByUrl("/login");
     return false;
   }
  }
}
