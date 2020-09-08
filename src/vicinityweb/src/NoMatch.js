import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

import {Container, Button} from 'react-bootstrap';
import 'react-bootstrap';

export const NoMatch = () => {  return (
    <div className="App-main">
    <Container>
      <h1>404 Page not found</h1>
      <Button href="/home">Return Home</Button>
    </Container>
    </div>
  );
}
