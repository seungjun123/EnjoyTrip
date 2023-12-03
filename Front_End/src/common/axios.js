import axios from 'axios'

export default axios.create({
  baseURL: 'http://192.168.203.121:8080',
  headers: {
    'Content-Type': 'application/json'
  },
  withCredentials: true
})
