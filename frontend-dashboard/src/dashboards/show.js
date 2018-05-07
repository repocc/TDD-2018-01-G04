import React from 'react';
import { Show, SimpleShowLayout, TextField, ReferenceArrayField } from 'admin-on-rest';

const DashboardTitle = ({ record }) => {
    return <span>Dashboard {record ? `"${record.name}"` : ''}</span>;
};

export const DashboardShow = (props) => (
    <Show title={<DashboardTitle />} {...props}>
        <SimpleShowLayout>
            <TextField source="name" />
            
        </SimpleShowLayout>
    </Show>
);