import React, { Component } from 'react';
import Room from './RoomList';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Slider from '@material-ui/core/Slider';
import Dialog from '@material-ui/core/Dialog';
import Button from '@material-ui/core/Button';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogContent from '@material-ui/core/DialogContent';
import FormGroup from '@material-ui/core/FormGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';


export default class extends Component {
    constructor(props) {
        super(props);
        this.state = {
            locations: [],
            locationsOnMap: [],
            filterdLocations: [],
            roomsType: [],
            markers:[],
            southWestLat: 0,
            southWestLng: 0,
            northEastLat: 0,
            northEastLng: 0,
            currentPage: 1,
            postsPerPage: 6,
            totalRooms: 0,
            firstPage: 1,
            lastPage: 0,
            isMobile: false,
            lowPrice: 0,
            highPrice: 1000,
            toggleDialog: false,
            isPetFriendly: false,
            type: "Room",
            lat: 43.5487,
            lng: -79.6629,
            zoom: 14
        }


    }


    UNSAFE_componentWillReceiveProps(nextProps) {
        this.setState(
            {
                lowPrice: nextProps.lowPrice,
                highPrice: nextProps.highPrice
            });
    }

    fetchAllData() {
        var url =  "http://tomcat-env.eba-e34hkyqq.ca-central-1.elasticbeanstalk.com/api/rooms"//"http://localhost:8080/api/rooms";////"https://localhost:44333/api/location";//
        fetch(url, { mode: 'cors' })
            .then(response => { return response.json(); })
            .then(location => {
                this.addMarkers(location);
                this.setState({
                    locations: location,
                    filterdLocations: location,
                    locationsOnMap: location,
                    totalRooms: location.length
                });
            })
            .catch(error => console.log(error));


    }
    updateRoomList() {
        var mapBounds = this.map.getBounds();
        var southWestLat = mapBounds.getSouthWest().lat();
        var southWestLng = mapBounds.getSouthWest().lng();
        var northEastLat = mapBounds.getNorthEast().lat();
        var northEastLng = mapBounds.getNorthEast().lng();
        
        var locationsOnMap = this.state.filterdLocations.filter(function (item) {
            return (southWestLat <= item['room'].latitude && northEastLat >= item['room'].latitude && southWestLng <= item['room'].longitude && northEastLng >= item['room'].longitude)

        })

        this.setState({
            locationsOnMap: locationsOnMap,
            southWestLat: southWestLat,
            southWestLng: southWestLng,
            northEastLat: northEastLat,
            northEastLng: northEastLng,
            totalRooms: locationsOnMap.length,
            currentPage: 1
        });
        this.setState({
            lastPage: Math.ceil(this.state.totalRooms / this.state.postsPerPage)
        });

    }
    getLocations() {     

        this.map.addListener('zoom_changed', () => {
            this.updateRoomList();
            this.storeLocation(this.map.getCenter().lat(), this.map.getCenter().lng(), this.map.getZoom());
            
           
        });
        this.map.addListener('dragend', () => {
            this.updateRoomList();
            this.storeLocation(this.map.getCenter().lat(), this.map.getCenter().lng(), this.map.getZoom());
           
        });       
    }
    addMarkers(locations) {
        const markers = [];
        locations.map((location, index) => {
            const marker = new google.maps.Marker({
                position: { lat: location['room'].latitude, lng: location['room'].longitude },
                map: this.map,
                title: 'his'
            });
            markers.push(marker);
        });
        this.markerCluster.addMarkers(markers);
        this.setState({
            markers: markers
        });

    }

    componentDidMount() {
        let lat;
        let lng;
        let zoom;
        if (localStorage.getItem('lat') != null && localStorage.getItem('lng') != null && localStorage.getItem('zoom') != null) {
            lat = localStorage.getItem('lat');
            lng = localStorage.getItem('lng');
            zoom = parseInt(localStorage.getItem('zoom'));
            
        } else {
            lat = this.state.lat;
            lng = this.state.lng;
            zoom = this.state.zoom;
        }
        this.map = new google.maps.Map(this.refs.map, {
            zoom: zoom,
            center: new google.maps.LatLng(lat, lng),
            mapTypeId: google.maps.MapTypeId.ROADMAP,
            disableDefaultUI: true,
            gestureHandling: "greedy"
        });

        this.markerCluster = new MarkerClusterer(this.map, [], {
            imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'
        });

        this.fetchAllData();
        this.getLocations();

    }

    nextPage() {
        if (this.state.currentPage != this.state.lastPage) {
            this.setState(prevState => {
                return ({
                    currentPage: prevState.currentPage + 1
                });
            })
        }        
    }
    prevPage() {
        if (this.state.currentPage != this.state.firstPage) {
            this.setState(prevState => {
                return ({
                    currentPage: prevState.currentPage - 1
                });
            })
        }        
    }
    moveToPage(pageNumber) {
        this.setState({ currentPage: pageNumber })
    }
    storeLocation(lat, lng, zoom) {
        localStorage.setItem('lat', lat);
        localStorage.setItem('lng', lng);
        localStorage.setItem('zoom', zoom);
    }
    PageNation() {

        if (this.state.lastPage > 1) {
            return (
                <nav className="Page navigation col-lg-12 ml-3 mr-3 mb-1 mt-1">
                    <ul className="pagination  justify-content-center">
                        <li className="page-item">
                            <a className="page-link" href="#" onClick={() => { this.prevPage(); }} aria-label="Previous">
                                <span aria-hidden="true"><i className="fas fa-arrow-left"></i></span>
                            </a>
                        </li>
                        <li className="page-item"><a className="page-link" onClick={() => { this.moveToPage(this.state.firstPage); }} href="#">{this.state.firstPage}</a></li>
                        <li className="page-item"><b className="page-link" href="#"><i className="fas fa-ellipsis-h"></i></b></li>
                        <li className="page-item"><a className="page-link" onClick={() => { this.moveToPage(this.state.lastPage); }} href="#">{this.state.lastPage}</a></li>
                        <li className="page-item">
                            <a className="page-link" href="#" onClick={() => { this.nextPage(); }} aria-label="Next">
                                <span aria-hidden="true"><i className="fas fa-arrow-right"></i></span>
                            </a>
                        </li>
                    </ul>
                </nav>
            );
        } else {
            return (
                <nav className="Page navigation col-lg-12 ml-3 mr-3 mb-1 mt-1">
                    <ul className="pagination  justify-content-center">
                        <li className="page-item">
                            <a className="page-link" onClick={() => { this.prevPage(); }} href="#" aria-label="Previous">
                                <span aria-hidden="true"><i className="fas fa-arrow-left"></i></span>
                            </a>
                        </li>
                        <li className="page-item"><a className="page-link" href="#">{this.state.firstPage}</a></li>
                        <li className="page-item">
                            <a className="page-link" onClick={() => { this.nextPage(); }} href="#" aria-label="Next">
                                <span aria-hidden="true"><i className="fas fa-arrow-right"></i></span>
                            </a>
                        </li>
                    </ul>
                </nav>
            );
        }
    }
    checkMobileRoom(isMobile) {
        var rooms;

            rooms = this.state.locationsOnMap.map((room, key) =>
                <div className="col-6" key={room['room'].roomId} >
                    <a href={"/room/detail/" + room['room'].roomId} >
                        <img className="room-image rounded img-fluid" src={room['images'][0].imagePath} />
                        <h5 className="text-dark">${room['room'].price} CAD/month</h5>
                        <h6 className="text-secondary">{room['room'].title}</h6>
                    </a>
                </div>
            );


            const indexOfLastPost = this.state.currentPage * this.state.postsPerPage;
            const indexOfFirstPost = indexOfLastPost - this.state.postsPerPage;
            rooms = rooms.slice(indexOfFirstPost, indexOfLastPost);


        if (isMobile) {
            return (
                <div className="hideRoomList col-lg-5 col-md-5 col-sm-12 border-top">

                    <div className="text-primary row m-2"><h3 className="col-12 text-center">{this.state.totalRooms} Rooms</h3></div>

                    <div className="roomList">
                        {rooms}
                        {this.PageNation()}
                    <span className="col-lg-12 m-3 text-muted text-center font-italic">
                            Rooms Everywhere Near You
                    </span>
                    </div>
                </div>
            );
        }
        else {
            return (
                <div className="col-lg-4 col-md-4 col-sm-12 border-top">

                    <div className="text-primary row m-2"><h3 className="col-12 text-center">{this.state.totalRooms} Rooms</h3></div>

                    <div className="roomList row m-0">
                        {rooms}
                        {this.PageNation()}
                    <span className="col-lg-12 m-3 text-muted text-center font-italic border-top">
                        Rooms Everywhere Near You
                    </span>
                    </div>
                </div>
                );
        }
    }
    CheckMobileMap(isMobile) {
        if (isMobile) {

            return (<div className="map-responsive col-lg-1 col-sm-12" id="map" ref="map" />);
        }
        else {

            return (<div className="map-responsive col-lg-8 col-md-8 hidden-xs-down"  id="map" ref="map" />);
        }
    }
    handleMobileClick() {
        if (this.state.isMobile) {
            this.setState({ isMobile: false });

        } else {
            this.setState({ isMobile: true });

        }
    }
    toggleDialog() {
        if (this.state.toggleDialog) {
            this.setState({
                toggleDialog: false
            });
        }
        else {
            this.setState({
                toggleDialog: true
            });
        }
    }

    filterMap() {
        const locations = this.state.roomsType;
        const lowPrice = this.state.lowPrice;
        const highPrice = this.state.highPrice;

        const filterdLocations = locations.filter(function (item) {
            return (lowPrice <= item.price && highPrice >= item.price);

        })
        this.setState(
            {
                filterdLocations: filterdLocations
            });

        this.clearMarkers();
        this.addMarkers(filterdLocations);
    }


    FilterRoomType(type) {
        const locations = this.state.locations;

        const filterdLocations = locations.filter(function (item) {
            return (type == item.type);

        })
        this.setState(
            {
                filterdLocations: filterdLocations,
                roomsType: filterdLocations
            });

        this.clearMarkers();
        this.addMarkers(filterdLocations);

    }

    clearMarkers() {
        const markers = this.state.markers;
        for (var i = 0; i < markers.length; i++) {
            markers[i].setMap(null);    
        }
        this.markerCluster.clearMarkers();
    }

    changeType(type) {
        this.setState({
            type: type
        });
    }

    FilterBox() {
        const handleChange = (event, newValue) => {

            this.setState(
                {
                    lowPrice: newValue[0],
                    highPrice: newValue[1]
                });

        };
      
        
        return (
            <Dialog open={this.state.toggleDialog} onExit={() => { this.updateRoomList(); }}
                
            >
                <DialogTitle>Filter</DialogTitle>
                <DialogContent>
                    <Typography>
                        Type
                    </Typography>

                    <div className={`RoomType btn ${this.state.type == 'Room' ? 'btn-primary' : 'btn-outline-primary'} pt-1 pb-1 ml-1 mr-1`} onClick={() => { this.FilterRoomType(1); this.changeType("Room"); }} >Room</div>
                    <div className={`RoomType btn ${this.state.type == 'Homestay' ? 'btn-primary' : 'btn-outline-primary'} pt-1 pb-1 ml-1 mr-1`} onClick={() => { this.FilterRoomType(2); this.changeType("Homestay"); }} >Homestay</div>
                    <div className={`RoomType btn ${this.state.type == 'Apartment' ? 'btn-primary' : 'btn-outline-primary'} pt-1 pb-1 ml-1 mr-1`} onClick={() => { this.FilterRoomType(3); this.changeType("Apartment"); }} >Apartment</div>
                    <div className={`RoomType btn ${this.state.type == 'Condo' ? 'btn-primary' : 'btn-outline-primary'} pt-1 pb-1 ml-1 mr-1`} onClick={() => { this.FilterRoomType(4); this.changeType("Condo");}} >Condo</div>
                    <div className={`RoomType btn ${this.state.type == 'House' ? 'btn-primary' : 'btn-outline-primary'} pt-1 pb-1 ml-1 mr-1`} onClick={() => { this.FilterRoomType(5); this.changeType("House");}} >House</div>
                    <div className={`RoomType btn ${this.state.type == 'Buy_Sell' ? 'btn-primary' : 'btn-outline-primary'} pt-1 pb-1 ml-1 mr-1`} onClick={() => { this.FilterRoomType(6); this.changeType("Buy_Sell"); }} >Buy/Sell</div>

                    <Typography>
                         Price
                    </Typography>

                    <Slider
                        defaultValue={[this.state.lowPrice, this.state.highPrice]}
                        aria-labelledby="discrete-slider"
                        valueLabelDisplay="auto"
                        onChangeCommitted={handleChange}
                        min={0}
                        max={1000}
                    />

      

                    <Button onClick={() => {
                        this.filterMap();
                        this.toggleDialog();
                    }} color="primary">
                        Save
                    </Button>
                    <Button onClick={() => {
                        this.toggleDialog();
                    }} color="primary">
                        Cancel
                    </Button>
                </DialogContent>
            </Dialog>
        );

    }

    render() {

        if (this.state.isMobile) {
            var bottomLetter = <h6>{this.state.totalRooms} Rooms List</h6>;

        } else {
            var bottomLetter = <h6>View Map</h6>;

        }
        

        return (
            <div>
                <div className="row m-0" >
                    
                    {this.checkMobileRoom(this.state.isMobile)}
 
                    {this.CheckMobileMap(this.state.isMobile)}
                    <div className="ShowMap">
                        <div onClick={() => { this.handleMobileClick(); }} className="fixed-bottom text-center btn btn-primary">{bottomLetter}</div>
                    </div>
                </div>
             </div>
            );
    }
}