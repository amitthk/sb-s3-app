import { Component, OnInit } from '@angular/core';
import { Sbs3appService } from '../../services/sbs3app.service';
import { S3LoginModel } from '../../models/';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  public loginModel: S3LoginModel;
  public inProgress: Boolean;
  public isSuccess: Boolean;
  public isFailed: Boolean;
  public percentComplete: Number;
  public result: string;

  constructor(private sbs3appService: Sbs3appService) { }

  ngOnInit() {
    this.result = '';
    this.loginModel = new S3LoginModel();
  }

  submitRequest() {

    if (this.loginModel.access_key_id === '' || this.loginModel.secret_access_key === '') {
      alert('Access_key_id and secret_access_key cannot be null!');
      return;
    }

    this.inProgress = true;
    this.percentComplete = 25;

    this.sbs3appService.postS3bucketRequest(this.loginModel).subscribe((response: string) => {
      this.result = response;
      this.inProgress = false;
      this.percentComplete = 100;
      this.isSuccess = true;

    }, this.handleError,
      this.handleCompleted);
  }

  private handleError(error: any): Promise<any> {
    console.error('An Error has occured: ', error);
    this.inProgress = false;
    this.isFailed = true;
    this.percentComplete = 100;
    return Promise.reject(error.message || error);
  }

  private handleCompleted() {
    console.log('Response Received!');
    this.inProgress = false;
    this.isSuccess = true;
    this.percentComplete = 100;
  }
}
