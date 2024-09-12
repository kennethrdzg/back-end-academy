import React, { Component } from 'react';
import { Link } from 'react-router-dom';

import {
    getPosts
} from '../../utils'

class Home extends Component{

    componentDidMount() {};

    render(){
        return(
        <div>
              <div>
                <p>Home</p>
              </div>
    
              <div>
                <Link to='/register'>
                  <button>
                    Register
                  </button>
                </Link>
              </div>
              <div>
                <Link to='/login'>Sign-in</Link>
              </div>
          </div>
        )
    }
}

export default Home;