import React, { Component } from 'react';
import { Admin, Resource, Delete } from 'admin-on-rest';

import myApiRestClient from './common/restClient' 
import authClient from './common/authClient';

import { DashboardsList, DashboardCreate, DashboardEdit, DashboardShow } from './dashboards';
import { RulesList, RuleCreate } from './rules';
import GenericIcon from 'material-ui/svg-icons/action/label';

import spanishMessages from 'aor-language-spanish';
import customSpanishMessages from './common/i18n/es';

import Login from './Login';

const ADMIN_PROFILE = 'admin';

const messages = {
    es: { ...spanishMessages, ...customSpanishMessages }
};


class App extends Component {
    render() {
        console.log(this.props); 
        return (
            <Admin authClient={authClient} restClient={myApiRestClient} loginPage={Login} title="Dashboard" locale="es" messages={messages}>
                {permissions => [
                    <Resource
                        name="dashboard"
                        list={DashboardsList}
                        show={DashboardShow}
                        create={permissions === ADMIN_PROFILE ? DashboardCreate : null}
                        edit={permissions === ADMIN_PROFILE ? DashboardEdit : null}
                        remove={permissions === ADMIN_PROFILE ? Delete : null}
                        icon={GenericIcon}
                    />,
                    permissions === ADMIN_PROFILE
                        ? <Resource name="rule" list={RulesList} create={RuleCreate} icon={GenericIcon} />
                        : null,
                ]}
            </Admin>
        );
    }
}

export default App;