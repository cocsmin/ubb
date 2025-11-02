import React, { useEffect } from 'react';
import { IonApp, IonRouterOutlet } from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';
import { Route, Redirect } from 'react-router-dom';
import AlbumsList from './pages/AlbumsList';
import AlbumDetail from './pages/AlbumDetail';
import Login from './pages/Login';
import auth from './services/auth';
import { connectSocket } from './services/socket';

import '@ionic/react/css/core.css';
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';

const PrivateRoute: any = ({ component: Component, ...rest }: any) => {
    return (
        <Route {...rest} render={(props: any) => (
            auth.isAuthenticated() ? <Component {...props} /> : <Redirect to="/login" />
        )} />
    );
};

const App: React.FC = () => {
    useEffect(() => {
        if (auth.isAuthenticated()) {
            connectSocket();
        }
    }, []);

    return (
        <IonApp>
            <IonReactRouter>
                <IonRouterOutlet>
                    <Route exact path="/login" component={Login} />
                    <PrivateRoute exact path="/albums" component={AlbumsList} />
                    <PrivateRoute exact path="/albums/new" component={AlbumDetail} />
                    <PrivateRoute exact path="/albums/:id" component={AlbumDetail} />
                    <Route exact path="/" render={() => <Redirect to={auth.isAuthenticated() ? "/albums" : "/login"} />} />
                </IonRouterOutlet>
            </IonReactRouter>
        </IonApp>
    );
};

export default App;
