import React from 'react';
import { Create, SimpleForm, TextInput, maxLength, required } from 'admin-on-rest';

const RuleCreateTitle = () => {
    return <span>Crear regla</span>;
};

export const RuleCreate = (props) => (
  <Create title={<RuleCreateTitle />} {...props}>
      <SimpleForm redirect="list">
          <TextInput source="name" label="resources.rule.fields.name" validate={[ required, maxLength(100) ]} />
          <TextInput source="query" label="resources.rule.fields.query" options={{ fullWidth: true }}  validate={[ required ]} />
      </SimpleForm>
  </Create>
);