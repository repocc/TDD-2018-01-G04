import React from 'react';
import { Create, SimpleForm, TextInput, maxLength, required, ReferenceArrayInput, SelectArrayInput } from 'admin-on-rest';

const DashboardCreateTitle = () => {
    return <span>Crear dashboard</span>;
};

export const DashboardCreate = (props) => (
  <Create title={<DashboardCreateTitle />} {...props}>
      <SimpleForm redirect="list">
          <TextInput source="name" label="resources.dashboard.fields.name" validate={[ required, maxLength(100) ]} />
          <ReferenceArrayInput label="resources.dashboard.fields.rule_ids" source="rule_ids" reference="rule" allowEmpty>
            <SelectArrayInput optionText="name" />
          </ReferenceArrayInput>
      </SimpleForm>
  </Create>
);