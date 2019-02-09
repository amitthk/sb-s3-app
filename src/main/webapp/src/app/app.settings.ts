import { environment } from '../environments/environment';

export class AppSettings {
    public static envEndpoints = new Map([['local', 'http://localhost:8081/'],[ 'dev', '/sbs3appapi/'],[ 'sit', '/sbs3appapi/'], [ 'uat', '/sbs3appapi/'],[ 'prod', '/sbs3appapi/']]);
    public static API_ENDPOINT= AppSettings.envEndpoints.get(environment.env);
  }
