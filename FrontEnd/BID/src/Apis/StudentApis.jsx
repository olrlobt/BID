import axios from "axios";

export default class StudentApis {
  constructor() {
    this.httpClient = axios.create();
  }
}
