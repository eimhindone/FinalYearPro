import React from 'react';
import '../App.css';

import { Row, Col } from 'react-bootstrap';
import 'react-bootstrap';

export const Footer = () =>
    <Row className="App-footer">
        <Col>
            <h3>Vicinity</h3>
            <p>Powered by react</p>
        </Col>
        <Col className="ml-auto">
            <h3>Final year pro by: </h3>
            <p>Eimhin Dunne and Adam McElroy</p>
        </Col>
        <Col className="ml-auto">
            <h3>DCU</h3>
            <p>Computer Applications</p>
        </Col>
    </Row>