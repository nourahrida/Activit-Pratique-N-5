import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public loginForm!:FormGroup;

  constructor(private fb :FormBuilder ,
              private autService :AuthService,
              private router :Router) {
  }
  ngOnInit(): void {
    this.loginForm= this.fb.group({
      username:this.fb.control(''),
      password:this.fb.control('')

    });
  }


  login() {
    let username = this.loginForm.value.username;
    let pass = this.loginForm.value.password;

    let aut: boolean = this.autService.login(username, pass);
    console.log("tee"+aut);
    if (aut == true) {
      console.log("tee");
      this.router.navigateByUrl("/admin");
    }
    else{
      this.router.navigateByUrl("");
    }

  }
}
