import React, { useCallback } from "react";
import { withRouter } from "react-router";
import app from './base';

import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { Container, Row, Col, Form, ButtonToolbar, ToggleButtonGroup, ToggleButton, Button } from 'react-bootstrap';
import 'react-bootstrap';

var interes = []
function functionName(name){
    // if (name in interes){
    //     interes.remove(name);
    // } else {
    //     interes.push(name);
    // }
    interes.push(name);
    console.log(interes)
};

const Interests = ({ history }) => {
    const handleSignUp = useCallback(async event => {
        event.preventDefault();
        const { checkbox } = event.target.elements;
        try {
            await app
                // TODO: upload interes to firebase here
                console.log(checkbox);
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
                        <h1>Interests</h1>
                        <p>Please select what you are interested in</p>
                        <Form onSubmit={handleSignUp}>
                            <ButtonToolbar>
                                <ToggleButtonGroup name="checkbox" type="checkbox" defaultValue={[]} className="mx-auto">
                                    <ToggleButton value={1} onClick={() => functionName("Sport")}>Sports</ToggleButton>
                                    <ToggleButton value={2} onClick={() => functionName("Music")}>Music</ToggleButton>
                                    <ToggleButton value={3} onClick={() => functionName("ArtsAndCrafts")}>Arts and crafts</ToggleButton>
                                    <ToggleButton value={4} onClick={() => functionName("Free")}>Free</ToggleButton>
                                </ToggleButtonGroup>
                            </ButtonToolbar>
                            <div className="mt-4">
                                <Button type="submit">Add Interests</Button>
                            </div>
                        </Form>
                    </Col>
                </Row>
            </Container>
        </div>
    );
};

export default withRouter(Interests);

