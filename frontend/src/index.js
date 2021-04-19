import React from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import App from './App'
import 'semantic-ui-css/semantic.min.css'
import {BASE_URL} from './api/http'
import { BrowserRouter, Route } from 'react-router-dom'
import { CookiesProvider } from 'react-cookie'
import LandingPage from './navigation/LandingPage'
import AdminPage from './navigation/AdminPage'
import BookStorePage from './navigation/BookStorePage'

ReactDOM.render(
  <BrowserRouter>
    <CookiesProvider>
      <Route exact path = '/'>
        <LandingPage/>
      </Route>
      <Route exact path = '/admin'>
        <AdminPage/>
      </Route>
      <Route exact path = '/bookstore'>
        <BookStorePage/>
      </Route>
    </CookiesProvider>
  </BrowserRouter>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals

