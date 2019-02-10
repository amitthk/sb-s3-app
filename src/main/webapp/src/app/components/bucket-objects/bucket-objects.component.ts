import { Component, OnInit } from '@angular/core';
import { Sbs3appService } from '../../services/sbs3app.service';

export class S3BucketDetailsModel {
  bucketName: string;
  listOfKeys: string[];
}

@Component({
  selector: 'app-bucket-objects',
  templateUrl: './bucket-objects.component.html',
  styleUrls: ['./bucket-objects.component.css']
})
export class BucketObjectsComponent implements OnInit {

  public s3BucketDetailsModel: S3BucketDetailsModel;
  public inProgress: Boolean;
  public isSuccess: Boolean;
  public isFailed: Boolean;
  public percentComplete: Number;
  public isError: Boolean;

  constructor(private sbs3appService: Sbs3appService) { }

  ngOnInit() {
    this.s3BucketDetailsModel = new S3BucketDetailsModel();
  }

  submitRequest() {


    this.inProgress = true;
    this.percentComplete = 25;
    this.sbs3appService.getS3bucketObjectRequest(this.s3BucketDetailsModel.bucketName).subscribe((response: string[]) => {
      this.s3BucketDetailsModel.listOfKeys = response;
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
