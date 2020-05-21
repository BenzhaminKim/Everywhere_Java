import React from 'react';

export default class extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            number: 0
        }
    }

    componetDidMount() {
    }
    increaseNumber() {
        this.setState(prevState => { return ({ number: prevState.number + 1 }); })
    }
    render() {
        return (
            <div>
                <h4>{this.props.number}</h4>
                <button onClick={() => { this.increaseNumber(); }}> incraseNumber </button>
                <h4>{this.state.number}</h4>
            </div>
            );
    }
}