import React from 'react';
import './App.css';
import { Cards } from './Cards';

import { Container, Row } from 'react-bootstrap';
import 'react-bootstrap';

// const admin = require('firebase-admin');
// let serviceAccount = require('./eventfinder-c285d-firebase-adminsdk-eyu13-ae33843c48.json');

// admin.initializeApp({
//   credential: admin.credential.cert(serviceAccount),
//   databaseURL: "https://eventfinder-c285d.firebaseio.com"
// });

// console.log("xxxxxxxxxxxx",admin)
// let db = admin.firestore();
// console.log("xxxxxxxxxxxx",db.collection('bar'))

// db.collection('users').get()
//   .then((snapshot) => {
//     snapshot.forEach((doc) => {
//       console.log("xxxxxx", doc.id, '=>', doc.data());
//     });
//   })
//   .catch((err) => {
//     console.log("xxxxx", 'Error getting documents', err);
//   });

export const Home = () => {
  return (
    <div className="App-main">
      <Container>
        <p>This is home</p>
        <Row>
          <Cards name="Opium" loc="Camdon St, Dublin" dis="5km" />
          <Cards name="Dicies" loc="Hardcort St, Dublin" dis="5km"/>
          <Cards name="Coppers" loc="Hardcort St, Dublin" dis="5km"/>
          <Cards name="Adams House" loc="Dicksborough, Kilkenny" dis="100km" />
          <Cards name="Dicies" loc="Hardcort St, Dublin" dis="5km"/>
        </Row>
      </Container>
    </div>
  );
}