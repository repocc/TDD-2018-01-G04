import React from 'react';
import { List, Datagrid, TextField, EditButton } from 'admin-on-rest';

const DashboardsTitle = () => {
    return <span>Listado de Dashboards</span>;
};

export const DashboardsList = (props) => (
    <List title={<DashboardsTitle />} {...props}>
        <Datagrid>
            <TextField source="id" label="resources.dashboard.fields.id" />
            <TextField source="name" label="resources.dashboard.fields.name" />
            <EditButton />
        </Datagrid>
    </List>
);