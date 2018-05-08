import React from 'react';
import { List, Datagrid, TextField, BooleanField, EditButton, ShowButton } from 'admin-on-rest';

const DashboardsTitle = () => {
    return <span>Listado de Dashboards</span>;
};

const ADMIN_PROFILE = 'admin';

export const DashboardsList = (props) => (
    <List title={<DashboardsTitle />} {...props}>
        {permissions =>
            <Datagrid>
                <TextField source="id" label="resources.dashboard.fields.id" />
                <TextField source="name" label="resources.dashboard.fields.name" />
                <BooleanField source="enabled" label="resources.dashboard.fields.enabled" />
                <ShowButton/>
                {permissions === ADMIN_PROFILE && <EditButton />}
            </Datagrid>
        }
    </List>
);