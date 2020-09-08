import React, { useCallback, useContext } from "react";
import { withRouter, Redirect } from "react-router";
import app from './base';
import { AuthContext } from "./Auth.js";

import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import 'react-bootstrap';

const Login = ({ history }) => {
  const handleLogin = useCallback(
    async event => {
      event.preventDefault();
      const { email, password } = event.target.elements;
      try {
        await app
          .auth()
          .signInWithEmailAndPassword(email.value, password.value);
        history.push("/home");
      } catch (error) {
        alert(error);
      }
    },
    [history]
  );

  const { currentUser } = useContext(AuthContext);

  if (currentUser) {
    return <Redirect to="/home" />;
  }

  return (
    // <div>
    //   <h1>Log in</h1>
    //   <form onSubmit={handleLogin}>
    //     <label>
    //       Email
    //       <input name="email" type="email" placeholder="Email" />
    //     </label>
    //     <label>
    //       Password
    //       <input name="password" type="password" placeholder="Password" />
    //     </label>
    //     <button type="submit">Log in</button>
    //   </form>
    // </div>

    <div className="App-main">
      <Container>
        <Row>
          <Col md={{ span: 4, offset: 4 }}>
            <h1>LogIn</h1>
            <Form onSubmit={handleLogin}>
              <Form.Group controlId="formBasicEmail">
                <Form.Label>Email address</Form.Label>
                <Form.Control name="email" type="email" placeholder="Email" />
                <Form.Text className="text-muted">
                  We'll never share your email with anyone else.
                             </Form.Text>
              </Form.Group>
              <Form.Group controlId="formBasicPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control name="password" type="password" placeholder="Password" />
              </Form.Group>
              <Button type="submit">Log In</Button>
            </Form>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default withRouter(Login);
