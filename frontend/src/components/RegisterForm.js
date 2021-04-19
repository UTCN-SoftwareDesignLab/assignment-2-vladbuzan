import React, { useState } from 'react'
import { Button, Container, Dropdown, Form, Input, Label } from 'semantic-ui-react'
import { encodeParams } from '../api/http'
import { register } from '../api/services/auth'
import '../style/style.css'

const RegisterForm = () => {

    const [username, setUsername] = useState()
    const [password, setPassword] = useState()
    const [email, setEmail] = useState()
    const [role, setRole] = useState("REGULAR")

    const roleOptions = [{
        key: 1,
        text: 'Regular',
        value: 'REGULAR'
    },
    {
        key: 2,
        text: 'Admin',
        value: 'ADMIN'
    }]

    const onRegisterClicked = () => {
        register(encodeParams({
            username,
            email,
            password,
            role
        })).then((response) => {
            alert(response.data.message)
        }).catch((_) => {
            alert("Couldn't register user")
        })
    }

    return (
        <div>
            <Container>
                <Form>
                    <Form.Field>
                        <Label>Username</Label>
                        <Input
                            placeholder='Username'
                            value={username}
                            onChange={(e) => setUsername(e.target.value)} />
                    </Form.Field>
                    <Form.Field>
                        <Label>Password</Label>
                        <Input
                            type='password'
                            placeholder='Password'
                            value={password}
                            onChange={(e) => setPassword(e.target.value)} />
                    </Form.Field>
                    <Form.Field>
                        <Label>Email</Label>
                        <Input
                            type='email'
                            placeholder='Email'
                            value={email}
                            onChange={(e) => setEmail(e.target.value)} />
                    </Form.Field>
                    <Form.Field>
                        <Dropdown
                            placeholder='Role'
                            search selection options={roleOptions}
                            onChange={(_, value) => setRole(value.value)} />
                    </Form.Field>
                    <Button type='LogIn'
                        onClick={onRegisterClicked} >
                        Register
                </Button>
                </Form>
            </Container>
        </div>
    )
}

export default RegisterForm