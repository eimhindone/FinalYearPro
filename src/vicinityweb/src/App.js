import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { Home } from './Home';
import { Events } from './Events';
import { Filter } from './Filter';
import { NoMatch } from './NoMatch';
import Interests from './Interests';
import Login from './Login';
import SignUp from './SignUp';
import { Signout } from './Signout';
import { Navibar } from './components/Navibar';
import { Footer } from './components/Footer';
import { AuthProvider } from './Auth'
import PrivateRoute from './PrivateRoute'

import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import 'react-bootstrap';

const App = () => {
  return (
    <AuthProvider>
      {/* <React.Fragment> */}
      <Router>
        <Navibar />
        <div>
          <Switch>
            <PrivateRoute exact path="/home" component={Home} />
            <PrivateRoute exact path="/events" component={Events} />
            <PrivateRoute exact path="/filter" component={Filter} />
            <Route exact path="/login" component={Login} />
            <Route exact path="/signup" component={SignUp} />
            <Route exact path="/signout" component={Signout} />
            <Route exact path="/interests" component={Interests} />
            <Route component={NoMatch} />
          </Switch>
        </div>
        <Footer />
      </Router>
      {/* </React.Fragment> */}
    </AuthProvider>
  );
}

export default App;