import React, { Component } from 'react';
import { Admin, Resource, Delete } from 'admin-on-rest';

import myApiRestClient from './common/restClient' 
import authClient from './common/authClient';

import { RulesList, RuleCreate, RuleEdit } from './rules';
import RuleIcon from 'material-ui/svg-icons/action/label';

import spanishMessages from 'aor-language-spanish';
import customSpanishMessages from './common/i18n/es';

import Login from './Login';

const messages = {
    es: { ...spanishMessages, ...customSpanishMessages }
};



class App extends Component {
    render() {
        console.log(this.props); 
        return (
            <Admin authClient={authClient} restClient={myApiRestClient} loginPage={Login} title="Dashboard" locale="es" messages={messages}>
                {permissions => [
                    /*<Resource
                        name="dashboard"
                        list={DashboardLIst}
                        edit={permissions === 'admin' ? DashboardEdit : null}
                        remove={permissions === 'admin' ? DashboardDelete : null}
                        icon={VisitorIcon}
                    />,*/
                    permissions === 'admin'
                        ? <Resource name="rule" list={RulesList} create={RuleCreate} icon={RuleIcon} />
                        : null,
                ]}
            </Admin>
        );
    }
}

export default App;