import React, { Component } from 'react'
import FlatButton from 'material-ui/FlatButton'
import { CardActions } from 'material-ui/Card'
import NavigationRefresh from 'material-ui/svg-icons/navigation/refresh'
import { connect } from 'react-redux'
import { refreshView as refreshViewAction } from 'admin-on-rest/src/actions/uiActions'
import { ListButton } from 'admin-on-rest';

class MyRefresh extends Component {
    componentDidMount() {
        const { refreshInterval, refreshView } = this.props
        if (refreshInterval) {
            this.interval = setInterval(() => {
                refreshView()
            }, refreshInterval)
        }
    }

    componentWillUnmount() {
        clearInterval(this.interval)
    }

    render() {
        const { label, refreshView, icon } = this.props;
        return (
            <FlatButton
                primary
                label={label}
                onClick={refreshView}
                icon={icon}
            />
        );
    }
}

const RefreshButton = connect(null, { refreshView: refreshViewAction })(MyRefresh)

const RefreshActions = ({ resource, filters, displayedFilters, filterValues, basePath, showFilter, refreshInterval }) => (
    <CardActions>
        {filters && React.cloneElement(filters, { resource, showFilter, displayedFilters, filterValues, context: 'button' }) }
        <ListButton/>
        <RefreshButton primary label="ACTUALIZAR" refreshInterval={refreshInterval} icon={<NavigationRefresh />} />
    </CardActions>
);

export default RefreshActions