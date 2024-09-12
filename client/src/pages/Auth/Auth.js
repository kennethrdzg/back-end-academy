import { Component } from "react";
import { Link }from 'react-router-dom';
import { userRegister } from "../../utils";

class Auth extends Component {

    constructor(props) {
        super(props);

        const pathname = window.location.pathname.replace('/', '');

        this.state = {};
        this.state.state = pathname;
        this.state.loading = true;
        this.state.error = null;
        this.state.formUsername = '';
        this.state.formPassword = '';

        // Bindings
        this.handleFormInput = this.handleFormInput.bind(this);
        this.handleFormTypeChange = this.handleFormTypeChange.bind(this);
        this.handleFormSubmit = this.handleFormSubmit.bind(this);
    }

    componentDidMount() {
        this.setState({
            loading: false
        })

        // Clear query params
        const url = document.location.href;
        window.history.pushState({}, '', url.split('?')[0]);
    };

    handleFormTypeChange(type){
        this.setState(
            {state: type}
        )
    }

    handleFormInput(field, value) {
        value = value.trim();

        const nextState = {};
        nextState[field] = value;

        this.setState(Object.assign(this.state, nextState));
    }

    async handleFormSubmit(event){
        event.preventDefault();

        this.setState({loading: true});

        // Validate username
        if(!this.state.formUsername){
            return this.setState({
                loading: false, 
                formError: 'Username is required'
            })
        }
        if(!this.state.formPassword){
            return this.setState({
                loading: false, 
                formError: 'Password is required'
            })
        }

        let token;
        try{
            if(this.state.state === 'register'){
                token = await userRegister(this.state.formUsername, this.state.formPassword);
            } else {
                token = 'Not yet';
            }
        } catch(err){
            console.error(err);
            if(err.message){
                this.setState({
                    formError: err.message,
                    loading: false
                });
            } else{
                this.setState({
                    formError: 'Something went wrong, try again',
                    loading: false
                });
            }
        }
        console.log("TOKEN: " + token);
        return;
    }

    render(){
        return (
            <div>
                <div>
                <Link to='/'>Home</Link>


                {this.state.loading && (
                    <div>
                        <p>Loading</p>
                    </div>
                )}

                { /* Registration Form */}

                {!this.state.loading && (
                    <div>
                    <div onClick={(e) => { this.handleFormTypeChange('register') }}>
                        <p>Register</p>
                    </div>
                    <div onClick={(e) => { this.handleFormTypeChange('login') }}>
                        <p>Sign-In</p>
                    </div>
                    </div>
                )}

                {this.state.state === 'register' && !this.state.loading && (
                    <div>

                    <form onSubmit={this.handleFormSubmit}>
                        <div>
                        <label>Username</label>
                        <input
                            type='text'
                            placeholder='Username'
                            value={this.state.formEmail}
                            onChange={(e) => { this.handleFormInput('formEmail', e.target.value) }}
                        />
                        </div>
                        <div>
                        <label>Password</label>
                        <input
                            type='password'
                            placeholder='your password'
                            value={this.state.formPassword}
                            onChange={(e) => { this.handleFormInput('formPassword', e.target.value) }}
                        />
                        </div>

                        {this.state.formError && (
                        <div>{this.state.formError}</div>
                        )}

                        <input type='submit' value='Register'/>
                    </form>
                    </div>
                )}

                {this.state.state === 'login' && !this.state.loading && (
                    <div>

                    <form onSubmit={this.handleFormSubmit}>
                        <div>
                        <label>Username</label>
                        <input
                            type='text'
                            placeholder='Username'
                            value={this.state.formEmail}
                            onChange={(e) => { this.handleFormInput('formEmail', e.target.value) }}
                        />
                        </div>
                        <div>
                        <label>Password</label>
                        <input
                            type='password'
                            placeholder='your password'
                            value={this.state.formPassword}
                            onChange={(e) => { this.handleFormInput('formPassword', e.target.value) }}
                        />
                        </div>

                        {this.state.formError && (
                        <div>{this.state.formError}</div>
                        )}

                        <input type='submit' value='Sign In' />
                    </form>
                    </div>
                )}
                </div>
            </div>
        )
    }
}

export default Auth;