import React, { useCallback } from "react";
import { withRouter } from "react-router";
import app from './base';

import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import 'react-bootstrap';

const SignUp = ({ history }) => {
  const handleSignUp = useCallback(async event => {
    event.preventDefault();
    const { email, password } = event.target.elements;
    try {
      await app
        .auth()
        .createUserWithEmailAndPassword(email.value, password.value);
      history.push("/interests");
    } catch (error) {
      alert(error);
    }
  }, [history]);

  return (
    <div className="App-main">
      <Container>
        <Row>
          <Col md={{ span: 4, offset: 4 }}>
            <h1>Sign Up</h1>
            <Form onSubmit={handleSignUp}>
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

              <Form.Group controlId="formBasicCheckbox">
                <Form.Check type="checkbox" label="Accept Tearms" />
              </Form.Group>

              <Button type="submit">Sign Up</Button>
            </Form>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default withRouter(SignUp);

