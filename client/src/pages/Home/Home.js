import React, { Component } from 'react';
// import { Link } from 'react-router-dom';

import {
    getPosts
} from '../../utils'

class Home extends Component{

    constructor(props){
        super(props);
        this.state = {
            numChildren: 0
        };
        this.getResults = this.getResults.bind(this);
    }

    async componentDidMount() {};

    async getResults(){
        this.setState({
            numChildren: 0
        })
        let result
        try{
            result = await getPosts();
            console.log(result);
            console.log(typeof(result));
        }
        catch (err){
            if(err.message){
                console.error(err);
                console.error(err.message);
            }
            else{
                console.error("Something went wrong: " + err);
            }
        }
        for(let i = 0; i < result.length; i++){
            this.setState(
                {numChildren: i}
            )
        }
        return;
    }

    render(){
        const posts = [];
        for(let i = 0; i < this.state.numChildren; i+= 1){
            posts.push(<p key={i}>{i}</p>)
        }
        return(
            <div>
                <p>Home!</p>
                <button onClick={this.getResults}>Get Posts</button>
                <div>
                    {posts}
                </div>
            </div>
        )
    }
}

export default Home;