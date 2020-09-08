import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

import { Container, Button } from 'react-bootstrap';
import 'react-bootstrap';
import app from './base';

export const Signout = () => {
    return (
        <div className="App-main">
            <Container>
                <h1>Sign Out</h1>
                <div className="App-padding">
                    <Button onClick={() => app.auth().signOut()} href="/Login">Sign out</Button>
                </div>
            </Container>
        </div>
    );
}
