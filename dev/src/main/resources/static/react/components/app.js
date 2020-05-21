import React, { Component } from 'react';
import GoogleMap from './google_map';

export default class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            lat: 43.5487,
            lng: -79.6629
        };

        
    }    

    componentDidMount() { 
        
    }


    render() {
        return (
            <div style={{ width: '100%' }}>
                <GoogleMap lat={this.state.lat} lng={this.state.lng} />
            </div>
            );
    }
}