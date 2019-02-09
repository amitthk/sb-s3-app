import { Component, OnInit } from '@angular/core';
import { Sbs3appService } from '../../services/sbs3app.service';
import { S3LoginModel, BucketDetailsModel } from '../../models/';


@Component({
  selector: 'app-bucket-objects',
  templateUrl: './bucket-objects.component.html',
  styleUrls: ['./bucket-objects.component.css']
})
export class BucketObjectsComponent implements OnInit {

  public result: BucketDetailsModel[];
  public loginModel: S3LoginModel;
  public inProgress: Boolean;
  public isSuccess: Boolean;
  public isFailed: Boolean;
  public percentComplete: Number;
  public isError: Boolean;

  constructor(private sbs3appService: Sbs3appService) { }

  ngOnInit() {
    this.result = [];
    this.loginModel = new S3LoginModel();
  }

  submitRequest() {

    if (this.loginModel.access_key_id === '' || this.loginModel.secret_access_key === '') {
      alert('Access_key_id and secret_access_key cannot be null!');
      return;
    }

    this.inProgress = true;
    this.percentComplete = 25;
    this.sbs3appService.postS3bucketObjectRequest(this.loginModel).subscribe((response: BucketDetailsModel[]) => {
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
    this.isError = true;
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
