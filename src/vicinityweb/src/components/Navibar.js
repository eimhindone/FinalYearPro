import React from 'react';
import '../App.css';

import {Navbar, Nav, NavDropdown, Form, FormControl, Button} from 'react-bootstrap';

import { LinkContainer } from "react-router-bootstrap";


export const Navibar = () => 
<Navbar bg="dark" variant="dark" expand="lg">
  <Navbar.Brand href="">Vicinity</Navbar.Brand>
  <Navbar.Toggle aria-controls="basic-navbar-nav" />
  <Navbar.Collapse id="basic-navbar-nav">
    <Nav className="mr-auto">
      <LinkContainer to="/home">
        {/* <NavItem className="mr-3">Home</NavItem> */}
        <Button variant="dark" className="mr-3">Home</Button>
      </LinkContainer>
      <LinkContainer to="/Events">
        <Button variant="dark" className="mr-3">Events</Button>
      </LinkContainer>
      <LinkContainer to="/Filter">
        <Button variant="dark">Filters</Button>
      </LinkContainer>
    </Nav>
    <Form inline>
    <Nav className="mr-auto">
        <NavDropdown title="User" className="App-basic-nav-dropdown">
          <NavDropdown.Item href="/Login">Log In</NavDropdown.Item>
          <NavDropdown.Item href="/SignUp">Sign Up</NavDropdown.Item>
          <NavDropdown.Divider />
          <NavDropdown.Item href="/Signout">Log Out</NavDropdown.Item>
        </NavDropdown>
      </Nav>
      <FormControl type="text" placeholder="Search" className="mr-sm-2" />
      <Button variant="outline-success">Search</Button>
    </Form>
  </Navbar.Collapse>
</Navbar>