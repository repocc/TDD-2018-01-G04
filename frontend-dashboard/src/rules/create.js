import React from 'react';
import { Create, ReferenceInput, SelectInput, SimpleForm, BooleanInput, TextInput, translate, maxLength, required } from 'admin-on-rest';

const RuleCreateTitle = () => {
    return <span>Crear regla</span>;
};

export const RuleCreate = (props) => (
  <Create title={<RuleCreateTitle />} {...props}>
      <SimpleForm redirect="list">
          <TextInput source="name" validate={[ required, maxLength(100) ]} />
      </SimpleForm>
  </Create>
);