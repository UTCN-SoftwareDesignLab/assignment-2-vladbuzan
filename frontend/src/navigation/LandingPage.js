import React, { useState } from 'react'
import { Button, Container, Grid, Segment } from 'semantic-ui-react'
import LogInForm from '../components/LogInForm'
import RegisterForm from '../components/RegisterForm'
import '../style/style.css'

const LandingPage = () => {
    const [showLogin, setShowLogin] = useState(true)

    const toggle = () => {
        setShowLogin(!showLogin)
    }
    return (
        <Container>
            <Segment>
                <Grid columns={2} relaxed='very' stackable>
                    <Grid.Column>
                {showLogin ? <LogInForm/> : <RegisterForm/>}
                </Grid.Column>
                <Grid.Column>
                {showLogin? <Button content='Sign up' icon='signup' verticalAlign='middle' size='big' onClick = {toggle}/>
                    : <Button content='Sign in' icon='sign-in' size='big' onClick = {toggle} />}
                </Grid.Column>
                </Grid>
                </Segment>
        </Container>
    )
}
export default LandingPage