import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import { Injectable } from "@angular/core";
import { AuthService } from "../services/auth.service";

@Injectable()
export class AuthorizationGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree {
    if (this.authService.isAuthenticated) {
      const requiredRoles = route.data['roles'];
      const userRoles: string[] = this.authService.roles;

      for (let role of userRoles) {
        if (role.includes(requiredRoles)) {
          return true;
        }
      }
      return false;
    } else {
      // Si l'utilisateur n'est pas authentifié
      this.router.navigateByUrl("/login");
      return false;
    }
  }
}
