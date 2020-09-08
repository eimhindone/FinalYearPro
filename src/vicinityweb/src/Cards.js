
import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

import { Col, Card } from 'react-bootstrap';
import 'react-bootstrap';


export const Cards = (props) =>
    <Col xs="12" md="6" lg="4">
        <Card className="mt-4">
            <Card.Img variant="top" src={require('./images/holder2.png')} />
            <Card.Body>
                <Card.Title className="App-card-head">{props.name}</Card.Title>
                <Card.Text className="App-card-body">
                    Name: {props.name}
                </Card.Text>
                <Card.Text className="App-card-body">
                    Location: {props.loc}
                </Card.Text>
            </Card.Body>
            <Card.Footer>
                <small className="text-muted">Distance: {props.dis}</small>
            </Card.Footer>
        </Card>
    </Col>
