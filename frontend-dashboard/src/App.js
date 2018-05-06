import React from 'react';
import { Admin, Resource, Delete } from 'admin-on-rest';

import myApiRestClient from './common/restClient' 
import authClient from './common/authClient';

import { RulesList, RuleCreate, RuleEdit } from './rules';
import RuleIcon from 'material-ui/svg-icons/action/label';

import spanishMessages from 'aor-language-spanish';
import customSpanishMessages from './common/i18n/es';

const messages = {
    es: { ...spanishMessages, ...customSpanishMessages }
};

const App = () => (
    <Admin authClient={authClient} restClient={myApiRestClient} title="Dashboard" locale="es" messages={messages}>
        <Resource name="rule" list={RulesList} create={RuleCreate} icon={RuleIcon} />
    </Admin>
);

export default App;