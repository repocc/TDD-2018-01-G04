import React from 'react';
import { Show, SimpleShowLayout } from 'admin-on-rest';
import RefreshActions from '../common/components/refreshActions'

const DashboardTitle = ({ record }) => {
    return <span>Dashboard {record ? `"${record.name}"` : ''}</span>;
};

const DashboardShowRecord = ({ record }) => (
    <table><tr>
         
    {record.counters && record.counters.map(c => {
        return (
            <td key={c.id}>
                <h4>Regla "{c.name}"</h4>
                <p>Valor: {c.value && Object.values(c.value)[0]}</p>
                <table className="dashboard-data">
                    <tr>
                        <td className="title">Fecha</td>
                        <td className="title">Valor</td>
                    </tr>
                    {c.snapshots && c.snapshots.map(s => {
                        return (
                            <tr key={s.date}>
                                <td>{s.date}</td>
                                <td>{s.value && Object.values(s.value)[0]}</td>
                            </tr>
                        )
                    })}
                </table>
            </td>
        )
    })} 
    </tr></table>
);

export const DashboardShow = (props) => (
    <Show title={<DashboardTitle />} {...props} actions={<RefreshActions refreshInterval="1000" />}>
        <SimpleShowLayout>
            <DashboardShowRecord/>
        </SimpleShowLayout>
    </Show>
);