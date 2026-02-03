import { useState, useEffect } from 'react'
//import axios from 'axios'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [count, setCount] = useState(0)
  const [backendStatus, setBackendStatus] = useState('Checking...')
  const [backendMessage, setBackendMessage] = useState('')
  const [loading, setLoading] = useState(true)
  const [userData, setUserData] = useState(null)
  const [error, setError] = useState('')

  // Test backend connection on component mount
  useEffect(() => {
    checkBackendConnection()
    fetchUserData()
  }, [])

  // Function to test backend connection
  const checkBackendConnection = async () => {
    try {
      setLoading(true)
      setError('')
      
      // Test basic connection
      const response = await axios.get('http://localhost:8080/api/test')
      setBackendStatus('✅ Connected')
      setBackendMessage(response.data)
      
      console.log('Backend connection successful:', response.data)
    } catch (err) {
      setBackendStatus('❌ Disconnected')
      setBackendMessage('Cannot reach backend server')
      setError(`Error: ${err.message}`)
      
      console.error('Backend connection failed:', err)
      
      // More detailed error info
      if (err.response) {
        console.error('Response status:', err.response.status)
        console.error('Response data:', err.response.data)
      } else if (err.request) {
        console.error('No response received. Check if backend is running.')
      }
    } finally {
      setLoading(false)
    }
  }

  // Function to fetch user data from backend
  const fetchUserData = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/user')
      setUserData(response.data)
      console.log('User data fetched:', response.data)
    } catch (err) {
      console.error('Failed to fetch user data:', err)
    }
  }

  // Function to simulate login
  const handleLogin = async () => {
    try {
      const response = await axios.post('http://localhost:8080/api/login', {
        username: 'testuser',
        password: 'testpass'
      })
      alert(`Login successful! Token: ${response.data.token}`)
      console.log('Login response:', response.data)
    } catch (err) {
      alert('Login failed. Check console for details.')
      console.error('Login error:', err)
    }
  }

  return (
    <>
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      
      <h1>Vite + React + Spring Boot</h1>
      
      {/* Backend Connection Status */}
      <div className="card backend-status">
        <h2>Backend Connection</h2>
        <div className={`status ${backendStatus.includes('✅') ? 'connected' : 'disconnected'}`}>
          <strong>Status:</strong> {backendStatus}
        </div>
        <p><strong>Message:</strong> {backendMessage}</p>
        
        {loading && <p className="loading">Checking connection...</p>}
        
        {error && (
          <div className="error">
            <p><strong>Error Details:</strong> {error}</p>
            <p className="help-text">
              Make sure your Spring Boot backend is running on <code>http://localhost:8080</code>
            </p>
          </div>
        )}
        
        <div className="action-buttons">
          <button onClick={checkBackendConnection} disabled={loading}>
            {loading ? 'Testing...' : 'Test Connection Again'}
          </button>
          <button onClick={handleLogin} className="login-btn">
            Test Login
          </button>
        </div>
      </div>

      {/* User Data Display */}
      {userData && (
        <div className="card user-data">
          <h2>User Data from Backend</h2>
          <div className="user-info">
            <p><strong>ID:</strong> {userData.id}</p>
            <p><strong>Name:</strong> {userData.name}</p>
            <p><strong>Email:</strong> {userData.email}</p>
          </div>
        </div>
      )}

      {/* Original Counter */}
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.jsx</code> and save to test HMR
        </p>
      </div>

      {/* Instructions */}
      <div className="instructions">
        <h3>Setup Instructions:</h3>
        <ol>
          <li>Make sure Spring Boot backend is running on <code>http://localhost:8080</code></li>
          <li>Check that CORS is properly configured in backend</li>
          <li>Verify backend endpoints: 
            <ul>
              <li><code>GET /api/test</code> - Connection test</li>
              <li><code>GET /api/user</code> - User data</li>
              <li><code>POST /api/login</code> - Authentication</li>
            </ul>
          </li>
          <li>Check browser console for detailed logs</li>
        </ol>
      </div>

      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>

      {/* Debug Info */}
      <div className="debug-info">
        <p><strong>Frontend URL:</strong> http://localhost:5173</p>
        <p><strong>Backend URL:</strong> http://localhost:8080</p>
        <p><strong>API Base URL:</strong> http://localhost:8080/api</p>
      </div>
    </>
  )
}

export default App