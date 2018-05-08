import React from 'react';
import { Show, SimpleShowLayout, TextField, BooleanField } from 'admin-on-rest';
import RefreshActions from '../common/components/refreshActions'

const DashboardTitle = ({ record }) => {
    return <span>Dashboard</span>;
};

export const DashboardShow = (props) => (
    <Show title={<DashboardTitle />} {...props} actions={<RefreshActions refreshInterval="10000" />}>
        <SimpleShowLayout>
            <TextField source="name" />
            <TextField source="rule_ids" />
        </SimpleShowLayout>
    </Show>
);