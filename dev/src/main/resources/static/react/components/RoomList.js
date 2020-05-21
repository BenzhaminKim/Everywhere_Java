import React from 'react'

export default class extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            number: 0,
            answer : "something"
        }
    }
    increase() {
        this.setState(prevState => {
            return (
                {
                    number: prevState.number + 1
                });
        })
    }
    changeAnswer() {
        this.setState(
            {
                    answer: "answered!"
            }
        );
    }
    render() {
        return(
            <div>
                <button onClick={() => { this.increase() }}>change number</button>
                <h5>{this.state.number}s</h5>
                <button onClick={() => { this.changeAnswer() }}>change answer</button>
                <h5>{this.state.answer}s</h5>
                <h5>{this.props.lat}s</h5>
                <h5>{this.props.lng}</h5>
            </div>
         );
    }
}