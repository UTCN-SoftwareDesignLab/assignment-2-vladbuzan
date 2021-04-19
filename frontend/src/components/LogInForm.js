import React, { useState } from 'react'
import { useHistory } from 'react-router'
import { Button, Container, Form, Input, Label } from 'semantic-ui-react'
import { encodeParams } from '../api/http'
import { logIn } from '../api/services/auth'
import { useCookies } from 'react-cookie'
import '../style/style.css'

const LogInForm = () => {

    const [username, setUsername] = useState()
    const [password, setPassword] = useState()
    const [cookies, setCookie, removeCookie] = useCookies(["user"]);
    const history = useHistory()

    const onLogInClicked = () => {
        logIn(encodeParams({
            username, password
        })).then((response) => {
            saveUserCookie(response.data)
            redirect(response.data)
        }).catch((reason) => alert(reason))
    }

    const saveUserCookie = (user) => {
        removeUserCookie()
        setCookie("user", user, { "path": "/" });
    }

    const removeUserCookie = () => {
        if (cookies.user !== undefined) {
            removeCookie("user", { "path": "/" });
        }
    }

    const redirect = (user) => {
        console.log(user.role)
        if (user.role === "REGULAR") {
            history.push('/bookstore')
        } else {
            history.push('/admin')
        }
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
                    <Button onClick={onLogInClicked} type='LogIn'>
                        Log In
                </Button>
                </Form>
            </Container>
        </div>
    )
}

export default LogInForm