import React from 'react';
import { Edit, SimpleForm, TextInput, BooleanInput, maxLength, required, ReferenceArrayInput, SelectArrayInput } from 'admin-on-rest';

const DashboardTitle = ({ record }) => {
    return <span>Dashboard {record ? `"${record.name}"` : ''}</span>;
};

export const DashboardEdit = (props) => (
  <Edit title={<DashboardTitle />} {...props}>
    <SimpleForm redirect="list">
        <TextInput source="name" label="resources.dashboard.fields.name" validate={[ required, maxLength(100) ]} />
        <BooleanInput source="enabled" label="resources.dashboard.fields.enabled" options={{
            labelPosition: 'right'
        }} />
        <ReferenceArrayInput label="resources.dashboard.fields.rule_ids" source="rule_ids" reference="rule" allowEmpty>
          <SelectArrayInput optionText="name" />
        </ReferenceArrayInput>
    </SimpleForm> 
  </Edit>
);