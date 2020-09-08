import * as firebase from 'firebase/app';
import 'firebase/auth';

const app = firebase.initializeApp({
  apiKey:"AIzaSyCHTwKUUY_WNdPm4c4raraIRzxZntSkc6M",
  authDomain:"eventfinder-c285d.firebaseapp.com",
  databaseURL:"https://eventfinder-c285d.firebaseio.com",
  projectId:"eventfinder-c285d",
  storageBucket:"eventfinder-c285d.appspot.com",
  messagingSenderId:846290533313,
});

export default app;