import React from 'react';
import { List, Datagrid, TextField } from 'admin-on-rest';

const RulesTitle = () => {
    return <span>Listado de Reglas</span>;
};

export const RulesList = (props) => (
    <List title={<RulesTitle />} {...props}>
        <Datagrid>
            <TextField source="name" label="resources.rule.fields.name" />
            <TextField source="query" label="resources.rule.fields.query" />
        </Datagrid>
    </List>
);