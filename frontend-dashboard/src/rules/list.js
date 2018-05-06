import React from 'react';
import { List, Datagrid, EditButton, TextField, translate } from 'admin-on-rest';
import Chip from 'material-ui/Chip';

const RulesTitle = () => {
    return <span>Listado de Reglas</span>;
};

export const RulesList = (props) => (
    <List title={<RulesTitle />} {...props}>
        <Datagrid>
            <TextField source="name" label="resources.categories.fields.name" />
            <EditButton />
        </Datagrid>
    </List>
);